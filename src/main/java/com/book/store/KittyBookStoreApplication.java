package com.book.store;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;

@SpringBootApplication
public class KittyBookStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(KittyBookStoreApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			System.out.println("Let's inspect the beans provided by Spring Boot:");

//			String[] beanNames = ctx.getBeanDefinitionNames();
//			Arrays.sort(beanNames);
//			for (String beanName : beanNames) {
//				System.out.println(beanName);
//			}

		};
	}

	@Bean
	public CommandLineRunner testDb(JdbcTemplate jdbcTemplate) {
		return args -> {
			try {
				Integer count = jdbcTemplate.queryForObject("SELECT 2", Integer.class);
				System.out.println("✅ Connected to MySQL successfully! Book count = " + count);
			} catch (Exception e) {
				System.err.println("❌ Failed to connect to MySQL: " + e.getMessage());
			}
		};
	}


}
