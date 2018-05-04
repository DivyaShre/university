package com.university.controller;

import java.util.LinkedHashMap;
import java.util.List;
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
// used to indicate that it is a controller
@RestController
//@RequestMapping is used to match the content inside it to the content in request
@RequestMapping("/user")
public class UserController 
{
	private static Logger log = Logger.getLogger(UserController.class);
	// when request finds /login it will come to LOGIN 
	private static final String LOGIN = "/login";
	private static final String LOGIN_FAILURE_CODE="loginerrorcode";
	private static final String LOGIN_FAILURE_MSG="loginerrormsg";
	// to save a new user details to the database
	private static final String SAVE = "/save";
	private static final String SAVE_FAILURE_CODE="saveerrorcode";
	private static final String SAVE_FAILURE_MSG="saveerrormsg";
	// to update existing user information
	private static final String UPDATE = "/update";
	private static final String UPDATE_FAILURE_CODE ="updateerrorcode";
	private static final String UPDATE_FAILURE_MSG ="updateerrormsg";
	// to soft delete/delete the user  
	private static final String DELETE = "/delete";
	private static final String DELETE_FAILURE_CODE = "deleteerrorcode";
	private static final String DELETE_FAILURE_MSG = "deleteerrormsg";
	private static final String GET = "/get";
	private static final String GET_FAILURE_CODE = "geterrorcode";
	private static final String GET_FAILURE_MSG = "geterrormsg";
	
		
	// @Autowired is used to get control over where and how autowired should be accomplished
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
	// @RequestMapping annotation used to map the HTTP requests to handler methods of MVC and REST controllers
	@RequestMapping(value = LOGIN, method = RequestMethod.POST)
	/*ResponseEntity represents the entire HTTP response you can control anything goes to it
	@RequestBody:Annotation indicating a method parameter should be bound to the body of the HTTP request. */
	public ResponseEntity<Map<String, Object>> getUserDetails(@RequestBody UserDto user,HttpServletRequest request, HttpServletResponse response) 
	{
		log.info("Inside getUserDetails");
		// try catch is used to handle the exceptions
		try{
			Map<String, Object> responseObj = new LinkedHashMap<String, Object>();
			UserDto userDetails = userService.getUserDetails(user);
			if(userDetails != null)
			{
				responseObj.put("userDetails", userDetails);
				log.info("Login Success");
				return new ResponseEntity<Map<String, Object>>(responseObj, HttpStatus.OK);
			}else{
				log.info(env.getProperty(LOGIN_FAILURE_MSG));
				//UNException is an user defined exception
				throw new UNException(env.getProperty(LOGIN_FAILURE_CODE), env.getProperty(LOGIN_FAILURE_MSG),Constants.EMPTY_STRING);
				}
			}
			catch (UNException un)
			{
				log.info(env.getProperty(LOGIN_FAILURE_MSG));
				throw new UNException(un.getErrCode(), un.getErrMsg(),Constants.EMPTY_STRING);
			}
			catch (Exception e)
			{
				log.info(env.getProperty(LOGIN_FAILURE_MSG));
				throw new UNException(env.getProperty(LOGIN_FAILURE_CODE), env.getProperty(LOGIN_FAILURE_MSG),e.getMessage());
			}
	}
	/**
	 * saving
	 * @param user
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = SAVE, method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> saveUserDetails(@RequestBody UserDto user,HttpServletRequest request, HttpServletResponse response)
	{
		log.info("Inside saveUserDetails");
		try{
				Map<String, Object> responseObj = new LinkedHashMap<String, Object>();
				long createdUserId = userService.saveUserDetails(user);
				if(createdUserId > 0){
				responseObj.put("createdUserId", createdUserId);
				log.info("save success");
				return new ResponseEntity<Map<String, Object>>(responseObj, HttpStatus.OK);
			}else{
				log.info(env.containsProperty(SAVE_FAILURE_MSG));
				throw new UNException(env.getProperty(SAVE_FAILURE_CODE), env.getProperty(SAVE_FAILURE_MSG), Constants.EMPTY_STRING);
				
			}
		}catch (UNException un) 
				{
					log.info(env.getProperty(SAVE_FAILURE_MSG));
					throw new UNException(un.getErrCode(),un.getErrMsg(),Constants.EMPTY_STRING);
				}
				catch (Exception e)
				{
					log.info(env.getProperty(SAVE_FAILURE_MSG));
					throw new UNException(env.getProperty(SAVE_FAILURE_CODE),env.getProperty(SAVE_FAILURE_MSG),Constants.EMPTY_STRING);
				}		
	}
	/**
	 * Update a user
	 * @param user
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = UPDATE, method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> updateUserDetails(@RequestBody UserDto user,HttpServletRequest request, HttpServletResponse response)
	{
		log.info("Inside updateUserDetails");
		try{
			Map<String, Object> responseObj = new LinkedHashMap<String, Object>();
			int updatedUser = userService.updateUserDetails(user);
			if( updatedUser > 0)
			{
				responseObj.put("updatedUser", updatedUser);
				log.info("update success");
				return new ResponseEntity<Map<String, Object>>(responseObj, HttpStatus.OK);
			}else{
					log.info(env.containsProperty(UPDATE_FAILURE_MSG));
					throw new UNException(env.getProperty(UPDATE_FAILURE_CODE), env.getProperty(UPDATE_FAILURE_MSG), Constants.EMPTY_STRING);
				}
			}catch (UNException un){
				log.info(env.getProperty(UPDATE_FAILURE_MSG));
				throw new UNException(env.getProperty(UPDATE_FAILURE_CODE),env.getProperty(UPDATE_FAILURE_MSG),Constants.EMPTY_STRING);
			}catch (Exception e){
					log.info(env.getProperty(UPDATE_FAILURE_MSG));
					throw new UNException(env.getProperty(UPDATE_FAILURE_CODE),env.getProperty(UPDATE_FAILURE_MSG),Constants.EMPTY_STRING);
			}	
	}
	/**
	 * Delete a user
	 * @param user
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = DELETE, method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>>deleteUserDetails(@RequestBody UserDto user,HttpServletRequest request, HttpServletResponse response)
	{
		log.info("Inside deleteUserDetails");
		try{
		Map<String, Object> responseObj = new LinkedHashMap<String, Object>();
		int createdUserId = userService.deleteUserDetails(user);
		if(createdUserId > 0)
		{
			log.info("Delete success");
			return new ResponseEntity<Map<String, Object>>(responseObj, HttpStatus.OK);
		}else{
				log.info(env.containsProperty(DELETE_FAILURE_MSG));
				throw new UNException(env.getProperty(DELETE_FAILURE_CODE), env.getProperty(UPDATE_FAILURE_MSG), Constants.EMPTY_STRING);
				}
			}catch (UNException un){
			 		log.info(env.getProperty(DELETE_FAILURE_MSG));
			 		throw new UNException(env.getProperty(DELETE_FAILURE_CODE),env.getProperty(DELETE_FAILURE_MSG),Constants.EMPTY_STRING);
			 	}catch (Exception e){
					log.info(env.getProperty(DELETE_FAILURE_MSG));
					throw new UNException(env.getProperty(DELETE_FAILURE_CODE),env.getProperty(DELETE_FAILURE_MSG),Constants.EMPTY_STRING);
				}	
	}
	/**
	 * get users list
	 * @return
	 */
	@RequestMapping(value = GET, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>>gettingUserDetails()
	{
		log.info("Inside gettingUserDetails");
		try{
				Map<String, Object> responseObj = new LinkedHashMap<String, Object>();
				List<UserDto> createdUserDetails = userService.gettingUserDetails();
			 	if(createdUserDetails != null)
			 	{
			 		responseObj.put("CreatedUserDetails", createdUserDetails);
			 		log.info("Get success");
			 		return new ResponseEntity<Map<String, Object>>(responseObj, HttpStatus.OK);
	 			}else{
	 					log.info(env.containsProperty(GET_FAILURE_MSG));
	 					throw new UNException(env.getProperty(GET_FAILURE_CODE), env.getProperty(GET_FAILURE_MSG), Constants.EMPTY_STRING);
	 				}
			 }catch (UNException un){
	 			log.info(env.getProperty(GET_FAILURE_MSG));
	 			throw new UNException(env.getProperty(GET_FAILURE_CODE),env.getProperty(GET_FAILURE_MSG),Constants.EMPTY_STRING);
	 		 }catch (Exception e){
	 			log.info(env.getProperty(GET_FAILURE_MSG));
	 			throw new UNException(env.getProperty(GET_FAILURE_CODE),env.getProperty(GET_FAILURE_MSG),Constants.EMPTY_STRING);
	 		}	
	}

	@ExceptionHandler(UNException.class)
	//showing error code and message in ErrorDTO class
	public ResponseEntity<ErrorDTO> exceptionHandeler(HttpServletRequest req, UNException exception)
	{
		ErrorDTO errorJson = new ErrorDTO();
		errorJson.setErrorCode(exception.getErrCode());
		errorJson.setErrorMessage(exception.getErrMsg());
		errorJson.setDescription(exception.getDescription());
		log.error(exception);
		return new ResponseEntity<ErrorDTO>(errorJson, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}