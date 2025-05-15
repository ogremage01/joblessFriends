package com.joblessfriend.jobfinder.resume.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.joblessfriend.jobfinder.member.domain.MemberVo;
import com.joblessfriend.jobfinder.resume.domain.ResumeVo;
import com.joblessfriend.jobfinder.resume.service.ResumeService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/resume")
public class ResumeController {
	
	@Autowired
	private ResumeService resumeService;
	
	//이력서 폼으로 이동
	@GetMapping("/write")
	public String resumeWritePage() {
		return "resume/resumeView";
	}
	
	//이력서 목록에서 이력서 출력 controller(데이터가 없으면 빈화면 출력)
	@GetMapping("/management")
	public String resumeManagement(HttpSession session, Model model) {
        MemberVo memberVo = (MemberVo) session.getAttribute("userLogin");
        if (memberVo == null) return "redirect:/auth/login";
        
        int memberId = memberVo.getMemberId();
        System.out.println(">>> memberId = " + memberId);

        List<ResumeVo> resumes = resumeService.getResumesByMemberId(memberId);
        model.addAttribute("resumes", resumes);
        return "resume/resumeManagementView";
    }

	//이력서 목록에서 이력서 삭제
    @PostMapping("/delete")
    public String deleteResume(HttpSession session,
                               @RequestParam("resumeId") int resumeId) {
        MemberVo memberVo = (MemberVo) session.getAttribute("userLogin");
        if (memberVo == null) return "redirect:/auth/login";
        
        int memberId = memberVo.getMemberId();

        resumeService.deleteResume(memberId, resumeId);
        return "redirect:/resume/management";
    }
    
    //이력서 이미지를 db 에저장
    @PostMapping("/uploadPhoto")
    @ResponseBody
    public Map<String, Object> uploadProfilePhoto(@RequestParam("photo") MultipartFile file,
            									  @RequestParam("resumeId") int resumeId,
            									  HttpSession session,
            									  HttpServletRequest request) {
    	Map<String, Object> result = new HashMap<>();
    	
    	// 로그인 유저 확인
    	MemberVo memberVo = (MemberVo) session.getAttribute("userLogin");
        if (memberVo == null) {
            result.put("success", false);
            result.put("error", "로그인 필요");
            return result;
        }
        
        try {
            // 업로드 경로 설정
            String uploadDir = request.getServletContext().getRealPath("/upload/resume/");
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            // 저장 파일명 생성
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File dest = new File(uploadDir + filename);
            file.transferTo(dest);

            // DB에 경로 저장
            String fileUrl = "/upload/resume/" + filename;
            resumeService.updateProfileImage(resumeId, memberVo.getMemberId(), fileUrl);

            result.put("success", true);
            result.put("url", fileUrl);
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", "업로드 실패: " + e.getMessage());
        }

        return result;

    }
    
    //화면에 이미지 저장
    @PostMapping("/uploadProfileImage")
    @ResponseBody
    public String uploadProfileImage(@RequestParam("resumeId") int resumeId,
                                     @RequestParam("profileImage") MultipartFile file,
                                     HttpSession session) {
        MemberVo memberVo = (MemberVo) session.getAttribute("userLogin");
        if (memberVo == null) return "unauthorized";

        int memberId = memberVo.getMemberId();

        try {
            // 1. 파일 이름 설정
            String originalFilename = file.getOriginalFilename();
            String savedFilename = UUID.randomUUID() + "_" + originalFilename;

            // 2. 저장 경로 설정
            String uploadDir = "C:/upload/profile/"; // 배포 시 서버 디렉토리에 맞게 조정
            File dest = new File(uploadDir + savedFilename);
            file.transferTo(dest);

            // 3. DB에 반영
            resumeService.updateProfileImage(resumeId, memberId, savedFilename);

            return savedFilename;
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

}
