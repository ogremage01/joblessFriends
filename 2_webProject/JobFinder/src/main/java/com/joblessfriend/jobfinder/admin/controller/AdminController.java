package com.joblessfriend.jobfinder.admin.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.joblessfriend.jobfinder.admin.domain.AdminVo;


import jakarta.servlet.http.HttpSession;

@RequestMapping("/admin")
@Controller
public class AdminController {
	
	private Logger logger = LoggerFactory.getLogger(AdminController.class);
	private final String logTitleMsg = "==Admin control==";
	
//	@Autowired
//	private AdminService adminService;
	
	@GetMapping("/login")
	public String login(Model model) {
		logger.info(logTitleMsg);
		logger.info("login");
		
		return "/admin/auth/adminLogin";
	}
	
//	@PostMapping("/login")
//	public String getlogin(String account, String password, HttpSession session, Model model) {
//		logger.info(logTitleMsg);
//		logger.info("login!" + account + ", " + password);
//
//		AdminVo adminVo = adminService.adminExist(account, password);
//
//		if(adminVo != null) {
//			session.setAttribute("admin", adminVo);
//
//			return "redirect:/admin/adminMain";
//		}else {
//			return "/admin/auth/adminLoginFall";
//		}
//
//
//	}
//
	
	
	@GetMapping("/logout")
	public String logout(HttpSession session, Model model) {
		logger.info(logTitleMsg);
		logger.info("login");
		
		session.invalidate();
		
		return "redirect:/admin/login";
	}
	
	@GetMapping("/main")
	public String main(Model model) {
		logger.info(logTitleMsg);
		logger.info("login");
		
		return "/admin/adminMain";
	}
	

}
