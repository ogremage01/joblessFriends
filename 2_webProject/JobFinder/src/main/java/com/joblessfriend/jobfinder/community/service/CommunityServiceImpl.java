package com.joblessfriend.jobfinder.community.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joblessfriend.jobfinder.community.dao.CommunityDao;
import com.joblessfriend.jobfinder.community.domain.CommunityVo;
import com.joblessfriend.jobfinder.util.SearchVo;

@Service
public class CommunityServiceImpl implements CommunityService{
	private Logger logger = LoggerFactory.getLogger(CommunityService.class);
	private final String logTitleMsg = "===================게시판 서비스 시작======================";
	
	@Autowired
	private CommunityDao communityDao;
	
	@Override
	@Transactional
	public void communityInsertOne(CommunityVo communityVo) {
		try {
			logger.info("{} 게시글 저장 시작", logTitleMsg);
			communityDao.communityInsertOne(communityVo);
			logger.info("게시글 저장 완료: communityId={}", communityVo.getCommunityId());
		} catch (Exception e) {
			logger.error("게시글 저장 중 오류 발생: ", e);
			throw new RuntimeException("게시글 저장 중 오류가 발생했습니다.", e);
		}
	}

	@Override
	public List<CommunityVo> communitySelectList(SearchVo searchVo) {
		// TODO Auto-generated method stub
		return communityDao.communitySelectList(searchVo);
	}

	@Override
	public CommunityVo communityDetail(int no) {
		// TODO Auto-generated method stub
		 CommunityVo community = communityDao.communityDetail(no);

		return community;
	}

	@Override
	@Transactional
	public void communityUpdate(CommunityVo communityVo) {
		try {
			logger.info("{} 게시글 수정 시작", logTitleMsg);
			communityDao.communityUpdate(communityVo);
			logger.info("게시글 수정 완료: communityId={}", communityVo.getCommunityId());
		} catch (Exception e) {
			logger.error("게시글 수정 중 오류 발생: ", e);
			throw new RuntimeException("게시글 수정 중 오류가 발생했습니다.", e);
		}
	}

	@Override
	@Transactional
	public void communityDelete(int communityId) {
		try {
			logger.info("{} 게시글 삭제 시작", logTitleMsg);
			// 먼저 관련된 파일 정보 삭제
			communityDao.communityFileDelete(communityId);
			// 그 다음 게시글 삭제
			communityDao.communityDelete(communityId);
			logger.info("게시글 및 관련 파일 삭제 완료: communityId={}", communityId);
		} catch (Exception e) {
			logger.error("게시글 삭제 중 오류 발생: ", e);
			throw new RuntimeException("게시글 삭제 중 오류가 발생했습니다.", e);
		}
	}

	@Override
	@Transactional
	public void communityFileInsertOne(Map<String, Object> fileMap) {
		try {
			logger.info("{} 이미지 파일 저장 시작", logTitleMsg);
			communityDao.communityFileInsertOne(fileMap);
			logger.info("이미지 파일 저장 완료: storedFileName={}", fileMap.get("STOREDFILENAME"));
		} catch (Exception e) {
			logger.error("이미지 파일 저장 중 오류 발생: ", e);
			throw new RuntimeException("이미지 파일 저장 중 오류가 발생했습니다.", e);
		}
	}

	@Override
	public int communitySeqNum() {
		// TODO Auto-generated method stub
		return communityDao.communitySeqNum();
	}

	@Override
	public List<Map<String, Object>> communityFileList(int communityId) {
		// TODO Auto-generated method stub
		return communityDao.communityFileList(communityId);
	}

	@Override
	@Transactional
	public void communityFileDelete(int communityId) {
		try {
			logger.info("{} 게시글 파일 삭제 시작", logTitleMsg);
			communityDao.communityFileDelete(communityId);
			logger.info("게시글 파일 삭제 완료: communityId={}", communityId);
		} catch (Exception e) {
			logger.error("게시글 파일 삭제 중 오류 발생: ", e);
			throw new RuntimeException("게시글 파일 삭제 중 오류가 발생했습니다.", e);
		}
	}

	@Override
	@Transactional
	public void communityFileNewInsert(Map<String, Object> fileMap) {
		try {
			logger.info("{} 새 이미지 파일 저장 시작", logTitleMsg);
			communityDao.communityFileNewInsert(fileMap);
			logger.info("새 이미지 파일 저장 완료: storedFileName={}", fileMap.get("STOREDFILENAME"));
		} catch (Exception e) {
			logger.error("새 이미지 파일 저장 중 오류 발생: ", e);
			throw new RuntimeException("새 이미지 파일 저장 중 오류가 발생했습니다.", e);
		}
	}

	//페이지네이션 전체 페이지 수
	@Override
	public int getCommunityTotalCount(SearchVo searchVo) {
		// TODO Auto-generated method stub
		return communityDao.getCommunityTotalCount(searchVo);
	}

	@Override
	public void communityViewCount(CommunityVo communityVo) {
		// TODO Auto-generated method stub
		communityDao.communityViewCount(communityVo);
	}

}
