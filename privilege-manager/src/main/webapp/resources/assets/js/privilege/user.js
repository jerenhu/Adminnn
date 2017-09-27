/**
 * 用户对象
 */
var User = {
	orgTreeObj:null,
	datagridObj:null,
	init:function(){
		User.initTreeObj();
		User.initDatagrid();
		User.bindEvent();
	},
	
	//绑定事件 
	bindEvent:function(){
		$("#jsSyncUser").click(function(){
			User.syncUser();
		});
		//查询按钮
		$('#jsUserSearchBtn').click(function() {
			User.doSearch();
		});
		//回车事件
		$('#jsUserSearchFm').on('keydown', function(event){
			if(event.keyCode == 13){
				User.doSearch();
			}
		});
		
		//编辑用户
		$('#jsEditUser').click(function(){
			User.edit();
		});
		//新增用户
		$('#jsAddUser').click(function(){
			User.add();
		});
	},
	
	//编辑用户
	edit:function(){
		var data = User.datagridObj.datagrid("getSelected");
		if(!data){
			$.messager.alert('信息','请选择一条记录！','info');
			return;
		}
		ygDialog({    
		    title: '编辑用户',    
		    width: sizeObject.width,    
		    height: 'auto',    
		    closed: false,    
		    cache: false,    
		    href: basePath+'/managment/privilege/user/updateUI.do?sessionId='+jsSessionId+'&userId='+data.id,    
		    modal: true,
		    buttons : [{
				text:'保存',
				iconCls : 'icon-save',
				handler:function(dialog){
					var form=$("#formU");
					if(form.form('validate')){
						$.messager.progress(); 
						var fdata=form.serialize();
						var companyIdHide = $("#"+companyHideId).val();
						if(!companyIdHide || companyIdHide==""){
							$.messager.progress('close');
							$.messager.alert('信息','请选择公司下的部门！','info');
							return false;
						}else{
							$.post(basePath+'/managment/privilege/user/update.do?sessionId='+jsSessionId, fdata,
									function(result){
										$.messager.progress('close');
										if(result.responseCode==100){
											dialog.close();
											$('#userdg').datagrid("reload");
										}else{
											$.messager.alert('信息','保存失败！','info');
										}
									},'json');
						}
					}
				}
			}],
			onLoad : function (){
				var form=$("#formU");
				form.form("load",data);
				//得到过期时间
				getFailTime();
			}
		});
	},

	//新增用户
	add:function() {
		var form = null;
		var nodes = common.zTree.orgTreeObj.getSelectedNodes();
		if(!nodes || nodes[0].text==common.zTree.rootText){
			$.messager.alert('信息','请选择公司下的部门！','info');
            return false;
        }
		ygDialog({    
		    title: '新增用户',    
		    width: sizeObject.width,    
		    height: 'auto',    
		    closed: false,    
		    cache: false,    
		    href: basePath + "/managment/privilege/user/insertUI.do?sessionId="+jsSessionId,    
		    modal: true,
		    buttons : [{
				text:'保存',
				iconCls : 'icon-save',
				handler:function(dialog){
					if(form.form('validate')){
						$.messager.progress(); 
						var data=form.serialize();
						
						var companyIdHide = $("#"+companyHideId).val();
						if(!companyIdHide || companyIdHide==""){
							$.messager.progress('close');
							$.messager.alert('信息','请选择公司下的部门！','info');
							return false;
						}else{
							$.post(basePath + '/managment/privilege/user/insert.do?sessionId='+jsSessionId,
									data,
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
				}
			}],
			onLoad : function (){
				form=$("#formA");
				//得到过期时间
				getFailTime();
				var n = common.zTree.orgTreeObj.getSelectedNodes();
				if(n!=null){  
					$("#jsCompanyIdHide").val(n.companyId);
					$("#jsDepartmentIdHide").val(n.id);
					$("#jsDepartmentText").html(n.text);
		        }
			}
		});    
	},
	
	//查询数据
	doSearch:function doSearch(){
		var myForm = $('#jsUserSearchFm');
		var _keyword = $('#jsKeyword');
		_keyword.val($.trim(_keyword.val()));
		var data = myForm.serializeJson();
		$.each(data,function(index,dom){ //去空格
			if(index && dom){
				data[index]=$.trim(dom);
			}
		});
		User.datagridObj.datagrid('load', data);
		var node = common.zTree.orgTreeObj.getNodeByParam("id", common.zTree.rootText, null);
		common.zTree.orgTreeObj.selectNode(node);
    	_keyword.focus();
	},
	
	//初始化表格数据
	initDatagrid:function(){
		User.datagridObj = $('#userdg').datagrid({
			url:basePath+"/managment/privilege/user/ajaxlist.do?sessionId="+jsSessionId,
			singleSelect : true,
			fitColumns : true,
			checkOnSelect : true,
			selectOnCheck : true,
			pageSize: 20,
			pageList: [20,50,100],
			columns : [ [ {
				checkbox : true
			},{
				title : '姓名',
				field : 'realName',
				width : 30,
				formatter:function(value,row,index){
					var sex = row.sex;
					var result = value;
					
					if(sex == 0){
						result = '<i class="gender male">&nbsp;</i>' + value;
					}else if(sex == 1){
						result = '<i class="gender female">&nbsp;</i>' + value;
					}
					
					var _failFlag = row.failFlag;
					//1失效，0未失效 ,2未设置失效时间
					if(_failFlag==0){
					}else if(_failFlag==1){
						result+='<i class="is-fail" title="已失效"></i>';
					}else if(_failFlag==2){
		           		//return 'background-color:#6293BB;color:#fff;';
					}
					
					return result;
				},
				align : 'left'
			},{
				title : '工号',
				field : 'username',
				width : 30,
				align : 'center'
			}, {
				title : '公司',
				field : 'companyName',
				width : 60,
				align : 'left'
			}, {
				title : '部门',
				field : 'deptName',
				width : 50,
				align : 'left'
			},{
				title : '角色',
				field : 'roles',
				width : 100,
				align : 'left',
				formatter:User.pRoleNameFormatter
			} , {
				title : '手机',
				field : 'mobile',
				width : 50,
				align : 'left'
			}, {
				title : '座机',
				field : 'phone',
				width : 50,
				align : 'left'
			}, {
				title : '邮箱',
				field : 'email',
				width : 80,
				align : 'left'
			},{
				title : '账户失效时间',
				field : 'failureTimeStr',
				width : 50,
				align : 'left'
			},{
				title : '密码过期时间',
				field : 'pwdFtimeStr',
				width : 50,
				align : 'left'
			}] ],
			onLoadSuccess : function() {
				setTimeout(function(){
		    		User.initToolTip();
		    	}, 1000);
				$('.datagrid-body td[field="deptName"]>[class$="deptName"]').tooltip({
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
			onSelect:function(rowIndex, rowData){
				var deptId = rowData.departmentId;
				var node = common.zTree.orgTreeObj.getNodeByParam("id", deptId, null);
				if(node){
					common.zTree.orgTreeObj.selectNode(node);
				}
			},
			onBeforeLoad : function(param) {//加载之前设置参数
				var n = $("#dept").tree("getSelected");
				if(n!=null){  
					param.isCompany=n.isCompany; //1：表示是公司---在方法userPageTreeLoadFilter中设置的
					if(param.isCompany && param.isCompany==1){
						param.companyId=n.id;
					}else{
						param.departmentId=n.id;
						param.companyId=n.companyId;
					}
					
		        }/* else{
		        	return false;
		        } */
			}
		});
	},
	initToolTip:function(){
		$('.jsPersonRoleStyle').each(function(i, o){
    		var _thisObj = $(o);
    		var _html = _thisObj.html();
    		_html = _html!=''?_html.replace(/[　]+/ig, '<br/>'):'';
    		
			_thisObj.tooltip({
				position: 'bottom',
				content: '<div style="color:#fff" >'+_html+'</div>',
				onShow: function(){
					var t = $(this);
					t.tooltip('tip').css({
						backgroundColor: '#666',
						borderColor: '#666'
					});
					
					t.tooltip('tip').unbind().bind('mouseenter',function(){
                        t.tooltip('show');
                    }).bind('mouseleave', function(){
                        t.tooltip('hide');
                    });
				}
			});
    	});
	},
	pRoleNameFormatter:function(value, row, index){
		if(value!=''){
			value = value.replace(/;,;+/ig, '　');
		}
		return '<div class="jsPersonRoleStyle">'+value+'</div>';
	},
	//初始化树形结构
	initTreeObj:function(){
		common.zTree.initDeptZtree($('#dept'), function(event, treeId, treeNode){
			//var url = basePath+'/addrbook/addressBook/getPersonnelData.jhtml';
			if(treeNode.text==common.zTree.rootText){
				var data = {};
				User.datagridObj.datagrid({queryParams:data });
			}else{
				var data = {companyId:treeNode.companyId, departmentId:treeNode.id};
				User.datagridObj.datagrid({queryParams:data});
			}
		});
	},
	
	// 同步用户数据【来自EHR的用户数据】
	syncUser:function() {
		$.messager.confirm("信息", "同步所有用户数据时需要等待一定时间，确定要同步吗？", function(r) {
			if(r){
				$.ajax({
					type:'post',
					url:basePath+'/managment/privilege/user/syncUser.do?sessionId='+jsSessionId,
					dataType:'json',
					beforeSend:function(){
						$.mask();
					},
					success:function(text){
						$.unmask({obj:window});
						if(text.status==1){
							$('#userdg').datagrid("reload");
							showSuc("操作成功");
						}else{
							showWarn('同步EHR用户数据失败！');
						}
					}, error:function(){
						$.unmask();
					}
				});
			}
		});
	},

	//编辑页面
	ipt:{
		init:function(){
			User.ipt.bindEvent();
		},
		
		//绑定事件
		bindEvent:function(){
			// url:'${basePath}/managment/privilege/dept/ajaxlistac.do?sessionId=${sessionId}',required:true,loadFilter:User.ipt.userPageTreeLoadFilter,onSelect:User.ipt.userPageComboTreeClick,readonly:true,
			var urlDept = basePath + '/managment/privilege/dept/ajaxlistac.do?sessionId='+jsSessionId;
			$("#jsDepartmentId").combotree({url:urlDept, required:true, loadFilter:User.ipt.userPageTreeLoadFilter, onSelect:User.ipt.userPageComboTreeClick, readonly:true});
			//url:'${basePath}/managment/privilege/user/getUserSystemIds.do?sessionId=${sessionId}&userId=${userId }',required:true,loadFilter:User.ipt.pagerFilterSys,readonly:true,multiple:true,
			// var uId = $('#js_userId').val();
			// var urlSys = basePath + '/managment/privilege/user/getUserSystemIds.do?userId='+uId+'&sessionId='+jsSessionId;
			// $("#jsSystemIds").combotree({url:urlSys, required:false,loadFilter:User.ipt.pagerFilterSys,readonly:true,multiple:true});
		},
		
		//
		userPageTreeLoadFilter:function(data){
			var companyData=null;
			var deptData=null;
			if(data && data.company){
				companyData=data.company;
				if(companyData && companyData.length>0){
					$.each(companyData,function(index,c){
						c.name=c.cname;
						c._parentId=c.pid;
						c.isCompany=1; /**1:标识是公司*/
					});
				}
				companyData=common.treeLoadFilter(companyData,{source:2}); //公司组成树形
				
				if(data && data.dept){
					deptData=common.treeLoadFilter(data.dept,{source:2});//部门组成树形
				}
				$.each(companyData[0].children,function(index,c){
					$.each(deptData,function(ind,d){
						if(c && d && d.companyId==c.id){
							c.children.push(d);
						}
					});
				});
			}
			return deptData;
		},
		userPageComboTreeClick:function(node){
			if(node==null || node.isCompany==1){
				setCompanyId("");
				$.messager.alert('信息','请选择公司下的部门！','info');
				return false;
			}else{
				setCompanyId(node.companyId);
			}
		},
		setCompanyId:function(companyId){
			$("#"+companyHideId).val(companyId);
		},
		pagerFilterSys:function(data){
	        if ($.isArray(data)){    // is array  
	        	for(var i=0; i<data.length; i++){
	        		data[i].text = data[i].name;
	        	}
	        }
	        return data;
		}
	}, 
	refresh:function(){
		window.location.reload();
	}
	
};