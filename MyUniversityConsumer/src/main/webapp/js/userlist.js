$(document).ready(function(){
	$("#getlist").click(function(){
		$(".userlistbyid").hide();
		$(".profile").hide();
		$(".userinfo").hide();
		$(".registerinfo").hide();
		$("#img2").hide();
		$(".edituserinfo").hide();
		$(".userlist").show();

	});
	
});

var GenerateTable= function(){
	runAjax("", USERLIST_URL, getUsers, true, METHOD_GET, "", "", true);
	
}
var getUsers = function(xhr,textStatus){
	var row="";
	var response = JSON.parse(decodeURIComponent(xhr.responseText.replace(/\+/g,'%20')));
	$('#content').empty(); // to avoid multiple appending to the table
	for(i=0; i<response.CreatedUserDetails.length; i++){
			row +="<tr ><td id='hiddenid'>"+response.CreatedUserDetails[i].id+"</td>"
			row +="<td class='view' onclick='viewUser(); return false;'>"
			+response.CreatedUserDetails[i].fname+
			"</td>"
			if(response.CreatedUserDetails[i].type==0){
				row +="<td class='view' onclick='viewUser(); return false;'>Student</td>"
			}else{
				row+="<td class='view' onclick='viewUser(); return false;'>Teacher</td>"
			}
			row+=" <td><button class='btn btn-primary btn-xs useredit' title='Edit' data-toggle='modal' onclick='editUser(); return false;' ><span class='glyphicon glyphicon-pencil'></span></button>"+" "+
			"<button class='btn btn-danger btn-xs userdelete' title='Delete' data-toggle='modal'  ><a href='#' id='mylink'></a><span class='glyphicon glyphicon-trash'></span></button></td></tr>";
			  
		
	}
	$("#content").append(row);

	
	
}
