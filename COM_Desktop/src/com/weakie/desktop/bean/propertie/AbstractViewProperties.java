package com.weakie.desktop.bean.propertie;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.weakie.share.bean.Point3D;
import com.weakie.share.bean.Speed;
import com.weakie.share.control.bean.ActionCommand;

public abstract class AbstractViewProperties implements ViewProperties{

	protected Map<String,String> param = new HashMap<String, String>();
	
	//this Parameter is only used for show properties in view
	protected static final String PARAM_BEGINPOINT = "BeginPoint";
	protected static final String PARAM_ENDPOINT = "EndPoint";
	protected static final String PARAM_SPEED = "Speed";
	protected static final String PARAM_TIME = "Time";
	protected static final String PARAM_TYPE = "Type";
	protected static final String PARAM_COMMENT = "Comment";
	
	//Define the order of properties shows in view
	private static final String[] keys = new String[]{
			PARAM_BEGINPOINT,
			PARAM_ENDPOINT,
			PARAM_SPEED,
			PARAM_TIME,
			PARAM_TYPE,
			PARAM_COMMENT};
	
	public AbstractViewProperties() {
		//TODO add default value
		param.put(keys[0], "(0,0,0)");
		param.put(keys[1], "(1,1,1)");
		param.put(keys[2], "(1,1,1)");
		param.put(keys[3], "2000");
		param.put(keys[4], this.getType());
		param.put(keys[5], "");
	}

	@Override
	public String getProperties(String key) {
		return this.param.get(key);
	}

	@Override
	public void updateProperties(Map<String, String> value) {
		this.param.putAll(value);
	}

	@Override
	public String[] getPropertiesName() {
		return keys;
	}

	@Override
	public String[] getTableData() {
		String[] table = new String[6];
		table[0] = param.get(keys[4]);
		table[1] = param.get(keys[1]);
		table[2] = param.get(keys[2]);
		table[3] = param.get(keys[3]);
		table[4] = "Ready";
		table[5] = param.get(keys[5]);
		return table;
	}

	@Override
	public final ActionCommand createActionCommand(int id) {
		Point3D beginPoint = null;;
		Point3D endPoint = null;
		Speed speed = null;
		long time = 0;
		
		try {
			beginPoint = Point3D.parsePoint3D(param.get(keys[0]));
		} catch (Exception e1) {}
		
		try {
			endPoint = Point3D.parsePoint3D(param.get(keys[1]));
		} catch (Exception e1) {}
		
		try {
			speed = Speed.parseSpeed(param.get(keys[2]));
		} catch (Exception e1) {}
		
		try{
			time = Long.parseLong(param.get(keys[3]));
		} catch (Exception e1) {}
		
		ActionCommand ac = new ActionCommand(
				id,
				beginPoint,
				endPoint, 
				speed, 
				time,
				param.get(keys[4]), 
				param.get(keys[5]));
		for (Entry<String, Object> e : this.getExtenerParam().entrySet()) {
			ac.addParameter(e.getKey(), e.getValue());
		}
		return ac;
	}

	protected abstract Map<String,Object> getExtenerParam();
}
