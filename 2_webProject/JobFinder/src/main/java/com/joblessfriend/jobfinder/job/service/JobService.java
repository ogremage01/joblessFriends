package com.joblessfriend.jobfinder.job.service;

import java.util.List;

import com.joblessfriend.jobfinder.job.domain.JobVo;

public interface JobService {


	JobVo getJobById(int jobPostId);
    JobVo getJobByIdForRecruitment(int jobPostId);
    List<JobVo> selectJobsByGroupId(int jobGroupId);

}
