package com.joblessfriend.jobfinder.resume.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PortfolioVo {
	
	private int portfolioId;
	private int resumeId;
	private String fileName;
	private String storedFileName;
	private Date createAt;
	private Date modifiedAt;
	private String fileExtension;
	

}
