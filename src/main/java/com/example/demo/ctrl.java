package com.example.demo;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class ctrl {
	
	@GetMapping("/")
	public String greet() {
		return "Hello World";
	}
}
