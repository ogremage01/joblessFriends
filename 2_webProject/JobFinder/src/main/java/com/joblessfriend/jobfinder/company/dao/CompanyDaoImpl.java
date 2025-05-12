package com.joblessfriend.jobfinder.company.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.joblessfriend.jobfinder.company.domain.CompanyVo;

@Repository
public class CompanyDaoImpl implements CompanyDao{
	
	@Autowired
	private SqlSession sqlSession;
	
	private String namespace = "com.joblessfriend.jobfinder.company.";
	

	@Override
	public List<CompanyVo> companySelectList(int page) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace + "companySelectList",page);
	}


	@Override
	public int companyCount() {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + "companyCount");
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
