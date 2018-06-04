$(document).ready(function(){
	$("#logoutbtn").click(function(){
		
		$("#register").show();
		$("#login").show();
		$("#img2").show();
		$(".profile").hide();
		$("#userlogo").hide();
		$("#getlist").hide();
		$(".userlist").hide();	

	});
});
	
var logout=function(){
	  
			runAjax("", LOGOUT_URL, logoutResponse, true, METHOD_GET, "", "", true);
 
     }
var logoutResponse = function(xhr,textStatus){
		_showToast('success',"logged out Successfully!");
		location.reload();
	
}