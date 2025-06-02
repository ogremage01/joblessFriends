package com.joblessfriend.jobfinder.company.service;

import com.joblessfriend.jobfinder.company.domain.CompanyApplyVo;
import com.joblessfriend.jobfinder.util.SearchVo;

import java.util.List;

public interface CompanyApplyService {

    List<CompanyApplyVo> getApplyMemberList(SearchVo vo);
    int getApplyMemberCount(SearchVo vo);
}
