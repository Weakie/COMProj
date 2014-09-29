package com.weakie.desktop.bean.propertie;

import java.util.Map;

import com.weakie.share.control.bean.ActionCommand;

public interface ViewProperties {

	public String getProperties(String key);
	
	public void updateProperties(Map<String,String> value);
	
	/**
	 * this method defines the properties shows in the PropertiesView page.
	 * @return
	 */
	public String[] getPropertiesName();
	
	public String[] getTableData();
	
	public String getType();
	
	public ActionCommand createActionCommand(int id);
}
