<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <title>无权限</title>
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <link rel="stylesheet" href="${basePath}/assets/css/nopwerbase.css"/>
    <link rel="stylesheet" href="${basePath}/assets/css/public.css"/>
    <link rel="stylesheet" href="${basePath}/assets/css/unauthorized.css"/>
</head>

<body>
<div class="main">
    <div class="unauthorized">
        <div class="unauthorized-msg">
            <span class="qin">亲，</span><span>该系统您还未授权哦！</span>
        </div>
        <div class="unauthorized-action">
            <a class="btn-unauthorized" href="javascript:history.go(-1);">进入门户首页</a>
            <!--<a class="btn-unauthorized" href="/login.shtml">返回重新登录</a>-->
        </div>
        <div class="unauthorized-img">
            <img src="${basePath}/assets/images/unauthorized-bg.png">
        </div>
    </div>
</div>
<!--演示-->
<script src="${basePath}/assets/js/libs/jquery-1.6.2.min.js"></script>
<script>
    $(function(){
        $("body").height(document.documentElement.clientHeight);
        $(window).resize(function(){
            $("body").height(document.documentElement.clientHeight);
        });
    });
    if(top.location!=self.location){  
        top.location = window.location;  
    }else{  
        //window.location.href = "/login.jsp";  
    }  
</script>

</body>
</html>