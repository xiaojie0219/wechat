package com.loong.wechat.message.resp;

public class ImageMessage extends BaseMessage {
    private Image image;

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
/*
	public String getXmlRespMessage() {
		String respMessage = ("<xml>"
				+ "<ToUserName><![CDATA[" +this.getToUserName() + "]]></ToUserName>"
				+ "<FromUserName><![CDATA["	+ this.getFromUserName() + "]]></FromUserName>"
				+ "<CreateTime>" + this.getCreateTime() + "</CreateTime>"
				+ "<MsgType><![CDATA[image]]></MsgType>" 
				+ "<Image><MediaId><![CDATA[" + this.getMediaId() + "]]></MediaId></Image>"
				+ "</xml>");
		return respMessage;
	}
*/
}
