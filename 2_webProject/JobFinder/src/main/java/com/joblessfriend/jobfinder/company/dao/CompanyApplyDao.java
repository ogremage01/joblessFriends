package com.joblessfriend.jobfinder.company.dao;

import com.joblessfriend.jobfinder.company.domain.ApplySummaryVo;
import com.joblessfriend.jobfinder.company.domain.QuestionAnswerVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CompanyApplyDao {
    List<ApplySummaryVo> getApplyListByCompany(Map<String, Object> paramMap);
    int countApplyByCompany(Map<String, Object> paramMap);
    List<ApplySummaryVo> getPagedApplyList(Map<String, Object> paramMap);
    //사전질문지//
    List<QuestionAnswerVo> getQuestionAnswersByJobPostAndMember(int jobPostId, int memberId);
    //지원합불 //
    void updateResumeState(Map<String, Object> param);


}