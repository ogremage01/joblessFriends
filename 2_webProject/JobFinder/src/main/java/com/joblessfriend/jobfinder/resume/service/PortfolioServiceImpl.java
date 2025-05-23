package com.joblessfriend.jobfinder.resume.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.joblessfriend.jobfinder.resume.dao.PortfolioDao;
import com.joblessfriend.jobfinder.resume.domain.PortfolioVo;

@Service
public class PortfolioServiceImpl implements PortfolioService {

	private Logger logger = LoggerFactory.getLogger(PortfolioServiceImpl.class);
	
	@Autowired	
	public PortfolioDao portfolioDao;
	
	
	@Override
	public List<PortfolioVo> portfolioSelectList(int resumeId) {
		return portfolioDao.portfolioSelectList(resumeId);
	}

	@Override
	public void portfolioInsertOne(PortfolioVo portfolioVo, MultipartHttpServletRequest mhr) {
		// TODO Auto-generated method stub
		portfolioDao.portfolioInsertOne(portfolioVo, mhr);
	}
	
	@Override
	public void portfolioDeleteOne(int portfolioId, int resumeId) {
		portfolioDao.portfolioDeleteOne(portfolioId, resumeId);
	}

	

	

}
