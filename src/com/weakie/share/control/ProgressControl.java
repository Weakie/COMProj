package com.weakie.share.control;

public interface ProgressControl {

	public void init(int size);
	
	public void begin(int id);
	public void update(int id,String value);
	public void end(int id);
	
	public void close();
}
