package com.staxter.uam.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.staxter.uam.dto.User;
import com.staxter.uam.exception.UserAlreadyExistsException;

/**
 * UserService Interface for User Management. It extend the UserDetailsService class to perform authentication while login
 * 
 * @author Veena Anil
 *
 */

public interface UserService extends UserDetailsService {

	/**
	 * The service method to create a new user and returns the created used object
	 * 
	 * @param user
	 * @return User
	 * @throws UserAlreadyExistsException
	 */
	User createUser(User user) throws UserAlreadyExistsException;

	/**
	 * This method returns the user details based on userName for the Login
	 * functionality
	 * 
	 * @param userName
	 * @return User
	 */
	User getUserDetailsbyUserName(String userName);
}
