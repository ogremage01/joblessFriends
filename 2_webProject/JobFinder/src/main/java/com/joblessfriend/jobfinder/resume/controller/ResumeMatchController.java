package com.joblessfriend.jobfinder.resume.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.joblessfriend.jobfinder.member.domain.MemberVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.joblessfriend.jobfinder.recruitment.domain.RecruitmentVo;
import com.joblessfriend.jobfinder.recruitment.service.RecruitmentService;
import com.joblessfriend.jobfinder.resume.domain.CareerVo;
import com.joblessfriend.jobfinder.resume.domain.ResumeVo;
import com.joblessfriend.jobfinder.resume.domain.SchoolVo;
import com.joblessfriend.jobfinder.resume.service.CertificateService;
import com.joblessfriend.jobfinder.resume.service.ResumeMatchService;
import com.joblessfriend.jobfinder.resume.service.ResumeService;
import com.joblessfriend.jobfinder.skill.domain.SkillVo;
import com.joblessfriend.jobfinder.skill.service.SkillService;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

	@Autowired
	private ResumeMatchService resumeMatchService;

	@GetMapping("/resume/matchScore")
	@ResponseBody
	public Map<Integer, Integer> getMatchScores(
			@RequestParam int jobPostId,
			@RequestParam List<Integer> resumeIds, HttpSession session) {



		RecruitmentVo recruitmentVo = recruitmentService.getRecruitmentId(jobPostId); // 공고 상세 조회
		Map<Integer, Integer> scoreMap = new HashMap<>();

		for (Integer resumeId : resumeIds) {
			ResumeVo resumeVo = resumeService.getResumeWithAllDetails(resumeId);// 이력서 전체 항목 포함
			System.out.println("전체 항목 포함 = " + resumeVo);
			Integer score = resumeMatchService.calculateMatchScore(resumeVo, recruitmentVo);

			scoreMap.put(resumeId, score);
		}

		return scoreMap;
	}

	/*
	 * @GetMapping("/match") public String resumeManagement(int resumeId, int
	 * jobPostId, HttpSession session, Model model) { // null 체크 필요
	 * 
	 * int matchScore = 0; //총점 변수
	 * 
	 * //항목별 max 점수 int skillTotalScore = 30; int schoolTotalScore = 30; int
	 * careerTotalScore = 40;
	 * 
	 * ResumeVo resumeVo = resumeService.getResumeByResumeId(resumeId);
	 * RecruitmentVo recruitmentVo = recruitmentService.getRecruitmentId(jobPostId);
	 * 
	 * //스킬 List<SkillVo> resumeSkillList = skillService.resumeTagList(resumeId);
	 * List<SkillVo> recruitmentSkillList = skillService.postTagList(jobPostId);
	 * 
	 * int skillScore = 0;
	 * 
	 * for (int i = 0; i < recruitmentSkillList.size(); i++) { for (int j = 0; j <
	 * resumeSkillList.size(); j++) { if (recruitmentSkillList.get(i).getTagId() ==
	 * resumeSkillList.get(j).getTagId()) { matchCnt++; } } }
	 * 
	 * 
	 * // 1. 이력서의 스킬 태그 ID를 Set으로 변환 Set<Integer> resumeSkillTagIds =
	 * resumeSkillList.stream() .map(SkillVo::getTagId)
	 * .collect(Collectors.toSet());
	 * 
	 * // 2. 채용공고 스킬과 매칭 int matchCnt = 0; for (SkillVo recruitmentSkill :
	 * recruitmentSkillList) { if
	 * (resumeSkillTagIds.contains(recruitmentSkill.getTagId())) { matchCnt++; } }
	 * 
	 * // 3. 점수 계산 skillScore = matchCnt * (skillTotalScore /
	 * recruitmentSkillList.size());
	 * 
	 * if(skillScore > skillTotalScore) { skillScore = skillTotalScore; }
	 * 
	 * 
	 * // 학력 List<SchoolVo> resumeSchoolList = resumeVo.getSchoolList();
	 * 
	 * String schoolType = recruitmentVo.getEducation(); int sortationNo = 0; //
	 * 고등학교 high, 대학교(2, 3) univ2, 대학교(4) univ4, 대학원 석사 master, 대학원 박사 doctor int
	 * schoolScore = 0;
	 * 
	 * switch (schoolType) {
	 * 
	 * case "고등학교 졸업": schoolScore = schoolTotalScore; break; case "대학 졸업(2,3년)":
	 * schoolScore = (int)Math.floor(schoolTotalScore * 0.7); break; case
	 * "대학교 졸업(4년)": schoolScore = (int)Math.floor(schoolTotalScore * 0.5); break;
	 * case "대학원 석사졸업": schoolScore = (int)Math.floor(schoolTotalScore * 0.25);
	 * break; case "대학원 박사졸업": schoolScore = 0; break; }
	 * 
	 * // 모든 학력에 대해 졸업이 아니면 점수 차감 for (SchoolVo resumeSchoolVo : resumeSchoolList) {
	 * String status = resumeSchoolVo.getStatus(); if (!status.equals("졸업")) { if
	 * (status.equals("졸업 예정")) { schoolScore -= (int) Math.floor(schoolTotalScore *
	 * 0.05); } else if (status.equals("재학 중")) { schoolScore -= (int)
	 * Math.floor(schoolTotalScore * 0.15); } } }
	 * 
	 * if (schoolScore > schoolTotalScore) { schoolScore = schoolTotalScore; }
	 * 
	 * // 경력 List<CareerVo> resumeCareerList = resumeVo.getCareerList();
	 * 
	 * String careerType = recruitmentVo.getCareerType(); int careerScore = 0;
	 * 
	 * switch (careerType) {
	 * 
	 * case "신입": careerScore = careerTotalScore; break; case "1~3년": careerScore =
	 * (int)Math.floor(careerTotalScore * 0.625); break; case "3~5년": careerScore =
	 * (int)Math.floor(careerTotalScore * 0.375); break;
	 * 
	 * case "5년이상": careerScore = 0; break;
	 * 
	 * 
	 * }
	 * 
	 * // 모든 경력 0.5, 같은 직군 경력 3, 같은 직무 경력 int careerAll = 0; int careerJobGroup = 0;
	 * int careerJob = 0;
	 * 
	 * int recJobGroupId = recruitmentVo.getJobGroupId(); int recJobId =
	 * recruitmentVo.getJobId();
	 * 
	 * for (CareerVo resumeCareerVo : resumeCareerList) { LocalDate hireYm =
	 * resumeCareerVo.getHireYm().toInstant().atZone(ZoneId.systemDefault()).
	 * toLocalDate(); LocalDate resignYm =
	 * resumeCareerVo.getResignYm().toInstant().atZone(ZoneId.systemDefault()).
	 * toLocalDate(); int careerMonth = (int) ChronoUnit.MONTHS.between(hireYm,
	 * resignYm);
	 * 
	 * if (resumeCareerVo.getJobId() == recJobId) { careerJob += careerMonth; } else
	 * if (resumeCareerVo.getJobGroupId() == recJobGroupId) { careerJobGroup +=
	 * careerMonth; } else { careerAll += careerMonth; } }
	 * 
	 * int careerJobYear = (int)Math.floor(careerJob/12);
	 * 
	 * careerScore += (int)Math.floor(careerAll/12) * 1; careerScore +=
	 * (int)Math.floor(careerJobGroup/12) * 3; careerScore +=
	 * resumeMatchService.selectCareerGradeScore(careerJobYear);
	 * 
	 * if (careerScore > careerTotalScore) { careerScore = careerTotalScore; }
	 * 
	 * matchScore = skillScore + schoolScore + careerScore;
	 * 
	 * model.addAttribute("matchScore", matchScore);
	 * 
	 * return ""; }
	 */
	
	
}
