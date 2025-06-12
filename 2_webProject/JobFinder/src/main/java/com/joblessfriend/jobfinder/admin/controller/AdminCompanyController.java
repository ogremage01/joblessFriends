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

import com.joblessfriend.jobfinder.admin.service.AdminCompanyService;
import com.joblessfriend.jobfinder.company.domain.CompanyVo;
import com.joblessfriend.jobfinder.company.service.CompanyService;
import com.joblessfriend.jobfinder.util.Pagination;
import com.joblessfriend.jobfinder.util.SearchVo;

@RequestMapping("/admin/member/company") // "/admin/member/company" 경로 하위 요청 처리
@Controller
public class AdminCompanyController {

	private Logger logger = LoggerFactory.getLogger(AdminAuthController.class); // 로그 출력용

	@Autowired
	private AdminCompanyService companyService; // 기업 서비스 주입

	/**
	 * 기업 회원 목록 페이지로 이동 (페이징 및 키워드 검색 가능)
	 */
	@GetMapping("")
	public String memberCompany(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "") String keyword) {

		logger.info("기업회원 목록으로 이동");

		SearchVo searchVo = new SearchVo();
		searchVo.setKeyword(keyword);
		searchVo.setPage(page);
		
		int totalPage = companyService.companyCount(searchVo);
		Pagination pagination = new Pagination(totalPage, searchVo);
		
		// Oracle 11g에 맞게 startRow, endRow 계산
		searchVo.setStartRow(pagination.getLimitStart() + 1); // 1부터 시작
		searchVo.setEndRow(searchVo.getStartRow() + searchVo.getRecordSize() - 1);
		List<CompanyVo> companyList = new ArrayList<>();
		
		companyList = companyService.companySelectList(searchVo);
				// 뷰로 데이터 전달

		model.addAttribute("searchVo", searchVo);
		model.addAttribute("companyList", companyList);
		model.addAttribute("pagination", pagination);

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
		

		int result = companyService.companyDeleteOne(companyId);

		return ResponseEntity.ok(result); // 삭제 결과 반환
	}

	/**
	 * 기업 회원 복수 삭제 (대량 탈퇴 처리)
	 */
	@DeleteMapping("/")
	public ResponseEntity<Integer> memberCompanyDeleteList(@RequestBody List<Integer> companyIdList) {
		logger.info("기업회원 대량 탈퇴프로세스 진행-어드민");

		

		int result = companyService.companyDeleteList(companyIdList);

		return ResponseEntity.ok(result); // 결과 반환
	}
}
