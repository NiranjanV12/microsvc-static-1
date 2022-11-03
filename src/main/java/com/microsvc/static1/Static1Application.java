package com.microsvc.static1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@SpringBootApplication
@EnableEncryptableProperties
public class Static1Application {

	public static void main(String[] args) {
		SpringApplication.run(Static1Application.class, args);
	}

}
