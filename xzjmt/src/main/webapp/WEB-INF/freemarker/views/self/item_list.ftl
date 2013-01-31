<@override name="body">
	<div class="blank"></div>
	<ul class="breadcrumb">
	  <li>
	    <a href="${wwwroot}/item">我的闲置</a><span class="divider">/</span>
	  </li>
	</ul>  
	<#list pageCtx.itemList as item>
		<#if item_index==0||(item_index)%2==0>
			<div class="row-fluid item-list">
		</#if>	
		<div class="span4 e">
			
			<div class="hover bar">
				<div class="title"><a href="${wwwroot}/item/${(item.itemId)!}">${(item.name)!}</a></div>
				<div class="info">
				<a href="${wwwroot}/item/${(item.itemId)!}">${(item.nickName)!}发表于<@p.calculate_time input_time=item.createDate/></a>
				</div>
			</div>
			<a class='close' href="${wwwroot}/self/item/delete/${(item.itemId)!}">&times;</a>
			<a href="${wwwroot}/item/${(item.itemId)!}"><img src="${staticroot}/${(item.picCover)!}"></a>
		</div>
		<div class="span2">
		</div>
		<#if !item_has_next||(item_index+1)%2==0>			
			</div>
			<div class="blank"></div>
		</#if>			
	</#list>	
	<@p.pagination action=wwwroot+"/item" pageSize=pageCtx.pageBean.pageSize />

<script>
</script>	
</@override>
<@extends name="../include/base.ftl" />