package com.weakie.server.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.weakie.server.bean.JsonData;
import com.weakie.share.bean.Point3D;
import com.weakie.share.bean.Speed;
import com.weakie.share.jni.SendData;

/**
 * Servlet implementation class DataDispatcherServlet
 */
public class DataDispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DataDispatcherServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
		StringBuilder sb = new StringBuilder();
		String line = null;
		while((line = in.readLine())!=null){
			sb.append(line);
		}
		
		Gson gson = new Gson();
		JsonData data = gson.fromJson(sb.toString(), JsonData.class);
		Speed speed = data.buildSpeedObject();
		Point3D point = data.buildPoint3DObject();
		byte[] buf = new byte[32];
		
		SendData.getInstance().initCOM();
		SendData.getInstance().formateData(point, speed, buf);
		SendData.getInstance().sendData(buf);
		return;
	}

}
