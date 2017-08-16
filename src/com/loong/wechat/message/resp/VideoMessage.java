package com.loong.wechat.message.resp;

/**
 * 视频消息回复类
 * 
 * @author Jay
 * 
 */
public class VideoMessage extends BaseMessage {
	// 视频
	private Video video;

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

}
