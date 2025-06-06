package com.joblessfriend.jobfinder.profiletemp.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.joblessfriend.jobfinder.profiletemp.domain.ProfileTempVo;

public interface ProfileTempService {
	
	void insertProfileTemp(ProfileTempVo vo);
    List<ProfileTempVo> findByMemberId(int memberId);
    void deleteByMemberId(int memberId);
    
    // 프로필 이미지 파일과 DB 데이터를 함께 삭제
    void deleteProfileImageAndData(String storedFileName, int memberId);
    
    // 프로필 이미지 업로드 처리 (파일 저장 + DB 저장)
    ProfileImageUploadResult uploadProfileImage(MultipartFile file, int memberId);
    
    // 업로드 결과를 담는 내부 클래스
    public static class ProfileImageUploadResult {
        private final String originalFileName;
        private final String storedFileName;
        private final String fileUrl;
        
        public ProfileImageUploadResult(String originalFileName, String storedFileName, String fileUrl) {
            this.originalFileName = originalFileName;
            this.storedFileName = storedFileName;
            this.fileUrl = fileUrl;
        }
        
        public String getOriginalFileName() { return originalFileName; }
        public String getStoredFileName() { return storedFileName; }
        public String getFileUrl() { return fileUrl; }
    }

}
