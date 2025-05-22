package com.joblessfriend.jobfinder.admin.dao;

import java.util.List;

import com.joblessfriend.jobfinder.company.domain.CompanyVo;
import com.joblessfriend.jobfinder.util.SearchVo;

import org.springframework.stereotype.Repository;

@Repository
public interface AdminCompanyDao {

	public List<CompanyVo> companySelectList(SearchVo searchVo);


	public CompanyVo companySelectOne(int companyId);

	public int companyUpdateOne(CompanyVo existCompanyVo);


	public int companyDeleteOne(int companyId);

	public int companyDeleteList(List<Integer> companyIdList);



	public int companyCount(SearchVo searchVo);

}
