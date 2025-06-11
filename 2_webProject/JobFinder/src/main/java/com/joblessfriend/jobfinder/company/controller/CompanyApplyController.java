package com.joblessfriend.jobfinder.company.controller;

import com.joblessfriend.jobfinder.company.domain.ApplySummaryVo;
import com.joblessfriend.jobfinder.company.domain.CompanyVo;
import com.joblessfriend.jobfinder.company.domain.QuestionAnswerVo;
import com.joblessfriend.jobfinder.company.service.CompanyApplyService;
import com.joblessfriend.jobfinder.recruitment.domain.RecruitmentVo;
import com.joblessfriend.jobfinder.recruitment.service.RecruitmentService;
import com.joblessfriend.jobfinder.resume.domain.ResumeVo;
import com.joblessfriend.jobfinder.resume.service.ResumeMatchService;
import com.joblessfriend.jobfinder.resume.service.ResumeService;
import com.joblessfriend.jobfinder.util.Pagination;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/company/apply")
public class CompanyApplyController {

    @Autowired
    private CompanyApplyService companyApplyService;

    @Autowired
    private RecruitmentService recruitmentService;
    @Autowired
    private ResumeService resumeService;

    @Autowired
    private ResumeMatchService resumeMatchService;

    @GetMapping("/{jobPostId}/applicants")
    public String getApplicants(@PathVariable int jobPostId,
                                @RequestParam(defaultValue = "1") int page,
                                HttpSession session,
                                Model model) {

        CompanyVo loginCompany = (CompanyVo) session.getAttribute("userLogin");
        if (loginCompany == null) {
            return "redirect:/auth/login";
        }

        int recordSize = 5;
        int pageSize = 10;

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("companyId", loginCompany.getCompanyId());
        paramMap.put("jobPostId", jobPostId);

        // 전체 지원자 수
        int totalCount = companyApplyService.countApplyByCompany(paramMap);

        // Pagination 객체 생성
        Pagination pagination = new Pagination(totalCount, page, recordSize, pageSize);

        // 페이징 범위 설정
        paramMap.put("startRow", pagination.getLimitStart() + 1); // Oracle은 1부터
        paramMap.put("endRow", pagination.getLimitStart() + recordSize);

        // 지원자 목록 조회
        List<ApplySummaryVo> applyList = companyApplyService.getPagedApplyList(paramMap);
        RecruitmentVo recruitmentVo = recruitmentService.getRecruitmentId(jobPostId);

        // ✅ 적합도 점수 계산 및 주입
        for (ApplySummaryVo apply : applyList) {
            int resumeId = apply.getResumeId(); // getter 확인 필요
            ResumeVo resumeVo = resumeService.getResumeCopyWithAllDetails(resumeId);

            apply.setMatchScore(resumeVo.getMatchScore()); // 필드 없으면 추가해야 함
        }
        // 뷰에 데이터 전달
        model.addAttribute("applyList", applyList);
        model.addAttribute("pagination", pagination);

        return "company/recruitment/applicantsListView";
    }
    @GetMapping("/question-answer")
    @ResponseBody
    public List<QuestionAnswerVo> getQuestionAnswers(@RequestParam int jobPostId,
                                                     @RequestParam int memberId) {
        return companyApplyService.getQuestionAnswersByJobPostAndMember(jobPostId, memberId);
    }

    @PostMapping("/updateState")
    @ResponseBody
    public ResponseEntity<String> updateResumeState(@RequestParam int jobPostId,
                                                    @RequestParam int memberId,
                                                    @RequestParam int stateId) {
        try {
            companyApplyService.updateResumeState(jobPostId, memberId, stateId);
            return ResponseEntity.ok("상태가 성공적으로 업데이트되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("상태 업데이트 중 오류가 발생했습니다.");
        }
    }


}
