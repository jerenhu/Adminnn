<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
request.setAttribute("basePath",basePath);
%>

<!DOCTYPE>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
<meta name="renderer" content="webkit">
<meta charset="UTF-8" />
<title>${companyName}${plainName}</title>
<script src="${basePath}/assets/js/check_browser.js"></script>
<link rel="shortcut icon" href="${basePath}/images/favicon.ico" type="image/x-icon" />
<link href="${basePath}/assets/css/base.css" rel="stylesheet" />
<link href="${basePath}/assets/css/login.css" rel="stylesheet" />
</head>
<body class="login-body">
	<div class="login-div">
		<div class="hd">
			<span class="hd-tt"><i></i>欢迎使用${companyName}${plainName}</span>
		</div>
		<div class="bd">
			<div class="login-box">
				<form id="myForm" method="post" action="${basePath}/login.do">
					<dl class="dl-lgn-form">
						<dd class="dd-user">
							<i></i> <input id="username" name="username" type="text"
								class="lgn-ipt" placeholder="请输入用户名" datatype="*4-16"
								value="${username }" nullmsg="用户名不能为空" errormsg="用户名必须为四位以上" />
						</dd>
						<dd class="mt20">
							<i></i> <input id="password" name="password" type="password"
								class="lgn-ipt" placeholder="请输入密码" datatype="*4-16"
								value="${password }" nullmsg="密码不能为空" errormsg="密码必须大于四位以上" />
						</dd>
						<dd class="mt20">
							<i></i><input id="verifyCode" name="verifyCode" placeholder="验证码" type="text" class="lgn-ipt-vc" placeholder="请输入验证码" datatype="*"
									value="" nullmsg="验证码不能为空" errormsg="验证码不能为空"/>
							<img id="verifyCodeImg" class="ckcode-img" src=""/>
						</dd>
						<dd class="mt20">
							<a href="javascript:;" class="btn-nrml btn-lgn fl"
								id="loginSubmit"><b>登录</b></a> <span class="fl ml10"
								style="margin-top: 8px;"> <input type="checkbox"
								id="reminedPwd" name="selectFlag" value="1"
								<c:if test="${selectFlag eq 1 }">checked="checked"</c:if> /> <label
								for="reminedPwd">记住密码</label> <span id="loginMsg" class="ml10">${message}</span>
							</span>
						</dd>
					</dl>
				</form>
			</div>
		</div>
		<div class="ft">
			${companyName} <span class="arial">&copy; ${copy}</span>
		</div>
	</div>
	<!--[if lt IE 7]>
<script src="assets/js/libs/pngfixed.js"></script>
<script type="text/javascript">
    DD_belatedPNG.fix('.login-box,.login-div .bd');
</script>
<![endif]-->
	<script src="${basePath}/assets/js/libs/jquery-1.6.2.min.js"></script>
	<script src="${basePath}/assets/js/libs/validform5.3.2.js"></script>
	<!--[if lt IE 9]>
<script src="assets/js/libs/placeholder.js"></script>
<![endif]-->
	<script>
		if (document.addEventListener) {
			//如果是Firefox  
			document.addEventListener("keypress", enterEvent, true);
		} else {
			//如果是IE
			document.attachEvent("onkeypress", enterEvent);
		}
		function enterEvent(evt) {
			if (evt.keyCode == 13) {
				if(checkcompany()){
					$('#loginSubmit').click();
				}
			}
		}
		$("#myForm").Validform(
			{
				ajaxPost : true,
				callback : function(data) {
					var sid = data.responseText;
					//请返回登录状态信息
					if (sid != null && sid != '0' && sid!='vc') {
						location.href = '${basePath}/managment/frame/index.do?sessionId='+sid;
					} else if(sid=='vc') {
						$("#loginMsg").removeClass('loading16')
						.addClass("Validform_wrong").text(
								"验证码错误！");
						return ;
					}else {
						$("#loginMsg").removeClass('loading16')
								.addClass("Validform_wrong").text(
										"用户名密码错误！");
						return;
					}
				}
			});
		$('#loginSubmit').click(function() {
			var verifyCode = $("#verifyCode").val();
			verifyCode = verifyCode.toLowerCase();
			var cokieVc = cookie("vc");
			cokieVc = cokieVc.toLowerCase();
			if(verifyCode!=cokieVc) {
				$("#loginMsg").removeClass('loading16')
				.addClass("Validform_wrong").text(
						"验证码错误！");
				return ;
			}
			$("#myForm").submit();
		});
		
		$(function() { 
			changeVerifyCode();
			$('#verifyCodeImg').click(function(){changeVerifyCode()});
		}); 
		
		function changeVerifyCode() {
			$('#verifyCodeImg').attr("src",'${basePath}/verifyCode.do?t='+Math.random());
		}
		
		function cookie(name, value, options) {
            if (typeof value != 'undefined') {
                options = options || {};
                if (value === null) {
                    value = '';
                    options.expires = -1;
                }
                var expires = '';
                if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
                    var date;
                    if (typeof options.expires == 'number') {
                        date = new Date();
                        date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
                    } else {
                        date = options.expires;
                    }
                    expires = '; expires=' + date.toUTCString();
                }
                var path = options.path ? '; path=' + (options.path) : '/';
                var domain = options.domain ? '; domain=' + (options.domain) : '';
                var secure = options.secure ? '; secure' : '';
                document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
            } else {
                var cookieValue = null;
                if (document.cookie && document.cookie != '') {
                    var cookies = document.cookie.split(';');
                    for (var i = 0; i < cookies.length; i++) {
                        var cookie = jQuery.trim(cookies[i]);
                        if (cookie.substring(0, name.length + 1) == (name + '=')) {
                            cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                            break;
                        }
                    }
                }
                return cookieValue;
            }
        }
		
	</script>
</body>
</html>