package com.joblessfriend.jobfinder.resume.controller;

import com.joblessfriend.jobfinder.member.domain.MemberVo;
import com.joblessfriend.jobfinder.recruitment.domain.JobPostAnswerVo;
import com.joblessfriend.jobfinder.recruitment.domain.JobPostQuestionVo;
import com.joblessfriend.jobfinder.resume.domain.ResumeApplyRequestVo;
import com.joblessfriend.jobfinder.resume.service.ResumeApplyService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/resume/apply")
public class ResumeApplyController {

    @Autowired
    private ResumeApplyService resumeApplyService;

    /**
     * 이력서 복사 및 지원 처리
     * @param session 로그인 세션
     * @return 생성된 복사 이력서 ID
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity<?> applyResume(@RequestBody ResumeApplyRequestVo vo, HttpSession session) {
        MemberVo loginUser = (MemberVo) session.getAttribute("userLogin");

        if (loginUser == null) {
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }

        try {
            vo.setMemberId(loginUser.getMemberId()); // 세션으로 memberId 덮어쓰기
            resumeApplyService.applyResumeWithCopy(vo.getResumeId(), vo.getJobPostId(), vo.getMemberId(), vo.getAnswerList());
            return ResponseEntity.ok("입사지원 완료");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("지원 실패: " + e.getMessage());
        }
    }


    @GetMapping("/questions")
    @ResponseBody
    public ResponseEntity<?> getQuestions(@RequestParam("jobPostId") int jobPostId) {
        try {
            List<JobPostQuestionVo> questions = resumeApplyService.getQuestionsByJobPostId(jobPostId);

            if (questions == null || questions.isEmpty()) {
                return ResponseEntity.ok().body(List.of()); // 빈 리스트 반환
            }

            return ResponseEntity.ok(questions);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("질문 조회 실패");
        }
    }
    @PostMapping("/answers")
    @ResponseBody
    public ResponseEntity<?> saveAnswers(@RequestBody List<JobPostAnswerVo> answerList, HttpSession session) {
        MemberVo loginUser = (MemberVo) session.getAttribute("userLogin");
        if (loginUser == null) return ResponseEntity.status(401).body("로그인 필요");

        try {
            for (JobPostAnswerVo a : answerList) {
                a.setMemberId(loginUser.getMemberId());
            }
            resumeApplyService.insertAnswers(answerList);  // ✅ 일괄 insert
            return ResponseEntity.ok("저장완료");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("실패");
        }
    }


    //지원하기버튼  중복체크 맵핑//
    @GetMapping("/check")
    @ResponseBody
    public int checkAlreadyApplied(@RequestParam int jobPostId, HttpSession session) {
        MemberVo user = (MemberVo) session.getAttribute("userLogin");
        int checkNum;
        if (user == null) {
            checkNum = 2;
            return checkNum;
        } // 로그인 안 되어 있으면 막기 2번 로그인필요로 사용할예정 //
        System.out.println("확인된: 잡포스트아이디" +jobPostId);
        System.out.println("확인된: 멤버아이디" + user.getMemberId());
        checkNum = resumeApplyService.hasAlreadyApplied(user.getMemberId(), jobPostId);
        return checkNum;
    }
}
