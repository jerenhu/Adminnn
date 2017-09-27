<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/page/share/taglib.jsp"%>
<title>权限值</title>
</head>
<body class="easyui-layout">   
    <div data-options="region:'west',title:'系统列表',split:true" style="width:200px;">
    	<ul id="systemMenu"></ul>
    </div>   
    <div data-options="region:'center'">
    	<div class="easyui-layout" data-options="fit:true">   
            <div data-options="region:'north',border:false" >
            	<div id="toolbar" class="easyui-toolbar">
					<c:if test="${my:hasPermission(sessionId,'privilege','pval', 0)}">
						<a href="javascript:;" iconCls="icon-add" plain="true" onclick="add()">添加</a>
						<a>-</a>
					</c:if>
					<c:if test="${my:hasPermission(sessionId,'privilege','pval', 2)}">
						<a href="javascript:;" iconCls="icon-edit" plain="true" onclick="edit()">修改</a>
						<a>-</a>
					</c:if>
					<c:if test="${my:hasPermission(sessionId,'privilege','pval', 3)}">
						<a href="javascript:;" iconCls="icon-del" plain="true" onclick="del()">删除</a>
						<a>-</a>
					</c:if>
				</div>
            </div>   
            <div data-options="region:'center',border:false">
    			<table id="dg"></table>
            </div>   
        </div>
    </div>   
</body> 
<script type="text/javascript">
	var sizeObject={width:500,height:320};
	$.parser.parse();
	$('#systemMenu').tree({
		url:'${basePath}/managment/privilege/module/getsystems.do?sessionId=${sessionId}',
		onSelect: function(node){
			$.post('${basePath}/managment/privilege/pval/ajaxlist.do?sessionId=${sessionId}',{systemId : node.id},function(data){
				$('#dg').datagrid("loadData",data);
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
	
	$('#dg').datagrid({
		singleSelect : false,
		fitColumns : true,
		checkOnSelect : true,
		selectOnCheck : true,
		pagination: false,
		columns : [ [ {
			checkbox : true
		}, {
			title : '名称',
			field : 'name',
			width : 80,
			align : 'left'
		}, {
			title : '整型位',
			field : 'position',
			width : 50,
			align : 'center',
			formatter: function(value,row,index){
				if(!value){return "0";}return value;
			}
		}, {
			title : '排序号',
			field : 'orderNo',
			width : 30,
			align : 'center'
		}, {
			title : '备注',
			field : 'remark',
			width : 200,
			align : 'left'
		} ] ],
		onLoadSuccess : function() {
		}
	});
	function refreshSelectedNode(){
		var n = $("#systemMenu").tree("getSelected");  
        if(n!=null){  
             $("#systemMenu").tree("select",n.target);
        }
	}
	function add() {
		var node = $("#systemMenu").tree("getSelected");
		ygDialog({    
		    title: '新增权限值',    
		    width: sizeObject.width,    
		    height: sizeObject.height,    
		    closed: false,    
		    cache: false,    
		    href: "${basePath}/managment/privilege/pval/insertUI.do?sessionId=${sessionId}&systemId="+node.id,    
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
							'${basePath}/managment/privilege/pval/insert.do?sessionId=${sessionId}',
							data,
							function(result){
								$.messager.progress('close');
								if(result.status==100){
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
	    		url:'${basePath}/managment/privilege/pval/checkExsits.do?sessionId=${sessionId}',
	    		data:{position : value , id : formData.id, systemId : node.id},
	    		success:function(json){
	    			if(json==1){
	    				valid=false;
	    			}
	    		}
	    	});
	    	return valid;
	    },
	    message: '整形位值不能重复!'
	    }
	});
	
	function edit() {
		var data = $('#dg').datagrid("getSelected");
		if(!data){
			$.messager.alert('信息','请选择一条记录！','info');
			return;
		}
		ygDialog({    
		    title: '编辑权限值',    
		    width: sizeObject.width,    
		    height: sizeObject.height,
		    closed: false,    
		    cache: false,    
		    href: "${basePath}/managment/privilege/pval/updateUI.do?sessionId=${sessionId}",
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
							'${basePath}/managment/privilege/pval/update.do?sessionId=${sessionId}',
							fdata,
							function(result){
								$.messager.progress('close');
								if(result.status==100){
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
		var rows=$('#dg').datagrid("getSelections");
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
						url : "${basePath}/managment/privilege/pval/delete.do?sessionId=${sessionId}&ids=" + id,
						dataType : 'json',
						success : function(text) {
							$.messager.progress('close');
							//使用'clearSelections':防止读取到了删除的数据
							$('#dg').datagrid("clearSelections");
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