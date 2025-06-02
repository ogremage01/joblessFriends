package com.joblessfriend.jobfinder.admin.dao;

public interface AdminDashboardDao {
    
    /**
     * 전체 회원 수 조회
     * @return 회원 수
     */
    int getTotalMemberCount();
    
    /**
     * 이번 달 회원 증가 수 조회
     * @return 이번 달 증가 회원 수
     */
    int getMonthlyMemberIncrease();
    
    /**
     * 전체 기업 회원 수 조회
     * @return 기업 회원 수
     */
    int getTotalCompanyCount();
    
    /**
     * 이번 달 기업 회원 증가 수 조회
     * @return 이번 달 증가 기업 수
     */
    int getMonthlyCompanyIncrease();
    
    /**
     * 활성 채용 공고 수 조회
     * @return 활성 채용 공고 수
     */
    int getActiveRecruitmentCount();
    
    /**
     * 전체 커뮤니티 게시글 수 조회
     * @return 커뮤니티 게시글 수
     */
    int getTotalCommunityCount();
    
    /**
     * 이번 주 커뮤니티 게시글 증가 수 조회
     * @return 이번 주 증가 게시글 수
     */
    int getWeeklyCommunityIncrease();
} 