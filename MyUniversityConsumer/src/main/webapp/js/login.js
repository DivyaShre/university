var login = function(){
	var emailId = $("#emailval").val();
	var password = $("#passval").val();
	
	var JSONObject = {};
	JSONObject.emailId = emailId;
	JSONObject.password = password;
	runAjax("requestJSON=" + JSON.stringify(JSONObject), LOGIN_URL, loginResponse, true, METHOD_POST, "", "", true);
	
}

var loginResponse = function(xhr,textStatus){
	if(xhr.status == "200"){
		var response = JSON.parse(decodeURIComponent(xhr.responseText.replace(/\+/g,'%20')));
		_showToast('success',"Logged in Successfully!");
		$("#register").hide();
		$("#login").hide();
		$(".userinfo").hide();
		$("#getlist").show();
		$("#userlogo").show();
		$("#img2").show();
		$('<h7>  ' + response.userDetails.fname+'</h7>').appendTo('#welcomeMessage');
		
		//for display info in user profile	
		$('<h3>  ' + response.userDetails.fname+'</h3>').appendTo('#profileusername');
		$('<h4> EmailId : ' + response.userDetails.emailId+'</h4>').appendTo('#profileemailId');
		$('<h4> Mobile NO : ' + response.userDetails.mobileNo+'</h4>').appendTo('#profilemobile');
		$('<h4> Alternate NO : ' + response.userDetails.alternateMobile+'</h4>').appendTo('#profilealtmobile');
		$('<h4> Gender : ' + response.userDetails.gender+'</h4>').appendTo('#profilegender');
		if(response.userDetails.type==0){
		$('<h4>Type : Student </h4>').appendTo('#profiletype');
		}else{
			$('<h4>Type : Teacher </h4>').appendTo('#profiletype');
		}


	}else if (xhr.status == "500") {
		_showToast('failure',xhr.responseText);
	} else {
		_showToast('failure',xhr.responseText);
	}
}

// login button clicked
$(document).ready(function(){
	$("#login").click(function(){
		$(".userinfo").show();
		$(".registerinfo").hide();
		$(".userlist").hide();
		$("#img2").hide();
		$(".resetcontainer").hide();
		$(".edituserinfo").hide();

	});
	$("#cancel1").click(function(){
		$(".userinfo").hide();
		$("#img2").show();
	});
});
	
// login validations
function validateEmail(){
		var email= document.getElementById("emailval").value;
		var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		if(regex.test(email)==false)
		{
		document.getElementById("email_msg").innerHTML ="Please enter valid email*";
		return false;
		}
		else
		{
			document.getElementById("email_msg").innerHTML="";
			return true;
		}
}
function validatePassword(){
		var password= document.getElementById("passval").value;
		
		if(password.length < 4 || password.length =="")
		{
		document.getElementById("pass_msg").innerHTML ="password should contains atleast 4 characters*";
		return false;
		}
		else
		{
			document.getElementById("pass_msg").innerHTML="";
			return true;
		}
}