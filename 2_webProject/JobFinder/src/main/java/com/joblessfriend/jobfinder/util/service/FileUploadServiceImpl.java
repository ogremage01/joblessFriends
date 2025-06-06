package com.joblessfriend.jobfinder.util.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.joblessfriend.jobfinder.resume.service.ResumeService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    private ResumeService resumeService;

    @Override
    public Map<String, Object> uploadProfilePhoto(MultipartFile file, int resumeId, int memberId, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();

        try {
            // 업로드 경로 설정
            String uploadDir = request.getServletContext().getRealPath("/upload/resume/");
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 저장 파일명 생성
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File dest = new File(uploadDir + filename);
            file.transferTo(dest);

            // DB에 경로 저장
            String fileUrl = "/upload/resume/" + filename;
            resumeService.updateProfileImage(resumeId, memberId, fileUrl);

            result.put("success", true);
            result.put("url", fileUrl);
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", "업로드 실패: " + e.getMessage());
        }

        return result;
    }

    @Override
    public Map<String, Object> uploadProfileImageToSession(MultipartFile file, HttpSession session) {
        Map<String, Object> result = new HashMap<>();

        // 세션에서 임시 이미지 목록 가져오기
        List<Map<String, Object>> uploadedFiles = getUploadedFilesFromSession(session);

        try {
            // 업로드 경로 설정 (C://upload/profile/)
            String uploadDir = "C:/upload/profile/";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 저장 파일명 생성
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File dest = new File(uploadDir + filename);
            file.transferTo(dest);

            // 웹에서 접근 가능한 URL 생성
            String fileUrl = "/profile/" + filename;

            // 세션에 업로드한 파일 정보 저장
            Map<String, Object> fileInfo = new HashMap<>();
            fileInfo.put("fileName", file.getOriginalFilename());
            fileInfo.put("storedFileName", filename);
            fileInfo.put("fileUrl", fileUrl);
            uploadedFiles.add(fileInfo);

            saveUploadedFilesToSession(uploadedFiles, session);

            result.put("success", true);
            result.put("imageUrl", fileUrl);
            result.put("fileName", file.getOriginalFilename());

        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("error", "업로드 실패: " + e.getMessage());
        }

        return result;
    }

    @Override
    public Map<String, Object> uploadPortfolioFile(MultipartFile file, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        System.out.println(">>> [업로드] 포트폴리오 파일 업로드 시작: " + file.getOriginalFilename());

        // 세션에서 업로드된 파일 목록 가져오기
        List<Map<String, Object>> uploadedFiles = getUploadedFilesFromSession(session);

        try {
            // 업로드 경로 설정
            String uploadDir = "C:/upload/portfolio/";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                boolean created = dir.mkdirs();
                System.out.println(">>> [업로드] 디렉토리 생성: " + uploadDir + ", 성공: " + created);
            }

            // 저장 파일명 생성
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File dest = new File(uploadDir + filename);
            file.transferTo(dest);
            
            System.out.println(">>> [업로드] 파일 저장 완료: " + dest.getAbsolutePath());
            System.out.println(">>> [업로드] 파일 존재 여부: " + dest.exists());

            // 파일 확장자 추출
            String fileExtension = "";
            String originalFilename = file.getOriginalFilename();
            if (originalFilename != null && originalFilename.lastIndexOf(".") > 0) {
                fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            }

            // 세션에 업로드한 파일 정보 저장
            Map<String, Object> fileInfo = new HashMap<>();
            fileInfo.put("fileName", file.getOriginalFilename());
            fileInfo.put("storedFileName", filename);
            fileInfo.put("fileExtension", fileExtension);
            uploadedFiles.add(fileInfo);

            saveUploadedFilesToSession(uploadedFiles, session);
            System.out.println(">>> [업로드] 세션에 파일 정보 저장 완료: " + filename);

            result.put("success", true);
            result.put("fileName", file.getOriginalFilename());
            result.put("storedFileName", filename);
            result.put("fileExtension", fileExtension);

        } catch (Exception e) {
            System.err.println(">>> [업로드] 포트폴리오 파일 업로드 실패: " + e.getMessage());
            e.printStackTrace();
            result.put("success", false);
            result.put("error", "업로드 실패: " + e.getMessage());
        }

        return result;
    }

    @Override
    public boolean deleteFileFromSession(String fileId, HttpSession session) {
        List<Map<String, Object>> uploadedFiles = getUploadedFilesFromSession(session);

        if (uploadedFiles != null) {
            boolean removed = uploadedFiles.removeIf(file -> fileId.equals(file.get("storedFileName")));
            saveUploadedFilesToSession(uploadedFiles, session);
            return removed;
        }
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getUploadedFilesFromSession(HttpSession session) {
        List<Map<String, Object>> uploadedFiles = (List<Map<String, Object>>) session.getAttribute("uploadedFiles");
        if (uploadedFiles == null) {
            uploadedFiles = new ArrayList<>();
        }
        return uploadedFiles;
    }

    @Override
    public void saveUploadedFilesToSession(List<Map<String, Object>> uploadedFiles, HttpSession session) {
        session.setAttribute("uploadedFiles", uploadedFiles);
    }

    @Override
    public boolean validateFileDownloadPermission(String storedFileName, int memberId) {
        System.out.println(">>> [검증] 파일 다운로드 권한 검증 시작: " + storedFileName);
        
        // 파일이 실제로 존재하는지 확인
        String uploadDir = "C:/upload/portfolio/";
        File file = new File(uploadDir + storedFileName);
        
        if (!file.exists()) {
            System.err.println(">>> [검증] 파일이 존재하지 않음: " + file.getAbsolutePath());
            return false;
        }
        
        // 보안을 위한 기본 검증 (경로 조작 방지)
        if (storedFileName.contains("..") || storedFileName.contains("/") || storedFileName.contains("\\")) {
            System.err.println(">>> [검증] 보안 위험 파일명: " + storedFileName);
            return false;
        }
        
        // 추가적인 권한 검증 로직을 여기에 구현할 수 있습니다.
        // 예: 해당 파일이 실제로 해당 사용자가 업로드한 파일인지 DB에서 확인
        
        System.out.println(">>> [검증] 파일 다운로드 권한 검증 통과");
        return true;
    }
} 