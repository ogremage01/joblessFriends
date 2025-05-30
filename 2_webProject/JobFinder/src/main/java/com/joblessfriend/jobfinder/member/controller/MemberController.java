package com.joblessfriend.jobfinder.member.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.joblessfriend.jobfinder.auth.controller.AuthController;
import com.joblessfriend.jobfinder.member.domain.MemberVo;
import com.joblessfriend.jobfinder.member.service.MemberBookmarkService;
import com.joblessfriend.jobfinder.member.service.MemberRecruitmentService;
import com.joblessfriend.jobfinder.member.service.MemberService;
import com.joblessfriend.jobfinder.recruitment.domain.RecruitmentVo;
import com.joblessfriend.jobfinder.recruitment.service.RecruitmentService;
import com.joblessfriend.jobfinder.skill.domain.SkillVo;
import com.joblessfriend.jobfinder.skill.service.SkillService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	private Logger logger = LoggerFactory.getLogger(AuthController.class);
	private final String logTitleMsg = "==Auth control==";
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MemberRecruitmentService recruitmentService;
	@Autowired
	private SkillService skillService;
	@Autowired
	private MemberBookmarkService bookmarkService;

	@GetMapping("/mypage")
	public String myPage() {
		return "member/myPageView";
	}
	
	@GetMapping("/info")
	public String info() {
		return "member/myPageInfoView";
	}
	
	// 비밀번호 변경
	@PostMapping("/passwordCheck")
	public ResponseEntity<Integer> passwordCheck(@RequestParam String password, @RequestParam int memberId, HttpSession session) {
		int result = memberService.updatePassword(password, memberId);
		if(result == 1) {
			logger.info("비밀번호 변경 성공");
			MemberVo memberVo = (MemberVo)session.getAttribute("userLogin");
			memberVo.setPassword(password);
			session.setAttribute("userLogin", memberVo);
		}else {
			logger.info("비밀번호 변경 실패");
		}
		return ResponseEntity.ok(result);
	}
	
    // 탈퇴
    @DeleteMapping("/delete/{memberId}")
    public ResponseEntity<Integer> memberDeleteOne(@PathVariable int memberId) {
        logger.info("개인회원 탈퇴프로세스 진행-개인회원");
        System.out.println(memberId); // 디버깅 출력
        // 나중에 댓글, 게시글, 이력서 등등 있는 경우도 봐야함 (fk 때문에 삭제안됨)

        int result = memberService.memberDeleteOne(memberId);

        return ResponseEntity.ok(result); // 삭제 결과 반환
    }
	
	@GetMapping("/bookmark")
	public String bookmark(Model model, HttpSession session) {
		
		MemberVo memberVo = (MemberVo) session.getAttribute("userLogin");
		int memberId = memberVo.getMemberId();
		
        List<RecruitmentVo> recruitmentList = recruitmentService.selectRecruitmentList(memberId);
        System.out.println(recruitmentList);

        Map<Integer, List<SkillVo>> skillMap = new HashMap<>();

        for (RecruitmentVo r : recruitmentList) {
            int jobPostId = r.getJobPostId();
            List<SkillVo> skillList = skillService.postTagList(jobPostId);//태그리스트들 put
            skillMap.put(jobPostId, skillList);
        }

       
        //        //추가적인페이지네이션 5개단위  //
        model.addAttribute("recruitmentList", recruitmentList);
        model.addAttribute("skillMap", skillMap);
		
		return "member/bookmark/bookmarkView";
	}
	
	@DeleteMapping("/bookmark")
	public ResponseEntity<String> bookmarkDelete(HttpSession session, @RequestBody int jobPostId){
		
		MemberVo memberVo = (MemberVo) session.getAttribute("userLogin");
		
		int memberId = memberVo.getMemberId();
		
		bookmarkService.deleteOne(memberId, jobPostId);
		
		return ResponseEntity.ok("찜삭제");
	}
}
