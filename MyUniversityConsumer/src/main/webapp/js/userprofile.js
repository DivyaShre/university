$(document).ready(function(){
	$("#userprofile").click(function(){
		$("#img2").hide();
		$("#getlist").show();
		$(".userlist").hide();
		$("#userlogo").show();
		$(".profile").show();

	});
});

$(document).ready(function(){
// Prepare the preview for profile picture
   /* $("#profile-picture").change(function(){
    	console.log("inside change method");
    	console.log(this.file);
        readURL(this);
    });*/
	
    $('#profilePicture').on('change', function() {
        var file = this.files[0];
        var data = new FormData();

		// If you want to add an extra field for the FormData
        data.append("file",file);
        data.append("userId", 1);
        $.ajax({
        	  url: UPLOAD_PIC, 
        	  type: "POST",
              enctype: 'multipart/form-data',
              data: data,
              processData: false,
              contentType: false,
              cache: false,             // Using FormData, no need to process data.
              success: function (data) {
                  console.log("SUCCESS : ", data);
              },
              error: function (e) {
                  console.log("ERROR : ", e);
              }
        	})
    });
});

function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $('#wizardPicturePreview').attr('src', e.target.result).fadeIn('slow');
        }
        reader.readAsDataURL(input.files[0]);
    }
}


