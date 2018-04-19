package com.university.dao;

import java.util.List;

import com.university.model.UserEntity;

public interface UserDAO {

	/**
	 * Get User Details by emailId and password
	 * @param emailId
	 * @param password
	 * @return
	 */
	UserEntity getUserDetails(String emailId, String password);
	
	/**
	 * Get user details by emailId
	 * @param emailId
	 * @return
	 */
	UserEntity getUserDetailsByEmailId(String emailId);
	
	/**
	 * Save user details
	 * @param user
	 * @return
	 */
	public long saveUserDetails(UserEntity user);


	/**
	 * 
	 * @param id
	 * @param fname
	 * @param mobileNo
	 * @param alternateMobile
	 * @return
	 */

	int updateUserDetails(long id, String fname, String mobileNo, String alternateMobile);

	
	/**
	 * 
	 * @param id
	 * @return
	 */
	UserEntity getUserDetailsById(long id);

	/**
	 * 
	 * @param status
	 * @param id
	 * @return
	 */

	int deleteUserDetails(int status, long id);

	/**
	 * 
	 * @return
	 */
	List<UserEntity> gettingUserDetails();

}
