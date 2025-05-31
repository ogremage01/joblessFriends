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

import com.joblessfriend.jobfinder.admin.service.AdminRecruitmentService;
import com.joblessfriend.jobfinder.recruitment.domain.RecruitmentVo;
import com.joblessfriend.jobfinder.util.Pagination;
import com.joblessfriend.jobfinder.util.SearchVo;

@RequestMapping("/admin/recruitment")
@Controller
public class AdminRecruitmentController {
	// 로그 출력용 Logger 생성
	private Logger logger = LoggerFactory.getLogger(AdminRecruitmentController.class);
	private final String logTitleMsg = "==Admin control==";

	@Autowired
	private AdminRecruitmentService adminRecruitmentService; // 구인공고 서비스 의존성 주입

	@GetMapping("")
	public String recruitmentSelectList(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "") String keyword) { // 검색 키워드 (옵션)

		logger.info("공고목록으로 이동");
		
		
		
		SearchVo searchVo = new SearchVo();
		searchVo.setKeyword(keyword);
		searchVo.setPage(page);

		int totalPage = adminRecruitmentService.getRecruitmentTotalCount(searchVo);
		Pagination pagination = new Pagination(totalPage, searchVo);
		
		// Oracle 11g에 맞게 startRow, endRow 계산
		searchVo.setStartRow(pagination.getLimitStart() + 1); // 1부터 시작
		searchVo.setEndRow(searchVo.getStartRow() + searchVo.getRecordSize() - 1);
		List<RecruitmentVo> recruitmentList = adminRecruitmentService.adminRecruitmentList(searchVo);

		model.addAttribute("recruitmentList", recruitmentList);
		model.addAttribute("pagination", pagination);
		model.addAttribute("searchVo", searchVo);
		
		return "admin/recruitment/recruitmentView";
	}

	@DeleteMapping("")

	public ResponseEntity<String> recruitmentDelete(@RequestBody List<Integer> jobPostIdList) {
		logger.info("공고삭제메서드");


		
		adminRecruitmentService.jobPostDelete(jobPostIdList);

		
		return ResponseEntity.ok("삭제완료"); 
	}

}
