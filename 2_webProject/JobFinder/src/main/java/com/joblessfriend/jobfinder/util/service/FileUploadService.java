package com.joblessfriend.jobfinder.util.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public interface FileUploadService {
    
    /**
     * 이력서 프로필 사진 업로드
     */
    Map<String, Object> uploadProfilePhoto(MultipartFile file, int resumeId, int memberId, HttpServletRequest request);
    
    /**
     * 임시 프로필 이미지 업로드 (세션에 저장)
     */
    Map<String, Object> uploadProfileImageToSession(MultipartFile file, HttpSession session);
    
    /**
     * 포트폴리오 파일 업로드 (세션에 저장)
     */
    Map<String, Object> uploadPortfolioFile(MultipartFile file, HttpSession session);
    
    /**
     * 세션에서 파일 삭제
     */
    boolean deleteFileFromSession(String fileId, HttpSession session);
    
    /**
     * 세션에서 업로드된 파일 목록 가져오기
     */
    List<Map<String, Object>> getUploadedFilesFromSession(HttpSession session);
    
    /**
     * 세션에 업로드된 파일 목록 저장
     */
    void saveUploadedFilesToSession(List<Map<String, Object>> uploadedFiles, HttpSession session);
    
    /**
     * 파일 다운로드 권한 검증
     */
    boolean validateFileDownloadPermission(String storedFileName, int memberId);
} 