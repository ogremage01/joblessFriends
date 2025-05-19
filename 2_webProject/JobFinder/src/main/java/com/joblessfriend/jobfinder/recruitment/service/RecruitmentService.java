package com.joblessfriend.jobfinder.recruitment.service;

import com.joblessfriend.jobfinder.job.domain.JobVo;
import com.joblessfriend.jobfinder.recruitment.domain.CompanyRecruitmentVo;
import com.joblessfriend.jobfinder.recruitment.domain.FilterRequestVo;
import com.joblessfriend.jobfinder.recruitment.domain.JobGroupVo;
import com.joblessfriend.jobfinder.recruitment.domain.RecruitmentVo;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface RecruitmentService {
    //직군 직무
    public List<JobGroupVo> jobGroupList();
    //직무
    public List<JobGroupVo> jobList(int jobGroupId);

    //채용공고 전체

    public List<RecruitmentVo> recruitmentList();
    //채용공고 상세뷰
    RecruitmentVo getRecruitmentId(int jobPostId);
	public void jobPostDelete(List<Integer> jobPostIdList);

    public void insertRecruitment(RecruitmentVo recruitmentVo, List<Integer> tagIdList);

	public List<RecruitmentVo> adminRecruitmentList();
	public List<CompanyRecruitmentVo> companyRecruitmentSelectList(int companyId);

    public int countFilteredPosts(FilterRequestVo filterRequestVo);


    public List<RecruitmentVo> getFilteredRecruitmentList(FilterRequestVo filterRequestVo);

	public boolean checkCompanyOwnsJobPost(int companyId, int jobPostId);
	public void jobPostStop(List<Integer> jobPostIdList);

}
