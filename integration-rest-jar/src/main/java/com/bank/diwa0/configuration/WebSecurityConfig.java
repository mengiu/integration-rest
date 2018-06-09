package com.bank.diwa0.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String APPLICATION_REST_PREFIX_ROLE = AppProperties.APPLICATION_REST_PREFIX + "**";

	@Autowired
	private String allowedRoles;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.antMatchers(APPLICATION_REST_PREFIX_ROLE).access(allowedRoles)
				.and().csrf().disable();
		http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());
	}

	@Bean
	public AuthenticationEntryPoint authenticationEntryPoint() {
		return new LTPAAuthenticationEntryPoint();
	}
}