package com.joblessfriend.jobfinder.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/auth")
@Controller
public class AuthController {
	
	@GetMapping("/signup")
	public String signup() {
		
	  return "auth/signUpForm";
	}
	
	@GetMapping("/login")
	public String login() {
		
	  return "auth/loginForm";
	}

}
