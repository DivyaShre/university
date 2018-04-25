var login = function(emailId, password){
	var JSONObject = {};
	JSONObject.emailId = emailId;
	JSONObject.password = password;
	runAjax("requestJSON=" + JSON.stringify(JSONObject), LOGIN_URL, loginResponse, true, METHOD_POST, "", "", true);
}

var loginResponse = function(xhr,textStatus){
	if(xhr.status == "200"){
		var response = JSON.parse(decodeURIComponent(xhr.responseText.replace(/\+/g,'%20')));
		_showToast('success',"Logged in Successfully!");
	}else if (xhr.status == "500") {
		_showToast('failure',xhr.responseJSON.message);
	} else {
		_showToast('failure',xhr.responseJSON.message);
	}
}