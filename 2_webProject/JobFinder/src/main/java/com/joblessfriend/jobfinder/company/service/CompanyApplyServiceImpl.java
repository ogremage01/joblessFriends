
// ✅ ServiceImpl
package com.joblessfriend.jobfinder.company.service;

import com.joblessfriend.jobfinder.company.dao.CompanyApplyDao;
import com.joblessfriend.jobfinder.company.domain.ApplySummaryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CompanyApplyServiceImpl implements CompanyApplyService {

    @Autowired
    private CompanyApplyDao companyApplyDao;

    @Override
    public List<ApplySummaryVo> getApplyListByCompany(Map<String, Object> paramMap) {
        return companyApplyDao.getApplyListByCompany(paramMap);
    }

    @Override
    public int countApplyByCompany(Map<String, Object> paramMap) {
        return companyApplyDao.countApplyByCompany(paramMap);
    }

    @Override
    public List<ApplySummaryVo> getPagedApplyList(Map<String, Object> paramMap) {
        return companyApplyDao.getPagedApplyList(paramMap);
    }
}