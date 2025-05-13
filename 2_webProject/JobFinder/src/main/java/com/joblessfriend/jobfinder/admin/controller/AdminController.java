package com.joblessfriend.jobfinder.admin.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.joblessfriend.jobfinder.admin.domain.AdminVo;
import com.joblessfriend.jobfinder.admin.service.AdminService;
import com.joblessfriend.jobfinder.community.controller.CommunityController;
import com.joblessfriend.jobfinder.company.domain.CompanyVo;
import com.joblessfriend.jobfinder.company.service.CompanyService;
import com.joblessfriend.jobfinder.company.service.CompanyServiceImpl;
import com.joblessfriend.jobfinder.jobGroup.service.JobGroupService;
import com.joblessfriend.jobfinder.recruitment.domain.JobGroupVo;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/admin")
@Controller
public class AdminController {



	private Logger logger = LoggerFactory.getLogger(AdminController.class);
	private final String logTitleMsg = "==Admin control==";

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private CompanyService companyService;
	
//	@Autowired
//	private JobGroupService jobGroupService;



	
	@GetMapping({"/",""})
	public String base(Model model, HttpSession session) {
		if(session.getAttribute("admin") != null) {
			return "redirect:/admin/main";
		}else {
			return "redirect:/admin/login";
		}
	}
	

	@GetMapping("/login")
	public String login(Model model) {
		logger.info("어드민login페이지로 이동");

		return "/admin/auth/adminLoginFormView";
	}

	@PostMapping("/login")
	public String getlogin(String adminId, String password, HttpSession session, Model model) {
		logger.info(logTitleMsg);
		logger.info("login!" + adminId + ", " + password);

		AdminVo adminVo = adminService.adminExist(adminId, password);

		if (adminVo != null) {
			session.setAttribute("admin", adminVo);

			return "redirect:/admin/main";
		} else {
			return "/admin/auth/adminLoginFallView";
		}

	}

	@GetMapping("/logout")
	public String logout(HttpSession session, Model model) {
		logger.info("admin Logout");

		session.invalidate();

		return "redirect:/admin/login";
	}

	@GetMapping("/main")
	public String main(Model model) {
		logger.info("go to admin main");

		return "/admin/adminMainView";
	}

	@GetMapping("/member/individual")
	public String member(Model model) {
		logger.info("개인회원 목록으로 이동");

		return "/admin/member/memberIndividualView";
	}

	@GetMapping("/member/company")
	public String memberCompany(Model model, @RequestParam(defaultValue = "0") int page) {
		logger.info("기업회원 목록으로 이동");
		
		List<CompanyVo> companyList = companyService.companySelectList(page);
		int companyCount = companyService.companyCount();
		System.out.println(companyCount);
		int totalPage = companyCount/10 + (companyCount%10==0?0:1);
		System.out.println(totalPage);
		int curPage = page;
		model.addAttribute("companyList", companyList);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("curPage",curPage);


		return "/admin/member/memberCompanyView";
	}
	
	
	@GetMapping("/member/company/{companyId}")
	public String memberCompanyDetail(Model model, @PathVariable int companyId) {
		logger.info("기업회원 세부정보로 이동");
		
		CompanyVo companyVo = companyService.companySelectOne(companyId);
		
		model.addAttribute("companyVo", companyVo);
		
		return "/admin/member/memberCompanyDetailView";
	}
	
	
	@PutMapping("/member/company/{companyId}")
	public ResponseEntity<Integer> memberCompanyDetailUpdate(@PathVariable int companyId, @RequestBody CompanyVo companyVo) {
	    logger.info("기업회원 정보 수정-어드민");
	    
	    System.out.println("입력정보확인"+companyVo.toString());

	    CompanyVo existCompanyVo = companyService.companySelectOne(companyVo.getCompanyId());

	    //System.out.println("바꾸기 전 정보" + existCompanyVo.toString());
	    
	    
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
	
	
	@DeleteMapping("/member/company/{companyId}")
	public ResponseEntity<Integer> memberCompanyDeleteOne(@PathVariable int companyId){
		logger.info("기업회원 탈퇴프로세스 진행-어드민");
		
		
		System.out.println(companyId);
		
		int result = companyService.companyDeleteOne(companyId);
		
		
		return ResponseEntity.ok(result);
	}
	
	@PostMapping("/member/company/massDelete")
	public ResponseEntity<Integer> memberCompanyDeleteList(@RequestBody List<Integer> companyIdList){
		logger.info("기업회원 대량 탈퇴프로세스 진행-어드민");
		
		
		for (Integer i : companyIdList) {
			System.out.println("삭제할 기업 아이디:" + i);
		}
		
		int result = companyService.companyDeleteList(companyIdList);
		
		
		return ResponseEntity.ok(result);
	}
	
	
//	@GetMapping("/job/jobGroup")
//	public String jobGroup(Model model, @RequestParam(defaultValue = "0") int page) {
//		logger.info("직군");
//		
//		List<JobGroupVo> jobGroupList = jobGroupService.jobGroupSelectList(page);
////		int companyCount = jobGroupService.Count();
////		System.out.println(companyCount);
////		int totalPage = companyCount/10 + (companyCount%10==0?0:1);
////		System.out.println(totalPage);
////		int curPage = page;
//		model.addAttribute("jobGroupList", jobGroupList);
////		model.addAttribute("totalPage", totalPage);
////		model.addAttribute("curPage",curPage);
////		
//
//		return "/admin/job/jobGroupView";
//	}
	
	

}
