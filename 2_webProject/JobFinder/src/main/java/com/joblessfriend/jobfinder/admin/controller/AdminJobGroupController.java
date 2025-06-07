package com.joblessfriend.jobfinder.admin.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joblessfriend.jobfinder.admin.service.AdminJobGroupService;
import com.joblessfriend.jobfinder.company.domain.CompanyVo;
import com.joblessfriend.jobfinder.jobGroup.domain.JobGroupVo;
import com.joblessfriend.jobfinder.jobGroup.service.JobGroupService;
import com.joblessfriend.jobfinder.util.Pagination;
import com.joblessfriend.jobfinder.util.SearchVo;

@RequestMapping("/admin/job/jobGroup") // 이 컨트롤러는 "/admin/job/jobGroup" 경로로 시작하는 요청을 처리
@Controller
public class AdminJobGroupController {

	// 로그 출력용 Logger 생성 
	private Logger logger = LoggerFactory.getLogger(AdminJobGroupController.class);
	private final String logTitleMsg = "==Admin control==";

	@Autowired
	private AdminJobGroupService jobGroupService; // 직군 관련 서비스 자동 주입

	// 직군 목록 페이지로 이동하는 GET 요청 처리
	@GetMapping("")
	public String jobGroupSelectList(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "") String keyword) { // 검색 키워드 (옵션)
		
		logger.info("직군 목록으로 이동");
		
		SearchVo searchVo = new SearchVo();
		searchVo.setKeyword(keyword);
		searchVo.setPage(page);
		

		
		int totalPage = jobGroupService.jobGroupCount(searchVo);
		Pagination pagination = new Pagination(totalPage, searchVo);
		
		// Oracle 11g에 맞게 startRow, endRow 계산
		searchVo.setStartRow(pagination.getLimitStart() + 1); // 1부터 시작
		searchVo.setEndRow(searchVo.getStartRow() + searchVo.getRecordSize() - 1);

		List<JobGroupVo> jobGroupList = new ArrayList<>(); // 직군 리스트 초기화
		jobGroupList = jobGroupService.jobGroupSelectList(searchVo);


		// 현재 페이지 정보 및 데이터 뷰에 전달
		
		model.addAttribute("jobGroupList", jobGroupList); // 직군 목록
		model.addAttribute("searchVo", searchVo);
		model.addAttribute("pagination", pagination);

		// 해당 뷰로 이동 (admin/job/jobGroupView.jsp 또는 .html 등)
		return "admin/job/jobGroupView";
	}
	
	/**
	 * 직군 추가
	 */
	@PostMapping("/add")
	public ResponseEntity<String> addJobGroup(@RequestBody JobGroupAddRequest request) {
		logger.info("직군 추가: {}", request.getJobGroupName());
		
		try {
			int result = jobGroupService.insertJobGroup(request.getJobGroupName());
			
			if (result > 0) {
				return ResponseEntity.ok("추가완료");
			} else {
				return ResponseEntity.badRequest().body("추가실패");
			}
		} catch (Exception e) {
			logger.error("직군 추가 실패: {}", e.getMessage());
			return ResponseEntity.badRequest().body("추가실패");
		}
	}
	
	/**
	 * 직군 삭제 (단일/다중 삭제 지원)
	 */
	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteJobGroups(@RequestBody List<Integer> jobGroupIdList) {
		logger.info("직군 삭제: {}", jobGroupIdList);
		
		try {
			int result = jobGroupService.deleteJobGroups(jobGroupIdList);
			
			if (result > 0) {
				return ResponseEntity.ok("삭제완료");
			} else {
				return ResponseEntity.badRequest().body("삭제실패");
			}
		} catch (Exception e) {
			logger.error("직군 삭제 실패: {}", e.getMessage());
			return ResponseEntity.badRequest().body("삭제실패");
		}
	}
	
	// 직군 추가 요청 DTO
	public static class JobGroupAddRequest {
		private String jobGroupName;
		
		public String getJobGroupName() {
			return jobGroupName;
		}
		
		public void setJobGroupName(String jobGroupName) {
			this.jobGroupName = jobGroupName;
		}
	}
}
