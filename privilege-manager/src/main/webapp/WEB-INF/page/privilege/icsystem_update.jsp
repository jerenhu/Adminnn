<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="/WEB-INF/page/share/taglib.jsp" %>
<title>修改系统</title>
</head>
	<body>
	<center>
	<form id="formU">
		<input name="id" type="hidden" />
        <div style="margin:20px auto;">
            <table class="form-tb" width="100%">
             	<tr>
                    <th width="80"><em class="cred">*</em>名称：</th>
                    <td>    
                        <input style="width:300px" id="name" name="name" type="text" class="ipt easyui-validatebox" data-options="required:true,missingMessage:'请输入系统名称'" />
                    </td>
                </tr>
             	<tr>
                    <th><em class="cred">*</em>标识：</th>
                    <td>    
                        <input style="width:300px" id="sn" name="sn" type="text" class="ipt easyui-validatebox" data-options="required:true,missingMessage:'请输入系统标识',validType:'unique'" />
                    </td>
                </tr>
                <tr>
                    <th><em class="cred">*</em>url前缀：</th>
                    <td>    
                    	<input style="width:300px" id="url" name="url" type="text" class="ipt easyui-validatebox" data-options="required:true,missingMessage:'请输入url前缀'" />
                    </td>
                </tr>
                <tr>
                    <th>排序号：</th>
                    <td>    
                    	<input  name="orderNo" type="text" class="ipt easyui-numberbox" data-options="min:0"></input>
                    </td>
                </tr>
                <tr>
                    <th style="vertical-align: top">描述：</th>
                    <td>  
                    	<textarea style="width:300px;height:55px" name="note" rows="2" cols="30"></textarea>
                    </td>
                </tr>
            </table>
        </div>
    </form>
    </center>
</body>
</html>