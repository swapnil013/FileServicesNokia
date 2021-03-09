package com.nokia.files;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import com.nokia.files.entity.File;

@SpringBootApplication
@EntityScan("com.nokia.files.entity")

@EnableAutoConfiguration()
public class FileServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileServicesApplication.class, args);
	}


}
