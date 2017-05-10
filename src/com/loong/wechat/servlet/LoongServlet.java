package com.loong.wechat.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.loong.wechat.services.LoongService;
import com.loong.wechat.utils.SignUtil;
import com.loong.wechat.utils.WeixinUtil;

/**
 * Servlet implementation class LoongServlet
 */
@WebServlet("/LoongServlet")
public class LoongServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(LoongServlet.class);
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoongServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response) 确认请求来自于微信服务器
	 */

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("LoongServlet testing is begin!");
		WeixinUtil.doGet(request, response);
	}

	/**
	 * 处理微信服务器发来的消息
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 消息的接受、处理、响应
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		// 调用核心业务类型接受消息、处理消息
		String respMessage = LoongService.processRequest(request);

		// 响应消息
		PrintWriter out = response.getWriter();
		out.print(respMessage);
		out.close();

	}

	public void init() throws ServletException {
		// Put your code here
	}

}
