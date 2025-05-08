package com.joblessfriend.jobfinder.company.dao;

import java.util.List;

import com.joblessfriend.jobfinder.company.domain.CompanyVo;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyDao {

	public List<CompanyVo> companySelectList(int page);

	public int companyCount();

}
