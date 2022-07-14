package com.etnetera.jslibrary;

import com.etnetera.jslibrary.domain.Framework;
import com.etnetera.jslibrary.repository.FrameworkRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class JslibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(JslibraryApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(FrameworkRepository repository){
		return args -> {
			// Reset collection
			if (!repository.findAll().isEmpty()){
				repository.deleteAll();
			}

			// Load collection with test data
			Framework fwReactJs = new Framework(
					"ReactJs",
					List.of("0.0.1", "0.0.2", "0.0.3")
			);

			Framework fwVueJs = new Framework(
					"VueJs",
					List.of("0.0.1", "0.0.2", "0.0.3")
			);

			Framework fwAngular = new Framework(
					"Angular",
					List.of("0.0.1", "0.0.2", "0.0.3")
			);

			repository.saveAll(List.of(fwReactJs, fwVueJs, fwAngular));

		};
	}
}
