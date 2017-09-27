<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/page/share/taglib.jsp"%>
<%-- 公司修改 --%>
<div class="pd10">
    <form id="myForm" method="post">
    	<input name="id" type="hidden" value="${(company.id)!''}"/>
        <table class="form-tb">
            <col width="100" />
            <col />
            <tbody>
                <tr>   
                    <th><em class="cred">*</em> ä¸çº§å¬å¸idï¼</th>
                    <td>
                    	<input style="width:120px;" name="pid"  type="text" value="${(company.pid)!''}" class="ipt easyui-validatebox" data-options="required:true,missingMessage:'ä¸çº§å¬å¸idä¸è½ä¸ºç©ºï¼'" autocomplete="off"/>
                    </td>
                </tr>
                <tr>   
                    <th><em class="cred">*</em> å¬å¸ä¸­æåç§°ï¼</th>
                    <td>
                    	<input style="width:120px;" name="cname"  type="text" value="${(company.cname)!''}" class="ipt easyui-validatebox" data-options="required:true,missingMessage:'å¬å¸ä¸­æåç§°ä¸è½ä¸ºç©ºï¼'" autocomplete="off"/>
                    </td>
                </tr>
                <tr>   
                    <th><em class="cred">*</em> å¬å¸è±æåç§°ï¼</th>
                    <td>
                    	<input style="width:120px;" name="ename"  type="text" value="${(company.ename)!''}" class="ipt easyui-validatebox" data-options="required:true,missingMessage:'å¬å¸è±æåç§°ä¸è½ä¸ºç©ºï¼'" autocomplete="off"/>
                    </td>
                </tr>
                <tr>   
                    <th><em class="cred">*</em> å¬å¸codeï¼</th>
                    <td>
                    	<input style="width:120px;" name="code"  type="text" value="${(company.code)!''}" class="ipt easyui-validatebox" data-options="required:true,missingMessage:'å¬å¸codeä¸è½ä¸ºç©ºï¼'" autocomplete="off"/>
                    </td>
                </tr>
                <tr>   
                    <th><em class="cred">*</em> æè¿°ï¼</th>
                    <td>
                    	<input style="width:120px;" name="descr"  type="text" value="${(company.descr)!''}" class="ipt easyui-validatebox" data-options="required:true,missingMessage:'æè¿°ä¸è½ä¸ºç©ºï¼'" autocomplete="off"/>
                    </td>
                </tr>
                <tr>   
                    <th><em class="cred">*</em> ç¶æ 1å¯ç¨ 0ç¦ç¨ï¼</th>
                    <td>
                    	<input style="width:120px;" name="status"  type="text" value="${(company.status)!''}" class="ipt easyui-validatebox" data-options="required:true,missingMessage:'ç¶æ 1å¯ç¨ 0ç¦ç¨ä¸è½ä¸ºç©ºï¼'" autocomplete="off"/>
                    </td>
                </tr>
                <tr>   
                    <th><em class="cred">*</em> ï¼</th>
                    <td>
                    	<input style="width:120px;" name="creator"  type="text" value="${(company.creator)!''}" class="ipt easyui-validatebox" data-options="required:true,missingMessage:'ä¸è½ä¸ºç©ºï¼'" autocomplete="off"/>
                    </td>
                </tr>
                <tr>   
                    <th><em class="cred">*</em> ï¼</th>
                    <td>
                    	<input style="width:120px;" name="createTime"  type="text" value="${(company.createTime)!''}" class="ipt easyui-validatebox" data-options="required:true,missingMessage:'ä¸è½ä¸ºç©ºï¼'" autocomplete="off"/>
                    </td>
                </tr>
                <tr>   
                    <th><em class="cred">*</em> ï¼</th>
                    <td>
                    	<input style="width:120px;" name="updator"  type="text" value="${(company.updator)!''}" class="ipt easyui-validatebox" data-options="required:true,missingMessage:'ä¸è½ä¸ºç©ºï¼'" autocomplete="off"/>
                    </td>
                </tr>
                <tr>   
                    <th><em class="cred">*</em> ï¼</th>
                    <td>
                    	<input style="width:120px;" name="updateTime"  type="text" value="${(company.updateTime)!''}" class="ipt easyui-validatebox" data-options="required:true,missingMessage:'ä¸è½ä¸ºç©ºï¼'" autocomplete="off"/>
                    </td>
                </tr>
                <tr>   
                    <th><em class="cred">*</em> 1ï¼å­å¨  0ï¼ å é¤ï¼</th>
                    <td>
                    	<input style="width:120px;" name="delFlag"  type="text" value="${(company.delFlag)!''}" class="ipt easyui-validatebox" data-options="required:true,missingMessage:'1ï¼å­å¨  0ï¼ å é¤ä¸è½ä¸ºç©ºï¼'" autocomplete="off"/>
                    </td>
                </tr>
                <#--<tr>   
                    <th><em class="cred">*</em> ç¶æï¼</th>
                    <td>
                    	<input id="status1" name="status" type="radio" value="1" class="easyui-validatebox" 
						<#if (deployGroup.status)??&& deployGroup.status=1>checked<#elseif !(deployGroup.status)?? && !(deployGroup.status)??>checked</#if>/><label for="status1">å¯ç¨</label> 
						<input id="status2" name="status" type="radio" value="0" class="ml10 easyui-validatebox"
						<#if (deployGroup.status)??&& deployGroup.status=0>checked</#if>/><label for="status2">ç¦ç¨</label></br>
                    </td>
                </tr>
                <tr>   
                    <th><em class="cred">*</em> ç±»åï¼</th>
                    <td>
                    	<select name="type" class="easyui-combobox" data-options="required:true,missingMessage:'è¯·éæ©ç±»å',editable:false, width:100">
                    		<option value="">è¯·éæ©</option>
                    		<option value="1" <#if (deployFile.type)?? && deployFile.type=1>selected</#if>>warå </option>
                    		<option value="2" <#if (deployFile.type)?? && deployFile.type=2>selected</#if>>jswå</option>
                    		<option value="3" <#if (deployFile.type)?? && deployFile.type=3>selected</#if>>cssJså</option>
                    	</select>
                    </td>
                </tr>
				<tr>   
                    <th><em class="cred">*</em> æåºå·ï¼</th>
                    <td>
                    	<input  name="orderNum"  type="text" value="${(floor.orderNum)!''}" class="ipt easyui-validatebox" data-options="required:true,missingMessage:'æåºå·ä¸è½ä¸ºç©ºï¼'" validtype="integer"/>
						<input style="width:200px;" name="orderCount"  type="text" value="${(floor.orderCount)!''}" class="ipt easyui-numberbox" data-options="required:true,missingMessage:'æåºå·ä¸è½ä¸ºç©ºï¼',min:1" autocomplete="off"/>
				   </td>
                </tr>
				<tr>   
                    <th><em class="cred">*</em> éè¯·æ³¨åçäººæ°ï¼</th>
                    <td>
                    	<input maxlength="8" id="jsst" style="width:120px;" name="UpdateInviteAmounts"  type="text" value="${(userInviteStatistics.inviteAmounts)!''}" class="ipt easyui-validatebox" data-options="required:true,missingMessage:'éè¯·æ³¨åçäººæ°ä¸è½ä¸ºç©ºï¼'" validtype="wennum['#jsst']" autocomplete="off"/>
                    </td>
                </tr>
				<tr>   
                    <th><em class="cred">*</em> é¢å®æ»æ°ï¼</th>
                    <td>
                    	<input id="jspreSaleCount" style="width:80px;" name="preSaleCount" maxlength="8" type="text" value="${(vo.preSaleCount)!''}" class="ipt easyui-validatebox" data-options="required:true,missingMessage:'é¢å®æ»æ°ä¸è½ä¸ºç©ºï¼'" validtype="wennumone['#jspreSaleCount',8]" autocomplete="off"/>
                    </td>
                </tr>
            	<tr>   
                    <th><em class="cred">*</em> é¢å®ç³è¯·å¬å¸ç¨æ·æ¯ä¾ï¼</th>
                    <td>
                    	<input id="jspreSaleCompanyRate" style="width:80px;" name="preSaleCompanyRate" maxlength="3"  type="text" value="${(vo.preSaleCompanyRate)!''}" class="ipt easyui-validatebox" data-options="required:true,missingMessage:'é¢å®ç³è¯·å¬å¸ç¨æ·æ¯ä¾ä¸è½ä¸ºç©ºï¼'" validtype="wennumtwo['#jspreSaleCompanyRate',0,100]" autocomplete="off"/>
                    	%
                    </td>
                </tr>
				<tr>   
                    <th><em class="cred">*</em> æ¥æï¼</th>
                    <td>
                    	<input style="width:140px;" name="inviteDate"  type="text" value="${(userInviteStatistics.inviteDate)!''}" class="ipt easyui-datebox easyui-validatebox" data-options="required:true,missingMessage:'æ¥æä¸è½ä¸ºç©ºï¼',dateFmt:'yyyy-MM-dd HH:mm:ss'" autocomplete="off"/>
                    </td>
                </tr>
				<tr>   
                    <th><em class="credss">*</em> å¤æ³¨ï¼</th>
                    <td rowspan="2">
                    	<textarea name="remark" style="width:200px;height:50px;" maxlength="200" >${(franFinance.remark)!''}</textarea>
                    </td>
                </tr>
                -->
            </tbody>
        </table>
    </form>
</div>
<script>
/*éªè¯:å¤§äºç­äº0çæ°å­
$.extend($.fn.validatebox.defaults.rules, {    
    wennum: {    
        validator: function(value,param){ 
        	return $.trim($(param[0]).val())==0 ||  $.string.isInteger(value);
        },    
        message: 'è¯·è¾å¥æ°å­'   
    }    
}); 

//æ©å±éªè¯
$.extend($.fn.validatebox.defaults.rules, {  
    wennumone: {//å¤§äºç­äº0çæ­£æ´æ°ï¼ä½æ¯é¿åº¦ä¸è½è¶è¿8    
        validator: function(value,param){ 
        	return ($.trim($(param[0]).val())==0 ||  $.string.isInteger(value)) && ($.trim($(param[0]).val()).length<=param[1]);
        },    
        message: 'è¯·è¾å¥å¤§äºç­äº0çæ­£æ´æ°ä¸é¿åº¦ä¸è½è¶è¿{1}'   
    },
    wennumtwo: {//å¤§äºç­äº0å°äºç­äº100çæ­£æ´æ°
        validator: function(value,param){ 
       		var _val=$.trim($(param[0]).val());
       		var _falg=(_val==0 ||  $.string.isInteger(value)) && (_val>=param[1] && _val<=param[2]);
			if(_falg){
				$('#jspreSalePersonalRate').text(100-_val);
			}       		
        	return _falg;
        },    
        message: 'è¯·è¾å¥å¤§äºç­äº{1}ä¸å°äºç­äº{2}æ­£æ´æ°'   
    }      
}); 
*/
</script>