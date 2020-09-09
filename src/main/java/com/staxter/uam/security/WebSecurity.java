package com.staxter.uam.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.staxter.uam.service.UserService;

 /**
 * 
 *  @author Veena Anil
 *
 */
 
@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	private UserService userService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private Environment env;

	public WebSecurity(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder, Environment env) {

		this.userService = userService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.env = env;
	}

	
	/**
	 *Configure authentication request
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable();
		http.authorizeRequests().antMatchers("/**").permitAll().and().addFilter(getAuthenticationFilter());

	}

	
	/**
	 * Function to return the AuthenticationFilter object for configuring authentication request
	 * @return
	 * @throws Exception
	 */
	private AuthenticationFilter getAuthenticationFilter() throws Exception {

		AuthenticationFilter authenticationFilter = new AuthenticationFilter(userService, env, authenticationManager());
		// Setting custom User authentication url
		authenticationFilter.setFilterProcessesUrl(env.getProperty("login.url.path"));
		// authenticationFilter.setAuthenticationManager(authenticationManager());
		return authenticationFilter;
	}

	
	/**
	 * This configure method let Spring security know which service is used to load
	 * user details and which password encoder should be used
	 *
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
	}

}
