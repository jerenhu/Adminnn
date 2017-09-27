<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="/WEB-INF/page/share/taglib.jsp" %>
<title>添加用户</title>
</head>
	<body>
	<form id="formA">
        <div style="margin:20px auto;">
            <table class="form-tb" style="width:100%;">
            	<tr>
                    <th><em class="cred">*</em>用户名：</th>
                    <td>    
                        <input name="username" type="text" class="ipt easyui-validatebox" data-options="required:true,validType:'unique'" />
                    </td>
                    <th><em class="cred">*</em>性别：</th>
                    <td>
                        <label title="男"><input name="sex" type="radio" value="0" checked /><i class="gender male">&nbsp;</i></label>&nbsp;&nbsp;&nbsp;&nbsp;
                        <label title="女"><input name="sex" type="radio" value="1" /><i class="gender female">&nbsp;</i></label>
                    </td>
                </tr>
                <tr>
                    <th><em class="cred">*</em>真实姓名：</th>
                    <td>    
                        <input name="realName" type="text" class="ipt easyui-validatebox" data-options="required:true" />
                    </td>
                    <th>座机：</th>
                    <td>    
                        <input name="phone" type="text" class="ipt easyui-textbox" />
                    </td>
                </tr>
                <tr>
                    <th>电话：</th>
                    <td>    
                        <input name="tel" type="text" class="ipt easyui-textbox" />
                    </td>
                    <th>传真：</th>
                    <td>    
                        <input name="fax" type="text" class="ipt easyui-textbox" />
                    </td>
                </tr>
                <tr>
                    <th><em class="cred">*</em>手机：</th>
                    <td>    
                        <input name="mobile" type="text" class="ipt easyui-textbox easyui-validatebox" data-options="required:true,validType:'mobile'"/>
                    </td>
                    <th><em class="cred">*</em>所属部门：</th>
                    <td class="belong-dept">    
                    	<%--公司id，隐藏域 --%>
                    	<input type="hidden" name="companyId" id="jsCompanyIdHide" value=""/>
                    	<input type="hidden" name="departmentId" id="jsDepartmentIdHide" value=""/>
                    	<span id="jsDepartmentText"></span>
                    </td>
                </tr>
             	<tr>
                    <th><em class="cred">*</em>邮箱：</th>
                    <td>    
                        <input name="email" class="ipt easyui-validatebox" data-options="required:true,validType:'email'" />
                    </td>
                    <th><em class="cred">*</em>有效期：</th>
                    <td>    
                        <select id="jsfailMonth" class="ipt easyui-combobox" name="failMonth" style="width:60px;" data-options="required:true,missingMessage:'请选择'" >   
                        	<option value="">请选择</option>   
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
                <tr>
                    <th style="vertical-align:top">地址：</th>
                    <td>  
                    	<textarea style="width:108%" name="address" rows="3" cols="30"></textarea>
                    </td>
                </tr>
                <%--<tr>
                    <th><em class="cred">*</em>所属系统：</th>
                    <td colspan="3">  
                    	<select name="systemIds" style="width:300px;" class="ipt easyui-combotree" data-options="url:'${basePath}/managment/privilege/user/getAllSystems.do?sessionId=${sessionId}',required:true,loadFilter:pagerFilterSys,multiple:true,readonly:true,panelHeight:120"></select>
                    </td>
                </tr>--%>
            </table>
        </div>
    </form>
    
    <style>
    	.form-tb .ipt{width:200px;}
    	.belong-dept .combo{
    		width:208px!important;
    	}
    	.belong-dept .combo-text{
    		width:186px!important;
    	}
    </style>
</body>
</html>