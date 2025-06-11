package com.joblessfriend.jobfinder.resume.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joblessfriend.jobfinder.recruitment.domain.RecruitmentVo;
import com.joblessfriend.jobfinder.recruitment.service.RecruitmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joblessfriend.jobfinder.job.service.JobService;
import com.joblessfriend.jobfinder.jobGroup.service.JobGroupService;
import com.joblessfriend.jobfinder.member.dao.MemberDao;
import com.joblessfriend.jobfinder.member.domain.MemberVo;
import com.joblessfriend.jobfinder.resume.dao.ResumeDao;
import com.joblessfriend.jobfinder.resume.domain.CareerVo;
import com.joblessfriend.jobfinder.resume.domain.CertificateResumeVo;
import com.joblessfriend.jobfinder.resume.domain.EducationVo;
import com.joblessfriend.jobfinder.resume.domain.PortfolioVo;
import com.joblessfriend.jobfinder.resume.domain.ResumeVo;
import com.joblessfriend.jobfinder.resume.domain.SchoolVo;
import com.joblessfriend.jobfinder.resume.parser.ResumeParser;
import com.joblessfriend.jobfinder.skill.domain.SkillVo;
import com.joblessfriend.jobfinder.skill.service.SkillService;

import jakarta.servlet.http.HttpSession;

@Service
public class ResumeServiceImpl implements ResumeService {

	@Autowired
	private ResumeDao resumeDao;

	@Autowired
	private MemberDao memberDao;

	@Autowired
	private SkillService skillService;

	@Autowired
	private ResumeMatchService resumeMatchService;

	@Autowired
	private RecruitmentService recruitmentService;

	@Autowired
	private JobGroupService jobGroupService;

	@Autowired
	private JobService jobService;

	@Autowired
	private ResumeParser resumeParser;

	@Override
	public List<ResumeVo> getResumesByMemberId(int memberId, int jobPostId) {
		List<ResumeVo> resumeList = resumeDao.findResumesByMemberId(memberId);
		RecruitmentVo recruitmentVo = recruitmentService.getRecruitmentId(jobPostId);

		for (ResumeVo resume : resumeList) {
			int resumeId = resume.getResumeId();
			resume.setSchoolList(resumeDao.getSchoolsByResumeId(resumeId));
			resume.setCareerList(resumeDao.getCareersByResumeId(resumeId));
			resume.setEducationList(resumeDao.getEducationsByResumeId(resumeId));
			resume.setPortfolioList(resumeDao.getPortfoliosByResumeId(resumeId));
			resume.setCertificateList(resumeDao.getCertificateByResumeId(resumeId));
			resume.setSkillList(resumeDao.getTagIdsByResumeId(resumeId));
			int score = resumeMatchService.calculateMatchScore(resume, recruitmentVo);
			resume.setMatchScore(score); // ResumeVo에 matchScore 필드가 있어야 함
		}

		return resumeList;
	}

	@Override
	public List<ResumeVo> getResumesByMemberId(int memberId) {
		return resumeDao.findResumesByMemberId(memberId);
	}

	@Override
	@Transactional
	public void deleteResume(int memberId, int resumeId) {
		// 1. 삭제할 이력서 정보 조회 (파일 정보 포함)
		ResumeVo resumeToDelete = resumeDao.getResumeWithAllDetails(resumeId);

		if (resumeToDelete != null) {
			// 2. 프로필 이미지 파일 삭제
			if (resumeToDelete.getProfile() != null && !resumeToDelete.getProfile().isEmpty()) {
				deleteFileFromSystem(resumeToDelete.getProfile(), "C:/upload/profile/");
			}

			// 3. 포트폴리오 파일들 삭제
			if (resumeToDelete.getPortfolioList() != null) {
				for (PortfolioVo portfolio : resumeToDelete.getPortfolioList()) {
					if (portfolio.getStoredFileName() != null && !portfolio.getStoredFileName().isEmpty()) {
						deleteFileFromSystem(portfolio.getStoredFileName(), "C:/upload/portfolio/");
					}
				}
			}
		}

		// 4. DB에서 이력서 삭제
		resumeDao.deleteResumeById(memberId, resumeId);
	}

	// 파일 시스템에서 파일 삭제하는 유틸리티 메서드
	private void deleteFileFromSystem(String fileName, String uploadDir) {
		try {
			// URL 형태인 경우 파일명만 추출
			if (fileName.startsWith("/")) {
				fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
			}

			File fileToDelete = new File(uploadDir + fileName);
			if (fileToDelete.exists()) {
				boolean deleted = fileToDelete.delete();
				if (deleted) {
					System.out.println("파일 삭제 성공: " + fileName);
				} else {
					System.out.println("파일 삭제 실패: " + fileName);
				}
			} else {
				System.out.println("삭제할 파일이 존재하지 않음: " + fileName);
			}
		} catch (Exception e) {
			System.err.println("파일 삭제 중 오류 발생: " + fileName + ", " + e.getMessage());
		}
	}

	@Override
	public void updateProfileImage(int resumeId, int memberId, String imageUrl) {
		resumeDao.updateProfileImage(resumeId, memberId, imageUrl);
	}

	@Override
	public ResumeVo getResumeByResumeId(int resumeId) {
		return resumeDao.getResumeByResumeId(resumeId);
	}

	@Override
	public ResumeVo getResumeWithAllDetails(int resumeId) {

		try {
			ResumeVo resume = resumeDao.getResumeWithAllDetails(resumeId);

			return resume;
		} catch (Exception e) {

			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public ResumeVo getResumeCopyWithAllDetails(int resumeId) {
		try {
			ResumeVo resume = resumeDao.getResumeCopyWithAllDetails(resumeId);

			return resume;
		} catch (Exception e) {

			e.printStackTrace();
			throw e;
		}
	}

	@Override
	@Transactional
	public void saveResume(ResumeVo resumeVo) {
		try {
			// resumeId가 있으면 수정, 없으면 신규 작성
			if (resumeVo.getResumeId() > 0) {
				// 수정 모드

				// 1. 메인 이력서 정보 업데이트
				resumeDao.updateResume(resumeVo);

				// 2. 기존 하위 데이터 모두 삭제
				deleteResumeDetails(resumeVo.getResumeId());

				// 3. 새로운 하위 데이터 저장
				saveResumeDetails(resumeVo, resumeVo.getResumeId());

			} else {
				// 신규 작성 모드

				// 1. 메인 이력서 정보 저장
				resumeDao.insertResume(resumeVo);
				int resumeId = resumeVo.getResumeId();

				// 2. 하위 데이터 저장
				saveResumeDetails(resumeVo, resumeId);

			}

		} catch (Exception e) {

			e.printStackTrace();
			throw new RuntimeException("이력서 저장/수정 중 오류가 발생했습니다.", e);
		}
	}

	@Override
	@Transactional
	public void updateResume(ResumeVo resumeVo) {
		try {
			int resumeId = resumeVo.getResumeId();

			// 1. 메인 이력서 정보 업데이트
			resumeDao.updateResume(resumeVo);

			// 2. 기존 하위 데이터 모두 삭제
			deleteResumeDetails(resumeId);

			// 3. 새로운 하위 데이터 저장
			saveResumeDetails(resumeVo, resumeId);

		} catch (Exception e) {

			e.printStackTrace();
			throw new RuntimeException("이력서 업데이트 중 오류가 발생했습니다.", e);
		}
	}

	@Override
	@Transactional
	public void saveResumeWithDetails(ResumeVo resumeVo) {
		// saveResume와 동일한 로직 (별칭 메서드)
		saveResume(resumeVo);
	}

	// 이력서 하위 데이터 저장 공통 메서드
	private void saveResumeDetails(ResumeVo resumeVo, int resumeId) {
		// 2. 학력 정보 저장
		if (resumeVo.getSchoolList() != null && !resumeVo.getSchoolList().isEmpty()) {
			for (SchoolVo school : resumeVo.getSchoolList()) {
				if (isValidSchool(school)) {
					school.setResumeId(resumeId);
					resumeDao.insertSchool(school);

				}
			}
		}

		// 3. 경력 정보 저장
		if (resumeVo.getCareerList() != null && !resumeVo.getCareerList().isEmpty()) {
			for (CareerVo career : resumeVo.getCareerList()) {
				if (isValidCareer(career)) {
					career.setResumeId(resumeId);
					resumeDao.insertCareer(career);

				}
			}
		}

		// 4. 교육 정보 저장
		if (resumeVo.getEducationList() != null && !resumeVo.getEducationList().isEmpty()) {
			for (EducationVo education : resumeVo.getEducationList()) {
				if (isValidEducation(education)) {
					education.setResumeId(resumeId);
					resumeDao.insertEducation(education);

				}
			}
		}

		// 5. 자격증 정보 저장
		if (resumeVo.getCertificateList() != null && !resumeVo.getCertificateList().isEmpty()) {
			for (CertificateResumeVo certificate : resumeVo.getCertificateList()) {
				if (isValidCertificate(certificate)) {
					certificate.setResumeId(resumeId);
					resumeDao.insertCertificateResume(certificate);

				}
			}
		}

		// 6. 포트폴리오 정보 저장
		System.out.println(">>> [saveResumeDetails] 포트폴리오 저장 시작, resumeId: " + resumeId);
		System.out.println(">>> [saveResumeDetails] PortfolioList 존재 여부: " + (resumeVo.getPortfolioList() != null));
		if (resumeVo.getPortfolioList() != null) {
			System.out.println(">>> [saveResumeDetails] PortfolioList 크기: " + resumeVo.getPortfolioList().size());
			if (!resumeVo.getPortfolioList().isEmpty()) {
				for (int i = 0; i < resumeVo.getPortfolioList().size(); i++) {
					PortfolioVo portfolio = resumeVo.getPortfolioList().get(i);
					System.out.println(">>> [saveResumeDetails] Portfolio[" + i + "]: fileName=" + portfolio.getFileName() + 
						", storedFileName=" + portfolio.getStoredFileName() + ", isValid=" + isValidPortfolio(portfolio));
					
					if (isValidPortfolio(portfolio)) {
						portfolio.setResumeId(resumeId);
						resumeDao.insertPortfolio(portfolio);
						System.out.println(">>> [saveResumeDetails] Portfolio[" + i + "] DB 저장 완료");
					} else {
						System.out.println(">>> [saveResumeDetails] Portfolio[" + i + "] 유효성 검사 실패, 저장하지 않음");
					}
				}
			} else {
				System.out.println(">>> [saveResumeDetails] PortfolioList가 비어있음");
			}
		} else {
			System.out.println(">>> [saveResumeDetails] PortfolioList가 null임");
		}

		// 7. 태그 정보 저장
		if (resumeVo.getSkillList() != null && !resumeVo.getSkillList().isEmpty()) {
			for (SkillVo skill : resumeVo.getSkillList()) {
				if (skill != null && skill.getTagId() > 0) {
					resumeDao.insertResumeTag(resumeId, (long) skill.getTagId());
				}
			}
		}
	}

	// 이력서 하위 데이터 삭제 공통 메서드
	private void deleteResumeDetails(int resumeId) {
		// 기존 포트폴리오 파일들 삭제
		List<PortfolioVo> existingPortfolios = resumeDao.getPortfoliosByResumeId(resumeId);
		if (existingPortfolios != null) {
			for (PortfolioVo portfolio : existingPortfolios) {
				if (portfolio.getStoredFileName() != null && !portfolio.getStoredFileName().isEmpty()) {
					deleteFileFromSystem(portfolio.getStoredFileName(), "C:/upload/portfolio/");
				}
			}
		}

		resumeDao.deleteSchoolsByResumeId(resumeId);
		resumeDao.deleteCareersByResumeId(resumeId);
		resumeDao.deleteEducationsByResumeId(resumeId);
		resumeDao.deleteCertificatesByResumeId(resumeId);
		resumeDao.deletePortfoliosByResumeId(resumeId);
		resumeDao.deleteTagsByResumeId(resumeId);

		// 추가로 필요한 삭제 로직이 있다면 여기에 추가
	}

	// 유효성 검사 메서드들
	private boolean isValidSchool(SchoolVo school) {
		return school != null && school.getSchoolName() != null && !school.getSchoolName().trim().isEmpty();
	}

	private boolean isValidCareer(CareerVo career) {
		return career != null && career.getCompanyName() != null && !career.getCompanyName().trim().isEmpty();
	}

	private boolean isValidEducation(EducationVo education) {
		return education != null && education.getEduName() != null && !education.getEduName().trim().isEmpty();
	}

	private boolean isValidPortfolio(PortfolioVo portfolio) {
		return portfolio != null && portfolio.getFileName() != null && !portfolio.getFileName().trim().isEmpty();
	}

	private boolean isValidCertificate(CertificateResumeVo certificate) {
		return certificate != null && certificate.getCertificateName() != null
				&& !certificate.getCertificateName().trim().isEmpty();
	}

	@Override
	public List<ResumeVo> getResumeListWithSummaryByMemberId(int memberId) {
		List<ResumeVo> resumeList = resumeDao.findResumesByMemberId(memberId);
		System.out.println(">>> 총 이력서 개수: " + resumeList.size());

		for (ResumeVo resume : resumeList) {
			int resumeId = resume.getResumeId();
			System.out.println(">>> 이력서 ID: " + resumeId);

			// 하위 정보 불러오기 (resumeId 기준)
			List<CareerVo> careers = resumeDao.getCareersByResumeId(resumeId);
			System.out.println(">>> 경력 개수: " + careers.size());
			List<SchoolVo> schools = resumeDao.getSchoolsByResumeId(resumeId);
			System.out.println(">>> 학력 개수: " + schools.size());
			List<SkillVo> skills = resumeDao.getTagIdsByResumeId(resumeId);
			System.out.println(">>> 스킬 ID 개수: " + skills.size());

			// 태그 ID → 태그명 포함된 SkillVo 리스트로 변환

			resume.setCareerList(careers);
			resume.setSchoolList(schools);
			resume.setSkillList(skills);
		}

		return resumeList;
	}

	// ===== 비즈니스 로직 메서드들 =====

	@Override
	public Map<String, Object> prepareResumeWritePageData(Integer resumeId, MemberVo loginUser) {
		Map<String, Object> result = new HashMap<>();

		// 직군/직무 목록 준비
		result.put("jobGroupList", jobGroupService.selectAllJobGroupsForAjax());

		// 모든 직무 목록 추가 (업데이트 시 기존 선택된 직무 표시용)
		List<com.joblessfriend.jobfinder.job.domain.JobVo> allJobs = new ArrayList<>();
		jobGroupService.selectAllJobGroupsForAjax().forEach(group -> {
			allJobs.addAll(jobService.selectJobsByGroupId(group.getJobGroupId()));
		});
		result.put("jobList", allJobs);

		// 수정 모드인 경우 이력서 데이터 조회
		if (resumeId != null && resumeId > 0) {
			try {
				// 이력서 전체 정보 조회
				ResumeVo resumeVo = getResumeWithAllDetails(resumeId);

				if (resumeVo == null) {
					result.put("error", "이력서를 찾을 수 없습니다.");
					return result;
				}

				// 본인 이력서인지 확인
				if (resumeVo.getMemberId() != loginUser.getMemberId()) {
					result.put("error", "본인의 이력서만 수정할 수 있습니다.");
					return result;
				}

				// 스킬 데이터 추가
				try {
					List<SkillVo> skillList = skillService.resumeTagList(resumeId);
					resumeVo.setSkillList(skillList != null ? skillList : new ArrayList<>());
					result.put("skillList", skillList != null ? skillList : new ArrayList<>());
				} catch (Exception e) {
					System.err.println(">>> [ResumeService] 스킬 데이터 조회 실패: " + e.getMessage());
					result.put("skillList", new ArrayList<>());
				}

				result.put("resumeData", resumeVo);
				result.put("isEditMode", true);
				result.put("currentResumeId", resumeId);

			} catch (Exception e) {
				System.err.println(">>> [ResumeService] 이력서 조회 실패: " + e.getMessage());
				e.printStackTrace();
				result.put("error", "이력서 조회 중 오류가 발생했습니다: " + e.getMessage());
			}
		} else {
			// 신규 작성 모드
			result.put("isEditMode", false);
		}

		return result;
	}

	@Override
	public String saveOrUpdateResume(Map<String, Object> requestMap, MemberVo loginUser) {
		try {
			// 이력서 데이터 파싱
			ResumeVo resumeVo = resumeParser.parseMapToResumeVo(requestMap, loginUser.getMemberId());

			// 파싱 결과 확인
			if (resumeVo == null) {
				return "이력서 데이터 파싱에 실패했습니다.";
			}

			// 수정 모드인지 확인 (resumeId가 있고 0보다 큰 경우)
			if (resumeVo.getResumeId() != 0 && resumeVo.getResumeId() > 0) {
				// 수정 모드

				// 기존 이력서 조회하여 권한 확인
				if (!checkResumeOwnership(resumeVo.getResumeId(), loginUser.getMemberId())) {
					return "본인의 이력서만 수정할 수 있습니다.";
				}

				// 이력서 수정
				updateResume(resumeVo);

			} else {
				// 신규 작성 모드
				saveResumeWithDetails(resumeVo);
			}

			return "success";

		} catch (Exception e) {
			System.err.println(">>> [ResumeService] 이력서 저장 실패: " + e.getMessage());
			e.printStackTrace();
			return "이력서 저장 중 오류가 발생했습니다: " + e.getMessage();
		}
	}

	@Override
	public String saveOrUpdateResume(Map<String, Object> requestMap, MemberVo loginUser, HttpSession session) {
		try {
			System.out.println(">>> [ResumeService] 세션 포함 이력서 저장 시작");
			
			// 이력서 데이터 파싱 (세션 포함)
			ResumeVo resumeVo = resumeParser.parseMapToResumeVo(requestMap, loginUser.getMemberId(), session);

			// 파싱 결과 확인
			if (resumeVo == null) {
				return "이력서 데이터 파싱에 실패했습니다.";
			}

			// 수정 모드인지 확인 (resumeId가 있고 0보다 큰 경우)
			if (resumeVo.getResumeId() != 0 && resumeVo.getResumeId() > 0) {
				// 수정 모드

				// 기존 이력서 조회하여 권한 확인
				if (!checkResumeOwnership(resumeVo.getResumeId(), loginUser.getMemberId())) {
					return "본인의 이력서만 수정할 수 있습니다.";
				}

				// 이력서 수정
				updateResume(resumeVo);

			} else {
				// 신규 작성 모드
				saveResumeWithDetails(resumeVo);
			}

			// 저장 성공 후 세션의 포트폴리오 데이터 클리어
			if (session != null) {
				session.removeAttribute("portfolioFiles");
				System.out.println(">>> [ResumeService] 저장 완료 후 세션의 포트폴리오 데이터 클리어됨");
			}

			return "success";

		} catch (Exception e) {
			System.err.println(">>> [ResumeService] 이력서 저장 실패: " + e.getMessage());
			e.printStackTrace();
			return "이력서 저장 중 오류가 발생했습니다: " + e.getMessage();
		}
	}

	@Override
	public boolean checkResumeOwnership(int resumeId, int memberId) {
		try {
			ResumeVo existingResume = getResumeByResumeId(resumeId);
			return existingResume != null && existingResume.getMemberId() == memberId;
		} catch (Exception e) {
			System.err.println(">>> [ResumeService] 이력서 권한 체크 실패: " + e.getMessage());
			return false;
		}
	}

	@Override
	public ResumeVo prepareResumePreviewData(Map<String, Object> requestMap, MemberVo loginUser, HttpSession session) {
		ResumeVo resumeVo = resumeParser.parseMapToResumeVo(requestMap, loginUser.getMemberId());
		session.setAttribute("resumePreviewData", resumeVo);
		return resumeVo;
	}

	@Override
	public List<Map<String, String>> prepareJobTitleData(ResumeVo resume) {
		List<Map<String, String>> jobTitles = new ArrayList<>();

		if (resume.getCareerList() != null) {
			for (CareerVo career : resume.getCareerList()) {
				Map<String, String> map = new HashMap<>();
				map.put("jobGroupName", jobGroupService.getJobGroupNameById(career.getJobGroupId()));
				map.put("jobName", jobService.getJobNameById(career.getJobId()));
				jobTitles.add(map);
			}
		}

		return jobTitles;
	}

	@Override
	public void enrichSkillData(ResumeVo resume) {
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
	}

}
