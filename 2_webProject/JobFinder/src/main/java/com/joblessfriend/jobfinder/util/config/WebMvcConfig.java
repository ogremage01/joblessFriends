
 package com.joblessfriend.jobfinder.util.config;
 
 import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
 import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
 
 @Configuration 
 public class WebMvcConfig implements WebMvcConfigurer {
 
	 @Override 
	 public void addResourceHandlers(ResourceHandlerRegistry registry) {
	 // /profile/** 로 요청이 들어오면 C:/upload/profile/ 에서 파일을 찾아 응답
		registry.addResourceHandler("/profile/**")
		.addResourceLocations("file:///C:/upload/profile/");  
		registry.addResourceHandler("/image/**")
		.addResourceLocations("file:///D:/files/");
	 }
	 
 }
 

