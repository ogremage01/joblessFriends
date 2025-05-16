package com.joblessfriend.jobfinder.resume.portfolio.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joblessfriend.jobfinder.resume.portfolio.dao.PortfolioDao;
import com.joblessfriend.jobfinder.resume.portfolio.domain.PortfolioVo;

@Service
public class PortfolioServiceImpl implements PortfolioService {

	private Logger logger = LoggerFactory.getLogger(PortfolioServiceImpl.class);
	
	@Autowired	
	public PortfolioDao portfolioDao;
	
	
	@Override
	public List<PortfolioVo> portfolioAllList() {
		// TODO Auto-generated method stub
		return portfolioDao.portfolioAllList();
	}

	

}
