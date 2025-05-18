package com.joblessfriend.jobfinder.recruitment.dao;

import com.joblessfriend.jobfinder.recruitment.domain.CompanyRecruitmentVo;
import com.joblessfriend.jobfinder.recruitment.domain.JobGroupVo;

import com.joblessfriend.jobfinder.recruitment.domain.RecruitmentVo;
import org.springframework.stereotype.Repository;


import java.util.List;


public interface RecruitmentDao {
    List<JobGroupVo> jobGroupList();
    List<JobGroupVo> jobList(int jobGroupId);
    public List<RecruitmentVo> recruitmentList();
    RecruitmentVo getRecruitmentId(int jobPostId);
	void jobPostDelete(List<Integer> jobPostIdList);//공고 삭제
	void jobPostFileDelete(List<Integer> jobPostIdList);//공고첨부파일삭제
	void jobPostTagDelete(List<Integer> jobPostIdList);//공고태그삭제


    void insertRecruitment(RecruitmentVo recruitmentVo);
    void insertJobPostTag(RecruitmentVo recruitmentVo, List<Integer> tagIdList);

	List<RecruitmentVo> adminRecruitmentList();
	List<CompanyRecruitmentVo> companyRecruitmentSelectList(int companyId);
	

}
