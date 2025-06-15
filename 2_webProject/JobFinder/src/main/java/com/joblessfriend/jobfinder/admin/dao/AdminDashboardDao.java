package com.joblessfriend.jobfinder.admin.dao;

import com.joblessfriend.jobfinder.util.ChartVo;

public interface AdminDashboardDao {
    
	/**
     * 전월, 당월 회원 증가 수 조회
     * @return 기업 회원 수
     */
	ChartVo getMonthlyMemberIncrease();
    
    /**
     * 전월, 당월 기업 회원 증가 수 조회
     * @return 이번 달 증가 기업 수
     */
	ChartVo getMonthlyCompanyIncrease();
    
    /**
     * 전월, 당월 등록된 채용공고 수 조회
     * @return 활성 채용 공고 수
     */
	ChartVo getMonthlyRecruitmentRegistCount();
    
    /**
     * 전월, 당월 커뮤니티 게시글 증가 수 조회
     * @return 커뮤니티 게시글 수
     */
	ChartVo getMonthlyCommunityIncrease();
    
    /**
     * 전월, 당월 지원 이력서 수 조회
     * @return 이번 주 증가 게시글 수
     */
	ChartVo getMonthlyApplyCount();
} 