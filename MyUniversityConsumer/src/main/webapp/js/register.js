
// register button clicked 
$(document).ready(function(){
	$("#register").click(function(){
  		$(".registerinfo").show();
  		$(".userinfo").hide();
  		$("#img2").hide();
  	});
 });
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
  		function validateRegEmail() 
  		{
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
  	
  		function validateRegPassword() 
  		{
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
  		function validateConfPassword() 
  		{
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
  		function validateMobile() 
  		{
  			var mobileNo= document.getElementById("mobileNo").value;
  		
  			if(mobileNo!=10)
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
  		function validateAlternateMobile() 
  		{
  			var amobileNo= document.getElementById("altMobileNo").value;
  		
  			if(amobileNo!=10)
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