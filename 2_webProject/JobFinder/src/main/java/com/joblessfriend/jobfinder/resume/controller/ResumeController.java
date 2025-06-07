package com.joblessfriend.jobfinder.resume.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.joblessfriend.jobfinder.member.domain.MemberVo;
import com.joblessfriend.jobfinder.resume.domain.PortfolioVo;
import com.joblessfriend.jobfinder.resume.domain.ResumeVo;
import com.joblessfriend.jobfinder.resume.domain.SchoolVo;
import com.joblessfriend.jobfinder.resume.service.ResumeService;
import com.joblessfriend.jobfinder.util.service.FileUploadService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/resume")
public class ResumeController {

	@Autowired
	private ResumeService resumeService;

	@Autowired
	private FileUploadService fileUploadService;

	@Value("${file.upload.resume.maxSize:10485760}") // 10MB 기본값
	private long maxFileSize;

	// ========== JSP 페이지 반환 메서드들 ==========

	// 이력서 폼으로 이동 (신규 작성 & 수정 통합)
	@GetMapping("/write")
	public String resumeWritePage(@RequestParam(value = "resumeId", required = false) Integer resumeId,
			HttpSession session, Model model) {

		// 로그인 체크
		MemberVo loginUser = (MemberVo) session.getAttribute("userLogin");
		if (loginUser == null) {
			return "redirect:/auth/login";
		}

		// 비즈니스 서비스를 통해 페이지 데이터 준비
		Map<String, Object> pageData = resumeService.prepareResumeWritePageData(resumeId, loginUser);
		
		// 에러가 있는 경우
		if (pageData.containsKey("error")) {
			model.addAttribute("errorMessage", pageData.get("error"));
			return "resume/resumeView";
		}
		
		// 모델에 데이터 추가
		model.addAllAttributes(pageData);
		
		// 수정 용 JSP 반환 여부 결정
		if (Boolean.TRUE.equals(pageData.get("isEditMode"))) {
			return "resume/resumeUpdateView";
		}

		return "resume/resumeView";
	}

	// 이력서 목록에서 이력서 출력 controller(데이터가 없으면 빈화면 출력)
	@GetMapping("/management")
	public String resumeManagement(HttpSession session, Model model) {
		// 세션에서 로그인 사용자 확인
		MemberVo loginUser = (MemberVo) session.getAttribute("userLogin");
		if (loginUser == null) {
			return "redirect:/auth/login"; // 로그인 안 된 경우
		}

		int memberId = loginUser.getMemberId();

		// 이력서 + 요약 정보 조회
		List<ResumeVo> resumes = resumeService.getResumeListWithSummaryByMemberId(memberId);

		System.out.println(">>>> 이력서 개수 = " + resumes.size());

		model.addAttribute("resumes", resumes);

		return "resume/resumeManagementView"; // -> /WEB-INF/views/resume/resumeManagementView.jsp
	}

	// 이력서 목록에서 이력서 삭제
	@PostMapping("/delete")
	public String deleteResume(HttpSession session, @RequestParam("resumeId") int resumeId) {
		MemberVo memberVo = (MemberVo) session.getAttribute("userLogin");
		if (memberVo == null)
			return "redirect:/auth/login";

		int memberId = memberVo.getMemberId();

		resumeService.deleteResume(memberId, resumeId);
		return "redirect:/resume/management";
	}

	// ========== 파일 업로드 관련 메서드들 ==========

	// 이력서 저장/수정 API
	@PostMapping("/save")
	@ResponseBody
	public ResponseEntity<String> saveResume(@RequestBody Map<String, Object> requestMap, HttpSession session) {
		// 로그인 체크
		MemberVo loginUser = (MemberVo) session.getAttribute("userLogin");
		if (loginUser == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
		}

		// 비즈니스 서비스를 통한 저장/수정 처리 (세션 포함)
		String result = resumeService.saveOrUpdateResume(requestMap, loginUser, session);
		
		if ("success".equals(result)) {
			return ResponseEntity.ok("이력서가 성공적으로 저장되었습니다.");
		} else if (result.contains("본인의 이력서만")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(result);
		} else if (result.contains("찾을 수 없습니다")) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
		} else if (result.contains("파싱에 실패")) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
		}
	}

	// 이력서 이미지를 db 에저장
	@PostMapping("/uploadPhoto")
	@ResponseBody
	public Map<String, Object> uploadProfilePhoto(@RequestParam("photo") MultipartFile file,
			@RequestParam("resumeId") int resumeId, HttpSession session, HttpServletRequest request) {
		
		// 로그인 유저 확인
		MemberVo memberVo = (MemberVo) session.getAttribute("userLogin");
		if (memberVo == null) {
			Map<String, Object> result = new HashMap<>();
			result.put("success", false);
			result.put("error", "로그인 필요");
			return result;
		}

		// 파일 업로드 서비스를 통한 처리
		return fileUploadService.uploadProfilePhoto(file, resumeId, memberVo.getMemberId(), request);
	}

	// 화면에 이미지 저장
	@PostMapping("/profile-temp/uploadImage")
	@ResponseBody
	public Map<String, Object> uploadProfileImage(@RequestParam("profileImage") MultipartFile file, HttpSession session) {
		// 파일 업로드 서비스를 통한 처리
		return fileUploadService.uploadProfileImageToSession(file, session);
	}

	// 포트폴리오 첨부파일을 세션에 저장
	@PostMapping("/uploadFile")
	@ResponseBody
	public Map<String, Object> uploadFile(@RequestParam("file") MultipartFile file, HttpSession session,
			HttpServletRequest request) {
		// 파일 업로드 서비스를 통한 처리
		return fileUploadService.uploadPortfolioFile(file, session);
	}

	// 파일 삭제
	@DeleteMapping("/deleteFile/{portfolioId}")
	@ResponseBody
	public ResponseEntity<String> deleteImage(@PathVariable("portfolioId") String fileId, HttpSession session) {
		boolean deleted = fileUploadService.deleteFileFromSession(fileId, session);
		return ResponseEntity.ok(deleted ? "삭제 성공" : "삭제 실패");
	}

	// 미리보기
	// 미리보기 세션저장용
	@PostMapping("/preview")
	@ResponseBody
	public ResponseEntity<String> preparePreview(@RequestBody Map<String, Object> requestMap, HttpSession session) {
		MemberVo loginUser = (MemberVo) session.getAttribute("userLogin");
		if (loginUser == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
		}

		resumeService.prepareResumePreviewData(requestMap, loginUser, session);
		return ResponseEntity.ok("success");
	}

	@GetMapping("/viewPreview")
	public String showPreview(HttpSession session, Model model) {
		ResumeVo resume = (ResumeVo) session.getAttribute("resumePreviewData");
		
		// 포트폴리오 데이터 디버깅
		System.out.println(">>> [viewPreview] 이력서 ID: " + (resume != null ? resume.getResumeId() : "null"));
		if (resume != null) {
			List<PortfolioVo> portfolioList = resume.getPortfolioList();
			System.out.println(">>> [viewPreview] 포트폴리오 목록 크기: " + (portfolioList != null ? portfolioList.size() : "null"));
			if (portfolioList != null && !portfolioList.isEmpty()) {
				for (int i = 0; i < portfolioList.size(); i++) {
					PortfolioVo portfolio = portfolioList.get(i);
					System.out.println(">>> [viewPreview] 포트폴리오[" + i + "]: ID=" + portfolio.getPortfolioId() + 
						", 원본파일명=" + portfolio.getFileName() + ", 저장파일명=" + portfolio.getStoredFileName());
				}
			} else {
				System.out.println(">>> [viewPreview] 포트폴리오 목록이 비어있습니다.");
			}
		}

		// 스킬 데이터 보완
		resumeService.enrichSkillData(resume);
		
		// 직무직군 id값을 네이밍으로 변환
		List<Map<String, String>> jobTitles = resumeService.prepareJobTitleData(resume);

		model.addAttribute("resume", resume);
		model.addAttribute("jobTitles", jobTitles);

		return "resume/resumePreview"; // /WEB-INF/views/resume/resumePreview.jsp
	}

	// 이력서 관리페이지에서 이력서미리보기
	@GetMapping("/view/{resumeId}")
	public String showResumePreviewFromDatabase(@PathVariable("resumeId") int resumeId, HttpSession session,
			Model model) {
		// 로그인 여부 확인
		MemberVo loginUser = (MemberVo) session.getAttribute("userLogin");
		if (loginUser == null) {
			return "redirect:/auth/login";
		}

		// 이력서 전체 정보 조회 (career, school, skill 포함)
		ResumeVo resume = resumeService.getResumeWithAllDetails(resumeId);
		
		// 권한 체크
		if (resume == null || !resumeService.checkResumeOwnership(resumeId, loginUser.getMemberId())) {
			model.addAttribute("errorMessage", "이력서를 조회할 수 없습니다.");
			return "resume/resumeView";
		}
		
		// 포트폴리오 데이터 디버깅
		System.out.println(">>> [view/{resumeId}] 이력서 ID: " + resumeId);
		List<PortfolioVo> portfolioList = resume.getPortfolioList();
		System.out.println(">>> [view/{resumeId}] 포트폴리오 목록 크기: " + (portfolioList != null ? portfolioList.size() : "null"));
		if (portfolioList != null && !portfolioList.isEmpty()) {
			for (int i = 0; i < portfolioList.size(); i++) {
				PortfolioVo portfolio = portfolioList.get(i);
				System.out.println(">>> [view/{resumeId}] 포트폴리오[" + i + "]: ID=" + portfolio.getPortfolioId() + 
					", 원본파일명=" + portfolio.getFileName() + ", 저장파일명=" + portfolio.getStoredFileName());
			}
		} else {
			System.out.println(">>> [view/{resumeId}] 포트폴리오 목록이 비어있습니다.");
		}

		// 스킬 데이터 보완
		resumeService.enrichSkillData(resume);
		
		// 직무직군명 변환
		List<Map<String, String>> jobTitles = resumeService.prepareJobTitleData(resume);

		// 모델에 이력서 객체 바인딩
		model.addAttribute("resume", resume);
		model.addAttribute("jobTitles", jobTitles);

		return "resume/resumePreview";
	}

	// 포트폴리오 파일 다운로드
	@GetMapping("/download/{portfolioId}")
	public void downloadPortfolioFile(@PathVariable("portfolioId") String storedFileName, 
			HttpServletResponse response, HttpSession session) {
		
		// 로그인 체크
		MemberVo loginUser = (MemberVo) session.getAttribute("userLogin");
		if (loginUser == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		
		// 파일 다운로드 권한 검증
		if (!fileUploadService.validateFileDownloadPermission(storedFileName, loginUser.getMemberId())) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		
		try {
			// 파일 경로 설정
			String uploadDir = "C:/upload/portfolio/";
			File file = new File(uploadDir + storedFileName);
			
			// 파일 존재 여부 재확인
			if (!file.exists()) {
				System.err.println(">>> [다운로드] 파일이 존재하지 않음: " + file.getAbsolutePath());
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
			
			// 원본 파일명 추출 (UUID 제거)
			String originalFileName = storedFileName;
			if (storedFileName.contains("_")) {
				originalFileName = storedFileName.substring(storedFileName.indexOf("_") + 1);
			}
			
			System.out.println(">>> [다운로드] 파일 다운로드 시작: " + originalFileName);
			
			// 한글 파일명 인코딩
			String encodedFileName = URLEncoder.encode(originalFileName, "UTF-8").replaceAll("\\+", "%20");
			
			// 응답 헤더 설정
			response.setContentType("application/octet-stream");
			response.setContentLength((int) file.length());
			response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + encodedFileName);
			
			// 파일 스트림으로 전송
			try (FileInputStream fis = new FileInputStream(file);
				 OutputStream os = response.getOutputStream()) {
				
				byte[] buffer = new byte[1024];
				int bytesRead;
				int totalBytes = 0;
				while ((bytesRead = fis.read(buffer)) != -1) {
					os.write(buffer, 0, bytesRead);
					totalBytes += bytesRead;
				}
				os.flush();
				System.out.println(">>> [다운로드] 파일 전송 완료: " + originalFileName + " (" + totalBytes + " bytes)");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

}
