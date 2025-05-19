package com.joblessfriend.jobfinder.resume.portfolio.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.joblessfriend.jobfinder.resume.portfolio.domain.PortfolioVo;


public interface PortfolioDao {
	
	public List<PortfolioVo> portfolioSelectList(int resumeId);

	public void portfolioInsertOne(PortfolioVo portfolioVo, MultipartHttpServletRequest mhr);	
	
	public void portfolioDeleteOne(int portfolioId, int resumeId);


}	
