package com.joblessfriend.jobfinder.search.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.joblessfriend.jobfinder.community.controller.CommunityController;
import com.joblessfriend.jobfinder.community.domain.CommunityVo;
import com.joblessfriend.jobfinder.community.service.CommunityService;
import com.joblessfriend.jobfinder.recruitment.domain.JobGroupVo;
import com.joblessfriend.jobfinder.recruitment.domain.RecruitmentVo;
import com.joblessfriend.jobfinder.recruitment.service.RecruitmentService;
import com.joblessfriend.jobfinder.search.service.SearchService;
import com.joblessfriend.jobfinder.skill.domain.SkillVo;
import com.joblessfriend.jobfinder.util.Pagination;
import com.joblessfriend.jobfinder.util.SearchVo;

import lombok.RequiredArgsConstructor;

@RequestMapping("/search")
@Controller
public class SearchController {

    private final CommunityController communityController;

   private Logger logger = LoggerFactory.getLogger(SearchController.class);
   
   @Autowired
   private SearchService searchService;
   @Autowired
   private RecruitmentService recruitmentService;


    SearchController(CommunityController communityController) {
        this.communityController = communityController;
    }
   
  
   
	/*
	 * // 헤더 검색 기능 (임시) - pcj
	 * 
	 * @PostMapping("") public String searchMainList(Model
	 * model, @RequestParam(defaultValue = "1") int page,
	 * 
	 * @RequestParam(defaultValue = "") String keyword) { SearchVo searchVo = new
	 * SearchVo(); //채용공고 서비스 사용함 추후 검색기능 완성되면 수정 될 예정 searchVo.setKeyword(keyword);
	 * searchVo.setPage(page); searchVo.setRecordSize(4);
	 * 
	 * int totalCount = 0; Pagination pagination = null;
	 * 
	 * if(keyword != null && !keyword.trim().isEmpty()) { totalCount =
	 * searchService.getRecruitmentSearchTotalCount(searchVo); // 총 레코드 수 조회
	 * pagination = new Pagination(totalCount, searchVo);
	 * 
	 * 
	 * // Oracle 11g에 맞게 startRow, endRow 계산
	 * searchVo.setStartRow(pagination.getLimitStart() + 1); // 1부터 시작
	 * searchVo.setEndRow(searchVo.getStartRow() + searchVo.getRecordSize() - 1); }
	 * 
	 * List<RecruitmentVo> recruitmentList =
	 * searchService.getRecruitmentSearchList(searchVo);
	 * 
	 * logger.info("메인서치 recruitmentList: "+ recruitmentList);
	 * model.addAttribute("recruitmentList", recruitmentList);
	 * model.addAttribute("pagination", pagination);
	 * model.addAttribute("totalCount", totalCount); // 검색결과 개수
	 * model.addAttribute("keyword", keyword); // 검색어
	 * 
	 * return "mainSearchView"; }
	 */
   @PostMapping("")
   public String searchMainList(Model model, @RequestParam(defaultValue = "1") int page, 
		      @RequestParam(defaultValue = "") String keyword) {

		    SearchVo searchVo = new SearchVo();
		    searchVo.setKeyword("%" + keyword + "%");
		    System.out.println(searchVo.getKeyword());
		    searchVo.setPage(page); // ★ page 파라미터 사용
		    searchVo.setRecordSize(4);

		    int totalCount = 0;
		    Pagination pagination = null;

		    // 검색어가 있든 없든 전체 카운트 및 페이징 처리
		    
		    try {
		    	totalCount = searchService.getRecruitmentSearchTotalCount(searchVo); // 총 레코드 수 조회
			    pagination = new Pagination(totalCount, searchVo);	
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		    

		    // Oracle 11g에 맞게 startRow, endRow 계산
		    searchVo.setStartRow(pagination.getLimitStart() + 1); // 1부터 시작
		    searchVo.setEndRow(searchVo.getStartRow() + searchVo.getRecordSize() - 1);

		    List<RecruitmentVo> recruitmentList = searchService.getRecruitmentSearchList(searchVo);

		    logger.info("메인서치 recruitmentList: " + recruitmentList);
		    model.addAttribute("recruitmentList", recruitmentList);
		    model.addAttribute("pagination", pagination);
		    model.addAttribute("totalCount", totalCount); // 검색결과 개수
		    model.addAttribute("keyword", keyword); // 검색어

		    return "mainSearchView";
		}

		/*
		 * @GetMapping("/recruitment") public List<RecruitmentVo>
		 * searchRecruitmentList(Model model, @RequestParam(defaultValue = "1") int
		 * page,
		 * 
		 * @RequestParam(defaultValue = "") String keyword) {
		 * 
		 * searchVo.setKeyword(keyword); searchVo.setPage(1); searchVo.setRecordSize(4);
		 * 
		 * int totalCount = searchService.getRecruitmentSearchTotalCount(searchVo); // 총
		 * 레코드 수 조회 Pagination pagination = new Pagination(totalCount, searchVo);
		 * 
		 * // Oracle 11g에 맞게 startRow, endRow 계산
		 * searchVo.setStartRow(pagination.getLimitStart() + 1); // 1부터 시작
		 * searchVo.setEndRow(searchVo.getStartRow() + searchVo.getRecordSize() - 1);
		 * 
		 * List<RecruitmentVo> recruitmentList =
		 * searchService.recruitmentList(searchVo);
		 * 
		 * model.addAttribute("recruitmentList", recruitmentList);
		 * model.addAttribute("pagination", pagination);
		 * 
		 * return null; }
		 * 
		 * @GetMapping("/community") public List<CommunityVo> searchCommunityList(Model
		 * model, @RequestParam(defaultValue = "1") int page,
		 * 
		 * @RequestParam(defaultValue = "") String keyword) {
		 * searchVo.setKeyword(keyword); searchVo.setPage(1); searchVo.setRecordSize(4);
		 * 
		 * int totalCount = searchService.getCommunityTotalCount(searchVo); // 총 레코드 수
		 * 조회 Pagination pagination = new Pagination(totalCount, searchVo);
		 * 
		 * // Oracle 11g에 맞게 startRow, endRow 계산
		 * searchVo.setStartRow(pagination.getLimitStart() + 1); // 1부터 시작
		 * searchVo.setEndRow(searchVo.getStartRow() + searchVo.getRecordSize() - 1);
		 * 
		 * List<CommunityVo> communityList = searchService.communityList(searchVo);
		 * 
		 * model.addAttribute("communityList", communityList);
		 * model.addAttribute("pagination", pagination);
		 * 
		 * return null; }
		 */
}
