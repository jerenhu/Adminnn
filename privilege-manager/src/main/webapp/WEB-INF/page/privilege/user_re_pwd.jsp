<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="/WEB-INF/page/share/taglib.jsp" %>
<title>重置密码</title>
</head>
	<body>
	<center>
	<form id="formA">
        <div style="margin:20px auto;">
            <table class="form-tb" style="width:100%">
            	<tr>
                    <th width="180" class="vtop"><em class="cred">*</em>原密码：</th>
                    <td width="180">    
                        <input id="oldpwd" name="oldpwd" type="password" class="ipt easyui-validatebox" data-options="required:true,missingMessage:'请输入原密码'" />
                    </td>
                    <td >
                    	<%-- 大小写提示--%>
                        <span style="color: rgb(255, 153, 0); position:right;display:none;" id="capitalold">大写锁定已开启</span>
                    </td>
                </tr>
            	<tr>
                    <th class="vtop"><em class="cred">*</em>新密码：</th>
                    <td>    
                        <input id="newpwd" name="newpwd" type="password" class="ipt easyui-validatebox" data-options="required:true,validType:'checkpwdf',missingMessage:'请输入新密码'" />
                    </td>
                    <td>
                        <%-- 大小写提示--%>
                        <div style="color: rgb(255, 153, 0); position: right;display:none" id="capital">大写锁定已开启</div>
                    </td>
                </tr>
            	<tr>
                    <th class="vtop"><em class="cred">*</em>重复新密码：</th>
                    <td>    
                        <input id="renewpwd" name="renewpwd" type="password" class="ipt easyui-validatebox" data-options="required:true,validType:'checkpwd',missingMessage:'请再次输入新密码'" />
                    </td>
                    <td>
                        <%-- 大小写提示--%>
                        <div style="color: rgb(255, 153, 0); position: right;display:none" id="capitalre">大写锁定已开启</div>
                    </td>
                </tr>
            </table>
        </div>
    </form>
    </center>
	<script type="text/javascript">
	//验证标识的唯一性
	$.extend($.fn.validatebox.defaults.rules, {
		checkpwd: {
	    validator: function(value, param){
	    	var valid=true;
	    	var newpwd=$("#newpwd").val();
	    	if(newpwd!=value){
	    		valid=false;
	    	}
	    	return valid;
	    },
	    message: '两次输入密码不一致!'
	    }
	});
	//检查密码的格式
	$.extend($.fn.validatebox.defaults.rules, {
		checkpwdf: {
	    validator: function(value, param){
	    	 return /(^[a-zA-Z])(?![a-zA-Z]+$).{7,14}$/i.test($.trim(value));
	    },
	    message: '密码规则不合法(字母开头，允许8-15字节，不能全是字母)!'
	    }
	});
	
	$("#oldpwd").focusin(function() {
		capsLockTips({pwd:"oldpwd",cap:"capitalold"});
	});
	$("#oldpwd").focusout(function() {
		$('#capitalold').css('display','none');
	});
	
	$("#newpwd").focusin(function() {
		capsLockTips({pwd:"newpwd",cap:"capital"});
	});
	$("#newpwd").focusout(function() {
		$('#capital').css('display','none');
	});
	
	$("#renewpwd").focusin(function() {
		capsLockTips({pwd:"renewpwd",cap:"capitalre"});
	});
	$("#renewpwd").focusout(function() {
		$('#capitalre').css('display','none');
	});

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