package com.weakie.share.control.gen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.weakie.share.control.bean.ActionCommand;
import com.weakie.share.util.LogUtil;

public class ActionGeneratorProducer {

	private static Map<String,String> generator = new HashMap<String,String>();
	//load the generator class
	static {
		String algorotmClassName = null;
		BufferedReader reader = null;
		try {
			InputStream in = ActionGeneratorProducer.class.getResourceAsStream("/algorithm.properties");
			reader = new BufferedReader(new InputStreamReader(in));
			while ((algorotmClassName = reader.readLine()) != null) {
				if(algorotmClassName.charAt(0) == '#'){
					continue;
				}
				try {
					Class.forName(algorotmClassName);
				} catch (ClassNotFoundException e) {
					LogUtil.error(e);
				}
			}
		} catch (IOException e) {
			LogUtil.error(e);
		} finally{
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					LogUtil.error(e);
				}
			}
		}
	}
	
	public static void register(String type, String clazzName){
		generator.put(type, clazzName);
	}
	
	public static Set<String> getGeneratorTypes(){
		return generator.keySet();
	}

	public static AbstractActionGenerator build(ActionCommand command){
		String clazzName = generator.get(command.getType());
		AbstractActionGenerator gen = null;
		try {
			gen = (AbstractActionGenerator) Class.forName(clazzName).newInstance();
			gen.initBaseParam(
					command.getBeginPoint(), 
					command.getEndPoint(),
					command.getSpeed(),
					command.getTime());
			gen.setParameter(command.getParameter());
		} catch (Exception e) {
			LogUtil.error(e);
		} 
		return gen;
	}

}
