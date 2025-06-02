
// ✅ Service
package com.joblessfriend.jobfinder.company.service;

import com.joblessfriend.jobfinder.company.domain.ApplySummaryVo;
import java.util.List;
import java.util.Map;

public interface CompanyApplyService {
    List<ApplySummaryVo> getApplyListByCompany(Map<String, Object> paramMap);
    int countApplyByCompany(Map<String, Object> paramMap);
    List<ApplySummaryVo> getPagedApplyList(Map<String, Object> paramMap);
}
