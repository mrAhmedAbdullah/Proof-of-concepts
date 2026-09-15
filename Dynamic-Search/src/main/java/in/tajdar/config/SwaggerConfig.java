package in.tajdar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {
	@Bean
	public OpenAPI swaggerApi() {
		return new OpenAPI().info(new Info().title("tajdar.in")
				.description("spring boot rest api, generating excel, pdf dynamic-search [search with multiple fields] ")
				.version("version 0.0.1 snapshot").license(new License().name("Apache 2.0").url("spring.io:")));
	}
}
