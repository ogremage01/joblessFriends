package com.joblessfriend.jobfinder.resume.controller;

import com.joblessfriend.jobfinder.member.dao.MemberDao;
import com.joblessfriend.jobfinder.member.domain.MemberVo;
import com.joblessfriend.jobfinder.resume.domain.ResumeSaveRequestVo;
import com.joblessfriend.jobfinder.resume.service.ResumeService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/resume")
@RequiredArgsConstructor
public class ResumeApiController {

    private final ResumeService resumeService;
    private final MemberDao memberDao;

    /**
     * 이력서 저장 API
     * 프론트에서 비동기로 JSON 형태로 전달받아 이력서 전체 저장 처리
     */
    @PostMapping("/save")
    public ResponseEntity<?> saveResume(@RequestBody ResumeSaveRequestVo request, HttpSession session) {
    	// 세션에서 로그인한 사용자 정보 꺼내기
        MemberVo loginUser = (MemberVo) session.getAttribute("userLogin");
        
        System.out.println("세션 loginUser 확인: " + loginUser.getEmail());
        if (loginUser == null) {
            throw new IllegalStateException("로그인한 사용자만 이력서를 저장할 수 있습니다.");
        }

        int memberId = loginUser.getMemberId();
        System.out.println(">>> 로그인된 memberId = " + memberId);

        // 저장 로직 호출
        resumeService.saveResume(request, memberId);

        return ResponseEntity.ok("이력서 저장 완료");
    }
}
