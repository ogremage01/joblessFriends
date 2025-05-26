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

import com.joblessfriend.jobfinder.community.domain.PostCommentVo;
import com.joblessfriend.jobfinder.community.domain.ReplyVo;
import com.joblessfriend.jobfinder.community.service.ReplyService;

@RequestMapping("/community/detail")
@Controller
public class ReplyController {
	
	@Autowired
	private ReplyService replyService;

	//결과 화면 테스트용 메서드 하나 만들기-postreply 아이디 직접 입력 
	
	/* 댓글 등록 후 댓글리스트가 갱신되는 로직 */
	@GetMapping("/reply/{postCommentId}")
	@ResponseBody
	public List<ReplyVo> replyList(@PathVariable("postCommentId") int postCommentId){
		
		
	    return replyService.replySelectList(postCommentId);
	}
	
//	@GetMapping("testRe/{postCommentId}")
//	public String test(@PathVariable("postCommentId") int postCommentId, Model model){
//		
//		List<PostCommentVo> replysList = replyService.replySelectList(postCommentId);
//		System.out.println("댓글 수: " + replysList.size());
//		
//		model.addAttribute("replysList", replysList);
//		
//		return "community/detail/postComment/reply";
//	}
	
	/* 리댓(대댓글) 작성하기 */
	@PostMapping("/replyUpload/{postCommentId}")
	@ResponseBody
	public ResponseEntity<?> replyUpload(@PathVariable int postCommentId,
			 @RequestBody ReplyVo replyVo) {
		System.out.println("~~~~~~~~~~~~~~~~답글쓰기 시작~~~~~~~~~~~~~~");
		replyVo.setCommunityId(postCommentId);
		
		System.out.println(replyVo);
		replyService.replyInsert(replyVo);
		
		return ResponseEntity.ok().build();
	}
	
	
	/* 업데이트 작성하기 */
	@PostMapping("/replyUpdate/{replyId}")
	@ResponseBody
	public ResponseEntity<?> replyUpdate(@PathVariable int replyId, @RequestBody ReplyVo replyVo) {
		System.out.println("~~~~~~게시판 수정 시작~~~~~~~~~~");
		replyVo.setReplyId(replyId);
		replyService.replyUpdate(replyVo);
		    
		return ResponseEntity.ok().build();
	}
		
	
	/* 대댓글(리댓) 삭제 기능 */
	@DeleteMapping("/replyDelete/{replyId}")
	@ResponseBody
	public ResponseEntity<String> replyUpload(@PathVariable("replyId") int replyId) {
		System.out.println("~~~~~~~~~~~~~~~~답글삭제 시작~~~~~~~~~~~~~~");
		
		replyService.replyDelete(replyId);
		
		return ResponseEntity.ok("답글 삭제");
	}
	

	
}
