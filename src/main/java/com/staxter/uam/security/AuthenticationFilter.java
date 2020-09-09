package com.staxter.uam.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.staxter.uam.service.UserService;
import com.staxter.uam.ui.model.request.UserLogin;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * 
 * This class is user for authentication and to generate user
 *  token when a user login to application
 * @author Veena Anil
 *
 */
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private UserService userService;
	private Environment env;

	public AuthenticationFilter(UserService userService, Environment env, AuthenticationManager authenticationManager) {

		this.userService = userService;
		this.env = env;
		super.setAuthenticationManager(authenticationManager);
	}

	/**
	 * The attemptAuthenticationRequest method in
	 * UsernamePasswordAuthenticationFilter class to read userName and password from
	 * the request create UsernamePasswordAuthenticationToken and call authenticate
	 * method on Authentication Manager
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		UserLogin creds;
		try {

			creds = new ObjectMapper().readValue(request.getInputStream(), UserLogin.class);

			return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(creds.getUserName(),
					creds.getPassword(), new ArrayList<>()));
		} catch (IOException e) {
			logger.error("An exception occured in attemptAuthentication() method inside class AuthenticationFilter"
					+ e.getMessage());
			throw new RuntimeException(e);
		}

	}

	/**
	 * Spring framework call the successful authentication method by itself if the
	 * authentication is successfull.
	 * 
	 * This method will generate the JWT token with the userdetails and add the JWT
	 * token to the Http response header and return it back with Http response.
	 * 
	 * The client application then will be able to read JWT token from response and
	 * use this JWT token for subsequent request.
	 * 
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		String userName = ((User) authResult.getPrincipal()).getUsername();
		// Need the user id to generate JWt token hence getting the UserDto object with
		// the userName
		// from the authentication result.
		com.staxter.uam.dto.User userDto = userService.getUserDetailsbyUserName(userName);

		// Generate a secure JWT token
		String token = Jwts.builder().setSubject(userDto.getId())
				.setExpiration(
						new Date(System.currentTimeMillis() + Long.parseLong(env.getProperty("token.expiration.time"))))
				.signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret")).compact();

		// Add generated JWT token to response header and return back to client
		// application
		response.addHeader("token", token);
		response.addHeader("userId", userDto.getId());
	}

}
