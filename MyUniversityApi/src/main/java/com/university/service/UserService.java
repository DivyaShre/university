package com.university.service;

import com.university.dto.UserDto;

public interface UserService {

	/**
	 * Get User Details by UserDto
	 * @param user
	 * @return
	 */
	UserDto getUserDetails(UserDto user);
}
