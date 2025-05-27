package com.joblessfriend.jobfinder.resume.controller;

import com.joblessfriend.jobfinder.resume.domain.ResumeSaveRequestVo;
import com.joblessfriend.jobfinder.resume.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/resume")
@RequiredArgsConstructor
public class ResumeApiController {

    private final ResumeService resumeService;

    /**
     * 이력서 저장 API
     * 프론트에서 비동기로 JSON 형태로 전달받아 이력서 전체 저장 처리
     */
    @PostMapping("/save")
    public ResponseEntity<?> saveResume(@RequestBody ResumeSaveRequestVo request, Principal principal) {
        // 로그인 사용자 이름 (보통 username 또는 email)
        String username = principal.getName();

        // 저장 로직 호출 (resume + school + career 등 하위 모두 포함)
        resumeService.saveResume(request, username);

        return ResponseEntity.ok("이력서 저장 성공");
    }
}
