package com.loong.wechat.message.resp;

/**
 * 响应文本消息
 */
public class TextMessage extends BaseMessage {
	// 回复的文本消息类容
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
	/*
	 * public String getXmlRespMessage(){ String respMessage =
	 * ("<xml><ToUserName><![CDATA[" + this.getToUserName() + "]]></ToUserName>"
	 * + "<FromUserName><![CDATA[" + this.getFromUserName() +
	 * "]]></FromUserName><CreateTime>" + this.getCreateTime() +
	 * "</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[" +
	 * this.getContent() + "]]></Content></xml>"); return respMessage; }
	 */
}
