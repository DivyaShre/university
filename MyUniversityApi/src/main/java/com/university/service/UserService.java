package com.university.service;

import java.util.List;

import com.university.dto.UserDto;

public interface UserService {

	/**
	 * Get User Details by UserDto
	 * @param user
	 * @return 
	 */
	UserDto getUserDetails(UserDto user);
	
	/**
	 * Save user details
	 * @param user
	 * @return
	 */
	long saveUserDetails(UserDto user);
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	
	int updateUserDetails(UserDto user);
	
	/**
	 * 
	 * @param user
	 * @return
	 */

	int deleteUserDetails(UserDto userDto);

	/**
	 * 
	 * @return
	 */

	List<UserDto> gettingUserDetails();
	

	/**
	 * 
	 * @param id
	 * @return
	 */

	UserDto getDetailsById(long id);
	
	/**
	 * 
	 * @param user
	 * @return
	 */

	long saveNewPassword(UserDto user);
	
	/**
	 * Update ImageUrl
	 * @param userId
	 * @param imageUrl
	 * @return
	 */
	boolean updateImageUrl(long userId, String imageUrl);
	
	
}
