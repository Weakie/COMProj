package com.weakie.share.control.gen;

import java.util.Map;

import com.weakie.share.bean.Point3D;
import com.weakie.share.bean.Speed;


public abstract class AbstractActionGenerator implements Iterable<ActionBean> {

	public static final String PARAM_BEGIN = "beginPoint";
	public static final String PARAM_END = "endPoint";
	public static final String PARAM_TIME = "totalTime";
	public static final String PARAM_SPEED = "speed";
	
	protected Point3D beginPoint;
	protected Point3D endPoint;
	protected long totalTime;
	protected Speed speed;
	
	public AbstractActionGenerator(){}
	
	public void initBaseParam(Point3D beginPoint,Point3D endPoint,Speed speed,long time){
		this.beginPoint = beginPoint;
		this.endPoint = endPoint;
		this.speed = speed;
		this.totalTime = time;
	}
	
	public Point3D getBeginPoint() {
		return beginPoint;
	}

	public Point3D getEndPoint() {
		return endPoint;
	}

	public Speed getSpeed(){
		return this.speed;
	}
	
	public long getTotalTime() {
		return totalTime;
	}

	public abstract int getSize();
	/**
	 * 设置参数
	 * @param value
	 */
	public abstract void setParameter(Map<String,Object> value);
	
	/**
	 * 子类返回具体的类型
	 * @return
	 */
	public abstract String getType();
	/**
	 * 子类返回具体的描述
	 * @return
	 */
	public abstract String getDescript();
	
}
