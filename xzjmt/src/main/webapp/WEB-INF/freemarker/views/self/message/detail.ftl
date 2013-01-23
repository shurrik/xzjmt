<@override name="body">
	<form id="pageForm" action="${wwwroot}/self/message/send" class="form-horizontal" method="post">
	  <fieldset>
			<legend></legend>
			<div class="blank"></div>
			<div class="row">
				<div class="span8">
					<div class="control-group">
					  <label class="control-label" for="nickName">寄信人：</label>
					  <div class="controls">
					  	<div class="read">
					  		${(message.senderName)!}
					  	</div>
					  </div>
					</div>
					<div class="control-group">
					  <label class="control-label" for="createDate">发送时间：</label>
					  <div class="controls">
					  	<div class="read">
					  		${(message.createDate?string('yyyy-MM-dd HH:mm:ss'))!}
					  	</div>
					  </div>
					</div>				
					<div class="control-group">
					  <label class="control-label" for="title">标题：</label>
					  <div class="controls">
					  	<div class="read">
					  		${(message.title)!}
					  	</div>
					  </div>
					</div>	
					<div class="control-group">
			        	<label class="control-label" for="content">内容：</label>
			            <div class="controls">
						  	<div class="read">
			${(message.content)!}
						  	</div>
			            </div>
			        </div>					
			        
			        <#if replyAble??&replyAble>
					<div class="form-actions">
			            <a class="btn btn-primary" href="${wwwroot}/self/message/reply/${(message.id)!}">回复</a>
			        </div>
			        </#if>
	        	</div>
	        	<div class="span4">
	        		<a href="${wwwroot}/self/message/list">&gt;回我的邮箱</a>
	        	</div>
	        </div>        
	  </fieldset>
	</form>
</@override>
<@extends name="../../include/base.ftl" />