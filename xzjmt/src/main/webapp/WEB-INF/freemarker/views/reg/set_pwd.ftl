
<#if reg_active_msg?? && reg_active_msg!=''>
	<div class="step5">
		<h3>注册失败</h3>
		<p>${reg_active_msg!}</p>
	</div>
	<div class="text_5">
		重新<a class="c_00a" href="${wwwroot}/member/reg">注册</a> 或 返回<a class="c_00a" href="${wwwroot}">首页</a><br />
		如果有疑问请联系客服：400-6885-365
	</div>
<#else>
	
	<div class="succeed">
		<h3>账号激活成功</h3>
		<p>请设置完成以下信息即可体验丰富精彩的考试点之旅了!</p>
	</div>

	<div class="set_pas">
	<form  id="signupform" action="${wwwroot}/reg/setting" method="post">
		<input name="mid" type="hidden" value="${mgid!}" />
		<input name="mail" type="hidden" value="${account_email!}" />
		<dl>
			<dt>昵　　称：</dt>
			<dd>
				<input type="text" name="nkname" value="${account_nkname!}" maxlength="16" />
				<#if reg_save_setting_msg??>
                <div class="set_tip"><label class="warn" for="nkname" generated="true">${reg_save_setting_msg!}</label></div>
                <#else>
                <div class="set_tip"><label class="info">3-15位字符（中英文、数字和"_"）</label></div>
                </#if>
			</dd>
			<div class="clear"></div>
		</dl>
		<dl>
			<dt>登录密码：</dt>
			<dd>
				<input type="password" name="passwd" id="passwd" autocomplete="off" maxlength="18" />
				<div class="set_tip"><label class="info">由6-16位字符组成，区分大小写</label></div>
			</dd>
		    <div class="clear"></div>
		</dl>
		<dl>
			<dt>确认密码：</dt>
			<dd>
				<input type="password" name="passwd2" id="passwd2" autocomplete="off" maxlength="18" />
				<div class="set_tip"><label class="info">请再次输入您设定的密码</label></div>
			</dd>
			<div class="clear"></div>
		</dl>
		<input type="submit" class="btn_4" value="" />
	</form>  
	<br class="clear" />   
	</div>
</#if>

