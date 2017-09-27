<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="/WEB-INF/page/share/taglib.jsp" %>
<title>添加模块</title>
</head>
	<body>
	<center>
	<form id="formA">
        <div style="margin:20px auto;">
			<input name="pid" type="hidden" value="${pid }" />
			<input name="systemId" type="hidden" value="${systemId }" />
            <table class="form-tb" width="100%">
             	<tr>
                    <th width="90"><em class="cred">*</em>模块名称：</th>
                    <td>
                    	<input style="width:300px;" name="name" type="text" class="ipt easyui-validatebox" data-options="required:true,missingMessage:'请输入模块名称'" />
                    </td>
                </tr>
                <tr>
                    <th>url：</th>
                    <td>    
                    	<input style="width:300px;" name="url" type="text" class="ipt easyui-validatebox" data-options="missingMessage:'请输入url'" />
                    </td>
                </tr>
                <tr>
                    <th><em class="cred">*</em>标识：</th>
                    <td>    
                    	<input style="width:300px;" name="sn" type="text" class="ipt easyui-validatebox" data-options="required:true,missingMessage:'请输入模块标识',validType:'unique'" />
                    </td>
                </tr>
                <tr>
                    <th>排序号：</th>
                    <td>  
                    	<input style="width:150px;" maxlength="11" name="orderNo" type="text" class="ipt easyui-numberbox" data-options="min:0"></input>
                    </td>
                </tr>
            </table>
        </div>
    </form>
    </center>
</body>
</html>