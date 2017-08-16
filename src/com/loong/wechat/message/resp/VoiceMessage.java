package com.loong.wechat.message.resp;

/**
 * 语音消息类
 * @author Administrator
 *
 */
public class VoiceMessage extends BaseMessage {
	private Voice voice;

	public Voice getVoice() {
		return voice;
	}

	public void setVoice(Voice voice) {
		this.voice = voice;
	}
	
}
