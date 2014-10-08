package com.weakie.server.bean;

import com.weakie.share.bean.Point3D;
import com.weakie.share.bean.Speed;

public class JsonData {

	private int x;
	private int y;
	private int z;
	
	private int sx;
	private int sy;
	private int sz;
	
	private int controlFlag;
	
	private char endFlag;
	private char startFlag;
	
	public JsonData() {
		// TODO Auto-generated constructor stub
	}

	public Point3D buildPoint3DObject(){
		return new Point3D(x,y,z);
	}
	
	public Speed buildSpeedObject(){
		return new Speed(sx,sy,sz);
	}
	
	public char getStartFlag(){
		return this.startFlag;
	}
	
	public char getEndFlag(){
		return this.endFlag;
	}
	
	public int getControlFlag(){
		return this.controlFlag;
	}
	
	public void setFlag(int controlFlag)
	{
		this.controlFlag = controlFlag;
	}
}
