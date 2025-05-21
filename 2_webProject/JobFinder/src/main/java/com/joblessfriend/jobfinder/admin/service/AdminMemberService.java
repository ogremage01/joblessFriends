package com.joblessfriend.jobfinder.admin.service;

import java.util.List;

import com.joblessfriend.jobfinder.company.domain.CompanyVo;
import com.joblessfriend.jobfinder.member.domain.MemberVo;

public interface AdminMemberService {

	
	List<MemberVo> memberSelectList(int page);

	int memberCount();

	List<MemberVo> memberSelectList(int page, String keyword);

	int memberCount(String keyword);

	MemberVo memberSelectOne(int memberId);

	int memberUpdateOne(MemberVo existMemberVo);

	int memberDeleteOne(int memberId);

	int memberDeleteList(List<Integer> memberIdList);



}
