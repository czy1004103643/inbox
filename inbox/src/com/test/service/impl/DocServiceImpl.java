package com.test.service.impl;

import java.io.File;
import java.util.UUID;

import com.sun.jmx.snmp.Timestamp;
import com.test.dao.DocDao;
import com.test.dao.FileHashDao;
import com.test.model.Doc;
import com.test.model.FileHash;
import com.test.model.Inbox;
import com.test.service.DocService;
import com.test.util.MyUtils;
import com.test.util.Static;

public class DocServiceImpl implements DocService{
	//配置dao
	private DocDao docDao;
	private FileHashDao fileHashDao;
	
	public void setFileHashDao(FileHashDao fileHashDao) {
		this.fileHashDao = fileHashDao;
	}

	public void setDocDao(DocDao docDao) {
		this.docDao = docDao;
	}

	@Override
	public void upload(Doc doc, String inboxId) {
		// TODO Auto-generated method stub
		File file = doc.getUploadFile();
		String uploadFileContentType = doc.getUploadFileContentType();
		String fileName = doc.getUploadFileFileName();
		//计算hash值
		String fileHash = MyUtils.getMd5ByFile(file);
		//数据库查询
		FileHash myHash = fileHashDao.findById(FileHash.class, fileHash);
		System.out.println("文件大小:"+file.length());
		if(myHash==null){ //如果为空 说明文件库里面没有该文件
			//上传文件
			Doc d = new Doc();
			d.setName(fileName);
			d.setSize(MyUtils.getFormatSize(file.length()));
			d.setDownload(0);
			String url = UUID.randomUUID().toString()+"."+fileName.split("\\.")[1];
			d.setUrl(url);
			d.setCreateTime(new Timestamp().getDate());
			Inbox inbox = new Inbox();
			inbox.setId(inboxId);
			d.setInbox(inbox);
			docDao.save(d);
			//保存该hash值
			myHash = new FileHash();
			myHash.setId(fileHash);
			myHash.setFileName(fileName);
			myHash.setFileUrl(url);
			fileHashDao.save(myHash);
			//写入文件到服务器
			MyUtils.writeFile(file, Static.inboxPath,url);
		}else{
			//上传文件 但不写入到服务器 已经存在
			Doc d = new Doc();
			d.setName(fileName);
			d.setUrl(myHash.getFileUrl());
			d.setSize(MyUtils.getFormatSize(file.length()));
			d.setDownload(0);
			d.setCreateTime(new Timestamp().getDate());
			Inbox inbox = new Inbox();
			inbox.setId(inboxId);
			d.setInbox(inbox);
			docDao.save(d);
		}
	}
	
}
