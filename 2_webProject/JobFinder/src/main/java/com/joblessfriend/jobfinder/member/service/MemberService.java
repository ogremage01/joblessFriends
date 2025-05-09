package com.joblessfriend.jobfinder.member.service;

import com.joblessfriend.jobfinder.member.domain.MemberVo;

public interface MemberService {

	MemberVo memberExist(String email, String password);

}
