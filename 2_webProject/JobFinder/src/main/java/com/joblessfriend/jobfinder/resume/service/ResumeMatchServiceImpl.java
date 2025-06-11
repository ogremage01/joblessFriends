package com.joblessfriend.jobfinder.resume.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.joblessfriend.jobfinder.resume.dao.ResumeDao;
import com.joblessfriend.jobfinder.resume.dao.ResumeMatchDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joblessfriend.jobfinder.recruitment.domain.RecruitmentVo;

import com.joblessfriend.jobfinder.resume.domain.CareerVo;
import com.joblessfriend.jobfinder.resume.domain.ResumeVo;
import com.joblessfriend.jobfinder.resume.domain.SchoolVo;
import com.joblessfriend.jobfinder.skill.domain.SkillVo;
import com.joblessfriend.jobfinder.skill.service.SkillService;

@Service
public class ResumeMatchServiceImpl implements ResumeMatchService {

	@Autowired
	private ResumeDao resumeDao;

	@Autowired
	private ResumeMatchDao resumeMatchDao;

	@Autowired
	private SkillService skillService;

	@Override
	public int calculateMatchScore(ResumeVo resumeVo, RecruitmentVo recruitmentVo) {
		System.out.println("계산기 입장");
		int skillTotalScore = 40;
		int schoolTotalScore = 30;
		int careerTotalScore = 30;

		int jobPostId = recruitmentVo.getJobPostId();
		
		// 1. 스킬 점수
		List<SkillVo> resumeSkillList = resumeVo.getSkillList();
		List<SkillVo> recruitmentSkillList = skillService.postTagList(jobPostId);

		int skillScore = 0;

		int matchCnt = 0;

		System.out.println("resumeSkillList.size()" + resumeSkillList.size());
		System.out.println("recruitmentSkillList.size()" + recruitmentSkillList.size());
		
		if (resumeSkillList.size() != 0 && resumeSkillList != null) {
			for (SkillVo resumeSkillVo : resumeSkillList) {
				int resumeSkillId = resumeSkillVo.getTagId();
				
				for (SkillVo recruitmentSkillVo : recruitmentSkillList) {
					int recruitmentSKillId = recruitmentSkillVo.getTagId();
					
					if (resumeSkillId == recruitmentSKillId) {
						matchCnt++;
					}

				}
			}

			skillScore = (int)(Math.ceil(matchCnt * (skillTotalScore / recruitmentSkillList.size())));
		}

		skillScore = Math.min(skillScore, skillTotalScore);
		skillScore = Math.max(skillScore, 0);
		
		// 2. 학력 점수
		System.out.println("skillTotalScore = " + resumeVo.getSchoolList());
		List<SchoolVo> resumeSchoolList = resumeVo.getSchoolList();
		String schoolType = recruitmentVo.getEducation();
		int schoolScore = 0;
		int sortationGrade = 0;

		switch (schoolType) {
		case "고등학교 졸업":
			sortationGrade = 1;
			break;
		case "대학 졸업(2,3년)":
			sortationGrade = 2;
			break;
		case "대학교 졸업(4년)":
			sortationGrade = 3;
			break;
		case "대학원 석사졸업":
			sortationGrade = 4;
			break;
		case "대학원 박사졸업":
			sortationGrade = 5;
			break;
		}
		

		if (resumeSchoolList.size() == 0 || resumeSchoolList == null) {
			schoolScore = 0;
		} else if (resumeSchoolList != null) {
			for (SchoolVo resumeSchoolVo : resumeSchoolList) {
				String sortation = resumeSchoolVo.getSortation();
				String status = resumeSchoolVo.getStatus();
				int resumeSchoolSoration = 0;

				if ("high".equals(sortation)) {
					resumeSchoolSoration = 1;
				} else if ("univ2".equals(sortation)) {
					resumeSchoolSoration = 2;
				} else if ("univ4".equals(sortation)) {
					resumeSchoolSoration = 3;
				} else if ("master".equals(sortation)) {
					resumeSchoolSoration = 4;
				} else if ("doctor".equals(sortation)) {
					resumeSchoolSoration = 5;
				}

				if (!"졸업".equals(status)) {
					if ("졸업 예정".equals(status)) {
						schoolScore -= (int) Math.floor(schoolTotalScore * 0.05);
					} else if ("재학 중".equals(status)) {
						schoolScore -= (int) Math.floor(schoolTotalScore * 0.15);
					}
				}
				
				if(sortationGrade > resumeSchoolSoration) {
					schoolScore = 0;
				} else if (sortationGrade == resumeSchoolSoration) {
					schoolScore = schoolTotalScore;
				} else if (sortationGrade < resumeSchoolSoration) {
					schoolScore = schoolTotalScore * (1 - ((resumeSchoolSoration - sortationGrade) / 10));
				}
				
				
			}
		}
		
		schoolScore = Math.min(schoolScore, schoolTotalScore);
		schoolScore = Math.max(schoolScore, 0);
		
		// 3. 경력 점수
		List<CareerVo> resumeCareerList = resumeVo.getCareerList();

		String careerType = recruitmentVo.getCareerType();
		System.out.println("careerType = " + recruitmentVo.getCareerType());

		int careerScore = 0;
		int careerGrade = 0;
		
		switch (careerType) {
		case "신입":
			careerGrade = 1;
			break;
		case "1~3년":
			careerGrade = 2;
			break;
		case "3~5년":
			careerGrade = 3;
			break;
		case "5년이상":
			careerGrade = 4;
			break;
		}
		
		int careerAll = 0;
		int careerJobGroup = 0;
		int careerJob = 0;

		int careerJobYear = 0;

		int careerSize = 0;

		int recJobGroupId = recruitmentVo.getJobGroupId();
		int recJobId = recruitmentVo.getJobId();

		if (resumeCareerList != null) {
			for (CareerVo resumeCareerVo : resumeCareerList) {
				Date hireYm = resumeCareerVo.getHireYm();
				Date resignYm = resumeCareerVo.getResignYm();

				System.out.println("YM: " + hireYm + " , " + resignYm);

				// Calendar를 사용한 월 수 계산
				Calendar startCal = Calendar.getInstance();
				startCal.setTime(hireYm);
				Calendar endCal = Calendar.getInstance();
				endCal.setTime(resignYm);

				int yearDiff = endCal.get(Calendar.YEAR) - startCal.get(Calendar.YEAR);
				int monthDiff = endCal.get(Calendar.MONTH) - startCal.get(Calendar.MONTH);
				int careerMonth = yearDiff * 12 + monthDiff;

				if (resumeCareerVo.getJobId() == recJobId) {
					careerJob += careerMonth;
				} else if (resumeCareerVo.getJobGroupId() == recJobGroupId) {
					careerJobGroup += careerMonth;
				} else {
					careerAll += careerMonth;
				}

				careerSize++;
			}

			careerJobYear = (int) Math.floor(careerJob / 12);
			
			//careerScore += selectCareerGradeScore(careerJobYear);
			
			int resumeCareerGrade = selectCareerGrade(careerJobYear);
			
			if(careerGrade > resumeCareerGrade) {
				careerScore = 0;
			} else if (careerGrade == resumeCareerGrade) {
				careerScore = careerTotalScore;
			} else if (careerGrade < resumeCareerGrade) {
				careerScore = careerTotalScore * (1 - ((resumeCareerGrade - careerGrade) / 10));
			}
			
			careerScore += (int) Math.floor(careerAll / 12) * 0.5;
			careerScore += (int) Math.floor(careerJobGroup / 12) * 2;

		}

		careerScore = Math.min(careerScore, careerTotalScore);
		careerScore = Math.max(careerScore, 0);

		double careerEvgYear = 0.0; 
				
		if(resumeCareerList.size() != 0) {
			careerEvgYear = (Math.floor((((careerJob + careerJobGroup + careerAll) / 12.0) / resumeCareerList.size()) * 10.0)) / 10.0;
			
			if (careerEvgYear < 1 && careerSize != 0) {	
				careerScore = (int) Math.floor(careerScore * 0.9);
			}
		}


		System.out.println("▶ 스킬 점수 = " + skillScore);
		System.out.println("▶ 학력 점수 = " + schoolScore);
		System.out.println("▶ 경력 점수 = " + careerScore);
		System.out.println("▶ 평균 근속 = " + careerEvgYear + "년");
		System.out.println("▶ 총점 = " + (skillScore + schoolScore + careerScore));

		Integer total = (skillScore + schoolScore + careerScore);		
		
		return total;
	}

	@Override
	public int selectCareerGrade(int careerJobYear) {
		return resumeMatchDao.selectCareerGrade(careerJobYear);
	}
}
