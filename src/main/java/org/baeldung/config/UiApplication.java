package org.baeldung.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextListener;

@Controller
@SpringBootApplication
public class UiApplication extends SpringBootServletInitializer {

	OAuth2RestTemplate template;
	
	@Bean
	public OAuth2RestTemplate createRestTemplate(OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails,
			OAuth2ClientContext clientContext) {
		this.template = new OAuth2RestTemplate(oAuth2ProtectedResourceDetails, clientContext);
		return this.template;
	}

	@Bean
	public RequestContextListener requestContextListener() {
		return new RequestContextListener();
	}

	public static void main(String[] args) {
		SpringApplication.run(UiApplication.class, args);
	}
	
	
	@RequestMapping(value = "/message", method = RequestMethod.GET)
	public String getMessageFromSecuredService(){
        ResponseEntity<String> entity = template.getForEntity("http://localhost:9006/user/detail", String.class);
        System.out.println("reponse:: " + entity.getBody());
        return "securedPage";
    }
}