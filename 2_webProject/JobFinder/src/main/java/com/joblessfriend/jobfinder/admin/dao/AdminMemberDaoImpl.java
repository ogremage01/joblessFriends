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

@Repository
public class AdminMemberDaoImpl implements AdminMemberDao {

	private Logger logger = LoggerFactory.getLogger(AdminMemberController.class);

	@Autowired
	private SqlSession sqlSession;
	private String namespace = "com.joblessfriend.jobfinder.admin.dao.AdminMemberDao.";

	
	

	@Override
	public List<MemberVo> memberSelectList(int page) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace + "memberSelectList", page);
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

		return sqlSession.selectList(namespace + "memberSelectListByKeyword", paramMap);
	}

	@Override
	public int memberCount(String keyword) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + "memberCountByKeyword", keyword);
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
