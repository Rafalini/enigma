package org.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("org.app.model")
public class EnigmaApplication {
	public static void main(String[] args) {
		SpringApplication.run(EnigmaApplication.class, args);
	}
}