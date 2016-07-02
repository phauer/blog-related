package de.philipphauer.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class VersioningContinuousDeliveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(VersioningContinuousDeliveryApplication.class, args);
	}

	@RequestMapping("/")
	public String ping() {
		return "Pong";
	}
}
