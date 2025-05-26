package com.joblessfriend.jobfinder.company.service;

import java.util.List;

import com.joblessfriend.jobfinder.company.domain.CompanyVo;

public interface CompanyService {

	CompanyVo companySelectOne(int companyId);

	int companyUpdateOne(CompanyVo existCompanyVo);

	int companyDeleteOne(int companyId);

	int companyInsertOne(CompanyVo companyVo);

	CompanyVo companyExist(String email, String password);

	CompanyVo companyFindId(String representative, String brn);

	CompanyVo companyEmailExist(String email);

	int updatePassword(String tempPwd, int companyId);

}
