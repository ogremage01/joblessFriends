package com.joblessfriend.jobfinder.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joblessfriend.jobfinder.member.domain.MemberVo;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/api")
public class ApiController {
	
	// 세션 상태 체크 API
	@GetMapping("/session/check")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> checkSession(HttpSession session) {
		Map<String, Object> result = new HashMap<>();
		MemberVo memberVo = (MemberVo) session.getAttribute("userLogin");
		
		if (memberVo != null) {
			result.put("valid", true);
			result.put("memberName", memberVo.getNickname());
		} else {
			result.put("valid", false);
		}
		
		return ResponseEntity.ok(result);
	}
} 