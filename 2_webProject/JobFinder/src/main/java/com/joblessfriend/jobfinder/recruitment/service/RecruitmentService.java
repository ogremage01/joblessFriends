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
    void updateRecruitment(RecruitmentVo vo, List<Integer> tagList, List<WelfareVo> welfareList, String tempKey);
    //update라인 //

    // 기존 태그 삭제

    void deleteTagsByJobPostId(int jobPostId);

    // 태그 재삽입  insertJobPostTag 사용

    // 기존 복리후생 삭제
    void deleteWelfareByJobPostId(int jobPostId);

    // 복리후생 재삽입 insertJobPostFile  사용
    // 파일 테이블 연동 (TEMP_KEY → JOB_POST_ID 업데이트)   updateJobPostIdByTempKey 사용

    void increaseViews(int jobPostId);
    public int countFilteredPosts(FilterRequestVo filterRequestVo);


    public List<RecruitmentVo> getFilteredRecruitmentList(FilterRequestVo filterRequestVo);
    int getFilteredRecruitmentTotalCount(FilterRequestVo filterRequestVo);
	public boolean checkCompanyOwnsJobPost(int companyId, int jobPostId);

}
