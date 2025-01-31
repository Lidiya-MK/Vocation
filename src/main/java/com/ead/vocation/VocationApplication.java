package com.ead.vocation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class VocationApplication {
	public static void main(String[] args) {
		SpringApplication.run(VocationApplication.class, args);
	}
}

