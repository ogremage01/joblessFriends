package com.joblessfriend.jobfinder.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.joblessfriend.jobfinder.community.service.CommunityService;


@RequestMapping("/admin/community/post") 
@Controller
public class AdminCommunityPostController {
	
	private Logger logger = LoggerFactory.getLogger(AdminCommunityPostController.class); // 로그 출력용

	@Autowired
	private CommunityService communityService;
	
	@GetMapping("")
	public String communityPostSelectList() {
		
		return "admin/community/postView";
	}


}
