package com.weakie.desktop.bean;

import com.weakie.desktop.bean.propertie.DefaultViewProperties;
import com.weakie.desktop.bean.propertie.DirectMoveViewProperties;
import com.weakie.desktop.bean.propertie.ViewProperties;

public class TablePropertieFactory {
	
	public static ViewProperties createNewPropertiesBean(String type){
		if(type.equals("DirectMove")){
			return new DirectMoveViewProperties();
		}else{
			return new DefaultViewProperties();
		}
	}
	
	public static ViewProperties createNewPropertiesBean(){
		return new DefaultViewProperties();
	}
}
