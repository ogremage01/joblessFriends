package com.joblessfriend.jobfinder.auth.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



import com.joblessfriend.jobfinder.admin.service.AdminAuthService;
import com.joblessfriend.jobfinder.company.domain.CompanyVo;
import com.joblessfriend.jobfinder.company.service.CompanyService;
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
	@Autowired
	private CompanyService companyService;
	
	// 회원가입 뷰
	@GetMapping("/signup")
	public String signup() {
		logger.info(logTitleMsg);
		logger.info("==signup==");
		
		return "auth/signUpForm";
	}
	
	// 이메일 중복확인 개인회원
	@PostMapping("/check/member/email")
	public ResponseEntity<String> valiCheckEmailMember(@RequestParam String email) {
		logger.info(logTitleMsg);
		logger.info("==valiCheckEmail Member== email: {}", email);
		
		MemberVo memberVo = memberService.memberEmailExist(email);
		String checkStr = "";
		if (memberVo == null) {
			checkStr = "사용가능";
		}else {
			checkStr = "중복";
		}
		
		return ResponseEntity.ok(checkStr);
	}
	
	// 이메일 중복확인 기업회원
	@PostMapping("/check/company/email")
	public ResponseEntity<String> valiCheckEmailCompany(@RequestParam String email) {
		logger.info(logTitleMsg);
		logger.info("==valiCheckEmail Company== email: {}", email);
		
		CompanyVo companyVo = companyService.companyEmailExist(email);
		String checkStr = "";
		if (companyVo == null) {
			checkStr = "사용가능";
		}else {
			checkStr = "중복";
		}
		
		return ResponseEntity.ok(checkStr);
	}
	
	// 회원가입 개인회원
	@PostMapping("/signup/member")
	public String signupMember(String email, String password, Model model) {
		logger.info(logTitleMsg);
		logger.info("signup Member! " + email + ", " + password);
		
		memberService.memberInsertOne(email, password);
		
		return "auth/signUpSuccessView";
	}
	
	// 회원가입 기업회원
	@PostMapping("/signup/company")
	public String signupCompany(CompanyVo companyVo, Model model) {
		logger.info(logTitleMsg);
		logger.info("signup Member! companyVo: {}" + companyVo);
		
		companyService.companyInsertOne(companyVo);
		
		return "auth/signUpSuccessView";
	}
	
	// 로그인 뷰
	@GetMapping("/login")
	public String login(Authentication authentication) {
		logger.info(logTitleMsg);
		logger.info("==login==");
		if (authentication != null && authentication.isAuthenticated()) {
			//.isAuthenticated()는 사용자가 인증된 상태임을 확인하는 조건식 (익명이 아니면 true 리턴)
			return "redirect:/";
		}
		
		return "auth/loginForm";
	}
	
	// 로그인 개인회원
	@PostMapping("/login/member")
	public ResponseEntity<Map<String, Object>> loginMember(@RequestParam String email, @RequestParam String password, HttpSession session) {
		logger.info(logTitleMsg);
		logger.info("login Member! " + email + ", " + password);
		
		String message = "이메일 혹은 비밀번호가 다릅니다.";
		
		MemberVo memberVo = memberService.memberExist(email, password);
		logger.info("memberVo: {}", memberVo);
		
		Map<String, Object> result = new HashMap<>();
		
		if(memberVo != null) {
			session.setAttribute("userLogin", memberVo);
			session.setAttribute("userType", "member");
			result.put("success", true);
		}else {
			result.put("success", false);
	        result.put("message", message);
		}
		
		return ResponseEntity.ok(result);
	}
	
	// 로그인 기업회원
	@PostMapping("/login/company")
	public ResponseEntity<Map<String, Object>> loginCompany(@RequestParam String email, @RequestParam String password, HttpSession session) {
		logger.info(logTitleMsg);
		logger.info("login company! " + email + ", " + password);
		
		String message = "이메일 혹은 비밀번호가 다릅니다.";
		
		CompanyVo companyVo = companyService.companyExist(email, password);
		logger.info("companyVo: {}", companyVo);
		
		Map<String, Object> result = new HashMap<>();
		
		if(companyVo != null) {
			session.setAttribute("userLogin", companyVo);
			session.setAttribute("userType", "company");
			result.put("success", true);
		}else {
			result.put("success", false);
	        result.put("message", message);
		}
		return ResponseEntity.ok(result);
	}
	
	// 로그아웃
	@GetMapping("/logout")
	public String logout(HttpSession session, Model model) {
		logger.info(logTitleMsg);
		logger.info("==logout==");
		
		session.invalidate();
		
		return "redirect:/";
	}
	
	// 계정찾기 뷰
	@GetMapping("/find")
	public String find() {
		logger.info(logTitleMsg);
		logger.info("==find==");
		
		return "auth/findAccountForm";
	}
	
	// 개인회원 비밀번호 찾기
	@PostMapping("/find/memberPwd")
	public String findMemberPwd() {
		logger.info(logTitleMsg);
		logger.info("findMemberPwd! ");
		
		return "redirect:auth/login";
	}
	
	// 기업회원 비밀번호 찾기
	@PostMapping("/find/companyPwd")
	public String findCompanyPwd() {
		logger.info(logTitleMsg);
		logger.info("findCompanyPwd! ");
		
		return "redirect:auth/login";
	}
	
	// 기업회원 아이디 찾기
	@PostMapping("/find/companyId")
	public ResponseEntity<String> findCompanyId(@RequestParam String representative, @RequestParam String brn) {
		logger.info(logTitleMsg);
		logger.info("findCompanyId! representative: {}, brn: {}", representative, brn);
		
		CompanyVo companyVo = companyService.companyFindId(representative, brn);
		String email = companyVo.getEmail();
		
		return ResponseEntity.ok(email);
	}
	

}
