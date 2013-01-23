<@override name="body">


  <div class="page-header">
    <h1><a href="${wwwroot}/item">${(cityName)!}</a> <small><a href="${wwwroot}/changecity">更多城市</a></small></h1>
  </div>

<!-- Button Groups
================================================== -->
<section id="buttonGroups">
  <div class="row">
    <div class="span4">
		<a href="${wwwroot}/item/1"><img src="${imgroot}/home_1.jpg"/></a>
    </div>
    <div class="span4">
		<a href="${wwwroot}/item/1"><img src="${imgroot}/home_1.jpg"/></a>
    </div>
    <div class="span4">
		<a href="${wwwroot}/item/1"><img src="${imgroot}/home_1.jpg"/></a>
    </div>        
  </div>
</section>

</@override>
<@extends name="include/base.ftl" />
