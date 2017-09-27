<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/page/share/taglib.jsp"%>
<%-- 查询js --%>
<%@ include file="/WEB-INF/page/share/dosearch.jspf"%>
<c:set var="systemSn" value="privilege" scope="page"/>
<c:set var="nameSpace" value="company" scope="page"/>
<title>公司管理</title>
<script type="text/javascript">
var basePath = "${basePath}";
var _jsSessionId="${sessionId}";
</script>
<script type="text/javascript" language="javascript" src="${basePath}/resources/assets/js/privilege/company.js"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false">
		<div id="toolbar" class="easyui-toolbar">
			<%-- 重置，刷新 --%>
			<%@ include file="/WEB-INF/page/share/toolbar_common.jspf"%>
			<c:if test="${my:hasPermission(sessionId,systemSn,nameSpace, 0)}">
			<a href="javascript:void(0)"  iconCls="icon-add" plain="true" onclick="company.addUI()">添加</a>
			<a>-</a>
			</c:if>
			<c:if test="${my:hasPermission(sessionId,systemSn,nameSpace, 2)}">
			<a href="javascript:void(0)"  iconCls="icon-edit" plain="true" onclick="company.updateUI()">修改</a>
			<a>-</a>
			</c:if>
			<c:if test="${my:hasPermission(sessionId,systemSn,nameSpace, 3)}">
			<a href="javascript:void(0)"  iconCls="icon-del" plain="true" onclick="company.del()">删除</a>
			<a>-</a>
			</c:if>
		</div>
		<c:if test="${my:hasPermission(sessionId,systemSn,nameSpace, 1)}">
		<!--搜索start	-->
		<div class="search-div">
			<form name="searchForm" id="searchFm" action="" method="post" onsubmit="return false;">
				<table class="search-tb">
					<col width="60" />
					<col />
					<col width="80" />
					<col />
					<col width="80" />
					<col />
					<tbody>
						<tr>
							<th>名称 ：</th>
							<td>
								<input class="ipt jsClearValue" name="cname" style="width: 150px;" autocomplete="off"/>
								<a iconCls="icon-search" class="easyui-linkbutton ml10" id="searchBtn" data-options="iconCls:'icon-search'">查询</a>
							</td>
							<%--
							<th>状态：</th>
							<td>
								<select id="cc" class="easyui-combobox" name="status" style="width: 100px;">
										<option value="">全部</option>
										<option value="1">启用</option>
										<option value="0">禁用</option>
								</select>
							</td>
							<th>时间：</th>
							<td><input class="ipt easyui-datebox w80 jsClearValue" name="startTime" style="width: 140px;" data-options="required:false,missingMessage:'时间不能为空！',dateFmt:'yyyy-MM-dd HH:mm:ss'" autocomplete="off"/> 到 
								<input class="ipt easyui-datebox w80 jsClearValue" name="endTime" style="width: 140px;" data-options="required:false,missingMessage:'时间不能为空！',dateFmt:'yyyy-MM-dd HH:mm:ss'" autocomplete="off"/>
							</td>
							<th>删除状态：</th>
							<td>
								<select id="cc" class="easyui-combobox" name="delFlag" style="width: 100px;">
										<option value="">全部</option>
										<option value="1" selected>未删除</option>
										<option value="0">已删除</option>
								</select> 
								
							</td>
							 --%>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!--搜索end-->
		</c:if>
	</div>
	<!--列表start-->
	<div data-options="region:'center',border:false">
		<table id="dg" class="easyui-datagrid" title="" style="width:700px;height:250px"
			data-options="collapsible : true,rownumbers:true,pagination : true,pageSize : 20,pageList : [ 20, 50,100 ],
							url :'${basePath}/managment/privilege/company/ajaxList.do?sessionId=${sessionId}', method : 'post',singleSelect : false">
			<thead>
				<tr>
					<th data-options="field:'id',checkbox:true"></th>
					<th data-options="field:'cname',width : 150,align:'left'">公司名称</th>
					<th data-options="field:'code',width : 150,align:'left'">公司编码</th>
					<%-- 
					<th data-options="field:'descr',width : 150,align:'left'">描述</th>
					<th data-options="field:'creator',width : 150,align:'left'">创建时间</th>
					<th data-options="field:'createTime',width : 150,align:'left'"></th>
					<th data-options="field:'updator',width : 150,align:'left'"></th>
					<th data-options="field:'updateTime',width : 150,align:'left'"></th>
					<th data-options="field:'delFlag',width : 150,align:'left',formatter : company.delFlagFormatter,styler : company.delFlagStyler">删除状态</th>
					--%>
				</tr>
			</thead>
		</table> 
	</div>
	<!--列表end-->
</body>
</html>
