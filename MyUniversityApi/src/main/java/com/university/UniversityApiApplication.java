package com.university;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
public class UniversityApiApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(UniversityApiApplication.class, args);
	}
	
	 @Override
	  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		String baseLogPath = this.getClass().getClassLoader().getResource("").getPath();
		baseLogPath = baseLogPath.replace("%20", " ");
		baseLogPath = baseLogPath.substring(0, baseLogPath.indexOf("WEB-INF/classes/"));//
		return application.sources(UniversityApiApplication.class);
	  }
}
