package com.nokia.files;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.nokia.files.entity.File;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@ComponentScan({ "com.nokia.files.*" })

@EnableJpaRepositories
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@EnableSwagger2
public class FileServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileServicesApplication.class, args);
	}
	
	@Bean
	public Docket fileApi()
	{
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.nokia.files.controller"))
				.build();
	}



}
