package com.test.model;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Inbox implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2789190961255785222L;
	//持久化属性
	private String id; //唯一标识符(因为id用来访问收件地址 所以弄复杂点)
	private String title; //收件夹标题名称
	private String logo; //logo图片名称
	private String remark; //收件夹说明 备注
	private Date endTime; //截止时间
	private Date createTime; //创建时间
	private int star ; //是否标星
	private String password ; //收件密码
	private int status; //状态 开启 关闭
	private String closeReason; //关闭原因
	//多对一
	private User user;
	
	//一对多
	private Set docs = new HashSet(0);
	
	//其他属性
	private File uploadFile;
	private String uploadFileContentType;
	
	
	public File getUploadFile() {
		return uploadFile;
	}
	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}
	public String getUploadFileContentType() {
		return uploadFileContentType;
	}
	public void setUploadFileContentType(String uploadFileContentType) {
		this.uploadFileContentType = uploadFileContentType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getStar() {
		return star;
	}
	public void setStar(int star) {
		this.star = star;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCloseReason() {
		return closeReason;
	}
	public void setCloseReason(String closeReason) {
		this.closeReason = closeReason;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public Set getDocs() {
		return docs;
	}
	public void setDocs(Set docs) {
		this.docs = docs;
	}
	public Inbox() {
		super();
	}
	public Inbox(String id, String title, String logo, String remark,
			Date endTime, Date createTime, int star, String password,
			int status, String closeReason, User user, Set docs) {
		super();
		this.id = id;
		this.title = title;
		this.logo = logo;
		this.remark = remark;
		this.endTime = endTime;
		this.createTime = createTime;
		this.star = star;
		this.password = password;
		this.status = status;
		this.closeReason = closeReason;
		this.user = user;
		this.docs = docs;
	}
	

}
