package com.funtl.hello.spring.cloud.web.admin.ribbon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.funtl.hello.spring.cloud.web.admin.ribbon.service.AdminService;

@RestController
public class AdminController {
	@Autowired
	private AdminService adminService;
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	@GetMapping(value="hi")
	public String sayHi(String message) {
		return adminService.sayHi(message);
	}
}
