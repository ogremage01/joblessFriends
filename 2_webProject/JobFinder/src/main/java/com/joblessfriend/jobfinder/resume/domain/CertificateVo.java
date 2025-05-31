package com.joblessfriend.jobfinder.resume.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CertificateVo {

	private int certificateId;// 자격증 ID
	private String certificateName;// 자격증명
	private String issuingAuthority;//  발급기관
	private int isActive;// 활성화 여부
	
}