package com.weakie.share.control.gen.impl;

import java.util.Iterator;
import java.util.Map;

import com.weakie.share.bean.Speed;
import com.weakie.share.control.gen.AbstractActionGenerator;
import com.weakie.share.control.gen.ActionBean;
import com.weakie.share.control.gen.ActionGeneratorProducer;

/**
 * Attention: current point must be same with the begin point!
 * Need Parameter: beginPoint,endPoint,totalTime
 * @author weakie E-mail:weakielin@gmail.com
 */
public class DefaultActionGenerator extends AbstractActionGenerator {

	public static final String TYPE = "StraightMove";
	public static final String DESCRIPT = "move point straight between two points";
	static{
		ActionGeneratorProducer.register(TYPE, DefaultActionGenerator.class.getName());
	}
	
	public DefaultActionGenerator() {}

	@Override
	public Iterator<ActionBean> iterator() {
		return new MyIterator();
	}
	
	private class MyIterator implements Iterator<ActionBean>{

		private boolean hasNext = true;
		@Override
		public boolean hasNext() {
			return this.hasNext;
		}

		@Override
		public ActionBean next() {
			this.hasNext = false;
			int sx = Math.abs((int) ((endPoint.getX()-beginPoint.getX())*1000/totalTime));
			int sy = Math.abs((int) ((endPoint.getY()-beginPoint.getY())*1000/totalTime));
			int sz = Math.abs((int) ((endPoint.getZ()-beginPoint.getZ())*1000/totalTime));
			return new ActionBean(0,endPoint,new Speed(sx,sy,sz),totalTime);
		}
		
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}

	@Override
	public void setParameter(Map<String, Object> value) {}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public String getDescript() {
		return DESCRIPT;
	}

	@Override
	public int getSize() {
		return 1;
	}

}
