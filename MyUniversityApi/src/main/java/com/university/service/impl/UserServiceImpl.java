package com.university.service.impl;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.university.dao.UserDAO;
import com.university.dto.UserDto;
import com.university.mapper.UserMapper;
import com.university.model.UserEntity;
import com.university.service.UserService;
import com.university.utility.Constants;
import com.university.utility.UNException;

@Service
public class UserServiceImpl implements UserService {
	
	private static final String EMAIL_ID_NULL_EMPTY_ERROR_CODE="emailidnullemptyerrorcode";
	private static final String EMAIL_ID_NULL_EMPTY_ERROR_MSG="emailidnullemptyerrormsg";
	private static final String PASSWORD_NULL_EMPTY_ERROR_CODE="passwordnullemptyerrorcode";
	private static final String PASSWORD_NULL_EMPTY_ERROR_MSG="passwordnullemptyerrormsg";
	private static final String INVALID_USER_ERROR_CODE="invalidusererrorcode";
	private static final String INVALID_USER_ERROR_MSG="invalidusererrormsg";
	private static final String INVALID_INPUT_ERROR_CODE="invalidinputerrorcode";
	private static final String INVALID_INPUT_ERROR_MSG="invalidinputerrormsg";
	
	@Autowired
	private Environment env;
	
	@Autowired
	UserDAO userDao;

	@Override
	@Transactional
	public UserDto getUserDetails(UserDto user) {
		if(user != null){
			if(StringUtils.isBlank(user.getEmailId())){
				throw new UNException(env.getProperty(EMAIL_ID_NULL_EMPTY_ERROR_CODE), env.getProperty(EMAIL_ID_NULL_EMPTY_ERROR_MSG),Constants.EMPTY_STRING);
			}
			if(StringUtils.isBlank(user.getPassword())){
				throw new UNException(env.getProperty(PASSWORD_NULL_EMPTY_ERROR_CODE), env.getProperty(PASSWORD_NULL_EMPTY_ERROR_MSG),Constants.EMPTY_STRING);
			}
		}else{
			throw new UNException(env.getProperty(INVALID_INPUT_ERROR_CODE), env.getProperty(INVALID_INPUT_ERROR_MSG),Constants.EMPTY_STRING);
		}
		
		UserEntity userDetails = userDao.getUserDetails(user.getEmailId(), user.getPassword());
		if(userDetails != null){
			return UserMapper.convertUserEntityToDto(userDetails);
		}else{
			throw new UNException(env.getProperty(INVALID_USER_ERROR_CODE), env.getProperty(INVALID_USER_ERROR_MSG),Constants.EMPTY_STRING);
		}
	}
}