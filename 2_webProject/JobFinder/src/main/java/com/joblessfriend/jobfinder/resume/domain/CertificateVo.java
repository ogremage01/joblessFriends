package com.joblessfriend.jobfinder.resume.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CertificateVo {

	private int certificateId;
	private int resumeId;
	private String certificateName;
	private Date acquisitionDate;
	private String issuingAuthority;
	
}
