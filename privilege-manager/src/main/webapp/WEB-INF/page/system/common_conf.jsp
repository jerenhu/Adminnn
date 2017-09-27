<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/page/share/taglib.jsp"%>
<%@ include file="/WEB-INF/page/share/dosearch.jspf"%>
<script type="text/javascript"
	src="${basePath}/assets/js/upload/jquery.uploader.js"></script>
<c:set var="systemSn" value="privilege" scope="page" />
<c:set var="nameSpace" value="loginlog" scope="page" />
<title>上传图片</title>
<style type="text/css">
.uploadButton{
	padding: 0;
	margin: 0;
	border-radius: 5px;
	background-color: #660;
	color: #FFF;
	width: 80px;
	height: 40px;
	border: none;
	vertical-align: middle;
}

.uploadButton.disabled {
	background-color: #999;
}

#loading {
	vertical-align: middle;
	background-color: #FF9;
}
.uploadButton{
	width: 75px;
    height: 30px;
    background:#4684BE;
    
}
</style>
</head>
<body class="easyui-layout">
	<!-- <div data-options="region:'north',border:true" class="toolbar-region">
		抬头  开始
		<div id="toolbar" class="easyui-toolbar">
		</div> 
		抬头  结束
	</div> -->
	<div data-options="region:'center',border:false">
		<!--subLayout start-->
		<div class="easyui-layout" data-options="" id="subLayout">

			<div id="commonPanel" style="margin:20px;" class="easyui-panel"
				data-options="title:'个性化图标',cls:'mt20',plain:true,fitWidth:true">
				<table class="form-tb">
					<tbody>
						<tr>
							<td style="width: 240px;margin-bottom:10px;">
								<a id="uploadLogo" class="easyui-linkbutton" href="javascript:;" iconCls="icon-edit" plain="false" >修改Logo</a>
								（图片大小420×60）
							</td>
						</tr>
						<tr>
							<th >
								<img id="jsLogoImg" width="420" height="60" alt="" src="${basePath }/assets/images/ec-logo.png">
		        				<span id="loading" style="display:none;">Loading…</span>
		        			</th>
						</tr>
						<tr>
							<td >
	        					<a style="margin-top:25px" id="uploadFavicon" class="easyui-linkbutton" href="javascript:;" iconCls="icon-edit" plain="false"  >修改Favicon</a>
							</td>
						</tr>
						<tr>
							<th style="text-align:left">
								<img id="jsIconImg" width="32" height="32" alt="" src="${basePath }/images/favicon.ico">
	        					<span id="loading" style="display:none;">Loading…</span>
							</th>
						</tr>
					</tbody>
				</table>
			</div>
			<div id="commonPanel" style="margin:20px" class="easyui-panel"
				data-options="title:'个性化配置',cls:'mt20',plain:true,fitWidth:true">
				<table class="form-tb">
					<tbody>
							<tr>
								<td>
		        					<a style="margin-top:25px" id="uploadCompany" class="easyui-linkbutton" href="javascript:;" iconCls="icon-edit" plain="false" onclick="updateInfo('companyName')">修改</a>
								</td>
							</tr>
							<tr>
								<th>
									修改公司名称：<input type="text" id="companyName" class="ipt easyui-validatebox" style="width:300px;" value="${companyName}">
								</th>
							</tr>
							<tr>
								<td>
									<a style="margin-top:25px" id="uploadCompany" class="easyui-linkbutton" href="javascript:;" iconCls="icon-edit" plain="false" onclick="updateInfo('plainName')">修改</a>
								</td>
							</tr>
							<tr>
							<th >
								修改平台名称：<input type="text" id="plainName" class="ipt easyui-validatebox" style="width:300px;" value="${plainName}">
							</th>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
<SCRIPT LANGUAGE="JavaScript">
$(function () {
	//上传logo
	$('#uploadLogo').uploader({
	    url : '${basePath}/managment/system/systemConfig/uploadImage.do?sessionId=${sessionId}',
	    inputName : 'filedata',
	    data : {
	    	filename : 'logo'
	    },
	    onStart : function (data) {
	        $('#loading').show();
	        this.$button.addClass('disabled');
	    },
	    onComplete : function (data) {
	    	data = eval("("+data+")");
	        $('#loading').hide();
	        this.$button.removeClass('disabled');
	        setTimeout(function(){
	        	var src = $("#jsLogoImg").attr("src")+"?"+(new Date().getTime());
	        	$("#jsLogoImg").attr("src", src);
	        	$('.header_nav_index', window.parent.document).css("background-image", "url("+src+")");
	        }, 200);
	    }
	});
	
	
	
	//上传Favicon
	$('#uploadFavicon').uploader({
	    url : '${basePath}/managment/system/systemConfig/uploadImage.do?sessionId=${sessionId}',
	    inputName : 'filedata',
	    data : {
	    	filename : 'favicon'
	    },
	    onStart : function (data) {
	        $('#loading').show();
	        this.$button.addClass('disabled');
	    },
	    onComplete : function (data) {
	        $('#loading').hide();
	        this.$button.removeClass('disabled');
	        setTimeout(function(){
		        var src = $("#jsIconImg").attr("src")+"?"+(new Date().getTime());
	        	$("#jsIconImg").attr("src", src);
	        },200);
	    }
	});
});

function updateInfo(name) {
	var key,sysValue;
	if(name=='plainName') {
		key = "plain_name";
		sysValue = $("#plainName").val();
	}else {
		key = "company_name";
		sysValue = $("#companyName").val();
	}
	var _url = "${basePath}/managment/system/systemConfig/updatePerson.do?sessionId==${sessionId}";
	$.post(_url, {configKey:key,configValue:sysValue},
			function(data) {
				if (data.responseCode == 100) {
					showSuc('操作成功');
				}else {
					showWarn("操作失败,请联系系统管理员");
				}
		}, 'json');
}
</SCRIPT>
</html>