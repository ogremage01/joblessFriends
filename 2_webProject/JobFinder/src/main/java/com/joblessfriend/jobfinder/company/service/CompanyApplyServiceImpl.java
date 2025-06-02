package com.joblessfriend.jobfinder.company.service;

import com.joblessfriend.jobfinder.company.dao.CompanyApplyDao;
import com.joblessfriend.jobfinder.company.domain.CompanyApplyVo;
import com.joblessfriend.jobfinder.util.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyApplyServiceImpl implements CompanyApplyService{

    @Autowired
    private CompanyApplyDao companyApplyDao;

    //전체리스트조회 //
    @Override
    public List<CompanyApplyVo> getApplyMemberList(SearchVo vo) {
        return companyApplyDao.getApplyMemberList(vo);
    }

    @Override
    public int getApplyMemberCount(SearchVo vo) {
        return companyApplyDao.getApplyMemberCount(vo);
    }
}
