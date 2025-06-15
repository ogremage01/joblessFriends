package com.joblessfriend.jobfinder.admin.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joblessfriend.jobfinder.admin.dao.AdminDashboardDao;
import com.joblessfriend.jobfinder.util.ChartVo;

@Service
public class AdminDashboardServiceImpl implements AdminDashboardService {
    
    @Autowired
    private AdminDashboardDao adminDashboardDao;
    
    @Override
    public Map<String, ChartVo> getDashboardStatistics() {
        Map<String, ChartVo> statistics = new HashMap<>();
        
        ChartVo memberIncrease = getMonthlyMemberIncrease(); 
        System.out.println("확인 전월: " + memberIncrease.getPreviousResult());
        System.out.println("확인 당월: " + memberIncrease.getCurrentResult());
        
        statistics.put("memberIncrease", getMonthlyMemberIncrease());
        statistics.put("companyIncrease", getMonthlyCompanyIncrease());
        statistics.put("recruitmentRegistCount", getMonthlyRecruitmentRegistCount());
        statistics.put("communityIncrease", getMonthlyCommunityIncrease());
        statistics.put("applyCount", getMonthlyApplyCount());
        
        return statistics;
    }

    @Override
    public ChartVo getMonthlyMemberIncrease() {
        return adminDashboardDao.getMonthlyMemberIncrease();
    }
    
    @Override
    public ChartVo getMonthlyCompanyIncrease() {
        return adminDashboardDao.getMonthlyCompanyIncrease();
    }
    
    @Override
    public ChartVo getMonthlyRecruitmentRegistCount() {
        return adminDashboardDao.getMonthlyRecruitmentRegistCount();
    }
    
    @Override
    public ChartVo getMonthlyCommunityIncrease() {
        return adminDashboardDao.getMonthlyCommunityIncrease();
    }
    
    @Override
    public ChartVo getMonthlyApplyCount() {
        return adminDashboardDao.getMonthlyApplyCount();
    }
} 