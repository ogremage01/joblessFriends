package com.joblessfriend.jobfinder.jobGroup.dao;

import java.util.List;

import com.joblessfriend.jobfinder.recruitment.domain.JobGroupVo;

public interface JobGroupDao {

	List<JobGroupVo> jobGroupSelectList(int page);

	List<JobGroupVo> jobGroupSelectList(int page, String keyword);

	int jobGroupCount(String keyword);

	int jobGroupCount();

}
