<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="/WEB-INF/page/share/taglib.jsp" %>
<title>修改权限值</title>
</head>
<body>
	<center>
	<form id="formU">
        <div style="margin:20px auto;">
        <input name="id" type="hidden" />
        <input name="systemId" type="hidden"  />
            <table class="form-tb" width="100%">
           		<tr>
                    <th><em class="cred">*</em>权限值名称：</th>
                    <td>
                    	<input style="width:300px" name="name" type="text" class="ipt easyui-validatebox" data-options="required:true,missingMessage:'请输入权限值名称'" />
                    </td>
                </tr>
             	<tr>
                    <th><em class="cred">*</em>整型的位：</th>
                    <td>
                    	<input style="width:300px" name="position" type="text" class="ipt easyui-numberbox" data-options="required:true,missingMessage:'请输入整型的位',validType:'unique'" />
                    </td>
                </tr>
             	<tr>
                    <th>排序号：</th>
                    <td>  
                    	<input name="orderNo" maxlength="11" type="text" class="ipt easyui-numberbox" data-options="min:0"></input>
                    </td>
                </tr>
                <tr>
                    <th style="vertical-align:top">描述：</th>
                    <td>  
                    	<textarea style="width:300px" name="remark" rows="3" cols="30"></textarea>
                    </td>
                </tr>
            </table>
        </div>
    </form>
    </center>
</body>
</html>