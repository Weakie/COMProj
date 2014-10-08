package com.weakie.server.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.weakie.share.bean.ControlParameter;
import com.weakie.share.jni.SendData;

/**
 * Servlet implementation class ControlParameterDispatcherServlet
 */
public class ControlParameterDispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ControlParameterDispatcherServlet() {
		super();
		// TODO Auto-generated constructor stub
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
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(
				request.getInputStream()));
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = in.readLine()) != null) {
			sb.append(line);
		}
		
		System.out.println(sb.toString());
		
		try {
			Gson gson = new Gson();
			ControlParameter data = gson.fromJson(sb.toString(), ControlParameter.class);

			int controlFlag = 7;
			
			byte[] buf = new byte[SendData.MAX_BUFFER_SIZE];
				
			
			SendData.getInstance().initCOM("com4");
			int bufSize = SendData.getInstance().formatIniWeldParaData(data, controlFlag, buf);

			SendData.getInstance().sendData(buf,bufSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.getWriter().write("success");
		return;
	}

}
