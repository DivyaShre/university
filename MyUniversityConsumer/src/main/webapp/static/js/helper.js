//The first function called when the website is loaded
$(document).ready(function() {
});

/**
 * Wrapper for jquery ajax method
 * 
 * @param {String}
 *            URL AJAX URL
 * @param {JSON}
 *            data data to be sent to server(json)
 * @param {String}
 *            callback Callback function
 * @param {Bool}
 *            isAsync True for asynchronous, false for Synchronous
 * @param {String}
 *            method GET/POST
 */
// JSONstring, functionName, returnCall, async
var runAjax = function(data, url, callback, isAsync, method, previousAjaxCalls, cache, showLoading) {

	if (url === null || typeof (url) === 'undefined' || url === '') {
		return;
	}

	if (showLoading === null || typeof (showLoading) === 'undefined'
			|| showLoading === '') {
		showLoading = false;
	}

	if (isAsync !== false || isAsync === null
			|| typeof (isAsync) === 'undefined' || isAsync === '')
		isAsync = true;

	if (data === null || typeof (data) === 'undefined' || data === '')
		data = "";
	
	if (previousAjaxCalls === null || typeof (previousAjaxCalls) === 'undefined' || previousAjaxCalls === '')
		previousAjaxCalls = "";

	if (method === null || typeof (method) === 'undefined' || method === '')
		method = "POST";

	if (cache !== false || typeof (cache) === 'undefined' || cache === ''
			|| cache === null) {
		cache = false;
	}
	$.ajax({
		type : method,
		url : url,
		data : data,
		cache : cache,
		async : isAsync,
		beforeSend : function() {
			if (showLoading) {
				$(".loading").fadeIn("fast");
			}
		},
		success : function(xhr, textStatus) {

		},
		complete : function(xhr, textStatus) {
				if(xhr.status == 404){
					window.location.href == APP_BASE_URL + "404";
				} else if(xhr.status != "401"){
					if (showLoading) {
						$(".loading").fadeOut("fast");
					}
					if (callback !== null || typeof (callback) !== 'undefined'
							|| callback !== '') {
						callback(xhr, textStatus);
					}
				} else {
					if(previousAjaxCalls != ""){
						previousAjaxCall = previousAjaxCalls;
					} else {
						previousAjaxCall = "";
					}
					$("#login_modal").modal("show");
				}
		},
		error : function(error) {
		}
	});

}

//To display the success or error messages
var _showToast = function(toastType, message) {
	$('.toast').remove();
	var toast = '<div class = "toast toast-' + toastType + '">' + message
			+ '</div>';
	$('body').append(toast);
	$('.toast').show();
	window.setTimeout(function(){
		$('.toast').hide();
	}, 3000);
}

function _splitURL() {
	var url = window.location.href;
	return url.split('#')[1];
}