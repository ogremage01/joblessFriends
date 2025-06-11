package com.joblessfriend.jobfinder.admin.dao;

import java.util.List;

import com.joblessfriend.jobfinder.jobGroup.domain.JobGroupVo;
import com.joblessfriend.jobfinder.util.SearchVo;


public interface AdminJobGroupDao {

	List<JobGroupVo> jobGroupSelectList(SearchVo searchVo);

	int jobGroupCount(SearchVo searchVo);
	
	// 직군 추가
	int insertJobGroup(String jobGroupName);
	
	// 직군 삭제 (단일/다중)
	int deleteJobGroups(List<Integer> jobGroupIdList);

}
