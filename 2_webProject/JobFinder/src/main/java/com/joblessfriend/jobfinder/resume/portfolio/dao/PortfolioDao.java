package com.joblessfriend.jobfinder.resume.portfolio.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.joblessfriend.jobfinder.resume.portfolio.domain.PortfolioVo;


public interface PortfolioDao {
	
	public List<PortfolioVo> portfolioAllList();
}	
