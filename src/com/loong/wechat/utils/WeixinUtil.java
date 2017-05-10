package com.loong.wechat.utils;
import java.io.BufferedReader;  
import java.io.IOException;
import java.io.InputStream;  
import java.io.InputStreamReader;  
import java.io.OutputStream;  
import java.io.PrintWriter;
import java.net.ConnectException;  
import java.net.URL;  
  

import javax.net.ssl.HttpsURLConnection;  
import javax.net.ssl.SSLContext;  
import javax.net.ssl.SSLSocketFactory;  
import javax.net.ssl.TrustManager;  
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.loong.wechat.pojo.AccessToken;

import net.sf.json.JSONObject;   

public class WeixinUtil {
	protected static PropertiesUtil pp = new PropertiesUtil();
	private static Logger logger = Logger.getLogger(WeixinUtil.class);
	
	// 获取access_token的接口地址（GET） 限200（次/天）
	public static String access_token_url = pp.read("config.properties","access_token_url");
	// 菜单创建（POST） 限100（次/天）
	public static String menu_create_url = pp.read("config.properties","menu_create_url");
	
	private static String appId = pp.read("config.properties","appId");
	private static String appSecret = pp.read("config.properties","appSecret"); 
 
    /** 
     * 发起https请求并获取结果 
     *  
     * @param requestUrl 请求地址 
     * @param requestMethod 请求方式（GET、POST） 
     * @param outputStr 提交的数据 
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值) 
     */  
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {  
        JSONObject jsonObject = null;  
        StringBuffer buffer = new StringBuffer();  
        try {  
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化  
            TrustManager[] tm = { new MyX509TrustManager() };  
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");  
            sslContext.init(null, tm, new java.security.SecureRandom());  
            // 从上述SSLContext对象中得到SSLSocketFactory对象  
            SSLSocketFactory ssf = sslContext.getSocketFactory();  
  
            URL url = new URL(requestUrl);  
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();  
            httpUrlConn.setSSLSocketFactory(ssf);  
  
            httpUrlConn.setDoOutput(true);  
            httpUrlConn.setDoInput(true);  
            httpUrlConn.setUseCaches(false);  
            // 设置请求方式（GET/POST）  
            httpUrlConn.setRequestMethod(requestMethod);  
  
            if ("GET".equalsIgnoreCase(requestMethod))  
                httpUrlConn.connect();  
  
            // 当有数据需要提交时  
            if (null != outputStr) {  
                OutputStream outputStream = httpUrlConn.getOutputStream();  
                // 注意编码格式，防止中文乱码  
                outputStream.write(outputStr.getBytes("UTF-8"));  
                outputStream.close();  
            }  
  
            // 将返回的输入流转换成字符串  
            InputStream inputStream = httpUrlConn.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
  
            String str = null;  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }  
            bufferedReader.close();  
            inputStreamReader.close();  
            // 释放资源  
            inputStream.close();  
            inputStream = null;  
            httpUrlConn.disconnect();  
            System.out.println(buffer.toString());
            jsonObject = JSONObject.fromObject(buffer.toString());  
        } catch (ConnectException ce) {  
            System.out.println("Weixin server connection timed out.");
        } catch (Exception e) {  
        	System.err.println("https request error:{}");
//            log.error("https request error:{}", e);  
        }  
        return jsonObject;  
    }  
    /** 
	 * 获取access_token 
	 *  
	 * @param appid 凭证 
	 * @param appsecret 密钥 
	 * @return 
	 */  
	public static AccessToken getAccessToken() {  
	    AccessToken accessToken = null;  
	    String requestUrl = access_token_url.replace("APPID", appId).replace("APPSECRET", appSecret);  
	    logger.info("access_token_url: " + access_token_url);
	    JSONObject jsonObject = httpRequest(requestUrl, "GET", null);  
	    // 如果请求成功  
	    if (null != jsonObject) {  
	        try {  
	            accessToken = new AccessToken();  
	            accessToken.setToken(jsonObject.getString("access_token"));  
	            accessToken.setExpiresIn(jsonObject.getInt("expires_in"));  
	        } catch (Exception e) {  
	            accessToken = null;  
	            // 获取token失败  
	            System.out.println("获取token失败 errcode:"+jsonObject.getInt("errcode")+"errmsg:"+jsonObject.getString("errmsg"));
//	            log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));  
	        }  
	    }  
	    return accessToken;  
	}
	
	/*
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response) 确认请求来自于微信服务器
	 */
	public static void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		System.out.println("微信确认请求开始！！！");
		// 微信加密签名
		String signature = request.getParameter("signature");
		System.out.println("signature:" + signature);
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		System.out.println("timestamp:" + timestamp);
		// 随机数
		String nonce = request.getParameter("nonce");
		System.out.println("nonce:" + nonce);
		// 随机字符串
		String echostr = request.getParameter("echostr");
		System.out.println("echostr:" + echostr);

		PrintWriter out = response.getWriter();
		// 通过校验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			out.print(echostr);
		}
		out.close();
	}
	
}
