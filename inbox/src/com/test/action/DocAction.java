package com.test.action;



import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ModelDriven;
import com.test.model.Doc;
import com.test.service.DocService;
import com.test.util.MyUtils;
import com.test.util.Static;

public class DocAction implements ModelDriven<Doc>{
	private Doc doc = new Doc();
	@Override
	public Doc getModel() {
		// TODO Auto-generated method stub
		return doc;
	}
	//配置service
	private DocService docService;
	
	private String linkId; //inboxId
	
	public String getLinkId() {
		return linkId;
	}

	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}

	public void setDocService(DocService docService) {
		this.docService = docService;
	}
	
	public void upload(){
		docService.upload(doc, linkId);
		MyUtils.outMsg("上传成功!", true);
	}
	

}
