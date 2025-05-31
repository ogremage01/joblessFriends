package com.joblessfriend.jobfinder.resume.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CertificateResumeVo  {

	private int certificateResumeId;// 자격증 ID
	private int resumeId; // 이력서  ID 참조용
	private String certificateName; // 자격증명
	private String issuingAuthority; // 발행처
	private Date acquisitionDate;// 취득일자
	
}