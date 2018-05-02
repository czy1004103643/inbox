package com.test.action;

import java.io.File;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ModelDriven;
import com.test.model.User;
import com.test.service.UserService;
import com.test.util.MyUtils;
import com.test.util.SmsUtils;
import com.test.util.Static;



public class UserAction implements ModelDriven<User>{
	private User user = new User();
	private String word;
	
	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	@Override
	public User getModel() {
		// TODO Auto-generated method stub
		return user;
	}
	//配置servic
	private UserService userService;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public void login(){
		Object[] obj = userService.login(user);
		if(obj[0]!=null){
			//存入session
			MyUtils.getSession().setAttribute(Static.onlineUser, obj[0]);
			//存cookie
			User u = (User)obj[0];
			String cookieValue = MyUtils.encodeCookie(u.getId(),u.getPassword(),u.getSalt());
			MyUtils.addCookie(Static.onlineUser, cookieValue);
			//在返回响应前  写入cookie
			MyUtils.outMsg((String)obj[1], true); 
		}else{
			MyUtils.outMsg((String)obj[1], false);
		}
	}
	
	/**
	 * 注册
	 */
	public void register(){
		String smsCode = MyUtils.getSessionObject(user.getPhone());//获取短信验证码
		if(null!=smsCode&&smsCode.equals(word)){
			//正确
			Object[] obj = userService.register(user);
			if(obj[0]!=null){ //返回用户不为空 代表注册成功
				MyUtils.getSession().setAttribute(Static.onlineUser, obj[0]);
				//清空验证码
				MyUtils.getSession().removeAttribute(user.getPhone());
				//返回数据
				MyUtils.outMsg((String)obj[1], true);
			}else{
				//注册失败 返回数据
				MyUtils.outMsg((String)obj[1], false);
			}
		}else{ 
			//返回数据
			MyUtils.outMsg("验证码有误", false);
		}
	}
	
	/**
	 * 更新密码
	 */
	public void changePwd(){
		User onlineUser = MyUtils.getSessionObject(Static.onlineUser);
		if(null!=onlineUser){
			Object[] obj = userService.changePwd(user, onlineUser.getId());
			if((boolean)obj[0]){
				MyUtils.outMsg((String)obj[1], true);
			}else{
				MyUtils.outMsg((String)obj[1], false);
			}
		}else{
			MyUtils.outMsg("你已经断网或离线,请刷新页面重新登录", false);
		}
	}
	
	public void changeHeadImg(){
		User onlineUser = MyUtils.getSessionObject(Static.onlineUser);
		if(null!=onlineUser){
			Object[] obj = userService.changeHeadImg(user, onlineUser.getId());
			//更新session
			MyUtils.getSession().setAttribute(Static.onlineUser, obj[2]);
			JSONObject jo = new JSONObject(); //返回数据
			jo.put("msg", obj[0]);
			jo.put("headImg", obj[1]);
			jo.put("status", true);
			MyUtils.writeJSON(jo);
		}else{
			MyUtils.outMsg("你已经断网或离线,请刷新页面重新登录", false);
		}
	}
	
	public void displayCookie(){
		Cookie[] cookies;
		cookies = MyUtils.getReq().getCookies();
		for (Cookie cookie : cookies) {
			System.out.println(cookie.getName());
			
		}
	}
	
	public  void getHeadImg(){
		String imgName = user.getHeadImg();
		//图片目录文件
		String headImgPath = Static.USER_HEADIMG;
		//请求的图片路径
		String imgPath = headImgPath+File.separator+imgName;
		File imgFile= new File(imgPath);
		if(!imgFile.exists()){
			imgPath = headImgPath+File.separator+"default.png";
			imgFile = new File(imgPath);
		}
		MyUtils.writeImg(imgFile);
	}
	
	
	public void SMS(){
		String verification  = MyUtils.getSix(); //获取随机验证码 6位
		String signName = "实训";
		String templateCode = "SMS_39225041";
		String paramString = "{name:'"+user.getPhone()+"',word:'"+verification+"'}";
		String recNum = user.getPhone();
		Object[] objs	=  SmsUtils.sendSms(signName, templateCode, paramString, recNum);
		//发送成功后  直接把验证码存入session;
		if((boolean) objs[0]){
			HttpSession session_msgCode = MyUtils.getSession();
			session_msgCode.setMaxInactiveInterval(5*60); //有效期为5分钟
			session_msgCode.setAttribute(recNum, verification);
			System.out.println(objs[1]);
		}else{
			System.out.println(objs[1]);
		}
		//返回数据
		JSONObject jo = new JSONObject();
		jo.put("msg", objs[1]);
		MyUtils.writeJSON(jo);
	}

}
