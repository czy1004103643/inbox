package com.test.util;

import java.io.File;

public class Static {
	//session名称
	public static final String onlineUser = "user";
	
	//inbox状态符
	public static final int INBOX_NOSTAR = 0;
	public static final int INBOX_STAR = 1;
	public static final int INBOX_ON = 0;
	public static final int INBOX_OFF = 1;
	
	//图片资源路径
	public static final String  srcPath = Config.getValues("uploadDirectory")+"src";
	public static final String  inboxPath = srcPath + File.separator + "inbox";
	public static final String  imgPath = srcPath+File.separator+"img";
	public static final String USER_HEADIMG = imgPath + File.separator + "usersHeadImg";
	public static final String INBOX_LOGO = imgPath + File.separator + "inboxLogo";
}
