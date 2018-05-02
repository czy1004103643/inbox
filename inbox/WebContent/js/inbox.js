$(function(){
	load()
})

function getInboxId(){
	var urlParam = window.location.search;
    inbox_id = urlParam.split("=")[1];
    return inbox_id;
}

function load(){
    $.ajax({
 		url : getWebProjectName() + '/inbox!getInboxById.action?id='+getInboxId(),
 		type : 'POST',
 		success : function(data) {
 			if (data.status) {
 				// 设置成功后
 				loadInfo(data.rows);
 			}
 		}
 	});
}

function loadInfo(rows){
	//加载logo
	$(".inbox-logo").html('<img id="logo" src="'+getWebProjectName()+"/inbox!getLogo.action?logo="+rows.logo+'" class="img-circle" style="width: 100px;height: 100px;"/>');
	//加载标题
	$(".inbox-title").html(rows.title);
	if(rows.password==null||rows.password==""){
		if(rows.status==0){
			//加载备注
			$("#remark").html(rows.remark)
		}else{
			//加载备注
			$("#remark").html(rows.closeReason);
			$(".inbox-footer").css('display','none');
		}
	}else{
		$("#remark").html("该收件夹已经加密,请输入密码然后上传文件");
		$(".inbox-footer").css('display','none');
		$("#pwdText").css('display','block');
	}
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


$("#file-upload").fileinput({
			language: 'zh', //设置语言
			uploadUrl: getWebProjectName()+"/doc!upload.action?linkId="+getInboxId(), //上传的地址
			allowedFileExtensions: [], //接收的文件后缀
			uploadAsync: true, //默认异步上传
			showUpload: true, //是否显示上传按钮
			showRemove: true, //显示移除按钮
			showPreview: true, //是否显示预览
			showCaption: true, //是否显示标题
			dropZoneEnabled: true,//是否显示拖拽区域
			browseClass: "btn btn-primary", //按钮样式 
			maxFileSize: 225280,//单位为kb，如果为0表示不限制文件大小 限制上传大小为220M
			maxFileCount: 10, //表示允许同时上传的最大文件个数
			enctype: 'multipart/form-data',	
});

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

function checkPassword(){
	var password = $("#password").val();
	var id = getInboxId()
	var data ={
			id:id,
			password:password
	}
	 $.ajax({
	 		url : getWebProjectName() + '/inbox!checkPassword.action',
	 		type : 'POST',
	 		data:data,
	 		success : function(data) {
	 			if (data.status) {
	 				showSuccessMsg("验证成功");
	 				$("#pwdText").css('display','none');
	 				if(data.rows.status==0){
	 					//加载备注
	 					$("#remark").html(data.rows.remark)
	 					$(".inbox-footer").css('display','block');
	 				}else{
	 					//加载备注
	 					$("#remark").html(data.rows.closeReason);
	 					$(".inbox-footer").css('display','none');
	 				}
	 			}else{
	 				showErrorMsg("密码错误，请重新输入");
	 			}
	 		}
	 });
}


$("#file-upload").on("fileuploaded",function(event,data,previewId,index){
	if(data.response.status){
		showSuccessMsg(data.response.msg);
	}
})