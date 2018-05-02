<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<form id="loginForm" class="form-horizontal" style="width: 400px;text-align: center;">
  <div class="form-group" style="margin-top: 30px;">
    <div class="col-sm-10 col-sm-offset-1">
      <input type="text" style="height: 40px;" name="name" class="form-control"  placeholder="用户名或手机" required="required" autocomplete="off">
    </div>
  </div>
  <div class="form-group" style="margin-top: 20px;">
    <div class="col-sm-10 col-sm-offset-1">
      <input type="password" style="height: 40px;" name="password" class="form-control"  placeholder="输入密码" required="required" autocomplete="off">
    </div>
  </div>
  <div class="form-group" style="margin-top: 20px;">
    <div class="col-sm-10 col-sm-offset-1">
      <input type="submit" style="height: 40px;" class="btn btn-primary form-control" value="登录" required="required" >
    </div>
  </div>
  <div class="form-group" style="margin-top: 20px;">
    <div class="col-sm-10 col-sm-offset-1">
     <p>还没有账号？<a href="javascript:;">立即注册</a></p>
    </div>
  </div>
</form>
<script>
	
	

	$("#loginForm").submit(function(e) {
		$.ajax({
			url : '${pageContext.request.contextPath}/user!login.action',
			type : 'POST',
			datatype : 'JSON',
			data : $('#loginForm').serialize(),
			success : function(data) {
				if(data.status){
					showSuccessMsg(data.msg);
					layer.close(layer.index);
					window.location.href = getWebProjectName() +'/main.jsp';
				}else{
					showErrorMsg(data.msg);
				}
			}
		})
		return false;
	});

	
	function showSuccessMsg(msg) {
		layer.msg(msg, {
			time : 2000, //2s后自动关闭
			icon : 1
		});
	}
	function showErrorMsg(msg) {
		layer.msg(msg, {
			time : 2000, //2s后自动关闭
			icon : 2
		});
	}
	/**
	 * 得到当前项目名称
	 */
	function getWebProjectName() {
		var webProjectName = undefined;
		//获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
		var pathName = window.document.location.pathname;
		//获取带"/"的项目名，如：/uimcardprj
		webProjectName = pathName.substring(0,
				pathName.substr(1).indexOf('/') + 1);

		return webProjectName;
	}
</script>

</body>
</html>