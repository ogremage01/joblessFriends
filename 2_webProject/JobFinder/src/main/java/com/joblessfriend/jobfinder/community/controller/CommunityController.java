package com.joblessfriend.jobfinder.community.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/community")//기본 시작 루트. 하단 모든 경로 앞에 붙는다 생각하기
@Controller
public class CommunityController {

	
	@GetMapping("")
	public String test(Model model) {
		
		//리스트 화면
		return "commue/list/commueList";
	}
	
	@GetMapping("/a")
	public String test2(Model model) {
		
		//업로드 화면
		return "commue/add/commueUpload";
	}
	
	@GetMapping("/d")
	public String test3(Model model) {
		
		//글 상세 화면
		return "commue/detail/commueDetail";
	}

}
