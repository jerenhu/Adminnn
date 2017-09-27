<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/page/share/taglib.jsp"%>
<%-- 查询js --%>
<%@ include file="/WEB-INF/page/share/dosearch.jspf"%>
<c:set var="systemSn" value="privilege" scope="page"/>
<c:set var="nameSpace" value="user" scope="page"/>
<title>设置有效期</title>
</head>
<body class="easyui-layout">   
    <div id="win" class="easyui-window" title="设置有效期" style="width:300px;height:180px;">
		<div style="padding:10px 20px 10px 40px;">
			<p>请选择日期: <input id="fdate" type="text"></p>
			<div style="padding:5px;text-align:center;">
				<a href="#" class="easyui-linkbutton" icon="icon-ok">确定</a>
				<a href="#" class="easyui-linkbutton" icon="icon-cancel">取消</a>
			</div>
		</div>
	</div>
</body>
<script>
	
</script>
</html>