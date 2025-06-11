package com.joblessfriend.jobfinder.resume.domain;

import java.util.Date;
import java.util.List;

import com.joblessfriend.jobfinder.skill.domain.SkillVo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResumeVo {
	
	private int resumeId;
	private String title;
	private String memberName;
	private Date birthDate;
	private String phoneNumber;
	private String email;
	private String address;
	private String selfIntroduction;
	private int memberId;
	private String profile;
	private int postalCodeId;
	private Date createDate;
	private Date modifyDate;
	private int matchScore;
	//private int jobGroupId;//deprecated.
	//private int jobId;//deprecated.

	private List<SkillVo> skillList;
	private List<CareerVo> careerList;
	private List<CertificateResumeVo> certificateList;
	private List<EducationVo> educationList;
	private List<PortfolioVo> portfolioList;
	private List<SchoolVo> schoolList;

	// getter, setter

	
}
