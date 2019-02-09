package com.storebooks.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;

import com.storebooks.service.UserSecurityService;


@Configurable
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private Environment env;
	
	@Autowired
	private UserSecurityService userSecurityService;
	
	private BCryptPasswordEncoder passwordEncooder(){
		return SecurityUtility.passwordEncoder();
	}
	
	private static final String[] PUBLIC_MATCHES = {
			"/css/**",
			"/js/**",
			"/image/**",
			"/book/**",
			"/user/**",
			"/bookList",
			"/bookDetail/**"
	};
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.csrf().disable().cors().disable().httpBasic().and().authorizeRequests()
		.antMatchers(PUBLIC_MATCHES).permitAll().anyRequest().authenticated();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncooder());
	}
	
	@Bean
	public HttpSessionStrategy httpSessionStrategy(){
		return new HeaderHttpSessionStrategy();
	}
}
