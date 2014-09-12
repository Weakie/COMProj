package com.weakie.share.bean;

import org.apache.commons.lang3.StringUtils;

import com.weakie.desktop.util.LogUtil;

public class Speed {
	private int x;
	private int y;
	private int z;
	
	
	public Speed(int x, int y, int z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
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

	public static Speed parseSpeed(String value) throws Exception{
		if(StringUtils.isEmpty(value)){
			LogUtil.info("value in parseSpeed is null");
			throw new Exception("value in parseSpeed is null");
		}
		if(StringUtils.endsWith(value, ")")){
			value = StringUtils.substring(value, 0, value.length()-1);
		}
		if(StringUtils.startsWith(value, "(")){
			value = StringUtils.substring(value, 1, value.length());
		}
		String[] values = StringUtils.split(value, ",");
		if(values.length != 3){
			LogUtil.info("value length is not 3");
			throw new Exception("value length is not 3");
		}
		return new Speed(Integer.parseInt(values[0]),Integer.parseInt(values[1]),Integer.parseInt(values[2]));
	}

}
