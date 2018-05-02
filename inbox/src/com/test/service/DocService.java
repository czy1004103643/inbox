package com.test.service;

import com.test.model.Doc;

public interface DocService {
	
	/**
	 * 上传文件
	 * @param doc
	 * @param inboxId
	 */
	void upload(Doc doc,String inboxId);
}
