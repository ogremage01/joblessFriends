package com.joblessfriend.jobfinder.community.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joblessfriend.jobfinder.community.domain.CommunityVo;
import com.joblessfriend.jobfinder.community.domain.PostCommentVo;
import com.joblessfriend.jobfinder.community.domain.ReplyVo;
import com.joblessfriend.jobfinder.community.service.PostCommentService;
import com.joblessfriend.jobfinder.community.service.ReplyService;

@RequestMapping("/community/detail")
@Controller
public class PostCommentController {
	@Autowired
	private PostCommentService postCommentService;
	@Autowired
	private ReplyService replyService;

	/*
	 * 기억할 것 (추가 예쩡)
	 * 1. @PathVariable: URL 경로 안에 포함된 값을 자바 변수로 매핑해주는 어노테이션
	 *
	 */
	
	/* 댓글 등록 후 댓글리스트가 갱신되는 로직 */
	@GetMapping("/comments/{communityId}")
	@ResponseBody
	public List<PostCommentVo> commentList(@PathVariable("communityId") int communityId){
		
		List<PostCommentVo> commentList = postCommentService.postCommentSelectList(communityId);
		List<ReplyVo> replyList = null;
		
		
		
		for (PostCommentVo commentVo : commentList) {        
	        //답글(리댓) 수 저장
	        replyList=replyService.replySelectList(commentVo.getPostCommentId());
	        commentVo.setReplyCount(replyList.size());
	    }
	    return commentList;
	}
	
	
	@PostMapping("/commentUpload/{communityId}")
	@ResponseBody
	public ResponseEntity<?> commentUpload(@PathVariable int communityId,
			 @RequestBody PostCommentVo postCommentVo) {
		System.out.println("~~~~~~~~~~~~~~~~댓글쓰기 시작~~~~~~~~~~~~~~");
		postCommentVo.setCommunityId(communityId);
		
		System.out.println(postCommentVo);
		postCommentService.postCommentInsert(postCommentVo);
		
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/commentUpdate/{postCommentId}")
	@ResponseBody
	public ResponseEntity<?> commentUpdate(@PathVariable int postCommentId,
			 @RequestBody PostCommentVo postCommentVo) {
		System.out.println("~~~~~~게시판 수정 시작~~~~~~~~~~");
		postCommentVo.setPostCommentId(postCommentId);
		postCommentService.postCommentUpdate(postCommentVo);
		    
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/delete/{postCommentId}")
	@ResponseBody
	public ResponseEntity<String> commentUpload(@PathVariable("postCommentId") int postCommentId) {
		System.out.println("~~~~~~~~~~~~~~~~댓글삭제 시작~~~~~~~~~~~~~~");
		
		replyService.replyCommentDelete(postCommentId);
		postCommentService.postCommentDelete(postCommentId);
		
		return ResponseEntity.ok("댓글 삭제");
	}
	
	
}
