
function showlogin(){
	$.ajax({
		url:getWebProjectName()+ '/login.jsp',
		type: 'get',
		dataType: 'html',
		success: function(data) {
			layer.open({
				content: data,
				type: 1,
				area: ['415px','300px' ], //宽
				zIndex: 1000,
				title: '登录',
				success: function (layer) {
				      var btn = layer.find('.layui-layer-title');
				      btn.css('text-align', 'center');
				}
			});
		}
	});
}

function showregister(){
	$.ajax({
		url: getWebProjectName()+'/register.jsp',
		type: 'get',
		dataType: 'html',
		success: function(data) {
			layer.open({
				content: data,
				type: 1,
				area: ['515px','330px' ], //宽
				zIndex: 1000,
				title: '注册',
				success: function (layer) {
				      var btn = layer.find('.layui-layer-title');
				      btn.css('text-align', 'center');
				}
			});
		}
	});
}
function getWebProjectName(){
	var webProjectName = undefined;
	//获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName=window.document.location.pathname;
	//获取带"/"的项目名，如：/uimcardprj
    webProjectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    
	return webProjectName;
}

