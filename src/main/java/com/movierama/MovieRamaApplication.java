package com.movierama;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "MovieRama API", version = "1.0", description = "API for MovieRama application"))
public class MovieRamaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieRamaApplication.class, args);
	}
}