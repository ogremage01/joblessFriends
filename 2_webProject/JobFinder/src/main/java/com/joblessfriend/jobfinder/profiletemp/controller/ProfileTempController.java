package com.joblessfriend.jobfinder.profiletemp.controller;

import java.util.HashMap;
import java.util.Map;

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
        
        // 1. 세션 검증 (컨트롤러의 책임)
        MemberVo memberVo = (MemberVo) session.getAttribute("userLogin");
        if (memberVo == null) {
            result.put("success", false);
            result.put("error", "로그인이 필요합니다");
            return result;
        }

        try {
            // 2. 비즈니스 로직을 서비스에 위임
            ProfileTempService.ProfileImageUploadResult uploadResult = 
                profileTempService.uploadProfileImage(file, memberVo.getMemberId());
            
            result.put("success", true);
            result.put("imageUrl", uploadResult.getFileUrl());
            result.put("fileName", uploadResult.getOriginalFileName());
            result.put("storedFileName", uploadResult.getStoredFileName());
            
        } catch (IllegalArgumentException e) {
            result.put("success", false);
            result.put("error", e.getMessage()); // 파일 검증 오류는 사용자에게 표시
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", "업로드 중 오류가 발생했습니다");
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
