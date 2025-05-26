package com.joblessfriend.jobfinder.school.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MajorInfo {
	
	private String majorName;     // 학과명
    private String department;    // 세부학과명
    private String summary;       // 학과 개요
    private String lClass;        // 계열
    private String mClass;        // 학과분류
    private String majorSeq;      // 학과코드

}
