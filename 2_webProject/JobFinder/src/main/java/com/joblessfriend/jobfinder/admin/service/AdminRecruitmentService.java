package com.joblessfriend.jobfinder.admin.service;

import com.joblessfriend.jobfinder.job.domain.JobVo;
import com.joblessfriend.jobfinder.recruitment.domain.*;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface AdminRecruitmentService {


	public void jobPostDelete(List<Integer> jobPostIdList);
	public List<RecruitmentVo> adminRecruitmentList();









}
