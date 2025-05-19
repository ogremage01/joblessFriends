package com.joblessfriend.jobfinder.resume.portfolio.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.joblessfriend.jobfinder.resume.portfolio.domain.PortfolioVo;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Repository
public class PortfolioDaoImpl implements PortfolioDao{
	
	@Autowired
	private SqlSession sqlSession;
	
	String namespace = "com.joblessfreind.jobfinder.resume.portfolio.";

	@Override
	public List<PortfolioVo> portfolioSelectList(int resumeId) {
		return sqlSession.selectList(namespace + "portfolioSelectList", resumeId);
	}

	@Override
	public void portfolioDeleteOne(int portfolioId, int resumeId) {
		Map<String, Object> map = new HashMap<>();
		
		map.put("portfolioId", portfolioId);
		map.put("resumeId", resumeId);
		
		sqlSession.delete(namespace + "portfolioDeleteOne", map);
	}

	@Override
	public void portfolioInsertOne(PortfolioVo portfolioVo, MultipartHttpServletRequest mhr) {
		// TODO Auto-generated method stub
		
	}
	
	
}
