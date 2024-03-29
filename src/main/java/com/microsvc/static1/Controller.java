package com.microsvc.static1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.json.JSONObject;
import org.springframework.http.MediaType;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.java.Log;

@RestController
@CrossOrigin(origins = "*")
@Log
@RequestMapping("/microsvc-static-1")




public class Controller {


private final WebClient webClient;
private final WebClient webClient2;
private final ApplicationEnvironmentProperties props;

private final ArrayList<Future<?>> lfutrs= new ArrayList<Future<?>>() ;

//@Autowired
//private OAuth2AuthorizedClientService authorizedClientService;

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
	
	@GetMapping("/load")
	public String load() {
		String returnStr = null;
		StringBuffer abcd=new StringBuffer();
		try {
			log.log(Level.INFO, "microsvc-static-1 : load");
			int itrLimit=Integer.parseInt(props.getEnv().getItrLimit());
			log.log(Level.INFO, "microsvc-static-1 : itrLimit"+itrLimit);

			List<Integer> strList= new ArrayList<>();
			for(int i=0;i<itrLimit;i++) {
				abcd.append(i);
				strList.add(i);
				//System.err.println(abcd);
			}
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
	
	//secure service2 with kong JWT/OIDC?
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
	
	

	@GetMapping("/inspect")
	public String inspect(
			//OAuth2AuthenticationToken authentication
			@RequestHeader Map<String, String> headers
			) {
		String returnStr = null;

		try {
			log.log(Level.INFO, "microsvc-static-1 : inspect");
		    headers.forEach((key, value) -> {
		        log.info(String.format("Header '%s' = %s", key, value));
		    });
			
//	        OAuth2AuthorizedClient authorizedClient = this.getAuthorizedClient(authentication);
//
//	        log.log(Level.INFO,authorizedClient.getAccessToken().getTokenValue();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.log(Level.SEVERE," ",e);
		}
		
		return returnStr;
		
	}
	
	

	@GetMapping("/scheduler")
	public String scheduler() {
		String returnStr = null;

		try {
			log.log(Level.INFO, "microsvc-static-1 : scheduler");

			ArrayList<Future<?>> rmFut= new ArrayList<Future<?>>() ;
			log.log(Level.INFO, new StringBuffer().append("ASize of active: ").append(lfutrs.size()).toString());

			for(Future<?> lFutr :lfutrs) {
				//lFutr.cancel(true);
				
				if(lFutr.isDone())
					rmFut.add(lFutr);
			}
			
			lfutrs.removeAll(rmFut);
			
			log.log(Level.INFO, new StringBuffer().append("BSize of active: ").append(lfutrs.size()).toString());
			int itrLimit=Integer.parseInt(props.getEnv().getItrLimit());
			int initalDelay=Integer.parseInt(props.getEnv().getItrLimit());
			int delay=Integer.parseInt(props.getEnv().getDelay());
			int trds=Integer.parseInt(props.getEnv().getTrds());
			ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(trds);

			
	        ExecuteCustomTask task1 = new ExecuteCustomTask("A"+Math.random(),itrLimit);
	        
	        Future<?> f1 = scheduler.scheduleWithFixedDelay(task1, initalDelay, delay, TimeUnit.SECONDS);
	        lfutrs.add(f1);
	        
	        //scheduler.scheduleWithFixedDelay(task2, 3, 15, TimeUnit.SECONDS);
	        //scheduler.scheduleWithFixedDelay(task3, 3, 20, TimeUnit.SECONDS);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.log(Level.SEVERE," ",e);
		}
		
		return "started";
		
	}

//    private OAuth2AuthorizedClient getAuthorizedClient(OAuth2AuthenticationToken authentication) {
//        return this.authorizedClientService.loadAuthorizedClient(
//            authentication.getAuthorizedClientRegistrationId(), authentication.getName());
//    }
}
