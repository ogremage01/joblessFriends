package com.joblessfriend.jobfinder.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.joblessfriend.jobfinder.admin.domain.AdminVo;
import com.joblessfriend.jobfinder.community.controller.Markdown;
import com.joblessfriend.jobfinder.community.domain.CommunityVo;
import com.joblessfriend.jobfinder.community.domain.NoticeVo;
import com.joblessfriend.jobfinder.community.service.NoticeService;
import com.joblessfriend.jobfinder.member.domain.MemberVo;
import com.joblessfriend.jobfinder.util.Pagination;
import com.joblessfriend.jobfinder.util.SearchVo;

import jakarta.servlet.http.HttpSession;

//공지 부문
/* 존재하는 기능: 공지글 삽입, 삭제, 업데이트(화면 따로 디자인 및 생성), 전체 띄우기*/


@RequestMapping("/admin/community/notice")
@Controller
public class AdminNoticeController {
	
	@Autowired
	private NoticeService noticeService;
	
	//관리자: 공지 리스트
	@GetMapping("")
	public String noticeAdminSelectList(Model model, @RequestParam(defaultValue = "1") int page, 
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

	    // 변환된 커뮤니티 리스트를 모델에 추가(화면에 출력하기 위함)
	    model.addAttribute("noticeList", noticeList);
	    model.addAttribute("searchVo", searchVo);
	    model.addAttribute("pagination", pagination);
	    
	    
        
		return "admin/community/notice/noticeView";
	}
	
	//관리자: 공지 추가
	@GetMapping("/upload")
	public String noticeAdminUpload(Model model) {

		//업로드 화면
		return "community/add/noticeUpload";
	}
	
	@PostMapping("/upload")
	public String noticeAdminUpload(@ModelAttribute NoticeVo noticeVo,
			 @SessionAttribute(name = "userLogin", required = false) AdminVo userLogin,//이부분은 좀 관리자에 맞게 수정 
			 HttpSession session,
			 MultipartHttpServletRequest mhr) {
    
		// 글 작성 완료 후 목록으로 돌아가기
	    return "redirect:/admin/community/notice/noticeView";
	}
	
	
	//관리자: 공지 삭제
	
	
	//관리자: 공지 수정
	
	

}
