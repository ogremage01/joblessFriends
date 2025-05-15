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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.joblessfriend.jobfinder.member.domain.MemberVo;
import com.joblessfriend.jobfinder.member.service.MemberService;

@RequestMapping("/admin/member/individual")
@Controller
public class AdminMemberController {

	private Logger logger = LoggerFactory.getLogger(AdminMemberController.class);
	
	@Autowired
	private MemberService memberService;
	
	//목록 view
	@GetMapping("")
	public String memberIndividual(Model model, @RequestParam(defaultValue = "0") int page,
			@RequestParam(value = "keyword", required = false) String keyword) {
		logger.info("개인회원 목록으로 이동");

		List<MemberVo> memberList = new ArrayList<>();
		int memberCount = 0;
		int totalPage = 0;
		if (keyword != null && !keyword.trim().isEmpty()) {
			memberList = memberService.memberSelectList(page, keyword);
			logger.info("memberSelectList page:{} keyword:{}", page, keyword);
			memberCount = memberService.memberCount(keyword);
			logger.info("memberService keyword:{}", keyword);
			totalPage = memberCount / 10 + (memberCount % 10 == 0 ? 0 : 1);

		} else {
			memberList = memberService.memberSelectList(page);
			logger.info("memberSelectList page:{}", page);
			memberCount = memberService.memberCount();
			System.out.println(memberCount);
			totalPage = memberCount / 10 + (memberCount % 10 == 0 ? 0 : 1);
		}

		int curPage = page;
		model.addAttribute("memberList", memberList);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("curPage", curPage);

		return "admin/member/memberIndividualView";
	}
	
	//상세
	@GetMapping("/{memberId}")
	public String memberIndividualDetail(Model model, @PathVariable int memberId) {
		logger.info("개인회원 세부정보로 이동");

		// 특정 기업 회원 상세 조회
		MemberVo memberVo = memberService.memberSelectOne(memberId);

		model.addAttribute("memberVo", memberVo); // 뷰로 데이터 전달

		return "admin/member/memberIndividualDetailView"; // 상세 뷰 반환
	}
	
	//수정
	@PatchMapping("/{memberId}")
	public ResponseEntity<Integer> memberIndividualDetailUpdate(@PathVariable int memberId,
			@RequestBody MemberVo memberVo) {

		logger.info("개인회원 정보 수정-어드민");
		System.out.println("입력정보확인: " + memberVo.toString());

		// 기존 데이터 조회
		MemberVo existMemberVo = memberService.memberSelectOne(memberVo.getMemberId());

		// 필드별로 null 여부 확인 후 값 갱신
		if (memberVo.getEmail() != null) {
			existMemberVo.setEmail(memberVo.getEmail());
		}
		if (memberVo.getPassword() != null) {
			// TODO: 비밀번호 해싱 처리 필요
			existMemberVo.setPassword(memberVo.getPassword());
		}
		if (memberVo.getNickname() != null) {
			existMemberVo.setNickname(memberVo.getNickname());
		}
//		if (memberVo.getResumeMax() != 0) {
//			existMemberVo.setResumeMax(memberVo.getResumeMax());
//		}
		if (memberVo.getCreateAt() != null) {
			existMemberVo.setCreateAt(memberVo.getCreateAt());
		}
		if (memberVo.getModifiedAt() != null) {
			existMemberVo.setModifiedAt(memberVo.getModifiedAt());
		}
		if (memberVo.getProvider() != null) {
			existMemberVo.setProvider(memberVo.getProvider());
		}

		System.out.println("바꾼 후 정보: " + existMemberVo.toString());

		// 수정 처리
		int result = memberService.memberUpdateOne(existMemberVo);

		// 성공 여부를 HTTP 응답으로 반환
		return ResponseEntity.ok(result);
	}

	//단일 삭제
	@DeleteMapping("/{memberId}")
	public ResponseEntity<Integer> memberMemberDeleteOne(@PathVariable int memberId) {
		logger.info("개인회원 탈퇴프로세스 진행-어드민");
		System.out.println(memberId); // 디버깅 출력

		int result = memberService.memberDeleteOne(memberId);

		return ResponseEntity.ok(result); // 삭제 결과 반환
	}

	//복수 삭제
	@DeleteMapping("/")
	public ResponseEntity<Integer> memberMemberDeleteList(@RequestBody List<Integer> memberIdList) {
		logger.info("개인회원 복수 탈퇴프로세스 진행-어드민");

		for (Integer i : memberIdList) {
			System.out.println("삭제할 기업 아이디: " + i); // 디버깅 출력
		}

		int result = memberService.memberDeleteList(memberIdList);

		return ResponseEntity.ok(result); // 결과 반환
	}
}
