package com.joblessfriend.jobfinder.member.dao;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.joblessfriend.jobfinder.auth.controller.AuthController;
import com.joblessfriend.jobfinder.member.domain.MemberVo;

@Repository
public class MemberDaoImpl implements MemberDao{
	
	private Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private SqlSession sqlSession;
	private String namespace = "com.joblessfriend.jobfinder.member.domain.";
	
	// 로그인
	@Override
	public MemberVo memberExist(String email, String password) {
		// TODO Auto-generated method stub
		
		HashMap<String, String> paramMap = new HashMap<>();
		
		paramMap.put("email", email);
		paramMap.put("password", password);
		
		return sqlSession.selectOne(namespace + "memberExist", paramMap);
		
	}
	
	// 닉네임 중복체크
	public boolean isNicknameExists(String nickname) {
		return sqlSession.selectOne(namespace + "isNicknameExists", nickname) != null;
	}
	
	// 회원가입
	@Override
	public int memberInsertOne(MemberVo memberVo) {
		// TODO Auto-generated method stub
		return sqlSession.insert(namespace + "memberInsertOne", memberVo);
	}

	// 이메일 중복체크
	@Override
	public MemberVo memberEmailExist(String email) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + "memberEmailExist", email);
	}
}
