package com.joblessfriend.jobfinder.admin.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.joblessfriend.jobfinder.admin.domain.AdminVo;
import com.joblessfriend.jobfinder.admin.service.AdminService;
import com.joblessfriend.jobfinder.company.domain.CompanyVo;
import com.joblessfriend.jobfinder.company.service.CompanyService;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/admin")
@Controller
public class AdminController {

	private Logger logger = LoggerFactory.getLogger(AdminController.class);
	private final String logTitleMsg = "==Admin control==";

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private CompanyService companyService;

	@GetMapping("/login")
	public String login(Model model) {
		logger.info(logTitleMsg);
		logger.info("login");

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
		logger.info(logTitleMsg);
		logger.info("login");

		session.invalidate();

		return "redirect:/admin/login";
	}

	@GetMapping("/main")
	public String main(Model model) {
		logger.info(logTitleMsg);
		logger.info("login");

		return "/admin/adminMainView";
	}

	@GetMapping("/member/individual")
	public String member(Model model) {
		logger.info(logTitleMsg);
		logger.info("login");

		return "/admin/member/memberIndividualView";
	}

	@GetMapping("/member/company")
	public String memberCompany(Model model) {
		logger.info(logTitleMsg);
		logger.info("login");
		
		List<CompanyVo> companyList = companyService.companySelectList();
		model.addAttribute("companyList", companyList);


		return "/admin/member/memberCompanyView";
	}

}
