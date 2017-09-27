//公共js
var common = {
	//树加载时的Filter:递归设置根节点的子节点(children)信息
    //-->data:后台返回的数据，param：自定义的参数对象{source:1/2}，source：1表示index.jsp，2表示user_page.jsp
	treeLoadFilter : function(data,param) {
		var _source=param.source;
		if (data && $.isArray(data)) { // is array
			if(_source && _source==2){
				for (var i = 0; i < data.length; i++) {
					data[i].text = data[i].name;
				}
			}
			var returnData = [];
			for (var i = 0; i < data.length; i++) {
				if (!data[i].pid) {// 根节点-->设置children节点信息
					common.setChildren(data, i, returnData, 1);
				}
			}
			return returnData;
		}
	},
	/** 封装节点的children信息 */
	setChildren : function(data, i, returnData, status) {
		data[i].children = [];
		if (data[i].pid && status == 0) {// 是子节点
			common.setChildrenInfo(data, i, returnData);
		} else if (status == 1) {// 根节点
			common.setChildrenInfo(data, i, returnData);
			returnData.push(data[i]);
		}
	},
	setChildrenInfo : function(data, i, returnData) {
		for (var j = 0; j < data.length; j++) {
			if (data[j].pid && data[j].pid == data[i].id) {
				common.setChildren(data, j, returnData, 0);
				data[i].children.push(data[j]);
			}
		}
	},

	/**
	 * 格式化日期（不含时间）
	 */
	formatterDate : function(date) {
		if (date == undefined) {
			return "";
		}
		/* json格式时间转js时间格式 */
		date = date.substr(1, date.length - 2);
		var obj = eval('(' + "{Date: new " + date + "}" + ')');
		var date = obj["Date"];
		if (date.getFullYear() < 1900) {
			return "";
		}

		var datetime = date.getFullYear()
				+ "-"// "年"
				+ ((date.getMonth() + 1) > 10 ? (date.getMonth() + 1) : "0"
						+ (date.getMonth() + 1)) + "-"// "月"
				+ (date.getDate() < 10 ? "0" + date.getDate() : date.getDate());
		return datetime;
	},
	/**
	 * 格式化日期（含时间"00:00:00"）
	 */
	formatterDate2 : function(date) {
		if (date == undefined) {
			return "";
		}
		/* json格式时间转js时间格式 */
		date = date.substr(1, date.length - 2);
		var obj = eval('(' + "{Date: new " + date + "}" + ')');
		var date = obj["Date"];
		if (date.getFullYear() < 1900) {
			return "";
		}

		/* 把日期格式化 */
		var datetime = date.getFullYear()
				+ "-"// "年"
				+ ((date.getMonth() + 1) > 10 ? (date.getMonth() + 1) : "0"
						+ (date.getMonth() + 1)) + "-"// "月"
				+ (date.getDate() < 10 ? "0" + date.getDate() : date.getDate())
				+ " " + "00:00:00";
		return datetime;
	},
	/**
	 * 格式化去日期（含时间）
	 */
	formatterDateTime : function(date) {
		if (date == undefined) {
			return "";
		}
		/* json格式时间转js时间格式 */
		date = date.substr(1, date.length - 2);
		var obj = eval('(' + "{Date: new " + date + "}" + ')');
		var date = obj["Date"];
		if (date.getFullYear() < 1900) {
			return "";
		}

		var datetime = date.getFullYear()
				+ "-"// "年"
				+ ((date.getMonth() + 1) > 10 ? (date.getMonth() + 1) : "0"
						+ (date.getMonth() + 1))
				+ "-"// "月"
				+ (date.getDate() < 10 ? "0" + date.getDate() : date.getDate())
				+ " "
				+ (date.getHours() < 10 ? "0" + date.getHours() : date
						.getHours())
				+ ":"
				+ (date.getMinutes() < 10 ? "0" + date.getMinutes() : date
						.getMinutes())
				+ ":"
				+ (date.getSeconds() < 10 ? "0" + date.getSeconds() : date
						.getSeconds());
		return datetime;
	},

	// 刷新
	refresh : function() {
		$('#dg').datagrid('reload');
	},
	// 重置
	reset : function() {
		$("#searchFm").form("reset");
	}

	// 加载Ztree
	,zTree:{
		rootText:'YASHA',
		orgTreeObj:null,
		companyTreeObj:null,
		//初始化树形结构
		initDeptZtree:function(obj, treeNodeClick){
			var setting = {
		            view: {
		                showLine: false,
		                showIcon: false,
		                showTitle: true,
		                expandSpeed : '',
		                selectedMulti: false
		            },
		            data: {
						simpleData: {
							enable: true,
							idKey: "id",
							pIdKey: "pId",
							rootPId: 0
						},
						key: {
							name:'text',
							title:'text'
						}
					},
		            callback: {
		                beforeClick: function(treeId,treeNode){
		                    var nodes = common.zTree.orgTreeObj.getSelectedNodes();
		                    if(!nodes || nodes[0] == treeNode){
		                        return false;
		                    }
		                },
		                onClick: function (event, treeId, treeNode) {
		                	if (typeof treeNodeClick === "function"){
		                		treeNodeClick(event, treeId, treeNode); 
		                    }
		                }
		            }
		        };
			
			var url = basePath+'/managment/privilege/dept/getOrgTree.do?sessionId='+jsSessionId;
			$.ajax({url:url, data:{}, async:true, type:'POST', dataType:'JSON', success:function(dt){
				var datas = dt.datas;
				common.zTree.orgTreeObj = $.fn.zTree.init(obj, setting, datas);
				var node = common.zTree.orgTreeObj.getNodeByParam("text", common.zTree.rootText, null);
				common.zTree.orgTreeObj.expandNode(node, true);
				common.zTree.orgTreeObj.selectNode(node);
			}});
		}, 
		initCompanyTree: function(obj, treeNodeClick){
			var setting = {
	            view: {
	                showLine: false,
	                showIcon: false,
	                showTitle: true,
	                expandSpeed : '',
	                selectedMulti: false
	            },
	            data: {
					simpleData: {
						enable: true,
						idKey: "id",
						pIdKey: "pId",
						code:'code',
						rootPId: 0
					},
					key: {
						name:'text',
						title:'text'
					}
				},
	            callback: {
	                beforeClick: function(treeId,treeNode){
//	                    var nodes = common.zTree.companyTreeObj.getSelectedNodes();
//	                    if(!nodes || nodes[0] == treeNode){
//	                        return false;
//	                    }
	                },
	                onClick: function (event, treeId, treeNode) {
	                	if (typeof treeNodeClick === "function"){
	                		treeNodeClick(event, treeId, treeNode); 
	                    }
	                }
	            }
	        };
			var url = basePath+'/managment/privilege/company/ajaxlista.do?sessionId='+jsSessionId;
			$.ajax({url:url, data:{}, async:true, type:'POST', dataType:'JSON', success:function(dt){
				var datas = [];
				if(dt && dt.length >0){
					for(var i = 0; i< dt.length; i++){
						var o = {id: dt[i].id, pId:dt[i].pid, text:dt[i].cname, code:dt[i].code};
						datas.push(o);
					}
				}
				common.zTree.companyTreeObj = $.fn.zTree.init(obj, setting, datas);
				var node = common.zTree.companyTreeObj.getNodeByParam("pId", "", null);
				if(node){
					common.zTree.companyTreeObj.expandNode(node, true);
					common.zTree.companyTreeObj.selectNode(node);
					if(node.id){
						var scdNode = common.zTree.companyTreeObj.getNodeByParam("pId", node.id, null);
						if(scdNode){
							common.zTree.companyTreeObj.expandNode(scdNode, true);
						}
					}
				}
			}});
		}
	}
};