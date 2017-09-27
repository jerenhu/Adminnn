<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/page/share/taglib.jsp"%>
<title>模块管理</title>
</head>
<body class="easyui-layout">   
    <div data-options="region:'west',title:'系统列表',split:true" style="width:200px;">
    	<ul id="systemMenu"></ul>
    </div>   
    <div data-options="region:'center',title:'模块列表'">
    	<div class="easyui-layout" data-options="fit:true">   
            <div data-options="region:'north',border:false">
            	<div id="toolbar" class="easyui-toolbar">
					<c:if test="${my:hasPermission(sessionId,'privilege','module', 0)}">
						<a href="javascript:;" iconCls="icon-add" plain="true" onclick="add()">添加</a>
						<a>-</a>
					</c:if>
					<c:if test="${my:hasPermission(sessionId,'privilege','module', 2)}">
						<a href="javascript:;" iconCls="icon-edit" plain="true" onclick="edit()">修改</a>
						<a>-</a>
					</c:if>
					<c:if test="${my:hasPermission(sessionId,'privilege','module', 3)}">
						<a href="javascript:;" iconCls="icon-del" plain="true" onclick="del()">删除</a>
						<a>-</a>
					</c:if>
					<c:if test="${my:hasPermission(sessionId,'privilege','module', 0)}">
						<a href="javascript:;" iconCls="icon-add" plain="true" onclick="addfunction()">添加操作权限</a>
						<a>-</a>
					</c:if>
				</div>
            </div>   
            <div data-options="region:'center',border:false">
    			<table id="tg"></table>
            </div>   
        </div>
    </div>
<style>
	.act-box{
		margin-bottom:4px;
	}
	.act-item {
		margin:4px 4px 0;
		border:1px solid #ccc;
		float:left;
		height:20px;overflow:hidden;
	}
	.act-item span{
		padding:2px 0px 2px 6px;
		height:16px;line-height:16px;
	}
	.act-item img{
		margin-left:2px;
		float:left;vertical-align:middle;cursor:pointer;
	}
	.act-item i{
		font-size:24px;
		cursor:pointer;
		color:gray;
		line-height:16px;
		display:inline-block;
		width:20px;
		height:20px;
		padding-top:1px;
		text-align:center;
		margin-left:2px;
	}
	.act-item i:hover{
		color:#ff3300;
		background:#ccc;
	}
</style>
</body> 
<script type="text/javascript">	
	var sizeObject={width:580,height:300};
	$.parser.parse();
	$('#systemMenu').tree({
		url:'${basePath}/managment/privilege/module/getsystems.do?sessionId=${sessionId}',
		onSelect: function(node){
			//使用'clearSelections':防止读取到了删除的数据
			$('#tg').treegrid("clearSelections");
		
			$.post('${basePath}/managment/privilege/module/getAll.do?sessionId=${sessionId}',{systemId : node.id},function(data){
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
		singleSelect : true,
		checkOnSelect : true,
		selectOnCheck : true,
		method: 'post',
		idField: 'id',
		treeField: 'name',
		loadFilter: pagerFilter,
		pagination: false,
		columns:[[
			{checkbox : true},
			{field:'name',title:'名称',align:'left',width:80},
			{field:'url',title:'url',width:120,align:'left'},
			{field:'pvs',title:'操作权限',width:200,align:'left',
				formatter: function(value,row,index){
					var htmltext='<div class="act-box">';
					for(var i=0,len=value.length;i<len;i++){
						htmltext+='<div class="act-item">'+ '<span style="float:left;">';
						htmltext+=value[i].name+'</span>';
						if(value[i].position!=1){
							/*	htmltext+='<img id="'+value[i].id+'" name="'+value[i].moduleId+'" title="删除" '+
										  ' onclick="deletePriVal(this)" '+
										  'src="${basePath}/images/delete.gif" />';
							*/
							htmltext+='<i id="'+value[i].id+'" name="'+value[i].moduleId+'"  title="删除" onclick="deletePriVal(this)" >×</i>';
						}else{
							htmltext+='<span class="no-del"></span>';
						}
						if(i!=len-1){
							//htmltext+='&nbsp;|&nbsp;';
						}
						htmltext+='</div>';
					}
					htmltext+='<div style="clear:both;"></div></div>';
					return htmltext;
				}
			},
			{field:'sn',title:'标识',width:40,align:'left'},
			{field:'orderNo',title:'排序号',width:25,align:'center'},
			{field:'childNode',title:'操作',width:25,align:'center',
				formatter: function(value,row,index){
					return '<img title="添加子类" style="cursor:pointer;" onclick="add(\''+row.id+'\')" src="${basePath}/resources/assets/images/icons/add.gif" />';
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
	
	function add(pid) {
		var node = $("#systemMenu").tree("getSelected");
		if(!pid){
			pid="";
		}
		ygDialog({    
		    title: '新增模块',    
		    width: sizeObject.width,    
		    height: sizeObject.height,    
		    closed: false,    
		    cache: false,    
		    href: "${basePath}/managment/privilege/module/insertUI.do?sessionId=${sessionId}&pid="+pid+"&systemId="+node.id,    
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
							'${basePath}/managment/privilege/module/insert.do?sessionId=${sessionId}',
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
	    		url:'${basePath}/managment/privilege/module/checkSnExsits.do?sessionId=${sessionId}',
	    		data:{sn : value , id : formData.id, systemId : node.id},
	    		success:function(json){
	    			if(json==1){
	    				valid=false;
	    			}
	    		}
	    	});
	    	return valid;
	    },
	    message: '标识不能重复!'
	    }
	});
	
	function edit() {
		var data = $('#tg').treegrid("getSelected");
		if(!data){
			$.messager.alert('信息','请选择一条记录！','info');
			return;
		}
		ygDialog({    
		    title: '编辑模块',    
		    width: sizeObject.width,    
		    height: sizeObject.height,    
		    closed: false,    
		    cache: false,    
		    href: "${basePath}/managment/privilege/module/updateUI.do?sessionId=${sessionId}",
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
							'${basePath}/managment/privilege/module/update.do?sessionId=${sessionId}',
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
					$.messager.alert('信息','请先删除子模块！','info');
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
						url : "${basePath}/managment/privilege/module/delete.do?sessionId=${sessionId}&ids=" + id,
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
	
	//删除权限值
	function deletePriVal(imgobj){
		$.ajax({
			type:'post',
			url : "${basePath}/managment/privilege/module/deletePriVal.do",
			data: {sessionId:'${sessionId}',systemPrivilegeValueId:$(imgobj).attr('id'),moduleId: $(imgobj).attr('name')},
			dataType:'json',
			success : function(text) {
				if(text.responseCode==100){
					$(imgobj).parent().remove();
				}
			}
		});
	}
	
	//添加操作权限
	function addfunction() {
		var node = $("#systemMenu").tree("getSelected"),
		rows = $('#tg').treegrid("getSelections");
		if(rows && rows.length==1){
			ygDialog({    
			    title: '给模块 <font color="red">'+rows[0].name+'</font> 分配权限值',    
			    width: sizeObject.width,    
			    height: 300,    
			    closed: false,    
			    cache: false,    
			    href: "${basePath}/managment/privilege/module/insertPriValUI.do?sessionId=${sessionId}&systemId="+node.id+"&moduleId="+rows[0].id+"&moduleSn="+rows[0].sn,  
			    modal: true,
			    buttons : [{
					text:'保存',
					iconCls : 'icon-save',
					handler:function(dialog){
						$.messager.progress(); 
						var data=$("#formA").serialize();
						if(!data){
							$.messager.progress('close');
							$.messager.alert('信息','请勾选权限值！','info');
							return false;
						}
						$.post(
							'${basePath}/managment/privilege/module/insertPriVal.do?sessionId=${sessionId}&moduleId='+rows[0].id,
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
				}],
				onOpen:function(){//页面打开后执行
					setTimeout(function(){
						$(".selectAllBtn").click(function(){
							//debugger;
							if(this.text == '全选'){
								//全选
								$("#formA input:checkbox").attr("checked","true");
							}else if(this.text == '取消'){
								//全不选
								$("#formA input:checkbox").removeAttr("checked");
							}
						});
					},500);
				}
			});    
		}else{
			$.messager.alert('信息','请选择一条记录！','info');
		}
	}
</script>
</html>