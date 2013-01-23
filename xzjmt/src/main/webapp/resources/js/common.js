
function showAlert(msg,timeout)
{
	if(timeout==undefined)
	{
		timeout=1000;
	}
	$('#alert strong').text(msg);
	$('#alert').show();
	setTimeout(closeAlert,timeout);
}

function closeAlert()
{
	$('#alert').fadeOut(1000);
}