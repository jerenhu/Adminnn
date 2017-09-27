<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="/WEB-INF/page/share/taglib.jsp" %>
<title>添加职责</title>
</head>
<body>
	<style>
		.lb-cpn{display: inline-block;word-break: break-all;margin-right: 8px;cursor: pointer;}
	</style>
	<form id="formA">
        <div style="margin:20px auto;">
            <table class="form-tb" width="100%">
               	<tr>
                    <th width="100"><em class="cred">*</em>名称：</th>
                    <td>
                    	<div class="js-role-name">
                    		<div style="width:1000px;">
	                        <label for="jsRroleName">${company.code }-${company.cname }-<span id="jsSystemName"></span>-</label>
	                        <input id="jsRroleName" style="width:280px" maxlength="64" name="name" type="text" class="ipt easyui-validatebox" data-options="required:true,missingMessage:'请输入职责名称'" />
                    		</div>
                    	</div>
                    </td>
                </tr>
             	<tr>
                    <th><em class="cred">*</em>标识：</th>
                    <td>    
                        <input style="width:300px" maxlength="64" name="sn" type="text" class="ipt easyui-validatebox" data-options="required:true,missingMessage:'请输入职责标识',validType:'unique'" />
                    </td>
                </tr>
                <tr>
                    <th><em class="cred">*</em>等级：</th>
                    <td>    
                        <select id="jsRoleLevel" class="easyui-combobox" data-options="editable:false,prompt:'请选择...',required:true,missingMessage:'请选择职责等级！'" name="roleLevel" style="width:300px;">
	                        <c:choose >
					        	<c:when test="${roleLevels.size() > 0}">
					        		<c:forEach items="${roleLevels}" var="roleLevel" varStatus="status">
							        	<option value="${roleLevel.code }">${roleLevel.name}</option>
							        </c:forEach>
					        	</c:when>
					        </c:choose>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th><em class="cred">*</em>所属系统：</th>
                    <td>    
                        <select id="jsSystemId" class="easyui-combobox" data-options="editable:false, onSelect:role.selectedSystem,prompt:'请选择...',required:true,missingMessage:'请选择所属系统！'" name="systemId" style="width:300px;">
	                        <c:choose >
					        	<c:when test="${systems.size() > 0}">
					        		<c:forEach items="${systems}" var="sys" varStatus="status">
							        	<option value="${sys.id }">${sys.name}</option>
							        </c:forEach>
					        	</c:when>
					        </c:choose>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th style="vertical-align:top">备注：</th>
                    <td>  
                    	<textarea  maxlength="1024" style="width:480px" name="note" rows="5" cols="30"></textarea>
                    </td>
                </tr>
            </table>
        </div>
    </form>
</body>
</html>