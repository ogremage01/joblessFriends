package com.joblessfriend.jobfinder.company.controller;

import com.joblessfriend.jobfinder.company.domain.CompanyApplyVo;
import com.joblessfriend.jobfinder.company.domain.CompanyVo;
import com.joblessfriend.jobfinder.company.service.CompanyApplyService;
import com.joblessfriend.jobfinder.util.Pagination;
import com.joblessfriend.jobfinder.util.SearchVo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/company/apply")
public class CompanyApplyController {

    @Autowired
    private CompanyApplyService companyApplyService;

    @GetMapping("/list")
    public String getApplyList(@ModelAttribute SearchVo searchVo, Model model, HttpSession session) {

        // 기업 ID는 세션 또는 로그인 정보에서 가져온다고 가정
        CompanyVo loginCompany = (CompanyVo) session.getAttribute("companyLogin");
        if (loginCompany == null) {
            return "redirect:/company/login"; // 로그인 안 됐으면 로그인 페이지로
        }

        int companyId = loginCompany.getCompanyId();
        searchVo.setRecordSize(5); // 페이지당 5명씩
        searchVo.setCompanyId(companyId); // ✅ SearchVo에 추가 필드로 세팅

        int totalCount = companyApplyService.getApplyMemberCount(searchVo);
        Pagination pagination = new Pagination(totalCount, searchVo);

        searchVo.setStartRow(pagination.getLimitStart() + 1);
        searchVo.setEndRow(searchVo.getStartRow() + searchVo.getRecordSize() - 1);

        List<CompanyApplyVo> applyList = companyApplyService.getApplyMemberList(searchVo);

        model.addAttribute("applyList", applyList);
        model.addAttribute("pagination", pagination);
        model.addAttribute("searchVo", searchVo);

        return "company/applyList"; // JSP 경로에 맞게 변경
    }
}

