package com.loong.wechat.message.resp;
/**
 * 响应文本消息
 */
public class TextMessage extends BaseMessage{
	//回复的消息类容
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

}
