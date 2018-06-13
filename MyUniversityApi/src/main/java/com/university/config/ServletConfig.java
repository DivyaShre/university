package com.university.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.university.servlet.FileDownloadServlet;
import com.university.servlet.FileUploadServlet;

@Configuration
public class ServletConfig {
	
	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		return new ServletRegistrationBean(new FileUploadServlet(), "/upload/*");
	}
	
	@Bean
	public ServletRegistrationBean servletRegistrationBean2() {
		return new ServletRegistrationBean(new FileDownloadServlet(), "/download/*");
	}
}
