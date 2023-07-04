package com.example.plataforma_cerebritos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
public class PlataformaCerebritosApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlataformaCerebritosApplication.class, args);
	}

}
