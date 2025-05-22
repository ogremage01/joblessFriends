package com.joblessfriend.jobfinder.admin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.joblessfriend.jobfinder.company.domain.CompanyVo;
import com.joblessfriend.jobfinder.util.SearchVo;

@Repository
public class AdminCompanyDaoImpl implements AdminCompanyDao{
	
	@Autowired
	private SqlSession sqlSession;
	
	private String namespace = "com.joblessfriend.jobfinder.admin.dao.AdminCompanyDao.";
	

	@Override
	public List<CompanyVo> companySelectList(SearchVo searchVo) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace + "companySelectList",searchVo);
	}


	@Override
	public int companyCount(SearchVo searchVo) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + "companyCount", searchVo);
	}


	@Override
	public CompanyVo companySelectOne(int companyId) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + "companySelectOne",companyId);
	}


	@Override
	public int companyUpdateOne(CompanyVo existCompanyVo) {
		// TODO Auto-generated method stub
		return sqlSession.update(namespace + "companyUpdateOne",existCompanyVo);
		
	}



	@Override
	public int companyDeleteOne(int companyId) {
		// TODO Auto-generated method stub
		return sqlSession.delete(namespace + "companyDeleteOne", companyId);
	}


	@Override
	public int companyDeleteList(List<Integer> companyIdList) {
		// TODO Auto-generated method stub
		return sqlSession.delete(namespace + "companyDeleteList", companyIdList);
	}
	





}
