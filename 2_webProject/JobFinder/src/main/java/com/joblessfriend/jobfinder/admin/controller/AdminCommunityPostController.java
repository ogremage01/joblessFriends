package com.joblessfriend.jobfinder.admin.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.joblessfriend.jobfinder.admin.service.AdminCommunityPostService;
import com.joblessfriend.jobfinder.community.domain.CommunityVo;
import com.joblessfriend.jobfinder.community.service.CommunityService;
import com.joblessfriend.jobfinder.util.Pagination;
import com.joblessfriend.jobfinder.util.SearchVo;


@RequestMapping("/admin/community/post") 
@Controller
public class AdminCommunityPostController {
	
	private Logger logger = LoggerFactory.getLogger(AdminCommunityPostController.class); // 로그 출력용
	private final String logTitleMsg = "==Admin / community control==";

	@Autowired
	private CommunityService communityService;
	@Autowired
	private AdminCommunityPostService communityPostService;
	
	@GetMapping("")
	public String communityPostSelectList(Model model, @RequestParam(defaultValue = "1") int page, 
			@RequestParam(defaultValue = "") String keyword) {
		
		/* 페이지네이션 */
		SearchVo searchVo = new SearchVo();
		searchVo.setKeyword(keyword);
		searchVo.setPage(page);
		searchVo.setRecordSize(8);	
		
		System.out.println("커뮤니티 검색어: "+keyword);
		
	    int totalCount = communityService.getCommunityTotalCount(searchVo);//전체 데이터 수
	    Pagination pagination = new Pagination(totalCount, searchVo);
	    
        searchVo.setStartRow(pagination.getLimitStart() + 1); // 1부터 시작
        searchVo.setEndRow(searchVo.getStartRow() + searchVo.getRecordSize() - 1);
        /* 페이지네이션 세팅 끝 */
        
     // 커뮤니티 리스트 전체 조회
	    List<CommunityVo> communityList = communityService.communitySelectList(searchVo);  // DB에서 리스트 가져오기
	 // 변환된 커뮤니티 리스트를 모델에 추가(화면에 출력하기 위함)
	    model.addAttribute("communityList", communityList);
	    model.addAttribute("searchVo", searchVo);
	    model.addAttribute("pagination", pagination);
        
		return "admin/community/postView";
	}
	
	
	@DeleteMapping("/delete")
	public ResponseEntity<String> communityPostDelete(@RequestBody List<Integer> communityIdList) {
		logger.info("게시물 삭제 메서드");

		for (Integer i : communityIdList) {
			System.out.println("삭제할 게시물 Id " + i);

		}
		
		communityPostService.communityPostDelete(communityIdList);

		
		return ResponseEntity.ok("삭제완료"); 
	}



}
