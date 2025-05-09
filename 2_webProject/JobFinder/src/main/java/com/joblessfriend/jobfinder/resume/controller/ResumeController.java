package com.joblessfriend.jobfinder.resume.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResumeController {
	
	@GetMapping("/home")
	public String home() {
		return "resume/resumeView";
	}
	
	@GetMapping("/inventory")
	public String inventory() {
		return "resume/resumeManagementView";
	}
}
