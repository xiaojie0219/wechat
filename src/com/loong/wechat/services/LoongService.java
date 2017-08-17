package com.loong.wechat.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.loong.wechat.manager.MenuManage;
import com.loong.wechat.message.resp.Article;
import com.loong.wechat.message.resp.Image;
import com.loong.wechat.message.resp.ImageMessage;
import com.loong.wechat.message.resp.NewsMessage;
import com.loong.wechat.message.resp.TextMessage;
import com.loong.wechat.message.resp.ViewMessage;
import com.loong.wechat.utils.MessageUtil;

/**
 * 解耦，使控制层与业务逻辑层分离开来，主要处理请求，响应
 */
public class LoongService implements Comparable<Object>{
	// private static OperatorUtil operatorUtil;
	private static Logger logger = Logger.getLogger(LoongService.class);

	public static String processRequest(HttpServletRequest request) {
		String respMessage = "";
		// 发送方账号（open_id）
		String fromUserName;
		// 公众账号
		String toUserName;
		// 消息类型
		String msgType;
		// 返回的文本消息内容
		String respContent = "";
		// xml请求解析
		Map<String, String> requestMap;
		try {
			requestMap = MessageUtil.pareXml(request);
			fromUserName = requestMap.get("FromUserName");
			toUserName = requestMap.get("ToUserName");
			msgType = requestMap.get("MsgType");
			logger.info("消息类型msgType：" + msgType);
			// 根据事件类型回复信息
			String event = requestMap.get("Event");

			// 事件KEY值，与创建自定义菜单时指定的KEY值对应
			String eventKey = requestMap.get("EventKey");
			if (!"".equals(event) && event != null){
				switch (event) {
				case (MessageUtil.EVENT_TYPE_SUBSCRIBE): {
					respContent = "测试公众号“中国龙”，感谢您的关注!";
					break;
				}
				case (MessageUtil.EVENT_TYPE_UNSUBSCRIBE): {
					respContent = "您太残忍了，友尽。";
					break;
				}
				case (MessageUtil.EVENT_TYPE_SCANCODE_WAITMSG): {
					respContent = "二维码中包含的信息为：" + requestMap.get("ScanResult");
					break;
				}
				case (MessageUtil.EVENT_TYPE_PIC_PHOTO_OR_ALBUM): {
					respContent = "弹出拍照或者相册发图。";
					break;
				}
				case (MessageUtil.EVENT_TYPE_VIEW): {
					switch (eventKey) {
					case (MenuManage.btn21Url):{
						ViewMessage viewMessage = new ViewMessage();
						viewMessage.setFromUserName(toUserName);
						viewMessage.setToUserName(fromUserName);
						viewMessage.setMsgType(msgType);
						viewMessage.setEvent(event);
						viewMessage.setEventKey(eventKey);
						respMessage = MessageUtil.viewMessageToXml(viewMessage);
						logger.info("view跳转url，返回信息respMessage:" +respMessage);
						break;
					}
					}
				}
				case (MessageUtil.EVENT_TYPE_CLICK):{
					switch (eventKey) {
					case (MenuManage.btn11Key):{
						respContent = "扫码功能目前不可用，完善中。。。";
						break;
					}
					case (MenuManage.btn12Key):{
						respContent = "拍照功能目前不可用，完善中。。。";
						break;
					}
					case (MenuManage.btn22Key):{
//						respContent = "测试技术文章正在搜集中，请耐心等待，不要激动。";
						//返回图文消息
						NewsMessage newsMessage = new NewsMessage();
						newsMessage.setFromUserName(toUserName);
						newsMessage.setToUserName(fromUserName);
						newsMessage.setCreateTime(System.currentTimeMillis());
						newsMessage.setMsgType("news");
						newsMessage.setArticleCount(2);
						List<Article> articleList = new ArrayList<Article>(); 
						Article article1 = new Article();
						Article article2 = new Article();
						
						article1.setTitle("清纯可爱的漂亮妹子");
						article1.setUrl("http://mp.weixin.qq.com");
						article1.setPicUrl("https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=206533366,2722257096&fm=173&s=A78145A1C80240DC5424ADBD03009000&w=601&h=790&img.JPEG");
						article1.setDescription("description1");
						
						article2.setTitle("完美体型");
						article2.setUrl("http://mp.weixin.qq.com");
						article2.setPicUrl("https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=1528715484,4059992687&fm=173&s=CCC27A2344420EFDDC11508A0100A091&w=587&h=422&img.JPEG");
						article2.setDescription("description2");
						
						articleList.add(article1);
						articleList.add(article2);
						newsMessage.setArticle(articleList);
						
						respMessage = MessageUtil.newsMessageToXml(newsMessage);
						logger.info("返回图文信息respMessage:" +respMessage);
						
						break;
					}
					case (MenuManage.btn23Key):{
						respContent = "测试管理文章正在搜集中，请耐心等待，不要激动。";
						break;
					}
					case (MenuManage.btn31Key):{
						respContent = "人生如戏，全靠演技。";
						break;
					}
					case (MenuManage.btn32Key):{
						respContent = "已通知管理员，稍后管理员会与您联系";
						break;
					}
					case (MenuManage.btn33Key):{
						respContent = "意见反馈被点击";
						break;
					}
					}
				}
				}
			}else{
				String fromContent = requestMap.get("Content");
				// 根据消息类型回复信息
				// 如果请求消息类型为文本，则回复文本信息
				// 如果请求消息类型为图片，则原图返回，实现“图”尚往来
				if((MessageUtil.REQ_MESSSAGE_TYPE_TEXT).equals(msgType)){
					if ("帮助".equals(fromContent)) {
						respContent = "别急，哥睡醒后再帮助您。";
					}else{
						respContent = "请果断添加管理员微信号xiaojay0219获得更多帮助。";
					}
				}else if ((MessageUtil.REQ_MESSSAGE_TYPE_IMAGE).equals(msgType)){
					String mediaId = requestMap.get("MediaId");
					ImageMessage imageMessage = new ImageMessage();
					imageMessage.setToUserName(fromUserName);
					imageMessage.setFromUserName(toUserName);
					imageMessage.setCreateTime(System.currentTimeMillis());
					imageMessage.setMsgType(msgType);
					Image image = new Image();
					image.setMediaId(mediaId);
					imageMessage.setImage(image);
					respMessage = MessageUtil.imageMessageToXml(imageMessage);
					/*
					respMessage = ("<xml><ToUserName><![CDATA["
							+ fromUserName
							+ "]]></ToUserName>"
							+ "<FromUserName><![CDATA["
							+ toUserName
							+ "]]></FromUserName><CreateTime>"
							+ System.currentTimeMillis()
							+ "</CreateTime>"
							+ "<MsgType><![CDATA[image]]></MsgType>"
							+ "<Image><MediaId><![CDATA[" + mediaId + "]]></MediaId></Image></xml>");
					*/
					logger.info("“图”尚往来，返回信息respMessage:" +respMessage);
				}
			}
			
			//如果请求消息respContent非空且返回信息respMessage为空，则返回文本信息
			if(!"".equals(respContent) && "".equals(respMessage)){
				TextMessage textMessage = new TextMessage();
				textMessage.setFromUserName(toUserName);
				textMessage.setToUserName(fromUserName);
				textMessage.setCreateTime(System.currentTimeMillis());
				textMessage.setMsgType("text");
				textMessage.setContent(respContent);
				respMessage = MessageUtil.textMessageToXml(textMessage);
				logger.info("返回文本信息respMessage:" +respMessage);
			}
			//如果请求消息respContent为空且返回信息respMessage也为空，则返回文本信息
			else if ("".equals(respContent) &&"".equals(respMessage)){
				//默认返回文本信息success
				respMessage = ("<xml><ToUserName><![CDATA["
						+ fromUserName
						+ "]]></ToUserName>"
						+ "<FromUserName><![CDATA["
						+ toUserName
						+ "]]></FromUserName><CreateTime>"
						+ System.currentTimeMillis()
						+ "</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA["
						+ "success" + "]]></Content></xml>");
				logger.info("返回默认文本信息respMessage:" +respMessage);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return respMessage;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}
}