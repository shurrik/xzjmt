<@override name="body">
<div class="blank"></div>
<section>
  <div class="row">
    <div class="span2">
    	  	热门城市:
    </div>  
  </div>
	<#list citys as city>
		<#if city_index==0||(city_index)%6==0>
			<div class="row">
		</#if>
			<div class="span2">
				<a href="${wwwroot}/item/city/${(city.cityId)!}">${(city.cityName)!}</a>
			</div>
		<#if !city_has_next||(city_index+1)%6==0>			
			</div>
		</#if>
	</#list>  
</section>  
</@override>
<@extends name="include/base.ftl" />
