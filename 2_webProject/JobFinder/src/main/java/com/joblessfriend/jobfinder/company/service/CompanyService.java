package com.joblessfriend.jobfinder.company.service;

import java.util.List;

import com.joblessfriend.jobfinder.company.domain.CompanyVo;

public interface CompanyService {

	List<CompanyVo> companySelectList(int page);

	int companyCount();

	CompanyVo companySelectOne(int companyId);

	int companyUpdateOne(CompanyVo existCompanyVo);

}
