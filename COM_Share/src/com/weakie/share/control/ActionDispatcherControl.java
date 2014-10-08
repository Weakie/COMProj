package com.weakie.share.control;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.weakie.share.bean.Point3D;
import com.weakie.share.control.bean.ActionCommand;
import com.weakie.share.control.gen.AbstractActionGenerator;
import com.weakie.share.control.gen.ActionBean;
import com.weakie.share.control.gen.ActionGeneratorProducer;
import com.weakie.share.jni.SendData;
import com.weakie.share.util.LogUtil;

public class ActionDispatcherControl {
	
	private static ActionDispatcherControl instance = new ActionDispatcherControl();
	public static ActionDispatcherControl getInstance() {
		return instance;
	}

	private ScheduledExecutorService taskExecutor;
	private Point3D basePoint;
	
	private ActionDispatcherControl() {
		this.basePoint = new Point3D(0,0,0);
		this.taskExecutor = Executors.newSingleThreadScheduledExecutor();
	}

	public synchronized void setBasePoint(int x,int y,int z){
		this.basePoint.setData(x, y, z);
	}
	
	public synchronized void executeCommands(List<ActionCommand> commandList,
			ProgressControl control, boolean addbase) {
		if (commandList == null || commandList.isEmpty()) {
			LogUtil.info("action list is empty, do not execute it");
			return;
		}
		Runnable task = new DispatcherTask(commandList, control,
				(addbase ? basePoint : null));
		this.taskExecutor.schedule(task, 2, TimeUnit.SECONDS);
	}

	private static class DispatcherTask implements Runnable {

		private Point3D basePoint;
		private ProgressControl control;
		private List<ActionCommand> commandList;

		public DispatcherTask(List<ActionCommand> list, ProgressControl control) {
			this.commandList = list;
			this.control = control;
		}
		
		public DispatcherTask(List<ActionCommand> list, ProgressControl control,Point3D basePoint) {
			this(list, control);
			this.basePoint = basePoint;
		}

		@Override
		public void run() {
			//Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
			try {
				byte[] buf = new byte[SendData.MAX_BUFFER_SIZE];
				this.control.init(this.commandList.size());
				
				end: for (ActionCommand ac : commandList) {
					this.control.begin(ac.getId());
					
					AbstractActionGenerator gen = ActionGeneratorProducer.build(ac);
					long timeFlag = System.currentTimeMillis();
					for (ActionBean b : gen) {
						//check if the threadPool shutdown or task canceled 
						if (Thread.interrupted() || this.control.isCanceled()) {
							break end;
						}
						
						//Format and Send data
						int bufSize = SendData.getInstance().formatPointData(b.getPoint(basePoint), b.getSpeed(), buf);
						SendData.getInstance().sendData(buf,bufSize);
						
						//control time to sleep, avoid the influence of calculate time 
						long sleeptime = b.getTime()+(timeFlag-System.currentTimeMillis());
						Thread.sleep(Math.max(sleeptime,0));
						timeFlag = System.currentTimeMillis();
						
						//update UI of command state
						this.control.update(ac.getId(), "Size="+gen.getSize()+" "+b.getPoint()+" "+b.getSpeed()+" "+sleeptime+" "+b.getTime());
					}
					
					this.control.end(ac.getId());
				}
				
			} catch (InterruptedException e) {
				LogUtil.error(e);
			} catch (Throwable a){
				LogUtil.error(a);
			} finally {
				this.control.close();
			}
		}

	}

	/**
	 * Once shutdown,never come back, this method only called while the app is shutdown!
	 */
	public synchronized void destroy(){
		this.taskExecutor.shutdownNow();
	}
}
