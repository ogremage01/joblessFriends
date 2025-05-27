package com.joblessfriend.jobfinder.resume.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.joblessfriend.jobfinder.member.domain.MemberVo;
import com.joblessfriend.jobfinder.recruitment.domain.RecruitmentDetailVo;
import com.joblessfriend.jobfinder.recruitment.domain.RecruitmentVo;
import com.joblessfriend.jobfinder.recruitment.service.RecruitmentService;
import com.joblessfriend.jobfinder.resume.domain.CertificateVo;
import com.joblessfriend.jobfinder.resume.domain.ResumeVo;
import com.joblessfriend.jobfinder.resume.service.CertificateService;
import com.joblessfriend.jobfinder.resume.service.ResumeMatchService;
import com.joblessfriend.jobfinder.resume.service.ResumeService;
import com.joblessfriend.jobfinder.skill.domain.SkillVo;
import com.joblessfriend.jobfinder.skill.service.SkillService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ResumeMatchController {
	
	@Autowired
	private ResumeService resumeService;
	
	@Autowired
	private RecruitmentService recruitmentService;
	
	@Autowired
	private SkillService skillService;
	
	@Autowired
	private CertificateService certificateService;
	
	
	@GetMapping("/match")
	public String resumeManagement(int resumeId, int jobPostId, HttpSession session, Model model) {
		int matchScore = 0;
		int totalScore = 0;
		int cnt = 0;
		
		ResumeVo resumeVo = resumeService.getResumeByResumeId(resumeId);
        RecruitmentVo recruitmentVo = recruitmentService.getRecruitmentId(jobPostId);
        
        List<SkillVo> resumeSkillList = skillService.resumeTagList(resumeId);
        List<SkillVo> recruitmentSkillList = skillService.postTagList(jobPostId);
        
        List<CertificateVo> resumeCertificateList = null; 
        List<CertificateVo> recuitmentCertificateList = null;
        
		/*
		 * List<SchoolVo> resumeSchoolVo = null;
		 * 
		 * List<CareerVo> resumeCareerVo = null;
		 */
        
        for(int i = 0; i < recruitmentSkillList.size(); i++) {
        	for(int j = 0; j < resumeSkillList.size(); j++) {
        		 if (recruitmentSkillList.get(i).getTagId() == resumeSkillList.get(j).getTagId()) {
        	            cnt++;
        	        }
        	}
        }
        
        totalScore = recruitmentSkillList.size() * 15;
        matchScore = cnt * 15;
        
        
        cnt = 0;
        for(int i = 0; i < recuitmentCertificateList.size(); i++) {
        	for(int j = 0; j < resumeCertificateList.size(); j++) {
        		 if (recuitmentCertificateList.get(i).getCertificateId() == resumeCertificateList.get(j).getCertificateId()) {
        	            cnt++;
        	        }
        	}
        }
        
        totalScore = recuitmentCertificateList.size() * 15;
        matchScore = cnt * 15;
        
        
		/*
		 * String SchoolType =
		 * 
		 * 
		 * 
		 * String careerType = recruitmentVo.getCareerType();
		 * 
		 * switch (careerType) { case "신입":
		 * 
		 * break; case "1~3년":
		 * 
		 * break; case "3~5년":
		 * 
		 * break; case "5년이상":
		 * 
		 * break; }
		 */
        
        
        model.addAttribute("matchScore", matchScore);
        
        return "";
    }
	
	
}

