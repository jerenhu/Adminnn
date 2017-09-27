<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/page/share/taglib.jsp"%>
<title>用户角色分配</title>
<body>
	<div class="easyui-layout" data-options="border:true,fit:true">
		<div data-options="region:'north',border:false" style="height:40px;padding-top:5px;">
			
			
			<form name="searchFmRole" id="searchFmRole" method="post" onsubmit="return false;">
	            <table class="search-tb">
	                 <tbody>
	                    <tr>
	                    	<td>&nbsp;&nbsp;&nbsp;名称/标识：
	                    		<input style="width:220px;" class="ipt" name="querycontent"/>
	                    	</td>
	                        <td>
								<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" id="searchBtnRole">查询</a>
	                        </td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<div data-options="region:'center',border:true,fit:true"  >
			<table id="roledg" data-options="border:true"></table>
		</div>
	</div>
</body>
</html>