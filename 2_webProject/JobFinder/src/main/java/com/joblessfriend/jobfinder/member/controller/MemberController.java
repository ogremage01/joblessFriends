package com.joblessfriend.jobfinder.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {

	@GetMapping("mypage")
	public String mypage() {
		return "member/myPageView";
	}
}
