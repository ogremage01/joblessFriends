package com.joblessfriend.jobfinder.search.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joblessfriend.jobfinder.community.controller.CommunityController;
import com.joblessfriend.jobfinder.community.domain.CommunityVo;
import com.joblessfriend.jobfinder.community.service.CommunityService;
import com.joblessfriend.jobfinder.member.domain.MemberVo;
import com.joblessfriend.jobfinder.recruitment.domain.JobGroupVo;
import com.joblessfriend.jobfinder.recruitment.domain.RecruitmentVo;
import com.joblessfriend.jobfinder.recruitment.service.RecruitmentService;
import com.joblessfriend.jobfinder.resume.domain.ResumeVo;
import com.joblessfriend.jobfinder.resume.service.ResumeService;
import com.joblessfriend.jobfinder.search.service.SearchService;
import com.joblessfriend.jobfinder.skill.domain.SkillVo;
import com.joblessfriend.jobfinder.skill.service.SkillService;
import com.joblessfriend.jobfinder.util.Pagination;
import com.joblessfriend.jobfinder.util.SearchVo;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequestMapping("/search")
@Controller
public class SearchController {

	private final CommunityController communityController;

	private Logger logger = LoggerFactory.getLogger(SearchController.class);

	@Autowired
	private SearchService searchService;
	@Autowired
	private SkillService skillService;
	@Autowired
	private RecruitmentService recruitmentService;
	@Autowired
	private ResumeService resumeService;

	SearchController(CommunityController communityController) {
		this.communityController = communityController;
	}

	/*
	 * // 헤더 검색 기능 (임시) - pcj
	 * 
	 * @PostMapping("") public String searchMainList(Model
	 * model, @RequestParam(defaultValue = "1") int page,
	 * 
	 * @RequestParam(defaultValue = "") String keyword) { SearchVo searchVo = new
	 * SearchVo(); //채용공고 서비스 사용함 추후 검색기능 완성되면 수정 될 예정 searchVo.setKeyword(keyword);
	 * searchVo.setPage(page); searchVo.setRecordSize(4);
	 * 
	 * int totalCount = 0; Pagination pagination = null;
	 * 
	 * if(keyword != null && !keyword.trim().isEmpty()) { totalCount =
	 * searchService.getRecruitmentSearchTotalCount(searchVo); // 총 레코드 수 조회
	 * pagination = new Pagination(totalCount, searchVo);
	 * 
	 * 
	 * // Oracle 11g에 맞게 startRow, endRow 계산
	 * searchVo.setStartRow(pagination.getLimitStart() + 1); // 1부터 시작
	 * searchVo.setEndRow(searchVo.getStartRow() + searchVo.getRecordSize() - 1); }
	 * 
	 * List<RecruitmentVo> recruitmentList =
	 * searchService.getRecruitmentSearchList(searchVo);
	 * 
	 * logger.info("메인서치 recruitmentList: "+ recruitmentList);
	 * model.addAttribute("recruitmentList", recruitmentList);
	 * model.addAttribute("pagination", pagination);
	 * model.addAttribute("totalCount", totalCount); // 검색결과 개수
	 * model.addAttribute("keyword", keyword); // 검색어
	 * 
	 * return "mainSearchView"; }
	 */
	@GetMapping(value = "")
	public String searchMainList(Model model, @RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "") String keyword,
			@RequestParam(value = "jobPostId", required = false) Integer jobPostId, HttpSession session) {

		SearchVo searchVo = new SearchVo();
		
		if(keyword != null && !keyword.isEmpty()) {
			searchVo.setKeyword(keyword);	
		}
		System.out.println(searchVo.getKeyword());
		searchVo.setPage(page); // ★ page 파라미터 사용
		searchVo.setRecordSize(4);
		System.out.println("첫 번째 getRecordSize: " + searchVo.getRecordSize());

		int totalCount = 0;
		Pagination pagination = null;

		// 검색어가 있든 없든 전체 카운트 및 페이징 처리

		try {
			totalCount = searchService.getRecruitmentSearchTotalCount(keyword); // 총 레코드 수 조회
			pagination = new Pagination(totalCount, searchVo);

			System.out.println(pagination.getStartPage());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println("getRecordSize: " + searchVo.getRecordSize());

		// Oracle 11g에 맞게 startRow, endRow 계산
		searchVo.setStartRow(pagination.getLimitStart() + 1); // 1부터 시작
		searchVo.setEndRow(searchVo.getStartRow() + searchVo.getRecordSize() - 1);

		List<RecruitmentVo> recruitmentList = searchService.getRecruitmentSearchList(searchVo);

		System.out.println("시작 setStartRow: " + searchVo.getStartRow());
		System.out.println("끝 setEndRow: " + searchVo.getEndRow());

		// 세션에서 로그인 정보 가져오기
		String userType = (String) session.getAttribute("userType");
		MemberVo memberVo = null;
		if (userType == "member") {
			memberVo = (MemberVo) session.getAttribute("userLogin");

			// 찜 확인 시작
			if (memberVo != null) {
				int memberId = memberVo.getMemberId();
				List<Integer> bookMarkedList = recruitmentService.bookMarkedJobPostIdList(memberId); // 전체 찜 리스트

				Map<Integer, Boolean> bookMarkedMap = new HashMap<>();
				for (RecruitmentVo r : recruitmentList) {
					bookMarkedMap.put(r.getJobPostId(), bookMarkedList.contains(r.getJobPostId()));
				}
				model.addAttribute("bookMarkedMap", bookMarkedMap);
			}

			// 찜 확인 완료
		}

		// 스킬 목록 불러오기
		Map<Integer, List<SkillVo>> skillMap = new HashMap<>();
		for (RecruitmentVo r : recruitmentList) {
			int jobPostId2 = r.getJobPostId();
			List<SkillVo> skillList = skillService.postTagList(jobPostId2);
			skillMap.put(jobPostId2, skillList);

		}

		// ✅ 개인회원(member)인 경우에만 이력서 조회
		if (memberVo != null && "member".equals(userType)) {
			int memberId = memberVo.getMemberId();
			List<ResumeVo> myResumeList;
			System.out.println("리스트" + jobPostId);
			if (jobPostId != null) {
				myResumeList = resumeService.getResumesByMemberId(memberId, jobPostId);
				System.out.println("이력서리스트" + myResumeList);// 적합도 포함
			} else {
				myResumeList = resumeService.getResumesByMemberId(memberId);
				System.out.println("이력서리스트2" + myResumeList);// 기본
			}

			model.addAttribute("resumeList", myResumeList);
		}

		logger.info("메인서치 recruitmentList: " + recruitmentList);
		model.addAttribute("userType", userType);
		model.addAttribute("recruitmentList", recruitmentList);
		model.addAttribute("skillMap", skillMap);
		model.addAttribute("pagination", pagination);
		model.addAttribute("totalCount", totalCount); // 검색결과 개수
		model.addAttribute("keyword", keyword); // 검색어

		return "mainSearchView";
	}

	// 페이지네이션 ajax
	@GetMapping("/json")
	@ResponseBody
	public Map<String, Object> getSearchListJson(@ModelAttribute SearchVo searchVo) {
	    searchVo.setRecordSize(4);

	    // 전체 레코드 수 조회
	    int totalCount = searchService.getRecruitmentSearchTotalCount(searchVo.getKeyword());
	    Pagination pagination = new Pagination(totalCount, searchVo);

	    // Oracle 11g 기준 rownum
	    searchVo.setStartRow(pagination.getLimitStart() + 1);
	    searchVo.setEndRow(searchVo.getStartRow() + searchVo.getRecordSize() - 1);

	    // 공고 리스트 조회
	    List<RecruitmentVo> recruitmentList = searchService.getRecruitmentSearchList(searchVo);

	    // 공고별 스킬 태그 조회
	    Map<Integer, List<SkillVo>> skillMap = new HashMap<>();
	    for (RecruitmentVo r : recruitmentList) {
	        skillMap.put(r.getJobPostId(), skillService.postTagList(r.getJobPostId()));
	    }

	    // 결과 맵 구성
	    Map<String, Object> result = new HashMap<>();
	    result.put("recruitmentList", recruitmentList);
	    result.put("skillMap", skillMap);
	    result.put("pagination", pagination);
	    result.put("totalCount", totalCount);

	    return result;
	}


	/*
	 * @GetMapping("/recruitment") public List<RecruitmentVo>
	 * searchRecruitmentList(Model model, @RequestParam(defaultValue = "1") int
	 * page,
	 * 
	 * @RequestParam(defaultValue = "") String keyword) {
	 * 
	 * searchVo.setKeyword(keyword); searchVo.setPage(1); searchVo.setRecordSize(4);
	 * 
	 * int totalCount = searchService.getRecruitmentSearchTotalCount(searchVo); // 총
	 * 레코드 수 조회 Pagination pagination = new Pagination(totalCount, searchVo);
	 * 
	 * // Oracle 11g에 맞게 startRow, endRow 계산
	 * searchVo.setStartRow(pagination.getLimitStart() + 1); // 1부터 시작
	 * searchVo.setEndRow(searchVo.getStartRow() + searchVo.getRecordSize() - 1);
	 * 
	 * List<RecruitmentVo> recruitmentList =
	 * searchService.recruitmentList(searchVo);
	 * 
	 * model.addAttribute("recruitmentList", recruitmentList);
	 * model.addAttribute("pagination", pagination);
	 * 
	 * return null; }
	 * 
	 * @GetMapping("/community") public List<CommunityVo> searchCommunityList(Model
	 * model, @RequestParam(defaultValue = "1") int page,
	 * 
	 * @RequestParam(defaultValue = "") String keyword) {
	 * searchVo.setKeyword(keyword); searchVo.setPage(1); searchVo.setRecordSize(4);
	 * 
	 * int totalCount = searchService.getCommunityTotalCount(searchVo); // 총 레코드 수
	 * 조회 Pagination pagination = new Pagination(totalCount, searchVo);
	 * 
	 * // Oracle 11g에 맞게 startRow, endRow 계산
	 * searchVo.setStartRow(pagination.getLimitStart() + 1); // 1부터 시작
	 * searchVo.setEndRow(searchVo.getStartRow() + searchVo.getRecordSize() - 1);
	 * 
	 * List<CommunityVo> communityList = searchService.communityList(searchVo);
	 * 
	 * model.addAttribute("communityList", communityList);
	 * model.addAttribute("pagination", pagination);
	 * 
	 * return null; }
	 */
}
