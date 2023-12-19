package com.sparta.plus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class PlusApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlusApplication.class, args);
	}

}
