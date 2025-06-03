package com.joblessfriend.jobfinder.company.dao;

import com.joblessfriend.jobfinder.company.domain.ApplySummaryVo;
import java.util.List;
import java.util.Map;

public interface CompanyApplyDao {
    List<ApplySummaryVo> getApplyListByCompany(Map<String, Object> paramMap);
    int countApplyByCompany(Map<String, Object> paramMap);
    List<ApplySummaryVo> getPagedApplyList(Map<String, Object> paramMap);
}