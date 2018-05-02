<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta charset="utf-8" />
		<title>快速创建收件夹</title>
		<link rel="stylesheet" type="text/css" href="js/bootstrap/css/bootstrap.min.css" />
		

		<link rel="stylesheet" type="text/css" href="css/common.css" />
		<link rel="stylesheet" type="text/css" href="css/header.css" />
	
		<link rel="stylesheet" type="text/css" href="js/layer/skin/default/layer.css"/>
	</head>

	<body>
		<!--导航栏开始-->
		<jsp:include page="header.jsp"></jsp:include>
		<!--导航栏结束-->
		<!--主内容开始-->
		<div class="inboxFile-content container clearfix">
			<ol class="breadcrumb">
				<li><a href="#">首页</a></li>
				<li id="title"></li>
     			<li class="active">收件记录</li>
			</ol>
			<!-- <button type="button" class="btn btn-default"  style="margin-bottom: 20px;" onclick="delInboxFile()">
						<i class="glyphicon glyphicon-trash"></i>
						<span id="sortList-text">删除文件</span> -->
				        
		    </button>
			<div class="panel panel-default"  style="border-bottom:0;box-shadow:none" >
				<table class="table table-hover">
					<thead>
						<tr>
							<th align="center"><input  id="check_all" type="checkbox" autocomplete="off"></th>
							<th width="20px"></th>
							<th id="fileName">文件名称</th>
							<th>文件大小</th>
							<th>上传时间</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody id="docs">
					
					</tbody>
				</table>
			</div>
		</div>
		<!--主内容结束-->
		
		
	</body>
	<script src="js/jquery-1.11.2.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="js/bootstrap/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="js/layer/layer.js" type="text/javascript" charset="utf-8"></script>
	<script src="js/inboxFile.js" type="text/javascript" charset="utf-8"></script>
   
</html>