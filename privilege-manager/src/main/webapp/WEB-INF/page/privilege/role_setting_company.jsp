<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="/WEB-INF/page/share/taglib.jsp" %>
<title>角色分配公司</title>
</head>
<body>
<script>
	var roleCompanysJson = ${roleCompanysJson};
</script>
<div id="JSsettingCompany" class="easyui-layout" data-options="fit:true" >   
    <div data-options="region:'center'" >
    	<table id="jsCompanyDg" ></table>  
    </div>   
</div> 
</body>
</html>