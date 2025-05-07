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
	
	private String namespace = "com.joblessfriend.jobfinder.company.domain";
	

	@Override
	public List<CompanyVo> companySelectList() {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace + "companySelectList");
	}

}
