package com.joblessfriend.jobfinder.admin.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.joblessfriend.jobfinder.company.domain.CompanyVo;
import com.joblessfriend.jobfinder.company.service.CompanyService;


@RequestMapping("/admin/member/company")
@Controller
public class AdminCompanyController {
	
	private Logger logger = LoggerFactory.getLogger(AdminAuthController.class);
	
	@Autowired
	private CompanyService companyService;

	@GetMapping("")
	public String memberCompany(Model model, @RequestParam(defaultValue = "0") int page,
			@RequestParam(value = "keyword", required = false) String keyword) {
		logger.info("기업회원 목록으로 이동");

		List<CompanyVo> companyList = new ArrayList<>();
		int companyCount = 0;
		int totalPage = 0;
		if (keyword != null && !keyword.trim().isEmpty()) {
			companyList = companyService.companySelectList(page, keyword);
			companyCount = companyService.companyCount(keyword);
			totalPage = companyCount / 10 + (companyCount % 10 == 0 ? 0 : 1);

		} else {
			companyList = companyService.companySelectList(page);
			companyCount = companyService.companyCount();
			System.out.println(companyCount);
			totalPage = companyCount / 10 + (companyCount % 10 == 0 ? 0 : 1);
		}

		int curPage = page;
		model.addAttribute("companyList", companyList);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("curPage", curPage);

		return "/admin/member/memberCompanyView";
	}

	@GetMapping("/{companyId}")
	public String memberCompanyDetail(Model model, @PathVariable int companyId) {
		logger.info("기업회원 세부정보로 이동");

		CompanyVo companyVo = companyService.companySelectOne(companyId);

		model.addAttribute("companyVo", companyVo);

		return "/admin/member/memberCompanyDetailView";
	}

	@PutMapping("/{companyId}")
	public ResponseEntity<Integer> memberCompanyDetailUpdate(@PathVariable int companyId,
			@RequestBody CompanyVo companyVo) {
		logger.info("기업회원 정보 수정-어드민");

		System.out.println("입력정보확인" + companyVo.toString());

		CompanyVo existCompanyVo = companyService.companySelectOne(companyVo.getCompanyId());

		// System.out.println("바꾸기 전 정보" + existCompanyVo.toString());

		if (companyVo.getCompanyName() != null) {
			existCompanyVo.setCompanyName(companyVo.getCompanyName());
		}

		if (companyVo.getEmail() != null) {
			existCompanyVo.setEmail(companyVo.getEmail());
		}

		// TODO: 비밀번호 해싱 처리 필요
		if (companyVo.getPassword() != null) {

			existCompanyVo.setPassword(companyVo.getPassword()); // 해싱 추가 필요
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

		// 테이블 수정 후 활성화
//	    if (companyVo.getPostalCodeId() != 0) {
//	        existCompanyVo.setPostalCodeId(companyVo.getPostalCodeId());
//	    }
//
//	    if (companyVo.getArenaName() != null) {
//	        existCompanyVo.setArenaName(companyVo.getArenaName());
//	    }
//
//	    if (companyVo.getAddress() != null) {
//	        existCompanyVo.setAddress(companyVo.getAddress());
//	    }

		System.out.println("바꾼 후 정보" + existCompanyVo.toString());
		int result = companyService.companyUpdateOne(existCompanyVo);

		return ResponseEntity.ok(result);

	}

	@DeleteMapping("/{companyId}")
	public ResponseEntity<Integer> memberCompanyDeleteOne(@PathVariable int companyId) {
		logger.info("기업회원 탈퇴프로세스 진행-어드민");

		System.out.println(companyId);

		int result = companyService.companyDeleteOne(companyId);

		return ResponseEntity.ok(result);
	}

	@DeleteMapping("/")
	public ResponseEntity<Integer> memberCompanyDeleteList(@RequestBody List<Integer> companyIdList) {
		logger.info("기업회원 대량 탈퇴프로세스 진행-어드민");

		for (Integer i : companyIdList) {
			System.out.println("삭제할 기업 아이디:" + i);
		}

		int result = companyService.companyDeleteList(companyIdList);

		return ResponseEntity.ok(result);
	}

}
