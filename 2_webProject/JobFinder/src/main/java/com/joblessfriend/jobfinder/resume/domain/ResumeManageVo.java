package com.joblessfriend.jobfinder.resume.domain;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResumeManageVo {
    private int rmId;           // 지원 번호 (PK)
    private int jobPostId;      // 채용공고 번호
    private int memberId;       // 회원 번호
    private String resumeFile;  // 복사된 이력서 식별자 (resumeId 또는 파일 내용)
    private int stateId;        // 지원 상태 번호
}
