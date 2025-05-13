package com.joblessfriend.jobfinder.auth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.joblessfriend.jobfinder.admin.controller.AdminController;
import com.joblessfriend.jobfinder.admin.service.AdminService;
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
	public String login() {
		logger.info(logTitleMsg);
		logger.info("==login==");
		
		return "auth/loginForm";
	}
	
	// 로그인 개인회원
	@PostMapping("/login/member")
	public String loginMember(String email, String password, HttpSession session, Model model) {
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
	
	// 로그인 기업회원
	@PostMapping("/login/company")
	public String loginCompany(String email, String password, HttpSession session, Model model) {
		logger.info(logTitleMsg);
		logger.info("login company! " + email + ", " + password);
		
		CompanyVo companyVo = companyService.companyExist(email, password);
		logger.info("companyVo: {}", companyVo);
		
		if(companyVo != null) {
			session.setAttribute("userLogin", companyVo);
			session.setAttribute("userType", "company");
			
			return "redirect:/";
		}else {
			return "auth/loginFailView";
		}
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
