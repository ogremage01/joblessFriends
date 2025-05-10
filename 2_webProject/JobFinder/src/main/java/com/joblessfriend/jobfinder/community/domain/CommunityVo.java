package com.joblessfriend.jobfinder.community.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommunityVo {
	private int communityId;
	private int memberId;
	private String title;
	private String content;
	private Date createAt;
	private Date modifiedAt;
	private int views;
}
