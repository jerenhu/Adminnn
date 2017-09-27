<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
request.setAttribute("basePath",basePath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>${companyName}${plainName}</title>
    <meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge" />
	<meta name="renderer" content="webkit" />
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <script src="${basePath}/assets/js/check_browser.js"></script>
    <link rel="shortcut icon" href="${basePath}/images/favicon.ico" type="image/x-icon" />
    <script src="${basePath}/resources/assets/boot.js?ver=${jscssvar}" type="text/javascript"></script>
    <link href="${basePath}/assets/css/base.css" rel="stylesheet" />
    <script src="${basePath}/assets/js/index.js"></script>
    <script src="${basePath}/assets/js/libs/messenger.js"></script>
    <script src="${basePath}/assets/js/libs/jquery.ygdialog.js"></script>
    <script src="${basePath}/resources/assets/js/common/common.js" type="text/javascript" ></script>
    <style>
    	
    </style>
    <script>
    	$(function(){
    		$('#leftMenuPanel').parent().addClass('pl-bar');
    		$('.layout-button-right').parents('.panel-header').children('.panel-title').css('height','28px');
    		/* $('.tabs li:not(".tabs-selected")').live('mouseenter',function(){
    			$(this).find('.tabs-close').show();
    		});
    		$('.tabs li:not(".tabs-selected")').live('mouseleave',function(){
    			$(this).find('.tabs-close').hide();
    		}); */
    		var toolTip = $('#jsUserName').tooltip({
    		    position: 'right',
    		    deltaY:-20,
    		    content: '<ul class="user-center">'+$('#jsUserCenterHtml').html()+'</ul>',
    		    position:'bottom',
    		    hideDelay:500,
    		    onShow: function(){
    		        $(this).tooltip('tip').css({
    		            backgroundColor: '#FFF',
    		            borderColor: '#1189DC'
    		        });
    		    }
    		});
    		$(".user-center").live('mouseenter',function(){
    			$('#jsUserName').tooltip("show");
    		});
    		$(".user-center").live('mouseleave',function(){
    			$('#jsUserName').tooltip("hide");
    		});
    		var toolTip = $('#jsMessageNotice').tooltip({
    		    position: 'right',
    		    deltaY:-20,
    		    content: '<ul class="message-notice">'+$('#jsChangePassword').html()+'</ul>',
    		    position:'bottom',
    		    hideDelay:5000000,
    		    onShow: function(){
    		        $(this).tooltip('tip').css({
    		            backgroundColor: '#FFF',
    		            borderColor: '#1189DC'
    		        });
    		    }
    		});
    		$('#jsMessageNotice').tooltip("show");
    	});
    </script>
</head>
<body class="easyui-layout main-frame">
	<div class="header" data-options="region:'north',split:true,border:true,disabled:true" style="height:60px;overflow:hidden;">
		<div class="easyui-layout " data-options="fit:true">
			<div data-options="region:'west',border:false" style="width:340px;overflow:hidden;">
				<div class="header_nav_index">
		            <a style="width:100%;height:100%;display:block" class="logo" href="#"></a>
	            </div>
	        </div>
	        <div data-options="region:'center',border:false" >
	            
	            <div id="systemNavMenu" class="easyui-tabs" data-options="tabWidth:82,fit:true,tabHeight:60,scrollIncrement:82,border:false," >
	            	<c:forEach items="${systems }" var="system" varStatus="status">
		            	<c:if test="${status.index eq 0 }">
		            		<c:set var="systemId" scope="request" value="${system.id}"/>
		            		<c:set var="systemName" scope="request" value="${system.name}"/>
		            		<c:set var="systemUrl" scope="request" value="${system.url}"/>
		            	</c:if>
						<div title='<i class="nav-icon-list ${system.sn}-icon "></i><span data-deskurl="${system.url}" data-sessionid="${sessionId}" data-url="${basePath}/managment/frame/systemTree.do?systemId=${system.id}&url=${system.url}&sessionId=${sessionId}" <c:if test="${status.index eq 0 }">class="curr" </c:if> >${system.name}</span>'>
							<%-- <a href="javascript:;"><i class="nav-icon-list ${system.sn}-icon "></i><span>${system.name}</span></a>--%>
						</div>
					</c:forEach>
	            </div>
	           
	            <!-- 
	            <div id="navMenu" class="nav-menu" style="position:relative;">
		            <div class="nav-munu-box">
			            <ul>
			            	<c:forEach items="${systems }" var="system" varStatus="status">
				            	<c:if test="${status.index eq 0 }">
				            		<c:set var="systemId" scope="request" value="${system.id}"/>
				            		<c:set var="systemName" scope="request" value="${system.name}"/>
				            		<c:set var="systemUrl" scope="request" value="${system.url}"/>
				            	</c:if>	
								<li data-deskurl="${system.url}" data-sessionid="${sessionId}" data-url="${basePath}/managment/frame/systemTree.do?systemId=${system.id}&url=${system.url}&sessionId=${sessionId}" <c:if test="${status.index eq 0 }">class="curr" </c:if>>
									<a href="javascript:;"><i class="nav-icon-list ${system.sn}-icon "></i><span>${system.name}</span></a>
								</li>
							</c:forEach>
			            </ul>
	            	</div>
	            	<div class="arrow-icon" style="float:left;background:#1B2E41">
			            <span id="moreMenu1"class="arrow gray-left-arrow" style="margin-top:20px;right:-36px"></span>
			            <span id="moreMenu" class="arrow white-right-arrow" style="margin-top:20px;right:-64px;;"></span>
		            </div>
		        </div>
		         -->
		    </div>
		    <div data-options="region:'east',border:false" style="width:250px;">
	            <div class="bd" style="text-align:right;margin-right:20px;z-index:10;height:100%;width:100%;line-height:65px;">
	                <ul class="top-toolbar">
	                	<li id="jsUserName">
	                		<i class="touxiang"></i>
	                		<font color="#65aef2">${login_user.realName }</font>
	                		<span class="triangle_border_down"></span>
	                		
	                	</li>
	                	<c:if test="${(not empty pwdMsg || not empty pwdWarnDay || not empty warnDay)}">
		                	<li id="jsMessageNotice" style="cursor:default;width:30px;"><i class="message"></i><span class="rounded-rectangle">1</span>	
		                	</li>
	                	</c:if>
	                	
	                	<li>
	                		<a id="lnkDesk" href="javascript:;" class="white"><i class="system-table"></i></a>
	                	</li>
	                </ul>
	                
	                
	                <ul id="jsUserCenterHtml">
	   					<li><a onclick="resetpassword()" href="javascript:void(0);"><i class="change-password"></i><span  style="display:inline-block;color:#65aef2;">重置密码</span></a></li>
	   					<li class="sperate-line"><i></i></li>
	   					<li><a href="${basePath}/logout.do"><i class="quit"></i><span style="display:inline-block;color:#65aef2;">退出</span></a></li>
	   				</ul>
	                <%-- 密码修改提示 --%>
	                	<c:if test="${not empty pwdMsg}">
	                		<script>
		               			$(function(){
				                	var msg = '${pwdMsg}';
				                    $.messager.alert('提示信息',msg,'warning',function(){
				                    	resetpassword(); //重置密码
				                    });
				                });
		           			</script>
	                	</c:if>
	               		<ul id="jsChangePassword" >
	               			<%-- 密码修改提示 --%>
	               			<c:if test="${not empty pwdMsg}">
		               			<li>
		                			<c:set var="newPwdMsg" value="${fn:replace(pwdMsg, '重置密码', '<a onclick=\"resetpassword();\" class=\"cblue\" href=\"javascript:void(0);\">重置密码</a>')}" />
		               				<span class="error-style"></span>${newPwdMsg}
		               			</li>
	               			</c:if>
	               			<%-- 密码过期提示 --%>
	          				<c:if test="${not empty pwdWarnDay}">
		               			<li>
				                	<span class="error-style"></span>距离密码过期还有<font style="display:inline-block;vertical-align: middle" color="red" size="4px;">&nbsp;${pwdWarnDay}&nbsp;</font>天，请及时<a style="color:#65aef2;cursor:pointer"onclick="resetpassword();" class=\"cblue\" >重置密码</a>。
		               			</li>
			                </c:if>
	               			<%-- 账户过期提示 --%>
	               			<c:if test="${not empty warnDay}">
		               			<li>
				                	<span class="error-style"></span>距离账号过期还有<font style="display:inline-block;vertical-align: middle" color="red" size="4px;">&nbsp;${warnDay}&nbsp;</font>天，请及时联系系统管理员处理。
		               			</li>
			                </c:if>
	               		</ul>
	            	</div>
	          	</div>
        	</div>
        </div>
	</div>   
    
    <div id="leftMenuPanel"  data-options="region:'west',title:'${systemName}菜单',split:true,border:true" style="width:150px;border-top:none;border-left:none;border-bottom:none;">
    	<%-- 密码没有过期 --%>
    	<c:if test="${empty pwdMsg}">
    	<ul id="leftMenu" url="${basePath}/managment/frame/systemTree.do?systemId=${systemId}&url=${systemUrl}&sessionId=${sessionId}" data-options="loadFilter : indexTreeLoadFilter" style="width:100%;height:100%;">
        </ul>
    	</c:if>
    </div>
    
    <div data-options="region:'center',border:false" style="border-top:none;">
    	<div class="easyui-layout" data-options="fit:true">   
            <div data-options="region:'center',border:false">
            	<div id="mainTabs" class="easyui-tabs" data-options="border:false,fit:true,tools:'#tabsButtons'" >
		            <div name="first" title="系统首页" fit="true">
		            	<%-- 密码没有过期 --%>
		            	<c:if test="${empty pwdMsg}">
		            	<iframe id="deskFrame" style="border:none;width:100%;height:100%;" src="${systemUrl}/index.do?sessionId=${sessionId}" frameborder="0" scrolling="none"></iframe>
		            	</c:if>
		            </div>
		        </div>
            </div>
           	<div data-options="region:'south',border:false" style="height:36px;text-align:center;line-height:31px;">
		    	${companyName}${plainName} <span class="arial">&copy; ${copy}</span>
		    </div>
        </div>   
    </div>
    <div id="tabsButtons" style="margin-top:1px;">
    	<a id="tabToolsFullScreen" class="easyui-linkbutton" iconCls="icon-window-max" plain="true" onclick="tabToolsFullScreenOnClick()">全屏</a>
	</div>
<script>
$.parser.parse();
function resetpassword(){
	ygDialog({    
	    title: '重置密码',    
	    width: 500,    
	    height: 250,    
	    closed: false,    
	    cache: false,    
	    href: '${basePath}/managment/privilege/user/rePasswordUI.do?sessionId=${sessionId}',    
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
						'${basePath}/managment/privilege/user/rePassword.do?sessionId=${sessionId}',
						data,
						function(result){
							$.messager.progress('close');
							if(result.responseCode==100){
								dialog.close();
								$.messager.alert('信息','密码重置成功，需重新登录，正在跳转...','info');
								setTimeout(function(){
									window.location.href="${basePath}/logout.do"; //密码重置成功后重新登录系统
								},500);
							}else{
								$.messager.alert('信息','保存失败:'+result.responseMsg,'info');
							}
						},'json'
					);
				}
			}
		}]
	});    
}

function indexTreeLoadFilter(data){
	return common.treeLoadFilter(data,{source:1});
}
</script>
</body>
</html>