package com.weakie.desktop.bean.propertie;

import java.util.Collections;
import java.util.Map;

import com.weakie.share.control.gen.impl.DirectMoveActionGenerator;

public class DirectMoveViewProperties extends AbstractViewProperties {

	private static final String[] keys = new String[]{
			PARAM_SPEED,
			PARAM_ENDPOINT,
			PARAM_TIME,
			PARAM_TYPE,
			PARAM_COMMENT};
	
	public DirectMoveViewProperties() {
		param.put(PARAM_BEGINPOINT, "not need");
	}

	@Override
	public String[] getPropertiesName() {
		return keys;
	}

	@Override
	public String getType() {
		return DirectMoveActionGenerator.TYPE;
	}

	@Override
	protected Map<String, String> getExtenerParam() {
		return Collections.emptyMap();
	}

}
