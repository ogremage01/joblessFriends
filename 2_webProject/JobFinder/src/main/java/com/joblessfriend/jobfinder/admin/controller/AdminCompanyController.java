package com.joblessfriend.jobfinder.admin.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.joblessfriend.jobfinder.company.domain.CompanyVo;
import com.joblessfriend.jobfinder.company.service.CompanyService;

@RequestMapping("/admin/member/company") // "/admin/member/company" 경로 하위 요청 처리
@Controller
public class AdminCompanyController {

	private Logger logger = LoggerFactory.getLogger(AdminAuthController.class); // 로그 출력용

	@Autowired
	private CompanyService companyService; // 기업 서비스 주입

	/**
	 * 기업 회원 목록 페이지로 이동 (페이징 및 키워드 검색 가능)
	 */
	@GetMapping("")
	public String memberCompany(Model model,
			@RequestParam(defaultValue = "0") int page, // 페이지 번호 기본값 0
			@RequestParam(value = "keyword", required = false) String keyword) { // 검색어 (선택)

		logger.info("기업회원 목록으로 이동");

		List<CompanyVo> companyList = new ArrayList<>();
		int companyCount = 0;
		int totalPage = 0;

		// 검색어가 있을 경우 검색 목록 조회
		if (keyword != null && !keyword.trim().isEmpty()) {
			companyList = companyService.companySelectList(page, keyword);
			companyCount = companyService.companyCount(keyword);
			totalPage = companyCount / 10 + (companyCount % 10 == 0 ? 0 : 1);
		} else {
			// 검색어가 없을 경우 전체 목록 조회
			companyList = companyService.companySelectList(page);
			companyCount = companyService.companyCount();
			System.out.println(companyCount); // 디버깅 출력
			totalPage = companyCount / 10 + (companyCount % 10 == 0 ? 0 : 1);
		}

		// 뷰로 데이터 전달
		int curPage = page;
		model.addAttribute("companyList", companyList);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("curPage", curPage);

		return "admin/member/memberCompanyView"; // 기업 회원 목록 뷰
	}

	/**
	 * 기업 회원 상세보기
	 */
	@GetMapping("/{companyId}")
	public String memberCompanyDetail(Model model, @PathVariable int companyId) {
		logger.info("기업회원 세부정보로 이동");

		// 특정 기업 회원 상세 조회
		CompanyVo companyVo = companyService.companySelectOne(companyId);

		model.addAttribute("companyVo", companyVo); // 뷰로 데이터 전달

		return "admin/member/memberCompanyDetailView"; // 상세 뷰 반환
	}

	/**
	 * 기업 회원 정보 수정 (PATCH 요청)
	 */
	@PatchMapping("/{companyId}")
	public ResponseEntity<Integer> memberCompanyDetailUpdate(@PathVariable int companyId,
			@RequestBody CompanyVo companyVo) {

		logger.info("기업회원 정보 수정-어드민");
		System.out.println("입력정보확인: " + companyVo.toString());

		// 기존 데이터 조회
		CompanyVo existCompanyVo = companyService.companySelectOne(companyVo.getCompanyId());

		// 필드별로 null 여부 확인 후 값 갱신
		if (companyVo.getCompanyName() != null) {
			existCompanyVo.setCompanyName(companyVo.getCompanyName());
		}
		if (companyVo.getEmail() != null) {
			existCompanyVo.setEmail(companyVo.getEmail());
		}
		if (companyVo.getPassword() != null) {
			// TODO: 비밀번호 해싱 처리 필요
			existCompanyVo.setPassword(companyVo.getPassword());
		}
		if (companyVo.getBrn() != null) {
			existCompanyVo.setBrn(companyVo.getBrn());
		}
		if (companyVo.getRepresentative() != null) {
			existCompanyVo.setRepresentative(companyVo.getRepresentative());
		}
		if (companyVo.getTel() != null) {
			existCompanyVo.setTel(companyVo.getTel());
		}

		// 주소 관련 정보는 향후 구현 시 활성화
		
		if (companyVo.getPostalCodeId() != 0) {
			existCompanyVo.setPostalCodeId(companyVo.getPostalCodeId());
		}
		if (companyVo.getArenaName() != null) {
			existCompanyVo.setArenaName(companyVo.getArenaName());
		}
		if (companyVo.getAddress() != null) {
			existCompanyVo.setAddress(companyVo.getAddress());
		}
		

		System.out.println("바꾼 후 정보: " + existCompanyVo.toString());

		// 수정 처리
		int result = companyService.companyUpdateOne(existCompanyVo);

		// 성공 여부를 HTTP 응답으로 반환
		return ResponseEntity.ok(result);
	}

	/**
	 * 기업 회원 단건 삭제 (탈퇴 처리)
	 */
	@DeleteMapping("/{companyId}")
	public ResponseEntity<Integer> memberCompanyDeleteOne(@PathVariable int companyId) {
		logger.info("기업회원 탈퇴프로세스 진행-어드민");
		System.out.println(companyId); // 디버깅 출력

		int result = companyService.companyDeleteOne(companyId);

		return ResponseEntity.ok(result); // 삭제 결과 반환
	}

	/**
	 * 기업 회원 복수 삭제 (대량 탈퇴 처리)
	 */
	@DeleteMapping("/")
	public ResponseEntity<Integer> memberCompanyDeleteList(@RequestBody List<Integer> companyIdList) {
		logger.info("기업회원 대량 탈퇴프로세스 진행-어드민");

		for (Integer i : companyIdList) {
			System.out.println("삭제할 기업 아이디: " + i); // 디버깅 출력
		}

		int result = companyService.companyDeleteList(companyIdList);

		return ResponseEntity.ok(result); // 결과 반환
	}
}
