<@override name="body">
<div class="blank"></div>
<section>
  <div class="row">
    <div class="span8 well bottom_20">
		<div class="row-fluid">
			<div class="span10">
				${(item.name)!}
			</div>
			
			<div id="span_collect" class="span2" <#if collected>style="display:none"</#if>>
				<a class="btn btn-success" href="javascript:collect(${(item.itemId)!})" title="收藏"><i class="icon-white icon-heart"></i> 收藏</a>
			</div>
			<div id="span_cancel_collect" class="span2" <#if !collected>style="display:none"</#if>>
				<a class="btn btn-danger" href="javascript:collect(${(item.itemId)!})" title="取消收藏"><i class="icon-white icon-heart"></i> 已收藏</a>
			</div>						
		</div>    
		<div class="row-fluid">
			<div class="span12">
				介绍：
				${(item.desc)!}
			</div>
		</div>
		<div class="row-fluid">
			<div class="span12">
				<@p.calculate_time input_time=item.createDate/>发布			
			</div>
		</div>	
		<#list itemPics as itemPic>
			<div class="row-fluid ">
				<div class="span12"><img src="${staticroot}/${(itemPic.picUrl)!}"/></div>
			</div>			
		</#list>	    
    </div>
    <div class="span3 well">
		<div class="row-fluid ">
	      <div class="span6"><a href="<@p.userLink userId=(user.userId)!/>"><img src="<@p.avatar imgUrl=(user.avatar)!/>"/></a></div>
	      <div class="span1"></div>
	      <div class="span5">
	      	<div class="row-fluid ">
	      		<div class="span10">
	      			<h5><a href="<@p.userLink userId=(user.userId)!/>">${(user.nickName)!}</a></h5>
	      		</div>
	      	</div>
	      	<div class="row-fluid ">
	      		<div class="span10">
	      			<@p.city_name cityId=(user.cityId)!/>
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
	    <#if !self>   
		<div class="row-fluid ">
	      <div class="span6"><i class="icon-envelope"></i><a href="${wwwroot}/self/message/write/${(user.userId)!}">发送私信</a></div>
	      <div class="span6">
	      	<a <#if followed>style="display:none"</#if> id="a_follow" href="javascript:followUser(${(user.userId)!})"><i id="icon_follow" class="icon-white icon-heart"></i>关注对方</a>
			<ul id="ul_followed" class="nav follow" <#if !followed>style="display:none"</#if>>
            	<li class="dropdown">
              		<a class="dropdown-toggle" data-toggle="dropdown" href="#"><i class="icon-ok"></i> 已关注<b class="caret"></b></a>
		              <ul id="menu3" class="dropdown-menu">
						<li><a href="javascript:followUser(${(user.userId)!})">取消关注</a></li>
		              </ul>
            	</li>
          	</ul>	      	
	      </div>
	    </div>
		<div class="row-fluid ">
	      <div class="span12">${(user.intro)!}</div>
	    </div>
	    </#if>	    	    	    
    </div>    
  </div>

</section>

<script>
function followUser(userId)
{
	$.ajax({  
		type: "POST",  
		url: "${wwwroot}/self/follow/"+userId+"?random="+Math.random(),
		dataType : "text",  
		timeout : 5000,
		success: function(data) {
			if(data=='success')
			{
				if($('#ul_followed').is(":hidden"))
				{
					$('#ul_followed').show();
					$('#a_follow').hide();
					showAlert('关注成功');
				}
				else
				{
					$('#ul_followed').hide();
					$('#a_follow').show();
					showAlert('取消成功');

				}
			}
		},
		error: function(){
			showAlert('连接超时');
		}
	});
}

function collect(itemId)
{
	$.ajax({  
		type: "POST",  
		url: "${wwwroot}/self/collect/"+itemId+"?random="+Math.random(),
		dataType : "text",  
		timeout : 5000,
		success: function(data) {
			if(data=='success')
			{
				if($('#span_cancel_collect').is(":hidden"))
				{
					$('#span_cancel_collect').show();
					$('#span_collect').hide();
					showAlert('收藏成功');
				}
				else
				{
					$('#span_cancel_collect').hide();
					$('#span_collect').show();					
					showAlert('取消成功');
				}
			}
		},
		error: function(){
			showAlert('连接超时');
		}
	});
}
</script>
</@override>
<@extends name="../include/base.ftl" />