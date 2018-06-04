$(document).ready(function(){
	$("#forgotpass").click(function(){
		$("#img2").hide();
		$(".userinfo").hide();
		$("#passwordreset").show();
	});
		
	
	$("#cancel4").click(function(){
		$("#passwordreset").hide();
		$(".userinfo").show();
		$("#img2").hide();
	});
});

function validateNewPassword(){
	var regpassword= document.getElementById("newpassval").value;
	
	if(regpassword.length < 4 || regpassword.length =="")
	{
	document.getElementById("newpass_msg").innerHTML ="password should contains atleast 4 characters*";
	return false;
	}
	else
	{
		document.getElementById("newpass_msg").innerHTML="";
		return true;
	}
}
function validateConfNewPassword(){
	var confpassword= document.getElementById("newconfpassval").value;
	var regpassword= document.getElementById("newpassval").value;
	if(confpassword!=regpassword)
	{
	document.getElementById("confnewpass_msg").innerHTML ="password doesnot match*";
	return false;
	}
	else
	{
		document.getElementById("confnewpass_msg").innerHTML="";
		return true;
	}
}
var savepassword = function(){
	var emailId = $("#emailval").val();
	var password = $("#newpassval").val();
	var JSONObject = {};
	JSONObject.emailId = emailId;
	JSONObject.password = password;

	runAjax("requestJSON=" + JSON.stringify(JSONObject), FORGOTPASSWORD_URL, forgotPasswordResponse, true, METHOD_POST, "", "", true);
	

}
var forgotPasswordResponse = function(xhr,textStatus){
	if(xhr.status == "200"){
		var response = JSON.parse(decodeURIComponent(xhr.responseText.replace(/\+/g,'%20')));
		_showToast('success',"password updated Successfully!");
		$(".userinfo").show();
		$("#passwordreset").hide();
		$("#img2").hide();

	}else if (xhr.status == "500") {
		_showToast('failure',xhr.responseText);
	} else {
		_showToast('failure',xhr.responseText);
	}

}