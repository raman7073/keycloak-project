package com.fiftyfive.authserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class AuthServerApplication {
   @Bean
   public WebClient webClient(){
	  return WebClient.builder().build();
   }

	public static void main(String[] args) {
		SpringApplication.run(AuthServerApplication.class, args);
	}

}
