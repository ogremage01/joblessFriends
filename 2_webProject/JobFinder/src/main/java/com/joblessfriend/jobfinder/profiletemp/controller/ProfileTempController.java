package com.joblessfriend.jobfinder.profiletemp.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.joblessfriend.jobfinder.member.domain.MemberVo;
import com.joblessfriend.jobfinder.profiletemp.domain.ProfileTempVo;
import com.joblessfriend.jobfinder.profiletemp.service.ProfileTempService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/profile-temp")
public class ProfileTempController {

    @Autowired
    private ProfileTempService profileTempService;

    @PostMapping("/uploadImage")
    @ResponseBody
    public Map<String, Object> uploadProfileImage(@RequestParam("profileImage") MultipartFile file,
                                     HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        
        MemberVo memberVo = (MemberVo) session.getAttribute("userLogin");
        if (memberVo == null) {
            result.put("success", false);
            result.put("error", "로그인이 필요합니다");
            return result;
        }

        int memberId = memberVo.getMemberId();

        try {
            // 1. 원본 이름 + 저장 파일명 생성
            String originalFilename = file.getOriginalFilename();
            String savedFilename = UUID.randomUUID().toString();

            // 2. 저장 경로 설정
            String uploadDir = "C:/upload/profile/";
            
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs(); // 폴더가 없으면 생성
            }
            
            File dest = new File(uploadDir + savedFilename);
            file.transferTo(dest);

            // 3. DB에 저장
            ProfileTempVo vo = new ProfileTempVo(memberId, savedFilename);
            profileTempService.deleteByMemberId(memberId); // 기존 데이터 제거
            profileTempService.insertProfileTemp(vo);

            // 웹에서 접근 가능한 URL 생성
            String fileUrl = "/profile/" + savedFilename;
            
            result.put("success", true);
            result.put("imageUrl", fileUrl);
            result.put("fileName", originalFilename);
            result.put("storedFileName", savedFilename);

        } catch (IOException e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("error", "업로드 실패: " + e.getMessage());
        }
        
        return result;
    }
    
    @DeleteMapping("/deleteImage/{storedFileName}")
    @ResponseBody
    public Map<String, Object> deleteProfileImage(@PathVariable("storedFileName") String storedFileName,
                                                   HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        
        // 1. 세션 검증 (컨트롤러의 책임)
        MemberVo memberVo = (MemberVo) session.getAttribute("userLogin");
        if (memberVo == null) {
            result.put("success", false);
            result.put("error", "로그인이 필요합니다");
            return result;
        }

        try {
            // 2. 비즈니스 로직을 서비스에 위임
            profileTempService.deleteProfileImageAndData(storedFileName, memberVo.getMemberId());
            
            result.put("success", true);
            result.put("message", "프로필 이미지가 삭제되었습니다");
            
        } catch (IllegalArgumentException e) {
            result.put("success", false);
            result.put("error", "입력값이 올바르지 않습니다");
        } catch (SecurityException e) {
            result.put("success", false);
            result.put("error", "접근 권한이 없습니다");
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", "삭제 중 오류가 발생했습니다");
        }

        return result;
    }
}
