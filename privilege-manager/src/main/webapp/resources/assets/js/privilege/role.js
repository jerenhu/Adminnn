/**
 * 角色
 */
var sizeObject={width:700,height:450};
var role = {
	dgObj:null,
	roleLevelMap:{},
	systemsMap:{},
	init:function(){
		this.bindEvent();
		this.initOrgTree();
		this.initDatagrid();
		for(var i = 0; i<roleLevelsJson.length;i++){
			this.roleLevelMap[roleLevelsJson[i].code] = roleLevelsJson[i].name;
		}
		for(var i = 0; i<systemsJson.length;i++){
			this.systemsMap[systemsJson[i].id] = systemsJson[i].name;
		}
	},
	bindEvent:function(){
		$('#jsSearchBtn').click(function(){
			role.doSearch();
		});
		//回车事件
		$('#searchFm').on('keydown', function(event){
			if(event.keyCode == 13){
				role.doSearch();
			}
		});
		
		//组装根据系统搜索的下拉框
		var systemsArr = [];
		systemsArr.push({id:'', name:'所有'});
		for(var i = 0; i<systemsJson.length; i++){
			systemsArr.push({id:systemsJson[i].id, name:systemsJson[i].name});
		}
		$("#jsAllSystemCombo").combobox({
			valueField: 'id',
			textField: 'name',
			data: systemsArr
		});
		//是否启用角色【启用、失效】
		$('#jsChangeValidState').click(function(){
			var _ts = $(this);
			var checkedRow = $('#dg').datagrid('getChecked');
			if(checkedRow && checkedRow.length > 0){
				if(checkedRow[0].validState==1){
					$.messager.confirm("信息", "禁用后，所有使用该角色的用户将无法使用，确定要禁用吗？", function(r) {
						if (r) {
							role.changeRoleValidState(checkedRow[0].id, 0);
						}
					});
				}else{
					role.changeRoleValidState(checkedRow[0].id, 1);
				}
			}
		});
		
		//添加角色
		$('#jsAddRole').click(function(){
			var nodes = common.zTree.companyTreeObj.getSelectedNodes();
			var companyId = nodes[0]?nodes[0].id:'';
			ygDialog({
			    title: '新增职责',    
			    width: sizeObject.width,    
			    height: sizeObject.height, 
			    closed: false,    
			    cache: false,    
			    href: basePath + '/managment/privilege/role/insertUI.do?sessionId='+jsSessionId+'&companyId='+companyId,    
			    modal: true,
			    buttons : [{
					text:'保存',
					iconCls : 'icon-save',
					handler:function(dialog){
						var form=$("#formA");
						if(form.form('validate')){
							$.messager.progress(); 
							role.genRoleCompanyIdsStr();
							var data=form.form("getData");
							$.post(
									basePath+'/managment/privilege/role/insert.do?sessionId='+jsSessionId+'&companyId='+companyId,
									data,
									function(result){
										$.messager.progress('close');
										if(result.responseCode==100){
											dialog.close();
											role.dgObj.datagrid("reload");
										}else{
											$.messager.alert('信息','保存失败！','info');
										}
									},'json'
								);
						}
					}
				}],onLoad:function(){
					$("#jsSystemName").text($('#jsSystemId').combobox('getText'));
				}
			});  
		});
		
		//修改角色
		$('#jsUpdateRole').click(function(){
			var data=role.dgObj.datagrid("getSelected");
			if(!data){
				$.messager.alert('信息','请选择一条记录！','info');
				return;
			}
			ygDialog({    
			    title: '编辑职责',    
			    width: sizeObject.width,    
			    height: sizeObject.height, 
			    closed: false,    
			    cache: false,    
			    href: basePath+'/managment/privilege/role/updateUI.do?sessionId='+jsSessionId+'&roleId='+data.id+'&companyId='+data.companyId,    
			    modal: true,
			    buttons : [{
					text:'保存',
					iconCls : 'icon-save',
					handler:function(dialog){
						var form=$("#formU");
						if(form.form('validate')){
							$.messager.progress();
							role.genRoleCompanyIdsStr();
							var fdata=form.form("getData");
							$.post(
								basePath+'/managment/privilege/role/update.do?sessionId='+jsSessionId,
								fdata,
								function(result){
									$.messager.progress('close');
									if(result.responseCode==100){
										dialog.close();
										role.dgObj.datagrid("reload");
									}else{
										$.messager.alert('信息','保存失败！','info');
									}
								},'json'
							);
						}
					}
				}],
				onLoad : function (){
					var form=$("#formU");
					form.form("load",data);
					$("#jsSystemName").text($('#jsSystemId').combobox('getText'));
				}
			});   
		});
		
		// 删除角色
		$('#jsDelRole').click(function(){
			var rows=role.dgObj.datagrid("getSelections");
			if (rows && rows.length > 0) {
				$.messager.confirm("信息", "确定删除选中记录？", function(r) {
					if (r) {
						$.messager.progress(); 
						var ids = [];
						for ( var i = 0, l = rows.length; i < l; i++) {
							var r = rows[i];
							ids.push(r.id);
						}
						var id = ids.join(',');
						$.ajax({
							url : basePath+"/managment/privilege/role/delete.do?sessionId="+jsSessionId+"&ids="+ id,
							dataType : 'json',
							success : function(text) {
								$.messager.progress('close');
								if (text.responseCode == 101) {
									$.messager.alert(text.responseMsg);
								}else{
									//使用'clearSelections':防止读取到了删除的数据
									role.dgObj.datagrid("clearSelections");
									role.dgObj.datagrid("reload");
								}
							}
						});
					}
				});
			} else {
				$.messager.alert('信息','请选择要删除的记录！','info');
			}
		});
		
		//设置角色的权限
		$('#jsSettingRoleAcl').click(function(){
			var row=role.dgObj.datagrid("getSelected");
			if(!row||row.validState==0){
				return;
			}
			if(!row.systemId){
				$.messager.alert('信息','请修改职责所属系统！','info');
				return;
			}
			ygDialog({    
			    title: '给 <font color="red">'+row.name+' </font>授权',    
			    width: 900,    
			    height: 'auto',    
			    closed: false,    
			    cache: false,    
			    href: basePath+"/managment/privilege/acl/roleModuleUI.do?releaseSn=role&sessionId="+jsSessionId+"&id=" + row.id + "&systemId="+row.systemId,
			    modal: true,
			    maximizable:true,
			    resizable:true,
				onLoad : function (){
					var systemId = $('#jsCurrSystemId').val();
					//系统菜单
					$('#systemMenu').tree({
						url:basePath+'/managment/privilege/module/getsystems.do?sessionId='+jsSessionId+'&systemId='+systemId,
						onSelect: function(node){
							//取消绑定的click事件
							$("#selectAllBtn .btn,.mbtn").unbind("click"); 
							//重新加载treegrid的数据
							$.post(basePath+"/managment/privilege/module/getAllPriValBySystemSn.do?sessionId="+jsSessionId,{systemSn : node.sn, type: nmSpace, releaseId : row.id},function(data){
								$('#rmtg').treegrid("loadData",data);
							},'json');
						},
						onLoadSuccess:function(node,data){  
					        $("#systemMenu li:eq(0)").find("div").addClass("tree-node-selected");   //设置第一个节点高亮  
					        var n = $("#systemMenu").tree("getSelected");  
					        if(n!=null){  
					             $("#systemMenu").tree("select",n.target);    //相当于默认点击了一下第一个节点，执行onSelect方法  
					        }
					    }  
					});
					//权限值
					$('#rmtg').treegrid({
						rownumbers : true,
						fitColumns : true,
						pagination : false,
						idField:'id',    
					    treeField:'name',
						columns : [ [ {
							title : '名称',
							field : 'name',
							width : 60,
							align : 'left'
						}, {
							title : ' 权限值',
							field : 'pvs',
							width : 150,
							align : 'left',
							formatter: function(value,row,index){
								var list=value,
									btnValue='全选',
									htmltext='',
									vflag=true;
								for(var i=0,len=list.length;i<len;i++){
									htmltext+='<label class="function-item select-box">'+
									'<input class="jsrmtg" type="checkbox" name="'+row.id+'" sn="'+row.sn+'" value="'+list[i].position+'"';
									if(list[i].flag==true){
										htmltext+='checked=checked';
									}else{
										vflag=false;
									}
									htmltext+=' />'+list[i].name+'</label>';
									/* if((i+1)%6==0){//6个换一行
										htmltext+='<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
									}  */
								}
								if(vflag){
									btnValue='取消';
								}
								var btnhtml='<input class="mbtn btn btnplan white" id="'+row.id+'" type="button" value="'+btnValue+'" style="cursor:pointer;"/>';
								//自动换行div
								var _autoHuanhangDivStart='<div style="width:370px;white-space: normal;">';
								var _autoHuanhangDivEnd='</div>';
								return _autoHuanhangDivStart+btnhtml+htmltext+_autoHuanhangDivEnd;
							}
						}] ],
						onLoadSuccess : function() {
							var node=$('#systemMenu').tree("getSelected");
							$("#jsRoleAcl :checkbox").click(function(){
								var name=this.name,
									moduleSn=$(this).attr('sn'),
									value=this.value,
									yes=false;
								if(this.checked){
									yes=true;
								}
								$.ajax({
									type:'post',
									url:basePath+'/managment/privilege/acl/setAcl.do?sessionId='+jsSessionId,
									data:{releaseId:row.id,releaseSn:nmSpace,systemSn:node.sn,moduleId:name,moduleSn:moduleSn,position:value,yes:yes}
								});
							});
							
							$(".mbtn").click(function(){
								var chks=document.getElementsByName(this.id),
									yes=false;
								if(this.value == '全选'){
									this.value='取消',yes=true;
									for(var i=0,len=chks.length;i<len;i++){
										chks[i].checked = true;
									}
								}else{
									this.value='全选';
									for(var i=0,len=chks.length;i<len;i++){
										chks[i].checked = false;
									}
								}
								$.ajax({
									type:'post',
									url:basePath+'/managment/privilege/acl/setAclByModule.do?sessionId='+jsSessionId,
									data:{releaseId:row.id,releaseSn:nmSpace,systemSn:node.sn,moduleId:this.id,yes:yes}
								});
							});
							
							$("#selectAllBtn .btn").click(function(){
								var _this = $(this);
								var yes=true;
								if(_this.text() == '全选'){
									//全选
									$(".jsrmtg:checkbox").attr("checked","true");
									$(".mbtn").val("取消");
								}else{
									//全不选
									yes=false;
									$(".jsrmtg:checkbox").removeAttr("checked");
									$(".mbtn").val("全选");
								}
								$.ajax({
									type:'post',
									url:basePath+'/managment/privilege/acl/setAllAcl.do?sessionId='+jsSessionId,
									data:{releaseId:row.id,releaseSn:nmSpace,systemSn:node.sn,yes:yes}
								});
							});
						}
					});
				}
			});    
		});
	},
	//修改权限有效状态
	changeRoleValidState:function(roleId, validState){
		var url = basePath+'/managment/privilege/role/changeValidState.do?sessionId='+jsSessionId;
		var data = {id:roleId, validState:validState};
		$.ajax({url:url, data:data, async:true, dataType:'JSON', type:'POST', success:function(dt){
			if(dt.status == 100){
				showSuc(dt.message);
				$('#dg').datagrid('reload');
			}else{
				showSuc(dt.message);
			}
		}});
	},
	//删除职责中的用户
	deleteRoleUser:function(current, uNo, uName, roleId, roleName){
		$.messager.confirm("信息", "确定要从职责中删除【"+uName+"】吗？", function(r) {
			if (r) {
				var url =  basePath+'/managment/privilege/role/deleteRoleUser.do?sessionId='+jsSessionId;
				var data = {userNo:uNo, roleId:roleId};
				$.ajax({url:url, data:data, async:true, type:'POST', dataType:'JSON', success:function(dt){
					if(dt.responseCode == 1){
						showSuc("删除成功");
						$(current).parent().remove();
					}else{
						showWarn('删除失败！');
					}
				}});
			}
		});
	},
	// 选择系统事件
	selectedSystem:function(rec){
		$('#jsSystemName').text(rec.text);
	},
	//删除职责中的组织
	deleteRoleCompany:function(current, cId, cName, roleId, roleName){
		$.messager.confirm("信息", "确定要从职责中删除【"+cName+"】吗？", function(r) {
			if (r) {
				var url =  basePath+'/managment/privilege/role/deleteRoleCompany.do?sessionId='+jsSessionId;
				var data = {cId:cId, roleId:roleId};
				$.ajax({url:url, data:data, async:true, type:'POST', dataType:'JSON', success:function(dt){
					if(dt.responseCode == 1){
						showSuc("删除成功");
						$(current).parent().remove();
					}else{
						showWarn('删除失败！');
					}
				}});
			}
		});
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
		this.initDatagrid(companyId, $.trim($('#js_querycontent').val()));
	},
	initOrgTree:function(){
		common.zTree.initCompanyTree($("#dept"), function(event, treeId, treeNode){
			$('#jsCompanyIdHide').val(treeNode.id);
			//使用'clearSelections':防止读取到了删除的数据
			if(treeNode.pId=="" || treeNode.pId=='0'){
				role.initDatagrid('');
			}else{
				role.initDatagrid(treeNode.id);
			}
		});
	},
	clickDgRow:function(rowIndex,rowData){
		var node = common.zTree.companyTreeObj.getNodeByParam("id", rowData.companyId, null);
    	common.zTree.companyTreeObj.selectNode(node);
    	
    	var validStatCtrl = $('#jsChangeValidState');
    	var settingRoleAcl = $('#jsSettingRoleAcl');
    	var updateRole = $('#jsUpdateRole');
    	validStatCtrl.linkbutton('enable');
    	updateRole.linkbutton('enable');
    	if(rowData.validState==1){
    		validStatCtrl.linkbutton({iconCls: 'icon-stop', text:'禁用'});
    		settingRoleAcl.linkbutton('enable');
    	}else{
    		validStatCtrl.linkbutton({iconCls: 'icon-start', text:'启用'});
    		settingRoleAcl.linkbutton('disable');
    	}
    },
	companyFormatter:function(value,row,index){
		var roleId = row.id;
		var roleName = row.name;
		
		if(value!=''){
			var values = [];
			var arr = value.split(';,;');
			for(var i = 0; i<arr.length ;i++){
				if(arr[i]!=''){
					var cArr = arr[i].split(',');
					var cId = cArr[0];
					var cName = cArr[1];
					values.push(cName+'<i uNo="'+cId+'" onclick="role.deleteRoleCompany(this, \''+cId+'\', \''+cName+'\', \''+roleId+'\', \''+roleName+'\')" class="jsDelRoleUser">×</i>');
				}
			}
			value = values.join('</span><span class="keep-style">');
			value = value.replace(/;,;+/ig, '　');
		}
		value = '<span class="keep-style">'+value+'</span>';
		return '<div class="jsPersonRoleStyle">'+value+'</div>';
	},
	initDatagrid:function(companyId, querycontent){
		var systemId = $("#jsAllSystemCombo").combobox("getValue");
		var roleUrl=basePath+'/managment/privilege/role/getRolesData.do?sessionId='+jsSessionId;
		role.dgObj=$('#dg').datagrid({
			url: roleUrl,
			queryParams:{companyId:companyId, querycontent:querycontent, showRoleUser:1, systemId:systemId?systemId:''}, 
			fitColumns : true,
			checkOnSelect : true,
			selectOnCheck : true,
			singleSelect:true,
			nowrap:false,
			pageSize: 20,
			onClickRow:role.clickDgRow,
			onCheck:role.clickDgRow,
			pageList: [20,50,100],
			columns : [ [ {
				checkbox : true
			}, {
				title : '名称',
				field : 'name',
				width : 200,
				align : 'left', 
				formatter:role.roleNameFormatter
			}, {
				title : '标识',
				field : 'sn',
				width : 80,
				align : 'left'
			}, {
				title : '等级',
				field : 'roleLevel',
				width : 40,
				align : 'center',
				formatter:role.roleLevelFormatter
			},{
				title : '状态',
				field : 'validState',
				width : 40,
				align : 'center',
				formatter:role.validStateFormatter
			}, {
				title : '所属组织',
				field : 'companyName',
				width : 80,
				align : 'left',
				formatter:role.companyNameFormatter
			}, {
				title : '所属系统',
				field : 'systemId',
				width : 80,
				align : 'left',
				formatter:role.systemIdFormatter
			},{
				title : '用户列表',
				field : 'usernames',
				width : 300,
				align : 'left',
				formatter:role.usernamesFormatter
			}, {
				title : '描述',
				field : 'note',
				width : 100,
				align : 'left'
			}] ],
			onLoadSuccess:role.onLoadDgSuccess
		});
	},
	onLoadDgSuccess:function(){
		$('#jsChangeValidState').linkbutton('disable');
		$('#jsSettingRoleAcl').linkbutton('disable');
		$('#jsUpdateRole').linkbutton('disable');
	},
	roleNameFormatter:function(value,row,index){
		if(row.longName){
			return row.longName;
		}
		var companyId = row.companyId;
		var systemId = row.systemId;
		var node = common.zTree.companyTreeObj.getNodeByParam("id", row.companyId, null);
		var companyName = '暂无';
		var companyNo = '暂无';
		if(node){
			companyName = node.text;
			companyNo = node.code;
		}
		var systemName = role.systemsMap[systemId]?role.systemsMap[systemId]:'暂无';
		return companyNo+'-'+companyName+'-'+systemName+'-'+value;
	},
	companyNameFormatter:function(value,row,index){
		var node = common.zTree.companyTreeObj.getNodeByParam("id", row.companyId, null);
		if(node){
			return node.text;
		}
		return '未知';
	},
	//职责等级
	roleLevelFormatter:function(value, row, index){
		var txt = role.roleLevelMap[value];
		return txt?txt:'未知';
	},
	//职责状态
	validStateFormatter:function(value, row, index){
		if(value=="1"){
			return "<span style='color:green'>有效</span>";
		}else{
			return "<span style='color:red'>无效</span>";
		}
	},
	//所属系统
	systemIdFormatter:function(value, row, index){
		var txt = role.systemsMap[value];
		return txt?txt:'未知';
	},
	usernamesFormatter:function(value, row, index){
		var roleId = row.id;
		var roleName = row.name;
		
		if(value!=''){
			var values = [];
			var arr = value.split(';,;');
			for(var i = 0; i<arr.length ;i++){
				if(arr[i]!=''){
					var uArr = arr[i].split(',');
					var uNo = uArr[0];
					var uName = uArr[1];
					values.push(uName+'<i uNo="'+uNo+'" onclick="role.deleteRoleUser(this, \''+uNo+'\', \''+uName+'\', \''+roleId+'\', \''+roleName+'\')" class="jsDelRoleUser">×</i>');
				}
			}
			value = values.join('</span><span class="keep-style">');
			value = value.replace(/;,;+/ig, '　');
		}
		value = '<span class="keep-style">'+value+'</span>';
		return '<div class="jsPersonRoleStyle">'+value+'</div>';
	},
	ajaxSyncFromUcenter:function(){
		var url = basePath + "/managment/privilege/role/syncAllFromUcenter.do?sessionId="+jsSessionId;
		$.ajax({url:url, data:{}, async:true, type:'POST', dataType:'JSON', success:function(dt){
			if(dt.responseCode==1){
				showSuc("同步成功！");
				$('#dg').datagrid('reload');
			}
		} });
	},
	ajaxSyncUserRoleFromUcenter:function(){
		var url = basePath + "/managment/privilege/role/syncAllUserRoleFromUcenter.do?sessionId="+jsSessionId;
		$.ajax({url:url, data:{}, async:true, type:'POST', dataType:'JSON', success:function(dt){
			if(dt.responseCode==1){
				showSuc("同步成功！");
				$('#dg').datagrid('reload');
			}
		} });
	},
	syncAllUserRoleFromUcenter:function(){
		$.messager.confirm("信息", "确定要同步所有用户职责数据吗？", function(r) {
			if (r) {
				role.ajaxSyncUserRoleFromUcenter();
			}
		});
	},
	
	syncAllFromUcenter:function(){
		$.messager.confirm("信息", "确定要同步所有数据吗？", function(r) {
			if (r) {
				role.ajaxSyncFromUcenter();
			}
		});
	},
	genRoleCompanyIdsStr:function(){
		var cpnIdArr = [];
		$('.lb-cpn input:checked').each(function(i,o){
			cpnIdArr.push(o.value);
		});
		
		$('#jsRoleCompanyIdsStr').val(cpnIdArr.join(','));
	},
	//设置职责管理的组织
	settingCompany:function(){
		var rows=role.dgObj.datagrid("getSelections");
		if(!rows || rows.length <=0){
			$.messager.alert('信息','请选择职责','info');
            return false;
        }
		var roleRow = rows[0];
		ygDialog({    
		    title: '分配组织',    
		    width: 800,    
		    height: 'auto', 
		    closed: false,    
		    cache: false,    
		    maximizable:true,
		    href: basePath+'/managment/privilege/role/settingCompanyUI.do?sessionId='+jsSessionId+'&roleId='+roleRow.id,    
		    modal: true,
		    buttons : [{
				text:'保存',
				iconCls : 'icon-save',
				handler:function(dialog){
					var selectedRows = $('#jsCompanyDg').datagrid("getSelections");
					var companyIdArr = [];
					if(selectedRows.length>0){
						for(var i = 0; i< selectedRows.length; i++){
							companyIdArr.push(selectedRows[i].id);
						}
					}
					var data = {roleId:roleRow.id, companyIds:companyIdArr.join(',')};
					$.messager.progress(); 
					$.post(
						basePath+'/managment/privilege/role/settingCompany.do?sessionId='+jsSessionId,
						data,
						function(result){
							$.messager.progress('close');
							if(result.responseCode==100){
								dialog.close();
								role.dgObj.datagrid("reload");
							}else{
								$.messager.alert('信息','保存失败！','info');
							}
						},'json'
					);
				}
			}],
			onLoad : function (){
				$('#jsCompanyDg').treegrid({
					url :basePath+'/managment/privilege/company/ajaxList.do?sessionId='+jsSessionId,
					singleSelect:false,
					checkOnSelect:true,
					pageSize:100,
					pageList:[100, 200],
					pagination:true,
					idField:'id',
				    columns:[[  
				        {field:'id',title:'',width:100, checkbox:true},    
				        {field:'cname',title:'名称',width:100},    
				        {field:'code',title:'编码',width:100}   
				    ]],
				    onLoadSuccess:function(){
				    	for(var i = 0; i<roleCompanysJson.length ;i++){
				    		$('#jsCompanyDg').treegrid("select", roleCompanysJson[i].companyId);
				    	}
				    }
				});
			}
		});  
	}
};

//验证标识的唯一性
$.extend($.fn.validatebox.defaults.rules, {
    unique: {
	    validator: function(value, param){
	    	var valid=true;
	    	var formData=$("#formU").form("getData");
	    	
	    	if(/[\u4e00-\u9fa5]+/g.test(value)){
	    		this.message="标识不能有空格或中文字符！";
	    		return false;
	    	}
	    	$.ajax({
	    		async:false,
	    		url:basePath+'/managment/privilege/role/checkSnExsits.do?sessionId='+jsSessionId,
	    		data:{sn : value,id : formData.id},
	    		success:function(json){
	    			if(json==1){
	    				valid=false;
	    			}
	    		}
	    	});
	    	this.message="标识不能重复!";
	    	return valid;
	    },
	    message: '标识不能重复!'
    }
});