$(document).ready(function(){
	$("#listtable").on('click','.useredit',function(){
		 var currentRow=$(this).closest("tr");
		 var edit_id=currentRow.find("td:eq(0)").html();
		localStorage.setItem('id',edit_id);	
		getCurrentDetails(edit_id);
	});

	$("#cancel3").click(function(){
		$(".edituserinfo").hide();
		$(".userlist").show();
	});

});
// edit button in user details by id page
var editUserid = function(){
	$(".userlist").hide();
	$(".userinfo").hide();
	$(".registerinfo").hide();
	$("#img2").hide();
	$(".userlistbyid").hide();
	$(".edituserinfo").show();
	var edit_id = localStorage.getItem('u_id');
	localStorage.setItem('id',edit_id);	
	getCurrentDetails(edit_id);
}
// edit button in user list page
var editUser = function(){
	
 	$(".userlist").hide();
	$(".userinfo").hide();
	$(".registerinfo").hide();
	$("#img2").hide();
	$(".userlistbyid").hide();
	$(".edituserinfo").show();
	
}
var getCurrentDetails = function(edit_id){
	var url = USERLIST_URL + "/" + edit_id;
	runAjax("", url, getUserDetailsById, true, METHOD_GET, "", "", true);

}
var getUserDetailsById = function(xhr,textStatus){
	
	 var response = JSON.parse(decodeURIComponent(xhr.responseText.replace(/\+/g,'%20')));
		localStorage.setItem('userDetails',JSON.stringify(response.CreatedUserDetailsById));
		currentdetails();
}	 

var currentdetails = function(){
	var userDetails = localStorage.getItem('userDetails');
	$("#editFirstName").val(JSON.parse(userDetails).fname);
	$("#editMiddleName").val(JSON.parse(userDetails).mname);
	$("#editLastName").val(JSON.parse(userDetails).lname);
	$("#input[name='gender']:checked").val(JSON.parse(userDetails).gender);
	$("#editMobileNo").val(JSON.parse(userDetails).mobileNo);
	$("#editAltMobileNo").val(JSON.parse(userDetails).alternateMobile);
}


var editUpdate = function(){
	
	var e_id = localStorage.getItem('id');	
/*	var firstname = document.getElementById("editFirstName").value;
	var middlename = document.getElementById("editMiddleName").value;
	var lastname = document.getElementById("editLastName").value;
	var mobileNo= document.getElementById("editMobileNo").value;
	var amobileNo= document.getElementById("editAltMobileNo").value;
	var mregex = /^(\+\d{1,3}[- ]?)?\d{10}$/;
	if(firstname=="")
	{
		document.getElementById("ufn_msg").innerHTML ="enter FirstName";
		return false;
	}else if(middlename=="")
	{
		document.getElementById("umn_msg").innerHTML ="enter MiddleName";
		return false;
	}else if(lastname=="")
	{
		document.getElementById("uln_msg").innerHTML ="enter LastName";
		return false;
	}else if(mregex.test(mobileNo)==false)
	{
		document.getElementById("umno_msg").innerHTML ="phone number must be 10 digits";
		return false;
		}else if(amregex.test(amobileNo)==false)  
		{
			document.getElementById("uano_msg").innerHTML ="phone number must be 10 digits";
			return false;
		}else {
			document.getElementById("ufn_msg").innerHTML ="";
			document.getElementById("umn_msg").innerHTML ="";
			document.getElementById("uln_msg").innerHTML ="";
			document.getElementById("umno_msg").innerHTML="";
			document.getElementById("uano_msg").innerHTML="";

			return true;

		}
	*/
	
	var fName = $("#editFirstName").val();
	var mName = $("#editMiddleName").val();
	var lName = $("#editLastName").val();
	var gender = $("input[name='gender']:checked").val();
	var mobileNo = $("#editMobileNo").val();
	var altMobileNo = $("#editAltMobileNo").val();
	var JSONObject = {};

	JSONObject.id = e_id;
	if(fName!= null && fName != ""){
		JSONObject.fname = fName;
	}
	if(mName!= null && mName != ""){
		JSONObject.mname = mName;
	}
	if(lName!= null && lName != ""){
		JSONObject.lname = lName;
	}
	if(gender!= null && gender != ""){
		JSONObject.gender = gender;
	}
	if(mobileNo!= null && mobileNo != ""){
		JSONObject.mobileNo = mobileNo;
	}
	if(altMobileNo!= null && altMobileNo != ""){
		JSONObject.alternateMobile = altMobileNo;
	}
	
	runAjax("requestJSON=" + JSON.stringify(JSONObject), EDITUSER_URL,editResponse, true, METHOD_POST, "", "", true);
	
}

var editResponse = function(xhr, textStatus){
	if(xhr.status == "200"){
		var response = JSON.parse(decodeURIComponent(xhr.responseText.replace(/\+/g,'%20')));
		_showToast('success',"update Successfully!");
		// to get updated userlist after successful update
		$(".userlist").show();
		runAjax("", USERLIST_URL, getUsers, true, METHOD_GET, "", "", true);
		$(".edituserinfo").hide();
		
	}else if (xhr.status == "500") {
		_showToast('failure',xhr.responseText);
	} else {
		_showToast('failure',xhr.responseText);
	}

}


