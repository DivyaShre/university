package com.university.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.university.dto.ErrorDTO;
import com.university.dto.UserDto;
import com.university.service.UserService;
import com.university.utility.Constants;
import com.university.utility.UNException;

@RestController
@RequestMapping("/user")
public class UserController {

	private static Logger log = Logger.getLogger(UserController.class);

	private static final String LOGIN = "/login";
	
	private static final String LOGIN_FAILURE_CODE="loginerrorcode";
	private static final String LOGIN_FAILURE_MSG="loginerrormsg";

	@Autowired
	private Environment env;
	
	@Autowired
	UserService userService;

	/**
	 * Login
	 * @param user
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = LOGIN, method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> getUserDetails(@RequestBody UserDto user,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("Inside getUserDetails");
		try{
			Map<String, Object> responseObj = new LinkedHashMap<String, Object>();
			UserDto userDetails = userService.getUserDetails(user);
			if(userDetails != null){
				responseObj.put("userDetails", userDetails);
				log.info("Login Success");
				return new ResponseEntity<Map<String, Object>>(responseObj, HttpStatus.OK);
			}else{
				log.info(env.getProperty(LOGIN_FAILURE_MSG));
				throw new UNException(env.getProperty(LOGIN_FAILURE_CODE), env.getProperty(LOGIN_FAILURE_MSG),Constants.EMPTY_STRING);
			}
		}
		catch (UNException un) {
			log.info(env.getProperty(LOGIN_FAILURE_MSG));
			throw new UNException(env.getProperty(LOGIN_FAILURE_CODE), env.getProperty(LOGIN_FAILURE_MSG),Constants.EMPTY_STRING);
		}
		catch (Exception e) {
			log.info(env.getProperty(LOGIN_FAILURE_MSG));
			throw new UNException(env.getProperty(LOGIN_FAILURE_CODE), env.getProperty(LOGIN_FAILURE_MSG),e.getMessage());
		}
	}
	
	@ExceptionHandler(UNException.class)
	public ResponseEntity<ErrorDTO> exceptionHandeler(HttpServletRequest req, UNException exception) {

		ErrorDTO errorJson = new ErrorDTO();
		errorJson.setErrorCode(exception.getErrCode());
		errorJson.setErrorMessage(exception.getErrMsg());
		errorJson.setDescription(exception.getDescription());
		log.error(exception);

		return new ResponseEntity<ErrorDTO>(errorJson, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}