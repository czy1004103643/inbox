package com.test.model;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

public class Doc implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2728102862334727287L;
	private String id; // 唯一ID
	private String name; // 文件名字
	private String url; // 文件地址 地址和文件名不一样
	private String size; // 文件大小 带单位
	private Date createTime; // 上传时间
	private int download; // 下载次数

	// 多对一
	private Inbox inbox;

	// 其他属性
	private File uploadFile;
	private String uploadFileContentType;
	private String uploadFileFileName;
	
	private String downLoadFileName;
	
	public String getDownLoadFileName() {
		return downLoadFileName;
	}

	public void setDownLoadFileName(String downLoadFileName) {
		this.downLoadFileName = downLoadFileName;
	}

	public String getUploadFileFileName() {
		return uploadFileFileName;
	}

	public void setUploadFileFileName(String uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getDownload() {
		return download;
	}

	public void setDownload(int download) {
		this.download = download;
	}

	public Inbox getInbox() {
		return inbox;
	}

	public void setInbox(Inbox inbox) {
		this.inbox = inbox;
	}

	public Doc(String id, String name, String url, String size,
			Date createTime, int download, Inbox inbox) {
		super();
		this.id = id;
		this.name = name;
		this.url = url;
		this.size = size;
		this.createTime = createTime;
		this.download = download;
		this.inbox = inbox;
	}

	public Doc() {
		super();
	}

}
