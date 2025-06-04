package com.joblessfriend.jobfinder.resume.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joblessfriend.jobfinder.recruitment.domain.RecruitmentVo;
import com.joblessfriend.jobfinder.recruitment.service.RecruitmentService;
import com.joblessfriend.jobfinder.resume.dao.ResumeMatchDao;
import com.joblessfriend.jobfinder.resume.domain.CareerVo;
import com.joblessfriend.jobfinder.resume.domain.ResumeVo;
import com.joblessfriend.jobfinder.resume.domain.SchoolVo;
import com.joblessfriend.jobfinder.resume.service.ResumeMatchService;
import com.joblessfriend.jobfinder.resume.service.ResumeService;
import com.joblessfriend.jobfinder.skill.domain.SkillVo;
import com.joblessfriend.jobfinder.skill.service.SkillService;

@Service
public class ResumeMatchServiceImpl implements ResumeMatchService {

    @Autowired
    private ResumeService resumeService;
    
    @Autowired
    private RecruitmentService recruitmentService;
    
    @Autowired
    private SkillService skillService;
    
    @Autowired
    private ResumeMatchDao resumeMatchDao;

    @Override
    public int calculateMatchScore(int resumeId, int jobPostId) {
        int skillTotalScore = 30;
        int schoolTotalScore = 30;
        int careerTotalScore = 40;

        ResumeVo resumeVo = resumeService.getResumeByResumeId(resumeId);
        RecruitmentVo recruitmentVo = recruitmentService.getRecruitmentId(jobPostId);

        // 1. 스킬 점수
        List<SkillVo> resumeSkillList = skillService.resumeTagList(resumeId);
        List<SkillVo> recruitmentSkillList = skillService.postTagList(jobPostId);

        Set<Integer> resumeSkillTagIds = resumeSkillList.stream()
                .map(SkillVo::getTagId)
                .collect(Collectors.toSet());

        int matchCnt = 0;
        for (SkillVo recruitmentSkill : recruitmentSkillList) {
            if (resumeSkillTagIds.contains(recruitmentSkill.getTagId())) {
                matchCnt++;
            }
        }

        int skillScore = matchCnt * (skillTotalScore / recruitmentSkillList.size());
        if (skillScore > skillTotalScore) skillScore = skillTotalScore;
        if (skillScore < 0) skillScore = 0;

        // 2. 학력 점수
        List<SchoolVo> resumeSchoolList = resumeVo.getSchoolList();
        String schoolType = recruitmentVo.getEducation();
        int schoolScore = 0;

        switch (schoolType) {
            case "고등학교 졸업":
                schoolScore = schoolTotalScore;
                break;
            case "대학 졸업(2,3년)":
                schoolScore = (int) Math.floor(schoolTotalScore * 0.7);
                break;
            case "대학교 졸업(4년)":
                schoolScore = (int) Math.floor(schoolTotalScore * 0.5);
                break;
            case "대학원 석사졸업":
                schoolScore = (int) Math.floor(schoolTotalScore * 0.25);
                break;
            case "대학원 박사졸업":
                schoolScore = 0;
                break;
        }

        for (SchoolVo resumeSchoolVo : resumeSchoolList) {
            String status = resumeSchoolVo.getStatus();
            if (!status.equals("졸업")) {
                if (status.equals("졸업 예정")) {
                    schoolScore -= (int) Math.floor(schoolTotalScore * 0.05);
                } else if (status.equals("재학 중")) {
                    schoolScore -= (int) Math.floor(schoolTotalScore * 0.15);
                }
            }
        }

        if (schoolScore > schoolTotalScore) schoolScore = schoolTotalScore;
        if (schoolScore < 0) schoolScore = 0;

        // 3. 경력 점수
        List<CareerVo> resumeCareerList = resumeVo.getCareerList();
        String careerType = recruitmentVo.getCareerType();
        int careerScore = 0;

        switch (careerType) {
            case "신입":
                careerScore = careerTotalScore;
                break;
            case "1~3년":
                careerScore = (int) Math.floor(careerTotalScore * 0.625);
                break;
            case "3~5년":
                careerScore = (int) Math.floor(careerTotalScore * 0.375);
                break;
        }

        int careerAll = 0;
        int careerJobGroup = 0;
        int careerJob = 0;

        int recJobGroupId = recruitmentVo.getJobGroupId();
        int recJobId = recruitmentVo.getJobId();

        for (CareerVo resumeCareerVo : resumeCareerList) {
            LocalDate hireYm = resumeCareerVo.getHireYm().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate resignYm = resumeCareerVo.getResignYm().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (resignYm.isAfter(LocalDate.now())) {
                resignYm = LocalDate.now();
            }
            int careerMonth = (int) ChronoUnit.MONTHS.between(hireYm, resignYm);

            if (resumeCareerVo.getJobId() == recJobId) {
                careerJob += careerMonth;
            } else if (resumeCareerVo.getJobGroupId() == recJobGroupId) {
                careerJobGroup += careerMonth;
            } else {
                careerAll += careerMonth;
            }
        }

        int careerJobYear = (int) Math.floor(careerJob / 12);

        careerScore += (int) Math.floor(careerAll / 12) * 1;
        careerScore += (int) Math.floor(careerJobGroup / 12) * 3;
        careerScore += selectCareerGradeScore(careerJobYear);

        if (careerScore > careerTotalScore) careerScore = careerTotalScore;
        if (careerScore < 0) careerScore = 0;

        return skillScore + schoolScore + careerScore;
    }

    @Override
	public int selectCareerGradeScore(int careerJobYear) {
		// TODO Auto-generated method stub
		return resumeMatchDao.selectCareerGradeScore(careerJobYear);
	}
}
