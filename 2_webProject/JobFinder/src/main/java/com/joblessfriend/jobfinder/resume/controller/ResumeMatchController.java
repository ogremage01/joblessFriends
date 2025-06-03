package com.joblessfriend.jobfinder.resume.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.joblessfriend.jobfinder.auth.controller.AuthController;
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

	private final AuthController authController;

	@Autowired
	private ResumeService resumeService;

	@Autowired
	private RecruitmentService recruitmentService;

	@Autowired
	private SkillService skillService;

	@Autowired
	private CertificateService certificateService;
	
	@Autowired
	private ResumeMatchService resumeMatchService;

	ResumeMatchController(AuthController authController) {
		this.authController = authController;
	}

	@GetMapping("/match")
	public String resumeManagement(int resumeId, int jobPostId, HttpSession session, Model model) { // null 체크 필요
		
		int matchScore = 0; //총점 변수

		//항목별 max 점수
		int skillTotalScore = 30; 
		int schoolTotalScore = 30;
		int careerTotalScore = 40;

		ResumeVo resumeVo = resumeService.getResumeByResumeId(resumeId);
		RecruitmentVo recruitmentVo = recruitmentService.getRecruitmentId(jobPostId);

		//스킬
		List<SkillVo> resumeSkillList = skillService.resumeTagList(resumeId);
		List<SkillVo> recruitmentSkillList = skillService.postTagList(jobPostId);

		int cnt = 0;
		int skillScore = 0;

		for (int i = 0; i < recruitmentSkillList.size(); i++) {
			for (int j = 0; j < resumeSkillList.size(); j++) {
				if (recruitmentSkillList.get(i).getTagId() == resumeSkillList.get(j).getTagId()) {
					cnt++;
				}
			}
		}
		
		skillScore = cnt * (skillTotalScore / recruitmentSkillList.size());
		
		if(skillScore > skillTotalScore) {
			skillScore = skillTotalScore;
		}
		
			
		// 학력
		List<SchoolVo> resumeSchoolList = resumeVo.getSchoolList();

		String schoolType = recruitmentVo.getEducation();
		int sortationNo = 0; // 고등학교 high, 대학교(2, 3) univ2, 대학교(4) univ4, 대학원 석사 master, 대학원 박사 doctor
		int schoolScore = 0;

		switch (schoolType) {

		case "고등학교 졸업":
			schoolScore = 30;
			break;
		case "대학 졸업(2,3년)":
			schoolScore = 20;
			break;
		case "대학교 졸업(4년)":
			schoolScore = 16;
			break;
		case "대학원 석사졸업":
			schoolScore = 7;
			break;
		case "대학원 박사졸업":
			schoolScore = 0;
			break;
		}
		
		//수정 필요
		for (SchoolVo item : resumeSchoolList) {
			String sortation = item.getSortation();
			String status = item.getStatus();
			
			if (sortation.equals("고등학교")) {
				if (status.equals("졸업 예정")) {
					schoolScore -= 1;
				} else if (status.equals("재학 중")) {
					schoolScore -= 6;
				}
			} else if (sortation.equals("대학교(2,3년)")) {
				if (status.equals("졸업 예정")) {
					schoolScore -= 1;
				} else if (status.equals("재학 중")) {
					schoolScore -= 4;
				}
			} else if (sortation.equals("대학교(4년)")) {
				if (status.equals("졸업 예정")) {
					schoolScore -= 1;
				} else if (status.equals("재학 중")) {
					schoolScore -= 3;
				}
			} else if (sortation.equals("석사")) {
				if (status.equals("졸업 예정")) {
					schoolScore -= 1;
				} else if (status.equals("재학 중")) {
					schoolScore -= 2;
				}
			} else if (sortation.equals("박사")) {
				if (status.equals("졸업 예정")) {
					schoolScore -= 1;
				} else if (status.equals("재학 중")) {
					schoolScore -= 2;
				}
			}

		}

		if (schoolScore > schoolTotalScore) {
			schoolScore = schoolTotalScore;
		}

		// 경력
		List<CareerVo> resumeCareerList = resumeVo.getCareerList();

		String careerType = recruitmentVo.getCareerType();
		int careerScore = 0;

		switch (careerType) {

		case "신입":
			careerScore = 40;
			break;
		case "1~3년":
			careerScore = 25;
			break;
		case "3~5년":
			careerScore = 10;
			break;
		/*
		 * case "5년이상": careerScore = 0; break;
		 */

		}

		// 모든 경력 0.5, 같은 직군 경력 3, 같은 직무 경력
		int careerAll = 0;
		int careerJobGroup = 0;
		int careerJob = 0;
		
		int recJobGroupId = recruitmentVo.getJobGroupId();
		int recJobId = recruitmentVo.getJobId(); 
		
		for (CareerVo item : resumeCareerList) {
			LocalDate hireYm = item.getHireYm().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate resignYm = item.getResignYm().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			int careerMonth = (int) ChronoUnit.MONTHS.between(hireYm, resignYm);
			
			if(item.getJobGroupId() != recJobGroupId) {
				careerAll += careerMonth;
			} else if(item.getJobGroupId() == recJobGroupId && item.getJobId() != recJobId) {
				careerJobGroup += careerMonth;
			} else if(item.getJobId() == recJobId) {
				careerJob += careerMonth;
			}
		}
		int careerJobYear = (int)Math.floor(careerJob/12); 
		
		careerScore += (int)Math.floor(careerAll/12) * 1;
		careerScore += (int)Math.floor(careerJobGroup/12) * 3;
		careerScore += resumeMatchService.selectCareerGradeScore(careerJobYear);
		
		if (careerScore > careerTotalScore) {
			careerScore = careerTotalScore;
		}

		matchScore = skillScore + schoolScore + careerScore;

		model.addAttribute("matchScore", matchScore);

		return "";
	}

}
