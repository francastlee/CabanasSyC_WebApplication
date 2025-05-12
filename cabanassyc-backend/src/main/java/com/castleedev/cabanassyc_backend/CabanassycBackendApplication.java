package com.castleedev.cabanassyc_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class CabanassycBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CabanassycBackendApplication.class, args);
	}

}
