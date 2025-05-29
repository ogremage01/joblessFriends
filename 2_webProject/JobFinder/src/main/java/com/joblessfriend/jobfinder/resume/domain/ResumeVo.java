package com.joblessfriend.jobfinder.resume.domain;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResumeVo {

	    private int resumeId;
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
	    private int jobGroupId;
	    private int jobId;
	    
	    private List<CareerVo> careerList;
	    private List<CertificateVo> certificateList;
	    private List<EducationVo> educationList;
	    private List<PortfolioVo> portfolioList;
	    private List<SchoolVo> schoolList;
	    
	    // getter, setter
	    
}
