package com.test.action;



import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.util.ServletContextAware;

import com.opensymphony.xwork2.ActionSupport;
import com.test.util.Static;

public class DownloadAction extends ActionSupport {
	
	//文件流
	private InputStream inStream;
	//文件地址
	private String fileUrl;
	//文件名称
	private String fileName;
	
	
	public InputStream getInStream() {
		return inStream;
	}


	public void setInStream(InputStream inStream) {
		this.inStream = inStream;
	}


	public String getFileUrl() {
		return fileUrl;
	}


	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}


	public String getFileName() {
		try {
			fileName = new String(fileName.getBytes("GBK"),"ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		File file = new File(Static.inboxPath+File.separator+fileUrl);
		try {
			inStream = new FileInputStream(file);
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println("红红火火恍恍惚惚");
		return SUCCESS;
	}
	
}
