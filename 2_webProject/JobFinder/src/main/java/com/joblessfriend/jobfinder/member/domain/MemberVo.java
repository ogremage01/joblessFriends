package com.joblessfriend.jobfinder.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberVo {
	
	private int memberId;
	private String email;
	private String password;
	private String nickname;
	private int resumeMax;
	
}
