package com.joblessfriend.jobfinder.community.controller;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.joblessfriend.jobfinder.community.domain.CommunityVo;
import com.joblessfriend.jobfinder.community.service.CommunityService;

@RequestMapping("/community")//기본 시작 루트. 하단 모든 경로 앞에 붙는다 생각하기
@Controller
public class CommunityController {
	
	@Autowired
	private CommunityService communityService;

	
	@GetMapping("")
	public String test(Model model) {
		
		//리스트 화면
		return "community/list/communityList";
	}
	
	@GetMapping("/upload")
	public String communityUpload(Model model) {
		
		//업로드 화면
		return "community/add/communityUpload";
	}
	@PostMapping("/upload")
	public String communityUpload(@ModelAttribute CommunityVo communityVo, Model model) {
	    System.out.println("글쓰기 연습 시작");
	    System.out.println("내용: " + communityVo.getContent());

	    communityService.communityInsertOne(communityVo);

	    // 글 작성 완료 후 목록 페이지로 리다이렉트
	    return "redirect:/community";
	}
	
	@GetMapping("/detail")
	public String test3(Model model) {
		
		//글 상세 화면
		return "community/detail/communityDetail";
	}

}
