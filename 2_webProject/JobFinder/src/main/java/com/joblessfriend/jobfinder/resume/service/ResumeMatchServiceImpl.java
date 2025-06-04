package com.joblessfriend.jobfinder.resume.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.joblessfriend.jobfinder.resume.dao.ResumeDao;
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
    ResumeDao resumeDao;

    @Autowired
    private SkillService skillService;

    @Override
    public int calculateMatchScore(ResumeVo resumeVo, RecruitmentVo recruitmentVo) {
        System.out.println("계산기 입장");
        int skillTotalScore = 30;
        int schoolTotalScore = 30;
        int careerTotalScore = 40;

        int resumeId = resumeVo.getResumeId();
        System.out.println("RESUME_ID: " + resumeId);
        int jobPostId = recruitmentVo.getJobPostId();
        System.out.println("스킬직전");
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
        System.out.println("스킬직전");
        int skillScore = recruitmentSkillList.isEmpty() ? 0 :
                matchCnt * (skillTotalScore / recruitmentSkillList.size());

        skillScore = Math.min(skillScore, skillTotalScore);
        skillScore = Math.max(skillScore, 0);
        System.out.println("skillScore = " + skillScore);
        // 2. 학력 점수
        System.out.println("skillTotalScore = " + resumeVo.getSchoolList());
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
            if (!"졸업".equals(status)) {
                if ("졸업 예정".equals(status)) {
                    schoolScore -= (int) Math.floor(schoolTotalScore * 0.05);
                } else if ("재학 중".equals(status)) {
                    schoolScore -= (int) Math.floor(schoolTotalScore * 0.15);
                }
            }

        }

        schoolScore = Math.min(schoolScore, schoolTotalScore);
        schoolScore = Math.max(schoolScore, 0);
        System.out.println("schoolScore = " + schoolScore);
        // 3. 경력 점수
        List<CareerVo> resumeCareerList = resumeVo.getCareerList();
        System.out.println(resumeCareerList+"??????????????");
        String careerType = recruitmentVo.getCareerType();
        System.out.println("careerType = " + recruitmentVo.getCareerType());
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
            case "5년이상":
                careerScore = (int) Math.floor(careerTotalScore * 0.375);
                break;
        }
        System.out.println("careerScore = " + careerScore);
        int careerAll = 0;
        int careerJobGroup = 0;
        int careerJob = 0;

//        int recJobGroupId = recruitmentVo.getJobGroupId();
        int recJobId = recruitmentVo.getJobId();

//        System.out.println("recJobGroupId = " + recJobGroupId);
        System.out.println("recJobId = " + recJobId);
//        for (CareerVo resumeCareerVo : resumeCareerList) {
//            LocalDate hireYm = resumeCareerVo.getHireYm().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//            LocalDate resignYm = resumeCareerVo.getResignYm().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//            if (resignYm.isAfter(LocalDate.now())) {
//                resignYm = LocalDate.now();
//            }
//            int careerMonth = (int) ChronoUnit.MONTHS.between(hireYm, resignYm);
//
//            if (resumeCareerVo.getJobId() == recJobId) {
//                careerJob += careerMonth; }
////            else if (resumeCareerVo.getJobGroupId() == recJobGroupId) {
////                careerJobGroup += careerMonth;
////            }
//                else {careerAll += careerMonth;
//            }
//        }

//        int careerJobYear = (int) Math.floor(careerJob / 12);
//
//        careerScore += (int) Math.floor(careerAll / 12) * 1;
//        careerScore += (int) Math.floor(careerJobGroup / 12) * 3;
//        careerScore += selectCareerGradeScore(careerJobYear);
//
//        careerScore = Math.min(careerScore, careerTotalScore);
//        careerScore = Math.max(careerScore, 0);
        System.out.println("▶ 스킬 점수 = " + skillScore);
        System.out.println("▶ 학력 점수 = " + schoolScore);
        System.out.println("▶ 경력 점수 = " + careerScore);
        System.out.println("▶ 총점 = " + (skillScore + schoolScore + careerScore));
        Integer total = (skillScore + schoolScore + careerScore);
        return total;
    }

    @Override
    public int selectCareerGradeScore(int careerJobYear) {
        return resumeDao.selectCareerGradeScore(careerJobYear);
    }
}
