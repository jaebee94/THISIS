package com.web.curation.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
	public Docket postsApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("Web")
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.web.curation.controller"))
				.paths(PathSelectors.ant("/**"))
				.build();
	}
 

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("THIS IS DISEASE API")
				.description("THISIS API Reference for Developers")
				.termsOfServiceUrl("22")
				.license("License")
				.licenseUrl("cj1081@naver.com").version("1.0").build();
	}
}
