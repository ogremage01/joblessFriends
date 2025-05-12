package com.joblessfriend.jobfinder.member.service;

import com.joblessfriend.jobfinder.member.domain.MemberVo;

public interface MemberService {

	MemberVo memberExist(String email, String password);

	int memberInsertOne(String email, String password);

	MemberVo memberEmailExist(String email);

}
