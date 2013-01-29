<div class="modal" id="login_dialog" style="display:none">
  <div class="modal-header">
    <a class="close" data-dismiss="modal">×</a>
    	<div>登录闲置姐妹淘</div><a href="${wwwroot}/reg">(还没有账号?)</a>
  </div>
  <div class="modal-body">
		<div class="row-fluid">
			<div class="span12">
				<form id="ajaxLogin" action="${wwwroot}/ajaxauth" class="" method="post">
						<div class="row-fluid">
						<label id="ajaxLoginMsg" for="ajaxLogin" generated="true" class="error" style="display:none"></label>
					        账号	<input name="username" type="text" class="" placeholder="邮箱/昵称" required>
					        </div>
					        						<div class="row-fluid">
					        密码	<input name="password" type="password" class="" placeholder="密码" required>
					        </div>
							<div class="row-fluid">
								<div class="span6">
							        <label class="checkbox">
							          <input type="checkbox" value="remember-me"> 记住我
							        </label>								
								</div>
								<div class="span6">
									<a href="" class="pull-right">找回密码</a>
								</div>								
							</div>							
							<input class="btn" type="button" value="登录" onclick="javascript:ajaxAuth();">													

				</form>
			</div>
		</div>			
  </div>
  <#--
  <div class="modal-footer">
    <a href="#" class="btn">关闭</a>
    <a href="#" class="btn btn-primary">登录</a>
  </div>
  -->
<#--
<form class="form-horizontal">
  <fieldset>
    <legend>登录</legend>
    <div class="control-group">
      <label class="control-label" for="input01">账号</label>
      <div class="controls">
        <input type="text" class="input" id="input01">
      </div>
    </div>
    <div class="control-group">
      <label class="control-label" for="input01">密码</label>
      <div class="controls">
        <input type="text" class="input" id="input01">
      </div>
    </div>
  </fieldset>
</form>  
-->
</div>
<script>
function ajaxAuth()
{
	if($("#ajaxLogin").valid())
	{
		$('#ajaxLoginMsg').text('');
		$('#ajaxLoginMsg').hide();		
		$.ajax({
			type: "POST",
			url:$('#ajaxLogin').attr('action'),
			data:$('#ajaxLogin').serialize(),// 你的formid
			async: false,
			error: function(request) {
				alert("Connection error");
			},
			success: function(data) {
				if(data=='success')
				{
					location.reload();
				}
				else
				{
					$('#ajaxLoginMsg').text(data);
					$('#ajaxLoginMsg').show();
				}
				return false;
			}
		});		
	}
	
	return false;
}
</script>