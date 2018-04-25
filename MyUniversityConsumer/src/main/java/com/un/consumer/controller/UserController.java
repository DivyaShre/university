package com.un.consumer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.un.consumer.util.CommonUtility;
import com.un.consumer.util.Constants;
import com.un.consumer.util.HttpSessionCollector;
import com.un.consumer.util.HttpUtil;
import com.un.consumer.util.ServiceResponse;

@PropertySource({ "classpath:application-prod.properties", "classpath:application-dev.properties" })
@RestController
@RequestMapping("/user")
public class UserController {

	private static final String USER_LOGIN = "/login";

	@Autowired
	Environment environment;

	@RequestMapping(value = USER_LOGIN, method = RequestMethod.POST)
	public void validateSiteuser(@RequestParam(value = "requestJSON") String requestJSON, HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = null;
		ServiceResponse responseObj = null;

		try {
			JSONObject reqObj = new JSONObject(requestJSON);
			String emailId = (String) (reqObj.has("emailId") == true ? reqObj.getString("emailId") : "");
			String password = (String) (reqObj.has("password") == true ? reqObj.getString("password") : "");
			
			String baseUrl = environment.getProperty("baseUrl");
			// getting basic admin role access from Auth
			String url = baseUrl + Constants.AUTH_PATH + "oauth/token";
			ServiceResponse responseObjAuth = HttpUtil.sendPostForLoging(url, emailId, password);
			JSONObject newJsonObject = new JSONObject(responseObjAuth.getResponse());
			if (responseObjAuth.getStatus() == Constants.STATUS_OK) {
				request.getSession().invalidate();
				session = request.getSession(true);
				session.setAttribute("access_token", newJsonObject.getString("access_token"));
				session.setAttribute("emailId", emailId);

				// check login success status
				String urlMain = baseUrl + Constants.API_PATH + "user/login";
				responseObj = HttpUtil.sendPost(urlMain, reqObj.toString(), (String) session.getAttribute("access_token"));
				JSONObject userWrapper = new JSONObject(responseObj.getResponse());
				JSONObject userObj = userWrapper.getJSONObject("userDetails");
				session.setAttribute("fName", userObj.getString("fname"));
				session.setAttribute("emailId", userObj.getString("emailId"));
				session.setAttribute("id", userObj.getLong("id"));
				session.setAttribute("roleId", userObj.getInt("roleId"));
				

				String name = userObj.getString("fname");
				if (!userObj.get("mname").equals(null) && !userObj.getString("mname").trim().equals("")) {
					name += " " + userObj.getString("mname");
				}

				if (!userObj.get("lname").equals(null) && !userObj.getString("lname").trim().equals("")) {
					name += " " + userObj.getString("lname");
				}

				session.setAttribute("name", name);
				userObj.put("applicationType", environment.getProperty("applicationType"));

				/* SingleSignOn : allow only one active session per user */
				// get last login session object to invalidate
				String invalidateSessionId = HttpSessionCollector.singleSignOnCheck(session);
				if (invalidateSessionId != null) {
					HttpSession sessionToInvalidate = HttpSessionCollector.find(invalidateSessionId);
					sessionToInvalidate.invalidate();
				}

				JSONObject responseObjString = new JSONObject();
				responseObjString.put("userDetails", userObj);

				CommonUtility.writeResponse(response, responseObjString.toString(), responseObj.getStatus());

			} else {
				CommonUtility.writeResponse(response, "", responseObjAuth.getStatus());
			}
		} catch (Exception e) {
			// session.invalidate();
			if (responseObj != null) {
				CommonUtility.writeResponse(response, responseObj.getResponse(), responseObj.getStatus());
			} else {
				CommonUtility.writeResponse(response, "Internal server Error", 500);
			}
		}
	}
}