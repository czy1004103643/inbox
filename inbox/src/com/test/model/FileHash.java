package com.test.model;

import java.io.Serializable;

public class FileHash implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7610404765507977018L;
	
	private String id;
	private String fileName;
	private String fileUrl;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public FileHash(String id, String fileName, String fileUrl) {
		super();
		this.id = id;
		this.fileName = fileName;
		this.fileUrl = fileUrl;
	}
	public FileHash() {
		super();
	}
}
