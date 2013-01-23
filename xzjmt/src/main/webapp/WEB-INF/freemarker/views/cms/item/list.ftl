
	<div class="hero-unit">
		<p></p>
  	</div>

	<div class="row-fluid">
		<div class="span12">
			<table class="table table-striped">
			    <thead>
			      <tr>
			        <th>#</th>
			        <th>名称</th>
			        <th>用户昵称</th>
			        <th>用户邮箱</th>
			        <th>分类</th>
			        <th>访问次数</th>
			        <th>收藏次数</th>
			      </tr>
			    </thead>
			    <tbody>
				<#list pageCtx.itemList as item>
			      <tr>
			        <td>${item_index+1}</td>
			        <td>${(item.name)!}</td>
			        <td>${(item.nickName)!}</td>
			        <td>${(item.email)!}</td>
			        <td>${(item.catName)!}</td>
			        <td>${(item.visited)!}</td>
			        <td>${(item.collected)!}</td>
			      </tr>
			    </#list>	          
			    </tbody>
			</table>		
		</div><!--/span-->
	</div><!--/row-->
