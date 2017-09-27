<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/page/share/taglib.jsp"%>
<%-- 查询js --%>
<%@ include file="/WEB-INF/page/share/dosearch.jspf"%>
<c:set var="systemSn" value="privilege" scope="page"/>
<c:set var="nameSpace" value="role" scope="page"/>
<title>职责管理</title>
<script type="text/javascript" language="javascript" src="${basePath}/resources/assets/js/privilege/role.js"></script>
<style>
	.jsPersonRoleStyle .keep-style{margin-right:8px;}
	.jsPersonRoleStyle .keep-style:hover .jsDelRoleUser{visibility:visible;}
	.jsDelRoleUser{visibility:hidden;display:inline-block;text-align: center;color: red;cursor: pointer;width: 12px;border: 1px solid red;border-radius: 12px;height: 12px;line-height: 12px;font-size:14px;font-weight:bold;}
	.js-role-name{border: 1px solid #CBCBCB; width: 478px;line-height: 26px;height: 26px;overflow: hidden;text-indent:4px;}
	.js-role-name>div{display: inline-block;line-height: 26px;position: relative;float: left;}
	.js-role-name>div>label{    display: inline-block;line-height: 26px;position: relative;float: left}
	
	.js-role-name input{border:0;float:left;}
</style>
</head>
<body class="easyui-layout">
	<div data-options="region:'west',title:'组织列表',split:true" style="width:200px;">
    	<!--<ul id="company"></ul>-->
    	<ul id="dept" class="ztree"></ul>
    </div>   
    <div data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true"> 
			<div data-options="region:'north',border:false">
				<div id="toolbar" class="easyui-toolbar">
					<%-- 重置，刷新 --%>
					<%@ include file="/WEB-INF/page/share/toolbar_common.jspf"%>
					<c:if test="${my:hasPermission(sessionId,systemSn,nameSpace, 0)}">
						<a href="javascript:;" iconCls="icon-add" plain="true" id="jsAddRole" >添加</a>
						<a>-</a>
					</c:if>
					<c:if test="${my:hasPermission(sessionId,systemSn,nameSpace, 2)}">
						<a href="javascript:;" iconCls="icon-edit" plain="true" disabled="true" id="jsUpdateRole" >修改</a>
						<a>-</a>
					</c:if>
					<%-- <c:if test="${my:hasPermission(sessionId,systemSn,nameSpace, 3)}">
						<a href="javascript:;" iconCls="icon-del" plain="true" id="jsDelRole" >删除</a>
						<a>-</a>
					</c:if> --%>
					<c:if test="${my:hasPermission(sessionId,systemSn,nameSpace, 2)}">
						<a href="javascript:;" iconCls="icon-start" plain="true" disabled="true" id="jsChangeValidState">启用</a>
						<a>-</a>
					</c:if>
					<c:if test="${my:hasPermission(sessionId,systemSn,nameSpace, 6)}">
						<a href="javascript:;" iconCls="icon-operate" plain="true" disabled="true" id="jsSettingRoleAcl" >操作授权</a>
						<a>-</a>
					</c:if>
					<%--
					<c:if test="${my:hasPermission(sessionId,systemSn,nameSpace, 6)}">
						<a href="javascript:;" iconCls="icon-sync" plain="true" onclick="role.settingCompany()">分配组织</a>
						<a>-</a>
					</c:if>
					
					<c:if test="${my:hasPermission(sessionId,systemSn,nameSpace, 6)}">
						<a href="javascript:;" iconCls="icon-sync" plain="true" onclick="role.syncAllFromUcenter()">同步MDM职责</a>
						<a>-</a>
					</c:if>
					<c:if test="${my:hasPermission(sessionId,systemSn,nameSpace, 6)}">
						<a href="javascript:;" iconCls="icon-sync" plain="true" onclick="role.syncAllUserRoleFromUcenter()">同步MDM用户职责</a>
						<a>-</a>
					</c:if>
					 --%>
				</div>
				<div style="padding:10px;">
				<form name="searchFm" id="searchFm" method="post" onsubmit="return false;">
                   	<%-- 组织id --%>
					<input type="hidden" name="companyId" id="jsCompanyIdHide"/>
	                <table class="search-tb">
		                <tbody>
		                    <tr>
		                    	<td>所属系统：
		                    		<select id="jsAllSystemCombo" data-options="editable:false" name="systemId" style="width:120px;">   
									</select>  
		                    	</td>
		                    	<td>名称/标识：
		                    		<input class="ipt" style="width:220px" name="querycontent" id="js_querycontent"/>
		                    	</td>
		                        <td>
									<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" id="jsSearchBtn">查询</a>
		                        </td>
							</tr>
						</tbody>
					</table>
				</form>
				</div>
			</div>
			<div data-options="region:'center',border:true">
				<table id="dg">
				</table>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	var roleLevelsJson = ${roleLevelsJson};
	var systemsJson = ${systemsJson};
	var basePath = '${basePath}';
	var nmSpace = '${nameSpace}';
	$(function(){
		role.init();
	});
	$.parser.parse();
</script>
</html>