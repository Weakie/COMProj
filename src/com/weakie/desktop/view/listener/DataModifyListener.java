package com.weakie.desktop.view.listener;

import java.util.Map;

import com.weakie.desktop.bean.propertie.ViewProperties;

public interface DataModifyListener {

	public void update(Map<String,String> properties);
	
	public ViewProperties getProperties();
	
	public boolean isAlive();
	
	public void disable(int index);
	
	public void enable(int index);
}
