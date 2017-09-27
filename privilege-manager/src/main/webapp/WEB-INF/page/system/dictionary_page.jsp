<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/page/share/taglib.jsp"%>
<title>数据字典列表</title>
</head>
<body class="easyui-layout">   
    <div data-options="region:'west',title:'系统列表',split:true" style="width:200px;">
    	<ul id="systemMenu"></ul>
    </div>   
    <div data-options="region:'center',title:'模块列表'">
    	<div class="easyui-layout" data-options="fit:true">   
            <div data-options="region:'north'" >
            	<div id="toolbar" class="easyui-toolbar">
					<c:if test="${my:hasPermission(sessionId,'privilege','dictionary', 0)}">
						<a href="javascript:;" iconCls="icon-add" plain="true" onclick="add()">添加</a>
						<a>-</a>
					</c:if>
					<c:if test="${my:hasPermission(sessionId,'privilege','dictionary', 2)}">
						<a href="javascript:;" iconCls="icon-edit" plain="true" onclick="edit()">修改</a>
						<a>-</a>
					</c:if>
					<c:if test="${my:hasPermission(sessionId,'privilege','dictionary', 3)}">
						<a href="javascript:;" iconCls="icon-del" plain="true" onclick="del()">删除</a>
						<a>-</a>
					</c:if>
				</div>
            </div>   
            <div data-options="region:'center'">
    			<table id="tg"></table>
            </div>   
        </div>
    </div>   
</body>
<script type="text/javascript">
	var sizeObject={width:500,height:280};
	$.parser.parse();
	$('#systemMenu').tree({
		url:'${basePath}/managment/privilege/module/getsystems.do?sessionId=${sessionId}',
		onSelect: function(node){
			//使用'clearSelections':防止读取到了删除的数据
			$('#tg').treegrid("clearSelections");
		
			$.post("${basePath}/managment/system/dictionary/getAll.do?sessionId=${sessionId}",{systemSn : node.sn},function(data){
				$('#tg').treegrid("loadData",data);
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
	function refreshSelectedNode(){
		var n = $("#systemMenu").tree("getSelected");  
        if(n!=null){  
             $("#systemMenu").tree("select",n.target);
        }
	}
	$('#tg').treegrid({
		animate: true,
		rownumbers : true,
		collapsible: true,
		fitColumns:true,
		singleSelect : false,
		checkOnSelect : true,
		selectOnCheck : true,
		method: 'post',
		idField: 'code',
		treeField: 'name',
		loadFilter: pagerFilter,
		pagination: true,
		pageSize: 10,
		pageList: [10,20,50],
		columns:[[
			{checkbox : true},
			{field:'name',title:'名称',align:'left',width:100},
			{field:'code',title:'编码',width:100,align:'left'},
			{field:'orderNo',title:'排序号',width:30,align:'center'},
			{field:'childNode',title:'操作',width:30,align:'center',
				formatter: function(value,row,index){
					return '<img title="添加子类" style="cursor:pointer;" onclick="add(\''+row.code+'\')" src="${basePath}/resources/assets/images/icons/add.gif" />';
				}
			}
		]],
		onLoadSuccess:function(){
	
		}
	});
	function pagerFilter(data){
        if ($.isArray(data)){    // is array  
            data = {  
                total: data.length,  
                rows: data  
            }; 
        }
        var dg = $(this);  
        var opts = dg.treegrid('options');  
        var pager = dg.treegrid('getPager');  
        pager.pagination({  
            onSelectPage:function(pageNum, pageSize){  
                opts.pageNumber = pageNum;  
                opts.pageSize = pageSize;  
                pager.pagination('refresh',{  
                    pageNumber:pageNum,  
                    pageSize:pageSize  
                });  
                dg.treegrid('loadData',data);  
            }  
        });  
        if (!data.topRows){  
        	data.topRows = [];
        	data.childRows = [];
        	for(var i=0; i<data.rows.length; i++){
        		var row = data.rows[i];
        		row._parentId ? data.childRows.push(row) : data.topRows.push(row);
        	}
			data.total = (data.topRows.length);
        }  
        var start = (opts.pageNumber-1)*parseInt(opts.pageSize);  
        var end = start + parseInt(opts.pageSize);  
		data.rows = $.extend(true,[],data.topRows.slice(start, end).concat(data.childRows));
		return data;
	}
	
	function add(pcode) {
		var node = $("#systemMenu").tree("getSelected");
		if(!pcode){
			pcode="";
		}
		ygDialog({    
		    title: '新增数据字典',    
		    width: sizeObject.width,    
		    height: sizeObject.height, 
		    closed: false,    
		    cache: false,    
		    href: "${basePath}/managment/system/dictionary/insertUI.do?sessionId=${sessionId}&pcode="+pcode+"&systemSn="+node.sn,    
		    modal: true,
		    buttons : [{
				text:'保存',
				iconCls : 'icon-save',
				handler:function(dialog){
					var form=$("#formA");
					if(form.form('validate')){
						$.messager.progress(); 
						var data=form.form("getData");
						$.post(
							'${basePath}/managment/system/dictionary/insert.do?sessionId=${sessionId}',
							data,
							function(result){
								$.messager.progress('close');
								if(result.responseCode==100){
									dialog.close();
									$("#systemMenu").tree("select",node.target);
								}else{
									$.messager.alert('信息','保存失败！','info');
								}
							},'json'
						);
					}
				}
			}]
		});    
	}
	
	//验证标识的唯一性
	$.extend($.fn.validatebox.defaults.rules, {
	    unique: {
	    validator: function(value, param){
	    	var node = $("#systemMenu").tree("getSelected");
	    	var valid=true;
	    	var formData=$("#formU").form("getData");
	    	$.ajax({
	    		async:false,
	    		url:'${basePath}/managment/system/dictionary/checkDictionaryCodeExsits.do?sessionId=${sessionId}',
	    		data:{code : value , id : formData.id, systemSn : node.sn},
	    		success:function(json){
	    			if(json==1){
	    				valid=false;
	    			}
	    		}
	    	});
	    	return valid;
	    },
	    message: '编码不能重复!'
	    }
	});
	
	function edit() {
		var data = $('#tg').treegrid("getSelected");
		if(!data){
			$.messager.alert('信息','请选择一条记录！','info');
			return;
		}
		ygDialog({    
		    title: '编辑数据字典',    
		    width: sizeObject.width,    
		    height: sizeObject.height, 
		    closed: false,    
		    cache: false,    
		    href: "${basePath}/managment/system/dictionary/updateUI.do?sessionId=${sessionId}",
		    modal: true,
		    buttons : [{
				text:'保存',
				iconCls : 'icon-save',
				handler:function(dialog){
					var form=$("#formU");
					if(form.form('validate')){
						$.messager.progress(); 
						var fdata=form.form("getData");
						$.post(
							'${basePath}/managment/system/dictionary/update.do?sessionId=${sessionId}',
							fdata,
							function(result){
								$.messager.progress('close');
								if(result.responseCode==100){
									dialog.close();
									refreshSelectedNode();
								}else{
									$.messager.alert('信息','保存失败！','info');
								}
							},'json'
						);
					}
				}
			}],
			onLoad : function (){
				var form = $("#formU");
				form.form("load",data);
			}
		});    
	}
	
	function del() {
		var rows=$('#tg').treegrid("getSelections");
		if (rows && rows.length > 0) {
			for(var i=0,len=rows.length;i<len;i++){
				if(rows[i].children){
					$.messager.alert('信息','请先删除子节点！','info');
					return;
				}
			}
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
						url : "${basePath}/managment/system/dictionary/delete.do?sessionId=${sessionId}&ids=" + id,
						dataType : 'json',
						success : function(text) {
							$.messager.progress('close');
							//使用'clearSelections':防止读取到了删除的数据
							$('#tg').treegrid("clearSelections");
							refreshSelectedNode();
						}
					});
				}
			});
		} else {
			$.messager.alert('信息','请选择要删除的记录！','info');
		}
	}
</script>
</html>