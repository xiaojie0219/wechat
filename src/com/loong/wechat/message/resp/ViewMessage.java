package com.loong.wechat.message.resp;

/**
 * 响应view跳转至url
 */
public class ViewMessage extends BaseMessage {
	private String Event;
	private String EventKey;
	public String getEvent() {
		return Event;
	}
	public void setEvent(String event) {
		Event = event;
	}
	public String getEventKey() {
		return EventKey;
	}
	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}
}
