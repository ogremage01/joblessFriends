package com.joblessfriend.jobfinder.company.dao;

import java.util.List;

import com.joblessfriend.jobfinder.company.domain.CompanyVo;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyDao {

	public List<CompanyVo> companySelectList(int page);

	public int companyCount();

	public CompanyVo companySelectOne(int companyId);

	public int companyUpdateOne(CompanyVo existCompanyVo);


	public int companyDeleteOne(int companyId);

	public int companyDeleteList(List<Integer> companyIdList);

	public int companyInsertOne(CompanyVo companyVo);

	public CompanyVo companyExist(String email, String password);

	public CompanyVo companyFindId(String representative, String brn);

	public CompanyVo companyEmailExist(String email);

}
