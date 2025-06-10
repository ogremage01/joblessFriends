package com.joblessfriend.jobfinder.resume.service;

import com.joblessfriend.jobfinder.recruitment.domain.JobPostAnswerVo;
import com.joblessfriend.jobfinder.recruitment.domain.JobPostQuestionVo;
import com.joblessfriend.jobfinder.resume.domain.ResumeVo;

import java.util.List;

public interface ResumeApplyService {

    /**
     * 기존 이력서 ID를 기반으로 지원용 복사 이력서를 생성하고 저장한다.
     * @param resumeId 복사할 원본 이력서 ID
     * @param memberId 현재 로그인된 사용자 ID
     * @return 생성된 복사 이력서의 ID (resume_apply_id)
     */
    int applyResumeWithCopy(int resumeId, int jobPostId, int memberId, List<JobPostAnswerVo> answerList, int matchScore);
    public List<JobPostQuestionVo> getQuestionsByJobPostId(int jobPostId);

    // 그대로 유지
    //답변갯수만큼시퀀스미리뽑기//
    void insertAnswersWithGeneratedIds(List<JobPostAnswerVo> answerList);
    //지원 중복체크//
    int hasAlreadyApplied(int memberId, int jobPostId);

    void insertAnswers(List<JobPostAnswerVo> answerList);
	ResumeVo getResumeWithAllDetails(int resumeId);
}
