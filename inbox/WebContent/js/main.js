$(function() {
	// 加载页面时 同时加载该用户的inbox信息
	loadInbox(1);
})

function loadInbox(sortId) {
	$.ajax({
		url : getWebProjectName() + '/inbox!getAll.action?sortId='+sortId,
		type : 'POST',
		success : function(data) {
			if (data.status) {
				console.info(data.rows)
				showInbox(data.rows);
			} else {
				showErrorMsg(data.msg);
			}
		}
	})
}

function showInbox(rows) {
	$(".inboxs-group").html(""); // 清空列表
	for (var i = 0; i < rows.length; i++) {
		var content = undefined;
		//是否标星
		if(rows[i].star==0){
			content = '<li class="inbox" id="'+rows[i].id+'">'
				+ '<div class="inbox-content">'
				+ '<a class="upvote" href="/inbox/inboxFile.jsp?id='+rows[i].id+'" title="收件数量('+rows[i].docs.length+'),点击查看详细信息">'
				+ '<span class="vote-count">'+rows[i].docs.length+'</span>'
				+ '</a>'
				+ '<div class="inbox-msg">'
				+ '<a class="inbox-msg-url title" title="预览" href="/inbox/inbox.html?link='+rows[i].id+'" target="_blank">'
				+ rows[i].title
				+ '</a> <span class="inbox-tagline description">'
				+ ' <span class="ctime" style="margin: 0;" title="创建时间">'+rows[i].createTime.split(" ")[0]+'</span>'
				+ '<span class="endtime" title="截止时间"> '
				+ ' <i class="glyphicon glyphicon-time"></i>'
				+ rows[i].endTime.split(" ")[0]
				+ '	</span>'
				+ '</span>'
				+ '</div>'
				+ '</div>'
				+ '<div class="inbox-actions">'
				+ '<a href="javascript:;" id="link_'+rows[i].id+'" '
				+ 'onclick="showLink(this.id)" title="获取收件夹地址">'
				+ '<i class="glyphicon glyphicon-link"></i>'
				+ '</a> <a href="javascript:;" id="time_'+rows[i].id+'"'
				+ 'onclick="showEndTime(this.id,this.title)" title="截止时间:'+rows[i].endTime.split(" ")[0]+'">'
				+ '<i class="glyphicon glyphicon-time"></i></a> ';
		}else{
			content = '<li class="inbox" id="'+rows[i].id+'">'
				+ '<div class="inbox-content">'
				+ '<a class="upvote" href="/inbox/inboxFile.jsp?id='+rows[i].id+'" title="收件数量(0),点击查看详细信息">'
				+ '<span class="vote-count">'+rows[i].docs.length+'</span>'
				+ '</a>'
				+ '<div class="inbox-msg">'
				+ '<a class="inbox-msg-url title" title="预览" href="/inbox/inbox.html?link='+rows[i].id+'" target="_blank">'
				+ rows[i].title
				+ ' <i class="glyphicon glyphicon-star"'
				+ 'style="font-size: 14px; color: #f0ad4e;"></i>'
				+ '</a> <span class="inbox-tagline description">'
				+ ' <span class="ctime" style="margin: 0;" title="创建时间">'+rows[i].createTime.split(" ")[0]+'</span>'
				+ '<span class="endtime" title="截止时间"> '
				+ ' <i class="glyphicon glyphicon-time"></i>'
				+ rows[i].endTime.split(" ")[0]
				+ '	</span>'
				+ '</span>'
				+ '</div>'
				+ '</div>'
				+ '<div class="inbox-actions">'
				+ '<a href="javascript:;" id="link_'+rows[i].id+'" '
				+ 'onclick="showLink(this.id)" title="获取收件夹地址">'
				+ '<i class="glyphicon glyphicon-link"></i>'
				+ '</a> <a href="javascript:;" id="time_'+rows[i].id+'"'
				+ 'onclick="showEndTime(this.id,this.title)" title="截止时间:'+rows[i].endTime.split(" ")[0]+'">'
				+ '<i class="glyphicon glyphicon-time"></i></a> ';
		}
		
		//是否加密
		if(rows[i].password!=null&&rows[i].password!=""){
			content += '<a href="javascript:;" id="password_'+rows[i].password+'" '
			+ 'onclick="showPwdLink(this.id)"> '
			+ '<i class="glyphicon glyphicon-lock" style="color: #5cb85c;"></i></a>';
		}
		
		//是否关闭
		if(rows[i].status == 0){
			content += '<a href="javascript:;" id="open_'+rows[i].id+'" '
				+ 'onclick="closeInbox(this.id)"> '
				+ '<i class="glyphicon glyphicon-play" style="color: #5cb85c;"></i></a>'
				
				+ '<a id="config_'+rows[i].id+'" href="javascript:;" onclick="showAction(this.id)">'
				+ '<i class="glyphicon glyphicon-cog"></i>'
				+ '</a>'
				+ '</div>'
				+ '</li>';
		}else{
			content +=  '<a href="javascript:;"  id="close_'+rows[i].id+'" '
			+ 'onclick="openInbox(this.id)"> '
			+ '<i class="glyphicon glyphicon-pause" style="color: #d9534f;"></i></a>'
			+ '<a id="config_'+rows[i].id+'" href="javascript:;" onclick="showAction(this.id)">'
			+ '<i class="glyphicon glyphicon-cog"></i>'
			+ '</a>'
			+ '</div>'
			+ '</li>';
		}
		
		
		
		$(".inboxs-group").append(content);
	}
}

function sortInbox(id, title) {
	$("#sortList-text").html(title);
	// 获取id传给后台 按照要求进行排序操作
	loadInbox(id.split("_")[1]);
}

function setEndTime(id) {
	var endTime = $("#datetimepicker").val();
	var data = {
		id : id,
		endTime : endTime
	}
	$.ajax({
		url : getWebProjectName() + '/inbox!updateEndTime.action',
		type : 'POST',
		data:data,
		dataType:'json',
		success : function(data) {
			if (data.status) {
				// 设置成功后
				loadInbox();
				closeLayer();
				showSuccessMsg(data.msg);
			} else {
				showErrorMsg(data.msg);
			}
		}
	})	
}

function setCloseReason(id) {
	var reason = $("#close_reason").val();
	var data = {
		id : id,
		closeReason : reason
	}
	$.ajax({
		url : getWebProjectName() + '/inbox!closeInbox.action',
		type : 'POST',
		data:data,
		dataType:'json',
		success : function(data) {
			if (data.status) {
				// 设置成功后
				loadInbox();
				closeLayer();
				showSuccessMsg(data.msg);
			} else {
				showErrorMsg(data.msg);
			}
		}
	});
}

function openInbox(id) {
	var inbox_id = id.split("_")[1];
	// 确定开启后
	$.ajax({
		url : getWebProjectName() + '/inbox!openInbox.action?id='+inbox_id,
		type : 'POST',
		success : function(data) {
			if (data.status) {
				// 设置成功后
				loadInbox();
				closeLayer();
				showSuccessMsg(data.msg);
			} else {
				showErrorMsg(data.msg);
			}
		}
	});
}

function showLink(id) {
	// 这里的id 加上标号 link_id
	var inbox_id = id.split("_")[1];
	var url = 'http://localhost:8080'+getWebProjectName() +'/inbox.html?link=' +inbox_id;
	var data = '<h3 style="color: #000 ;margin-top: 0px;font-size: 16px;line-height: 20px;padding: 2px 15px;">收件地址</h3>'
			+ '<ul class="config-menu">'
			+ '<li class="divider"></li>'
			+ '<li>'
			+ '<div class="input-group">'
			+ '<input id="1234" type="text" value="'
			+ url
			+ '" class="form-control" readonly="readonly">'
			+ '<span class="input-group-btn">'
			+ '<button  onclick="copy()" class="btn btn-default" type="button">复制</button>'
			+ '</span>' + '</div>' + '</li>' + '</ul>';
	layer.open({
		content : [ data, '#' + id ],
		type : 4,
		area : [ '400px', ],
		shade : 0,
		zIndex : 1000,
		tips : [ 4, "#fff" ],
	});
}

function  showPwdLink(id){
	var password = id.split("_")[1];
	var data = '<h3 style="color: #000 ;margin-top: 0px;font-size: 16px;line-height: 20px;padding: 2px 15px;">收件密码</h3>'
		+ '<ul class="config-menu">'
		+ '<li class="divider"></li>'
		+ '<li>'
		+ '<div class="input-group">'
		+ '<input id="1234" type="text" value="'
		+ password
		+ '" class="form-control" readonly="readonly">'
		+ '<span class="input-group-btn">'
		+ '<button  onclick="copy()" class="btn btn-default" type="button">复制</button>'
		+ '</span>' + '</div>' + '</li>' + '</ul>';
		layer.open({
			content : [ data, '#' + id ],
			type : 4,
			area : [ '400px', ],
			shade : 0,
			zIndex : 1000,
			tips : [ 4, "#fff" ],
		});
}


function showEndTime(id, title) {
	var inbox_id = id.split("_")[1];
	var time = title.split(":")[1];
	var data = '<h3 style="color: #000 ;margin-top: 0px;font-size: 16px;line-height: 20px;padding: 2px 15px;">设置截至时间</h3>'
			+ '<ul class="config-menu">'
			+ '<li class="divider"></li>'
			+ '<li>'
			+ '<div class="input-group">'
			+ '<input  id="datetimepicker" type="text" value="'
			+ time
			+ '"  class="form-control">'
			+ '</div>'
			+ '</li>'
			+ '<li>'
			+ '<div class="input-group">'
			+ '<button onclick="closeLayer()" class="btn btn-default" type="button" style="margin-top:10px;margin-left:90px">取消</button>'
			+ '<button onclick="setEndTime(\''+ inbox_id+ '\')" class="btn btn-success" type="button" style="margin-top:10px;margin-left:10px">确定</button>'
			+ '</div>' + '</li>' + '</ul>';
	layer.open({
		content : [ data, '#' + id ],
		type : 4,
		area : [ '270px', ],
		shade : 0,
		zIndex : 1000,
		tips : [ 4, "#fff" ],
	});

	$('#datetimepicker').datetimepicker({
		lang : 'ch',
		timepicker : false,
		format : 'Y-m-d',
		minDate : '-1970/01/01',
		maxDate : getdateLastMonth(),
	});
}

function showPassWd(){
	var id = $(".config-menu").attr("id");
    var inbox_id = id.split("_")[1];
	var data = '<ul class="config-menu" style="margin: 20px 20px;list-style: none;">'
		+ '<li>'
		+ '<div class="input-group">'
		+ '<input  type="password"  id="inbox_pwd"  class="form-control">'
		+ '</div>'
		+ '</li>'
		+ '<li>'
		+ '<div class="input-group">'
		+ '<button onclick="closeLayer()" class="btn btn-default" type="button" style="margin-top:10px;margin-left:90px">取消</button>'
		+ '<button onclick="setInboxPwd(\''+ inbox_id+ '\')" class="btn btn-success" type="button" style="margin-top:10px;margin-left:10px">确定</button>'
		+ '</div>' + '</li>' + '</ul>';
		layer.open({
			content : data,
			type : 1,
			area : [ '270px', '170px'], // 宽
			zIndex : 1000,
			title : '设置收件夹密码'
		});
	
}



function showInboxForm() {
	$.ajax({
		url : 'inboxForm.html',
		type : 'get',
		dataType : 'html',
		success : function(data) {
			layer.open({
				content : data,
				type : 1,
				area : [ '630px', '550px' ], // 宽
				zIndex : 1000,
				title : '创建收件夹'
			});
		}
	});
}

function closeInbox(id) {
	var inbox_id = id.split("_")[1];
	var data = '<h3 style="color: #000 ;margin-top: 0px;font-size: 16px;line-height: 20px;padding: 2px 15px;">设置关闭原因</h3>'
			+ '<ul class="config-menu">'
			+ '<li class="divider"></li>'
			+ '<li style="height: 101px;">'
			+

			'<textarea  id="close_reason" type="text"  class="form-control" rows="3">收件已停止</textarea>'
			+ '<span style="color: #999;font-size: 12px;">自定义暂停/停止原因,会在收件页显示</span>'
			+ '</li>'
			+ '<li>'
			+ '<div class="input-group">'
			+ '<button onclick="closeLayer()" class="btn btn-default" type="button" style="margin-top:20px;margin-left:80px">取消</button>'
			+ '<button onclick="setCloseReason(\''+inbox_id+'\')" class="btn btn-danger" type="button" style="margin-top:20px;margin-left:10px">确定停止</button>'
			+ '</div>' + '</li>' + '</ul>';
	layer.open({
		content : [ data, '#' + id ],
		type : 4,
		area : [ '270px', '220px' ],
		shade : 0,
		zIndex : 1000,
		tips : [ 4, "#fff" ],
	});
}

function closeLayer() {
	layer.close(layer.index);
}

function copy() {
	// 这里的id 加上标号 input_id
	var e = document.getElementById(1234);
	e.select();
	document.execCommand("Copy");
	// 显示复制成功
	layer.close(layer.index);
	showSuccessMsg("复制成功")
}

function showSuccessMsg(msg) {
	layer.msg(msg, {
		time : 2000, // 20s后自动关闭
		icon : 1
	});
}

// 显示菜单操作
function showAction(id) {
	$.ajax({
		url : 'actions.html',
		type : 'get',
		dataType : 'html',
		success : function(data) {
			layer.open({
				content : [ data, '#' + id ],
				type : 4,
				area : [ '200px', '225px' ],
				shade : 0,
				zIndex : 1000,
				tips : [ 4, "#fff" ],
				success: function(layero, index){
					  $(".config-menu").attr("id",id);
				}
			});
		}
	});
}


function star(){
     var id = $(".config-menu").attr("id");
     var inbox_id = id.split("_")[1];
     $.ajax({
 		url : getWebProjectName() + '/inbox!star.action?id='+inbox_id,
 		type : 'POST',
 		success : function(data) {
 			if (data.status) {
 				// 设置成功后
 				loadInbox();
 				closeLayer();
 				showSuccessMsg(data.msg);
 			} else {
 				showErrorMsg(data.msg);
 			}
 		}
 	});
}

function cancelStar(){
	 var id = $(".config-menu").attr("id");
     var inbox_id = id.split("_")[1];
     $.ajax({
  		url : getWebProjectName() + '/inbox!cancelStar.action?id='+inbox_id,
  		type : 'POST',
  		success : function(data) {
  			if (data.status) {
  				// 设置成功后
  				loadInbox();
  				closeLayer();
  				showSuccessMsg(data.msg);
  			} else {
  				showErrorMsg(data.msg);
  			}
  		}
  	});
}

function deleteInbox(){
	var id = $(".config-menu").attr("id");
    var inbox_id = id.split("_")[1];
    $.ajax({
 		url : getWebProjectName() + '/inbox!delete.action?id='+inbox_id,
 		type : 'POST',
 		success : function(data) {
 			if (data.status) {
 				// 设置成功后
 				loadInbox();
 				closeLayer();
 				showSuccessMsg(data.msg);
 			} else {
 				showErrorMsg(data.msg);
 			}
 		}
 	});
}

function setInboxPwd(id){
	var password = $("#inbox_pwd").val();
	var data = {
		id : id,
		password : password
	};
	$.ajax({
		url : getWebProjectName() + '/inbox!updatePwd.action',
		type : 'POST',
		data:data,
		dataType:'json',
		success : function(data) {
			if (data.status) {
				// 设置成功后
				loadInbox();
				layer.closeAll();
				showSuccessMsg(data.msg);
			} else {
				showErrorMsg(data.msg);
			}
		}
	})	
}
/**
 * 获取下一月的当天
 */
function getdateLastMonth() {
	var now = new Date()
	y = now.getFullYear()
	m = now.getMonth() + 1 + 1
	d = now.getDate()
	m = m < 10 ? "0" + m : m
	d = d < 10 ? "0" + d : d
	return y + "/" + m + "/" + d
}

function showSuccessMsg(msg) {
	layer.msg(msg, {
		time : 2000, // 2s后自动关闭
		icon : 1
	});
}
function showErrorMsg(msg) {
	layer.msg(msg, {
		time : 2000, // 2s后自动关闭
		icon : 2
	});
}
/**
 * 得到当前项目名称
 */
function getWebProjectName() {
	var webProjectName = undefined;
	// 获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
	var pathName = window.document.location.pathname;
	// 获取带"/"的项目名，如：/uimcardprj
	webProjectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);

	return webProjectName;
}