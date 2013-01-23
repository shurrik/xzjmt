<@override name="body">
<form id="pageForm" action="${wwwroot}/self/message/send" class="form-horizontal" method="post">
	<input type="hidden" name="receiver" value="${(message.sender)!}">
  <fieldset>
		<legend></legend>
		<div class="blank"></div>
		<div class="control-group">
		  <label class="control-label" for="nickName">收信人：</label>
		  <div class="controls">
		  	<div class="read">
		  		${(message.senderName)!}
		  	</div>
		  </div>
		</div>
		<div class="control-group">
		  <label class="control-label" for="title">标题：</label>
		  <div class="controls">
		    <input type="text" class="text" id="title" name="title">
		  </div>
		</div>	
		<div class="control-group">
        	<label class="control-label" for="content">内容</label>
            <div class="controls">
              <textarea class="input-xlarge" id="content" name="content" rows="7" >
              
              
-------------------------------
${(message.content)!}
              </textarea>
            </div>
        </div>					
	
		<div class="form-actions">
            <a class="btn btn-primary">发送</a>
        </div>
        
  </fieldset>
</form>
<script>
    jQuery(function($){
		$('.btn-primary').click(function(){
			$('#pageForm').submit();
		});
    });
</script>      				
</@override>
<@extends name="../../include/base.ftl" />