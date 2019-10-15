package com.funtl.hello.spring.cloud.service.admin.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

	@Value("${server.port}")
	private String port;
	
	@GetMapping(value="hi")
	public String sayHi(String message) {
		return String.format("Hi you message is:%s port is : %s", message, port);
	}
}
