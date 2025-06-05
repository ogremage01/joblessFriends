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
    void insertQuestion(JobPostQuestionVo questionVo);
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
    void deleteQuestionsByJobPostId(int jobPostId);

    //질문지//
    void deleteAnswersByJobPostId(int jobPostId);
    void updateQuestionTextByOrder(JobPostQuestionVo questionVo);
    List<JobPostQuestionVo> getRecruitmentQuestion(int jobPostId);
    List<RecruitmentVo> adminRecruitmentList();
	List<CompanyRecruitmentVo> companyRecruitmentSelectList(int companyId);
//    필터
    public int countFilteredPosts(FilterRequestVo filterRequestVo);

    public List<RecruitmentVo> getFilteredRecruitmentList(FilterRequestVo filterRequestVo);
    int getFilteredRecruitmentTotalCount(FilterRequestVo filterRequestVo);
	void jobPostStop(List<Integer> jobPostIdList);
	List<RecruitmentVo> selectRecruitmentList(int memberId);
	
	//메인용 list
	List<RecruitmentVo> recruitmentListLatest(SearchVo searchVo);
	List<RecruitmentVo> recruitmentListViews(SearchVo searchVo);
	
	//(찜 구분)
	Integer selectBookMark(int memberId, int jobPostId);
	
	//memberId 중 jobPostId에 사용중인 북마크 찾기(찜 구분)-리스트에서 사용
	List<Integer> bookMarkedJobPostIdList(int memberId);


    void deleteQuestionByOrder(Integer jobPostId, int order);
}
