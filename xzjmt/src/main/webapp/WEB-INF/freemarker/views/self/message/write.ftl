<@override name="body">
<form id="pageForm" action="${wwwroot}/self/message/send" class="form-horizontal" method="post">
	<input type="hidden" name="receiver" value="${(recUser.userId)!}">
  <fieldset>
		<legend></legend>
		<div class="blank"></div>
		<div class="row">
			<div class="span8">		
				<div class="control-group">
				  <label class="control-label" for="nickName">收信人：</label>
				  <div class="controls">
				  	<div class="read">
				  		${(recUser.nickName)!}
				  	</div>
				  </div>
				</div>
				<div class="control-group">
				  <label class="control-label" for="title">标题：</label>
				  <div class="controls">
				    <input type="text" class="text" id="title" name="title" required maxLength="50">
				  </div>
				</div>	
				<div class="control-group">
		        	<label class="control-label" for="content">内容(最多800字)：</label>
		            <div class="controls">
		              <textarea class="input-xlarge" id="content" name="content" rows="7" required maxLength="800"></textarea>
		            </div>
		        </div>					
			
				<div class="form-actions">
		            <a class="btn btn-primary">发送</a>
		        </div>
	      </div>
	    	<div class="span4">
	    		<a href="${wwwroot}/self/message/list">&gt;回我的邮箱</a>
	    	</div>
	</div>			        	       
  </fieldset>
</form>
<script>
$().ready(function() {
	$("#pageForm").validate();
});
    jQuery(function($){
		$('.btn-primary').click(function(){
			$('#pageForm').submit();
		});
    });
</script>      				
</@override>
<@extends name="../../include/base.ftl" />