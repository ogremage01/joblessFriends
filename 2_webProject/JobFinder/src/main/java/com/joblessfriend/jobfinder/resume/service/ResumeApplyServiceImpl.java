package com.joblessfriend.jobfinder.resume.service;

import com.joblessfriend.jobfinder.recruitment.domain.JobPostAnswerVo;
import com.joblessfriend.jobfinder.recruitment.domain.JobPostQuestionVo;
import com.joblessfriend.jobfinder.resume.dao.ResumeApplyDao;
import com.joblessfriend.jobfinder.resume.domain.*;
import com.joblessfriend.jobfinder.skill.domain.SkillVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResumeApplyServiceImpl implements ResumeApplyService {

    @Autowired
    private ResumeApplyDao resumeApplyDao;

    @Autowired
    private ResumeService resumeService;

    @Override
    @Transactional
    public int applyResumeWithCopy(int resumeId, int jobPostId, int memberId, List<JobPostAnswerVo> answerList, int matchScore) {
        ResumeVo origin = resumeService.getResumeWithAllDetails(resumeId);

        ResumeVo applyCopy = new ResumeVo();
        applyCopy.setMemberId(memberId);
        applyCopy.setTitle(origin.getTitle());
        applyCopy.setMemberName(origin.getMemberName());
        applyCopy.setBirthDate(origin.getBirthDate());
        applyCopy.setPhoneNumber(origin.getPhoneNumber());
        applyCopy.setEmail(origin.getEmail());
        applyCopy.setSelfIntroduction(origin.getSelfIntroduction());
        applyCopy.setProfile(origin.getProfile());
        applyCopy.setSchoolList(origin.getSchoolList());
        applyCopy.setCareerList(origin.getCareerList());
        applyCopy.setEducationList(origin.getEducationList());
        applyCopy.setCertificateList(origin.getCertificateList());
        applyCopy.setPortfolioList(origin.getPortfolioList());
        applyCopy.setAddress(origin.getAddress());
        applyCopy.setMatchScore(matchScore);


        int originId = origin.getResumeId();
        // 1. 이력서 복사
        resumeApplyDao.insertResumeCopy(applyCopy);
        int applyId = applyCopy.getResumeId();

        // 2. 하위 항목 복사
        if (origin.getSchoolList() != null)
            origin.getSchoolList().forEach(s -> {
                s.setResumeId(applyId);
                resumeApplyDao.insertSchool(s);
            });

        if (origin.getCareerList() != null)
            origin.getCareerList().forEach(c -> {
                c.setResumeId(applyId);
                resumeApplyDao.insertCareer(c);
            });

        if (origin.getEducationList() != null)
            origin.getEducationList().forEach(e -> {
                e.setResumeId(applyId);
                resumeApplyDao.insertEducation(e);
            });

        if (origin.getPortfolioList() != null)
            origin.getPortfolioList().forEach(p -> {
                p.setResumeId(applyId);
                resumeApplyDao.insertPortfolio(p);
            });
        if (origin.getCertificateList() != null) {
            origin.getCertificateList().forEach(cert -> {
                cert.setResumeId(applyId); // 복사 대상 resumeId 설정
                resumeApplyDao.insertCertificateResume(cert);
            });
        }


        // 3. 스킬 태그 복사 - 원본 이력서의 스킬 사용
        List<SkillVo> originSkills = origin.getSkillList();
        if (originSkills != null && !originSkills.isEmpty()) {
            originSkills.forEach(skill -> resumeApplyDao.insertResumeTagCopy(applyId, skill.getTagId()));
        }

        // 4. 지원 이력 등록
        ResumeManageVo manageVo = new ResumeManageVo();
        manageVo.setRmId(applyId);
        manageVo.setJobPostId(jobPostId);
        manageVo.setMemberId(memberId);
        manageVo.setResumeFile(String.valueOf(originId));
        manageVo.setStateId(1);

        resumeApplyDao.insertResumeManage(manageVo);

         // 🚀 한 번에 여러 개 받아옴
        //리스트가없을경우 그냥 지원 //
        if (answerList != null && !answerList.isEmpty()) {
            List<Integer> newAnswerIds = resumeApplyDao.selectNextAnswerIds(answerList.size());

            for (int i = 0; i < answerList.size(); i++) {
                JobPostAnswerVo answer = answerList.get(i);
                answer.setAnswerId(newAnswerIds.get(i));
                answer.setJobPostId(jobPostId);
                answer.setMemberId(memberId);
            }

            resumeApplyDao.insertAnswers(answerList);
        }

        return applyId;
    }





    @Override
    public List<JobPostQuestionVo> getQuestionsByJobPostId(int jobPostId) {
        return resumeApplyDao.findQuestionsByJobPostId(jobPostId);
    }

    @Override
    public void insertAnswersWithGeneratedIds(List<JobPostAnswerVo> answerList) {

    }


    @Override
    public int hasAlreadyApplied(int memberId, int jobPostId) {
        return resumeApplyDao.countByMemberAndJobPost(memberId, jobPostId);
    }

    @Override
    public void insertAnswers(List<JobPostAnswerVo> answerList) {
        resumeApplyDao.insertAnswers(answerList);
    }





	@Override
	public ResumeVo getResumeWithAllDetails(int resumeId) {
		// TODO Auto-generated method stub
		return resumeApplyDao.getResumeWithAllDetails(resumeId);
	}

}
