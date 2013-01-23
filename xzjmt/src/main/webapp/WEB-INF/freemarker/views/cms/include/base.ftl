<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>闲置姐妹淘后台</title>

    <!-- Le styles -->
    <link href="${cssroot}/bootstrap.css" rel="stylesheet">
    <link href="${cssroot}/cms.css" rel="stylesheet">    
    <style type="text/css">
      body {
        padding-top: 60px;
        padding-bottom: 40px;
      }
      .sidebar-nav {
        padding: 9px 0;
      }
    </style>
	<script type="text/javascript" src="${jsroot}/jquery-1.7.min.js"></script>
	<script type="text/javascript" src="${jsroot}/bootstrap.min.js"></script>
	<script type="text/javascript" src="${jsroot}/cms.js"></script>
	
  </head>

  <body>

    <div class="navbar navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container-fluid">
          <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </a>
          <a class="brand" href="#">闲置姐妹淘后台</a>
          <div class="nav-collapse">
            <ul class="nav">
              <li class="active"><a href="#">首页</a></li>
            </ul>
            <p class="navbar-text pull-right">
            	<a href="#">shurrik</a>&nbsp;<a href="${wwwroot}" target="_blank">去前台</a>
            </p>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>

    <div class="container-fluid">
		<#include "menu.ftl">


		<div class="span12">
			<div class="tabbable" style="margin-bottom: 9px;">
			        <ul id="tab" class="nav nav-tabs">
			          <li class="active"><a href="mainTab" data-toggle="tab">欢迎</a></li>
			          
			          <#--
						<li class=""><a href="#2" data-toggle="tab">fdfd</a></li>			          
			          <li class=""><a href="#3" data-toggle="tab">段 3</a></li>
			          -->
			        </ul>
			        <div id="pane" class="tab-content">
	
			          <div class="tab-pane active" id="main">
			          	<@block name="body">
						</@block>
			          </div>
			          
			          <#--

			          <div class="tab-pane" id="3">
			            <p>这里是段3</p>
			          </div>
			          -->
			        </div>
			</div>

		</div>
		
        <div id="myModal" class="modal hide fade" style="display: none;">
		  <div class="modal-header">
		    <a class="close" data-dismiss="modal">×</a>
		    <h3>操作提醒</h3>
		  </div>
		  <div class="modal-body">
		    <p id="model_body">对话框内容</p>
		  </div>
		  <div class="modal-footer">
		    <a href="#" class="btn" data-dismiss="modal">关闭</a>
		  </div>
		</div>		
		
    </div><!--/.fluid-container-->

  </body>
</html>

