<@override name="body">
<ul class="nav nav-pills">
  <li <#if !sendbox>class="active"</#if>>
    <a href="${wwwroot}/self/message/list">收件箱</a>
  </li>
  <li <#if sendbox>class="active"</#if>>
  	<a href="${wwwroot}/self/message/list?type=send">发件箱</a>
  </li>
</ul>
	<div class="row-fluid">
		<div class="span10">
			<table class="table">
			    <thead>
			      <tr>
			        <th>#</th>
			        <#if sendbox>
			        	<th>收信人</th>
			        <#else>
			        	<th>寄信人</th>
			        </#if>
			        
			        <th style="width:400px">标题</th>
			        <th>发送时间</th>
			      </tr>
			    </thead>
			    <tbody>
				<#list pageCtx.itemList as message>
			      <tr>
			        <td>${message_index+1}</td>
			        <#if sendbox>
			        	<td>${(message.receiverName )!}</td>			        	
			        <#else>
			        	<td>${(message.senderName )!}</td>
			        </#if>	
			        <td><a href="${wwwroot}/self/message/${(message.id)!}">${(message.title)!}</a></td>
			        <td>${(message.createDate?string('yyyy-MM-dd HH:mm:ss'))!}</td>

			      </tr>
			    </#list>	          
			    </tbody>
			</table>		
		</div><!--/span-->
	</div><!--/row-->     				
</@override>
<@extends name="../../include/base.ftl" />	