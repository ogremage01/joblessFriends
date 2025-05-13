package com.joblessfriend.jobfinder.resume.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.joblessfriend.jobfinder.member.domain.MemberVo;
import com.joblessfriend.jobfinder.resume.domain.ResumeVo;
import com.joblessfriend.jobfinder.resume.service.ResumeService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/resume")
public class ResumeController {
	
	@Autowired
	private ResumeService resumeService;
	
	@GetMapping("/write")
	public String resumeWritePage() {
		return "resume/resumeView";
	}
	
	@GetMapping("/management")
	public String resumeManagement(HttpSession session, Model model) {
        MemberVo memberVo = (MemberVo) session.getAttribute("userLogin");
        if (memberVo == null) return "redirect:/auth/login";
        
        Integer memberId = memberVo.getMemberId();

        List<ResumeVo> resumes = resumeService.getResumesByMemberId(memberId);
        model.addAttribute("resumes", resumes);
        return "resume/resumeManagementView";
    }

    @PostMapping("/delete")
    public String deleteResume(HttpSession session,
                               @RequestParam("resumeId") int resumeId) {
        MemberVo memberVo = (MemberVo) session.getAttribute("userLogin");
        if (memberVo == null) return "redirect:/auth/login";
        
        int memberId = memberVo.getMemberId();

        resumeService.deleteResume(memberId, resumeId);
        return "redirect:/resume/management";
    }
}
