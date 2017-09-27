window.onload=function(){
	checkBrowser();
};

function checkBrowser(){
	try{
		var browser=navigator.appName;
		var b_version=navigator.appVersion;
		var version=b_version.split(";"); 
		var trim_Version=version[1].replace(/[ ]/g,""); 
		var _ie6=browser=="Microsoft Internet Explorer" && trim_Version=="MSIE6.0";
		var _ie7=browser=="Microsoft Internet Explorer" && trim_Version=="MSIE7.0";
		if(_ie6 || _ie7){
			var _html='<div style="line-height:26px;">'+
			'<p>为提高系统浏览速度和工作效率，请使用IE8.0以上浏览器,<br/>'+
			'建议使用<a class="cblue" href="http://w.x.baidu.com/alading/anquan_soft_down_normal/11843">Firefox浏览器</a>或<a class="cblue" href="http://www.google.cn/intl/zh-CN/chrome/">Chrome浏览器</a>最佳。</p>'+
			'</div>';
			document.write(_html);
			document.close();
		}
	}catch(e){
		//
	}
}

	