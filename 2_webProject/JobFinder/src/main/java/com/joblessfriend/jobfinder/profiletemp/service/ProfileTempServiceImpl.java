package com.joblessfriend.jobfinder.profiletemp.service;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joblessfriend.jobfinder.profiletemp.dao.ProfileTempDao;
import com.joblessfriend.jobfinder.profiletemp.domain.ProfileTempVo;

@Service
public class ProfileTempServiceImpl implements ProfileTempService{
	
	private static final Logger log = LoggerFactory.getLogger(ProfileTempServiceImpl.class);
	
	@Autowired
    private ProfileTempDao profileTempDao;

    @Override
    public void insertProfileTemp(ProfileTempVo vo) {
        profileTempDao.insertProfileTemp(vo);
    }

    @Override
    public List<ProfileTempVo> findByMemberId(int memberId) {
        return profileTempDao.findByMemberId(memberId);
    }

    @Override
    public void deleteByMemberId(int memberId) {
        profileTempDao.deleteByMemberId(memberId);
    }
    
    @Override
    @Transactional
    public void deleteProfileImageAndData(String storedFileName, int memberId) {
        validateInput(storedFileName, memberId);
        
        try {
            // 1. DB에서 임시 프로필 데이터 삭제 (트랜잭션 내에서 먼저 실행)
            deleteByMemberId(memberId);
            
            // 2. 파일 시스템에서 파일 삭제
            deleteFileFromFileSystem(storedFileName, memberId);
            
            log.info("프로필 이미지 삭제 완료: memberId={}, fileName={}", memberId, storedFileName);
            
        } catch (Exception e) {
            log.error("프로필 이미지 삭제 실패: memberId={}, fileName={}", memberId, storedFileName, e);
            throw new RuntimeException("프로필 이미지 삭제 중 오류가 발생했습니다.", e);
        }
    }
    
    /**
     * 입력값 검증
     */
    private void validateInput(String storedFileName, int memberId) {
        if (storedFileName == null || storedFileName.trim().isEmpty()) {
            throw new IllegalArgumentException("파일명이 유효하지 않습니다.");
        }
        
        if (memberId <= 0) {
            throw new IllegalArgumentException("회원 ID가 유효하지 않습니다.");
        }
        
        // 파일명 보안 검증 (경로 조작 방지)
        if (storedFileName.contains("..") || storedFileName.contains("/") || storedFileName.contains("\\")) {
            log.warn("의심스러운 파일명 접근 시도: memberId={}, fileName={}", memberId, storedFileName);
            throw new SecurityException("유효하지 않은 파일명입니다.");
        }
    }
    
    /**
     * 파일 시스템에서 파일 삭제
     */
    private void deleteFileFromFileSystem(String storedFileName, int memberId) {
        String uploadDir = "C:/upload/profile/";
        File fileToDelete = new File(uploadDir + storedFileName);
        
        if (fileToDelete.exists()) {
            boolean deleted = fileToDelete.delete();
            if (!deleted) {
                log.warn("파일 삭제 실패: memberId={}, fileName={}, path={}", memberId, storedFileName, fileToDelete.getAbsolutePath());
                throw new RuntimeException("파일 삭제에 실패했습니다.");
            } else {
                log.debug("파일 삭제 성공: memberId={}, fileName={}", memberId, storedFileName);
            }
        } else {
            log.warn("삭제하려는 파일이 존재하지 않음: memberId={}, fileName={}, path={}", memberId, storedFileName, fileToDelete.getAbsolutePath());
        }
    }

}
