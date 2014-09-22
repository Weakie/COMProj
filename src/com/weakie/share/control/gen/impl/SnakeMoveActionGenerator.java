package com.weakie.share.control.gen.impl;

import java.util.Iterator;
import java.util.Map;

import com.weakie.share.control.gen.AbstractActionGenerator;
import com.weakie.share.control.gen.ActionBean;

public class SnakeMoveActionGenerator extends AbstractActionGenerator {

	private static final String TYPE = "SnakeMove";
	private static final String DESCRIPT = "Generate moved points as a snake move path.";
	
	private int amplitude;
	private int phase;
	private int period;
	
	public SnakeMoveActionGenerator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Iterator<ActionBean> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setParameter(Map<String, Object> value) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public String getDescript() {
		// TODO Auto-generated method stub
		return DESCRIPT;
	}

}
