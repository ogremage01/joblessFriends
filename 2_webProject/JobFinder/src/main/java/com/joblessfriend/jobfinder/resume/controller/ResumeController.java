package com.joblessfriend.jobfinder.resume.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.joblessfriend.jobfinder.member.domain.MemberVo;
import com.joblessfriend.jobfinder.resume.domain.ResumeVo;
import com.joblessfriend.jobfinder.resume.service.ResumeService;
import com.joblessfriend.jobfinder.util.file.FileUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/resume")
public class ResumeController {
	
	@Autowired
	private ResumeService resumeService;
	
	@Autowired
	private FileUtils fileUtils;
	
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
    
    //포트폴리오
	
	//이미지 업로드(서버 파일 시스템에만 저장)
		@PostMapping("/uploadFile")
		public ResponseEntity<Map<String, Object>> uploadImage(@RequestParam("uploadFile") 
		MultipartFile file, HttpSession session) throws Exception {
		    // fileUtils의 uploadFile 메서드 호출
		    Map<String, String> uploadResult = fileUtils.uploadFile(file);

		    String storedFileName = uploadResult.get("storedFileName");
		    String originalFileName = uploadResult.get("originalFileName");
		    String fileExtension = storedFileName.substring(storedFileName.lastIndexOf('.') + 1); // 확장자 추출
		    String fileUrl = "http://localhost:9090/resume/"+ storedFileName;

		    		
		    System.out.println("\n파일명: " + storedFileName);
		    System.out.println("원래파일명: " + originalFileName);
		    System.out.println("확장자명: "+ fileExtension);
		    System.out.println("링크: "+fileUrl);
		    System.out.println("파일 사이즈: "+ file.getSize());
		    
		    Map<String, Object> fileMap = new HashMap<>();

		    fileMap.put("originalFileName", originalFileName);
		    fileMap.put("storedFileName", storedFileName);
		    fileMap.put("fileSize", file.getSize());
		    fileMap.put("fileExtension", fileExtension);
		    fileMap.put("fileLink", fileUrl);
		    
		    
		    // 세션에 파일 정보 추가
		    List<Map<String, Object>> uploadedFiles = (List<Map<String, Object>>) session.getAttribute("uploadedFiles");
		    if (uploadedFiles == null) {
		        uploadedFiles = new ArrayList<>();
		    }
		    uploadedFiles.add(fileMap);
		    session.setAttribute("uploadedFiles", uploadedFiles);
		    

		    // DB에 저장
		  //  communityService.communityFileInsertOne(fileMap);
		    
		    
		    Map<String, Object> response = new HashMap<>();
		    response.put("fileUrl", fileUrl);
		    response.put("fileName", originalFileName);
		    response.put("fileId", storedFileName); // 고유 식별자 대체

		    return ResponseEntity.ok(response);
		}
		
		//파일 삭제
		@DeleteMapping("/deleteFile/{portfolioId}")
		public ResponseEntity<String> deleteImage(@PathVariable("portfolioId") String fileId, HttpSession session) {
		    List<Map<String, Object>> uploadedFiles = (List<Map<String, Object>>) session.getAttribute("uploadedFiles");

		    if (uploadedFiles != null) {
		        uploadedFiles.removeIf(file -> fileId.equals(file.get("storedFileName"))); // storedFileName가 같은 경우 리스트에서 제외
		        session.setAttribute("uploadedFiles", uploadedFiles); // 리스트 갱신
		    }

		    return ResponseEntity.ok("삭제 성공");
		}

}
