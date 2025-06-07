package com.joblessfriend.jobfinder.resume.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.joblessfriend.jobfinder.member.domain.MemberVo;
import com.joblessfriend.jobfinder.resume.domain.CareerVo;
import com.joblessfriend.jobfinder.resume.domain.ResumeVo;
import com.joblessfriend.jobfinder.resume.parser.ResumeParser;
import com.joblessfriend.jobfinder.resume.service.ResumeService;
import com.joblessfriend.jobfinder.jobGroup.service.JobGroupService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joblessfriend.jobfinder.job.service.JobService;
import com.joblessfriend.jobfinder.util.file.FileUtils;
import com.joblessfriend.jobfinder.skill.domain.SkillVo;
import com.joblessfriend.jobfinder.skill.service.SkillService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/resume")
public class ResumeController {

	@Autowired
	private ResumeService resumeService;

	@Autowired
	private FileUtils fileUtils;

	@Autowired
	private ResumeParser resumeParser;

	@Autowired
	private JobGroupService jobGroupService;

	@Autowired
	private JobService jobService;

	@Autowired
	private SkillService skillService;

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

		// 직군/직무 목록을 모델에 추가 (신규, 수정 모두)
		model.addAttribute("jobGroupList", jobGroupService.selectAllJobGroupsForAjax());
		// 모든 직무 목록 추가 (업데이트 시 기존 선택된 직무 표시용)
		List<com.joblessfriend.jobfinder.job.domain.JobVo> allJobs = new ArrayList<>();
		jobGroupService.selectAllJobGroupsForAjax().forEach(group -> {
			allJobs.addAll(jobService.selectJobsByGroupId(group.getJobGroupId()));
		});
		model.addAttribute("jobList", allJobs);

		// 수정 모드인 경우 이력서 데이터 조회
		if (resumeId != null && resumeId > 0) {
			try {

				// 이력서 전체 정보 조회
				ResumeVo resumeVo = resumeService.getResumeWithAllDetails(resumeId);

				if (resumeVo == null) {

					model.addAttribute("errorMessage", "이력서를 찾을 수 없습니다.");
					return "resume/resumeView";
				}

				// 본인 이력서인지 확인
				if (resumeVo.getMemberId() != loginUser.getMemberId()) {

					model.addAttribute("errorMessage", "본인의 이력서만 수정할 수 있습니다.");
					return "resume/resumeView";
				}

				// 모델에 이력서 데이터 추가
				model.addAttribute("resumeData", resumeVo);
				model.addAttribute("isEditMode", true);
				model.addAttribute("currentResumeId", resumeId);

				// 스킬 데이터 추가
				try {
					List<SkillVo> skillList = skillService.resumeTagList(resumeId);

					resumeVo.setSkillList(skillList != null ? skillList : new ArrayList<>());

					model.addAttribute("skillList", skillList != null ? skillList : new ArrayList<>());
				} catch (Exception e) {
					System.err.println(">>> [ResumeController] 스킬 데이터 조회 실패: " + e.getMessage());
					model.addAttribute("skillList", new ArrayList<>());
				}

				// 수정용 JSP 반환
				return "resume/resumeUpdateView";

			} catch (Exception e) {
				System.err.println(">>> [ResumeController] 이력서 조회 실패: " + e.getMessage());
				e.printStackTrace();
				model.addAttribute("errorMessage", "이력서 조회 중 오류가 발생했습니다: " + e.getMessage());
				return "resume/resumeView";
			}
		} else {
			// 신규 작성 모드
			model.addAttribute("isEditMode", false);

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
		try {
			// 로그인 체크
			MemberVo loginUser = (MemberVo) session.getAttribute("userLogin");
			if (loginUser == null) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
			}

			// 이력서 데이터 파싱
			ResumeVo resumeVo = resumeParser.parseMapToResumeVo(requestMap, loginUser.getMemberId());

			// 파싱 결과 확인
			if (resumeVo == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이력서 데이터 파싱에 실패했습니다.");
			}

			// 수정 모드인지 확인 (resumeId가 있고 0보다 큰 경우)
			if (resumeVo.getResumeId() != 0 && resumeVo.getResumeId() > 0) {
				// 수정 모드

				// 기존 이력서 조회하여 권한 확인
				ResumeVo existingResume = resumeService.getResumeByResumeId(resumeVo.getResumeId());
				if (existingResume == null) {
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body("이력서를 찾을 수 없습니다.");
				}

				if (existingResume.getMemberId() != loginUser.getMemberId()) {
					return ResponseEntity.status(HttpStatus.FORBIDDEN).body("본인의 이력서만 수정할 수 있습니다.");
				}

				// 이력서 수정
				resumeService.updateResume(resumeVo);

			} else {
				// 신규 작성 모드

				resumeService.saveResumeWithDetails(resumeVo);

			}

			return ResponseEntity.ok("이력서가 성공적으로 저장되었습니다.");

		} catch (Exception e) {
			System.err.println(">>> [ResumeController] 이력서 저장 실패: " + e.getMessage());
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("이력서 저장 중 오류가 발생했습니다: " + e.getMessage());
		}
	}

	// 이력서 이미지를 db 에저장
	@PostMapping("/uploadPhoto")
	@ResponseBody
	public Map<String, Object> uploadProfilePhoto(@RequestParam("photo") MultipartFile file,
			@RequestParam("resumeId") int resumeId, HttpSession session, HttpServletRequest request) {
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
			if (!dir.exists())
				dir.mkdirs();

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

	// 화면에 이미지 저장
	@PostMapping("/profile-temp/uploadImage")
	@ResponseBody
	public Map<String, Object> uploadProfileImage(@RequestParam("profileImage") MultipartFile file, HttpSession session) {
		Map<String, Object> result = new HashMap<>();

		// 세션에서 임시 이미지 목록 가져오기
		List<Map<String, Object>> uploadedFiles = (List<Map<String, Object>>) session.getAttribute("uploadedFiles");
		if (uploadedFiles == null) {
			uploadedFiles = new ArrayList<>();
		}

		try {
			// 업로드 경로 설정 (C://upload/profile/)
			String uploadDir = "C:/upload/profile/";
			File dir = new File(uploadDir);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			// 저장 파일명 생성
			String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
			File dest = new File(uploadDir + filename);
			file.transferTo(dest);

			// 웹에서 접근 가능한 URL 생성
			String fileUrl = "/profile/" + filename;

			// 세션에 업로드한 파일 정보 저장
			Map<String, Object> fileInfo = new HashMap<>();
			fileInfo.put("fileName", file.getOriginalFilename());
			fileInfo.put("storedFileName", filename);
			fileInfo.put("fileUrl", fileUrl);
			uploadedFiles.add(fileInfo);

			session.setAttribute("uploadedFiles", uploadedFiles);

			result.put("success", true);
			result.put("imageUrl", fileUrl);
			result.put("fileName", file.getOriginalFilename());

		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("error", "업로드 실패: " + e.getMessage());
		}

		return result;
	}

	// 포트폴리오 첨부파일을 세션에 저장
	@PostMapping("/uploadFile")
	@ResponseBody
	public Map<String, Object> uploadFile(@RequestParam("file") MultipartFile file, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();

		// 세션에서 업로드된 파일 목록 가져오기
		List<Map<String, Object>> uploadedFiles = (List<Map<String, Object>>) session.getAttribute("uploadedFiles");
		if (uploadedFiles == null) {
			uploadedFiles = new ArrayList<>();
		}

		try {
			// 업로드 경로 설정
			String uploadDir = "C:/upload/portfolio/";
			File dir = new File(uploadDir);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			// 저장 파일명 생성
			String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
			File dest = new File(uploadDir + filename);
			file.transferTo(dest);

			// 파일 확장자 추출
			String fileExtension = "";
			String originalFilename = file.getOriginalFilename();
			if (originalFilename != null && originalFilename.lastIndexOf(".") > 0) {
				fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
			}

			// 세션에 업로드한 파일 정보 저장
			Map<String, Object> fileInfo = new HashMap<>();
			fileInfo.put("fileName", file.getOriginalFilename());
			fileInfo.put("storedFileName", filename);
			fileInfo.put("fileExtension", fileExtension);
			uploadedFiles.add(fileInfo);

			session.setAttribute("uploadedFiles", uploadedFiles);

			result.put("success", true);
			result.put("fileName", file.getOriginalFilename());
			result.put("storedFileName", filename);
			result.put("fileExtension", fileExtension);

		} catch (Exception e) {
			result.put("success", false);
			result.put("error", "업로드 실패: " + e.getMessage());
		}

		return result;
	}

	// 파일 삭제
	@DeleteMapping("/deleteFile/{portfolioId}")
	@ResponseBody
	public ResponseEntity<String> deleteImage(@PathVariable("portfolioId") String fileId, HttpSession session) {
		List<Map<String, Object>> uploadedFiles = (List<Map<String, Object>>) session.getAttribute("uploadedFiles");

		if (uploadedFiles != null) {
			uploadedFiles.removeIf(file -> fileId.equals(file.get("storedFileName"))); // storedFileName가 같은 경우 리스트에서 제외
			session.setAttribute("uploadedFiles", uploadedFiles); // 리스트 갱신
		}

		return ResponseEntity.ok("삭제 성공");
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

		ResumeVo resumeVo = resumeParser.parseMapToResumeVo(requestMap, loginUser.getMemberId());
		session.setAttribute("resumePreviewData", resumeVo);

		return ResponseEntity.ok("success");
	}

	@GetMapping("/viewPreview")
	public String showPreview(HttpSession session, Model model) {
		ResumeVo resume = (ResumeVo) session.getAttribute("resumePreviewData");

		model.addAttribute("resume", resume);

		// 직무직군 id값을 네이밍으로 변환
		List<Map<String, String>> jobTitles = new ArrayList<>();
		for (CareerVo career : resume.getCareerList()) {
			Map<String, String> map = new HashMap<>();
			map.put("jobGroupName", jobGroupService.getJobGroupNameById(career.getJobGroupId()));
			map.put("jobName", jobService.getJobNameById(career.getJobId()));
			jobTitles.add(map);
		}
		// skillList에서 태그명 정보 보완
		List<SkillVo> skillList = resume.getSkillList();
		System.out.println(">>> skillList in preview: ");
		if (skillList != null) {
			for (SkillVo skill : skillList) {
				// 태그명이 없는 경우 서비스에서 조회하여 보완
				if (skill.getTagName() == null || skill.getTagName().isEmpty()) {
					SkillVo fullSkill = skillService.getSkillById(skill.getTagId());
					if (fullSkill != null) {
						skill.setTagName(fullSkill.getTagName());
					}
				}
				System.out.println(" - tagId: " + skill.getTagId() + ", tagName: " + skill.getTagName());
			}
		}

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
		if (resume == null || resume.getMemberId() != loginUser.getMemberId()) {
			model.addAttribute("errorMessage", "이력서를 조회할 수 없습니다.");
			return "resume/resumeView";
		}

		// 직무직군명 변환
		List<Map<String, String>> jobTitles = new ArrayList<>();
		for (CareerVo career : resume.getCareerList()) {
			Map<String, String> map = new HashMap<>();
			map.put("jobGroupName", jobGroupService.getJobGroupNameById(career.getJobGroupId()));
			map.put("jobName", jobService.getJobNameById(career.getJobId()));
			jobTitles.add(map);
		}
		model.addAttribute("jobTitles", jobTitles);



		// 모델에 이력서 객체 바인딩
		model.addAttribute("resume", resume);

		return "resume/resumePreview";
	}

}
