package com.staxter.uam.service;

import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.staxter.uam.dto.User;
import com.staxter.uam.exception.UserAlreadyExistsException;
import com.staxter.uam.repository.UserEntity;
import com.staxter.uam.repository.UserRepository;
import com.staxter.uam.utility.Constants;
import com.staxter.uam.utility.Utils;

/**
 * The service implementation class for UserService
 * 
 * @author Veena Anil
 *
 */
@Service
public class UserServiceImpl implements UserService {

	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	Utils utils;
	UserRepository userRepository;
	/**
	 * Springsecurity password encoder object
	 */
	BCryptPasswordEncoder bCryptPasswordEncoder;

	public UserServiceImpl() {
		super();
	}

	@Autowired
	public UserServiceImpl(Utils utils, BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
		this.utils = utils;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.userRepository = userRepository;
	}

	/**
	 * The service method create a new user in the user repository If the user already exist throws UserAlreadyExistsException
	 * 
	 */
	@Override
	public User createUser(User user) throws UserAlreadyExistsException {
		// user.setId(utils.generateUserId());
		UserEntity userEntity = userRepository.findByUserName(user.getUserName());
		if (userEntity != null && userEntity.getUserName().equals(user.getUserName())) {
			throw new UserAlreadyExistsException(Constants.USER_ALREADY_EXISTS);
		}
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		userEntity = modelMapper.map(user, UserEntity.class);
		userEntity.setHashedPassword(bCryptPasswordEncoder.encode(user.getPlainTextPassword()));

		try {
			userRepository.save(userEntity);
		} catch (Exception ex) {
			logger.error("An exceprtion occured while adding new user to the database" + ex.getMessage());

		}
		User createdUser = modelMapper.map(userEntity, User.class);
		return createdUser;
	}

	/**
	 * This method is invoked by Spring while performing authentication and return t
	 * he UserDetails that matches with the userName
	 *
	 * @param userName return UserDetails
	 */
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByUserName(userName);
		if (user == null)
			throw new UsernameNotFoundException(userName);
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getHashedPassword(),
				true, true, true, true, new ArrayList<>());

	}

	/**
	 * This method returns the user details based on userName for the Login functionality
	 */
	public User getUserDetailsbyUserName(String userName) {
		UserEntity userEntity = userRepository.findByUserName(userName);
		if (userEntity == null)
			throw new UsernameNotFoundException(userName);
		return new ModelMapper().map(userEntity, User.class);
	}

}
