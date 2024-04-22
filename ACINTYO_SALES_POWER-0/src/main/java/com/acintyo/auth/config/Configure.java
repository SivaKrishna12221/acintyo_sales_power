package com.acintyo.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
@PropertySource("classpath:/hardcode-messages.properties")
public class Configure {

	    @Bean
	    public ReloadableResourceBundleMessageSource messageSource() {
	        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
	        messageSource.setBasename("classpath:validation-messages");
	        messageSource.setDefaultEncoding("UTF-8");
	        messageSource.setCacheSeconds(10); // Reload messages every 10 seconds
	        return messageSource;
	    }
	  
}
