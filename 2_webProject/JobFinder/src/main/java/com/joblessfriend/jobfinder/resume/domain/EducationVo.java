package com.joblessfriend.jobfinder.resume.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EducationVo {
    private int eduId;
	private int resumeId;
	private String eduInstitution;
	private String eduName;
	private Date startDate;
	private Date endDate;
    private String content;
}
