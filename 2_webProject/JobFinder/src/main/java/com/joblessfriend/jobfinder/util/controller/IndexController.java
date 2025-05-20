package com.joblessfriend.jobfinder.util.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joblessfriend.jobfinder.util.Pagination;
import com.joblessfriend.jobfinder.util.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
	public String index(Model model) {
		// 1. 기본 SearchVo 생성 (recordSize = 8 기본값)
		SearchVo searchVo = new SearchVo();
		searchVo.setPage(1); // 첫 페이지

		// 2. 전체 개수 조회 → Pagination 객체 생성
		int totalCount = recruitmentService.getRecruitmentTotalCount(searchVo);
		Pagination pagination = new Pagination(totalCount, searchVo);

		// 3. Oracle 11g용 row 계산
		searchVo.setStartRow(pagination.getLimitStart() + 1); // ROWNUM은 1부터 시작
		searchVo.setEndRow(searchVo.getStartRow() + searchVo.getRecordSize() - 1);

		// 4. 페이징된 리스트만 조회
		List<RecruitmentVo> recruitmentList = recruitmentService.recruitmentList(searchVo);

		// 5. 스킬 맵핑 처리
		Map<Integer, List<SkillVo>> skillMap = new HashMap<>();
		for (RecruitmentVo r : recruitmentList) {
			int jobPostId = r.getJobPostId();
			List<SkillVo> skillList = skillService.postTagList(jobPostId);
			skillMap.put(jobPostId, skillList);
		}

		// 6. 전달
		model.addAttribute("now", new Date());
		model.addAttribute("recruitmentList", recruitmentList);
		model.addAttribute("skillMap", skillMap);
		model.addAttribute("pagination", pagination); // 필요 시 index.jsp에서 사용 가능

		return "index";
	}

}
