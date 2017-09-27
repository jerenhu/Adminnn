<!DOCTYPE HTML>
<html>
<head>
<meta charset='utf-8'>
<title>&#20122;&#21414;&#32929;&#20221; -
	&#34987;&#36843;&#19979;&#32447;&#36890;&#30693;</title>
<style>
* {
	margin: 0;
	padding: 0;
	font-family: '&#24494;&#36719;&#38597;&#40657;';
	text-align: center;
}

html, body, .main {
	width: 100%;
	height: 100%;
}

body {
	margin: 0 auto;
}

.main {
	position: relative;
	overflow: hidden;
}

.unauthorized {
	position: absolute;
	top: 50%;
	left: 50%;
	margin-top: -256px;
	margin-left: -306px;
	width: 612px;
	height: 512px;
}

.unauthorized-msg {
	color: #0b4d5c;
	line-height: 40px;
}

.unauthorized-msg .msg-title {
	font-size: 40px;
}

.unauthorized-msg .msg-how {
	font-size: 24px;
}

.unauthorized-action {
	width: 100px;
	margin: 30px auto;
}

.btn-unauthorized {
	display: inline-block;
	width: 100px;
	height: 38px;
	line-height: 38px;
	color: #fbfeff;
	font-size: 12px;
	background: #0e65af;
	border-radius: 4px;
	text-decoration: none;
}

.btn-unauthorized:hover {
	opacity: 0.9;
}

.setTimeOut {
	margin-top: 16px;
	color: #a4a4a4;
	font-size: 14px;
	line-height: 24px;
}

.setTimeOut span {
	display: inline-block;
	width: 20px;
	color: #f00000;
}

.hide {
	width: 0;
	height: 0;
	border: 0;
	overflow: hidden;
}
</style>
</head>
<body id='body'>
	<div class='main'>
		<div class='unauthorized'>
			<iframe border='0' width='0' height='0' class='hide'
				src='https://idmtest.chinayasha.com:8443/siam/logout'></iframe>
			<div class='unauthorized-msg'>
				<span class='msg-title'>Sorry&#65292;</span><span class='msg-how'>&#24744;&#24050;&#32463;&#34987;&#36843;&#19979;&#32447;&#65292;&#35831;&#37325;&#26032;&#30331;&#24405;&#65281;</span>
			</div>
			<div class='setTimeOut'>
				<span id='time'>3</span>&#31186;&#21518;&#33258;&#21160;&#36339;&#36716;
			</div>
			<div class='unauthorized-action'>
				<a class='btn-unauthorized' href='javascript:void(0);'
					onclick='toLogin();'>&#31435;&#21363;&#21069;&#24448;</a>
			</div>
			<div class='unauthorized-img'>
				<img src='/assets/img/hr-service/unauthorized-bg.png' />
			</div>
		</div>
	</div>
	<script>
		var toUrl = '/portal/workplat/system/customize.jhtml';
		try {
			toUrl = window.top.document.referrer;
		} catch (M) {
			if (window.parent) {
				try {
					toUrl = window.parent.document.referrer;
				} catch (L) {
					toUrl = '';
				}
			}
		}
		if (toUrl === '') {
			toUrl = document.referrer;
		}
		var timer = null;
		function toLogin() {
			window.location.href = toUrl;
			clearInterval(timer);
		}
		function run() {
			var s = document.getElementById('time');
			if (s.innerHTML == 1) {
				toLogin();
				return false;
			}
			s.innerHTML = s.innerHTML * 1 - 1;
		}
		timer = window.setInterval('run();', 1000);
	</script>
</body>
</html>