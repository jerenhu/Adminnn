<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/page/share/taglib.jsp"%>
<title>授权</title>
</head>
<body >
	<%-- 这里的style不要放在head中，否则没有效果，要放在body中才行 --%>
	<style type="text/css">
		.select-box {
			display: inline-block;
			font-weight: normal;
			margin-right: 0px;
			padding: 0 0 0 3px;
			word-break: keep-word;
		}
	</style>
	<input type="hidden" value="${systemId }" id="jsCurrSystemId"/>
	<div class="easyui-layout" data-options="fit:true,border:false" >
		<div data-options="region:'west',title:'系统列表',collapsible:false,border:true" style="width:180px;border:1px">
	    	<ul id="systemMenu"></ul>
	    </div>
	    <div class="easyui-layout"  data-options="region:'center',border:true ">
	    	<div class="easyui-layout" data-options="border:false, fit:true" >
		    	<div data-options="region:'north',border:false" style="height:39px;overflow:hidden;">
		    		<div id="selectAllBtn" style="padding-top:6px;">
		    			<!-- 
						<input class="btn btnplan" type="button" value="全选" style="cursor: pointer;"/>&nbsp;&nbsp;
						<input class="btn btnplan" type="button" value="取消" style="cursor: pointer;"/>
						-->
						<a  class="easyui-linkbutton btn btnplan selectAllBtn  " iconCls="icon-ok" plain="false" style="cursor: pointer;width:60px ;float:left;margin-left:20px">全选</a>&nbsp;&nbsp;
						<a class="easyui-linkbutton btn btnplan selectAllBtn "  iconCls="icon-cancel"  plain="false"  style="cursor: pointer;width:60px;float:left;margin-left:15px">取消</a>
					</div>
				</div>
				<div id="jsRoleAcl" data-options="region:'center'">
		    		<table id="rmtg"></table>
		    	</div>
	    	</div>
	    </div>
    </div>
</body>
</html>