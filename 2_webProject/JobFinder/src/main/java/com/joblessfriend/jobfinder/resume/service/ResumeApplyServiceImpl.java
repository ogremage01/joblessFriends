package com.joblessfriend.jobfinder.resume.service;

import com.joblessfriend.jobfinder.recruitment.domain.JobPostAnswerVo;
import com.joblessfriend.jobfinder.recruitment.domain.JobPostQuestionVo;
import com.joblessfriend.jobfinder.resume.dao.ResumeApplyDao;
import com.joblessfriend.jobfinder.resume.domain.*;
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
    public int applyResumeWithCopy(int resumeId, int jobPostId, int memberId, List<JobPostAnswerVo> answerList) {
        ResumeVo origin = resumeService.getResumeWithAllDetails(resumeId);

        ResumeVo applyCopy = new ResumeVo();
        applyCopy.setMemberId(memberId);
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

        // 3. 스킬 태그 복사
        List<Integer> tagIds = resumeApplyDao.getTagIdsByResumeId(resumeId);
        if (tagIds != null)
            tagIds.forEach(tagId -> resumeApplyDao.insertResumeTagCopy(applyId, tagId));

        // 4. 지원 이력 등록
        ResumeManageVo manageVo = new ResumeManageVo();
        manageVo.setJobPostId(jobPostId);
        manageVo.setMemberId(memberId);
        manageVo.setResumeFile(String.valueOf(applyId));
        manageVo.setStateId(1);

        resumeApplyDao.insertResumeManage(manageVo);

         // 🚀 한 번에 여러 개 받아옴
        List<Integer> newAnswerIds = resumeApplyDao.selectNextAnswerIds(answerList.size());  // 🚀 한 번에 여러 개 받아옴

        for (int i = 0; i < answerList.size(); i++) {
            JobPostAnswerVo answer = answerList.get(i);
            answer.setAnswerId(newAnswerIds.get(i)); // 시퀀스 미리 할당
            answer.setJobPostId(jobPostId);
            answer.setMemberId(memberId);
        }

        resumeApplyDao.insertAnswers(answerList);


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

}
