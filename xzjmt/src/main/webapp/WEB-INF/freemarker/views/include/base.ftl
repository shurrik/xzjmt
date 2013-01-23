<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<@block name="seo_self">
    <title>闲置姐妹淘</title>
	<meta name="keywords" content="闲置、二手、女性、交换" />
    <meta name="description" content="女性二手闲置交换信息发布平台" />
    </@block>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <meta http-equiv="content-language" content="zh-cn" />
    <meta name="robots" content="all" />
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" /> 
<#-- 样式库 -->
<@block name="csslib">  
   	<link href="${cssroot}/bootstrap.css" rel="stylesheet">
	<link href="${cssroot}/base.css" rel="stylesheet">
</@block>

<#-- 脚本库 -->
<@block name="jslib">  
	<script type="text/javascript" src="${jsroot}/jquery-1.7.min.js"></script>
	<script type="text/javascript" src="${jsroot}/bootstrap.min.js"></script>
	<script type="text/javascript" src="${jsroot}/common.js"></script>	
</@block>
</head>
  <body data-spy="scroll" data-target=".subnav" data-offset="50" screen_capture_injected="true">


  <!-- Navbar
    ================================================== -->
    <div class="navbar navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </a>
          <a class="brand" href="${wwwroot}">闲置姐妹淘</a>
          <div class="nav-collapse">
			<#include "nav.ftl"/>
            
			<@loginUser>
			<#if loginUsr??>
		    	<ul class="nav pull-right">
		          <li class="">
		          <#--
		          	<img src="${imgroot}/avatar.jpg" class=""/>
		          	-->
		         	<img src="<@p.avatar imgUrl=(loginUsr.avatar)!/>" class="nav_avatar"/>
		          </li>	    	
		          <li class="">
		            <a href="${wwwroot}/self">${loginUsr.nickName}</a>
		          </li>
		          <li class="">
		            <a href="${wwwroot}/logout">退出</a>
		          </li>		          
		        </ul> 				
			<#else>
				<form id="loginForm" action="${wwwroot}/authenticate" class="navbar-form pull-right" method="post">
				  账号：<input name="username" type="text" class="span2" placeholder="邮箱/昵称">
				  密码：<input name="password" type="password" class="span2">
					<a href="javascript:$('#loginForm').submit();" class="btn" >登录</a>
					<a href="${wwwroot}/reg" class="btn">注册</a>
				</form>
			</#if>
			</@loginUser>
           
          </div>
        </div>
      </div>
    </div>

<br/>
<br/>
<br/>


    <div class="container">
		<div id="alert" class="alert alert-success" style="display:none">
        	<a class="close" href="javascript:closeAlert();">×</a>
        	<strong>保存成功</strong>
      	</div>
	<#-- 页面主体 -->
	<@block name="body">
	</@block>
<#include "footer.ftl" />