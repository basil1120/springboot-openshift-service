package com.sprintel.main.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprintel.main.entities.Status;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1")
public class CommonController {
	
	private static final Logger logger = LoggerFactory.getLogger(CommonController.class);
	private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	

	@Value("${microservices.department.url}")
	private String URL;

	@GetMapping("/status")
	public ResponseEntity<Status> getAppStatus(){
		Status status = new Status();
		LocalDateTime now = LocalDateTime.now();
		ObjectMapper mapper = new ObjectMapper();
		try {
			status.setStatusCode("000");
			status.setStatusDescription("Success");
			status.setStatusDateTime(dtf.format(now));
			status.setStatusVersion("1.0.0");
			String responseAsJson = mapper.writeValueAsString(status);
			logger.info("SpringBoot+OpenShit:::RequestDateTime[{}]:::Payload[{}]",dtf.format(now), responseAsJson);
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		return ResponseEntity.ok().body(status);
	}
	
	@RequestMapping(value = "/status", method = RequestMethod.POST, produces = { "application/json"})
	public ResponseEntity<Status> postAppStatus(){
		Status status = new Status();
		LocalDateTime now = LocalDateTime.now();
		ObjectMapper mapper = new ObjectMapper();
		try {
			
			String responseAsJson = null;
			
		    RestTemplate restTemplate = new RestTemplate();
		 
		    ResponseEntity<Status> response = restTemplate.getForEntity(URL+ "/status" , Status.class);
			
		    if(response.getStatusCodeValue() == 200) {
				status.setStatusCode("200");
				status.setStatusDescription("Success");
				status.setStatusDateTime(dtf.format(now));
				status.setStatusVersion("1.0.0");
		    }else {
				status.setStatusCode("500");
				status.setStatusDescription("Failed");
				status.setStatusDateTime(dtf.format(now));
				status.setStatusVersion("1.0.0");
		    }
		    responseAsJson = mapper.writeValueAsString(status);
			logger.info("SpringBoot+OpenShit:::RequestDateTime[{}]:::Payload[{}]",dtf.format(now), responseAsJson);
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		return ResponseEntity.ok().body(status);
	}
}
