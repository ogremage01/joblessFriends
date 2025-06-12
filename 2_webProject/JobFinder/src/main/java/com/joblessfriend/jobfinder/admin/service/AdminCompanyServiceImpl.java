package com.joblessfriend.jobfinder.admin.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joblessfriend.jobfinder.admin.dao.AdminCompanyDao;
import com.joblessfriend.jobfinder.company.dao.CompanyDao;
import com.joblessfriend.jobfinder.company.domain.CompanyVo;
import com.joblessfriend.jobfinder.member.domain.MemberVo;
import com.joblessfriend.jobfinder.recruitment.dao.RecruitmentDao;
import com.joblessfriend.jobfinder.recruitment.domain.CompanyRecruitmentVo;
import com.joblessfriend.jobfinder.util.SearchVo;

@Service
public class AdminCompanyServiceImpl implements AdminCompanyService {
	
	private Logger logger = LoggerFactory.getLogger(AdminCompanyServiceImpl.class);
	
	@Autowired
	AdminCompanyDao companyDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RecruitmentDao recruitmentDao;



	@Override
	public int companyCount(SearchVo searchVo) {
		// TODO Auto-generated method stub
		return companyDao.companyCount(searchVo);
	}

	@Override
	public CompanyVo companySelectOne(int companyId) {
		// TODO Auto-generated method stub
		return companyDao.companySelectOne(companyId);
	}

	@Override
	public int companyUpdateOne(CompanyVo existCompanyVo) {
		// TODO Auto-generated method stub
		return companyDao.companyUpdateOne(existCompanyVo);
		
	}


	@Override
	@Transactional
	public int companyDeleteOne(int companyId) {
		logger.info("어드민 기업 탈퇴 처리 시작 - companyId: {}", companyId);
		
		try {
			// 1. 기업의 채용공고 관련 데이터 삭제
			List<CompanyRecruitmentVo> jobPostList = recruitmentDao.companyRecruitmentSelectList(companyId);
			
			if (!jobPostList.isEmpty()) {
				logger.info("기업의 채용공고 삭제 처리 시작 - 공고 수: {}", jobPostList.size());
				
				// 채용공고 ID 리스트 추출
				List<Integer> jobPostIdList = jobPostList.stream()
					.map(CompanyRecruitmentVo::getJobPostId)
					.toList();
				
				// 채용공고 관련 데이터 삭제
				// 1) 채용공고 파일 삭제
				recruitmentDao.jobPostFileDelete(jobPostIdList);
				logger.info("채용공고 파일 삭제 완료 - companyId: {}", companyId);
				
				// 2) 채용공고 태그 삭제
				recruitmentDao.jobPostTagDelete(jobPostIdList);
				logger.info("채용공고 태그 삭제 완료 - companyId: {}", companyId);
				
				// 3) 채용공고 복리후생 삭제
				for (Integer jobPostId : jobPostIdList) {
					recruitmentDao.deleteWelfareByJobPostId(jobPostId);
					recruitmentDao.deleteQuestionsByJobPostId(jobPostId);
					recruitmentDao.deleteAnswersByJobPostId(jobPostId);
				}
				logger.info("채용공고 상세 데이터 삭제 완료 - companyId: {}", companyId);
				
				// 4) 채용공고 메인 삭제
				recruitmentDao.jobPostDelete(jobPostIdList);
				logger.info("채용공고 메인 삭제 완료 - companyId: {}", companyId);
			} else {
				logger.info("기업에 등록된 채용공고가 없음 - companyId: {}", companyId);
			}
			
			// 2. 지원자 관리 데이터 삭제 (RESUME_MANAGE)
			// - 이 부분은 JOB_POST 삭제 시 CASCADE로 처리됨
			logger.info("지원자 관리 데이터는 CASCADE로 삭제됨 - companyId: {}", companyId);
			
			// 3. 기타 연관 데이터 삭제 (필요시 추가)
			// - 채팅, 리뷰 등은 별도 처리 필요할 수 있음
			
			// 4. 최종적으로 기업 정보 삭제
			int result = companyDao.companyDeleteOne(companyId);
			
			logger.info("어드민 기업 탈퇴 처리 완료 - companyId: {}, result: {}", companyId, result);
			return result;
			
		} catch (Exception e) {
			logger.error("어드민 기업 탈퇴 처리 중 오류 발생 - companyId: {}", companyId, e);
			throw new RuntimeException("기업 탈퇴 처리 중 오류가 발생했습니다.", e);
		}
	}

	@Override
	@Transactional
	public int companyDeleteList(List<Integer> companyIdList) {
		logger.info("어드민 기업 대량 탈퇴 처리 시작 - 대상 수: {}", companyIdList.size());
		
		int totalDeleted = 0;
		for (Integer companyId : companyIdList) {
			try {
				int result = companyDeleteOne(companyId);
				totalDeleted += result;
			} catch (Exception e) {
				logger.error("기업 ID {} 탈퇴 처리 실패", companyId, e);
				// 개별 실패는 로그만 남기고 계속 진행
			}
		}
		
		logger.info("어드민 기업 대량 탈퇴 처리 완료 - 처리된 수: {}/{}", totalDeleted, companyIdList.size());
		return totalDeleted;
	}


	@Override
	public List<CompanyVo> companySelectList(SearchVo searchVo) {
		// TODO Auto-generated method stub
		return companyDao.companySelectList(searchVo);
	}


}
