package com.joblessfriend.jobfinder.util.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		List<RecruitmentVo> recruitmentList = recruitmentService.recruitmentList();
		System.out.println(recruitmentList);

		Map<Integer, List<SkillVo>> skillMap = new HashMap<>();

		for (RecruitmentVo r : recruitmentList) {
			int jobPostId = r.getJobPostId();
			List<SkillVo> skillList = skillService.postTagList(jobPostId);// 태그리스트들 put
			skillMap.put(jobPostId, skillList);
		}
		
		Date now = new Date();
		
		model.addAttribute("now", now);
		model.addAttribute("recruitmentList", recruitmentList);
		model.addAttribute("skillMap", skillMap);

		return "index"; // /WEB-INF/views/index.jsp
	}
}
