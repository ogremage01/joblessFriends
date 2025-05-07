package com.joblessfriend.jobfinder.recruitment.service;

import com.joblessfriend.jobfinder.recruitment.domain.JobGroupVo;

import java.util.List;

public interface RecruitmentService {
    //직군 직무
    public List<JobGroupVo> jobGroupList();
    //직무
    public List<JobGroupVo> jobList(int jobGroupId);
}
