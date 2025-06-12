package com.joblessfriend.jobfinder.admin.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joblessfriend.jobfinder.admin.dao.AdminMemberDao;
import com.joblessfriend.jobfinder.auth.controller.AuthController;
import com.joblessfriend.jobfinder.member.domain.MemberVo;
import com.joblessfriend.jobfinder.resume.dao.ResumeDao;
import com.joblessfriend.jobfinder.member.dao.MemberRecruitmentDao;
import com.joblessfriend.jobfinder.util.SearchVo;

@Service
public class AdminMemberServiceImpl implements AdminMemberService{
	
	private Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	private AdminMemberDao memberDao;
	
	@Autowired
	private ResumeDao resumeDao;
	
	@Autowired
	private MemberRecruitmentDao memberRecruitmentDao;

	
	@Override//관리자용
	public List<MemberVo> memberSelectList(SearchVo searchVo) {
		// TODO Auto-generated method stub
		return memberDao.memberSelectList(searchVo);
	}
	
	@Override//관리자용
	public int memberCount(SearchVo searchVo) {
		// TODO Auto-generated method stub
		return memberDao.memberCount(searchVo);
	}

	@Override//관리자용
	public MemberVo memberSelectOne(int memberId) {
		// TODO Auto-generated method stub
		return memberDao.memberSelectOne(memberId);
	}

	@Override//관리자용
	public int memberUpdateOne(MemberVo existMemberVo) {
		// TODO Auto-generated method stub
		return memberDao.memberUpdateOne(existMemberVo);
	}

	@Override//관리자.이용자 겸용(분할해야함)
	@Transactional
	public int memberDeleteOne(int memberId) {
		logger.info("어드민 회원 탈퇴 처리 시작 - memberId: {}", memberId);
		
		try {
			// 1. 이력서 관련 데이터 삭제
			List<com.joblessfriend.jobfinder.resume.domain.ResumeVo> resumeList = resumeDao.findResumesByMemberId(memberId);
			for (com.joblessfriend.jobfinder.resume.domain.ResumeVo resume : resumeList) {
				int resumeId = resume.getResumeId();
				logger.info("이력서 삭제 처리 - resumeId: {}", resumeId);
				
				// 이력서 하위 데이터 삭제
				resumeDao.deleteSchoolsByResumeId(resumeId);
				resumeDao.deleteCareersByResumeId(resumeId);
				resumeDao.deleteEducationsByResumeId(resumeId);
				resumeDao.deleteCertificatesByResumeId(resumeId);
				resumeDao.deleteTagsByResumeId(resumeId);
				resumeDao.deletePortfoliosByResumeId(resumeId);
				
				// 이력서 메인 삭제
				resumeDao.deleteResumeById(memberId, resumeId);
			}
			
			// 2. 지원 이력 삭제 (RESUME_MANAGE 테이블의 데이터)
			// - 이 부분은 CASCADE로 처리되지만 명시적으로 로그 출력
			logger.info("회원의 지원 이력은 CASCADE로 삭제됨 - memberId: {}", memberId);
			
			// 3. 북마크 삭제
			// - 이 부분도 CASCADE로 처리되지만 명시적으로 로그 출력
			logger.info("회원의 북마크는 CASCADE로 삭제됨 - memberId: {}", memberId);
			
			// 4. 기타 연관 데이터 삭제 (필요시 추가)
			// - 커뮤니티 글, 댓글 등은 CASCADE나 별도 처리 필요
			
			// 5. 최종적으로 회원 정보 삭제
			int result = memberDao.memberDeleteOne(memberId);
			
			logger.info("어드민 회원 탈퇴 처리 완료 - memberId: {}, result: {}", memberId, result);
			return result;
			
		} catch (Exception e) {
			logger.error("어드민 회원 탈퇴 처리 중 오류 발생 - memberId: {}", memberId, e);
			throw new RuntimeException("회원 탈퇴 처리 중 오류가 발생했습니다.", e);
		}
	}

	@Override//관리자용
	@Transactional
	public int memberDeleteList(List<Integer> memberIdList) {
		logger.info("어드민 회원 대량 탈퇴 처리 시작 - 대상 수: {}", memberIdList.size());
		
		int totalDeleted = 0;
		for (Integer memberId : memberIdList) {
			try {
				int result = memberDeleteOne(memberId);
				totalDeleted += result;
			} catch (Exception e) {
				logger.error("회원 ID {} 탈퇴 처리 실패", memberId, e);
				// 개별 실패는 로그만 남기고 계속 진행
			}
		}
		
		logger.info("어드민 회원 대량 탈퇴 처리 완료 - 처리된 수: {}/{}", totalDeleted, memberIdList.size());
		return totalDeleted;
	}

	

}
