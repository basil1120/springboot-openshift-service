package com.sprintel.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.TagsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableFeignClients
@EnableSwagger2
public class HandlerApplication {

	String version="1.0.0";
	
	public static void main(String[] args) {
		SpringApplication.run(HandlerApplication.class, args);
	}
	
	@Bean
	public Docket swaggerApi() {
	    return new Docket(DocumentationType.SWAGGER_2)
	        .select()
	            .apis(RequestHandlerSelectors.basePackage("com.sprintel.main.controllers"))
	            .paths(PathSelectors.any())
	        .build()
	        .apiInfo(new ApiInfoBuilder().version(version).title("Department API").description("Documentation Department API v" + version).build());
	}
	
	@Bean
	UiConfiguration uiConfig() {
		return new UiConfiguration(Boolean.TRUE, Boolean.FALSE, 1, 1, ModelRendering.MODEL, Boolean.FALSE, DocExpansion.LIST, Boolean.FALSE, null, OperationsSorter.ALPHA, Boolean.FALSE, TagsSorter.ALPHA, UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS, null);
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
	    return builder
	            .setConnectTimeout(3000)
	            .setReadTimeout(3000)
	            .build();
	}

}
