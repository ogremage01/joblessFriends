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
import com.joblessfriend.jobfinder.resume.domain.CareerVo;
import com.joblessfriend.jobfinder.resume.domain.CertificateVo;
import com.joblessfriend.jobfinder.resume.domain.ResumeVo;
import com.joblessfriend.jobfinder.resume.domain.SchoolVo;
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
      int cnt = 0;

      ResumeVo resumeVo = resumeService.getResumeByResumeId(resumeId);
      RecruitmentVo recruitmentVo = recruitmentService.getRecruitmentId(jobPostId);

      List<SkillVo> resumeSkillList = skillService.resumeTagList(resumeId);
      List<SkillVo> recruitmentSkillList = skillService.postTagList(jobPostId);

      
      List<SchoolVo> resumeSchoolVo = resumeVo.getSchoolList();
       
      List<CareerVo> resumeCareerVo = resumeVo.getCareerList();
      

      for (int i = 0; i < recruitmentSkillList.size(); i++) {
         for (int j = 0; j < resumeSkillList.size(); j++) {
            if (recruitmentSkillList.get(i).getTagId() == resumeSkillList.get(j).getTagId()) {
               cnt++;
            }
         }
      }
     
      
      
      matchScore = cnt * (30 / recruitmentSkillList.size());
      
     
      
      
      
      String SchoolType = recruitmentVo.getEducation();
	  int sorationNo = 0;
	  int status;
	  int graduate;
      
	      switch (SchoolType) { 
	      
	      case "고등학교 졸업":break; 
	      case "대학 졸업(2,3년)":break; 
	      case "대학교 졸업(4년)":break; 
	      case "대학원 석사졸업":break; 
	      case "대학원 박사졸업":break; 
	  }
       
        
        String careerType = recruitmentVo.getCareerType();
        
        switch (careerType) { 
        
	        case "신입":break; 
	        case "1~3년":break; 
	        case "3~5년":break; 
	        case "5년이상":break; 
	        
        }
       
      // 모든 경력 0.5, 같은 직군 경력 3, 같은 직무 경력
      

      
      
      
      model.addAttribute("matchScore", matchScore);

      return "";
   } // 스킬 3, 학력 3, 경력 4 비율

}
