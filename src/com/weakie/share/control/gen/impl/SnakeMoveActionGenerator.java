package com.weakie.share.control.gen.impl;

import java.util.Iterator;
import java.util.Map;

import com.weakie.share.bean.Point3D;
import com.weakie.share.bean.Speed;
import com.weakie.share.control.gen.AbstractActionGenerator;
import com.weakie.share.control.gen.ActionBean;
import com.weakie.share.control.gen.ActionGeneratorProducer;
/**
 * Need Parameter: beginPoint,endPoint,amplitude,phase,period
 * @author weakie Email:weakielin@gmail.com
 *
 */
public class SnakeMoveActionGenerator extends AbstractActionGenerator {

	public static final String TYPE = "SnakeMove";
	public static final String DESCRIPT = "Generate moved points as a snake move path.";
	static{
		ActionGeneratorProducer.register(TYPE, SnakeMoveActionGenerator.class.getName());
	}
	
	public static final String PARAM_AMPLITUDE = "amplitude";
	public static final String PARAM_PHASE = "phase";
	public static final String PARAM_PERIOD = "period";
	
	private static final int[] AMP = new int[]{0,1,0,-1};
	
	//Obtained parameter
	private int amplitude;
	private int phase;
	private int period;
	
	//Calculated parameter
	private int lengthOfTotal;	//length of XY between begin and end
	private int lengthOfZ;		//length of Z in total move
	private int stepTime;		//time in each step
	private double sin;
	private double cos;
	
	public SnakeMoveActionGenerator() {}

	private void initialize(){
		int dx = Math.abs(beginPoint.getX()-endPoint.getX());
		int dy = Math.abs(beginPoint.getY()-endPoint.getY());
		this.lengthOfTotal = (int) Math.sqrt(dx*dx+dy*dy);
		this.lengthOfZ = endPoint.getZ() - beginPoint.getZ();
		this.stepTime = period/4;
		this.sin = ((double)(endPoint.getY()-beginPoint.getY()))/lengthOfTotal;
		this.cos = ((double)(endPoint.getX()-beginPoint.getX()))/lengthOfTotal;
	}
	
	
	@Override
	public Iterator<ActionBean> iterator() {
		return new MyIterator();
	}
	
	private class MyIterator implements Iterator<ActionBean>{

		private int index = 0;
		private final int maxIndex;
		private final Point3D prevPoint;
		
		public MyIterator(){
			this.maxIndex = SnakeMoveActionGenerator.this.getSize();
			this.prevPoint = new Point3D(beginPoint);
		}
		
		@Override
		public boolean hasNext() {
			return this.index < maxIndex;
		}

		@Override
		public ActionBean next() {
			this.index++;
			
			//calculate position
			int x = this.index*phase/4;
			int y = AMP[this.index%4]*amplitude;
			int z = lengthOfZ*this.index/this.maxIndex;
			
			//Translate x,y
			int xx = (int)(x*cos-y*sin+beginPoint.getX());
			int yy = (int)(x*sin+y*cos+beginPoint.getY());
			int zz = z+beginPoint.getZ();
			
			//Generate targetPoint and targetSpeed, speed(x/ms) use prevPoint and stepTime(ms)
			Point3D targetPoint = new Point3D(xx,yy,zz);
			int sx = (targetPoint.getX()-this.prevPoint.getX())*1000/stepTime;
			int sy = (targetPoint.getY()-this.prevPoint.getY())*1000/stepTime;
			int sz = z*1000/stepTime;
			Speed targetSpeed = new Speed(
							Math.abs(sx),
							Math.abs(sy),
							Math.abs(sz));
			
			//Update prevPoint
			this.prevPoint.setData(xx, yy, zz);
			//Return data
			return new ActionBean(index,targetPoint,targetSpeed,stepTime);
		}
		
	}

	@Override
	public int getSize() {
		return this.lengthOfTotal*4/this.phase;
	}

	@Override
	public void setParameter(Map<String, Object> value) {
		this.amplitude = (int) value.get(PARAM_AMPLITUDE);
		this.phase = (int) value.get(PARAM_PHASE);
		this.period = (int) value.get(PARAM_PERIOD);
		this.initialize();
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public String getDescript() {
		return DESCRIPT;
	}

	
}