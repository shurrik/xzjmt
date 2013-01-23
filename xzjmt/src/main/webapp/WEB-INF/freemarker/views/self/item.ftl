<@override name="csslib">
	<@super />
      <link rel="stylesheet" type="text/css" href="${cssroot}/uploadify.css" charset="utf-8" />
</@override>
<@override name="jslib">
	<@super />
	
</@override>	
<@override name="body">
<#--
<div class="blank"></div>
<section>
  <div class="row">
    <div class="span8 well">
		<div class="row-fluid">
			<div class="span12"><a href="#">我的闲置</a></div>
		</div>
    </div>
    <div class="span3 well">
		<div class="row-fluid ">
	      <div class="span6"><img src="${imgroot}/avatar_large.jpg"/></div>
	      <div class="span1"></div>
	      <div class="span5">
	      	<div class="row-fluid ">
	      		<div class="span5">
	      			<h5>shurrik</h5>
	      		</div>
	      	</div>
	      	<div class="row-fluid ">
	      		<div class="span5">
	      			<img src="${imgroot}/red-heart.gif"/>
	      		</div>
	      	</div>	      	
	      </div>
	    </div>
	    <div class="blank">
	    </div>
		<div class="row-fluid ">
	      <div class="span6"><a href="#"><i class="icon-envelope"></i>我的消息</a></div>
	      <div class="span6"><a href="#"><i class="icon-cog"></i>我的设置</a></div>
	    </div>
		<div class="row-fluid ">
	      <div class="span6"><a href="#"><i class="icon-file"></i>发布闲置</a></div>
	      <div class="span6"><a href="#"><i class="icon-heart"></i>我的收藏</a></div>
	    </div>	 
		<div class="row-fluid ">
	      <div class="span12">一件东西代表某一特定的产品或服务，它可能是食品、电器、软件、美容化妆用品、汽车等等。豆瓣将会帮助你发现喜欢的东西，并结识同好。</div>
	    </div>	    	    	    
    </div>    
  </div>

</section>
-->

<form id="itemForm" action="${wwwroot}/self/item/add" class="form-horizontal">
	<input name="picCover" id="picCover" type="hidden">
  <fieldset>
		<legend></legend>
		<div class="control-group">
		  <label class="control-label" for="name">名称</label>
		  <div class="controls">
		    <input type="text" class="input-xlarge" id="name" name="name">
		  </div>
		</div>
		
		<div class="control-group">
        	<label class="control-label" for="desc">描述</label>
            <div class="controls">
              <textarea class="input-xlarge" id="desc" name="desc" rows="7"></textarea>
            </div>
        </div>
        
		<div class="control-group">
		    <label class="control-label" for="cityId">所在城市</label>
            <div class="controls">
              <select id="cityId" name="cityId">
                <option>请选择</option>
                <#list citys as city>
                	<option value="${(city.cityId)!}" <#if user.cityId??&user.cityId==city.cityId>selected</#if>>${(city.cityName)!}</option>                	
                </#list>
              </select>
            </div>
		</div>
		<div class="control-group">
		    <label class="control-label" for="catId">分类</label>
            <div class="controls">
              <select id="catId" name="catId">
                <option>请选择</option>
                <#list categorys as category>
                	<option value="${(category.catId)!}">${(category.catName)!}</option>                	
                </#list>
              </select>
            </div>
		</div>		        
		<div class="control-group">
        	<label class="control-label" for="desc">上传照片</label>
            <div class="controls">
				<div id="queue"></div>
				<input id="file_upload" name="file_upload" type="file" multiple="true">
				<div id="fileQueue">
				</div>
            </div>
        </div>        	

	
		<div class="form-actions">
            <a class="btn btn-primary" href="javascript:void(0);" onclick="javascript:$('#itemForm').submit();">保存</a>
        </div>
        
  </fieldset>
</form>
<script>
$(function(){
	$('#file_upload').uploadify({
		'formData'     : {
			'thumbnail[0].width':220,
			'thumbnail[0].height':180,
			'folder' : '/img/item'
			
		},			
		'swf'      : '${cssroot}/uploadify.swf',
		'uploader' : '${wwwroot}/upload',
		'onUploadSuccess' : function(file, data, response) {
			var json = eval("("+data+")");
			if($('#picCover').val()=='')
			{
				$('#picCover').val(json.thumbnail[0].relativeUrl);
			}
			appendPic(json.relativeUrl,json.thumbnail[0].relativeUrl)
		}
	});
	$('.span4 .close').live('click',function(){
		$(this).parent().parent().fadeOut('fast');
	});	
});

function appendPic(picUrl,picUrlSmall)
{
	var str = 
			"<div class='row'>"+
				"<div class='span4'>"+
					"<input type='hidden' name='picUrl' value='"+picUrl+"'>"+
					"<input type='hidden' name='picUrlSmall' value='"+picUrlSmall+"'>"+
					"<a class='close'>&times;</a>"+
						"<img src='${staticroot}/"+picUrlSmall+"'/>"+
				"</div>"+
			"</div>";
	$("#fileQueue").append(str);			
}
</script>
</@override>
<@extends name="../include/base.ftl" />