package com.joblessfriend.jobfinder.community.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.User;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.joblessfriend.jobfinder.community.domain.CommunityVo;
import com.joblessfriend.jobfinder.community.domain.PostCommentVo;
import com.joblessfriend.jobfinder.community.service.CommunityService;
import com.joblessfriend.jobfinder.community.service.PostCommentService;
import com.joblessfriend.jobfinder.member.domain.MemberVo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/community")//기본 시작 루트. 하단 모든 경로 앞에 붙는다 생각하기
@Controller
public class CommunityController {
	@Autowired
	private PostCommentService postCommentService;
	@Autowired
	private CommunityService communityService;

	//커뮤니티 메인
//	@RequestMapping(value="", method = {RequestMethod.GET, RequestMethod.POST})
	@GetMapping("")
	public String communityList(Model model) {
	    // 커뮤니티 리스트 조회
	    List<CommunityVo> communityList = communityService.communitySelectList();  // DB에서 리스트 가져오기

	    // communityList를 순회하면서 각 커뮤니티의 content를 마크다운 -> HTML로 변환 후, 태그 제거
	    for (CommunityVo communityVo : communityList) {
	        // 마크다운을 HTML로 변환
	        String htmlContent = Markdown.markdownToHtml(communityVo.getContent());

	        // HTML 태그를 제거하고 텍스트만 남기기
	        String textContent = htmlContent.replaceAll("<[^>]*>", "");  // HTML 태그 제거

	        // 변환된 텍스트를 커뮤니티 객체에 다시 저장
	        communityVo.setContent(textContent);  // 태그 제거된 텍스트만 저장
	    }

	    // 변환된 커뮤니티 리스트를 모델에 추가
	    model.addAttribute("communityList", communityList);

	    // 리스트 화면 반환
	    return "community/list/communityList";
	}
	
	//커뮤니티 생성(화면)
	@GetMapping("/upload")
	public String communityUpload(Model model) {
		
		//업로드 화면
		return "community/add/communityUpload";
	}
	
	//커뮤니티 메인(저장)
	@PostMapping("/upload")
	public String communityUpload(@ModelAttribute CommunityVo communityVo, Model model, 
			 @SessionAttribute(name = "userLogin", required = false) MemberVo userLogin) {
		
	    System.out.println("글쓰기 연습 시작");
	    System.out.println("내용: " + communityVo.getContent());
	    System.out.println("세션정보: "+userLogin.getMemberId());
	    
	    
	    communityVo.setMemberId(userLogin.getMemberId());
	    

	    communityService.communityInsertOne(communityVo);

	    // 글 작성 완료 후 목록 페이지로 리다이렉트
	    return "redirect:/community";
	}
	
	//커뮤니티 세부
	@GetMapping("/detail")
	public String communityDetail(@RequestParam int no, Model model) {
		System.out.println("게시판 세부 시작");
		
		// 커뮤니티 상세 정보 가져오기
		CommunityVo communityVo = communityService.communityDetail(no);
		// 마크다운 -> HTML 변환
		String htmlContent = Markdown.markdownToHtml(communityVo.getContent());
	    
		/* 페이지 처음 생성시 보여지는 댓글리스트 로직 */
		List<PostCommentVo> commentsList = postCommentService.postCommentSelectList(no);
		System.out.println("댓글 수: " + commentsList.size());		
		
		model.addAttribute("commentsList", commentsList);
		
	    model.addAttribute("community", communityVo);
	    model.addAttribute("contentHtml", htmlContent); // 변환된 HTML
		
//		CommunityVo communityVo = communityService.communityDetail(communityId);
//		
//		model.addAttribute("community", communityVo);
		//글 상세 화면
		return "community/detail/communityDetail";
	}
	
	@GetMapping("/update")
	public String communityUpdate(@RequestParam int no, Model model) {
		System.out.println("게시판 수정 시작");
		
		CommunityVo communityVo = communityService.communityDetail(no);
		
		model.addAttribute("community", communityVo);
		//글 상세 화면
		return "community/update/communityUpdate";
	}
	
	@PostMapping("/update")
	public String communityUpdate(@ModelAttribute CommunityVo communityVo, Model model) {
		System.out.println("게시판 수정 시작");
		
		communityService.communityUpdate(communityVo);
		
		model.addAttribute("community", communityVo);
		    
		//글 상세 화면
		return "redirect:/community/detail?no="+communityVo.getCommunityId();
	}
	
	@DeleteMapping("/delete/{communityId}")
	public ResponseEntity<String> communityDelete(@PathVariable("communityId") int communityId){
		communityService.communityDelete(communityId);
		
		return ResponseEntity.ok("게시물이 삭제되었습니다.");
	}
	

}
