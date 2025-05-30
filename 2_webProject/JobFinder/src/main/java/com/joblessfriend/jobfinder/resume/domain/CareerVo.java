package com.joblessfriend.jobfinder.resume.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class CareerVo {
	private int resumeId; // 이력서 ID참조용
    private String companyName; // 회사명
    private String departmentName;// 부서명
    private String hireYm;// 입사일자
    private String resignYm;// 퇴사일자
    private String position;// 직급
    private int jobGroupId; // 직군 ID
    private int jobId; // 직무 ID            
    private String workDescription;    // textarea 상세
    private String salary;// 연봉
    
}
