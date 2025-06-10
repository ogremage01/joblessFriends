package com.joblessfriend.jobfinder.resume.domain;

import com.joblessfriend.jobfinder.recruitment.domain.JobPostAnswerVo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResumeApplyRequestVo {
    private int resumeId;
    private int jobPostId;
    private int memberId; // 서버에서 세션으로 세팅해도 무방
    private int matchScore;
    private List<JobPostAnswerVo> answerList;
}
