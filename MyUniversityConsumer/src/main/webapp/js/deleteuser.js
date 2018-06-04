$(document).ready(function(){
$("#listtable").on('click','.userdelete',function(){
	 var currentRow=$(this).closest("tr");
	 var u_id=currentRow.find("td:eq(0)").html(); 
	 
	// confirm box pop up a alert box that delete confirmation. if click ok it will run delete function, else return false.
     var d=confirm("Are you sure you want to delete this user?"); 
     if(d==true){
    	deleteUser(u_id);
    }else{
    	return false;
    }
	
	});
});
var deleteUser = function(u_id){
	var JSONObject = {};
	JSONObject.id = u_id;

	runAjax("requestJSON=" + JSON.stringify(JSONObject), DELETEUSER_URL, deleteUserDetails, true, METHOD_POST, "", "", true);
	

}
var deleteUserDetails = function(xhr, textStatus){
	if(xhr.status == "200"){
		var response = JSON.parse(decodeURIComponent(xhr.responseText.replace(/\+/g,'%20')));
		_showToast('success',"delete Successfully!");
		runAjax("", USERLIST_URL, getUsers, true, METHOD_GET, "", "", true); // recalling runajax of userlist to get updated list after deleting particular row

	}else if (xhr.status == "500") {
		_showToast('failure',xhr.responseText);
	} else {
		_showToast('failure',xhr.responseText);
	}	
}

