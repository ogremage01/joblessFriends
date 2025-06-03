package com.joblessfriend.jobfinder.util.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joblessfriend.jobfinder.util.Pagination;
import com.joblessfriend.jobfinder.util.SearchVo;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.joblessfriend.jobfinder.member.domain.MemberVo;
import com.joblessfriend.jobfinder.recruitment.domain.RecruitmentVo;
import com.joblessfriend.jobfinder.recruitment.service.RecruitmentService;
import com.joblessfriend.jobfinder.skill.domain.SkillVo;
import com.joblessfriend.jobfinder.skill.service.SkillService;

@Controller
public class IndexController {

	@Autowired
	private RecruitmentService recruitmentService;
	
	 @Autowired
	    private SkillService skillService;

	@GetMapping("/")
	public String index(Model model, HttpSession session) {
		
		
		// 1. 기본 SearchVo 생성 (recordSize = 8 기본값)
		SearchVo searchVo = new SearchVo(); // 최신 순
		searchVo.setPage(1); // 첫 페이지
		searchVo.setRecordSize(40); // 보여줄 공고 수
		
		SearchVo searchVoSlide = new SearchVo();// 마감임박 순, 조회수 순
		
		// 2. 전체 개수 조회 → Pagination 객체 생성
		int totalCount = recruitmentService.getRecruitmentTotalCount(searchVo);
		Pagination pagination = new Pagination(totalCount, searchVo);
		
		int totalCountSlide = recruitmentService.getRecruitmentTotalCount(searchVoSlide);
		Pagination paginationSlide = new Pagination(totalCountSlide, searchVoSlide);

		// 3. Oracle 11g용 row 계산
		searchVo.setStartRow(pagination.getLimitStart() + 1); // ROWNUM은 1부터 시작
		searchVo.setEndRow(searchVo.getStartRow() + searchVo.getRecordSize() - 1);

		searchVoSlide.setStartRow(paginationSlide.getLimitStart() + 1); // ROWNUM은 1부터 시작
		searchVoSlide.setEndRow(searchVoSlide.getStartRow() + searchVoSlide.getRecordSize() - 1);

		// 4. 페이징된 리스트만 조회
		List<RecruitmentVo> recruitmentListLatest = recruitmentService.recruitmentListLatest(searchVo); // 최신 순
		List<RecruitmentVo> recruitmentListViews = recruitmentService.recruitmentListViews(searchVoSlide); // 조회수 순
		List<RecruitmentVo> recruitmentListEndDate = recruitmentService.recruitmentList(searchVoSlide); // 마감임박 순

		// 5. 스킬 맵핑 처리
	    
		// 최신 순
		Map<Integer, List<SkillVo>> skillMapLatest = new HashMap<>();
		for (RecruitmentVo r : recruitmentListLatest) {
			int jobPostId = r.getJobPostId();
			List<SkillVo> skillList = skillService.postTagList(jobPostId);
			skillMapLatest.put(jobPostId, skillList);
			
		}
		
		// 조회수 순
		Map<Integer, List<SkillVo>> skillMapViews = new HashMap<>();
		for (RecruitmentVo r : recruitmentListViews) {
			int jobPostId = r.getJobPostId();
			List<SkillVo> skillList = skillService.postTagList(jobPostId);
			skillMapViews.put(jobPostId, skillList);
			
		}
		
		// 마감임박 순
		Map<Integer, List<SkillVo>> skillMapEndDate = new HashMap<>();
		for (RecruitmentVo r : recruitmentListEndDate) {
			int jobPostId = r.getJobPostId();
			List<SkillVo> skillList = skillService.postTagList(jobPostId);
			skillMapEndDate.put(jobPostId, skillList);
			
		}

		// 6. 전달
		model.addAttribute("now", new Date());
		
		// 최신 순
		model.addAttribute("recruitmentListLatest", recruitmentListLatest);
		model.addAttribute("skillMapLatest", skillMapLatest);
		// 조회수 순
		model.addAttribute("recruitmentListViews", recruitmentListViews);
		model.addAttribute("skillMapViews", skillMapViews);
		// 마감임박 순
		model.addAttribute("recruitmentListEndDate", recruitmentListEndDate);
		model.addAttribute("skillMapEndDate", skillMapEndDate);
		
		model.addAttribute("pagination", pagination); // 필요 시 index.jsp에서 사용 가능

		
		return "index";
	}

}
