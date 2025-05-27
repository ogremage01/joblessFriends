package com.joblessfriend.jobfinder.resume.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CertificateVo {

	private int certificateId;
	private String certificateName;
	private String issuingAuthority;
	private int isActive;
	
}