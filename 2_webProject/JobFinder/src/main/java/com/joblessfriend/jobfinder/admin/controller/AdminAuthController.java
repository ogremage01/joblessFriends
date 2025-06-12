package com.joblessfriend.jobfinder.admin.controller;

import java.util.Map;

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
import com.joblessfriend.jobfinder.admin.service.AdminDashboardService;
import com.joblessfriend.jobfinder.util.ChartVo;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/admin") // "/admin" 경로에 대한 요청을 처리하는 컨트롤러
@Controller
public class AdminAuthController {

	private Logger logger = LoggerFactory.getLogger(AdminAuthController.class); // 로그 출력을 위한 Logger
	private final String logTitleMsg = "==Admin control==";

	@Autowired
	private AdminAuthService adminService; // 어드민 인증 서비스 주입
	
	@Autowired
	private AdminDashboardService adminDashboardService; // 어드민 대시보드 서비스 주입

	/**
	 * /admin 기본 경로 접근 시 세션 확인 후 main 또는 login 페이지로 리다이렉트
	 */
	@GetMapping("")
	public String base(Model model, HttpSession session) {
		// 세션에 어드민 정보가 있으면 메인으로, 없으면 로그인 페이지로 이동
		if(session.getAttribute("userLogin") instanceof AdminVo) {
			return "redirect:/admin/main";
		}else {
			return "redirect:/admin/login";
		}
	}

	/**
	 * 어드민 로그인 페이지 이동
	 */
	@GetMapping("/login")
	public String login(Model model) {
		logger.info("어드민 login 페이지로 이동");
		return "admin/auth/adminLoginFormView"; // 로그인 폼 뷰 반환
	}

	/**
	 * 어드민 로그인 처리
	 */
	@PostMapping("/login")
	public String getlogin(String adminId, String password, HttpSession session, Model model) {
		logger.info(logTitleMsg);
		logger.info("login! " + adminId + ", " + password);

		// 입력된 ID와 비밀번호로 어드민 존재 여부 확인
		AdminVo adminVo = adminService.adminExist(adminId, password);

		if (adminVo != null) {
			// 로그인 성공 → 세션에 어드민 정보 저장 후 메인으로 이동
			session.setAttribute("userLogin", adminVo);
			session.setAttribute("userType", "admin");
			return "redirect:/admin/main";
		} else {
			// 로그인 실패 → 실패 페이지로 이동
			return "admin/auth/adminLoginFallView";
		}
	}

	/**
	 * 어드민 로그아웃 처리
	 */
	@GetMapping("/logout")
	public String logout(HttpSession session, Model model) {
		logger.info("admin Logout");

		// 세션 무효화 (로그아웃)
		session.invalidate();

		// 로그인 페이지로 리다이렉트
		return "redirect:/admin/login";
	}

	/**
	 * 어드민 메인 페이지
	 */
	@GetMapping("/main")
	public String main(Model model) {
		logger.info("go to admin main");
		
		// 대시보드 통계 데이터 조회 및 모델에 추가
		Map<String, ChartVo> dashboardStats = adminDashboardService.getDashboardStatistics();
		
		model.addAllAttributes(dashboardStats);
		
		return "admin/adminMainView"; // 어드민 메인 뷰 반환
	}
}
