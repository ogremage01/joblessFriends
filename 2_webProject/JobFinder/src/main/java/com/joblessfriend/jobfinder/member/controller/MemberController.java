package com.joblessfriend.jobfinder.member.controller;

import java.util.Map;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.joblessfriend.jobfinder.auth.controller.AuthController;
import com.joblessfriend.jobfinder.member.domain.MemberVo;
import com.joblessfriend.jobfinder.member.service.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	private Logger logger = LoggerFactory.getLogger(AuthController.class);
	private final String logTitleMsg = "==Auth control==";
	
	@Autowired
	private MemberService memberService;

	@GetMapping("/mypage")
	public String myPage() {
		return "member/myPageView";
	}
	
	@GetMapping("/info")
	public String info() {
		return "member/myPageInfoView";
	}
	
	@PostMapping("/passwordCheck")
	public ResponseEntity<Integer> passwordCheck(@RequestParam String password, @RequestParam int memberId, HttpSession session) {
		int result = memberService.updatePassword(password, memberId);
		if(result == 1) {
			logger.info("비밀번호 변경 성공");
			MemberVo memberVo = (MemberVo)session.getAttribute("userLogin");
			memberVo.setPassword(password);
			session.setAttribute("userLogin", memberVo);
		}else {
			logger.info("비밀번호 변경 실패");
		}
		return ResponseEntity.ok(result);
	}
	
}
