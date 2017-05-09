package com.loong.wechat.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.loong.wechat.services.LoongService;
import com.loong.wechat.utils.SignUtil;

/**
 * Servlet implementation class LoongServlet
 */
@WebServlet("/LoongServlet")
public class LoongServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * 确认请求来自于微信服务器
	 */

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			System.out.println("接口测试开始！！！");
			//微信加密签名
			String signature = request.getParameter("signature");
			System.out.println("signature:" + signature);
			//时间戳
			String timestamp = request.getParameter("timestamp");
			System.out.println("timestamp:" + timestamp);
			//随机数
			String nonce = request.getParameter("nonce");
			System.out.println("nonce:" + nonce);
			//随机字符串
			String echostr = request.getParameter("echostr");
			System.out.println("echostr:" + echostr);
			
			PrintWriter out = response.getWriter();
			//通过校验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
			if(SignUtil.checkSignature(signature,timestamp,nonce)){
				out.print(echostr);
			}
			out.close();
			out = null;
//			response.encodeRedirectURL("success.jsp");
			
		
	}
	/**
	 * 处理微信服务器发来的消息
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//消息的接受、处理、响应
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		//调用核心业务类型接受消息、处理消息
		String respMessage = LoongService.processRequest(request);
		
		//响应消息
		PrintWriter out = response.getWriter();
		out.print(respMessage);
		out.close();
		
		
	}

	public void init() throws ServletException {
		// Put your code here
	}

}
