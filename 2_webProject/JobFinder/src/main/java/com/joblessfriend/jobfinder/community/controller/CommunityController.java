package com.joblessfriend.jobfinder.community.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.joblessfriend.jobfinder.community.domain.CommunityVo;
import com.joblessfriend.jobfinder.community.service.CommunityService;
import com.joblessfriend.jobfinder.member.domain.MemberVo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/community")//기본 시작 루트. 하단 모든 경로 앞에 붙는다 생각하기
@Controller
public class CommunityController {
	
	@Autowired
	private CommunityService communityService;

	
//	@RequestMapping(value="", method = {RequestMethod.GET, RequestMethod.POST})
	@GetMapping("")
	public ModelAndView communityList() {
		
		List<CommunityVo> communityList=communityService.communitySelectList();
		
		ModelAndView modelList = new ModelAndView("community/list/communityList");
		modelList.addObject("communityList", communityList);
		
		//리스트 화면
		return modelList;
	}
	
	@GetMapping("/upload")
	public String communityUpload(Model model) {
		
		//업로드 화면
		return "community/add/communityUpload";
	}
	@PostMapping("/upload")
	public String communityUpload(@ModelAttribute CommunityVo communityVo, Model model, 
			 @SessionAttribute(name = "userLogin", required = false) MemberVo loginUser) {
		
	    System.out.println("글쓰기 연습 시작");
	    System.out.println("내용: " + communityVo.getContent());
	    System.out.println("세션정보: "+loginUser.getMemberId());
	    
	    
	    communityVo.setMemberId(loginUser.getMemberId());
	    

	    communityService.communityInsertOne(communityVo);

	    // 글 작성 완료 후 목록 페이지로 리다이렉트
	    return "redirect:/community";
	}
	
	@GetMapping("/detail")
	public String test3(@RequestParam int communityId, Model model) {
		System.out.println("게시판 세부 시작");
		
		CommunityVo communityVo = communityService.communityDetail(communityId);
	    String htmlContent = Markdown.markdownToHtml(communityVo.getContent());

	    model.addAttribute("community", communityVo);
	    model.addAttribute("contentHtml", htmlContent); // 변환된 HTML
		
//		CommunityVo communityVo = communityService.communityDetail(communityId);
//		
//		model.addAttribute("community", communityVo);
		//글 상세 화면
		return "community/detail/communityDetail";
	}

}
