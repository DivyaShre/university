

$(document).ready(function(){
$("#listtable").on('click','.view',function(){
	 var currentRow=$(this).closest("tr");
	 var user_id=currentRow.find("td:eq(0)").html();
	 getDetails(user_id);
	 });
});
var viewUser = function(){

 	$(".userlist").hide();
	$(".userinfo").hide();
	$(".registerinfo").hide();
	$("#img2").hide();
	
	$(".edituserinfo").hide();
	$(".userlistbyid").show();

	
}
var getDetails = function(user_id){
var url = USERLIST_URL + "/" + user_id;
runAjax("", url, getUserById, true, METHOD_GET, "", "", true);

}


var getUserById = function(xhr,textStatus){

	 var response = JSON.parse(decodeURIComponent(xhr.responseText.replace(/\+/g,'%20')));
	 $('.detailsinfo').empty();
		localStorage.setItem('u_id',response.CreatedUserDetailsById.id);

	var newHTML="";
	$('<div class=divText>FirstName:' + response.CreatedUserDetailsById.fname+ '</div>').appendTo('.detailsinfo');
	if(response.CreatedUserDetailsById.mname != null){
		$('<div class=divText>MiddleName:' + response.CreatedUserDetailsById.mname+ '</div>').appendTo('.detailsinfo');
	}else{
		$('<div class=divText>MiddleName: N/A </div>').appendTo('.detailsinfo');
	}
	if(response.CreatedUserDetailsById.lname != null){
		$('<div class=divText>LastName:' + response.CreatedUserDetailsById.lname+ '</div>').appendTo('.detailsinfo');
	}else{
		$('<div class=divText>LastName: N/A </div>').appendTo('.detailsinfo');

	}
	$('<div class=divText>EmailId:' + response.CreatedUserDetailsById.emailId+ '</div>').appendTo('.detailsinfo');
	$('<div class=divText>MobileNo:' + response.CreatedUserDetailsById.mobileNo+ '</div>').appendTo('.detailsinfo');
	if(response.CreatedUserDetailsById.alternateMobile != null){
	$('<div class=divText>AlternateMobileNo:' + response.CreatedUserDetailsById.alternateMobile+ '</div>').appendTo('.detailsinfo');
	}if(response.CreatedUserDetailsById.type==0){
		$('<div class=divText>Type:Student</div>').appendTo('.detailsinfo');
	}else{
		$('<div class=divText>Type:Teacher</div>').appendTo('.detailsinfo');
	}if(response.CreatedUserDetailsById.gender!=null){
	$('<div class=divText>Gender:' + response.CreatedUserDetailsById.gender+ '</div>').appendTo('.detailsinfo');
	}
}

