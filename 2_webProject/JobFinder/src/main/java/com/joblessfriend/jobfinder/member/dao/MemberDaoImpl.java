package com.joblessfriend.jobfinder.member.dao;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.joblessfriend.jobfinder.member.domain.MemberVo;

@Repository
public class MemberDaoImpl implements MemberDao{

	@Autowired
	private SqlSession sqlSession;
	private String namespace = "com.joblessfriend.jobfinder.member.domain.";
	
	@Override
	public MemberVo memberExist(String email, String password) {
		// TODO Auto-generated method stub
		
		HashMap<String, String> paramMap = new HashMap<>();
		
		paramMap.put("email", email);
		paramMap.put("password", password);
		
		return sqlSession.selectOne(namespace + "memberExist", paramMap);
		
	}

}
