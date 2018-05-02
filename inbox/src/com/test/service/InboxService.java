package com.test.service;

import java.util.Date;
import java.util.List;

import com.test.model.Inbox;

public interface InboxService {
	/**
	 * 获取所有的inbox 
	 * @param inbox 对象参数
	 * @param id 用户id
	 * @param sortId  排序的id
	 * @return  返回该用户的所有数据
	 */
	List<Inbox> getAll(Inbox inbox,int userId,int sortId);
	
	/**
	 * 获取inbox
	 * @param id
	 * @return
	 */
	Inbox getInboxById(String id);
	
	
	/**
	 * 添加收件夹
	 * @param inbox
	 * @param userId
	 */
	void add(Inbox inbox,int userId);
	
	/**
	 * 修改截止时间
	 * @param endTime
	 * @param id
	 */
	void updateEndTime(Date endTime,String id);
	
	/**
	 * 关闭收件夹
	 * @param closeReason
	 * @param id
	 */
	void closeInbox(String closeReason,String id);
	
	/**
	 * 打开收件夹
	 * @param id
	 */
	boolean openInbox(String id);
	
	/**
	 * 标星
	 * @param id
	 */
	void star(String id);
	
	/**
	 * 取消标星
	 * @param id
	 */
	void cancelStar(String id);
	
	/**
	 * 删除该收件夹
	 * @param id
	 */
	void delete(String id);
	
	/**
	 * 为收件夹添加密码
	 * @param id
	 * @param pwd
	 */
	void updatePwd(String id,String pwd);
	
}
