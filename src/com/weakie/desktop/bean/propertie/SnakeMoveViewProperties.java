package com.weakie.desktop.bean.propertie;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.weakie.share.control.gen.impl.SnakeMoveActionGenerator;
import com.weakie.share.util.LogUtil;

public class SnakeMoveViewProperties extends AbstractViewProperties {

	// this Parameter is only used for show properties in view
	private static final String PARAM_AMPLITUDE = "Amplitude";
	private static final String PARAM_PHASE = "Phase";
	private static final String PARAM_PERIOD = "Period(ms)";

	private static final String[] keys = new String[] { 
			PARAM_BEGINPOINT,
			PARAM_ENDPOINT, 
			PARAM_AMPLITUDE, 
			PARAM_PHASE, 
			PARAM_PERIOD,
			PARAM_TYPE, 
			PARAM_COMMENT };

	public SnakeMoveViewProperties() {
		param.put(PARAM_SPEED, "not need");
		param.put(PARAM_TIME, "not need");
		param.put(PARAM_AMPLITUDE, "200");
		param.put(PARAM_PHASE, "800");
		param.put(PARAM_PERIOD, "2000");
	}

	@Override
	public String[] getPropertiesName() {
		return keys;
	}

	@Override
	public String getType() {
		return SnakeMoveActionGenerator.TYPE;
	}

	@Override
	protected Map<String, Object> getExtenerParam() {
		try {
			int amplitude = Integer.parseInt(param.get(PARAM_AMPLITUDE));
			int phase = Integer.parseInt(param.get(PARAM_PHASE));
			int period = Integer.parseInt(param.get(PARAM_PERIOD));
			Map<String, Object> extener = new HashMap<String, Object>();
			extener.put(SnakeMoveActionGenerator.PARAM_AMPLITUDE, amplitude);
			extener.put(SnakeMoveActionGenerator.PARAM_PERIOD, period);
			extener.put(SnakeMoveActionGenerator.PARAM_PHASE, phase);
			return extener;
		} catch (Exception e) {
			LogUtil.error(e);
			return Collections.emptyMap();
		}
	}
}
