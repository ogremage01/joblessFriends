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
	public List<MemberVo> memberSelectAll(SearchVo searchVo) {
		return sqlSession.selectList(namespace + "memberSelectAll", searchVo);
	}

	@Override
	public int memberSelectCount(SearchVo searchVo) {
		return sqlSession.selectOne(namespace + "memberSelectCount", searchVo);
	}

	@Override
	public MemberVo memberSelectOne(int memberId) {
		return sqlSession.selectOne(namespace + "memberSelectOne", memberId);
	}

	@Override
	public int memberDeleteOne(int memberId) {
		return sqlSession.delete(namespace + "memberDeleteOne", memberId);
	}

}
