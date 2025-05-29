package com.joblessfriend.jobfinder.community.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.joblessfriend.jobfinder.community.domain.CommunityVo;
import com.joblessfriend.jobfinder.community.domain.NoticeVo;
import com.joblessfriend.jobfinder.community.domain.PostCommentVo;
import com.joblessfriend.jobfinder.community.service.NoticeService;
import com.joblessfriend.jobfinder.util.Pagination;
import com.joblessfriend.jobfinder.util.SearchVo;

import jakarta.servlet.http.HttpSession;

//공지 부문
/* 존재하는 기능: 공지 리스트 보이기, 세부사항 보이기. */

@RequestMapping("/community/notice")
@Controller
public class NoticeController {
	
	@Autowired
	private NoticeService noticeService;
	
	//공지 리스트
	@GetMapping("")
	public String noticeList(Model model, @RequestParam(defaultValue = "1") int page, 
			@RequestParam(defaultValue = "") String keyword) {

/* 페이지네이션 */
		SearchVo searchVo = new SearchVo();
		searchVo.setKeyword(keyword);
		searchVo.setPage(page);
		searchVo.setRecordSize(4);	
		
	    int totalCount = noticeService.getNoticeTotalCount(searchVo);//전체 데이터 수
	   
	    Pagination pagination = new Pagination(totalCount, searchVo);
	    
        searchVo.setStartRow(pagination.getLimitStart() + 1); // 1부터 시작
        searchVo.setEndRow(searchVo.getStartRow() + searchVo.getRecordSize() - 1);
/* 페이지네이션 세팅 끝 */
        
        
	    // 커뮤니티 리스트 조회
	    List<NoticeVo> noticeList = noticeService.noticeSelectList(searchVo);  // DB에서 리스트 가져오기
	    
	    
	    // noticeList를 순회하면서 각 커뮤니티의 content를 마크다운 -> HTML로 변환 후, 태그 제거
	    for (NoticeVo noticeVo : noticeList) {
	        // 마크다운을 HTML로 변환
	        String htmlContent = Markdown.markdownToHtml(noticeVo.getContent());

	        // HTML 태그를 제거하고 텍스트만 남기기
	        String textContent = htmlContent.replaceAll("<[^>]*>", "");  // HTML 태그 제거

	        // 변환된 텍스트를 커뮤니티 객체에 다시 저장
	        noticeVo.setContent(textContent);  // 태그 제거된 텍스트만 저장
	        
	    }

	    // 변환된 커뮤니티 리스트를 모델에 추가(화면에 출력하기 위함)
	    model.addAttribute("noticeList", noticeList);
	    model.addAttribute("searchVo", searchVo);
	    model.addAttribute("pagination", pagination);
	    
	    
	    // 리스트 화면 반환
	    return "community/notice/noticeMain";
	}
	
	
	//세부사항
	
	@GetMapping("/detail")
	public String noticeDetail(@RequestParam int no, Model model, HttpSession session) {
		System.out.println("공지글 세부 시작");

		// 커뮤니티 상세 정보 가져오기
		NoticeVo noticeVo = noticeService.noticeDetail(no);
		System.out.println("공지글 세부 아이디: "+no);
		//session중일 떄 view 카운트
		if(session != null || session.getAttribute("userLogin")!=null) {
			Boolean viewed = (Boolean) session.getAttribute("notice_"+no);
			
			if(viewed == null || !viewed) {
	
				int views = noticeVo.getViews();
				
				views += 1;
				
				noticeVo.setViews(views);
				
				//조회수 업데이트
				noticeService.noticeViewCount(noticeVo);
				
				session.setAttribute("notice_"+no, true);//해당 세션 중 뷰 카운트 했으면 true(여러번 카운트 못하게 막음)
			}
		
		}
		
		// 마크다운 -> HTML 변환
		String htmlContent = Markdown.markdownToHtml(noticeVo.getContent());

	    model.addAttribute("notice", noticeVo);
	    model.addAttribute("contentHtml", htmlContent); // 변환된 HTML
		
		//글 상세 화면
		return "community/notice/noticeDetail";
	}
	

}
