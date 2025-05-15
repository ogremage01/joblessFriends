package com.joblessfriend.jobfinder.community.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joblessfriend.jobfinder.community.domain.PostCommentVo;
import com.joblessfriend.jobfinder.community.service.CommunityService;
import com.joblessfriend.jobfinder.community.service.PostCommentService;

@Controller
public class PostCommentController {
	@Autowired
	private PostCommentService postCommentService;

	/*
	 * 기억할 것 
	 * 1. @PathVariable: URL 경로 안에 포함된 값을 자바 변수로 매핑해주는 어노테이션
	 * 2. jsp include를 통해 detail에서 비동기로 움직이는 것들
	 */
	
	/* 댓글 등록 후 댓글리스트가 갱신되는 로직 */
	@GetMapping("/community/detail/comments/{communityId}")
	@ResponseBody
	public List<PostCommentVo> getCommentsJson(@PathVariable("communityId") int communityId){
		
	    return postCommentService.postCommentSelectList(communityId);
	}
	
//	@GetMapping("test/{communityId}")
//	public String test(@PathVariable("communityId") int communityId, Model model){
//		
//		List<PostCommentVo> commentsList = postCommentService.postCommentSelectList(communityId);
//		System.out.println("댓글 수: " + commentsList.size());		
//		
//		model.addAttribute("commentsList", commentsList);
//		
//		return "community/detail/postComment/commentList";
//	}
}
