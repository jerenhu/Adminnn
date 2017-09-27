<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("basePath",basePath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>${companyName}${plainName}</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <link rel="shortcut icon" href="${basePath}/images/favicon.ico" type="image/x-icon" />
    <link href="${basePath}/assets/css/base.css" rel="stylesheet" />
    <script src="${basePath}/resources/assets/boot.js?ver=${jscssvar}" type="text/javascript"></script>
</head>
<body>
<div>权限系统桌面</div>
</body>
</html>
