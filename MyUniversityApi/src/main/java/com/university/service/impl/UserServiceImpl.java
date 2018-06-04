package com.university.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.university.dao.UserDAO;
import com.university.dto.UserDto;
import com.university.mapper.UserMapper;
import com.university.model.UserEntity;
import com.university.service.UserService;
import com.university.utility.Constants;
import com.university.utility.UNException;

@PropertySource({"classpath:error.properties"})
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
	private static final String USER_ALREADY_EXISTS_ERROR_CODE="useralreadyexistserrorcode";
	private static final String USER_ALREADY_EXISTS_ERROR_MSG="useralreadyexistserrormsg";
	private static final String FNAME_NULL_EMPTY_ERROR_CODE="fnamenullemptyerrorcode";
	private static final String FNAME_NULL_EMPTY_ERROR_MSG="fnamenullerrormsg";
	private static final String MOBILE_NO_NULL_EMPTY_ERROR_CODE="mobilenonullemptyerrorcode";
	private static final String MOBILE_NO_NULL_EMPTY_ERROR_MSG="mobilenonullerrormsg";
	private static final String UPDATE_ERROR_CODE = "updatenotpossiblecode";
	private static final String UPDATE_ERROR_MSG = "updatenotpossiblemsg";
	private static final String DELETE_ERROR_CODE = "deleteerrorcode";
	private static final String DELETE_ERROR_MSG = "deleteerrormsg";
	private static final String EMAIL_ERROR_CODE = "emailerrorcode";
	private static final String EMAIL_ERROR_MSG = "emailerrormsg";
	private static final String MOBILE_ERROR_CODE = "mobileerrorcode";
	private static final String MOBILE_ERROR_MSG = "mobileerrormsg";
	
	@Autowired
	private Environment env;
	
	@Autowired
	UserDAO userDao;
	@Override
// Provides the application the ability to declaratively control transaction boundaries on CDI manager beans
	@Transactional
	public UserDto getUserDetails(UserDto user) 
	{
		if(user != null)
		{
			if(StringUtils.isBlank(user.getEmailId()))
			{
				throw new UNException(env.getProperty(EMAIL_ID_NULL_EMPTY_ERROR_CODE), env.getProperty(EMAIL_ID_NULL_EMPTY_ERROR_MSG),Constants.EMPTY_STRING);
			}
			if(!Pattern.matches(Constants.EMAIL_PATTERN, user.getEmailId())){
				throw new UNException(env.getProperty(EMAIL_ERROR_CODE), env.getProperty(EMAIL_ERROR_MSG),Constants.EMPTY_STRING);
			}
			if(StringUtils.isBlank(user.getPassword()))
			{
				throw new UNException(env.getProperty(PASSWORD_NULL_EMPTY_ERROR_CODE), env.getProperty(PASSWORD_NULL_EMPTY_ERROR_MSG),Constants.EMPTY_STRING);
			}

		}else{
			throw new UNException(env.getProperty(INVALID_INPUT_ERROR_CODE), env.getProperty(INVALID_INPUT_ERROR_MSG),Constants.EMPTY_STRING);
			}
			
			UserEntity userDetails = userDao.getUserDetails(user.getEmailId(), user.getPassword());
			if(userDetails != null)
			{
				return UserMapper.convertUserEntityToDto(userDetails);
			}else{
				throw new UNException(env.getProperty(INVALID_USER_ERROR_CODE), env.getProperty(INVALID_USER_ERROR_MSG),Constants.EMPTY_STRING);
				}
	}
	@Override
	@Transactional
	public long saveUserDetails(UserDto userDto) 
	{
		if(userDto!=null)
		{			
			if(StringUtils.isBlank(userDto.getEmailId()))
			{
				throw new UNException(env.getProperty(EMAIL_ID_NULL_EMPTY_ERROR_CODE), env.getProperty(EMAIL_ID_NULL_EMPTY_ERROR_MSG),Constants.EMPTY_STRING);
			}
			if(!Pattern.matches(Constants.EMAIL_PATTERN, userDto.getEmailId())){
				throw new UNException(env.getProperty(EMAIL_ERROR_CODE), env.getProperty(EMAIL_ERROR_MSG),Constants.EMPTY_STRING);
			}
			if(StringUtils.isBlank(userDto.getFname()))
			{
				throw new UNException(env.getProperty(FNAME_NULL_EMPTY_ERROR_CODE), env.getProperty(FNAME_NULL_EMPTY_ERROR_MSG),Constants.EMPTY_STRING);
			}
			if(StringUtils.isBlank(userDto.getPassword()))
			{
				throw new UNException(env.getProperty(PASSWORD_NULL_EMPTY_ERROR_CODE), env.getProperty(PASSWORD_NULL_EMPTY_ERROR_MSG),Constants.EMPTY_STRING);
			}
			if(StringUtils.isBlank(userDto.getMobileNo()))
			{
				throw new UNException(env.getProperty(MOBILE_NO_NULL_EMPTY_ERROR_CODE), env.getProperty(MOBILE_NO_NULL_EMPTY_ERROR_MSG),Constants.EMPTY_STRING);
			}
			if(!Pattern.matches(Constants.MOBILE_NO_PATTERN, userDto.getMobileNo())){
				throw new UNException(env.getProperty(MOBILE_ERROR_CODE), env.getProperty(MOBILE_ERROR_MSG),Constants.EMPTY_STRING);
			}
				
			}else{
					throw new UNException(env.getProperty(INVALID_INPUT_ERROR_CODE), env.getProperty(INVALID_INPUT_ERROR_MSG),Constants.EMPTY_STRING);
			}
		
			UserEntity existingUser = userDao.getUserDetailsByEmailId(userDto.getEmailId());
			if(existingUser == null)
			{
				UserEntity user = new UserEntity();
				user.setEmailId(userDto.getEmailId());
				user.setFname(userDto.getFname());
				user.setMname(userDto.getMname());
				user.setLname(userDto.getLname());
				user.setPassword(userDto.getPassword());
				user.setMobileNo(userDto.getMobileNo());
				user.setAlternateMobile(userDto.getAlternateMobile());
				user.setType(userDto.getType());
				user.setRoleId(1);
				user.setStatus(Constants.ACTIVE);
				user.setGender(userDto.getGender());
				user.setCreatedBy(1);
				user.setCreatedTimestamp(new Date());
				long createdUserId = userDao.saveUserDetails(user);
				return createdUserId;
			}else{
				throw new UNException(env.getProperty(USER_ALREADY_EXISTS_ERROR_CODE), env.getProperty(USER_ALREADY_EXISTS_ERROR_MSG),Constants.EMPTY_STRING);
			}
	}
	@Override
	@Transactional
	public int updateUserDetails(UserDto user)
	{
		if(StringUtils.isBlank(user.getFname()) && StringUtils.isBlank(user.getMobileNo()) && StringUtils.isBlank(user.getAlternateMobile())) 
		{
			throw new UNException(env.getProperty(UPDATE_ERROR_CODE), env.getProperty(UPDATE_ERROR_MSG),Constants.EMPTY_STRING);
		}
		if(!StringUtils.isBlank(user.getMobileNo()))
		{
			if(!Pattern.matches(Constants.MOBILE_NO_PATTERN, user.getMobileNo()))
			{
				throw new UNException(env.getProperty(MOBILE_ERROR_CODE), env.getProperty(MOBILE_ERROR_MSG),Constants.EMPTY_STRING);
			}
		}
		if(!StringUtils.isBlank(user.getAlternateMobile()))
		{
			if(!Pattern.matches(Constants.MOBILE_NO_PATTERN, user.getAlternateMobile()))
			{
				throw new UNException(env.getProperty(MOBILE_ERROR_CODE), env.getProperty(MOBILE_ERROR_MSG),Constants.EMPTY_STRING);
			}
		}
		
		if(user.getId()>0)
		{
			int updatedUser = userDao.updateUserDetails(user.getId(), user.getFname(), user.getMname(),user.getLname(), user.getMobileNo(), user.getAlternateMobile(), user.getGender());
			return updatedUser;
		}else{
				throw new UNException(env.getProperty(UPDATE_ERROR_CODE), env.getProperty(UPDATE_ERROR_MSG),Constants.EMPTY_STRING);
		}
				
	}
	@Override
	@Transactional
	public int deleteUserDetails(UserDto user)
	{
		if(user.getId()>0)
		{
			UserEntity existingUser = userDao.getUserDetailsById(user.getId());
			if(existingUser.getStatus() == Constants.ACTIVE)
			{
				int deleteUser = userDao.deleteUserDetails(Constants.IN_ACTIVE,user.getId());
				return deleteUser;
			}
		}
		throw new UNException(env.getProperty(DELETE_ERROR_CODE), env.getProperty(DELETE_ERROR_MSG),Constants.EMPTY_STRING);
		
	}
	@Override
	@Transactional
	public List<UserDto> gettingUserDetails() 
	{
		List<UserDto> userDtoList = new ArrayList<UserDto>();
		List<UserEntity> list= userDao.gettingUserDetails(Constants.ACTIVE); //get only active users
		for(UserEntity user: list )
		{
			userDtoList.add((UserMapper.convertUserEntityToDto(user)));
		}
		return userDtoList;
	}
	@Override
	@Transactional
	public UserDto getDetailsById(long id) {
		UserEntity list= userDao.getUserDetailsById(id);
		if(list!=null){
		return UserMapper.convertUserEntityToDto(list);
		}else{
			throw new UNException(env.getProperty(INVALID_USER_ERROR_CODE), env.getProperty(INVALID_USER_ERROR_MSG),Constants.EMPTY_STRING);
			}

	}
	@Override
	@Transactional
	public long saveNewPassword(UserDto userDto) {
		if(userDto!=null)
		{			
			if(StringUtils.isBlank(userDto.getEmailId()))
			{
				throw new UNException(env.getProperty(EMAIL_ID_NULL_EMPTY_ERROR_CODE), env.getProperty(EMAIL_ID_NULL_EMPTY_ERROR_MSG),Constants.EMPTY_STRING);
			}
			if(!Pattern.matches(Constants.EMAIL_PATTERN, userDto.getEmailId())){
				throw new UNException(env.getProperty(EMAIL_ERROR_CODE), env.getProperty(EMAIL_ERROR_MSG),Constants.EMPTY_STRING);
			}
		
		}else{
			throw new UNException(env.getProperty(INVALID_INPUT_ERROR_CODE), env.getProperty(INVALID_INPUT_ERROR_MSG),Constants.EMPTY_STRING);
		}
		UserEntity existingUser = userDao.getUserDetailsByEmailId(userDto.getEmailId());
		if(existingUser != null)
		{
			existingUser.setPassword(userDto.getPassword());
			long savedPassword = userDao.saveUserDetails(existingUser);
			return savedPassword;
		}else{
			throw new UNException(env.getProperty(USER_ALREADY_EXISTS_ERROR_CODE), env.getProperty(USER_ALREADY_EXISTS_ERROR_MSG),Constants.EMPTY_STRING);
		}
		
	}
	
	
}
