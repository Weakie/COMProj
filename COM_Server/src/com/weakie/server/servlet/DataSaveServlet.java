package com.weakie.server.servlet;
import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.weakie.server.bean.JsonSaveData;

import com.weakie.share.bean.Point3D;
import com.weakie.share.bean.Speed;


public class DataSaveServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1732785718936820654L;



	public DataSaveServlet(){
		super();
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	
	public static void fileWriterTest(String fileName, String content){
		FileWriter writer = null;
		try{
			writer = new FileWriter(fileName, true);
			writer.write(content);
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try{
				if(writer != null){
					writer.close();
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(
				request.getInputStream(),"utf-8"));
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = in.readLine()) != null) {
			sb.append(line);
		}
		System.out.println(sb.toString());
		
		try {
			Gson gson = new Gson();
			ArrayList<ArrayList<JsonSaveData>> filesList = new ArrayList<ArrayList<JsonSaveData>>();
			filesList = gson.fromJson(sb.toString(), new TypeToken<ArrayList<ArrayList<JsonSaveData>>>() {}.getType());
			String[] fileNameStr = new String[filesList.size()];
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
			for(int i = 0; i < filesList.size(); i++){
				
				fileNameStr[i] = "d://weldTestSaveData//"+ df.format(new Date()) +" "+i+ ".txt";		
				try{	
					File file = new File(fileNameStr[i]);
					if(file.createNewFile()){
						System.out.println("Create file successsed");
					}
				}catch(Exception e){  
	            	System.out.println(e);  
	        	}  	
				
				ArrayList<JsonSaveData> actionsList = new ArrayList<JsonSaveData>();
				actionsList = filesList.get(i);
				//ArrayList<JsonSaveData> actionsList = new ArrayList<JsonSaveData>();
				//actionsList = (ArrayList<JsonSaveData>) filesList.get(i);
				String tempStr = new String();
				for(int j = 0; j < actionsList.size(); j++){
					//String str = new String();
					//str = actionsList.get(j).toString(); 
					//JsonSaveData data = gson.fromJson(str, JsonSaveData.class);
					JsonSaveData actionData = new JsonSaveData();
					actionData = (JsonSaveData) actionsList.get(j);
					Point3D point = actionData.buildPoint3DObject();
					Speed speed = actionData.buildSpeedObject();
					tempStr += "X坐标: " + point.getX() 
							+ " Y坐标: " + point.getY()
							+ " Z坐标: " + point.getZ()
							+ " X速度: " + speed.getX()
							+ " Y速度: " + speed.getY()
							+ " Z速度: " + speed.getZ()
							+ " sleep时间: " + actionData.getSlpTime()
							+ " 描述: " + actionData.getDes()
							+ "\r\n";
				}
				fileWriterTest(fileNameStr[i], tempStr);
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.getWriter().write("success");
		return;
	}
	
}
