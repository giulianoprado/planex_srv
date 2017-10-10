package edu.usp.planex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PlanexApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlanexApplication.class, args);
	}
}
