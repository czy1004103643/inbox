package com.test.service;

import com.test.dao.UserDao;
import com.test.model.User;

public interface UserService {
	/**
	 * 用户注册
	 * @param user
	 * @return object[0]代表用户对象 object[1]代表返回信息
	 */
	Object[] register(User user);
	
	/**
	 * 用户登录
	 * @param user
	 * @return object[0]代表用户对象 object[1]代表返回信息
	 */
	Object[] login(User user);
	
	User getUserById(int id);
	
	/**
	 * 根据传来的id  和密码信息进行修改密码
	 * @param user
	 * @param id
	 * @return
	 */
	Object[] changePwd(User user,int id);
	
	/**
	 * 修改用户头像
	 * @param user
	 * @param id
	 * @return object[0]代表返回信息 object[1]代表返回图片地址
	 */
	Object[] changeHeadImg(User user,int id);
}
