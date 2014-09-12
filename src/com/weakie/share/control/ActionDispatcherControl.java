package com.weakie.share.control;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.weakie.desktop.util.LogUtil;
import com.weakie.share.control.bean.ActionCommand;
import com.weakie.share.control.gen.AbstractActionGenerator;
import com.weakie.share.control.gen.ActionBean;
import com.weakie.share.control.gen.ActionGeneratorProducer;
import com.weakie.share.jni.SendData;

public class ActionDispatcherControl {

	private static ActionDispatcherControl instance = new ActionDispatcherControl();

	public static ActionDispatcherControl getInstance() {
		return instance;
	}

	private ScheduledExecutorService taskExecutor;

	private ActionDispatcherControl() {
		this.taskExecutor = Executors.newSingleThreadScheduledExecutor();
	}

	public void executeCommands(List<ActionCommand> commandList,
			ProgressControl control) {
		if (commandList == null || commandList.isEmpty()) {
			LogUtil.info("action list is empty, do not execute it");
			return;
		}
		Runnable task = new DispatcherTask(commandList, control);
		this.taskExecutor.schedule(task, 2, TimeUnit.SECONDS);
	}

	private static class DispatcherTask implements Runnable {

		private ProgressControl control;
		private List<ActionCommand> commandList;

		public DispatcherTask(List<ActionCommand> list, ProgressControl control) {
			this.commandList = list;
			this.control = control;
		}

		@Override
		public void run() {
			//Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
			try {
				byte[] buf = new byte[32];
				this.control.init(this.commandList.size());
				end: for (ActionCommand ac : commandList) {
					this.control.begin(ac.getId());
					
					AbstractActionGenerator gen = ActionGeneratorProducer.build(ac);
					long timeFlag = System.currentTimeMillis();
					for (ActionBean b : gen) {
						if (Thread.interrupted()) {
							break end;
						}
						SendData.getInstance().formateData(b.getPoint(),b.getSpeed(), buf);
						SendData.getInstance().sendData(buf);
						//control time to sleep,avoid the calculate influence
						Thread.sleep(Math.max(b.getTime()+(timeFlag-System.currentTimeMillis()),0));
						timeFlag = System.currentTimeMillis();
						//update ui
						this.control.update(ac.getId(), b.getPoint()+"");
					}
					
					this.control.end(ac.getId());
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				this.control.close();
			}
		}

	}

}
