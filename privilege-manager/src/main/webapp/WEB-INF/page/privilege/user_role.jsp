<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/page/share/taglib.jsp"%>
<title>用户角色分配</title>
<body>

<script>
var _jsSessionId = '${sessionId}';
var userRoleObj = {
	hasChange:false,
	personRoles:[],
	originalPersonRoles:[],
	dgObj:null,
	companyId:'${companyId}',
	personNo:'${personNo}',
	personId:'${id}',
	init:function(){
		this.personRoles = ${personRoles };
		this.originalPersonRoles = ${personRoles };
		userRoleObj.dgObj = $('#rsubDg');
		userRoleObj.initTreeObj();
		
		//查询按钮
		$('#jsRoleSearchBtn').click(function() {
			userRoleObj.doSearch();
		});
		
		//回车事件
		$('#jsRolesSearchform').on('keydown', function(event){
			if(event.keyCode == 13){
				userRoleObj.doSearch();
			}
		});
		
		setTimeout(function(){
			//默认选中用户所在的公司
			var node = common.zTree.companyTreeObj.getNodeByParam("id", userRoleObj.companyId, null);
			common.zTree.companyTreeObj.selectNode(node);
			userRoleObj.initDg(node.id,'');
		}, 100);
		
	},
	doSearch:function(){
		var companyId = '';
		var nodes = common.zTree.companyTreeObj.getSelectedNodes();
		if(nodes[0]){
			if(nodes[0].pId=='' || nodes[0].pId=='0'){
				companyId = '';
			}else{
				companyId = nodes[0].id;
			}
		}
		userRoleObj.initDg(companyId, $.trim($('#querycontent').val()));
	},
	//检测是否有变动
	checkPersonRolesChange:function(){
		var result = false;
		if(userRoleObj.personRoles.length != userRoleObj.originalPersonRoles.length){
			result = true;
		}else{
			if(userRoleObj.personRoles.length>0){
				var roleIds = [];
				var originalRoleIds = [];
				for(var i=0; i< userRoleObj.personRoles.length;i ++){
					roleIds.push(userRoleObj.personRoles[i].roleId);
				}
				for(var i=0; i< userRoleObj.originalPersonRoles.length;i ++){
					originalRoleIds.push(userRoleObj.originalPersonRoles[i].roleId);
				}
				if(roleIds.sort().toString() != originalRoleIds.sort().toString()){
					result = true;
				}
			}
		}
		return result;
	},
	initDg:function(companyId,querycontent){
		userRoleObj.dgObj.datagrid({    
		    url:basePath+'/managment/privilege/role/getRolesData.do?sessionId='+_jsSessionId,
		    queryParams:{companyId:companyId, querycontent:querycontent},
		    pagination : true, pageSize : 10, pageList : [ 10, 20,30 ,50],
		    onLoadSuccess:function(data){
		    	//userRoleObj.dgObj.datagrid('uncheckAll');
		    	userRoleObj.checkRolesByUser(data);
		    },
		    onCheck:userRoleObj.addOrRemoveRole,
		    onUncheck:userRoleObj.addOrRemoveRole,
		    onCheckAll:userRoleObj.addOrRemoveRoleCheckAll,
		    onUncheckAll:userRoleObj.addOrRemoveRoleCheckAll
		    //onClickRow:userRoleObj.addOrRemoveRole
		});
	},
	addOrRemoveRoleCheckAll:function(rows){
		if(rows && rows.length>0){
			for(var i = 0; i< rows.length; i++){
				userRoleObj.addOrRemoveRole(i, rows[i]);
			}
		}
	},
	addOrRemoveRole:function(idx,rowData){
		//var node = common.zTree.companyTreeObj.getNodeByParam("id", rowData.deptId, null);
    	//common.zTree.companyTreeObj.selectNode(node);
		//选中行时：
		if($('input[type="checkbox"][value="'+rowData.id+'"]').attr("checked")){
			var role = {id:'', personId:userRoleObj.personId, personNo:userRoleObj.personNo, roleId:rowData.id};
			userRoleObj.personRoles.push(role);
		}else{//取消选中时
			if(userRoleObj.personRoles.length>0){
				var tempUserRoleObj=[];
				for(var j = 0; j<userRoleObj.personRoles.length; j++){
					if(rowData.id == userRoleObj.personRoles[j].roleId){
					}else{
						tempUserRoleObj.push(userRoleObj.personRoles[j]);
					}
				}
				userRoleObj.personRoles = tempUserRoleObj;
			}
		}
		//过滤重复数据
		userRoleObj.personRoles = userRoleObj.unique(userRoleObj.personRoles);
	},
	unique:function(target) {    
		var result = [];    
      	loop:   
		for (var i = 0, n = target.length; i < n; i++){    
	        for (var x = i + 1; x < n; x++) {    
	          if (target[x].roleId === target[i].roleId)  
	            continue loop;     
	        }    
			result.push(target[i]);    
		}    
      	return result;    
    },
	//选中已经有的角色
	checkRolesByUser:function(data){
		for(var i=0;i<data.rows.length;i++){
			for(var j = 0; j<userRoleObj.personRoles.length; j++){
				if(data.rows[i].id == userRoleObj.personRoles[j].roleId){
					userRoleObj.dgObj.datagrid('selectRecord',data.rows[i].id);
				}
			}
    	}
	},
	initTreeObj:function(){
		common.zTree.initCompanyTree($("#jsUserRoleDept"),function(event, treeId, treeNode){
			if(treeNode.pId=="" || treeNode.pId=='0'){
				userRoleObj.initDg('','');
			}else{
				//var data = {deptId:treeNode.id};
				userRoleObj.initDg(treeNode.id,'');
			}
		});
		// this.initDg('','');
	},
	roleCompanyFormatter:function(value,row,index){
		var node = common.zTree.companyTreeObj.getNodeByParam("id", row.companyId, null);
		if(node){
			return node.text;
		}
		return '暂无';
	}
};
$(document).ready(function(){ 
	// doQuery();
	userRoleObj.init();
});
</script>
<div id="jsRoleLayout" data-options="fit:true" class="easyui-layout" >   
    <div data-options="region:'west',split:true" style="width:200px;">
    	<ul id="jsUserRoleDept" class="ztree"></ul>
    </div>   
    <div data-options="region:'center'" >
    	<div id="jsRoleCenter" class="easyui-layout" data-options="fit:true">   
		    <div data-options="region:'north'" style="height:40px;">
		    	<form id="jsRolesSearchform" style="margin:5px" onsubmit="return false;">
		    		名称/标识：<input type="text"  id="querycontent" name="querycontent" style="width:160px"  placeholder="输入名称或者标示" />
		    		<a iconCls="icon-search" id="jsRoleSearchBtn" class="easyui-linkbutton ml10" data-options="iconCls:'icon-search'" >查询</a>
		    	</form>
		    </div>   
		    <div data-options="region:'center'" >
		    	<table id="rsubDg"  
					data-options="idField : 'id',selectOnCheck:true,checkOnSelect:true,rownumbers:true, method : 'post',singleSelect : false">
					<thead>
						<tr>
							<th data-options="field:'id',checkbox:true"></th>
							<th data-options="field:'longName',width : 100,align:'left'">名称</th>
							<th data-options="field:'sn',width : 60,align:'left'">标识</th>
							<th data-options="field:'companyId',width : 50,align:'left',formatter:userRoleObj.roleCompanyFormatter">所属公司</th>
							<!--<th data-options="field:'companyName',width : 100,align:'left'">所属公司</th>-->
						</tr>
					</thead>
				</table>
		    </div>
		</div>
    </div>
</div>
  	<input name="id" type="hidden" id="rpid" value="${(id) }"/>
  	<input style="width:120px;" name="companyId"  type="hidden" value="${(companyId) }" />
</body>
</html>