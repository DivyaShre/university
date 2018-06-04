// register button clicked 
$(document).ready(function(){
	$("#register").click(function(){
  		$(".registerinfo").show();
  		$(".userinfo").hide();
  		$(".userlist").hide();
		$(".resetcontainer").hide();
  		$("#img2").hide();
  	});
	$("#cancel2").click(function(){
		$(".registerinfo").hide();
		$("#img2").show();

	});
 });

var register = function(){
	var fName = $("#firstName").val();
	var mName = $("#middleName").val();
	var lName = $("#lastName").val();
	var regEmail = $("#regemailval").val();
	var regPassword = $("#regpassval").val();
	var confRegPassword = $("#confpassval").val();
	var mobileNo = $("#mobileNo").val();
	var altMobileNo = $("#altMobileNo").val();
	var gender = $("input[name='gender']:checked").val();
	var type = $("input[name='type']:checked").val();
	var JSONObject = {};
	JSONObject.fname = fName;
	JSONObject.mname = mName;
	JSONObject.lname = lName;
	JSONObject.emailId = regEmail;
	JSONObject.password = regPassword;
	JSONObject.confRegPassword = confRegPassword;
	JSONObject.mobileNo = mobileNo;
	JSONObject.alternateMobile = altMobileNo;
	JSONObject.gender = gender;
	JSONObject.type = type;
	runAjax("requestJSON=" + JSON.stringify(JSONObject), REGISTER_URL, registerResponse, true, METHOD_POST, "", "", true);
}
var registerResponse = function(xhr,textStatus){
	if(xhr.status == "200"){
		var response = JSON.parse(decodeURIComponent(xhr.responseText.replace(/\+/g,'%20')));
		_showToast('success',"Registered Successfully!");
		$(".registerinfo").hide();
		$("#img2").show();
	}else if (xhr.status == "500") {
		_showToast('failure',xhr.responseText);
	} else {
		_showToast('failure',xhr.responseText);
	}
	
}


 // register validation
function validateFn(){
	var firstname = document.getElementById("firstName").value;
	if(firstname=="")
		{
			document.getElementById("fn_msg").innerHTML ="enter FirstName*";
			return false;
		}else{
			document.getElementById("fn_msg").innerHTML ="";
			return true;
		}
 }
function validateMn(){
	var middlename = document.getElementById("middleName").value;
	if(middlename=="")
		{
			document.getElementById("mn_msg").innerHTML ="enter MiddleName";
			return false;
		}else{
			document.getElementById("mn_msg").innerHTML ="";
			return true;
		}
}
function validateLn(){
  			var lastname = document.getElementById("lastName").value;
	if(lastname=="")
		{
			document.getElementById("ln_msg").innerHTML ="enter LastName*";
			return false;
		}else{
			document.getElementById("ln_msg").innerHTML ="";
			return true;
		}

}
function validateRegEmail(){
	var regemail= document.getElementById("regemailval").value;
	var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	if(regex.test(regemail)==false)
	{
	document.getElementById("regemail_msg").innerHTML ="Please enter valid email*";
	return false;
	}
	else
	{
		document.getElementById("regemail_msg").innerHTML="";
		return true;
	}
}
  	
function validateRegPassword(){
	var regpassword= document.getElementById("regpassval").value;
	
	if(regpassword.length < 4 || regpassword.length =="")
	{
	document.getElementById("regpass_msg").innerHTML ="password should contains atleast 4 characters*";
	return false;
	}
	else
	{
		document.getElementById("regpass_msg").innerHTML="";
		return true;
	}
}
function validateConfPassword(){
	var confpassword= document.getElementById("confpassval").value;
	var regpassword= document.getElementById("regpassval").value;
	if(confpassword!=regpassword)
	{
	document.getElementById("confpass_msg").innerHTML ="password doesnot match*";
	return false;
	}
	else
	{
		document.getElementById("confpass_msg").innerHTML="";
		return true;
	}
}
function validateMobile(){
	var mobileNo= document.getElementById("mobileNo").value;
	var mregex = /^(\+\d{1,3}[- ]?)?\d{10}$/;
	if(mregex.test(mobileNo)==false)
	{
	document.getElementById("mno_msg").innerHTML ="phone number must be 10 digits*";
	return false;
	}
	else
	{
		document.getElementById("mno_msg").innerHTML="";
		return true;
	}
}
function validateAlternateMobile(){
	var amobileNo= document.getElementById("altMobileNo").value;
	var amregex = /^(\+\d{1,3}[- ]?)?\d{10}$/;
	if(amregex.test(amobileNo)==false)  
	{
	document.getElementById("ano_msg").innerHTML ="phone number must be 10 digits";
	return false;
	}
	else
	{
		document.getElementById("ano_msg").innerHTML="";
		return true;
	}
}
function loginreg(){
	$(".userinfo").show();
	$(".registerinfo").hide();

	
}
