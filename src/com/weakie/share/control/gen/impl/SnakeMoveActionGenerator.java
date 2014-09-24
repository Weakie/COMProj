package com.weakie.share.control.gen.impl;

import java.util.Iterator;
import java.util.Map;

import com.weakie.share.bean.Point3D;
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

	private class MyIterator implements Iterator<ActionBean>{

		private int index = 0;
		private final int maxIndex;
		private Point3D endPoint;
		
		public MyIterator(){
			int dx = Math.abs(beginPoint.getX()-endPoint.getX());
			int dy = Math.abs(beginPoint.getY()-endPoint.getY());
			int length = (int) Math.sqrt(dx*dx+dy*dy);
			this.maxIndex = length/phase;
		}
		
		@Override
		public boolean hasNext() {
			return this.index < maxIndex;
		}

		@Override
		public ActionBean next() {
			// TODO Auto-generated method stub
			return new ActionBean(index,endPoint,speed,totalTime);
		}
		
	}
}
