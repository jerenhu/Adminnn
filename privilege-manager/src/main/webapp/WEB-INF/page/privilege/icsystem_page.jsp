<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/page/share/taglib.jsp"%>
<%-- 查询js --%>
<%@ include file="/WEB-INF/page/share/dosearch.jspf"%>
<c:set var="systemSn" value="privilege" scope="page"/>
<c:set var="nameSpace" value="icsystem" scope="page"/>
<title>系统管理</title>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false"  >
		<div id="toolbar" class="easyui-toolbar">
			<%-- 重置，刷新 --%>
			<%@ include file="/WEB-INF/page/share/toolbar_common.jspf"%>
			<%-- <jsp:include page="/WEB-INF/page/share/toolbar_common.jspf" flush="false"/> --%>
			<c:if test="${my:hasPermission(sessionId,systemSn,nameSpace, 0)}">
				<a href="javascript:;" iconCls="icon-add" plain="true" onclick="add()">添加</a>
				<a>-</a>
			</c:if>
			<c:if test="${my:hasPermission(sessionId,systemSn,nameSpace, 2)}">
				<a href="javascript:;" iconCls="icon-edit" plain="true" onclick="edit()">修改</a>
				<a>-</a>
			</c:if>
			<c:if test="${my:hasPermission(sessionId,systemSn,nameSpace, 3)}">
				<a href="javascript:;" iconCls="icon-del" plain="true" onclick="del()">删除</a>
				<a>-</a>
			</c:if>
		</div>
		<%--搜索start--%>
		<div class="search-div">
			<form name="searchFm" id="searchFm" method="post">
				<table class="search-tb" width="550">
					<col width="70" />
					<col />
					<col width="70" />
					<col />
					<tbody>
						<tr>
							<th>系统名称 ：</th>
							<td>
								<input class="ipt" name="name"/>
							</td>
							<th>系统标识：</th>
							<td>
								<input class="ipt" name="sn"/>
							</td>
							<th></th>
							<td>
								<a iconCls="icon-search" class="easyui-linkbutton" id="searchBtn" data-options="iconCls:'icon-search'">查询</a>
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<%--搜索end--%>
	</div>
	<div data-options="region:'center',border:false">
		<table id="dg">
		</table>
	</div>
</body>
<script type="text/javascript">
	var sizeObject={width:500,height:360};
	var dataList=null;

	function parsePage() {
			dataList=$('#dg').datagrid({
			url: '${basePath}/managment/privilege/icsystem/ajaxlist.do?sessionId=${sessionId}',
			singleSelect : false,
			fitColumns : true,
			checkOnSelect : true,
			selectOnCheck : true,
			striped:true,
			pageSize: 20,
			pageList: [20,50,100],
			columns : [ [ {
				checkbox : true
			}, {
				title : '名称',
				field : 'name',
				width : 100,
				align : 'left'
			}, {
				title : '标识',
				field : 'sn',
				width : 70,
				align : 'left'
			}, {
				title : 'url前缀',
				field : 'url',
				width : 200,
				align : 'left'
			}, {
				title : '排序号',
				field : 'orderNo',
				width : 30
			}, {
				title : '备注',
				field : 'note',
				width : 100,
				align : 'left'
			} ] ],
			onLoadSuccess : function() {
			}
		});
	}
	
	//验证标识的唯一性
	$.extend($.fn.validatebox.defaults.rules, {
	    unique: {
	    validator: function(value, param){
	    	var valid=true;
	    	var formData=$("#formU").form("getData");
	    	$.ajax({
	    		async:false,
	    		url:'${basePath}/managment/privilege/icsystem/checkSnExsits.do?sessionId=${sessionId}',
	    		data:{sn : value,id : formData.id},
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
	
	function add() {
		ygDialog({    
		    title: '新增系统',    
		    width: sizeObject.width,    
		    height: sizeObject.height,    
		    closed: false,    
		    cache: false,    
		    href: '${basePath}/managment/privilege/icsystem/insertUI.do?sessionId=${sessionId}',    
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
							'${basePath}/managment/privilege/icsystem/insert.do?sessionId=${sessionId}',
							data,
							function(result){
								$.messager.progress('close');
								if(result.responseCode==100){
									dialog.close();
									dataList.datagrid("reload");
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
	
	function edit() {
		var data=dataList.datagrid("getSelected");
		if(!data){
			$.messager.alert('信息','请选择一条记录！','info');
			return;
		}
		ygDialog({    
		    title: '编辑系统',    
		    width: sizeObject.width,    
		    height: sizeObject.height,    
		    closed: false,    
		    cache: false,    
		    href: '${basePath}/managment/privilege/icsystem/updateUI.do?sessionId=${sessionId}',    
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
							'${basePath}/managment/privilege/icsystem/update.do?sessionId=${sessionId}',
							fdata,
							function(result){
								$.messager.progress('close');
								if(result.responseCode==100){
									dialog.close();
									dataList.datagrid("reload");
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
			}
		});    
	}
	
	function del() {
		var rows=dataList.datagrid("getSelections");
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
						url : "${basePath}/managment/privilege/icsystem/delete.do?sessionId=${sessionId}&ids="
								+ id,
						dataType : 'json',
						success : function(text) {
							$.messager.progress('close');
							if (text.responseCode == 101) {
								$.messager.alert(text.responseMsg);
							}else{
								//使用'clearSelections':防止读取到了删除的数据
								dataList.datagrid("clearSelections");
								dataList.datagrid("reload");
							}
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