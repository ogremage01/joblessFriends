package com.joblessfriend.jobfinder.resume.domain;

import java.util.Date;

import lombok.Data;

@Data
public class SchoolVo {
	private String sortation; // 고등학교/대학교
	private String schoolName;
	private String yearOfGraduation;
	private String status; // 졸업/재학 등
	private String majorName; // 전공명
	private java.util.Date startDate; // 입학일자
	private java.util.Date endDate; // 졸업일자
	private int resumeId;
	@Override
	public String toString() {
		return "SchoolVo{" +
	            "sortation='" + sortation + '\'' +
	            ", schoolName='" + schoolName + '\'' +
	            ", majorName='" + majorName + '\'' +
	            ", startDate=" + startDate +
	            ", endDate=" + endDate +
	            ", status='" + status + '\'' +
	            ", resumeId=" + resumeId +
	            '}';
	}
	
	
}