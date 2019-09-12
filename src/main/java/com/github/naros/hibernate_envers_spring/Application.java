package com.github.naros.hibernate_envers_spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.github.naros.hibernate_envers_spring.services.TestService;

/**
 * @author Chris Cranford
 */
@SpringBootApplication
public class Application implements CommandLineRunner
{
	/**
	 * Main entry point
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	private TestService testService;

	@Override
	public void run(String... args) throws Exception {
		// invoke the test service at startup
		//
		// Creates a transaction
		// Inserts new entity
		testService.saveEntity();
	}
}