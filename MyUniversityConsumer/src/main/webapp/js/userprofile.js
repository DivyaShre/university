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
    $("#profile-picture").change(function(){
        readURL(this);
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


