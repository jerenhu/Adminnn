
var Index = {
    tabs:null,
    init: function () {
        var tree = $("#leftMenu");
        this.tabs = $("#mainTabs");

        //节点点击事件
        tree.tree({
        	onBeforeExpand:function(node){
        		//展开前关闭其他同级节点
        		if(!node.pid){
        			var roots = tree.tree("getRoots");
            		for(var i = 0; i< roots.length; i++){
            			if(node.id != roots[i].id){
            				tree.tree("collapse", roots[i].target);
            			}
            		}
        		}
        	},
        	onClick: function(node){
        		if(tree.tree("isLeaf",node.target)){
        			node.url=node.url.substring(node.url.lastIndexOf("http://"));//20161216tuanzuo 如果链接中有多个"http://"那么使用最后一个"http://"作为真正的url
        			addBlankTab(node);
        		}else{
        			$("#leftMenu").tree('toggle', node.target);
        		}
        	},
        	//追加节点
	        onLoadSuccess:function(node, data){
	        	if(data.length>0){
	        		tree.tree('collapseAll');
	        		for(var i=0;i<data.length;i++){
	        			var obj=data[i];
	        			if(obj.isLeaf!=undefined&&!obj.isLeaf){
	        				$('#'+obj.domId).append('<i class="jsChangeIcon expand-icon"></i>');
	        			}
	        			if(i==0){
	        				tree.tree('expand', obj.target);
	        			}
	        		}
	        	}
	        	
	        },
	        onCollapse:function(node){
	        	var _this=node.target;
	        	$(_this).find('.jsChangeIcon').removeClass('collapse-icon').addClass('expand-icon');
	        },
	        onExpand:function(node){
	        	var _this=node.target;
	        	$(_this).find('.jsChangeIcon').removeClass('expand-icon').addClass('collapse-icon');
	        },
	        
        });
        /*
        //导航点击
        $('#navMenu li').bind('click',
            function () {
                var _this = $(this);
                _this.addClass('curr').siblings().removeClass('curr');
                _this.children('.nav-icon-list').removeClass('${system.sn}-icon').addClass('${system.sn}-icon1').siblings().removeClass('${system.sn}-icon1');
                var _tt = _this.find('span').text();
                var _url = _this.attr('data-url');
                var _deskUrl=_this.attr('data-deskurl');
                var _sessionId = _this.attr('data-sessionid');
                $('#west').find('.mini-layout-region-title').html(_tt + '菜单');
                tree.tree("options").url=_url;
                $("#leftMenuPanel").panel({
                	title:_this.html().trim()+"菜单"
                });
                tree.tree("reload");
                $('#deskFrame').attr('src',_deskUrl+"/index.do?sessionId="+_sessionId);
                return false;
        });*/
        /*$('#leftMenu').tree({
        	onSelect: function(e, node){
        		if( $("#leftMenu>li>div").css("display")=='none' ){
            		alert(123);
            	}else{
            		alert(146);
            	}
        	}

        });*/
        
        //系统导航点击
        $('#systemNavMenu').tabs({
        	onSelect: function(title){
        		var _this = $($(title).get(1));
               	var _tt = _this.text();
                var _url = _this.attr('data-url');
                var _deskUrl=_this.attr('data-deskurl');
	            var _sessionId = _this.attr('data-sessionid');
	            $('#west').find('.mini-layout-region-title').html(_tt + '菜单');
	            tree.tree("options").url=_url;
	            $("#leftMenuPanel").panel({
	            	title:_this.html().trim()+"菜单"
	            });
	            tree.tree("reload");
	            $('#deskFrame').attr('src',_deskUrl+"/index.do?sessionId="+_sessionId);
	            return false;
			},
			onContextMenu:function(e, title, idx){
				return false;
			}
		}); 

        //系统桌面点击
        $('#lnkDesk').click(function(){
            Index.tabs.tabs('select',0);
        });
    }
};

//全屏切换
var maxTab = false;//最大化
function tabToolsFullScreenOnClick(){
	if (maxTab) {
        maxTab = false;
        $("body").layout('expand','north');
        //$("body").layout('expand','south');
        $("body").layout('expand','west');
        $("#tabToolsFullScreen").linkbutton({
    		iconCls: 'icon-window-max',
    		text: '全屏'
    	});
    } else {
        maxTab = true;
        $("body").layout('collapse','north');
        //$("body").layout('collapse','south');
        $("body").layout('collapse','west');
        $("#tabToolsFullScreen").linkbutton({
    		iconCls: 'icon-window-min',
    		text: '收起'
    	});
    }
}

function closeTab() {
	Index.tabs.removeTab(currentTab);
}

function closeAllBut() {
	var but = [currentTab];            
    but.push(Index.tabs.getTab("first"));
    Index.tabs.removeAll(but);
}

function closeAll() {
	var but = [];            
    but.push(Index.tabs.getTab("first"));
    Index.tabs.removeAll(but);
}


function refreshMainTab(title){
	if(title=="current"){
		currentTab=Index.tabs.getActiveTab();
	}else{
		currentTab=getTabByTitle(title);
	}
	Index.tabs.reloadTab(currentTab);
}

//刷新[easyui]一个没有id的tab
function refreshMainTabForNoteIdIsNull(node){
	updateEasyUITab(node);
}

function closeMainTab(title) {
	if (title == "current") {
		currentTab = Index.tabs.getActiveTab();
	} else {
		currentTab = getTabByTitle(title);
	}
	Index.tabs.tabs('close',currentTab);
}

//关闭一个没有id的tab
function closeMainTabForNoteIdIsNull(obj) {
	var title = $.trim(obj.title).slice(1,-1); 
	if (title == "current") {
		currentTab = Index.tabs.getActiveTab();
	} else {
		title+='<span style=\'display:none;\'>undefined</span>';
		currentTab = getTabByTitle(title);
	}
	Index.tabs.tabs('close',currentTab);
}

function getTabByTitle(title){
	//var tabs=Index.tabs.getTabs();
	var tabs=Index.tabs.tabs('tabs');
	var currentTab=null;
	for(var i=0,j=tabs.length;i<j;i++){
		if($.trim(tabs[i].panel('options').title)==$.trim(title)){
			currentTab=$.trim(tabs[i].panel('options').title);
			return currentTab;
		}
	}
	return currentTab;
};



$(function(){
    Index.init();
    var $allLi=$("#navMenu li");
    var liLength=$allLi.length;
    if(liLength<11){
    	$('.arrow-icon').hide();
    }else{
    	$('.arrow-icon').show();
    }
    if($allLi && $allLi.length>0){
    	for(var i=0;i<$allLi.length-2;i++){
    		var before=$($allLi[i]).position().left;
    		var after=$($allLi[i+1]).position().left;
    		if(before>after){
    			$("#moreMenu").show();
    			$("#navMenu ul").width($allLi.length*160);
    		}
    	}
    }
    $("#moreMenu").click(function(){
    	if($("#navMenu li").is(":animated")){
    		return;
    	} else{
	    	if(($($allLi[$allLi.length-1]).position().left+80)<$("#moreMenu").position().left){
	    		
	    	}
	    	
	    	$("#navMenu li").animate({left:"-=80px"});
	    	$("#moreMenu1").show();
	    	var lastLi=$($allLi[$allLi.length-1]).position().left+80;
	    	var mMenu=$("#moreMenu").position().left;
	    	if(lastLi<mMenu){
	    		$("#navMenu li").animate({left:"+=80px"});
	    		$("#moreMenu").removeClass('white-right-arrow').addClass('gray-right-arrow');
	    	}else{
	    		$("#moreMenu1").removeClass('gray-left-arrow').addClass('white-left-arrow');
	    	}
    	}
    });
    $("#moreMenu1").click(function(){
    	if($("#navMenu li").is(":animated")){
    	} else{
	    	if($($allLi[0]).position().left>=0){
	    		
	    	}
	    	$("#navMenu li").stop().animate({left:"+=80px"});
	    	
	    	var first=$($allLi[0]).position().left;
	    	if(first>=0){
	    		$("#navMenu li").animate({left:"-=80px"});
	    		//$("#moreMenu1").hide();\
	    		$("#moreMenu1").removeClass('white-left-arrow').addClass('gray-left-arrow');
	    		
	    		
	    		return;
	    	}else{
	    		$("#moreMenu").removeClass('gray-right-arrow').addClass('white-right-arrow');
	    	}
	    	var lastLi=$($allLi[$allLi.length-1]).position().left;
	    	var mMenu=$("#moreMenu").position().left;
	    	if(lastLi>mMenu){
	    		
	    		return;
	    	}
    	}
    	
    });
    //跨域调用方法
    var messenger = new Messenger('parent', 'MessengerIcEasy');
    messenger.listen(function (msgs) {
    	var arrMsg=msgs.split('{|}');
    	var t=arrMsg[0];
    	var msg=arrMsg[1];
        switch(t){
        case "showSuc":
        	showSuc(msg);
        	break;
        case "showInfo":
        	showMsg(msg,"info");
        	break;
        case "showWarn":
        	showMsg(msg,"warn");
        	break;
        case "showError":
        	showMsg(msg,"error");
        	break;
        case "showTab": //添加 或者 刷新tab 例如    showTab({id:'可以不写',text:'',url:'',refresh:true(刷新)/false(不刷新)});
        	addBlankTab(msg);
        	break;	
        case "refreshTab": //easyui刷新tab 例如 refreshTab('系统列表')
        	//refreshMainTab(msg);
        	refreshMainTabEasyUI({text:msg});
        	break;	
        case "closeTab":
        	closeMainTab(msg);
        	break;
        case "closeTabEx": //需要在其他系统的 base.js中定义这个方法，例如：var\www\html\easyui\1.4\resources\assets\js\base.js   例如  closeTabEx('系统列表');
        	closeMainTabForNoteIdIsNull({title:msg});//关闭[easyui]一个没有id的tab
        	break;
        case "refreshTabEx": //刷新[easyui]一个没有id的tab 例如：refreshTabEx({id:'可以不写',text:'',url:''})
        	refreshMainTabForNoteIdIsNull(msg);
        	break;
        }
        
    });
    
});

/**20160420wen得到当前页面的title*/
function getCurrentTabTitle(){
	Index.tabs.tabs('getSelected').panel('options').title;
}

/**20160420wen刷新tab的内容:easyui*/
function refreshMainTabEasyUI(node){
	if(!node.text){
		node=eval("("+node+")"); //转成对象
	}
	var _title=node.text;
	_title=Index.tabs.find(".tabs-title:contains('"+_title+"')").html().replace(/\"/g,"'");
	var tab = Index.tabs.tabs('getTab', _title);
	if(tab){
		if(node.url && node.url!=''){
			var _content= '<iframe style="border:none;width:100%;height:100%;" src="'+node.url+'" frameborder="0" scrolling="none"></iframe>';
			Index.tabs.tabs('update', {
				tab : tab,
				options : {
					title : _title,
					content : _content
				}
			});
		}else{
			tab.panel('refresh');
		}
	}
}


function showSuc(msg){
	/**
	 * ui.js已经重写了 $.messager.show 方法，
	 * 已经重写：/privilege-manager/src/main/webapp/resources/assets/js/libs/ui.js----> $.messager.show = function (options) {
	 */
	$.messager.show({
		title:'提示',
		msg:msg,
		timeout:1000,
		showType:'slide',
		position:'bottomRight' //右下角
	});
	
    /*top.mini.showTips({
        content: '<b>提示</b> <br/>'+msg,
        state: 'info',
        x:'right',
        y: 'bottom',
        timeout:1000
    });*/
}


function showMsg(msg,t){
	/*var _iconCls="";
	switch(t){
	case "info":
		_iconCls="mini-messagebox-info";
		break;
	case "warn":
		_iconCls="mini-messagebox-warning";
		break;
	case "error":
		_iconCls="mini-messagebox-error";
		break;
	}
	var _w=400;
	if(msg.length>300){
		_w=650;
        msg="<div style='width:100%;height:250px;overflow:auto;word-break:break-all;float:left;'>"+msg+"</div>";
    }
	
	mini.showMessageBox({
        width: _w,
        title: "提示",
        buttons: ["ok"],
        message: msg,
        iconCls:_iconCls
    });*/
	var _iconCls="";
	var _title="";
	switch(t){
	case "info":
		_iconCls="info";
		_title="提示";
		break;
	case "warn":
		_iconCls="warning";
		_title="警告";
		break;
	case "error":
		_iconCls="error";
		_title="错误";
		break;
	}
	var _w=400;
	if(msg.length>300){
		_w=650;
        msg="<div style='width:100%;height:250px;overflow:auto;word-break:break-all;float:left;'>"+msg+"</div>";
    }
	$.messager.alert(_title,msg,_iconCls);
}

function addBlankTab(node) {
	if(!node.text){
		node=eval("("+node+")"); //转成对象
	}
	var _title=node.text+"<span style='display:none;'>"+node.id+"</span>";
	var _content= '<iframe style="border:none;width:100%;height:100%;" src="'+node.url+'" frameborder="0" scrolling="none"></iframe>';
	
	var tab=Index.tabs.tabs('getTab',_title);
	if(!tab){
		Index.tabs.tabs('add',{
			title: _title,
			fit: true,
			closable:true,
			content:_content
		});
		$.messager.progress('close');
	}else{
		Index.tabs.tabs('select',node.text+"<span style='display:none;'>"+node.id+"</span>");
		
		//更新内容
		if(node.refresh){
			updateEasyUITab(node);
		}
	}
}

//20160219刷新easyui的tab
function updateEasyUITab(node){
	if(!node.text){
		node=eval("("+node+")"); //转成对象
	}
	var _title=node.text+"<span style='display:none;'>"+node.id+"</span>";
	var _content= '<iframe style="border:none;width:100%;height:100%;" src="'+node.url+'" frameborder="0" scrolling="none"></iframe>';
	var tab=Index.tabs.tabs('getTab',_title);
	Index.tabs.tabs('update',{
		tab: tab,
		options: {
			title: _title,
			content: _content
		}
	});
}

