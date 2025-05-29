package com.joblessfriend.jobfinder.resume.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResumeSaveRequestVo {
	// 이력서 제목
	private String title;
	
	// 인적사항
	@JsonProperty("name")
	private String memberName;
	
	@JsonProperty("birthdate")
    private Date birthDate;
    private String phoneNumber;
    private String email;
    private String address;
    private String selfIntroduction;
    private String profile;
    private int postalCodeId;
    private int jobGroupId;
    private int jobId;

    // 학력(고등학교/대학교)
    private List<SchoolVo> schools;

    // 교육
    private List<EducationVo> educations;

    // 경력
    private List<CareerVo> careers;

    // 자격증 (CERTIFICATE_RESUME용)
    private List<Long> certificateIds;

    // 스킬 태그
    private List<Long> tagIds;

    // 포트폴리오 (파일명 리스트)
    private List<PortfolioVo> portfolios;
}


