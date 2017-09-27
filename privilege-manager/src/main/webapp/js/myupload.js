/*
	模拟表单异步提交
	原理：根据iframe的onload事件监听上传情况，
	onload事件第二次触发就表示上传完毕。（不同的浏览器onload事件触发的次数不一致）。
*/
var upload_flag=0;    //该变量用于判定是否上传完毕
var messageid;		  //JqueryminiUI里的遮罩层
var timer1;			  //定时器:用于显示进度条

//返回用户使用的浏览器  值：  opera ；webkit；ie...
function userAgent(){
    var ua = navigator.userAgent;
    ua = ua.toLowerCase();
    var match = /(webkit)[ \/]([\w.]+)/.exec(ua) ||
    /(opera)(?:.*version)?[ \/]([\w.]+)/.exec(ua) ||
    /(msie) ([\w.]+)/.exec(ua) ||
    !/compatible/.test(ua) && /(mozilla)(?:.*? rv:([\w.]+))?/.exec(ua) ||
    [];
    return match[1];
}

//iframe的onload事件函数
function closewin(obj){
	upload_flag++;
	var vsn=userAgent();
	switch(vsn){
	    case "webkit":     //safari or chrome
	    	upload_flag++;
	     break;
	    case "opera":      //opera
	    	upload_flag++;
	     break;
	    default:    
	     break;
	}
	if(upload_flag>1){
		mini.hideMessageBox(messageid);
		var responseText=obj.contentWindow.document.body.innerHTML;
		responseText=mini.decode(responseText);
		if(responseText.responseCode==101){
			mini.alert("异常错误！");
			clearTimeout(timer1);
			return;
		}
		if(responseText.responseCode==100){
			clearTimeout(timer1);
			upload_flag=0;
			CloseWindow("save");
		}
	}
}

//验证上传文件的格式
function vilidfile(val){
	var indx=val.lastIndexOf(".");
	var str=val.substring(indx,val.length);
	if(str!='.jpg' && str!='.JPG' && str!='.png' && str!='.PNG' && str!='.icon'){
		mini.alert('格式不正确!');
	}
}

//进度条处理
function showPercent(){
	$.ajax({
		url:bootPATH.substring(0,bootPATH.length-3)+'managment/process/getPercent.do',
		type:'post',
		success:function(text){
			$("#percent").html(text).css("width",text);
		}
	});
	timer1=setTimeout(showPercent,500);
}

//上传（页面上得保存按钮的处理函数）,参数为JqueryminiUI表单对象
function submit(yourForm){
	var val=mini.get("file").value;
	var indx=val.lastIndexOf(".");
	var str=val.substring(indx,val.length);
	if(val!=null && val!="" && val!='undefined' && val!=null && str!='.jpg' && str!='.JPG' && str!='.png' && str!='.PNG' && str!='.icon'){
		mini.alert('格式不正确!');
		return;
	}
	yourForm.validate();
	if (yourForm.isValid() == false) return;
	yourForm.submit();
	messageid = mini.loading('正在保存...'+
			'<div id="percentDiv" style="width:180px;height:10px; background-color:#CCC; border-radius:5px; -moz-border-radius:5px; margin-top:5px;">'+
			'<div id="percent" style="width:0%; background:green; float:left; height:10px;line-height:10px;'+
			' border-radius:5px; -moz-border-radius:5px; color:#FFF; font-size:10px; text-align:right">0%</div>'+   
			'</div>'
	);
	showPercent();
}