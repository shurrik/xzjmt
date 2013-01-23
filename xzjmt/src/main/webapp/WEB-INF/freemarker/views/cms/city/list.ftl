	<div class="subnav">
	    <ul class="nav nav-pills">
	      <li class=""><a href="cms/city/add">新增</a></li>
	      <li class=""><a href="cms/city/edit">编辑</a></li>
	      <li class=""><a href="cms/city/delete">删除</a></li>
	    </ul>
  	</div>

	<div class="row-fluid">
		<div class="span10">
			<table class="table table-striped">
			    <thead>
			      <tr>
			        <th>#</th>
			        <th>城市Id</th>
			        <th>城市名称</th>
			      </tr>
			    </thead>
			    <tbody>
				<#list pageCtx.itemList as city>
			      <tr>
			        <td>${city_index+1}</td>
			        <td>${(city.cityId )!}</td>
			        <td>${(city.cityName)!}</td>
			      </tr>
			    </#list>	          
			    </tbody>
			</table>		
		</div><!--/span-->
	</div><!--/row-->
