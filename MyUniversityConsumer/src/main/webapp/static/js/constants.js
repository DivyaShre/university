var BASE_URL = "http://localhost:8081/";

var API_BASE_URL = BASE_URL + "UniversityApi/";
var APP_BASE_URL = BASE_URL + "UniversityConsumer/";
var UN = "un";
var AUTH_KEY = "auth";

//Methods
var METHOD_GET = "GET";
var METHOD_POST = "POST";

// URLs
var LOGIN_URL = APP_BASE_URL + UN + "/user/login";
var REGISTER_URL = APP_BASE_URL + UN + "/user/register";
var USERLIST_URL = APP_BASE_URL + UN + "/user/getAllUser";
var DELETEUSER_URL = APP_BASE_URL + UN + "/user/delete";
var EDITUSER_URL = APP_BASE_URL + UN + "/user/update";
var FORGOTPASSWORD_URL = APP_BASE_URL + UN + "/user/forgotpassword";
var LOGOUT_URL = APP_BASE_URL + UN + "/user/logout";
var UPLOAD_PIC = API_BASE_URL + "upload/userprofilepic";
var DOWNLOAD_PIC = API_BASE_URL + "download/"; 
