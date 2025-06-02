package com.joblessfriend.jobfinder.admin.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AdminDashboardDaoImpl implements AdminDashboardDao {
    
    @Autowired
    private SqlSession sqlSession;
    
    private final String namespace = "com.joblessfriend.jobfinder.admin.mapper.adminDashboardMapper";
    
    @Override
    public int getTotalMemberCount() {
        return sqlSession.selectOne(namespace + ".getTotalMemberCount");
    }
    
    @Override
    public int getMonthlyMemberIncrease() {
        return sqlSession.selectOne(namespace + ".getMonthlyMemberIncrease");
    }
    
    @Override
    public int getTotalCompanyCount() {
        return sqlSession.selectOne(namespace + ".getTotalCompanyCount");
    }
    
    @Override
    public int getMonthlyCompanyIncrease() {
        return sqlSession.selectOne(namespace + ".getMonthlyCompanyIncrease");
    }
    
    @Override
    public int getActiveRecruitmentCount() {
        return sqlSession.selectOne(namespace + ".getActiveRecruitmentCount");
    }
    
    @Override
    public int getTotalCommunityCount() {
        return sqlSession.selectOne(namespace + ".getTotalCommunityCount");
    }
    
    @Override
    public int getWeeklyCommunityIncrease() {
        return sqlSession.selectOne(namespace + ".getWeeklyCommunityIncrease");
    }
} 