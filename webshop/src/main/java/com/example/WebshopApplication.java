package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebshopApplication {

	public static void main(String[] args) {
		System.out.println("Chạy vào main rồi!"); // <-- phải nằm trong hàm main
		SpringApplication.run(WebshopApplication.class, args);
	}
}
