package com.joblessfriend.jobfinder.profiletemp.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public String uploadProfileImage(@RequestParam("profileImage") MultipartFile file,
                                     HttpSession session) {
        MemberVo memberVo = (MemberVo) session.getAttribute("userLogin");
        if (memberVo == null) return "unauthorized";

        int memberId = memberVo.getMemberId();

        try {
            // 1. 원본 이름 + 저장 파일명 생성
            String originalFilename = file.getOriginalFilename();
            String savedFilename = UUID.randomUUID() + "_" + originalFilename;

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

            return savedFilename;

        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }
}
