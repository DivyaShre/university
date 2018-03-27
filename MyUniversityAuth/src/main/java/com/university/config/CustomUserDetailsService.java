package com.university.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.stereotype.Service;

import com.university.dao.RoleDao;
import com.university.dao.UserDao;
import com.university.model.CustomUserDetails;
import com.university.model.RoleEntity;
import com.university.model.UserEntity;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	//private static final long serialVersionUID = 6529685098267757690L;

	@Autowired
	private UserDao userDao;

	@Autowired
	RoleDao roleDao;

	/**
	 * This method is used to load a user by accessing userName
	 */
	@Override
	public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Find by username
		CustomUserDetails userDetails=null;
		try {
			UserEntity userEntity = userDao.getById(username);
			if (userEntity.getStatus() == 1) {

				RoleEntity role = roleDao.getById(userEntity.getRoleId());

				// assign grant authority based on role
				GrantedAuthority authority = new SimpleGrantedAuthority(role.getRoleName());
				userDetails =  new CustomUserDetails(userEntity.getFname(), userEntity.getPassword(),true, true, true, true, Arrays.asList(authority));

			}
			else {

				throw new UnapprovedClientAuthenticationException("Username " + username + " not Active");
			}
			return userDetails;
		}
		catch (Exception e) {
			throw new UsernameNotFoundException("Username " + username + " not found");
		}
	}
}
