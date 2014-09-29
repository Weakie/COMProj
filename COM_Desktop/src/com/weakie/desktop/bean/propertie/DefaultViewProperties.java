package com.weakie.desktop.bean.propertie;

import java.util.Collections;
import java.util.Map;

import com.weakie.share.control.gen.impl.DefaultActionGenerator;

public class DefaultViewProperties extends AbstractViewProperties {

	private static final String[] keys = new String[]{
			PARAM_BEGINPOINT,
			PARAM_ENDPOINT,
			PARAM_TIME,
			PARAM_TYPE,
			PARAM_COMMENT};
	
	public DefaultViewProperties() {
		param.put(PARAM_SPEED, "not need");
	}

	@Override
	public String[] getPropertiesName() {
		return keys;
	}

	@Override
	public String getType() {
		return DefaultActionGenerator.TYPE;
	}

	@Override
	protected Map<String, Object> getExtenerParam() {
		return Collections.emptyMap();
	}

}
