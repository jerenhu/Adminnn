<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="/WEB-INF/page/share/taglib.jsp" %>
<title>修改部门</title>
</head>
	<body>
	<center>
	<form id="formU">
		<input name="id" type="hidden" />
        <div style="margin:20px auto;">
            <table class="form-tb"width="100%">
             	<tr>
                    <th width="80"><em class="cred">*</em>名称：</th>
                    <td>    
                        <input style="width:300px" name="name" type="text" class="ipt easyui-validatebox" data-options="required:true,missingMessage:'请输入部门名称'" />
                    </td>
                </tr>
                <tr>   
                    <th><em class="cred">*</em> 排序号：</th>
                    <td>
						<input  style="width:150px;" maxlength="11" name="orderNo"  type="text" class="ipt easyui-numberbox" data-options="required:true,missingMessage:'排序号不能为空！',min:1"/>
				   </td>
                </tr>
                <tr>
                    <th style="vertical-align:top">描述：</th>
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