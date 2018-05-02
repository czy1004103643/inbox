<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="<c:url value='js/bootstrap/css/bootstrap.min.css'/>" />
<link rel="stylesheet" href="<c:url value='css/common.css'/>" />
<link rel="stylesheet"
	href="<c:url value='js/layer/skin/default/layer.css'/>" />
<link rel="stylesheet"
	href="<c:url value='js/datetimepicker/jquery.datetimepicker.css'/>" />
<link rel="stylesheet" href="<c:url value='css/main.css'/>" />
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<!--主内容开始-->
	<div class="container clearfix main-content">
		<!--工具栏开始-->
		<div class="toolbar">
			<div class="btn-group">
				<button type="button"
					style="background: none; border: 1px solid #efefef; box-shadow: none; color: #999;"
					class="btn btn-default dropdown-toggle" data-toggle="dropdown">
					<i class="glyphicon glyphicon-sort"></i> <span id="sortList-text">创建时间</span>
					<span class="caret"></span>
				</button>
				<ul class="dropdown-menu" role="menu"
					style="min-width: 100%; text-align: center;">
					<li><a href="javascript:;" id="sort_1"
						onclick="sortInbox(this.id,this.title)" title="创建时间">创建时间</a></li>
					<li><a href="javascript:;" id="sort_2"
						onclick="sortInbox(this.id,this.title)" title="截止时间">截止时间</a></li>
					<li><a href="javascript:;" id="sort_3"
						onclick="sortInbox(this.id,this.title)" title="收件数量">收件数量</a></li>
					<li><a href="javascript:;" id="sort_4" style="color: #f0ad4e;"
						onclick="sortInbox(this.id,this.title)" title="只显标星">只显标星</a></li>
					
					<li><a href="javascript:;" id="sort_5"
						onclick="sortInbox(this.id,this.title)" title="只显截止">只显截止</a></li>
					<li><a href="javascript:;" id="sort_6" style="color: #5cb85c;"
						onclick="sortInbox(this.id,this.title)" title="只显开启">只显开启</a></li>
					<li><a href="javascript:;" id="sort_7" style="color: #d9534f;"
						onclick="sortInbox(this.id,this.title)" title="只显关闭">只显关闭</a></li>
				</ul>
				
			</div>

			<div class="set-btn-right">
				<a class="btn btn-set btn-outline btn-width"
					onclick="showInboxForm()"> <i
					class="glyphicon glyphicon-pencil"
					style="margin-left: -15px; padding-left: 10px;"></i> 创建收件夹
				</a>
			</div>
		</div>
		<!--工具栏结束-->
		<!--文件列表开始-->
		<ul class="inboxs-group">
			
		</ul>
	</div>
	<!--主内容结束-->
</body>
<script src="<c:url value='js/jquery-1.11.2.min.js'/>"></script>
<script src="<c:url value='js/bootstrap/js/bootstrap.min.js'/>"></script>
<script src="<c:url value='js/layer/layer.js'/>"></script>
<script src="<c:url value='js/main.js'/>"></script>
<script src="<c:url value='js/datetimepicker/jquery.datetimepicker.js'/>"></script>
</html>