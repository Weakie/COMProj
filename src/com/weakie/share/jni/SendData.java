package com.weakie.share.jni;

import com.weakie.share.bean.Point3D;
import com.weakie.share.bean.Speed;

public class SendData {

	private PtrData ptrHandler;
	
	private static SendData instance = new SendData();
	
	private SendData() {
		this.ptrHandler = new PtrData();
	}

	public static SendData getInstance(){
		return instance;
	}
	
	public synchronized void initCOM(){
		if(this.ptrHandler.isInited()){
			System.out.println("COM is already initialized");
			return;
		}
		SendData.initCOM(ptrHandler);
		this.ptrHandler.setInited(true);
	}
	
	public synchronized void sendData(byte[] buffer){
		if(!this.ptrHandler.isInited()){
			System.out.println("COM is already destroyed, please init it before send data");
			return;
		}
		SendData.sendData(ptrHandler, buffer);
	}
	
	public synchronized void destroy(){
		if(!this.ptrHandler.isInited()){
			System.out.println("COM is already destroyed");
			return;
		}
		SendData.destroy(ptrHandler);
		this.ptrHandler.setInited(false);
	}
	
	public void formateData(Point3D p,Speed s,byte[] buf){
		if(buf==null || buf.length<32){
			System.out.println("please init buf first");
		}
		SendData.formatData(p.getX(), p.getY(), p.getZ(), s.getX(), s.getY(), s.getZ(), buf);
	}
	
	private static class PtrData {
		public long pHCom = 0;
		public long pWrOverland = 0;
		
		private volatile boolean isInit = false;
		public void setInited(boolean flag){
			this.isInit = flag;
		}
		public boolean isInited(){
			return this.isInit;
		}
		public String toString(){
			return "PtrData object:"+this.pHCom+","+this.pWrOverland+","+this.isInit;
		}
	}
	
	static{
		//System.loadLibrary("JNI_COM_MODEL");
		System.loadLibrary("SendData");
	}
	
	private static native boolean initCOM(PtrData dataHandler);

	private static native boolean sendData(PtrData dataHandler,byte[] buffer);
	
	private static native boolean destroy(PtrData dataHandler);
	
	private static native void formatData(int x,int y,int z,int sx,int sy,int sz,byte[] buffer);
}
