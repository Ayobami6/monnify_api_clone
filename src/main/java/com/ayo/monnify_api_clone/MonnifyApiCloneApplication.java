package com.ayo.monnify_api_clone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class MonnifyApiCloneApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();
		
		SpringApplication.run(MonnifyApiCloneApplication.class, args);
	}

}
