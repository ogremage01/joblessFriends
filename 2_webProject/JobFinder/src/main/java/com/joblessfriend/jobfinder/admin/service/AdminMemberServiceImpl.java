package com.joblessfriend.jobfinder.admin.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joblessfriend.jobfinder.admin.dao.AdminMemberDao;
import com.joblessfriend.jobfinder.auth.controller.AuthController;
import com.joblessfriend.jobfinder.member.domain.MemberVo;
import com.joblessfriend.jobfinder.util.SearchVo;

@Service
public class AdminMemberServiceImpl implements AdminMemberService{
	
	private Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	private AdminMemberDao memberDao;
	

	
	@Override//관리자용
	public List<MemberVo> memberSelectList(SearchVo searchVo) {
		// TODO Auto-generated method stub
		return memberDao.memberSelectList(searchVo);
	}
	
	@Override//관리자용
	public int memberCount(SearchVo searchVo) {
		// TODO Auto-generated method stub
		return memberDao.memberCount(searchVo);
	}

	@Override//관리자용
	public MemberVo memberSelectOne(int memberId) {
		// TODO Auto-generated method stub
		return memberDao.memberSelectOne(memberId);
	}

	@Override//관리자용
	public int memberUpdateOne(MemberVo existMemberVo) {
		// TODO Auto-generated method stub
		return memberDao.memberUpdateOne(existMemberVo);
	}

	@Override//관리자.이용자 겸용(분할해야함)
	public int memberDeleteOne(int memberId) {
		// TODO Auto-generated method stub
		return memberDao.memberDeleteOne(memberId);
	}

	@Override//관리자용
	public int memberDeleteList(List<Integer> memberIdList) {
		// TODO Auto-generated method stub
		return memberDao.memberDeleteList(memberIdList);
	}

	

}
