package com.joblessfriend.jobfinder.member.service;

import java.util.List;

import com.joblessfriend.jobfinder.company.domain.CompanyVo;
import com.joblessfriend.jobfinder.member.domain.MemberVo;

public interface MemberService {

	MemberVo memberExist(String email, String password);

	int memberInsertOne(String email, String password);

	MemberVo memberEmailExist(String email);

	int memberDeleteOne(int memberId);

	int updatePassword(String password, int memberId);

	String generateUniqueNickname();

}
