<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
request.setAttribute("basePath",basePath);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
		<meta name="renderer" content="webkit">
		<meta charset="UTF-8" />
		<title>${companyName}${plainName}</title>
		<script src="${basePath}/assets/js/check_browser.js"></script>
		<link rel="shortcut icon" href="${basePath}/images/favicon.ico" type="image/x-icon" />
		<link href="${basePath}/assets/css/base.css" rel="stylesheet" />
		<style type="text/css">
			*{margin: 0;padding: 0;}
			body{font-family: "微软雅黑";background: url("${basePath}/images/login-bg/bg-1.png") no-repeat center top;}
			img{box-sizing: border-box;vertical-align: middle;}
			.contain{background: url("${basePath}/images/login-bg/bg-1.png") no-repeat center bottom;height: 100%;overflow: hidden;position:relative;z-index:9999;}
			.logo{width: 425px;height: 46px;margin: 0 auto;position:absolute;top:-64px;}
			.login-box{width: 437px;height: 345px;position: fixed;left: 50%;top: 50%;margin-left: -218.5px;margin-top: -180px;background: white;
				border-radius: 8px;padding-top: 16px;}
			.login-box div{width: 351px;height: 48px;border: 1px solid #cfcfcf;border-radius: 26px;margin: 20px auto;line-height: 48px;}
			.login-box label{display: inline-block;margin-left: 18px;}	
			.login-box input{height: 30px;width: 285px;position: relative;top:-2px;border: none;outline: none;margin-left: 12px;font-size: 16px;font-family: "微软雅黑"!important;}
			#login-enimg{width: 208px;margin-left: 42px;overflow: hidden;position: relative;}
			#login-enimg label{width: 114px;height: 40px;background: white;position: absolute;right: 3px;top: 4px;overflow:hidden;border-radius: 26px;}
			#login-enimg label img{
				width: 100%;
				height: 100%;
				display: block;
			}
			.getpassword{position: absolute;right: 56px;top: 210px;}
			.getpassword input{width: 14px;height: 14px;text-indent: 10px;}
			#loginSubmit{cursor:pointer;width: 351px;height: 50px;background: #48566B;color: white;display: block;margin: 0 auto;margin-top: 34px;border-radius: 26px;}
			#loginSubmit:hover{background:#567e9a;}
			.logo{text-indent:0;}
			.Validform_error, .lgn-ipt.Validform_error {background: white;}
			input:-webkit-autofill {-webkit-box-shadow : 0 0 0px 1000px white inset ;}
		</style>
		<script>
		onload = function () {
            if (top.location != self.location) {
                top.location = window.location;
            } else {
                //window.location.href = "/login.jsp";
            }
        };
		</script>
	</head>
	<body>
		<div class="contain">
			
			<div class="login-box">
			<form id="myForm" method="post" action="${basePath}/login.do" autocomplete="off">
				<a class="logo">
					<img src="${basePath}/images/denglu_logo.png"/>
				</a>
				<span style="margin-left:44px;padding-left:20px;"id="loginMsg" class="ml10">${message}</span>
				</br>
				<%-- 大小写提示--%>
                <span style="color: rgb(255, 153, 0); margin-left:44px;padding-left:20px;display:none" id="capital">大写锁定已开启</span>
				
				<div id="login-user">
					<label for="jsUser" class="icon-user"><img src="${basePath}/images/user.png"></label>
					<input id="username" name="username" type="text"
								class="lgn-ipt" placeholder="请输入用户名" datatype="*4-16"
								value="${username }" nullmsg="用户名不能为空" errormsg="用户名必须为四位以上" autocomplete="off"/>
				</div>
				<div id="login-user">
					<label for="password" class="icon-key"><img src="${basePath}/images/password.png"></label>
					<input id="password" name="password" type="password"
							class="lgn-ipt" placeholder="请输入密码" datatype="*4-16"
							value="${password }" nullmsg="密码不能为空" errormsg="密码必须大于四位以上" />
				</div>
				<div id="login-enimg">
					<input maxlength="4" id="verifyCode" name="verifyCode" style="text-indent: 10px;" placeholder="验证码" type="text" class="ckcode-img" placeholder="请输入验证码" datatype="*"
						value="" nullmsg="验证码不能为空" errormsg="验证码不能为空"/>
					<label for="jsEnimg" class="icon-key">
						<img id="verifyCodeImg" class="ckcode-img" src="" alt="图片验证码"/>
					</label>
				</div>
				<span class="getpassword">
					<input type="checkbox" id="reminedPwd" name="selectFlag" value="1" <c:if test="${selectFlag eq 1 }">checked="checked"</c:if> />
					<label for="reminedPwd" class="reminedText" style="position: relative;top: -4px;right: 10px;">记住密码</label>
					
				</span>
				<input type="submit" id="loginSubmit" name="" value="登录"/>
			</form>
			</div>
		</div>
		<footer style="position: absolute;clear: both;text-align:center;bottom:0;width:100%;font-size:16px;z-index:10000;">
		  <p style="margin:20px auto;color:#fff;font-size:12px;"><br />Copyright © ${copy} ${companyName} 保留所有权利。 浙ICP备16009894号</p>
		</footer>

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
				$('#loginSubmit').click();
			}
		}
		$("#myForm").Validform(
			{
				ajaxPost : true,
				callback : function(data) {
					var sid = data.responseText;
					//请返回登录状态信息
					if (sid != null && sid != '0' && sid!='vc' && sid!='ft'&& sid!='sft' ) {
						location.href = '${basePath}/managment/frame/index.do?sessionId='+sid;
					} else if(sid=='vc') {
						$("#loginMsg").removeClass('loading16').addClass("Validform_wrong").text("验证码错误！");
						changeVerifyCode();
						return false;
					}else if(sid=="ft"){
						$("#loginMsg").removeClass('loading16').addClass("Validform_wrong").text("登录凭证已过期，请联系系统管理员！");
						changeVerifyCode();
						return false;
					}else if(sid=="sft"){
						$("#loginMsg").removeClass('loading16').addClass("Validform_wrong").text("登录凭证不合法，请联系系统管理员！");
						changeVerifyCode();
						return false;
					}else {
						$("#loginMsg").removeClass('loading16').addClass("Validform_wrong").text("用户名密码错误！");
						changeVerifyCode();
						return false;
					}
				},
				tiptype:function(msg,o,cssctl){
					var objtip=$("#loginMsg");
					cssctl(objtip,o.type);
					objtip.text(msg);
				},
			});
		$('#loginSubmit').click(function() {
			var verifyCode = $("#verifyCode").val();
			verifyCode = verifyCode.toLowerCase();
			var cokieVc = cookie("vc");
			cokieVc = cokieVc.toLowerCase();
			if(verifyCode!=cokieVc) {
				$("#loginMsg").removeClass('loading16').addClass("Validform_wrong").text("验证码错误！");
				changeVerifyCode();
				return ;
			}
			$("#myForm").submit();
		});
		
		$(function() { 
			getBg();
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
		
		
		$("#password").focusin(function() {
			capsLockTips({pwd:"password",cap:"capital"});
		});
		$("#password").focusout(function() {
			$('#capital').css('display','none');
		});
		
		/* 背景图片轮播 */
		function getBg(){
			var str='${basePath}/images/login-bg/bg-1.png | ${basePath}/images/login-bg/bg-2.png | ${basePath}/images/login-bg/bg-3.png | ${basePath}/images/login-bg/bg-4.png ';
			var cl = ["#48566B" ,"#415472" ,"#7A612D" ,"#7C4830"];
			var imgArrs=str.split('|');
			var randNum = cookie("bgRandNum");
			if(!randNum){
				randNum = parseInt(imgArrs.length*Math.random());
				cookie("bgRandNum", randNum, { expires: 7 });
			}else if(randNum>imgArrs.length){
				randNum=0;
			}
			var url=imgArrs[randNum];
			var cr=cl[randNum];
			$('.contain').css('background','url("'+url+'") no-repeat center bottom');
			$('body').css('background','url("'+url+'") no-repeat center top');
			$('#loginSubmit').css('background',cr);
		}
		// 公共操作Cookie的方法，用法如：【base.utils.cookie("cookieName", "cookieValue", { expires: 7 }); //存储一个带7天期限的cookie】
        function cookie (name, value, options) {
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
		
		/**大小写锁定检查*/
		function capsLockTips(obj){
			var _pwd=obj.pwd;
			var _cap=obj.cap;
			var inputPWD = document.getElementById(_pwd);
			var capital = false;
			var capitalTip = {
				elem : document.getElementById(_cap),
				toggle : function(s) {
					var sy = this.elem.style;
					var d = sy.display;
					if (s) {
						sy.display = s;
					} else {
						sy.display = d == 'none' ? '' : 'none';
					}
				}
			}
			var detectCapsLock = function(event) {
				if (capital) {
					return
				}
				;
				var e = event || window.event;
				var keyCode = e.keyCode || e.which; // 按键的keyCode
				var isShift = e.shiftKey || (keyCode == 16) || false; // shift键是否按住
				if (((keyCode >= 65 && keyCode <= 90) && !isShift) // Caps Lock 打开，且没有按住shift键
						|| ((keyCode >= 97 && keyCode <= 122) && isShift)// Caps Lock 打开，且按住shift键
				) {
					capitalTip.toggle('block');
					capital = true
				} else {
					capitalTip.toggle('none');
				}
			}
			inputPWD.onkeypress = detectCapsLock;
			inputPWD.onkeyup = function(event) {
				var e = event || window.event;
				if (e.keyCode == 20 && capital) {
					capitalTip.toggle();
					return false;
				}
			}
		}
		
	</script>
</body>
</html>