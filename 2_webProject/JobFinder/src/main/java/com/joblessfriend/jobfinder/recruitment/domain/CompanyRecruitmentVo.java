package com.joblessfriend.jobfinder.recruitment.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import com.joblessfriend.jobfinder.skill.domain.SkillVo;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class CompanyRecruitmentVo {
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
    //private String jobImg;          // 채용공고 이미지
    private String careerType;      // 경력
    private String education;       // 학력
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;       // 접수 마감일
    private String jobName;         // 직업이름
    private String jobGroupName;    // 직군이름
    private Boolean isContinuous; //공고 마감 여부. 0:마감아님.1:마감
    private Integer maxApplicants;//최대 채용인원
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date modifiedDate; //최종수정일
    private List<SkillVo> skillList; //스킬리스트
    private int applicantCount;
    //상시채용건 추가필요//
}
