package com.weakie.share.control.gen;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.weakie.share.control.bean.ActionCommand;

public class ActionGeneratorProducer {

	private static Map<String,String> generator = new HashMap<String,String>();
	//load the generator class
	static {
		String algorotmClassName = null;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("algorithm.properties"));
			while ((algorotmClassName = reader.readLine()) != null) {
				if(algorotmClassName.charAt(0) == '#'){
					continue;
				}
				try {
					Class.forName(algorotmClassName);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
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
			e.printStackTrace();
		} 
		return gen;
	}

}
