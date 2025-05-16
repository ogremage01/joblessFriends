package com.joblessfriend.jobfinder.recruitment.dao;

import com.joblessfriend.jobfinder.recruitment.domain.JobGroupVo;

import com.joblessfriend.jobfinder.recruitment.domain.RecruitmentVo;
import org.springframework.stereotype.Repository;


import java.util.List;


public interface RecruitmentDao {
    List<JobGroupVo> jobGroupList();
    List<JobGroupVo> jobList(int jobGroupId);
    public List<RecruitmentVo> recruitmentList();
    RecruitmentVo getRecruitmentId(int jobPostId);
	int jobPostDelete(List<Integer> jobPostIdList);


    void insertRecruitment(RecruitmentVo recruitmentVo);
    void insertJobPostTag(RecruitmentVo recruitmentVo, List<Integer> tagIdList);

	List<RecruitmentVo> adminRecruitmentList();
	List<RecruitmentVo> companyRecruitmentSelectList(int companyId);

}
