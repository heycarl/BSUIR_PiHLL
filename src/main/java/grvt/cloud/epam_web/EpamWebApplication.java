package grvt.cloud.epam_web;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EpamWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(EpamWebApplication.class, args);
	}
	@Bean
	public OpenAPI triangleOpenAPI() {
		return new OpenAPI()
				.info(new Info().title("EPAM BSUIR labs")
						.description("Triangle solver")
						.version("v0.0.1"));
	}
}
