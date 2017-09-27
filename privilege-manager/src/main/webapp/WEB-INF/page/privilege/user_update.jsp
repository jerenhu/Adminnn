<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    
	<form id="formU">
		<input type='hidden' name="id" />
		<input type='hidden' value="${userId }" id="js_userId"/>
        <div style="margin:20px auto;">
            <table class="form-tb" width="100%">
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
                    <td>    
                    	<%--公司id，隐藏域 --%>
                    	<input type="hidden" name="companyId" id="jsCompanyIdHide"/>
                        <select style="width:210px;" id="jsDepartmentId" name="departmentId" class="ipt" data-options="panelHeight:200"></select>
                    </td>
                </tr>
             	<tr>
                    <th><em class="cred">*</em>邮箱：</th>
                    <td >    
                        <input name="email" class="ipt easyui-validatebox" data-options="required:true,validType:'email'" />
                    </td>
                    <th><em class="cred">*</em>有效期：</th>
                    <td>    
                        <select id="jsfailMonth" class="ipt easyui-combobox" name="failMonth" style="width:60px;" data-options="editable:false,disabled:true,required:true,missingMessage:'请选择'" >   
						    <option value="">请选择</option>   
						    <option value="1">1个月</option>   
						    <option value="2">2个月</option>   
						    <option value="3">3个月</option>   
						    <option value="4">4个月</option>   
						    <option value="5">5个月</option>   
						    <option value="6">6个月</option>   
						    <option value="12">12个月</option>
						</select>  
						<span id="jsfailTime">过期时间：${failTime}</span>
                    </td>
                </tr>
                <tr>
                    <th style="vertical-align:top;">地址：</th>
                    <td colspan="3">  
                    	<textarea style="width:90%" name="address" rows="2" cols="30"></textarea>
                    </td>
                </tr>
                <%--<tr>
                    <th>所属系统：</th>
                    <td colspan="3">  
                    	<select name="systemIds" id="jsSystemIds" style="width:600px" class="ipt" ></select>
                    </td>
                </tr>--%>
            </table>
        </div>      
    </form>
    <style>
    	.form-tb .ipt{
    		width:200px;
    	}
    </style>
<script>
$(function(){
	User.ipt.init();
});
</script>