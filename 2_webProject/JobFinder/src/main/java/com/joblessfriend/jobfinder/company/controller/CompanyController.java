package com.joblessfriend.jobfinder.company.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties.Job;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joblessfriend.jobfinder.company.domain.CompanyVo;
import com.joblessfriend.jobfinder.company.service.CompanyService;
import com.joblessfriend.jobfinder.recruitment.domain.RecruitmentVo;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/company")
@Controller
public class CompanyController {
	
	private Logger logger = LoggerFactory.getLogger(CompanyController.class); // 로그 출력용

	
	@Autowired
	CompanyService companyService;
	
	@GetMapping("")
	public String companyMain(HttpSession session) {
		
		return "company/companyMainView";
		
	}
	
	@GetMapping("/info")
	public String companyInfo(Model model, HttpSession session) {
		
		CompanyVo loginCompanyVo = (CompanyVo) session.getAttribute("userLogin");
        Object userType = session.getAttribute("userType");

        if (loginCompanyVo == null || !"company".equals(userType)) {
            return "redirect:/auth/login";
        }
        
		CompanyVo companyVo = companyService.companySelectOne(loginCompanyVo.getCompanyId());
		
		model.addAttribute("companyVo",companyVo);
		
		return "company/companyInfoView";
		
	}
	
	
	@PatchMapping("/info")
	public ResponseEntity<Integer> companyUpdate(@RequestBody CompanyVo companyVo, HttpSession session){
		
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
		// 세션 회원정보 다시 담기
		session.setAttribute("userLogin", existCompanyVo);
		
		// 성공 여부를 HTTP 응답으로 반환
		return ResponseEntity.ok(result);
				
	}
	
	@DeleteMapping("/info/{companyId}")
	public ResponseEntity<Integer> companyDelete(@PathVariable int companyId, HttpSession session){
		logger.info("기업회원 탈퇴프로세스 진행-기업회원");
		

		int result = companyService.companyDeleteOne(companyId);

		if(result == 1) {
			session.invalidate();
		}
		
		
		return ResponseEntity.ok(result); // 삭제 결과 반환
	}

	@GetMapping("/delete")
	public String deleteAccountResult() {
		
		
		return "common/deleteResult";
	}
	

}
