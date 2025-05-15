package com.joblessfriend.jobfinder.admin.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joblessfriend.jobfinder.company.domain.CompanyVo;
import com.joblessfriend.jobfinder.jobGroup.domain.JobGroupVo;
import com.joblessfriend.jobfinder.jobGroup.service.JobGroupService;

@RequestMapping("/admin/job/jobGroup") // 이 컨트롤러는 "/admin/job/jobGroup" 경로로 시작하는 요청을 처리
@Controller
public class AdminJobGroupController {

	// 로그 출력용 Logger 생성 
	private Logger logger = LoggerFactory.getLogger(AdminJobGroupController.class);
	private final String logTitleMsg = "==Admin control==";

	@Autowired
	private JobGroupService jobGroupService; // 직군 관련 서비스 자동 주입

	// 직군 목록 페이지로 이동하는 GET 요청 처리
	@GetMapping("")
	public String jobGroupSelectList(Model model, 
			@RequestParam(defaultValue = "0") int page, // 페이지 번호, 기본값은 0
			@RequestParam(value = "keyword", required = false) String keyword) { // 검색 키워드 (옵션)
		
		logger.info("직군 목록으로 이동");

		List<JobGroupVo> jobGroupList = new ArrayList<>(); // 직군 리스트 초기화
		int jobGroupCount = 0; // 총 직군 수
		int totalPage = 0; // 총 페이지 수

		// 키워드가 있는 경우 → 검색 기반 목록 조회
		if (keyword != null && !keyword.trim().isEmpty()) {
			jobGroupList = jobGroupService.jobGroupSelectList(page, keyword); // 키워드 기반 페이징 목록
			jobGroupCount = jobGroupService.jobGroupCount(keyword); // 키워드 기반 총 개수
			totalPage = jobGroupCount / 10 + (jobGroupCount % 10 == 0 ? 0 : 1); // 페이지 수 계산
		} else {
			// 키워드 없는 경우 → 전체 목록 조회
			jobGroupList = jobGroupService.jobGroupSelectList(page); // 전체 페이징 목록
			jobGroupCount = jobGroupService.jobGroupCount(); // 전체 개수
			System.out.println(jobGroupCount); // 디버깅용 출력
			totalPage = jobGroupCount / 10 + (jobGroupCount % 10 == 0 ? 0 : 1); // 페이지 수 계산
		}

		// 현재 페이지 정보 및 데이터 뷰에 전달
		int curPage = page;
		model.addAttribute("jobGroupList", jobGroupList); // 직군 목록
		model.addAttribute("totalPage", totalPage);       // 총 페이지 수
		model.addAttribute("curPage", curPage);           // 현재 페이지

		// 해당 뷰로 이동 (admin/job/jobGroupView.jsp 또는 .html 등)
		return "admin/job/jobGroupView";
		
		
	
	}
	
//	@GetMapping("/list")
//	@ResponseBody
//	public List<JobGroupVo> jobGroupInnerList(){
//		
//		List<JobGroupVo> jobGroupList = 
//		
//		
//		return null;
//	}
	
}
