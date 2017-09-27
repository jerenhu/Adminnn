<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="/WEB-INF/page/share/taglib.jsp" %>
<script src="${basePath}/js/myupload.js" type="text/javascript"></script>
<title>修改模块</title>
</head>
<body>
	<center>
	<form id="formU">
		<input name="id" type="hidden" />
        <div style="margin:20px auto;">
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
                    	<input style="width:150px;"maxlength="11" name="orderNo" type="text" class="ipt easyui-numberbox" data-options="min:0"></input>
                    </td>
                </tr>
                <!-- 
                <tr>
                	<th>小图标：</th>
                  	<td>
	             		<a id="uploadFile-1" iconcls="icon-upload" class="easyui-linkbutton jsUploadFile" href="javascript:void(0);">浏览</a>
		              	<div id="showImgPanel" style="cursor:pointer;display:inline">
		              		&nbsp;&nbsp;
		              		<img class="showImage" src="" />
		              		&nbsp;&nbsp;<a href="javascript:void(0);" id="jsDeleteIcon" class="cred" title="删除图标">x</a>&nbsp;&nbsp;
		              	</div>
                    	<input type="hidden" value="" id="imgUrlTxt" name="icon" />
                    	&nbsp;&nbsp;
                    	<font color="red">提示：（建议上传尺寸：宽30px*高17px；高不超过17px）</font>
                  	</td>
                </tr>
                 -->
            </table>
        </div>
    </form>
    </center>
</body>
</html>