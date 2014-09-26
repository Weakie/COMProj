package com.weakie.share.control.gen.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.weakie.share.bean.Point3D;
import com.weakie.share.control.gen.ActionBean;

public class SnakeMoveActionGeneratorTest {

	@Test
	public void testIterator() {
		int amplitude = 200;
		int phase = 800;
		int period = 2000;//ms
		Map<String, Object> extener = new HashMap<String, Object>();
		extener.put(SnakeMoveActionGenerator.PARAM_AMPLITUDE, amplitude);
		extener.put(SnakeMoveActionGenerator.PARAM_PERIOD, period);
		extener.put(SnakeMoveActionGenerator.PARAM_PHASE, phase);
		
		SnakeMoveActionGenerator gen = new SnakeMoveActionGenerator();
		gen.initBaseParam(new Point3D(0,0,100), new Point3D(0,8000,120), null, 0);
		gen.setParameter(extener);
		
		System.out.println(gen.getSize());
		for(ActionBean ac:gen){
			System.out.println(ac.getPoint()+"   "+ac.getSpeed()+"   "+ac.getTime());
		}
	}

}
