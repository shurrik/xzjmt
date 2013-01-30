/*	var host=location.hostname, _h=host.split('.');
	document.domain = _h.length > 2 ? host.substr(host.indexOf('.')+1) : host;
	//用于ajax操作时校验时校验是否登录，如果没有登录，则弹出登录框
	var ptn = /^([a-zA-Z0-9_\.\-\+])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	var showLoginDialog = function(){
		$.get(wwwroot+'/tologin',function (result) {
			$.blockUI({
			    message: result, focusInput: false,
			    theme: false,
			    css: {
			      border: 'none', background: 'none', width: 470+'px',
			      top: (($(window).height() - 190)/2) + 'px',
			      left: (($(window).width() - 470)/2) + 'px'
			    },
			    overlayCSS:  {backgroundColor:'#666', opacity:0.5}
			});
		});
	};*/
	//为ajax操作加上完成后的调用，如果没有登录，则调用登录接口
	var onComplete = function(result) {
		 var respText = result.responseText;
        //判断返回值不是 json 格式
	    if (!respText || !respText.match("^\{(.+:.+,*){1,}\}$")) {
			return;
		} else {
			// 通过这种方法可将字符串转换为对象
			jsonResp = eval("(" + respText + ")");
			if(jsonResp.needAjaxLogin){
				//弹出登录框
				//showLoginDialog();
				popLogin();
			}
		}
	   
	};
	$(document).ready(function() {
		$.ajaxSetup({
			complete : onComplete
		});
/*		$("#J_uid").live('focus', function () {
			var tgt = $('#J_LoginTicket');
			if(tgt && tgt.val().length == 0)
				flushLoginTicket();  // 进入登录页，则获取login ticket
		});*/
	});

/*   //由于一个 login ticket 只允许使用一次, 当每次登录需要调用该函数刷新 lt
	var flushLoginTicket = function(){  
	    var _services = 'service=' + encodeURIComponent(wwwroot+'/sso');
	    var url = casroot+'/login?'+_services+'&get-lt=true&n='+ new Date().getTime();
	    $.getScript(url, function(data, textStatus, jqxhr) {
	    	$("#J_LoginTicket").val(_loginTicket);
	    	$("#J_execution").val(_execution);
		});
	};
	// 登录验证函数, 由 onsubmit 事件触发
	var loginValidate = function(){
	    var msg;
	    var uid = $('#J_uid'), pwd = $('#J_pwd');
	    if ($.trim(uid.val()).length == 0 ){
	        msg = "请输入您的邮箱或昵称";
	        uid.focus();
	    } else if ($.trim(pwd.val()).length == 0 ){
	        msg = "请输入密码";  
	        pwd.focus();
	    }
	    if (msg && msg.length > 0) {
	        $('#loginTip').text(msg).fadeIn();
	        return false;
	        // Can't request the login ticket.
	    } else if ($('#J_LoginTicket').val().length == 0){
	        $('#loginTip').text('登录失败，请稍后再试。');pwd.val('');
	        return false;
	    } else {
	        // 验证成功后，动态创建用于提交登录的 iframe 
	    	if(($("#ssoLoginFrame")[0])) return false;
	        $('body').append($('<iframe name="ssoLoginFrame"/>').attr({
	            style: "display:none;width:0;height:0",
	            id: "ssoLoginFrame",
	            src: "javascript:false;"
	        }));
	        return true;
	    } 
	};
	// 登录处理回调函数，将由 iframe 中的页同自动回调  
	var feedBackUrlCallBack = function (result) {
	    customLoginCallBack(result);
	    deleteIFrame('#ssoLoginFrame');// 删除用完的iframe,但是一定不要在回调前删除，Firefox可能有问题的  
	};
	
	// 自定义登录回调逻辑
	var customLoginCallBack = function(result){
	    // 登录失败，显示错误信息
	    if (result.login == 'fails'){
	    	$('#J_pwd').val('').focus();
	        $('#loginTip').text(result.msg).fadeIn();
	        // 重新刷新 login ticket
	        flushLoginTicket();
	    } else {
	    	$('#loginTip').text("登录成功,正转向登录前页面...").fadeIn();
	    	setTimeout(flushLoginTicket, 20);
	    	setTimeout(function () {
	    		location.reload();
    		}, 1000);
	    }
	};
	var deleteIFrame = function (iframeName) {
	    var iframe = $(iframeName);
	    if (iframe) { // 删除用完的iframe，避免页面刷新或前进、后退时，重复执行该iframe的请求
	        iframe.remove();
	    }
	};
	
	function closeMask(){
		$("#mask,#login_dialog").hide().remove();
		$.unblockUI();
	}*/