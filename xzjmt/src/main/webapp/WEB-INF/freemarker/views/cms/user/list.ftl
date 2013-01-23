
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
			        <th>手机</th>
			        <th>qq</th>
			        <th>注册时间</th>			        			        			        
			      </tr>
			    </thead>
			    <tbody>
				<#list pageCtx.itemList as user>
			      <tr>
			        <td>${user_index+1}</td>
			        <td>${(user.email)!}</td>
			        <td>${(user.nickName)!}</td>
			        <td>${(user.mobile)!}</td>
			        <td>${(user.qq)!}</td>
					<td>${(user.registerDate?string('yyyy-MM-dd HH:mm:ss'))!}</td>			        			        			        
			      </tr>
			    </#list>	          
			    </tbody>
			</table>		
		</div><!--/span-->
	</div><!--/row-->
