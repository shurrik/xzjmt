$(function(){
	$('.menu a').click(function(){
		$('#main').load($(this).attr('href'));
		$('.menu').removeClass('active');
		$(this).parent().addClass("active");
		$('#tab').children().eq(0).find('a').text($(this).text());
		showTab(0);
		return false;
	});
	
	$('.nav-pills > li > a').live('click',function(){
		addTab($(this).text(),$(this).attr('href'));
		return false;
	});
	
	
	$('.nav li').click(function(){
		
		var index = $(this).index();
		var pane = $('#pane').children().eq(index);
		pane.addClass('active');
		pane.siblings().removeClass('active');
	});
	
	$('a.cancel').live('click',function(){
		removeTab();
		showTab(0);
	});
	
	$('a.btn-primary').live('click',function(){
		//$('#pageForm').submit();

		$.ajax({
			cache: true,
			type: "POST",
			//url:ajaxCallUrl,
			url:$('#pageForm').attr('action'),
			data:$('#pageForm').serialize(),// 你的formid
			async: false,
			error: function(request) {
				alert("Connection error");
			},
			success: function(data) {
				showMessage(data);
				removeTab();
				$('.active.menu a').click();
			}
		});
		
	});
	
	$('.table tr').live('click',function(){
		alert(123);
		$(this).addClass('selected');
	});		
	
	
});

function addTab(tabName,href)
{
	if($('#tab2').length==0)
	{
		$('#tab').append('<li id="tab2" class=""><a id="newTab" href="#2" data-toggle="tab">'+tabName+'</a></li>');
		$('#pane').append('<div class="tab-pane" id="main2"></div>');
	}
	else
	{
		$('#newTab').text(tabName);
	}
	$('#main2').load(href);
	showTab(1);
}

function showTab(index)
{
	var tab = $('.nav-tabs').children().eq(index);
	tab.addClass('active');
	tab.siblings().removeClass('active');
	var pane = $('#pane').children().eq(index);
	pane.addClass('active');
	pane.siblings().removeClass('active');
}

function removeTab()
{
	$('#tab2').remove();
	$('#main2').remove();
}

function showMessage(message)
{
	$('#model_body').text(message);
	$('#myModal').modal({
	    backdrop:false,
	    keyboard:true,
	    show:true
	});
	setTimeout( function(){ $('#myModal').modal('hide');}, 2000);
}