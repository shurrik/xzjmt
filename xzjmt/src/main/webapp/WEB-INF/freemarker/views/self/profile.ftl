<@override name="csslib">
	<@super/>
	<link href="${cssroot}/jquery.Jcrop.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${cssroot}/uploadify.css" charset="utf-8" />	
</@override>
<@override name="jslib">
	<@super/>
	<script type="text/javascript" src="${jsroot}/jquery.Jcrop.min.js"></script>
  	<script type="text/javascript" src="${jsroot}/jquery.uploadify.min.js"></script>	
</@override>
<@override name="body">
<form id="pageForm" action="${wwwroot}/self/save" class="form-horizontal" method="post">
	<input type="hidden" name="userId" value="${(userId)!}">
  <fieldset>
		<legend>个人设置</legend>

		<div class="row-fluid">
			<div class="span1"></div>
			<div class="span4">
			<input id="file_upload" name="file_upload" type="file" multiple="false">
			</div>
		</div>
			
		<div class="row-fluid">
			<input type="hidden" id="x">
			<input type="hidden" id="y">
			<input type="hidden" id="w">
			<input type="hidden" id="h">
			<input type="hidden" id="srcPath" value="<#if user.avatar??>${(user.avatar)!}<#else><@p.avatarPath/></#if>">			
			
			<div class="span1">
			</div>
			<#--
			<div class="span4">
				<img src="${imgroot}/default_avatar.jpg" id="target"/>
			</div>
			-->
			<div class="span4">
				<div id="avatar_upload">
					<img src="<@p.avatar imgUrl=(user.avatar)!/>" id="target"/>					
				</div>			
			</div>			
			<div class="span2">
				<div class="row-fluid">
					<div style="width:100px;height:100px;overflow:hidden;float:right;">
		            	<img src="<@p.avatar imgUrl=(user.avatar)!/>" id="preview"  class="jcrop-preview" />
		          	</div>
          		</div>
          		<div class="blank"></div>
          		<div class="blank"></div>          		
				<div class="row-fluid">
					<a class="btn btn-success pull-right" href="javascript:saveAvatar();" title="确定">确定</a>
          		</div>				
			</div>
		</div>		
		<div class="blank"></div>
		<div class="control-group">
		  <label class="control-label" for="nickName">手机：</label>
		  <div class="controls">
		    <input type="text" minlength="11" maxlength="11" class="input digits" id="mobile" name="mobile" value="${(user.mobile)!}">
		  </div>
		</div>
		<div class="control-group">
		  <label class="control-label" for="qq">qq：</label>
		  <div class="controls">
		    <input type="text" class="text" id="qq" name="qq" value="${(user.qq)!}">
		  </div>
		</div>	
		<div class="control-group">
		    <label class="control-label" for="cityId">所在城市</label>
            <div class="controls">
              <select id="cityId" name="cityId">
                <option value="">请选择</option>
                <#list citys as city>
                	<option value="${(city.cityId)!}" <#if user.cityId??&user.cityId=city.cityId>selected</#if>>${(city.cityName)!}</option>                	
                </#list>
              </select>
            </div>
		</div>
		<div class="control-group">
        	<label class="control-label" for="desc">个人介绍</label>
            <div class="controls">
              <textarea class="input-xlarge" id="intro" name="intro" rows="7">${(user.intro)!}</textarea>
            </div>
        </div>					
	
		<div class="form-actions">
            <a class="btn btn-primary">保存</a>
        </div>
        
  </fieldset>
</form>
<script>
jQuery(function($){

  // Create variables (in this scope) to hold the API and image size
  var jcrop_api, boundx, boundy;
  
  $('#target').Jcrop({
    onChange: updatePreview,
    //onSelect: updatePreview,
    onSelect: sl,
    aspectRatio: 1
  },function(){
    // Use the API to get the real image size
    var bounds = this.getBounds();
    boundx = bounds[0];
    boundy = bounds[1];
    // Store the API in the jcrop_api variable
    jcrop_api = this;
  });

  function updatePreview(c)
  {
    if (parseInt(c.w) > 0)
    {
      var rx = 100 / c.w;
      var ry = 100 / c.h;

      $('#preview').css({
        width: Math.round(rx * boundx) + 'px',
        height: Math.round(ry * boundy) + 'px',
        marginLeft: '-' + Math.round(rx * c.x) + 'px',
        marginTop: '-' + Math.round(ry * c.y) + 'px'
      });
    }
  };
  
  function sl(c)
  {
  	var x = Math.round(c.x);
  	var y = Math.round(c.y);
  	var w = Math.round(c.w);
  	var h = Math.round(c.h);
  	$('#x').val(x);
  	$('#y').val(y);
  	$('#w').val(w);
  	$('#h').val(h);
  }
  


	$('.btn-primary').click(function(){
		$('#pageForm').submit();
	});
	
	$('#file_upload').uploadify({
		'formData'     : {
			'thumbnail[0].width':300,
			'thumbnail[0].height':0,			
			//'thumbnail[0].height':180,
			'folder' : '/img/tmp'
		},			
		'swf'      : '${cssroot}/uploadify.swf',
		'uploader' : '${wwwroot}/upload',
		'onUploadSuccess' : function(file, data, response) {
			var json = eval("("+data+")");
			//uploadAvatar(json.relativeUrl);
			uploadAvatar(json.thumbnail[0].relativeUrl);
		}
	});	
	
	
	function Jc(element)
	{
	  $(element).Jcrop({
	    onChange: updatePreview,
	    //onSelect: updatePreview,
	    onSelect: sl,
	    aspectRatio: 1
	  },function(){
	    // Use the API to get the real image size
	    var bounds = this.getBounds();
	    boundx = bounds[0];
	    boundy = bounds[1];
	    // Store the API in the jcrop_api variable
	    jcrop_api = this;
	  });		
	}
	
	function uploadAvatar(picUrl)
	{
		$('#avatar_upload').empty();
		//$('#target').attr("src","${staticroot}/"+picUrl);	
		var str = "<img id='target' src='${staticroot}/"+picUrl+"'/>";
		$('#avatar_upload').append(str);
		$('#preview').attr("src","${staticroot}/"+picUrl);
		$('#srcPath').val(picUrl);
		Jc($('#target'));
	}	
	
	$("#pageForm").validate();	
});


    
  function saveAvatar()
  {
  	var x = $('#x').val();
  	var y = $('#y').val();
  	var w = $('#w').val();
  	var h = $('#h').val();
  	var srcPath = $('#srcPath').val();
  	$.ajax({  
		type: "POST",  
		url: "${wwwroot}/self/avatar/generate",
		data:"x="+x+"&y="+y+"&width="+w+"&height="+h+"&srcPath="+srcPath,
		dataType : "text",  
		timeout : 5000,
		success: function(data) {
			showAlert('保存头像成功');
		},
		error: function(){
			showAlert('连接超时');
		}
	});
  }    

</script>      				
</@override>
<@extends name="../include/base.ftl" />