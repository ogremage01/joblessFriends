package com.joblessfriend.jobfinder.resume.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class CareerVo {
	private int resumeId; // 이력서 ID참조용
    private String companyName;
    private String departmentName;
    private String hireYm;
    private String resignYm;
    private String position;
    private String jobTitle;
    private String taskRole;           // 담당직무 (간략)
    private String workDescription;    // textarea 상세
    private String salary;
    
}
