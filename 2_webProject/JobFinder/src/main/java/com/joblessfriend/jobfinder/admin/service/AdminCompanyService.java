package com.joblessfriend.jobfinder.admin.service;

import java.util.List;

import com.joblessfriend.jobfinder.company.domain.CompanyVo;
import com.joblessfriend.jobfinder.util.SearchVo;

public interface AdminCompanyService {



	int companyCount(SearchVo searchVo);

	CompanyVo companySelectOne(int companyId);

	int companyUpdateOne(CompanyVo existCompanyVo);


	int companyDeleteOne(int companyId);

	int companyDeleteList(List<Integer> companyIdList);


	List<CompanyVo> companySelectList(SearchVo searchVo);



}
