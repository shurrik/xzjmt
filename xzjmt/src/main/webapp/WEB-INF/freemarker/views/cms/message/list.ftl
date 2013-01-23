
	<div class="hero-unit">
		<p></p>
  	</div>

	<div class="row-fluid">
		<div class="span12">
			<table class="table table-striped">
			    <thead>
			      <tr>
			        <th>#</th>
			        <th>发送账号</th>
			        <th>接受账号</th>
			        <th>标题</th>			        
			        <th>内容</th>
			        <th>发送时间</th>
			        <th>阅读时间</th>			        
			      </tr>
			    </thead>
			    <tbody>
				<#list pageCtx.itemList as message>
			      <tr>
			        <td>${message_index+1}</td>
			        <td>${(message.senderEmail)!}</td>
			        <td>${(message.receiverEmail)!}</td>
					<td>${(message.title)!}</td>			        
					<td><@p.subStr content=(message.content)! length=20/></td>
					<td>${(message.createDate?string('yyyy-MM-dd HH:mm:ss'))!}</td>
					<td>${(message.readDate?string('yyyy-MM-dd HH:mm:ss'))!}</td>											        
			      </tr>
			    </#list>	          
			    </tbody>
			</table>		
		</div><!--/span-->
	</div><!--/row-->
