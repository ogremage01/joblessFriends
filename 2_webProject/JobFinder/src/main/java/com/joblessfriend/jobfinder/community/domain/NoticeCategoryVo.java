package com.joblessfriend.jobfinder.community.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeCategoryVo {
	private int noticeCategoryId;
	private String noticeCategoryContent;
	private Date createAt;
	private Date modifiedAt;
	
}
