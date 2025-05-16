package com.joblessfriend.jobfinder.company.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.joblessfriend.jobfinder.company.domain.CompanyVo;
import com.joblessfriend.jobfinder.recruitment.domain.RecruitmentVo;
import com.joblessfriend.jobfinder.recruitment.service.RecruitmentService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/company/recruitment")
public class CompanyRecruitmentController {

	@Autowired
	RecruitmentService recruitmentService;
	
	@GetMapping("")
	public String CompanyRecruitmentList(Model model, HttpSession session) {
		CompanyVo companyVo = (CompanyVo) session.getAttribute("userLogin");

		int companyId = companyVo.getCompanyId();
		
		List<RecruitmentVo> CompanyRecruitmentList = recruitmentService.companyRecruitmentSelectList(companyId);
		
		model.addAttribute("recruitmentList", CompanyRecruitmentList);
		
		return "company/recruitment/recruitmentListView";
	}
	
}
