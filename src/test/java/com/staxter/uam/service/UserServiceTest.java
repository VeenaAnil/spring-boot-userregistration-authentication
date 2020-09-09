package com.staxter.uam.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.staxter.uam.dto.User;
import com.staxter.uam.exception.UserAlreadyExistsException;
import com.staxter.uam.repository.UserEntity;
import com.staxter.uam.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserServiceImpl userService;

	@Mock
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	UserEntity user;
	User userDto;

	@Test
	void shouldSavedUserSuccessFully() {

		userDto = new User();
		userDto.setFirstName("Veena");
		userDto.setLastName("Anil Kumar");
		userDto.setUserName("veenaanilku");
		userDto.setPlainTextPassword("pass234568");

		given(userRepository.findByUserName(userDto.getUserName())).willReturn(null);
		given(userRepository.save(any(UserEntity.class))).willAnswer(invocation -> invocation.getArgument(0));
		// test
		User savedUser = userService.createUser(userDto);
		assertThat(savedUser).isNotNull();
		verify(userRepository).save(any(UserEntity.class));

	}

	@Test
	void shouldThrowErrorUserAlreadyExisting() {
		user = new UserEntity();
		user.setFirstName("Veena");
		user.setLastName("Anil Kumar");
		user.setUserName("veenaanilku");
		userDto = new User();
		userDto.setFirstName("Veena");
		userDto.setLastName("Anil Kumar");
		userDto.setUserName("veenaanilku");
		userDto.setPlainTextPassword("pass234568");

		given(userRepository.findByUserName(userDto.getUserName())).willReturn(user);

		// test
		assertThrows(UserAlreadyExistsException.class, () -> {
			userService.createUser(userDto);
		});
		verify(userRepository, never()).save(any(UserEntity.class));
	}

}
