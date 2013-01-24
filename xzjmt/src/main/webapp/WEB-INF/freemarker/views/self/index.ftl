<@override name="body">
<div class="blank"></div>
<section>
  <div class="row">
    <div class="span8 <#if (pageCtx.itemList?size>0)>well</#if>">
	<#list pageCtx.itemList as item>
		<#if item_index==0||(item_index)%3==0>
			<div class="row-fluid">			
		</#if>	
			<div class="span4"><a href="${wwwroot}/item/${(item.itemId)!}"><img src="${staticroot}/${(item.picCover)!}"></a></div>
		<#if !item_has_next||(item_index+1)%3==0>			
			</div>
			<div class="blank"></div>
		</#if>			
	</#list>		
		
		<@p.pagination action=wwwroot+"/self" pageSize=pageCtx.pageBean.pageSize />	
    </div>
    <div class="span3 well">
		<div class="row-fluid ">
	      <div class="span6"><img src="<@p.avatar imgUrl=(user.avatar)!/>"/></div>
	      <div class="span1"></div>
	      <div class="span5">
	      	<div class="row-fluid ">
	      		<div class="span10">
	      			<h5>${(user.nickName)!}</h5>
	      		</div>
	      	</div>
	      	<div class="row-fluid ">
	      		<div class="span10">
	      			<i class="icon-map-marker"></i><@p.city_name cityId=(user.cityId)!/>
	      		</div>
	      	</div>	      	
	      </div>
	    </div>
	    <div class="blank"></div>
		<div class="row-fluid ">
	      	手机：${(user.mobile)!}
	    </div>	    
	    <div class="blank"></div>	    
		<div class="row-fluid ">
	      	qq：${(user.qq)!}
	    </div>
	    <div class="blank"></div>	       
		<div class="row-fluid ">
	      <div class="span6"><a href="${wwwroot}/self/message/list"><i class="icon-envelope"></i>我的消息</a></div>
	      <div class="span6"><a href="${wwwroot}/self/profile"><i class="icon-cog"></i>个人设置</a></div>
	    </div>
		<div class="row-fluid ">
	      <div class="span6"><a href="${wwwroot}/self/item/new"><i class="icon-file"></i>发布闲置</a></div>
	      <div class="span6"><a href="${wwwroot}/self/collection"><i class="icon-heart"></i>我的收藏</a></div>
	    </div>	 
		<div class="row-fluid ">
	      <div class="span12">${(user.intro)!}</div>
	    </div>	    	    	    
    </div>    
  </div>

</section>

</@override>
<@extends name="../include/base.ftl" />