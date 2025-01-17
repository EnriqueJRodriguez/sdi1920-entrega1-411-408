package com.uniovi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SpringSecurityDialect securityDialect() {
		return new SpringSecurityDialect();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				.antMatchers("/css/**", "/img/**", "/script/**", "/", "/signup", "/login/**").permitAll()
				.antMatchers("/user/list").hasAnyAuthority("ROLE_STANDARD, ROLE_ADMIN")
				.antMatchers("/user/{id}/invitation/send").hasAnyAuthority("ROLE_STANDARD, ROLE_ADMIN")
				.antMatchers("/user/list/update").hasAnyAuthority("ROLE_STANDARD, ROLE_ADMIN")
				.antMatchers("/user/**").hasAnyAuthority("ROLE_ADMIN")	
				.antMatchers("/invitation/list").hasAnyAuthority("ROLE_STANDARD, ROLE_ADMIN")
				.antMatchers("/invitation/list/update").hasAnyAuthority("ROLE_STANDARD, ROLE_ADMIN")
				.antMatchers("/invitation/{id}/accept").hasAnyAuthority("ROLE_STANDARD, ROLE_ADMIN")
				.antMatchers("/invitation/**").hasAnyAuthority("ROLE_ADMIN")	
				.antMatchers("/friend/list").hasAnyAuthority("ROLE_STANDARD, ROLE_ADMIN")
				.antMatchers("/friend/**").hasAnyAuthority("ROLE_ADMIN")
				.antMatchers("/publication/list").hasAnyAuthority("ROLE_STANDARD, ROLE_ADMIN")
				.antMatchers("/publication/add").hasAnyAuthority("ROLE_STANDARD, ROLE_ADMIN")
				.antMatchers("/publication/**").hasAnyAuthority("ROLE_ADMIN")
				.anyRequest().authenticated().and().formLogin()
				.loginPage("/login").permitAll().defaultSuccessUrl("/user/list").and().logout().permitAll();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
