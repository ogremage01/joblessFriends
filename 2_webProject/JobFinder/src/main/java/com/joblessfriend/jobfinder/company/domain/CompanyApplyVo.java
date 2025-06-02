package com.joblessfriend.jobfinder.company.domain;

import com.joblessfriend.jobfinder.member.domain.MemberVo;
import com.joblessfriend.jobfinder.recruitment.domain.JobPostAnswerVo;
import com.joblessfriend.jobfinder.recruitment.domain.JobPostQuestionVo;
import com.joblessfriend.jobfinder.recruitment.domain.RecruitmentVo;
import com.joblessfriend.jobfinder.resume.domain.ResumeVo;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CompanyApplyVo {
    private MemberVo member;                 // 지원자 정보
    private CompanyVo company;               // 기업 정보
    private int stateId;                     // 지원 상태 코드
    private String stateName;                // 상태 이름
    private ResumeVo resumeVo;               // 복사된 이력서 정보
    private Date applyDate;                  // 지원날짜
    private RecruitmentVo recruitmentVo;     // 채용공고 정보
    private List<JobPostAnswerVo> answerList; //답변리스트
    private List<JobPostQuestionVo> questionList; //질문 리스트

    private int startRow;
    private int endRow;
}

