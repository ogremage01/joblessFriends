package com.joblessfriend.jobfinder.member.service;

import java.util.List;

import com.joblessfriend.jobfinder.company.domain.CompanyVo;
import com.joblessfriend.jobfinder.member.domain.MemberVo;

public interface MemberService {

	MemberVo memberExist(String email, String password);

	int memberInsertOne(String email, String password);

	MemberVo memberEmailExist(String email);

	List<MemberVo> memberSelectList(int page);

	int memberCount();

	List<MemberVo> memberSelectList(int page, String keyword);

	int memberCount(String keyword);

	MemberVo memberSelectOne(int memberId);

	int memberUpdateOne(MemberVo existMemberVo);

	int memberDeleteOne(int memberId);

	int memberDeleteList(List<Integer> memberIdList);

	int updatePassword(String password, int memberId);

	String generateUniqueNickname();

}
