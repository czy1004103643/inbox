//初始化大部分 文件后缀名
var audio = ['mp3','wav','ra','wma','mid'];
var video = ['avi','mpg','mov','swf'];
var reduce =['rar','zip','gz','z','arj'];
var text = ['txt','doc','html','pdf'];
var picture = ['bmp','gif','jpg','pic','png','tif','jpeg'];

$(function(){
	load();
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
 				console.info(data.rows);
 				loadTitle(data.rows.title);
 				loadDoc(data.rows.docs);
 				loadAction();
 			}
 		}
 	});
}
/**
 * 加载标题
 * @param title
 */
function loadTitle(title){
	$("#title").html(title);
}
function loadDoc(Docs){
	$("#docs").html(""); //清空文件
	for(var i =0;i < Docs.length;i++){
		var content = undefined;
		content += '<tr>'+
		'<td width="45"><input class="file_checkbox" id="'+Docs[i].id+'" type="checkbox"  autocomplete="off"></td>';
		if($.inArray(getFileExt(Docs[i].name), audio)!=-1){
			content +='<td><span class="glyphicon glyphicon-music"></span></td>';
		}else if($.inArray(getFileExt(Docs[i].name), video)!=-1){
			content +='<td><span class="glyphicon glyphicon-film"></span></td>';
		}else if($.inArray(getFileExt(Docs[i].name), reduce)!=-1){
			content +='<td><span class="glyphicon glyphicon-tasks"></span></td>';
		}else if($.inArray(getFileExt(Docs[i].name), text)!=-1){
			content +='<td><span class="glyphicon glyphicon-file"></span></td>';
		}else if($.inArray(getFileExt(Docs[i].name), picture)!=-1){
			content +='<td><span class="glyphicon glyphicon-picture"></span></td>';
		}
		content +='<td align="left" style="width: 400px;">'+Docs[i].name+'</td>'+
			'<td>'+Docs[i].size+'</td>'+
			'<td>'+Docs[i].createTime+'</td>'+
			'<td><a href="/inbox/download.action?fileUrl='+Docs[i].url+'&fileName='+Docs[i].name+'"><span class="glyphicon glyphicon-download-alt"></span>下载</a></td>'+
		'</tr>';
		$("#docs").append(content);
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

function getFileExt(fileName){
	var index1 = fileName.lastIndexOf(".");
	var index2 = fileName.length;
	var ext =  fileName.substring(index1+1,index2);
	return ext;
}

function loadAction(){
	$("#check_all").bind('click',function(){
		var flag = $(this).is(":checked");
		var inputs = $("input[class='file_checkbox']");
		if(flag){
			//全选
			for(var i = 0;i < inputs.length;i++){
					inputs[i].checked = true;
			}
			$("#fileName").html("已选中"+inputs.length+"个文件");
			//修改样式
			$("table tbody tr").addClass("checkActive");
		}else{
			//全不选
			for(var i = 0;i < inputs.length;i++){
					inputs[i].checked = false;
			}
			$("#fileName").html("文件名称");
			$("table tbody tr").removeClass("checkActive");
		}
	})

	$(".file_checkbox").bind('click',function(){
		var flag = $(this).is(":checked");
		var inputs = $("input[class='file_checkbox']");
		var num = checkNum();
		if(flag){
			//选中该tr
			$(this).parent("td").parent("tr").addClass("checkActive");
			if(num==inputs.length){
				$("input[id='check_all']")[0].checked = true;
				$("#fileName").html("已选中"+num+"个文件");
			}else{
				$("#fileName").html("已选中"+num+"个文件");
			}
		}else{
			$(this).parent("td").parent("tr").removeClass("checkActive");
			if(num==0){
				$("input[id='check_all']")[0].checked = false;
				$("#fileName").html("文件名称");
			}else{
				$("input[id='check_all']")[0].checked = false;
				$("#fileName").html("已选中"+num+"个文件");
			}
		}
	})
}


//返回当前选中的文件数
function checkNum(){
	var inputs = $("input[class='file_checkbox']");
	var j = 0;
	for(var i = 0;i < inputs.length;i++){
		if(inputs[i].checked){
			j++;
		}
	}
	return j;
}

function delInboxFile(){
	var size =$("input").is(":checked").length; //选择的数量
	console.info(size);
}
