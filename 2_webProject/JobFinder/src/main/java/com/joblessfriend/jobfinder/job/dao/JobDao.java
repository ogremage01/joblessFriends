package com.joblessfriend.jobfinder.job.dao;

import com.joblessfriend.jobfinder.job.domain.JobVo;
import com.joblessfriend.jobfinder.recruitment.domain.RecruitmentVo;

public interface JobDao {


    JobVo getJobById(int jobPostId);
}
