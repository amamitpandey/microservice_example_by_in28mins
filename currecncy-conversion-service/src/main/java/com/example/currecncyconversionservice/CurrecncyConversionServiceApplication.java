package com.example.currecncyconversionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.example.currecncyconversionservice")
public class CurrecncyConversionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrecncyConversionServiceApplication.class, args);
	}

}
