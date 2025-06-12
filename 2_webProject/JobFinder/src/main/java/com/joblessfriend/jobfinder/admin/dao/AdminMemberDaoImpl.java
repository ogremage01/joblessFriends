package com.joblessfriend.jobfinder.admin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.joblessfriend.jobfinder.admin.controller.AdminMemberController;
import com.joblessfriend.jobfinder.auth.controller.AuthController;
import com.joblessfriend.jobfinder.company.domain.CompanyVo;
import com.joblessfriend.jobfinder.member.domain.MemberVo;
import com.joblessfriend.jobfinder.util.SearchVo;

@Repository
public class AdminMemberDaoImpl implements AdminMemberDao {

	private Logger logger = LoggerFactory.getLogger(AdminMemberController.class);

	@Autowired
	private SqlSession sqlSession;
	private String namespace = "com.joblessfriend.jobfinder.admin.dao.AdminMemberDao.";

	
	

	@Override
	public List<MemberVo> memberSelectList(SearchVo searchVo) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace + "memberSelectList", searchVo);
	}

	@Override
	public int memberCount(SearchVo searchVo) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + "memberCount", searchVo);
	}

	@Override
	public MemberVo memberSelectOne(int memberId) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + "memberSelectOne", memberId);
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

}
