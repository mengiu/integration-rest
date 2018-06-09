package com.bank.diwa0.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build().apiInfo(getApiInfo());
	}

	private static final String APP_NAME = "diwa0-visage-integrazione-rest";
	private static final String APP_VERSION = "v1-20171218";

	public ApiInfo getApiInfo() {
		return new ApiInfo(APP_NAME, "", APP_VERSION, "", new Contact("","",""), "", "");
	}
}