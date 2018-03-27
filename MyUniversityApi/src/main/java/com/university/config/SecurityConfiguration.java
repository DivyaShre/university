/**
 * 
 */
package com.university.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/upload/**").antMatchers("/uploadmedia/**").antMatchers("/transcoder/decrypt/**").antMatchers("/download/**");// .antMatchers("/course/cloud/server/get/**").antMatchers("/site/download/**").antMatchers("/admin/download/**").antMatchers("/csc/download/**");
	}

}