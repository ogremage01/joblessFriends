package com.joblessfriend.jobfinder.recruitment.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
public class RecruitmentVo {
    private Integer jobPostId;          // 채용공고 번호
    private int companyId;          // 기업 번호
    private String companyName;     // 기업 이름
    private String title;           // 채용공고 제목
    private String content;         // 채용공고 내용
    private int jobGroupId;         // 직군 번호
    private int jobId;              // 직무 번호
    private int views;              // 조회수
    private String salary;             //급여
    private String workHours;       //근무 시간
    private String jobImg;          // 채용공고 이미지
    private String careerType;      // 경력
    private String education;       // 학력
    private String tempKey;         // 이미지 임시저장용 컬럼
    private String templateType;    //템플릿타입
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;       // 접수 마감일
    private String jobName;         // 직업이름
    private String jobGroupName;    // 직군이름
    private int isContinuous;   //마감
    private Integer maxApplicants;
    //상시채용건 추가필요//
}
