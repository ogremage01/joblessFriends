package com.joblessfriend.jobfinder.company.dao;

import com.joblessfriend.jobfinder.company.domain.CompanyApplyVo;
import com.joblessfriend.jobfinder.util.SearchVo;

import java.util.List;

public interface CompanyApplyDao {
    //지원자수 카운트와 리스트조회//
    List<CompanyApplyVo> getApplyMemberList(SearchVo vo);
    int getApplyMemberCount(SearchVo vo);
}
