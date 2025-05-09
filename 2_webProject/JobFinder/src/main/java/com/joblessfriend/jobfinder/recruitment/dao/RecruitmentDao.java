package com.joblessfriend.jobfinder.recruitment.dao;

import com.joblessfriend.jobfinder.recruitment.domain.JobGroupVo;

import com.joblessfriend.jobfinder.recruitment.domain.RecruitmentVo;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface RecruitmentDao {
    List<JobGroupVo> jobGroupList();
    List<JobGroupVo> jobList(int jobGroupId);
    public List<RecruitmentVo> recruitmentList();
}
