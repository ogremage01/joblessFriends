package com.joblessfriend.jobfinder.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joblessfriend.jobfinder.member.dao.MemberDao;
import com.joblessfriend.jobfinder.member.domain.MemberVo;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private MemberDao memberDao;
	
	@Override
	public MemberVo memberExist(String email, String password) {
		// TODO Auto-generated method stub
		MemberVo memberVo = memberDao.memberExist(email, password);
		
		return memberVo;
	}

}
