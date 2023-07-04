package ru.classicdev.typer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.classicdev.typer.storage.StorageProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class TyperApplication {

	public static void main(String[] args) {
		SpringApplication.run(TyperApplication.class, args);
	}

}
