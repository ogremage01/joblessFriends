package com.joblessfriend.jobfinder.admin.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joblessfriend.jobfinder.admin.dao.AdminDashboardDao;

@Service
public class AdminDashboardServiceImpl implements AdminDashboardService {
    
    @Autowired
    private AdminDashboardDao adminDashboardDao;
    
    @Override
    public Map<String, Object> getDashboardStatistics() {
        Map<String, Object> statistics = new HashMap<>();
        
        statistics.put("memberCount", getTotalMemberCount());
        statistics.put("memberMonthlyIncrease", getMonthlyMemberIncrease());
        statistics.put("companyCount", getTotalCompanyCount());
        statistics.put("companyMonthlyIncrease", getMonthlyCompanyIncrease());
        statistics.put("recruitmentCount", getActiveRecruitmentCount());
        statistics.put("communityCount", getTotalCommunityCount());
        statistics.put("communityWeeklyIncrease", getWeeklyCommunityIncrease());
        
        return statistics;
    }
    
    @Override
    public int getTotalMemberCount() {
        return adminDashboardDao.getTotalMemberCount();
    }
    
    @Override
    public int getMonthlyMemberIncrease() {
        return adminDashboardDao.getMonthlyMemberIncrease();
    }
    
    @Override
    public int getTotalCompanyCount() {
        return adminDashboardDao.getTotalCompanyCount();
    }
    
    @Override
    public int getMonthlyCompanyIncrease() {
        return adminDashboardDao.getMonthlyCompanyIncrease();
    }
    
    @Override
    public int getActiveRecruitmentCount() {
        return adminDashboardDao.getActiveRecruitmentCount();
    }
    
    @Override
    public int getTotalCommunityCount() {
        return adminDashboardDao.getTotalCommunityCount();
    }
    
    @Override
    public int getWeeklyCommunityIncrease() {
        return adminDashboardDao.getWeeklyCommunityIncrease();
    }
} 