	<div class="subnav">
	    <ul class="nav nav-pills">
	      <li class=""><a href="cms/category/add">新增</a></li>
	      <li class=""><a href="cms/category/edit">编辑</a></li>
	      <li class=""><a href="cms/category/delete">删除</a></li>
	    </ul>
  	</div>

	<div class="row-fluid">
		<div class="span10">
			<table class="table table-striped">
			    <thead>
			      <tr>
			        <th>#</th>
			        <th>分类名称</th>
			        <th>顺序</th>
			      </tr>
			    </thead>
			    <tbody>
				<#list pageCtx.itemList as cat>
			      <tr>
			        <td>${cat_index+1}</td>
			        <td>${(cat.catName)!}</td>
			        <td>${(cat.order)!}</td>
			      </tr>
			    </#list>	          
			    </tbody>
			</table>		
		</div><!--/span-->
	</div><!--/row-->
