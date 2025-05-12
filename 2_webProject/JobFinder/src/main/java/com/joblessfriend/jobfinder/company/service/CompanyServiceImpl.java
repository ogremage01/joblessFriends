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

	// 기업회원가입
	@Override
	public int companyInsertOne(CompanyVo companyVo) {
		// TODO Auto-generated method stub
		return companyDao.companyInsertOne(companyVo);
	}

	// 기업로그인
	@Override
	public CompanyVo companyExist(String email, String password) {
		// TODO Auto-generated method stub
		return companyDao.companyExist(email, password);
	}
	
	// Id 찾기
	@Override
	public CompanyVo companyFindId(String representative, String brn) {
		// TODO Auto-generated method stub
		return companyDao.companyFindId(representative, brn);
	}

	// 이메일 중복확인
	@Override
	public CompanyVo companyEmailExist(String email) {
		// TODO Auto-generated method stub
		return companyDao.companyEmailExist(email);

	}

}
