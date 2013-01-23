
	<div class="hero-unit">
		<p></p>
  	</div>

	<div class="row-fluid">
		<div class="span12">
			<table class="table table-striped">
			    <thead>
			      <tr>
			        <th>#</th>
			        <th>邮箱</th>
			        <th>昵称</th>
			        <th>闲置Id</th>
			        <th>注册时间</th>			        			        			        
			      </tr>
			    </thead>
			    <tbody>
				<#list pageCtx.itemList as collection>
			      <tr>
			        <td>${collection_index+1}</td>
			        <td>${(collection.email)!}</td>
			        <td>${(collection.nickName)!}</td>
			        <td>${(collection.itemId)!}</td>
					<td>${(collection.createDate?string('yyyy-MM-dd HH:mm:ss'))!}</td>			        			        			        
			      </tr>
			    </#list>	          
			    </tbody>
			</table>		
		</div><!--/span-->
	</div><!--/row-->
