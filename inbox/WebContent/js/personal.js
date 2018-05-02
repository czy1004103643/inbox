function showCont(id) {
	$(".showcont").hide();
	$("#" + id).show();
}

$(".list-group-item").bind('click', function() {
	var obj = $(this);
	$(".list-group-item").removeClass('liActive');
	obj.addClass('liActive');
})

$("#newPassword")
		.keyup(
				function() {
					var strongRegex = new RegExp(
							"^(?=.{14,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$",
							"g");
					var mediumRegex = new RegExp(
							"^(?=.{10,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$",
							"g");
					var enoughRegex = new RegExp("(?=.{6,}).*", "g");
					if (false == enoughRegex.test($(this).val())) {
						$("#password_Tips2")
								.html(
										"<span class='safe_1'>弱</span><span class='safe_1'>中</span><span class='safe_1'>强");
						$("#password_Tips2").removeClass('strong');
						$("#password_Tips2").removeClass('medium');
						$("#password_Tips2").removeClass('enough');
						$("#password_Tips2").addClass('default');
					} else if (strongRegex.test($(this).val())) {
						$("#password_Tips2").removeClass('medium');
						$("#password_Tips2").removeClass('enough');
						$("#password_Tips2").removeClass('strong');
						$("#password_Tips2").addClass('strong');

					} else if (mediumRegex.test($(this).val())) {
						$("#password_Tips2").removeClass('strong');
						$("#password_Tips2").removeClass('enough');
						$("#password_Tips2").removeClass('medium');
						$("#password_Tips2").addClass('medium');

					} else {
						$("#password_Tips2").removeClass('strong');
						$("#password_Tips2").removeClass('medium');
						$("#password_Tips2").removeClass('enough');
						$("#password_Tips2").addClass('enough');
					}
					return true;
				});

// 检查两次密码是否一致
function checkPwd() {
	var pwd = $("input[name='newPassword']").val();
	var pwdAgain = $("input[name='newPasswordAgain']").val();
	if (pwd != pwdAgain) {
		showErrorMsg("两次输入密码不一致")
		return false;
	}
	return true;
}

// 异步提交表单
$("#frmChangePwd").submit(function(e) {
	//检验
	if(checkPwd()){
		$.ajax({
			url : getWebProjectName()+'/user!changePwd.action',
			type : 'POST',
			datatype : 'JSON',
			data : $('#frmChangePwd').serialize(),
			success : function(data) {
				if (data.status) {
					showSuccessMsg(data.msg);
				} else {
					showErrorMsg(data.msg);
				}
			}
		})
	}
	
	return false;
});

$("#frmChangeImg").submit(function(e) {
	//检验
	var form = new FormData(document.getElementById("frmChangeImg"));
	$.ajax({
		url : getWebProjectName()+'/user!changeHeadImg.action',
		type : 'POST',
		data : form,
		processData:false,
        contentType:false,
		success : function(data) {
			if (data.status) {
				showSuccessMsg(data.msg);
				//异步刷新界面图片
				$("#userHeadImg").attr("src",getWebProjectName()+"/user!getHeadImg.action?headImg="+data.headImg);
				$("#Img").attr("src",getWebProjectName()+"/user!getHeadImg.action?headImg="+data.headImg);
			} else {
				showErrorMsg(data.msg);
			}
		}
	})
	
	
	return false;
});


function uploadHeadImg() {
	$("input[name='uploadFile']").click();
}
function change_headImg() {
	PreviewImage($("input[name='uploadFile']")[0], 'Img', 'Imgdiv');
}

function PreviewImage(fileObj, imgPreviewId, divPreviewId) {
	var allowExtention = ".jpg,.bmp,.gif,.png,.jpeg";// 允许上传文件的后缀名document.getElementById("hfAllowPicSuffix").value;
	var extention = fileObj.value.substring(fileObj.value.lastIndexOf(".") + 1)
			.toLowerCase();
	var browserVersion = window.navigator.userAgent.toUpperCase();
	if (allowExtention.indexOf(extention) > -1) {
		if (fileObj.files) {// HTML5实现预览，兼容chrome、火狐7+等
			if (window.FileReader) {
				var reader = new FileReader();
				reader.onload = function(e) {
					document.getElementById(imgPreviewId).setAttribute("src",
							e.target.result);
				}
				reader.readAsDataURL(fileObj.files[0]);
			} else if (browserVersion.indexOf("SAFARI") > -1) {
				alert("不支持Safari6.0以下浏览器的图片预览!");
			}
		} else if (browserVersion.indexOf("MSIE") > -1) {
			if (browserVersion.indexOf("MSIE 6") > -1) {// ie6
				document.getElementById(imgPreviewId).setAttribute("src",
						fileObj.value);
			} else {// ie[7-9]
				fileObj.select();
				if (browserVersion.indexOf("MSIE 9") > -1)
					fileObj.blur();// 不加上document.selection.createRange().text在ie9会拒绝访问
				var newPreview = document.getElementById(divPreviewId + "New");
				if (newPreview == null) {
					newPreview = document.createElement("div");
					newPreview.setAttribute("id", divPreviewId + "New");
					newPreview.style.width = document
							.getElementById(imgPreviewId).width
							+ "px";
					newPreview.style.height = document
							.getElementById(imgPreviewId).height
							+ "px";
					newPreview.style.border = "solid 1px #d2e2e2";
				}
				newPreview.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src='"
						+ document.selection.createRange().text + "')";
				var tempDivPreview = document.getElementById(divPreviewId);
				tempDivPreview.parentNode.insertBefore(newPreview,
						tempDivPreview);
				tempDivPreview.style.display = "none";
			}
		} else if (browserVersion.indexOf("FIREFOX") > -1) {// firefox
			var firefoxVersion = parseFloat(browserVersion.toLowerCase().match(
					/firefox\/([\d.]+)/)[1]);
			if (firefoxVersion < 7) {// firefox7以下版本
				document.getElementById(imgPreviewId).setAttribute("src",
						fileObj.files[0].getAsDataURL());
			} else {// firefox7.0+
				document.getElementById(imgPreviewId).setAttribute("src",
						window.URL.createObjectURL(fileObj.files[0]));
			}
		} else {
			document.getElementById(imgPreviewId).setAttribute("src",
					fileObj.value);
		}
	} else {
		alert("仅支持" + allowExtention + "为后缀名的文件!");
		fileObj.value = "";// 清空选中文件
		if (browserVersion.indexOf("MSIE") > -1) {
			fileObj.select();
			document.selection.clear();
		}
		fileObj.outerHTML = fileObj.outerHTML;
	}
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