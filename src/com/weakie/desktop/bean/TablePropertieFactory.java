package com.weakie.desktop.bean;

import com.weakie.desktop.bean.propertie.DefaultViewProperties;
import com.weakie.desktop.bean.propertie.DirectMoveViewProperties;
import com.weakie.desktop.bean.propertie.SnakeMoveViewProperties;
import com.weakie.desktop.bean.propertie.ViewProperties;
import com.weakie.share.control.gen.impl.DefaultActionGenerator;
import com.weakie.share.control.gen.impl.DirectMoveActionGenerator;
import com.weakie.share.control.gen.impl.SnakeMoveActionGenerator;

public class TablePropertieFactory {
	
	public static ViewProperties createNewPropertiesBean(String type){
		if(type.equals(DirectMoveActionGenerator.TYPE)){
			return new DirectMoveViewProperties();
		}else if(type.equals(SnakeMoveActionGenerator.TYPE)){
			return new SnakeMoveViewProperties();
		}
		return new DefaultViewProperties();
	}
	
	public static ViewProperties createNewPropertiesBean(){
		return new DefaultViewProperties();
	}
}
