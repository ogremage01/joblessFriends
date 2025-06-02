package com.joblessfriend.jobfinder.company.controller;

import com.joblessfriend.jobfinder.company.domain.ApplySummaryVo;
import com.joblessfriend.jobfinder.company.domain.CompanyVo;
import com.joblessfriend.jobfinder.company.service.CompanyApplyService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/company/apply")
public class CompanyApplyController {

    @Autowired
    private CompanyApplyService companyApplyService;

    @GetMapping("/{jobPostId}/applicants")
    public String getApplicants(@PathVariable int jobPostId,
                                HttpSession session,
                                Model model) {

        CompanyVo loginCompany = (CompanyVo) session.getAttribute("userLogin");
        if (loginCompany == null) {
            return "redirect:/auth/login";
        }

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("companyId", loginCompany.getCompanyId());
        paramMap.put("jobPostId", jobPostId);
        paramMap.put("startRow", 1);
        paramMap.put("endRow", 5);

        // 전체 지원자 수
        int count = companyApplyService.countApplyByCompany(paramMap);
        model.addAttribute("applyCount", count);

        // 지원자 목록
        List<ApplySummaryVo> applyList = companyApplyService.getPagedApplyList(paramMap);
        model.addAttribute("applyList", applyList);

        return "company/recruitment/applicantsListView";
    }
}
