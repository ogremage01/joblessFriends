package com.joblessfriend.jobfinder.recruitment.service;

import com.joblessfriend.jobfinder.job.domain.JobVo;
import com.joblessfriend.jobfinder.recruitment.domain.JobGroupVo;
import com.joblessfriend.jobfinder.recruitment.domain.RecruitmentVo;

import java.util.List;

public interface RecruitmentService {
    //직군 직무
    public List<JobGroupVo> jobGroupList();
    //직무
    public List<JobGroupVo> jobList(int jobGroupId);

    //채용공고 전체

    public List<RecruitmentVo> recruitmentList();
    //채용공고 상세뷰
    RecruitmentVo getRecruitmentId(int jobPostId);
	public int jobPostDelete(List<Integer> jobPostIdList);
	public List<RecruitmentVo> adminRecruitmentList();
	public List<RecruitmentVo> companyRecruitmentSelectList(int companyId);

}
