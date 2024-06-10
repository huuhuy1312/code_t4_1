package com.example.web_spring_security_demo;

//import com.example.web_spring_security_demo.config.TwilioConfig;
import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties
@EnableScheduling
public class WebSpringSecurityDemoApplication {

//	@Autowired
//	private TwilioConfig twilioConfig;

	public static void main(String[] args) {
		SpringApplication.run(WebSpringSecurityDemoApplication.class, args);
	}

//	@PostConstruct
//	public void setup()
//	{
//		Twilio.init(twilioConfig.getAccountSid(),twilioConfig.getAuthToken());
//	}

}
