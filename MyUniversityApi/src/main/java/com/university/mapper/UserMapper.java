package com.university.mapper;

import com.university.dto.UserDto;
import com.university.model.UserEntity;

public class UserMapper {

	public static UserDto convertUserEntityToDto(UserEntity user){
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setFname(user.getFname());
		userDto.setMname(user.getMname());
		userDto.setLname(user.getLname());
		userDto.setEmailId(user.getEmailId());
		userDto.setPassword(user.getPassword());
		userDto.setMobileNo(user.getMobileNo());
		userDto.setAlternateMobile(user.getAlternateMobile());
		userDto.setRoleId(user.getRoleId());
		userDto.setType(user.getType());
		userDto.setStatus(user.getStatus());
		userDto.setGender(user.getGender());
		return userDto;
	}
}
