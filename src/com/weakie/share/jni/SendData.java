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
		SendData.initCOM(ptrHandler);
		//TODO only for test here
		System.out.println(this.ptrHandler.pHCom);
		System.out.println(this.ptrHandler.pWrOverland);
	}
	
	public synchronized void sendData(byte[] buffer){
		SendData.sendData(ptrHandler, buffer);
	}
	
	public synchronized void formateData(Point3D p,Speed s,byte[] buf){
		SendData.formatData(p.getX(), p.getY(), p.getZ(), s.getX(), s.getY(), s.getZ(), buf);
	}
	
	private static class PtrData {
		public long pHCom = 0;
		public long pWrOverland = 0;
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
