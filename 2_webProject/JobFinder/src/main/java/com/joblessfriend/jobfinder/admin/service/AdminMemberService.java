package com.joblessfriend.jobfinder.admin.service;

import java.util.List;

import com.joblessfriend.jobfinder.company.domain.CompanyVo;
import com.joblessfriend.jobfinder.member.domain.MemberVo;
import com.joblessfriend.jobfinder.util.SearchVo;

public interface AdminMemberService {

	
	int memberCount(SearchVo searchVo);

	MemberVo memberSelectOne(int memberId);

	int memberUpdateOne(MemberVo existMemberVo);

	int memberDeleteOne(int memberId);

	int memberDeleteList(List<Integer> memberIdList);

	List<MemberVo> memberSelectList(SearchVo searchVo);



}
