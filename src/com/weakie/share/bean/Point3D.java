package com.weakie.share.bean;

import org.apache.commons.lang3.StringUtils;

import com.weakie.share.util.LogUtil;

public class Point3D {

	private int x;
	private int y;
	private int z;

	public Point3D(){
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	
	public Point3D(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Point3D(Point3D p){
		this.x = p.x;
		this.y = p.y;
		this.z = p.z;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}
	
	@Override
	public String toString() {
		return "Point3D [x=" + x + ", y=" + y + ", z=" + z + "]";
	}
	
	public static Point3D addPoint(Point3D src,Point3D p2){
		Point3D p = new Point3D();
		p.setX(src.x+p2.x);
		p.setY(src.y+p2.y);
		p.setZ(src.z+p2.z);
		return p;
	}
	
	public static Point3D parsePoint3D(String value) throws Exception{
		if(StringUtils.isEmpty(value)){
			LogUtil.info("value in parsePoint is null");
			throw new NullPointerException("value in parsePoint is null");
		}
		if(StringUtils.endsWith(value, ")")){
			value = StringUtils.substring(value, 0, value.length()-1);
		}
		if(StringUtils.startsWith(value, "(")){
			value = StringUtils.substring(value, 1, value.length());
		}
		String[] values = StringUtils.split(value, ",");
		if(values.length != 3){
			LogUtil.info("Point value length is not 3");
			throw new Exception("point value length is not 3");
		}
		return new Point3D(Integer.parseInt(values[0].trim()),Integer.parseInt(values[1].trim()),Integer.parseInt(values[2].trim()));
	}

	
	
}
