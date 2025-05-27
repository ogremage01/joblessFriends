package com.joblessfriend.jobfinder.admin.service;

import java.util.List;

import com.joblessfriend.jobfinder.jobGroup.domain.JobGroupVo;
import com.joblessfriend.jobfinder.util.SearchVo;



public interface AdminJobGroupService {

	List<JobGroupVo> jobGroupSelectList(SearchVo searchVo);

	int jobGroupCount(SearchVo searchVo);

}
