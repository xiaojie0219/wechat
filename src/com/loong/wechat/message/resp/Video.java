package com.loong.wechat.message.resp;

/**
 * 视频类
 * 
 * @author Jay
 * 
 */
public class Video {
	// 保存在微信平台上的媒体文件ID
	private String MediaId;
	// 保存在微信平台上的视频缩略图ID
	private String ThumbMediaId;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getThumbMediaId() {
		return ThumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}

}
