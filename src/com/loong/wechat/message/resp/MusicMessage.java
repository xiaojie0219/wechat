package com.loong.wechat.message.resp;
/**
 * 响应的音乐消�?
 * @author pengsong
 * @date 2016.01.19
 */
public class MusicMessage extends BaseMessage{
	//音乐
	private Music Music;

	public Music getMusic() {
		return Music;
	}

	public void setMusic(Music music) {
		Music = music;
	}
	
}
