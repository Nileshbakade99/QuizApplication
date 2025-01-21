package com.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	    @Override
	    public void addInterceptors(InterceptorRegistry registry) {
//	        registry.addInterceptor(new TimeCheckingHandler())
//	                .addPathPatterns("/user/javaquiz");  // Only apply the handler to quiz and exam paths
	    }
}
