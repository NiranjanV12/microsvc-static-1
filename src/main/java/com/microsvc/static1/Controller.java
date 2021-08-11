package com.microsvc.static1;

import java.util.logging.Level;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.java.Log;

@RestController
@CrossOrigin(origins = "*")
@Log
@RequestMapping("/microsvc-static-1")

public class Controller {


private final WebClient webClient;


private final ApplicationEnvironmentProperties props;


public Controller(WebClient.Builder webClientBuilder, ApplicationEnvironmentProperties props) {
	this.props = props;
	this.webClient = webClientBuilder.baseUrl(props.getEnv().getServUrl1()).build();
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

}
