<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/page/share/taglib.jsp"%>
<%-- 查询js --%>
<%@ include file="/WEB-INF/page/share/dosearch.jspf"%>
<c:set var="systemSn" value="privilege" scope="page"/>
<c:set var="nameSpace" value="user" scope="page"/>
<script type="text/javascript" language="javascript" src="${basePath}/resources/assets/js/privilege/user.js"></script>
<style>
	.datagrid-row td[field='realName']>div {overflow: visible; }
	i.isFail{float: right;display: block;position: absolute;top: -11px;right: 0px;background: red;}
</style>
<title>用户管理</title>
</head>
<body class="easyui-layout">   
    <div data-options="region:'west',title:'公司列表',split:true" style="width:200px;">
    	<ul id="dept" class="ztree"></ul>
    </div>   
    <div data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true">   
            <div data-options="region:'north'" style="height:88px;">
            	<div id="toolbar" class="easyui-toolbar">
            		<%-- 刷新 --%>
					<c:if test="${my:hasPermission(sessionId,systemSn,nameSpace, queryValue)}">
						<a href="javascript:void(0)" iconCls="icon-refresh" plain="true" onclick="User.refresh()">刷新</a>
						<a>-</a>
					</c:if>
					<!-- 用户同步，来自EHR的数据 -->
					<c:if test="${my:hasPermission(sessionId,systemSn,nameSpace, 7)}">
						<a href="javascript:;" iconCls="icon-sync" plain="true" id="jsSyncUser">同步人员主数据</a>
						<a>-</a>
					</c:if>
					<c:if test="${my:hasPermission(sessionId,systemSn,nameSpace, 0)}">
						<a href="javascript:;" iconCls="icon-add" plain="true" id="jsAddUser">添加</a>
						<a>-</a>
					</c:if>
					<c:if test="${my:hasPermission(sessionId,systemSn,nameSpace, 2)}">
						<a href="javascript:;" iconCls="icon-edit" plain="true" id="jsEditUser" >修改</a>
						<a>-</a>
					</c:if>
					<c:if test="${my:hasPermission(sessionId,systemSn,nameSpace, 3)}">
						<a href="javascript:;" iconCls="icon-del" plain="true" onclick="del()">删除</a>
						<a>-</a>
					</c:if>
					<c:if test="${my:hasPermission(sessionId,systemSn,nameSpace, 1)}">
						<a href="javascript:;" iconCls="icon-view-details" plain="true" onclick="detail()">查看详情</a>
						<a>-</a>
					</c:if>
					
					<c:if test="${my:hasPermission(sessionId,systemSn,nameSpace, 2)}">
						<a href="javascript:;" iconCls="icon-aduit" plain="true" onclick="addrole()">分配职责</a>
						<a>-</a>
					</c:if>
					
					<%--<c:if test="${my:hasPermission(sessionId,systemSn,nameSpace, 6)}">
						<a href="javascript:;" iconCls="icon-operate" plain="true" onclick="adduseracl()">操作授权</a>
						<a>-</a>
					</c:if>
					<c:if test="${my:hasPermission(sessionId,systemSn,nameSpace, 2)}">
						<a href="javascript:;" iconCls="icon-edit" plain="true" onclick="updatePassword()">修改密码</a>
						<a>-</a>
					</c:if>

					<c:if test="${my:hasPermission(sessionId,systemSn,nameSpace, 2)}">
						<a href="javascript:;" iconCls="icon-reset-account" plain="true" onclick="updateFailTime()">重置账户有效期</a>
						<a>-</a>
					</c:if>
					<c:if test="${my:hasPermission(sessionId,systemSn,nameSpace, 2)}">
						<a href="javascript:;" iconCls="icon-stop" plain="true" onclick="setFailure()">立即失效</a>
						<a>-</a>
					</c:if>
					--%>
				</div>
				<div style="padding:6px;">
					<form name="jsUserSearchFm" id="jsUserSearchFm" method="post" onsubmit="return false;">
		                  <table class="search-tb">
		                     <tbody>
		                        <tr>
		                        	<td>关键字：
		                        		<input class="ipt" name="keyword" id="jsKeyword" style="width:200px;" placeholder="工号/姓名"/>
		                        	</td>
		                            <td>
										<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" id="jsUserSearchBtn">查询</a>
		                            </td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
            </div>   
            <div data-options="region:'center'">
    			<table id="userdg"></table>
            </div>   
        </div>
    </div>   
</body>
<script>
$(function(){
	User.init();
});
	var companyHideId='jsCompanyIdHide';
	var sizeObject={width:750,height:450};
	$.parser.parse();
	/*
	$('#dept').tree({
		loadFilter: userPageTreeLoadFilter,
		url:'${basePath}/managment/privilege/dept/getOrgTree.do?sessionId=${sessionId}',
		onSelect: function(node){
			//使用'clearSelections':防止读取到了删除的数据
			$('#userdg').datagrid("clearSelections");
			$('#userdg').datagrid("reload");
		},
		onLoadSuccess:function(node,data){  
			$("#dept").tree("collapseAll")
	        // $("#dept li:eq(0)").find("div").addClass("tree-node-selected");   //设置第一个节点高亮  
	        //var n = $("#dept").tree("getSelected");  
	       // if(n!=null){  
	        //    $("#dept").tree("select",n.target);    //相当于默认点击了一下第一个节点，执行onSelect方法  
	        //} 
	    },
	    onDblClick:function(node){
	    }
	});*/
	
	function pagerFilterSys(data){
        if ($.isArray(data)){    // is array  
        	for(var i=0; i<data.length; i++){
        		data[i].text = data[i].name;
        	}
        }
        return data;
	}
	
	function userPageComboTreeClick(node){
		if(node==null || node.isCompany==1){
			setCompanyId("");
			$.messager.alert('信息','请选择公司下的部门！','info');
			return false;
		}else{
			setCompanyId(node.companyId);
		}
	}
	
	function setCompanyId(companyId){
		$("#"+companyHideId).val(companyId);
	}
	
	//验证标识的唯一性
	$.extend($.fn.validatebox.defaults.rules, {
	    unique: {
	    validator: function(value, param){
	    	var valid=true;
	    	var formData=$("#formU").form("getData");
	    	$.ajax({
	    		async:false,
	    		url:'${basePath}/managment/privilege/user/checkUserNameExsits.do?sessionId=${sessionId}',
	    		data:{username : value , id : formData.id},
	    		success:function(json){
	    			if(json==1){
	    				valid=false;
	    			}
	    		}
	    	});
	    	return valid;
	    },
	    message: '用户名不能重复!'
	    }
	});
	
	//得到过期时间
	function getFailTime(){
		//选择有效期
		$("#jsfailMonth").combobox({
			onSelect:function(record){
				//得到过期时间
				getFailTimeAjax(record.value);
			}
		});
	}
    function getFailTimeAjax(month){
    	$.ajax({
			url : "${basePath}/managment/privilege/user/getFailTime.do?sessionId=${sessionId}",
			data:{month:month},
			dataType : 'json',
			success : function(text) {
				$.messager.progress('close');
				if(text && text!=''){
					$("#jsfailTime").html("过期时间："+text);
				}else{
					$("#jsfailTime").html("");
				};
			}
		});
    }
	
	
	function detail() {
		var data=$('#userdg').datagrid("getSelected");
		if(!data){
			$.messager.alert('信息','请选择一条记录！','info');
			return;
		}
		ygDialog({    
		    title: '用户详情',    
		    width: sizeObject.width,    
		    height: 'auto',   
		    closed: false,    
		    cache: false,    
		    href: '${basePath}/managment/privilege/user/detailUI.do?sessionId=${sessionId}&userId='+data.id,    
		    modal: true,
			onLoad : function (){
				var form=$("#formD");
				form.form("load",data);
			}
		});    
	}
	
	function del() {
		var rows=$('#userdg').datagrid("getSelections");
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
						url : "${basePath}/managment/privilege/user/delete.do?sessionId=${sessionId}&ids="+ id,
						dataType : 'json',
						success : function(text) {
							$.messager.progress('close');
							if (text.responseCode == 101) {
								$.messager.alert(text.responseMsg);
							}else{
								//使用'clearSelections':防止读取到了删除的数据
								$('#userdg').datagrid("clearSelections");
								$('#userdg').datagrid("reload");
							}
						}
					});
				}
			});
		} else {
			$.messager.alert('信息','请选择要删除的记录！','info');
		}
	}
	
	
	function setFailure() {
		var data=$('#userdg').datagrid("getSelected");
		if(!data || data.length > 1){
			$.messager.alert('信息','请选择一条记录！','info');
			return;
		}
		var userId = data.id;
		$.ajax({
			type:'post',
			url:'${basePath}/managment/privilege/user/setFailure.do?sessionId=${sessionId}',
			data:{userId:userId},
			dataType:'json',
			success:function(text){
				if(text.responseCode==1){
					$('#userdg').datagrid("reload");
					showSuc("失效成功");
				}else{
					showWarn('立即失效失败！');
				}
			}
		});
	}
	
	function addrole() {
		var row=$('#userdg').datagrid("getSelected"),roles=[];
		if(!row){
			$.messager.alert('信息','请选择一条记录！','info');
			return;
		}
		
		function addRoleInfo(_obj){//添加角色
			var rowData=_obj.rowData;
			for(var i=0,len=roles.length;i<len;i++){
				if(roles[i]==rowData.id){
					return;
				}
			}
			roles.push(rowData.id);
		}
		
		function delRoleInfo(_obj){//删除角色
			var rowData=_obj.rowData;
			for(var i=0,len=roles.length;i<len;i++){
				if(roles[i]==rowData.id){
					roles.splice(i, 1);
					return;
				}
			}
		}
		ygDialog({    
		    title: '给 <font color="red">'+row.realName+'</font> 分配职责',    
		    width: 900,    
		    height: 'auto',
		    closed: false,    
		    cache: false,
		    maximizable:true,
		    resizable:true,
		    href: "${basePath}/managment/privilege/user/insertRoleUI.do?sessionId=${sessionId}&id="+row.id+"&companyId="+row.companyId+"&personNo="+row.username,
		    //modal: true,
		    buttons : [{
				text:'保存',
				iconCls : 'icon-save',
				handler:function(dialog){
					/*
					$.ajax({
						type:'post',
						url:'${basePath}/managment/privilege/user/saveUserRole.do?sessionId=${sessionId}',
						data:{userId:row.id, userNo:row.username, roleIds:roles.join(",")},
						dataType:'json',
						success:function(text){
							if(text.responseCode==100){
								dialog.close();
								$('#userdg').datagrid("reload");
							}else{
								$.messager.alert('信息','保存失败！','info');
							}
						}
					});*/
					
					//判断角色是否有变动
					if(userRoleObj.checkPersonRolesChange()){
						var rows = $('#userdg').datagrid('getChecked');
						var ajaxSave = function(){
							//已经选择的角色
							var allrows = userRoleObj.personRoles;
							var ids = [];
							var roleIdArr = [];
							$.each(allrows, function(i, row) {
								roleIdArr.push(row.roleId);
							});
							var roleids = roleIdArr.join(',');
							var url = basePath + '/managment/privilege/user/saveUserRole.do?sessionId='+_jsSessionId;
							$.ajax({
								type : 'POST',
								dataType : 'JSON',
								url : url,
								data : {
									userId:userRoleObj.personId,
									userNo:userRoleObj.personNo,
									roleIds:roleids
								},
								error: function (XMLHttpRequest, textStatus, errorThrown) {
								},
								success : function(data) {
									if (data.responseCode == 100) {
										$('#userdg').datagrid('reload');
										showSuc('设置成功');
										dialog.close();
									} else {
										$.messager.alert('警告',data.responseMsg);
									}
								}
							});
						};
						
						$.messager.confirm('温馨提示','确定要修改'+rows[0].realName+'('+rows[0].username+')的角色吗?',function(r){
							if (r){
								ajaxSave();
							}
						});
					}else{
						dialog.close();
					}
					
				}
			}],
			onLoad : function (){
				$.ajax({
					async:false,
					type:'post',
					url:'${basePath}/managment/privilege/user/getRoleByUserId.do?sessionId=${sessionId}',
					data:{userId:row.id},
					dataType:'json',
					success:function(text){
						if(text && text.length>0){
							for(var i=0;i<text.length;i++){
								roles.push(text[i].id);
							}
						}
					}
				});
				//$.parser.parse($('#roledg'));
				$('#roledg').treegrid({
					url: '${basePath}/managment/privilege/user/getRoles.do?sessionId=${sessionId}&userId='+row.id+'&companyId='+row.companyId,
					method : 'post',
					singleSelect : false,
					idField : "id",
					fitColumns : true,
					checkOnSelect : true,
					selectOnCheck : true,
					pagination : true,
					pageSize: 1000,
					pageList: [1000],
					columns : [ [ {
						field:'id',
						checkbox : true
					}, {
						title : '角色名称',
						field : 'name',
						width : 100,
						align : 'left'
					}, {
						title : '角色标识',
						field : 'sn',
						width : 50,
						align : 'left'
					}, {
						title : '备注',
						field : 'note',
						width : 100,
						align : 'left'
					} ] ],
					onLoadSuccess : function(row, data) {
						var _data =data.datas;
						 //选中角色
						 if ($.isArray(_data)){    // is array  
							 for(var i=0;i<_data.length;i++){
								 var row=_data[i];
								 if(row.checked){
									 $('#roledg').treegrid("select",row.id);
								 }
							 }
						 }
						 $('.datagrid-body td[field="name"]>[class$="name"]').tooltip({
				                content: function(){
				                    var txt = $(this).text();
				                    return txt;
				                },
				                onShow: function(){
			                        var t = $(this);
			                        t.tooltip('tip').unbind().bind('mouseenter',function(){
			                            t.tooltip('show');
			                        }).bind('mouseleave', function(){
			                            t.tooltip('hide');
			                        });
			                    }
				            });
					},
					onSelect : function(rowData){
						addRoleInfo({rowData:rowData});
					},
					onUnselect : function(rowData){
						delRoleInfo({rowData:rowData});
					},
					onSelectAll : function(rows){
						$.each(rows,function(index,rowData){
							if(rowData){
								addRoleInfo({rowData:rowData});
							}
						});
					} ,
					onUnselectAll : function(rows){
						$.each(rows,function(index,rowData){
							if(rowData){
								delRoleInfo({rowData:rowData});
							}
						});
					} 
				});
				
				//查询方法
				var doSearchRole = function doSearch(){
					var myForm = $('#searchFmRole');
					var data = myForm.serializeJson();
					$.each(data,function(index,dom){ //去空格
						if(index && dom){
							data[index]=$.trim(dom);
						}
					});
					/*$('#userdg').datagrid({
						url:basePath+'/search/sku/ajaxList.do',
						queryParams:data
					});*/
					$('#roledg').treegrid('load', data);
				};
				//查询按钮
				$('#searchBtnRole').click(function() {
					doSearchRole();
				});
				//回车事件
				$('#searchFmRole').on('keydown', function(event){
					if(event.keyCode == 13){
						doSearchRole();
					}
				});
			}
		});    
	}
	
	function adduseracl() {
		var row=$('#userdg').datagrid("getSelected");
		if(!row){
			$.messager.alert('信息','请选择一条记录！','info');
			return;
		}
		ygDialog({   
			id:'dialog-roleModuleUI',
		    title: '给 <font color="red"> '+row.realName+' </font>授权',    
		    width: 800,    
		    height: 'auto',    
		    cache: false,    
		    href: "${basePath}/managment/privilege/acl/roleModuleUI.do?releaseSn=role&sessionId=${sessionId}&id=" + row.id,
		    modal: true,
		    buttons:[{text:'取消', iconCls:'icon-close',handler:function(dialog){dialog.close();}}],
			onLoad : function (){
				$('#dialog-roleModuleUI .dialog-button .l-btn-text.icon-close').text('关闭');
				//系统菜单
				$('#systemMenu').tree({
					url:'${basePath}/managment/privilege/module/getsystems.do?sessionId=${sessionId}',
					onSelect: function(node){
						//取消绑定的click事件
						$("#selectAllBtn .btn,.mbtn").unbind("click"); 
						//重新加载treegrid的数据
						$.post("${basePath}/managment/privilege/module/getAllPriValBySystemSn.do?sessionId=${sessionId}",{systemSn : node.sn, type: '${nameSpace}', releaseId : row.id},function(data){
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
						title : '权限值',
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
								} */
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
								url:'${basePath}/managment/privilege/acl/setAcl.do?sessionId=${sessionId}',
								data:{releaseId:row.id,releaseSn:'${nameSpace}',systemSn:node.sn,moduleId:name,moduleSn:moduleSn,position:value,yes:yes}
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
								url:'${basePath}/managment/privilege/acl/setAclByModule.do?sessionId=${sessionId}',
								data:{releaseId:row.id,releaseSn:'${nameSpace}',systemSn:node.sn,moduleId:this.id,yes:yes}
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
								url:'${basePath}/managment/privilege/acl/setAllAcl.do?sessionId=${sessionId}',
								data:{releaseId:row.id,releaseSn:'${nameSpace}',systemSn:node.sn,yes:yes}
							});
						});
					}
				});
			}
		});    
	}
	
	//修改密码
	function updatePassword() {
		var row=$('#userdg').datagrid("getSelected");
		if(!row){
			$.messager.alert('信息','请选择一条记录！','info');
			return;
		}
		ygDialog({    
		    title: '修改密码',    
		    width: 400,    
		    height: 230,    
		    closed: false,    
		    cache: false,    
		    href: "${basePath}/managment/privilege/user/udpatePasswordUI.do?sessionId=${sessionId}",
		    modal: true,
		    buttons : [{
				text:'保存',
				iconCls : 'icon-save',
				handler:function(dialog){
					var form=$("#formP");
					if(form.form('validate')){
						$.messager.progress(); 
						var fdata=form.serialize();
						$.post(
							'${basePath}/managment/privilege/user/updatePassowrd.do?sessionId=${sessionId}',
							fdata,
							function(result){
								$.messager.progress('close');
								if(result.responseCode==100){
									dialog.close();
									$('#userdg').datagrid("reload");
								}else{
									$.messager.alert('信息','保存失败！','info');
								}
							},'json'
						);
					}
				}
			}],
			onLoad : function (){
				var form=$("#formP");
				form.form("load",row);
			}
		}); 
	}
	//重置账户有效期
	function updateFailTime() {
		var row=$('#userdg').datagrid("getSelected");
		if(!row){
			$.messager.alert('信息','请选择一条记录！','info');
			return;
		}
		ygDialog({    
		    title: '重置账户有效期',    
		    width: 400,    
		    height: 220,    
		    closed: false,    
		    cache: false,    
		    href: "${basePath}/managment/privilege/user/updateFailTimeUI.do?sessionId=${sessionId}",
		    modal: true,
		    buttons : [{
				text:'保存',
				iconCls : 'icon-save',
				handler:function(dialog){
					var form=$("#formP");
					if(form.form('validate')){
						$.messager.progress(); 
						var fdata=form.serialize();
						$.post(
							'${basePath}/managment/privilege/user/updateFailTime.do?sessionId=${sessionId}',
							fdata,
							function(result){
								$.messager.progress('close');
								if(result.responseCode==100){
									dialog.close();
									$('#userdg').datagrid("reload");
								}else{
									$.messager.alert('信息','保存失败！','info');
								}
							},'json'
						);
					}
				}
			}],
			onLoad : function (){
				var form=$("#formP");
				form.form("load",row);
				$('#jsfailMonth').combobox('setValue',3);
				var _month=$('#jsfailMonth').combobox('getValue');
				//得到过期时间
				getFailTimeAjax(_month);
				//得到过期时间
				getFailTime();
				$('#jsfailMonth').combobox('setValue',3);
			}
		}); 
	}
</script>
</html>