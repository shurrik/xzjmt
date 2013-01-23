<#macro home>
xzjmt.me
</#macro>
<#macro webName>
闲置姐妹淘
</#macro>


<#macro uploadImg id name folder="" scriptData="" attributes="" fileExt="*.jpg;*.jpeg;*.gif;*.png" fileDesc="图像文件(*.jpg;*.jpeg;*.gif;*.png)" fileQueue="" onComplete="" onAllComplete="">
		<input id="${id}" type="file" name="${name}" ${attributes}
			uploader="${cssroot}/uploadify.swf"
			cancelImg="${imgroot}/uploadify-cancel.png" 
			script="${wwwroot}/upload" 
			wmode="transparent"
			folder="${folder}"								
			fileQueue="${fileQueue}"
			scriptData="${scriptData}"
			onComplete="${onComplete}"
			onAllComplete="${onAllComplete}"
			fileExt="${fileExt}"
			fileDesc="${fileDesc}" />
</#macro>

<#macro pagination action total=pageCtx.pageBean.totalCount currentPage=pageCtx.pageBean.currentPage pageNum=1 pageSize=20 >
<#if (total>pageSize)>
	<#-- 最大页距 -->
	<#assign maxPageSpace = 9>
	<#-- 分页总数 -->
	<#if (total>pageSize&&total%pageSize>0)>
		<#assign pageCount=(total/pageSize)?int+1>
	<#else>	
		<#assign pageCount=total/pageSize?int>
	</#if>
	<#-- 显示首页 -->
	<#if ((currentPage-maxPageSpace)>1)>
		<#assign showHeadPage = (currentPage-maxPageSpace)>
	<#else>
		<#assign showHeadPage = 1>
	</#if>
	<#-- 显示尾页 -->
	<#if ((currentPage+maxPageSpace)<pageCount)>
		<#assign showFootPage = (currentPage+maxPageSpace)>
	<#else>
		<#assign showFootPage = pageCount>
	</#if>
	<#if (total>pageSize)>
	<div class="pagination pagination-centered">
	  <ul>	
		<#if (currentPage>1)>
			<li><a href="javascript:pageFormSubmit(1)" class="first_page nextPageIcoA-b">首 页</a></li>
		<#else>
			<li><span class="disabled first_page nextPageIcoA-a">首 页</span></li>
		</#if>
		<#if (showHeadPage>1)>
		<li><span class="break">...</span></li>
		</#if>
		<#if (currentPage>1)>
			<li><a href="javascript:pageFormSubmit(${currentPage-1})" class="prev_page prev_page_a nextPageIcoA-d">上一页</a></li>
		<#else>
			<li><span class="disabled prev_page prev_page_a nextPageIcoA-c">上一页</span></li>
		</#if>
		<#list showHeadPage..showFootPage as i>
			<#if i==currentPage>
				<li><span class="active">${i}</span></li>
			<#else>
				<li><a href="javascript:pageFormSubmit(${i})" class="num_page">${i}</a></li>
			</#if>
		</#list>
		<#if (currentPage<pageCount)>
			<li><a href="javascript:pageFormSubmit(${currentPage+1})" class="next_page next_page_a nextPageIcoA-e">下一页</a></li>
		<#else>
			<li><span class="disabled next_page next_page_a nextPageIcoA-f">下一页</span></li>
		</#if>
		<#if (showFootPage<pageCount)>
		<li><span class="break">...</span></li>
		</#if>
		<#if (currentPage<pageCount)>
			<li><a href="javascript:pageFormSubmit(${pageCount})" class="last_page nextPageIcoA-g">末 页</a></li>
		<#else>
			<li><span class="disabled last_page nextPageIcoA-h">末 页</span></li>
		</#if>
		
		<form id="pageForm" action="${action}" method="get">
		<input type="hidden" name="pageNum" id="pageNum" value="${pageNum}">
		</form>
	  </ul>		
	</div>
	</#if>
	<script>
	function pageFormSubmit(i)
	{
		$('#pageNum').val(i);
		$('#pageForm').submit();
	}
	</script>
</#if>	
</#macro>


<#macro subStr content length dot=true>
<#compress>
<#if content??>
	<#if (content?length<=length)>
		${content!}
	<#else>
		<#assign output = content[0..length-1]! />
		${output!}<#if dot>...</#if>
	</#if>
</#if>
</#compress>
</#macro>


<#macro calculate_time input_time>
	<@calculateTime timems = input_time><span>${timelabel!}</span></@calculateTime>
</#macro>

<#macro avatar imgUrl>
	<#if imgUrl!=''>${staticroot}${imgUrl!}<#else>${imgroot}/default_avatar.jpg</#if>
</#macro>

F:\xzjmt\img\avatar

<#macro avatarPath>/img/avatar/default_avatar.jpg</#macro>

<#macro userLink userId>${wwwroot}/user/${userId!}</#macro>

<#macro city_name cityId>
	<@cityName cityId = cityId!>${cityName!}</@cityName>
</#macro>


