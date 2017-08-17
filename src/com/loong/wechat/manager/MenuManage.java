package com.loong.wechat.manager;

import net.sf.json.JSONObject;

import com.loong.wechat.pojo.Button;
import com.loong.wechat.pojo.CommonButton;
import com.loong.wechat.pojo.ComplexButton;
import com.loong.wechat.pojo.Menu;
import com.loong.wechat.pojo.ViewButton;
import com.loong.wechat.utils.PropertiesUtil;
import com.loong.wechat.utils.WeixinUtil;

/**
 * 菜单创建类
 * @author Jay
 *
 */
public class MenuManage {
	protected static PropertiesUtil pp = new PropertiesUtil();
	// 获取access_token的接口地址（GET） 限200（次/天）
	public static String access_token_url = pp.read("config.properties",
			"access_token_url");
	// 菜单创建（POST） 限100（次/天）
	public static String menu_create_url = pp.read("config.properties",
			"menu_create_url");
	public static final String btn11Key = "scan";
	public static final String btn12Key = "takePicture";
	public static final String btn21Url = "https://m.baidu.com";
	public static final String btn22Key = "testTechnology";
	public static final String btn23Key = "testManage";
	public static final String btn31Key = "help";
	public static final String btn32Key = "callAdmin";
	public static final String btn33Key = "suggestions";
	/** 
     * 组装菜单数据 
     *  
     * @return 
     */  
    public static Menu getMenu() {  
        CommonButton btn11 = new CommonButton();  
        btn11.setName("扫码");  
        btn11.setType("scancode_waitmsg");  
        btn11.setKey(btn11Key);  
  
        CommonButton btn12 = new CommonButton();  
        btn12.setName("拍照");  
        btn12.setType("pic_photo_or_album");  
        btn12.setKey(btn12Key); 
  
        ViewButton btn21 = new ViewButton();  
        btn21.setName("测试思想");  
        btn21.setType("view");  
        btn21.setUrl(btn21Url);  
  
        CommonButton btn22 = new CommonButton();  
        btn22.setName("测试技术");  
        btn22.setType("click");  
        btn22.setKey(btn22Key);  
  
        CommonButton btn23 = new CommonButton();  
        btn23.setName("测试管理");  
        btn23.setType("click");  
        btn23.setKey(btn23Key);  
  
        CommonButton btn31 = new CommonButton();  
        btn31.setName("操作说明");  
        btn31.setType("click");  
        btn31.setKey(btn31Key);  
  
        CommonButton btn32 = new CommonButton();  
        btn32.setName("呼叫管理员");  
        btn32.setType("click");  
        btn32.setKey(btn32Key);  
  
        CommonButton btn33 = new CommonButton();  
        btn33.setName("意见反馈");  
        btn33.setType("click");  
        btn33.setKey(btn33Key);  
  
        ComplexButton mainBtn1 = new ComplexButton();  
        mainBtn1.setName("测试入口");  
        mainBtn1.setSub_button(new Button[] { btn11, btn12});  
  
        ComplexButton mainBtn2 = new ComplexButton();  
        mainBtn2.setName("测试资讯");  
        mainBtn2.setSub_button(new Button[] { btn21, btn22, btn23});  
  
        ComplexButton mainBtn3 = new ComplexButton();  
        mainBtn3.setName("互动"); 
        mainBtn3.setSub_button(new Button[] { btn31, btn32, btn33 });  
  
        /** 
         * 这是公众号的菜单结构，每个一级菜单都有二级菜单项<br> 
         *  
         * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br> 
         * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br> 
         * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 }); 
         */  
        Menu menu = new Menu();  
        menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });  
  
        return menu;  
    }  
    
    /** 
	 * 创建菜单 
	 *  
	 * @param menu 菜单实例 
	 * @param accessToken 有效的access_token 
	 * @return 0表示成功，其他值表示失败 
	 */  
	public static int createMenu(Menu menu, String accessToken) {  
	    int result = 0;  
	  
	    // 拼装创建菜单的url  
	    String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);  
	    // 将菜单对象转换成json字符串  
	    String jsonMenu = JSONObject.fromObject(menu).toString();  
	    // 调用接口创建菜单  
	    JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST", jsonMenu);  
	  
	    if (null != jsonObject) {  
	        if (0 != jsonObject.getInt("errcode")) {  
	            result = jsonObject.getInt("errcode"); 
	            System.out.println("创建菜单失败errcode:"+jsonObject.getInt("errcode")+"errmsg:"+jsonObject.getString("errmsg"));
//	            logger.error("创建菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));  
	        }  
	    }  
	  
	    return result;  
	} 
	
	public static void main(String args[]){
		String token = WeixinUtil.getAccessToken().getToken();
		int result = createMenu(getMenu(),token);
		// 判断菜单创建结果  
        if (0 == result){
        	System.out.println("菜单创建成功！");  
        }else{
        	System.out.println("菜单创建失败！");     
        }   
	}
}
