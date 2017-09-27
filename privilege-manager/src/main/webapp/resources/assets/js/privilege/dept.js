/**
 * 部门对象
 */
var Dept = {
	companyTreeObj:null,
	datagridObj:null,
	init:function(){
		Dept.initTreeObj();
		Dept.initDatagrid();
		Dept.bindEvent();
	},
	
	//绑定事件 
	bindEvent:function(){
		
	},
	//初始化表格数据
	initDatagrid:function(){
		Dept.datagridObj = $('#tg');
//		Dept.datagridObj = $('#userdg').datagrid({
//			url:basePath+"/managment/privilege/user/ajaxlist.do?sessionId="+jsSessionId,
//			singleSelect : false,
//			fitColumns : true,
//			checkOnSelect : true,
//			selectOnCheck : true,
//			pageSize: 20,
//			pageList: [20,50,100],
//			columns : [ [ {
//				checkbox : true
//			},{
//				title : '姓名',
//				field : 'realName',
//				width : 30,
//				formatter:function(value,row,index){
//					var sex = row.sex;
//					var result = value;
//					
//					if(sex == 0){
//						result = '<i class="gender male">&nbsp;</i>' + value;
//					}else if(sex == 1){
//						result = '<i class="gender female">&nbsp;</i>' + value;
//					}
//					
//					var _failFlag = row.failFlag;
//					//1失效，0未失效 ,2未设置失效时间
//					if(_failFlag==0){
//					}else if(_failFlag==1){
//						result+='<i class="is-fail" title="已失效"></i>';
//					}else if(_failFlag==2){
//		           		//return 'background-color:#6293BB;color:#fff;';
//					}
//					
//					return result;
//				},
//				align : 'left'
//			},{
//				title : '工号',
//				field : 'username',
//				width : 30,
//				align : 'center'
//			}, {
//				title : '公司',
//				field : 'companyName',
//				width : 60,
//				align : 'left'
//			}, {
//				title : '部门',
//				field : 'deptName',
//				width : 50,
//				align : 'left'
//			},{
//				title : '角色',
//				field : 'roles',
//				width : 100,
//				align : 'left',
//				formatter:User.pRoleNameFormatter
//			} , {
//				title : '手机',
//				field : 'mobile',
//				width : 50,
//				align : 'left'
//			}, {
//				title : '座机',
//				field : 'phone',
//				width : 50,
//				align : 'left'
//			}, {
//				title : '邮箱',
//				field : 'email',
//				width : 80,
//				align : 'left'
//			},{
//				title : '账户失效时间',
//				field : 'failureTimeStr',
//				width : 50,
//				align : 'left'
//			},{
//				title : '密码过期时间',
//				field : 'pwdFtimeStr',
//				width : 50,
//				align : 'left'
//			}] ],
//			onLoadSuccess : function() {
//				setTimeout(function(){
//		    		User.initToolTip();
//		    	}, 1000);
//			},
//			onBeforeLoad : function(param) {//加载之前设置参数
//				var n = $("#dept").tree("getSelected");
//				if(n!=null){  
//					param.isCompany=n.isCompany; //1：表示是公司---在方法userPageTreeLoadFilter中设置的
//					if(param.isCompany && param.isCompany==1){
//						param.companyId=n.id;
//					}else{
//						param.departmentId=n.id;
//						param.companyId=n.companyId;
//					}
//					
//		        }/* else{
//		        	return false;
//		        } */
//			}
//		});
	},
	//初始化树形结构
	initTreeObj:function(){
		var url = basePath+'/managment/privilege/dept/ajaxlist.do?sessionId='+jsSessionId;
		common.zTree.initCompanyTree($('#company'), function(event, treeId, treeNode){
			Dept.datagridObj.treegrid("clearSelections");
			Dept.datagridObj.treegrid({
				url:url,
				queryParams:{companyId:treeNode.id}
			});
		});
		setTimeout(function(){
			var node = common.zTree.companyTreeObj.getNodeByParam("pId", "", null);
			if(node){
				Dept.datagridObj.treegrid({
					url:url,
					queryParams:{companyId:node.id}
				});
			}
		}, 500);
		
	}
};