
// ✅ Service
package com.joblessfriend.jobfinder.company.service;

import com.joblessfriend.jobfinder.company.domain.ApplySummaryVo;
import com.joblessfriend.jobfinder.company.domain.QuestionAnswerVo;

import java.util.List;
import java.util.Map;

public interface CompanyApplyService {
    List<ApplySummaryVo> getApplyListByCompany(Map<String, Object> paramMap);
    int countApplyByCompany(Map<String, Object> paramMap);
    List<ApplySummaryVo> getPagedApplyList(Map<String, Object> paramMap);

    List<QuestionAnswerVo> getQuestionAnswersByJobPostAndMember(int jobPostId, int memberId);
    void updateResumeState(int jobPostId, int memberId, int stateId);
}
