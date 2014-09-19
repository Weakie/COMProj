package com.weakie.share.control.gen.impl;

import java.util.Iterator;
import java.util.Map;

import com.weakie.share.control.gen.AbstractActionGenerator;
import com.weakie.share.control.gen.ActionBean;
import com.weakie.share.control.gen.ActionGeneratorProducer;

/**
 * Attention: current point must be same with the begin point!
 * @author weakie E-mail:weakielin@gmail.com
 * 2014��9��9������9:31:24
 */
public class DirectMoveActionGenerator extends AbstractActionGenerator {

	public static final String TYPE = "DirectMove";
	public static final String DESCRIPT = "move point directly between two points";
	static{
		ActionGeneratorProducer.register(TYPE, DirectMoveActionGenerator.class.getName());
	}
		
	public DirectMoveActionGenerator() {}

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
			return new ActionBean(0,endPoint,speed,totalTime);
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
