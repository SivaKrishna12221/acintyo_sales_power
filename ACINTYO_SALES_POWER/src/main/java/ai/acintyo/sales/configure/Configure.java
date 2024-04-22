package ai.acintyo.sales.configure;

import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

@Configuration
public class Configure {

	@Bean
  	public ReloadableResourceBundleMessageSource createRRBMS()
	{
	
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
	    messageSource.addBasenames("classpath:validation");
	    messageSource.setDefaultEncoding("UTF-8");
	    messageSource.setCacheSeconds(10);
        return messageSource;
	    
	}
	 @Bean 
	  public Jackson2ObjectMapperBuilder createJackson2ObjectMapperBuilder()
	  {
		  return new Jackson2ObjectMapperBuilder().serializers(new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss:nn")))
				  .deserializers(new LocalTimeDeserializer(DateTimeFormatter.ofPattern("HH:mm:ss:nn")));
	  }
}
