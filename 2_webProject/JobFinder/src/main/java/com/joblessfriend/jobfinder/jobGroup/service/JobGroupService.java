package com.joblessfriend.jobfinder.jobGroup.service;

import java.util.List;

import com.joblessfriend.jobfinder.recruitment.domain.JobGroupVo;

public interface JobGroupService {

	List<JobGroupVo> jobGroupSelectList(int page);

}
