package com.joblessfriend.jobfinder.community.domain;



import java.util.Date;

import com.joblessfriend.jobfinder.member.domain.MemberVo;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommunityVo extends MemberVo{
	
	@Id
	private int communityId;
	private int memberId;
	private String title;
	private String content;
	private Date createAt;
	private Date modifiedAt;
	private int views;
}
