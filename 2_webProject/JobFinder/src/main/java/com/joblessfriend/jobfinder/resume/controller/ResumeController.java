package com.joblessfriend.jobfinder.resume.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/resume")
public class ResumeController {
	
	@GetMapping("/home")
	public String home() {
		return "resume/resumeView";
	}
	
	@GetMapping("/management")
	public String resumeManagement() {
		return "resume/resumeManagementView";
	}
}
