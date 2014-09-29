package com.weakie.desktop.view.util;

import org.apache.commons.lang3.StringUtils;

public class DataFormat {

	public static String formatTableData(String text,int col){
		if ((col <= 1)) {
			String[] value = StringUtils.split(StringUtils.trim(text), ',');
			for(int i = 0; i < value.length;i++){
				value[i] = value[i].trim();
			}
			String result = "";
			if (value.length == 0) {
				result = "0 , 0 , 0";
			} else if (value.length == 1) {
				result = value[0] + " , 0 , 0";
			} else if (value.length == 2) {
				result = value[0] + " , " + value[1] + " , 0";
			} else {
				result = value[0] + " , " + value[1] + " , " + value[2];
			}
			return "( " + result + " )";
		} else if (col == 2) {
			return text + "ms";
		} else {
			return text;
		}
	}

	public static boolean verifyTableData(String text,int col){
		boolean b = true;
    	if(col<=1){
    		b = (",0123456789".indexOf(text)>=0); 
    	}else if(col == 2 ){
    		b = ("0123456789".indexOf(text)>=0); 
    	}
    	return b;
	}
	
	public static String formatTextData(String text,int col){
		if(StringUtils.isEmpty(text)){
			return "";
		}
		if(col<=1){
    		return StringUtils.substring(text, 1, text.length()-1);
    	}else if(col == 2 ){
    		return StringUtils.substring(text, 0, text.length()-2);
    	}
		return text;
	}
}
