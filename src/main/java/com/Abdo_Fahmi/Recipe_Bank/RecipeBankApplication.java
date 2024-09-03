package com.Abdo_Fahmi.Recipe_Bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing // used for the @CreatedAt & @LastModified annotations
public class RecipeBankApplication {
	public static void main(String[] args) {
		SpringApplication.run(RecipeBankApplication.class, args);
	}
}
