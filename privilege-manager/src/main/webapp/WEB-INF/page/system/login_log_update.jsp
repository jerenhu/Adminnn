<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="/WEB-INF/page/share/taglib.jsp" %>
<title>查看登录日志</title>
</head>
<body>
	<center>
	<form id="formU">
		<input name="id" type="hidden" />
        <div style="margin:20px auto;">
            <table class="form-tb" width="100%">
            	<tr>
                    <th><em class="cred">*</em>IP地址：</th>
                    <td>    
                        <input style="width:300px" name="ip" type="text" class="ipt easyui-textbox" readonly="readonly" />
                    </td>
                </tr>
             	<tr>
                    <th><em class="cred">*</em>用户id：</th>
                    <td>    
                        <input style="width:300px" name="operationId" type="text" class="ipt easyui-textbox" readonly="readonly" />
                    </td>
                </tr>
                <tr>
                    <th><em class="cred">*</em>用户名：</th>
                    <td>    
                        <input style="width:300px"  name="operationUsername" type="text" class="ipt easyui-textbox" readonly="readonly" />
                    </td>
                </tr>
                <tr>
                    <th><em class="cred">*</em>真实姓名：</th>
                    <td>    
                        <input style="width:300px" name="operationPerson" type="text" class="ipt easyui-textbox" readonly="readonly" />
                    </td>
                </tr>
                <tr>
                    <th><em class="cred">*</em>操作内容：</th>
                    <td>    
                        <input style="width:300px" name="operationContent" type="text" class="ipt easyui-textbox" readonly="readonly" />
                    </td>
                </tr>
                <tr>
                    <th><em class="cred">*</em>操作时间：</th>
                    <td>    
                        <input name="operationTimeStr" type="text" class="ipt easyui-textbox" readonly="readonly" />
                    </td>
                </tr>
            </table>
        </div>
    </form>
    </center>
</body>
</html>