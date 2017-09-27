<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/page/share/taglib.jsp"%>
<%-- 查询js --%>
<%@ include file="/WEB-INF/page/share/dosearch.jspf"%>
<script type="text/javascript" language="javascript" src="${basePath}/resources/assets/js/libs/plupload/plupload.js" ></script>
<c:set var="systemSn" value="privilege" scope="page"/>
<c:set var="nameSpace" value="config" scope="page"/>
<title>系统配置</title>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" >
		<div id="toolbar" class="easyui-toolbar">
			<%-- 重置，刷新 --%>
			<%@ include file="/WEB-INF/page/share/toolbar_common.jspf"%>
			<c:if test="${my:hasPermission(sessionId,systemSn,nameSpace, 0)}">
				<a href="javascript:;" iconCls="icon-reload" plain="true" onclick="init()">同步</a>
				<a>-</a>
			</c:if>
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
		<div style="padding:10px;">
		<form name="searchFm" id="searchFm" method="post">
                  <table class="search-tb">
                     <tbody>
                        <tr>
                        	<td>配置名称：
                        		<input class="ipt" name="configName"/>
                        	</td>
                            <td>配置标识：
                            	<input class="ipt" name="configSn"/>
                            </td>
                            <td>
								<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" id="searchBtn">查询</a>
                            </td>
						</tr>
					</tbody>
				</table>
		</form>
		</div>
	</div>
	<div data-options="region:'center',border:true">
		<table id="dg">
		</table>
	</div>
</body>
<script type="text/javascript">
	var sizeObject={width:600,height:400};
	var dataList=null;
	function parsePage() {
			dataList=$('#dg').datagrid({
			url: '${basePath}/managment/system/systemConfig/ajaxList.do?sessionId=${sessionId}',
			singleSelect : false,
			fitColumns : true,
			checkOnSelect : true,
			selectOnCheck : true,
			pageSize: 20,
			pageList: [20,50,100],
			columns : [ [ {
				checkbox : true
			}, {
				title : '配置名称',
				field : 'configName',
				width : 100,
				align : 'left'
			}, {
				title : '配置标识',
				field : 'configSn',
				width : 70,
				align : 'left'
			}, {
				title : '配置key',
				field : 'configKey',
				width : 80,
				align : 'left'
			}, {
				title : '配置key的value值',
				field : 'configValue',
				width : 80,
				align : 'left'
			},{
				title : '排序',
				field : 'configOrder',
				width : 80,
				align : 'center'
			}, {
				title : '备注',
				field : 'remark',
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
		    		url:'${basePath}/managment/system/systemConfig/checkSnExsits.do?sessionId=${sessionId}',
		    		data:{configSn : value,id : formData.id},
		    		success:function(json){
		    			if(json==1){
		    				valid=false;
		    			}
		    		}
		    	});
		    	return valid;
		    },
	    	message: '系统配置标识不能重复!'
	    },
	    uniquekey: {
		    validator: function(value, param){
		    	var valid=true;
		    	var formData=$("#formU").form("getData");
		    	$.ajax({
		    		async:false,
		    		url:'${basePath}/managment/system/systemConfig/checkKeyExsits.do?sessionId=${sessionId}',
		    		data:{configKey : value,id : formData.id},
		    		success:function(json){
		    			if(json==1){
		    				valid=false;
		    			}
		    		}
		    	});
		    	return valid;
		    },
		    message: '系统配置key不能重复!'
		}
	});
	
	function add() {
		ygDialog({    
		    title: '新增系统配置',    
		    width: sizeObject.width,    
		    height: sizeObject.height, 
		    closed: false,    
		    cache: false,    
		    href: '${basePath}/managment/system/systemConfig/addUI.do?sessionId=${sessionId}',    
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
							'${basePath}/managment/system/systemConfig/add.do?sessionId=${sessionId}',
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
		    title: '编辑系统配置',    
		    width: sizeObject.width,    
		    height: sizeObject.height, 
		    closed: false,    
		    cache: false,    
		    href: '${basePath}/managment/system/systemConfig/updateUI.do?sessionId=${sessionId}',
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
							'${basePath}/managment/system/systemConfig/update.do?sessionId=${sessionId}',
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
				
				//上传图片
				setTimeout(function(){
					var configKey=$.trim(data.configKey);
					if("plain_logo"==configKey || "plan_icon"==configKey){
						$("#uploadFile-1").show();
						//$("#jsconfigvalue").di
						
						//图片上传
						var uploader=null;
						//上传控件初始化
				      	uploader = new plupload.Uploader({
				            runtimes: "flash,html5,silverlight,html4",
				            browse_button:  "uploadFile-1",
				            url: "${basePath}/managment/system/systemConfig/uploadImage.do?sessionId=${sessionId}&configKey="+configKey+"&id="+data.id,
				            //multipart_params: {"":_poptyp,"brandId":}, //设置你的参数
				            multipart: true,
				            unique_names: true,
				            chunk_size: "4MB",
				            urlstream_upload: true,
				            multiple_queues: false,
				            multipart_params: {JSESSID: $("#uploadFile-1")},
				            flash_swf_url: "${basePath}/resources/assets/js/libs/plupload/Moxie.swf",
				            silverlight_xap_url: "${basePath}/resources/assets/js/libs/plupload/Moxie.xap",
				            max_file_size: "4MB",
				            filters: [
				                {title: "Image files", extensions: "jpg,jpeg,bmp,png"}
				            ],
				            init: {
				                FilesAdded: function (up, files) {
				                	console.log(1);
									uploader.start();
				                },
				                FileUploaded: function (up, file, info) {
				                	var res = $.parseJSON(info.response);
				                }, Error: function (up, err) {
				                    if (err.code == "-600") {
				                        alert("文件大小为" + parseInt(err.file.size / (1024 * 1024)) + "MB，文件太大！");
				                    }
				                    if (err.code == "-601") {
				                        alert('"' + err.file.name + '" 的' + err.message);
				                    }
				                }
				            }
				        });
				        uploader.init();
					}
				},1000);
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
						url : "${basePath}/managment/system/systemConfig/delete.do?sessionId=${sessionId}&idStrs="
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
	
	//同步
	function init() {
		$.ajax({
			url : "${basePath}/managment/system/systemConfig/init.do?sessionId=${sessionId}",
			dataType : 'json',
			success : function(text) {
				if (text.responseCode == 101) {
					mini.alert(text.responseMsg);
				}
				dataList.datagrid("reload");
			}
		});
	}
</script>
</html>