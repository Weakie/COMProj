package com.weakie.server.bean;

import com.weakie.share.bean.Point3D;
import com.weakie.share.bean.Speed;

public class JsonSaveData {
	private int x;
	private int y;
	private int z;
	
	private int sx;
	private int sy;
	private int sz;
	
	private float slpTime;
	
	private String des;
	
	private char endFlag;
	private char startFlag;
	
	public JsonSaveData() {
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
	
	public void setSlpTime(float temp){
		this.slpTime = temp;
	}
	
	public float getSlpTime(){
		return slpTime;
	}
	
	public void setDes(String des){
		this.des = des;
	}
	
	public String getDes(){
		return des;
	}
}
