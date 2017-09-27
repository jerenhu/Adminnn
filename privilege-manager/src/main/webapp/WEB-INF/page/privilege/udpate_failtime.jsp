<%-- 重置账户有效期 --%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="/WEB-INF/page/share/taglib.jsp" %>
<title>重置账户有效期</title>
</head>
	<body>
	<center>
	<form id="formP">
        <div style="margin:20px auto;">
        	<input name="id" type="hidden" />
            <table class="form-tb">
            	<tr>
             		<th><em class="cred">*</em>用户名：</th>
                    <td>    
                        <input name="username" type="text" class="ipt easyui-validatebox" data-options="required:true,missingMessage:'请输入用户名'" readonly />
                    </td>
                </tr>
             	<tr>
                    <th><em class="cred">*</em>有效期：</th>
                    <td>    
                        <select id="jsfailMonth" class="ipt easyui-combobox" name="failMonth" style="width:60px;" data-options="required:true,missingMessage:'请选择'" >   
                        	<option value="" selected>请选择</option>   
						    <option value="1">1个月</option>   
						    <option value="2">2个月</option>   
						    <option value="3">3个月</option>   
						    <option value="4">4个月</option>   
						    <option value="5">5个月</option>   
						    <option value="6">6个月</option>   
						    <option value="12">12个月</option>   
						</select>  
						<span id="jsfailTime"></span>
                    </td>
                </tr>
            </table>
        </div>
    </form>
    </center>
</body>
</html>