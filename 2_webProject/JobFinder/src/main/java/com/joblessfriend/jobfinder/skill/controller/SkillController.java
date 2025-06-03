package com.joblessfriend.jobfinder.skill.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.joblessfriend.jobfinder.skill.domain.SkillVo;
import com.joblessfriend.jobfinder.skill.service.SkillService;

@RestController
@RequestMapping("/skill")
public class SkillController {

	@Autowired
	private SkillService skillService;
	
	@GetMapping("/list")
	public List<SkillVo> getSkillsByJobGroupId(@RequestParam("jobGroupId") int jobGroupId) {
		return skillService.tagList(jobGroupId);
	}
	
	@GetMapping("/test")
	@ResponseBody
	public String testConnection() {
		try {
			return "스킬 컨트롤러 연결 성공";
		} catch (Exception e) {
			return "오류: " + e.getMessage();
		}
	}
	
	@GetMapping("/autocomplete")
	public List<SkillVo> getSkillAutocomplete(@RequestParam("keyword") String keyword) {
		try {
			if (keyword == null || keyword.trim().isEmpty()) {
				return new ArrayList<>();
			}
			return skillService.getSkillsByKeyword(keyword.trim());
		} catch (Exception e) {
			System.err.println(">>> [SkillController] 스킬 자동완성 오류: " + e.getMessage());
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
}
