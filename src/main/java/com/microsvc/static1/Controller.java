package com.microsvc.static1;

import java.util.logging.Level;

import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

import lombok.extern.java.Log;

@RestController
@CrossOrigin(origins = "*")
@Log
@RequestMapping("/microsvc-static-1")

public class Controller {


private final WebClient webClient;
private final WebClient webClient2;


private final ApplicationEnvironmentProperties props;


public Controller(WebClient.Builder webClientBuilder, ApplicationEnvironmentProperties props) {
	this.props = props;
	this.webClient = webClientBuilder.baseUrl(props.getEnv().getServUrl1()).build();
	this.webClient2 = webClientBuilder.baseUrl(props.getEnv().getAt_Url()).build();

}

	@GetMapping("/health")
	public String health() {
		String returnStr = null;

		try {
			log.log(Level.INFO, "microsvc-static-1 : healthy");
			returnStr= new StringBuffer("static-1 : healthy > color: ").append(props.getEnv().getColor()).toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.log(Level.SEVERE," ",e);
		}
		
		return returnStr;
		
	}


	@GetMapping("/callOtherService")
	public String callOtherService() {
		log.log(Level.INFO, "callOtherService");
		String returnStr = "callOtherService: ";
		try {
			
			returnStr+=webClient.get()
	        .uri("/health")
	        .retrieve()
	        .bodyToMono(String.class).block().toString();
			log.log(Level.INFO, "callOtherService: "+returnStr);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.log(Level.SEVERE," ",e);
		}
		return returnStr;
	}
	
	
	@GetMapping("/callOtherServiceJWT")
	public String callOtherServiceJWT() {
		log.log(Level.INFO, "callOtherServiceJWT");
		String returnStr = "callOtherServiceJWT: ";
		try {
			JSONObject jsonObj = new JSONObject(
					webClient2.post()			
			        .headers(h -> h.setContentType(MediaType.APPLICATION_FORM_URLENCODED))
			        .body(BodyInserters.fromFormData("username", props.getEnv().getAt_username())
			                .with("password", props.getEnv().getAt_password())
			                .with("grant_type", props.getEnv().getAt_grant_type())
			                 .with("client_id", props.getEnv().getAt_client_id()))
			        .retrieve()
			        .bodyToMono(String.class).block()
					);
			
			
			returnStr+=webClient.get()
	        .uri("/health")
	        .headers(h -> h.setBearerAuth(jsonObj.get("access_token").toString()))
	        .retrieve()
	        .bodyToMono(String.class).block().toString();
			log.log(Level.INFO, "callOtherService: "+returnStr);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.log(Level.SEVERE," ",e);
		}
		return returnStr;
	}
}
