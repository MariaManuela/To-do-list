package com.main.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for views
 *
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
	/**
	 * Sets views
	 */
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/index").setViewName("index");
		registry.addViewController("/").setViewName("index");
		registry.addViewController("/editTask").setViewName("editTask");
		
	}

}
