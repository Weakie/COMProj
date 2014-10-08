package com.weakie.share.jni;

import com.weakie.share.bean.Point3D;
import com.weakie.share.bean.Speed;
import com.weakie.share.util.LogUtil;
import com.weakie.share.bean.ControlParameter;

public class SendData {
	public static final int ABSOLUTE_POSITION = 1;
	public static final int RELATIVE_POSITION = 3;
	public static final int START_WELD = 5;
	public static final int WELD_PARA = 7;

	public static final int MAX_BUFFER_SIZE = 128;
	
	private PtrData ptrHandler;
	private static SendData instance = new SendData();
	
	private SendData() {
		this.ptrHandler = new PtrData();
	}

	public static SendData getInstance(){
		return instance;
	}
	
	public synchronized boolean isInited(){
		return this.ptrHandler.isInited();
	}
	
	public synchronized boolean initCOM(String port){
		if(this.ptrHandler.isInited()){
			LogUtil.info("COM is already initialized");
			return true;
		}
		boolean result = SendData.initCOM(ptrHandler,port);
		this.ptrHandler.setInited(result);
		return result;
	}
	
	public synchronized void sendData(byte[] buffer,int bufSize){
		if(!this.ptrHandler.isInited()){
			LogUtil.info("COM is already destroyed, please init it before send data");
			return;
		}
		SendData.sendData(ptrHandler, buffer, bufSize);
	}
	
	public synchronized void destroy(){
		if(!this.ptrHandler.isInited()){
			LogUtil.info("COM is already destroyed");
			return;
		}
		SendData.destroy(ptrHandler);
		this.ptrHandler.setInited(false);
		
	}
	
	public int formatPointData(Point3D p,Speed s,byte[] buf){
		if(buf==null || buf.length<MAX_BUFFER_SIZE){
			LogUtil.info("please init buf first!");
		}
		return SendData.formatPointData(p.getX(), p.getY(), p.getZ(), s.getX(), s.getY(), s.getZ(), ABSOLUTE_POSITION, buf);
	}
	
	public int formatPointData(Point3D p,Speed s,int flag,byte[] buf){
		if(buf==null || buf.length<MAX_BUFFER_SIZE){
			LogUtil.info("please init buf first!");
		}
		return SendData.formatPointData(p.getX(), p.getY(), p.getZ(), s.getX(), s.getY(), s.getZ(), flag, buf);
	}
	
	public int formatIniWeldParaData(ControlParameter cp, int flag, byte[] buf){
		if(buf==null || buf.length<MAX_BUFFER_SIZE){
			LogUtil.info("please init buf first!");
		}
		return SendData.formatIniWeldParaData(cp.getH0(), cp.getH1(), cp.getH2(), cp.getH3(), cp.getH4(), cp.getH5(), cp.getV0(), cp.getV1(), cp.getV2(), cp.getV3(), cp.getV4(), cp.getV5(), cp.getI1(), cp.getI2(), cp.getI3(), flag, buf);
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
	
	private static native boolean initCOM(PtrData dataHandler,String port);

	private static native boolean sendData(PtrData dataHandler,byte[] buffer,int bufSize);
	
	private static native boolean destroy(PtrData dataHandler);
	
	private static native int formatPointData(int x,int y,int z,int sx,int sy,int sz,int flag,byte[] buffer);
	
	private static native int formatIniWeldParaData(int h0, int h1, int h2, int h3, int h4, int h5, int v0, int v1, int v2, int v3, int v4, int v5, int i1, int i2, int i3, int flag, byte[] buffer);
}
