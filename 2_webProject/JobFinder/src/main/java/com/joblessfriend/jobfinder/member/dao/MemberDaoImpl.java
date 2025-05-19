package com.joblessfriend.jobfinder.member.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.joblessfriend.jobfinder.auth.controller.AuthController;
import com.joblessfriend.jobfinder.company.domain.CompanyVo;
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
	
	@Override
	public List<MemberVo> memberSelectList(int page) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace + "memberSelectList",page);
	}
	
	@Override
	public int memberCount() {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + "memberCount");
	}

	@Override
	public List<MemberVo> memberSelectList(int page, String keyword) {
		// TODO Auto-generated method stub
		
		Map<String, Object> paramMap = new HashMap<>();
	    paramMap.put("page", page);
	    paramMap.put("keyword", keyword);
	    
		return sqlSession.selectList(namespace + "memberSelectListByKeyword",paramMap);
	}

	@Override
	public int memberCount(String keyword) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + "memberCountByKeyword",keyword);
	}

	@Override
	public MemberVo memberSelectOne(int memberId) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + "memberSelectOne",memberId);
	}

	@Override
	public int memberUpdateOne(MemberVo existMemberVo) {
		// TODO Auto-generated method stub
		return sqlSession.update(namespace + "memberUpdateOne", existMemberVo);
	}

	@Override
	public int memberDeleteOne(int memberId) {
		// TODO Auto-generated method stub
		return sqlSession.delete(namespace + "memberDeleteOne", memberId);
	}

	@Override
	public int memberDeleteList(List<Integer> memberIdList) {
		// TODO Auto-generated method stub
		return sqlSession.delete(namespace + "memberDeleteList", memberIdList);
	}

	@Override
	public int updatePassword(String password, int memberId) {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap = new HashMap<>();
	    paramMap.put("password", password);
	    paramMap.put("memberId", memberId);
		return sqlSession.update(namespace + "updatePassword", paramMap);
	}
}
