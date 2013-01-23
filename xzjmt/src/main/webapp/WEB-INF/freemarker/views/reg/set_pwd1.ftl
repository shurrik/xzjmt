<@override name="body">
<form id="pageForm" action="${wwwroot}/reg/save" class="form-horizontal" method="post">
	<input type="hidden" name="userId" value="${(userId)!}">
  <fieldset>
		<legend>注册闲置姐妹淘</legend>
		<div class="control-group">
		  <label class="control-label" for="nickName">昵称：</label>
		  <div class="controls">
		    <input type="text" class="input" id="nickName" name="nickName">
		  </div>
		</div>
		<div class="control-group">
		  <label class="control-label" for="input01">密码：</label>
		  <div class="controls">
		    <input type="text" class="text" id="passwd" name="passwd">
		  </div>
		</div>		
	
		<div class="form-actions">
            <a class="btn btn-primary">保存</a>
        </div>
        
  </fieldset>
</form>
<script>
$(function(){
	$('.btn-primary').click(function(){
		$('#pageForm').submit();
	});
});
</script>      				
</@override>
<@extends name="../include/base.ftl" />