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
import com.joblessfriend.jobfinder.resume.dao.ResumeApplyDao;
import com.joblessfriend.jobfinder.resume.domain.ResumeVo;
import com.joblessfriend.jobfinder.member.dao.MemberRecruitmentDao;
import com.joblessfriend.jobfinder.util.SearchVo;

@Service
public class AdminMemberServiceImpl implements AdminMemberService{
	
	private Logger logger = LoggerFactory.getLogger(AdminMemberServiceImpl.class);
	
	@Autowired
	private AdminMemberDao memberDao;
	
	@Autowired
	private ResumeDao resumeDao;
	
	@Autowired
	private ResumeApplyDao resumeApplyDao;
	
	@Autowired
	private MemberRecruitmentDao memberRecruitmentDao;

	@Override
	public List<MemberVo> memberSelectAll(SearchVo searchVo) {
		return memberDao.memberSelectAll(searchVo);
	}

	@Override
	public MemberVo memberSelectOne(int memberId) {
		return memberDao.memberSelectOne(memberId);
	}

	@Override
	@Transactional
	public int memberDeleteOne(int memberId) {
		try {
			logger.info("회원 탈퇴 프로세스 시작 - memberId: {}", memberId);
			
			// 1. 회원의 복사된 이력서 조회 및 삭제
			List<ResumeVo> resumeCopies = resumeApplyDao.findResumeCopyByMemberId(memberId);
			if (resumeCopies != null && !resumeCopies.isEmpty()) {
				logger.info("복사된 이력서 {}개 삭제 중...", resumeCopies.size());
				for (ResumeVo resumeCopy : resumeCopies) {
					int resumeCopyId = resumeCopy.getResumeId();
					// 복사된 이력서 하위 데이터 삭제
					resumeApplyDao.deleteTagCopyByResumeId(resumeCopyId);
					resumeApplyDao.deletePortfolioCopyByResumeId(resumeCopyId);
					resumeApplyDao.deleteCertificateCopyByResumeId(resumeCopyId);
					resumeApplyDao.deleteEducationCopyByResumeId(resumeCopyId);
					resumeApplyDao.deleteCareerCopyByResumeId(resumeCopyId);
					resumeApplyDao.deleteSchoolCopyByResumeId(resumeCopyId);
					// 복사된 이력서 메인 삭제
					resumeApplyDao.deleteResumeCopyById(resumeCopyId);
				}
				logger.info("복사된 이력서 삭제 완료");
			}
			
			// 2. 회원의 원본 이력서 조회 및 삭제
			List<ResumeVo> resumes = resumeDao.findResumesByMemberId(memberId);
			if (resumes != null && !resumes.isEmpty()) {
				logger.info("원본 이력서 {}개 삭제 중...", resumes.size());
				for (ResumeVo resume : resumes) {
					int resumeId = resume.getResumeId();
					// 이력서 하위 데이터 삭제 (CASCADE 관계로 자동 처리되지만 명시적으로 삭제)
					resumeDao.deleteTagsByResumeId(resumeId);
					resumeDao.deletePortfoliosByResumeId(resumeId);
					resumeDao.deleteCertificatesByResumeId(resumeId);
					resumeDao.deleteEducationsByResumeId(resumeId);
					resumeDao.deleteCareersByResumeId(resumeId);
					resumeDao.deleteSchoolsByResumeId(resumeId);
					// 이력서 메인 삭제
					resumeDao.deleteResumeById(memberId, resumeId);
				}
				logger.info("원본 이력서 삭제 완료");
			}
			
			// 3. 지원 이력(RESUME_MANAGE) 삭제 - CASCADE로 처리
			logger.info("지원 이력 삭제는 CASCADE로 처리됨");
			
			// 4. 북마크(BOOKMARK) 삭제 - CASCADE로 처리
			logger.info("북마크 삭제는 CASCADE로 처리됨");
			
			// 5. 회원 정보 삭제
			int result = memberDao.memberDeleteOne(memberId);
			
			if (result > 0) {
				logger.info("회원 탈퇴 프로세스 완료 - memberId: {}", memberId);
			} else {
				logger.warn("회원 탈퇴 실패 - 해당 회원이 존재하지 않음 - memberId: {}", memberId);
			}
			
			return result;
			
		} catch (Exception e) {
			logger.error("회원 탈퇴 처리 중 예외 발생 - memberId: {}", memberId, e);
			throw e; // 트랜잭션 롤백을 위해 예외를 다시 던짐
		}
	}

	@Override
	@Transactional
	public int memberDeleteMulti(List<Integer> memberIds) {
		try {
			logger.info("회원 대량 탈퇴 프로세스 시작 - 총 {}명", memberIds.size());
			
			int totalDeleted = 0;
			
			for (Integer memberId : memberIds) {
				try {
					int result = memberDeleteOne(memberId);
					totalDeleted += result;
					logger.info("회원 탈퇴 완료 - memberId: {}, 누적 삭제: {}/{}", memberId, totalDeleted, memberIds.size());
				} catch (Exception e) {
					logger.error("회원 탈퇴 실패 - memberId: {}", memberId, e);
					// 개별 실패 시에도 계속 진행
				}
			}
			
			logger.info("회원 대량 탈퇴 프로세스 완료 - 총 {}명 중 {}명 삭제", memberIds.size(), totalDeleted);
			return totalDeleted;
			
		} catch (Exception e) {
			logger.error("회원 대량 탈퇴 처리 중 예외 발생", e);
			throw e;
		}
	}

	@Override
	public int memberSelectCount(SearchVo searchVo) {
		return memberDao.memberSelectCount(searchVo);
	}

}
