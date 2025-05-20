package com.joblessfriend.jobfinder.job.dao;

import java.util.List;

import com.joblessfriend.jobfinder.job.domain.JobVo;

public interface JobDao {

    JobVo getJobByIdForRecruitment(int jobPostId);
    
	List<JobVo> selectJobsByGroupId(int jobGroupId); 
}
