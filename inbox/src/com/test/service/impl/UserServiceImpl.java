package com.test.service.impl;

import java.io.File;
import java.util.UUID;

import com.sun.jmx.snmp.Timestamp;
import com.test.dao.UserDao;
import com.test.model.User;
import com.test.service.UserService;
import com.test.util.MyUtils;
import com.test.util.Static;

public class UserServiceImpl implements UserService {
	// 配置dao
	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public Object[] register(User user) {
		// TODO Auto-generated method stub
		User uPhone = userDao.find("from User u where u.phone ='" + user.getPhone()+"'");
		User uName = userDao.find("from User u where u.name ='" + user.getName()+"'");
		if(uPhone==null){
			//手机号未注册
			if(uName==null){
				//用户名未注册
				uName = new User();
				uName.setName(user.getName());
				uName.setPhone(user.getPhone());
				uName.setStatus(0);
				uName.setRole(0);
				uName.setHeadImg("default.png"); //默认头像
				// 给密码加密 添加盐 7位数
				String salt = MyUtils.getRandomString(7);
				String md5Pwd = MyUtils.getMD5(user.getPassword() + salt); // 加密后的密码
				uName.setPassword(md5Pwd);
				uName.setSalt(salt);
				uName.setCreateTime(new Timestamp().getDate());
				userDao.save(uName); //保存
				return new Object[]{uName,"注册成功!欢迎你"};
			}else{
				return new Object[]{null,"用户名已存在,请重新输入"};
			}	
		}
		return new Object[]{null,"手机号已经被注册!请直接登录"};
	}

	@Override
	public Object[] login(User user) {
		// TODO Auto-generated method stub
		//按照手机号和用户名查找用户
		User u = userDao.find("from User u where u.phone ='" + user.getName()+"'"+
		" or u.name ='"+user.getName()+"'");
		if(u!=null){
			String salt = u.getSalt();
			String password = u.getPassword(); // 数据库中的密文密码
			String md5Pwd = MyUtils.getMD5(user.getPassword() + salt); // 根据表单密码和盐计算的密文密码
			if(md5Pwd.equals(password)){
				return new Object[]{u,"登录成功!欢迎你"+u.getName()};
			}
			return new Object[]{null,"密码错误"};
		}
		return new Object[]{null,"用户名不存在"};
	}

	
	

	@Override
	public Object[] changePwd(User user, int id) {
		// TODO Auto-generated method stub
		User u = userDao.find("from User u where u.id =" + id);
		//MD5加密该密码
		String salt = u.getSalt(); //获取用户盐
		String password = user.getPassword(); //传来的原始密码
		String md5Pwd = MyUtils.getMD5(password + salt); //计算后的密文密码
		if(md5Pwd.equals(u.getPassword())){ //密码正确
			//更新密码
			String newMD5Pwd = MyUtils.getMD5(user.getNewPassword() + salt);
			u.setPassword(newMD5Pwd);
			userDao.update(u);
			return new Object[]{true,"密码修改成功!"};
		}
		return new Object[]{false,"原密码输出错误！请重试"};
	}

	@Override
	public Object[] changeHeadImg(User user, int id) {
		// TODO Auto-generated method stub
		User u = userDao.find("from User u where u.id =" + id);
		//获取之前的图片文件名
		String oldImgName = u.getHeadImg();
		//获取图片文件
		File uploadFile = user.getUploadFile();
		String uploadFileContentType = user.getUploadFileContentType();
		//获取图片后缀
		String ext = uploadFileContentType.split("/")[1];
		//图片名称为 UUID保证唯一性
		String imgName = UUID.randomUUID().toString()+"."+ext;
		u.setHeadImg(imgName);
		userDao.update(u);
		//写入文件到服务器
		MyUtils.writeFile(uploadFile, Static.USER_HEADIMG, imgName);
		//删除之前的文件
		MyUtils.deleteFile(Static.USER_HEADIMG, oldImgName);
		return new Object[]{"头像修改成功!",imgName,u};
	}

	@Override
	public User getUserById(int id) {
		// TODO Auto-generated method stub
		User u = userDao.find("from User u where u.id =" + id);
		return u;
	}

}
