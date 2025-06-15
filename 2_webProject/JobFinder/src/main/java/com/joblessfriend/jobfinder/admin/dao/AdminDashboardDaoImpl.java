package com.joblessfriend.jobfinder.admin.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.joblessfriend.jobfinder.util.ChartVo;

@Repository
public class AdminDashboardDaoImpl implements AdminDashboardDao {
    
    @Autowired
    private SqlSession sqlSession;
    
    private final String namespace = "com.joblessfriend.jobfinder.admin.mapper.adminDashboardMapper";
    
    @Override
	public ChartVo getMonthlyMemberIncrease() {
		return sqlSession.selectOne(namespace + ".getMonthlyMemberIncrease");
	}
	
	@Override
	public ChartVo getMonthlyCompanyIncrease() {
		return sqlSession.selectOne(namespace + ".getMonthlyCompanyIncrease");
	}
	
	@Override
	public ChartVo getMonthlyRecruitmentRegistCount() {
		return sqlSession.selectOne(namespace + ".getMonthlyRecruitmentRegistCount");
	}
	
	@Override
	public ChartVo getMonthlyCommunityIncrease() {
		return sqlSession.selectOne(namespace + ".getMonthlyCommunityIncrease");
	}
	
	@Override
	public ChartVo getMonthlyApplyCount() {
		return sqlSession.selectOne(namespace + ".getMonthlyApplyCount");
	}
} 