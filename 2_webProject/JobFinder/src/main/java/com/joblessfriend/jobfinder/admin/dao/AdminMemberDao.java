package com.joblessfriend.jobfinder.admin.dao;

import java.util.List;

import com.joblessfriend.jobfinder.company.domain.CompanyVo;
import com.joblessfriend.jobfinder.member.domain.MemberVo;
import com.joblessfriend.jobfinder.util.SearchVo;

public interface AdminMemberDao {

	List<MemberVo> memberSelectAll(SearchVo searchVo);

	int memberSelectCount(SearchVo searchVo);

	MemberVo memberSelectOne(int memberId);

	int memberDeleteOne(int memberId);

}
