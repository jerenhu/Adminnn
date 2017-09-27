<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="/WEB-INF/page/share/taglib.jsp" %>
<title>添加模块权限值</title>
</head>
<body>
	<%-- 这里的style不要放在head中，否则没有效果，要放在body中才行 --%>
	<style type="text/css">
		#formA .select-box {
			display: inline-block;
			font-weight: normal;
			word-break: keep-word;

			border:1px solid #ccc;
			padding:1px 5px;
			margin:2px;
		}
		#formA .select-box:hover{
			background:#eee;
		}
		#formA .select-box span {
			margin-left: 1px;
		}
	</style>
	
	<div class="tb_class" >
		<!-- <input cls="icon-ok" class="easyui-linkbutton btn btnplan selectAllBtn " type="button" value="全选" style="cursor: pointer;"/> -->
		<a  class="easyui-linkbutton btn btnplan selectAllBtn  " iconCls="icon-ok" plain="false" style="cursor: pointer;width:60px ;float:left;margin-left:20px">全选</a>&nbsp;&nbsp;
		<!-- <input class="easyui-linkbutton btn btnplan selectAllBtn "  iconCls="icon-ok"  plain="false" type="button" value="取消" style="cursor: pointer;"/> -->
		<a class="easyui-linkbutton btn btnplan selectAllBtn "  iconCls="icon-remove"  plain="false"  style="cursor: pointer;width:60px;float:left;margin-left:15px">取消</a>
	</div>
	
	<div data-options="region:'center'" style="width:100%;height:170px;overflow-y:scroll;overflow-x:hidden;padding:10px 0 10px 0;">
		<form id="formA" method="post">
	        <div style="margin:0 20px;">
		        <c:choose >
		        	<c:when test="${priVals.size() > 0}">
		        		<c:forEach items="${priVals}" var="pval" varStatus="status">
				        	<label class="select-box">
				        		<input type="checkbox" name="pvs" value="${pval.position }" />
				        		<span>${pval.name}</span>
				        	</label>
				        	<%-- 6个换一行 
				        	<c:if test="${(status.index+1)%6==0}">
				        	<br />
				        	</c:if>
				        	--%>
				        </c:forEach>
		        	</c:when>
		        	<c:otherwise>
		        		<span class="cgray">无可用操作权限</span>
		        	</c:otherwise>
		        </c:choose>
	        	
	        </div>
	    </form>
    </div>
</body>
</html>