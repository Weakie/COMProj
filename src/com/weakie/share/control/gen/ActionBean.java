package com.weakie.share.control.gen;

import com.weakie.share.bean.Point3D;
import com.weakie.share.bean.Speed;

public class ActionBean {

	private int id;
	private long time;
	private Point3D point;
	private Speed speed;
	
	public ActionBean(int id,Point3D point,Speed speed,long time) {
		this.id = id;
		this.point = point;
		this.speed = speed;
		this.time = time;
	}

	public int getId() {
		return id;
	}

	public Point3D getPoint() {
		return point;
	}

	public Speed getSpeed() {
		return speed;
	}

	public long getTime() {
		return time;
	}
	
	@Override
	public String toString() {
		return "ActionBean [id=" + id + ", point=" + point + ", speed=" + speed
				+ ", time=" + time + "]";
	}
	
}
