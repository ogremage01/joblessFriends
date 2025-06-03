package com.joblessfriend.jobfinder.member.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplyPostVo {
	private Date applyDate;
	private String title;
	private String companyName;
	private String state;
	private int jobPostId;
	private int companyId;

}
