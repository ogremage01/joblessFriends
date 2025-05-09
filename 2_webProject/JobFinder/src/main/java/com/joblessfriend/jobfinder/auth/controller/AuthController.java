package com.joblessfriend.jobfinder.auth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.joblessfriend.jobfinder.admin.controller.AdminController;
import com.joblessfriend.jobfinder.admin.service.AdminService;
import com.joblessfriend.jobfinder.member.domain.MemberVo;
import com.joblessfriend.jobfinder.member.service.MemberService;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/auth")
@Controller
public class AuthController {
	
	private Logger logger = LoggerFactory.getLogger(AuthController.class);
	private final String logTitleMsg = "==Auth control==";
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/signup")
	public String signup() {
		logger.info(logTitleMsg);
		logger.info("==signup==");
		
		return "auth/signUpForm";
	}
	
	@GetMapping("/login")
	public String login() {
		logger.info(logTitleMsg);
		logger.info("==login==");
		
		return "auth/loginForm";
	}
	
	@PostMapping("/login/member")
	public String getLogin(String email, String password, HttpSession session, Model model) {
		logger.info(logTitleMsg);
		logger.info("login Member! " + email + ", " + password);
		
		MemberVo memberVo = memberService.memberExist(email, password);
		logger.info("memberVo: {}", memberVo);
		
		if(memberVo != null) {
			session.setAttribute("userLogin", memberVo);
			session.setAttribute("userType", "member");
			
			return "redirect:/";
		}else {
			return "auth/loginFailView";
		}
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session, Model model) {
		logger.info(logTitleMsg);
		logger.info("logout");
		
		session.invalidate();
		
		return "redirect:/";
	}

}
