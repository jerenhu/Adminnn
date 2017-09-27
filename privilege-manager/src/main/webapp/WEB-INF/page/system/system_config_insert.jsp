<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="/WEB-INF/page/share/taglib.jsp" %>
<title>添加系统配置</title>
</head>
<body>
	<center>
	<form id="formA">
        <div style="margin:20px auto;">
            <table class="form-tb" width="100%">
           		<tr>
                    <th width="140px"><em class="cred">*</em>配置名称：</th>
                    <td>    
                        <input style="width:300px" name="configName" type="text" class="ipt easyui-validatebox" data-options="required:true,missingMessage:'请输入配置名称'" />
                    </td>
                </tr>
           		<tr>
                    <th><em class="cred">*</em>配置标识：</th>
                    <td>    
                        <input style="width:300px" name="configSn" type="text" class="ipt eas  yui-validatebox" data-options="required:true,missingMessage:'请输入配置标识',validType:'unique'" />
                    </td>
                </tr>
                <tr>
                    <th><em class="cred">*</em>配置key：</th>
                    <td>    
                        <input style="width:300px" name="configKey" type="text" class="ipt easyui-validatebox" data-options="required:true,missingMessage:'请输入配置key',validType:'uniquekey'" />
                    </td>
                </tr>
                <tr>
                    <th><em class="cred">*</em>配置key的value值：</th>
                    <td>    
                        <input style="width:300px" name="configValue" type="text" class="ipt easyui-validatebox" data-options="required:true,missingMessage:'请输入配置key的value值'" />
                    </td>
                </tr>
                <tr>	
                    <th><em class="cred">*</em>排序号：</th>
                    <td>    
                    	<input style="width:150px" maxlength="11" name="configOrder" type="text" class="ipt easyui-numberbox" data-options="required:true,missingMessage:'请输入排序号',min:0"/>
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