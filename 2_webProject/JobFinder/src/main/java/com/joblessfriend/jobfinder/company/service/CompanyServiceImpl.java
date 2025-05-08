package com.joblessfriend.jobfinder.company.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joblessfriend.jobfinder.company.dao.CompanyDao;
import com.joblessfriend.jobfinder.company.domain.CompanyVo;

@Service
public class CompanyServiceImpl implements CompanyService {
	
	@Autowired
	CompanyDao companyDao;

	@Override
	public List<CompanyVo> companySelectList(int page) {
		// TODO Auto-generated method stub
		
		return companyDao.companySelectList(page);
	}

	@Override
	public int companyCount() {
		// TODO Auto-generated method stub
		return companyDao.companyCount();
	}

}
