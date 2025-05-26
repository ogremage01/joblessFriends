package com.joblessfriend.jobfinder.recruitment.dao;

import com.joblessfriend.jobfinder.recruitment.domain.*;

import com.joblessfriend.jobfinder.util.SearchVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


import java.util.List;


public interface RecruitmentDao {
    List<JobGroupVo> jobGroupList();
    List<JobGroupVo> jobList(int jobGroupId);
    int getRecruitmentTotalCount(SearchVo searchVo);
    List<RecruitmentVo> recruitmentList(SearchVo searchVo);
    RecruitmentVo getRecruitmentId(int jobPostId);
    List<WelfareVo> selectWelfareByJobPostId(int jobPostId);
	void jobPostDelete(List<Integer> jobPostIdList);//공고 삭제
	void jobPostFileDelete(List<Integer> jobPostIdList);//공고첨부파일삭제
	void jobPostTagDelete(List<Integer> jobPostIdList);//공고태그삭제


    void insertRecruitment(RecruitmentVo recruitmentVo);
    void insertJobPostTag(RecruitmentVo recruitmentVo, List<Integer> tagIdList);
    void insertJobPostWelfare(WelfareVo Welfarevo);
    void insertJobPostFile(JobPostFileVo fileVo);
    void updateJobPostIdByTempKey(@Param("jobPostId") int jobPostId, @Param("tempKey") String tempKey);


    //update라인 //
    void updateRecruitment(RecruitmentVo recruitmentVo);
    // 기존 태그 삭제

    void deleteTagsByJobPostId(int jobPostId);

    // 태그 재삽입  insertJobPostTag 사용

    // 기존 복리후생 삭제
    void deleteWelfareByJobPostId(int jobPostId);

    // 복리후생 재삽입 insertJobPostFile  사용
    // 파일 테이블 연동 (TEMP_KEY → JOB_POST_ID 업데이트)   updateJobPostIdByTempKey 사용
    void increaseViews(int jobPostId);

    List<RecruitmentVo> adminRecruitmentList();
	List<CompanyRecruitmentVo> companyRecruitmentSelectList(int companyId);
//    필터
    public int countFilteredPosts(FilterRequestVo filterRequestVo);

    public List<RecruitmentVo> getFilteredRecruitmentList(FilterRequestVo filterRequestVo);
    int getFilteredRecruitmentTotalCount(FilterRequestVo filterRequestVo);
	void jobPostStop(List<Integer> jobPostIdList);
	List<RecruitmentVo> selectRecruitmentList(int memberId);



}
