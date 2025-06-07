package com.joblessfriend.jobfinder.profiletemp.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.joblessfriend.jobfinder.profiletemp.dao.ProfileTempDao;
import com.joblessfriend.jobfinder.profiletemp.domain.ProfileTempVo;

@Service
public class ProfileTempServiceImpl implements ProfileTempService{
	
	private static final Logger log = LoggerFactory.getLogger(ProfileTempServiceImpl.class);
	private static final String UPLOAD_DIR = "C:/upload/profile/";
	private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
	private static final String[] ALLOWED_EXTENSIONS = {".jpg", ".jpeg", ".png", ".gif"};
	
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
    public ProfileImageUploadResult uploadProfileImage(MultipartFile file, int memberId) {
        validateFile(file);
        validateMemberId(memberId);
        
        try {
            // 1. 파일명 생성
            String originalFileName = file.getOriginalFilename();
            String storedFileName = generateStoredFileName(originalFileName);
            
            // 2. 디렉토리 확인 및 생성
            ensureUploadDirectoryExists();
            
            // 3. 파일 저장
            saveFileToFileSystem(file, storedFileName);
            
            // 4. DB 처리 (기존 데이터 삭제 후 새 데이터 저장)
            saveProfileTempToDatabase(memberId, storedFileName);
            
            // 5. 웹 접근 URL 생성
            String fileUrl = "/profile/" + storedFileName;
            
            log.info("프로필 이미지 업로드 완료: memberId={}, originalFileName={}, storedFileName={}", 
                    memberId, originalFileName, storedFileName);
            
            return new ProfileImageUploadResult(originalFileName, storedFileName, fileUrl);
            
        } catch (Exception e) {
            log.error("프로필 이미지 업로드 실패: memberId={}, fileName={}", 
                     memberId, file.getOriginalFilename(), e);
            throw new RuntimeException("프로필 이미지 업로드 중 오류가 발생했습니다.", e);
        }
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
     * 업로드 파일 검증
     */
    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("업로드할 파일이 없습니다.");
        }
        
        // 파일 크기 검증
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("파일 크기가 너무 큽니다. (최대 10MB)");
        }
        
        // 파일 확장자 검증
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null || !isAllowedExtension(originalFileName)) {
            throw new IllegalArgumentException("지원하지 않는 파일 형식입니다. (jpg, jpeg, png, gif만 가능)");
        }
    }
    
    /**
     * 허용된 확장자인지 확인
     */
    private boolean isAllowedExtension(String fileName) {
        String lowerFileName = fileName.toLowerCase();
        for (String extension : ALLOWED_EXTENSIONS) {
            if (lowerFileName.endsWith(extension)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 회원 ID 검증
     */
    private void validateMemberId(int memberId) {
        if (memberId <= 0) {
            throw new IllegalArgumentException("회원 ID가 유효하지 않습니다.");
        }
    }
    
    /**
     * 저장할 파일명 생성
     */
    private String generateStoredFileName(String originalFileName) {
        String extension = "";
        int lastDotIndex = originalFileName.lastIndexOf('.');
        if (lastDotIndex > 0) {
            extension = originalFileName.substring(lastDotIndex);
        }
        return UUID.randomUUID().toString() + extension;
    }
    
    /**
     * 업로드 디렉토리 존재 확인 및 생성
     */
    private void ensureUploadDirectoryExists() {
        File dir = new File(UPLOAD_DIR);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (!created) {
                throw new RuntimeException("업로드 디렉토리 생성에 실패했습니다: " + UPLOAD_DIR);
            }
            log.info("업로드 디렉토리 생성됨: {}", UPLOAD_DIR);
        }
    }
    
    /**
     * 파일을 파일 시스템에 저장
     */
    private void saveFileToFileSystem(MultipartFile file, String storedFileName) throws IOException {
        File dest = new File(UPLOAD_DIR + storedFileName);
        file.transferTo(dest);
        log.debug("파일 저장 완료: {}", dest.getAbsolutePath());
    }
    
    /**
     * DB에 프로필 임시 데이터 저장
     */
    private void saveProfileTempToDatabase(int memberId, String storedFileName) {
        // 기존 데이터 삭제
        deleteByMemberId(memberId);
        
        // 새 데이터 저장
        ProfileTempVo vo = new ProfileTempVo(memberId, storedFileName);
        insertProfileTemp(vo);
        
        log.debug("DB 저장 완료: memberId={}, storedFileName={}", memberId, storedFileName);
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
        File fileToDelete = new File(UPLOAD_DIR + storedFileName);
        
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
