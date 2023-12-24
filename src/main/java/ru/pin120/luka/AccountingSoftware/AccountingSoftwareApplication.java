package ru.pin120.luka.AccountingSoftware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class AccountingSoftwareApplication {
	public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/target/classes/static/uploads/";

	public static void main(String[] args) {
		try {
			Files.createDirectories(Paths.get(UPLOAD_DIRECTORY));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		SpringApplication.run(AccountingSoftwareApplication.class, args);
	}

}
