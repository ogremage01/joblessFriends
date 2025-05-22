package com.joblessfriend.jobfinder.recruitment.service;

import com.joblessfriend.jobfinder.job.domain.JobVo;
import com.joblessfriend.jobfinder.recruitment.domain.*;

import java.util.List;

import com.joblessfriend.jobfinder.util.SearchVo;
import org.springframework.http.ResponseEntity;

public interface RecruitmentService {
    //직군 직무
    public List<JobGroupVo> jobGroupList();
    //직무
    public List<JobGroupVo> jobList(int jobGroupId);

    //채용공고 전체


    int getRecruitmentTotalCount(SearchVo searchVo);
    public List<RecruitmentVo> recruitmentList(SearchVo searchVo);
    //채용공고 상세뷰
    RecruitmentVo getRecruitmentId(int jobPostId);
	public void jobPostDelete(List<Integer> jobPostIdList);
    List<WelfareVo> selectWelfareByJobPostId(int jobPostId);
    //
    public void insertRecruitment(RecruitmentVo recruitmentVo, List<Integer> tagIdList, List<WelfareVo> welfareList);

    void insertJobPostFile(JobPostFileVo fileVo);
    void updateJobPostIdByTempKey(int jobPostId, String tempKey);


    public int countFilteredPosts(FilterRequestVo filterRequestVo);


    public List<RecruitmentVo> getFilteredRecruitmentList(FilterRequestVo filterRequestVo);
    int getFilteredRecruitmentTotalCount(FilterRequestVo filterRequestVo);
	public boolean checkCompanyOwnsJobPost(int companyId, int jobPostId);

}
