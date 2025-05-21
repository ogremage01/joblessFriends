package com.joblessfriend.jobfinder.admin.dao;

import java.util.List;

import com.joblessfriend.jobfinder.company.domain.CompanyVo;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminCompanyDao {

	public List<CompanyVo> companySelectList(int page);

	public int companyCount();

	public CompanyVo companySelectOne(int companyId);

	public int companyUpdateOne(CompanyVo existCompanyVo);


	public int companyDeleteOne(int companyId);

	public int companyDeleteList(List<Integer> companyIdList);

	public List<CompanyVo> companySelectList(int page, String keyword);

	public int companyCount(String keyword);

}
