package com.test.service.impl;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;

import com.sun.jmx.snmp.Timestamp;
import com.test.dao.InboxDao;
import com.test.dao.UserDao;
import com.test.model.Inbox;
import com.test.model.User;
import com.test.service.InboxService;
import com.test.util.MyUtils;
import com.test.util.Static;

public class InboxServiceImpl implements InboxService {
	// 配置dao
	private InboxDao inboxDao;
	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setInboxDao(InboxDao inboxDao) {
		this.inboxDao = inboxDao;
	}

	@Override
	public List<Inbox> getAll(Inbox inbox, int userId, int sortId) {
		// TODO Auto-generated method stub
		List<Inbox> inboxs = new ArrayList<Inbox>();
		String hql = "from Inbox where userId =" + userId; // 获取该用户的所有inbox
		hql = addSort(hql, sortId); // 添加排序条件
		inboxs = inboxDao.get(hql);
		for (Inbox item : inboxs) {
			item.setUser(null); // 设置空 防止用户信息泄露 以及JSON循环读取
		}
		// 当排序条件是3 说明是按照收件数量
		if (sortId == 3) {
			sortInboxByDocSize(inboxs);
		}
		return inboxs;
	}

	@Override
	public void add(Inbox inbox, int userId) {
		// TODO Auto-generated method stub
		User user = userDao.find("from User t where t.id =" + userId);
		Inbox newInbox = new Inbox();
		newInbox.setUser(user);
		// 复制属性(表单属性 包括:收件夹名称 收件夹说明 截止时间)
		newInbox.setTitle(inbox.getTitle());
		newInbox.setRemark(MyUtils.textareaToSql(inbox.getRemark())); // 改变样式存入数据库
		newInbox.setEndTime(inbox.getEndTime());
		// 设置star status
		newInbox.setStar(Static.INBOX_NOSTAR); // 未标星
		newInbox.setStatus(Static.INBOX_ON); // 开启
		if (null != inbox.getUploadFile()) {
			// logo文件不为空
			File uploadFile = inbox.getUploadFile();
			String uploadFileContentType = inbox.getUploadFileContentType();
			// 获取图片后缀
			String ext = uploadFileContentType.split("/")[1];
			// 图片名称为 UUID保证唯一性
			String imgName = UUID.randomUUID().toString() + "." + ext;
			newInbox.setLogo(imgName);
			// 写入文件
			MyUtils.writeFile(uploadFile, Static.INBOX_LOGO, imgName);
		}
		// 设置创建时间
		newInbox.setCreateTime(new Timestamp().getDate());
		inboxDao.save(newInbox);
	}

	@Override
	public void updateEndTime(Date endTime, String id) {
		// TODO Auto-generated method stub
		Inbox inbox = inboxDao.findById(Inbox.class, id);
		inbox.setEndTime(endTime);
		inboxDao.update(inbox);
	}

	@Override
	public void closeInbox(String closeReason, String id) {
		// TODO Auto-generated method stub
		Inbox inbox = inboxDao.findById(Inbox.class, id);
		inbox.setCloseReason(MyUtils.textareaToSql(closeReason));
		inbox.setStatus(Static.INBOX_OFF);
		inboxDao.update(inbox);
	}

	@Override
	public boolean openInbox(String id) {
		// TODO Auto-generated method stub
		Inbox inbox = inboxDao.findById(Inbox.class, id);
		if(inbox.getEndTime().toString().compareToIgnoreCase(time())>0){
			inbox.setCloseReason(null);
			inbox.setStatus(Static.INBOX_ON);
			inboxDao.update(inbox);
			return true;
		}
		return false;
		
	}

	@Override
	public void star(String id) {
		// TODO Auto-generated method stub
		Inbox inbox = inboxDao.findById(Inbox.class, id);
		inbox.setStar(Static.INBOX_STAR);
		inboxDao.update(inbox);
	}

	@Override
	public void cancelStar(String id) {
		// TODO Auto-generated method stub
		Inbox inbox = inboxDao.findById(Inbox.class, id);
		inbox.setStar(Static.INBOX_NOSTAR);
		inboxDao.update(inbox);
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		Inbox inbox = inboxDao.findById(Inbox.class, id);
		// 获取inbox的logo 把该logo删除
		String imgName = inbox.getLogo();
		// 删除inbox 根据级联关系 会删除所有的收件夹下的文件信息
		User user = inbox.getUser(); // 解除父子关系
		user.getInboxs().remove(inbox);
		inboxDao.delete(inbox);
		// 删除文件
		MyUtils.deleteFile(Static.INBOX_LOGO, imgName);
	}

	@Override
	public void updatePwd(String id, String pwd) {
		// TODO Auto-generated method stub
		Inbox inbox = inboxDao.findById(Inbox.class, id);
		inbox.setPassword(pwd);
		inboxDao.update(inbox);
	}

	@Override
	public Inbox getInboxById(String id) {
		// TODO Auto-generated method stub
		Inbox inbox = inboxDao.findById(Inbox.class, id);
		inbox.setUser(null);
		return inbox;
	}

	/*************** 拓展方法 ****************/
	/**
	 * 添加排序返回hql
	 * 
	 * @param hql
	 * @param sortId
	 * @return
	 */
	public String addSort(String hql, int sortId) {
		switch (sortId) {
		case 1: // 如果是1 说明是创建时间排序 所有排序都是倒序
			hql += " order by createTime desc";
			break;
		case 2: // 如果是2 说明是截止时间排序 所有排序都是倒序
			hql += "  order by endTime desc";
			break;
		case 4: // 如果是4 说明是只显示标星的数据
			hql += " and star = '" + Static.INBOX_STAR + "'";
			break;
		case 5: // 如果是5说明是只显示截止的数据
			hql += " and endTime <= '" + time()
					+ "'";
			break;
		case 6: // 如果是6 说明是只显示开启的数据
			hql += " and status = '" + Static.INBOX_ON + "'";
			break;
		case 7: // 如果是7 说明是只显示关闭的数据
			hql += " and status = '" + Static.INBOX_OFF + "'";
			break;
		default:
			break;
		}
		return hql;
	}

	public void sortInboxByDocSize(List<Inbox> data) {
		Collections.sort(data, new Comparator<Inbox>() {

			@Override
			public int compare(Inbox o1, Inbox o2) {
				// TODO Auto-generated method stub
				Integer a = o1.getDocs().size();
				Integer b = o2.getDocs().size();
				// 降序
				return b.compareTo(a);
			}

		});
	}

	public String time() {
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = format.format(date);
		return time;
	}
}
