package com.joblessfriend.jobfinder.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.joblessfriend.jobfinder.admin.dao.AdminCompanyDao;
import com.joblessfriend.jobfinder.company.dao.CompanyDao;
import com.joblessfriend.jobfinder.company.domain.CompanyVo;
import com.joblessfriend.jobfinder.member.domain.MemberVo;
import com.joblessfriend.jobfinder.util.SearchVo;

@Service
public class AdminCompanyServiceImpl implements AdminCompanyService {
	
	@Autowired
	AdminCompanyDao companyDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;



	@Override
	public int companyCount(SearchVo searchVo) {
		// TODO Auto-generated method stub
		return companyDao.companyCount(searchVo);
	}

	@Override
	public CompanyVo companySelectOne(int companyId) {
		// TODO Auto-generated method stub
		return companyDao.companySelectOne(companyId);
	}

	@Override
	public int companyUpdateOne(CompanyVo existCompanyVo) {
		// TODO Auto-generated method stub
		return companyDao.companyUpdateOne(existCompanyVo);
		
	}


	@Override
	public int companyDeleteOne(int companyId) {
		// TODO Auto-generated method stub
		return companyDao.companyDeleteOne(companyId);
	}

	@Override
	public int companyDeleteList(List<Integer> companyIdList) {
		// TODO Auto-generated method stub
		return companyDao.companyDeleteList(companyIdList);
	}


	@Override
	public List<CompanyVo> companySelectList(SearchVo searchVo) {
		// TODO Auto-generated method stub
		return companyDao.companySelectList(searchVo);
	}


}
