package com.joblessfriend.jobfinder.job.dao;

import java.util.List;

import com.joblessfriend.jobfinder.job.domain.JobVo;
import com.joblessfriend.jobfinder.recruitment.domain.RecruitmentVo;

public interface JobDao {


	JobVo getJobById(int jobPostId);
    JobVo getJobByIdForRecruitment(int jobPostId);
    List<JobVo> selectJobsByGroupId(int jobGroupId);
    String getJobNameById(int jobId);
}
