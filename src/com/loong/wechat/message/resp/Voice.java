package com.loong.wechat.message.resp;
/**
 * 语音类
 * @author Jay
 *
 */
public class Voice {
	//保存在微信平台上的语音文件ID
	private String MediaId;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
	
}
