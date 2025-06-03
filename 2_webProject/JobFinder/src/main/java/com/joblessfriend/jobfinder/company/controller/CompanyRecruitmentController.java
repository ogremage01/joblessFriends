package com.joblessfriend.jobfinder.company.controller;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.joblessfriend.jobfinder.company.domain.CompanyVo;
import com.joblessfriend.jobfinder.company.service.CompanyRecruitmentService;
import com.joblessfriend.jobfinder.recruitment.domain.CompanyRecruitmentVo;
import com.joblessfriend.jobfinder.skill.service.SkillService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/company/recruitment")
public class CompanyRecruitmentController {

	@Autowired
	CompanyRecruitmentService recruitmentService;

	@Autowired
	SkillService skillService;

	@GetMapping("")
	public String CompanyRecruitmentList(Model model, HttpSession session) {
		CompanyVo companyVo = (CompanyVo) session.getAttribute("userLogin");
		Object userType = session.getAttribute("userType");
		
		if (companyVo == null || !"company".equals(userType)) {
            return "redirect:/auth/login";
        }
		
		int companyId = companyVo.getCompanyId();

		List<CompanyRecruitmentVo> CompanyRecruitmentList = recruitmentService.companyRecruitmentSelectList(companyId);

		for (Iterator iterator = CompanyRecruitmentList.iterator(); iterator.hasNext();) {
			CompanyRecruitmentVo companyRecruitmentVo = (CompanyRecruitmentVo) iterator.next();

			companyRecruitmentVo.setSkillList(skillService.postTagList(companyRecruitmentVo.getJobPostId()));
		}

		model.addAttribute("recruitmentList", CompanyRecruitmentList);

		return "company/recruitment/recruitmentListView";
	}

	@DeleteMapping("")

	public ResponseEntity<String> recruitmentDelete(@RequestBody List<Integer> jobPostIdList) {
		// logger.info("공고삭제메서드");


		recruitmentService.jobPostDelete(jobPostIdList);

		return ResponseEntity.ok("삭제완료");
	}

	@PatchMapping("/stop")

	public ResponseEntity<String> recruitmentStop(@RequestBody List<Integer> jobPostIdList) {
		// logger.info("공고삭제메서드");



		recruitmentService.jobPostStop(jobPostIdList);

		return ResponseEntity.ok("마감완료");
	}

	@GetMapping("/{jobPostId}/applicants")
	public String jobPostApplicantsList(HttpSession session, @PathVariable int jobPostId) {
		CompanyVo companyVo = (CompanyVo) session.getAttribute("userLogin");
		if (companyVo == null) {
			return "redirect:/auth/login";
		}

		return "company/recruitment/applicantsListView";

	}

}
