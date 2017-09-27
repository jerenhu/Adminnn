<%-- 修改密码--%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="/WEB-INF/page/share/taglib.jsp" %>
<title>修改密码</title>
</head>
	<body>
	<center>
	<form id="formP">
        <div style="margin:20px auto;">
        	<input name="id" type="hidden" />
            <table class="form-tb">
            	<tr>
             		<th><em class="cred">*</em>用户名：</th>
                    <td>    
                        <input name="username" type="text" class="ipt easyui-validatebox" data-options="required:true,missingMessage:'请输入用户名'" readonly />
                    </td>
                </tr>
             	<tr>
                    <th class="vtop"><em class="cred">*</em>密码：</th>
                    <td>    
                    	<input id="password" name="password" type="password" class="ipt easyui-validatebox" data-options="required:true,missingMessage:'请输入密码'" />
                    </td>
                </tr>
                <tr>
                	<th></th>
                	<td>
                    	<%-- 大小写提示--%>
                        <div style="color: rgb(255, 153, 0); padding: 2px; position: left;display:none" id="capital">大写锁定已开启</div>
                    </td>
                </tr>
            </table>
        </div>
    </form>
    </center>
    <script type="text/javascript">
	$("#password").focusin(function() {
		capsLockTips({pwd:"password",cap:"capital"});
	});
	$("#password").focusout(function() {
		$('#capital').css('display','none');
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