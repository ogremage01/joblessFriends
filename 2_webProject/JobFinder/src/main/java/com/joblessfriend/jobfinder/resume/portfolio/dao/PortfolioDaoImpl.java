package com.joblessfriend.jobfinder.resume.portfolio.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.joblessfriend.jobfinder.resume.portfolio.domain.PortfolioVo;

public class PortfolioDaoImpl implements PortfolioDao{
	
	@Autowired
	private SqlSession sqlSession;
	
	String namespace = "com.joblessfreind.jobfinder.resume.portfolio.";

	@Override
	public List<PortfolioVo> portfolioAllList() {
		return sqlSession.selectList(namespace + "portfolioAllList");
	}
	
	
}
