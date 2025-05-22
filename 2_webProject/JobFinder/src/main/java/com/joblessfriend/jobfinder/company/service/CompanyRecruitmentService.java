package com.joblessfriend.jobfinder.company.service;

import com.joblessfriend.jobfinder.job.domain.JobVo;
import com.joblessfriend.jobfinder.recruitment.domain.*;

import java.util.List;

import com.joblessfriend.jobfinder.util.SearchVo;
import org.springframework.http.ResponseEntity;

public interface CompanyRecruitmentService {

	public void jobPostDelete(List<Integer> jobPostIdList);

	void jobPostStop(List<Integer> jobPostIdList);
	List<CompanyRecruitmentVo> companyRecruitmentSelectList(int companyId);

}
