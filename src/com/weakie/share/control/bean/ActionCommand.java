package com.weakie.share.control.bean;

import java.util.HashMap;
import java.util.Map;

import com.weakie.share.bean.Point3D;
import com.weakie.share.bean.Speed;

public class ActionCommand {

	private int id;
	
	private Point3D beginPoint;
	private Point3D endPoint;
	private Speed speed;
	private long time;
	private String type;
	private String comment;
	private Map<String,Object> param = new HashMap<String,Object>();
	
	public ActionCommand(int id,String beginPoint,String endPoint,String speed,String time,String type,String comment) throws Exception {
		this.id = id;
		this.beginPoint = Point3D.parsePoint3D(beginPoint);
		this.endPoint = Point3D.parsePoint3D(endPoint);
		this.speed = Speed.parseSpeed(speed);
		this.time = Long.parseLong(time);
		this.type = type;
		this.comment = comment;
	}

	public int getId() {
		return id;
	}

	public Point3D getBeginPoint() {
		return beginPoint;
	}

	public Point3D getEndPoint() {
		return endPoint;
	}

	public Speed getSpeed(){
		return speed;
	}
	
	public long getTime() {
		return time;
	}

	public String getType() {
		return type;
	}

	public String getComment() {
		return comment;
	}
	
	public void addParameter(String key, Object value){
		this.param.put(key, value);
	}

	/**
	 * Inherited method
	 * @return
	 */
	public Map<String,Object> getParameter(){
		return param;
	}

	@Override
	public String toString() {
		return "ActionCommand [id=" + id + ", beginPoint=" + beginPoint
				+ ", endPoint=" + endPoint + ", speed=" + speed + ", time="
				+ time + ", type=" + type + ", comment=" + comment + "]";
	}

	
}
