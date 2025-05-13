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
import com.joblessfriend.jobfinder.admin.service.AdminAuthService;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/admin")
@Controller
public class AdminAuthController {



	private Logger logger = LoggerFactory.getLogger(AdminAuthController.class);
	private final String logTitleMsg = "==Admin control==";

	@Autowired
	private AdminAuthService adminService;	

	
	@GetMapping({"/",""})
	public String base(Model model, HttpSession session) {
		if(session.getAttribute("admin") != null) {
			return "redirect:/admin/main";
		}else {
			return "redirect:/admin/login";
		}
	}
	

	@GetMapping("/login")
	public String login(Model model) {
		logger.info("어드민login페이지로 이동");

		return "/admin/auth/adminLoginFormView";
	}

	@PostMapping("/login")
	public String getlogin(String adminId, String password, HttpSession session, Model model) {
		logger.info(logTitleMsg);
		logger.info("login!" + adminId + ", " + password);

		AdminVo adminVo = adminService.adminExist(adminId, password);

		if (adminVo != null) {
			session.setAttribute("admin", adminVo);

			return "redirect:/admin/main";
		} else {
			return "/admin/auth/adminLoginFallView";
		}

	}

	@GetMapping("/logout")
	public String logout(HttpSession session, Model model) {
		logger.info("admin Logout");

		session.invalidate();

		return "redirect:/admin/login";
	}

	@GetMapping("/main")
	public String main(Model model) {
		logger.info("go to admin main");

		return "/admin/adminMainView";
	}
	

}
