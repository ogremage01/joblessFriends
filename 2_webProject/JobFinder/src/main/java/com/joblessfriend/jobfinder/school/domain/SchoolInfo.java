package com.joblessfriend.jobfinder.school.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SchoolInfo {

	private String schoolName; // 학교이름
    private String address; // 학교주소
    
    
    // 대학교용 추가 필드
    private String kindName; // 학교이름
}
