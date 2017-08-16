package com.loong.wechat.message.resp;
/**
 * 音乐消息回复类
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
