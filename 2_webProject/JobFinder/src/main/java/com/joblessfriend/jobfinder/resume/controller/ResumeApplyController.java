package com.joblessfriend.jobfinder.resume.controller;

import com.joblessfriend.jobfinder.member.domain.MemberVo;
import com.joblessfriend.jobfinder.resume.service.ResumeApplyService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/resume/apply")
public class ResumeApplyController {

    @Autowired
    private ResumeApplyService resumeApplyService;

    /**
     * 이력서 복사 및 지원 처리
     * @param resumeId 원본 이력서 ID
     * @param session 로그인 세션
     * @return 생성된 복사 이력서 ID
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity<?> applyResume(@RequestParam("resumeId") int resumeId,@RequestParam("jobPostId") int jobPostId, HttpSession session) {

        MemberVo loginUser = (MemberVo) session.getAttribute("userLogin");

        if (loginUser == null) {
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }

        try {
            int copiedResumeId = resumeApplyService.applyResumeWithCopy(resumeId, jobPostId, loginUser.getMemberId());
            String message = "입사지원 완료";
            return ResponseEntity.ok(message);


        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("지원 실패: " + e.getMessage());
        }
    }
}
