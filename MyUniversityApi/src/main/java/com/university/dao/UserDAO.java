package com.university.dao;

import com.university.model.UserEntity;

public interface UserDAO {

	/**
	 * Get User Details by emailId and password
	 * @param emailId
	 * @param password
	 * @return
	 */
	UserEntity getUserDetails(String emailId, String password);
}
