package grvt.cloud.epam_web;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class EpamWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(EpamWebApplication.class, args);
	}

}
