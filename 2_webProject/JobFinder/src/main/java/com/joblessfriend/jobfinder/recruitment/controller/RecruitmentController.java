package com.joblessfriend.jobfinder.recruitment.controller;

//
//import com.joblessfriend.jobfinder.recruitment.domain.JobGroupVo;
//import com.joblessfriend.jobfinder.recruitment.service.RecruitmentService;
import com.joblessfriend.jobfinder.company.domain.CompanyVo;
import com.joblessfriend.jobfinder.company.service.CompanyService;
import com.joblessfriend.jobfinder.job.domain.JobVo;
import com.joblessfriend.jobfinder.job.service.JobService;
import com.joblessfriend.jobfinder.recruitment.dao.RecruitmentDao;
import com.joblessfriend.jobfinder.recruitment.domain.*;
import com.joblessfriend.jobfinder.recruitment.service.RecruitmentService;
import com.joblessfriend.jobfinder.skill.domain.SkillVo;
import com.joblessfriend.jobfinder.skill.service.SkillService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/Recruitment")
@Controller
public class RecruitmentController {
    
    @Autowired
    private RecruitmentService recruitmentService;

    @Autowired
    private JobService jobService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private SkillService skillService;

    @GetMapping("/list")
    public String getAllList(Model model) {
        List<JobGroupVo> jobGroupList = recruitmentService.jobGroupList();
        List<RecruitmentVo> recruitmentList = recruitmentService.recruitmentList();
        System.out.println(recruitmentList);

        Map<Integer, List<SkillVo>> skillMap = new HashMap<>();

        for (RecruitmentVo r : recruitmentList) {
            int jobPostId = r.getJobPostId();
            List<SkillVo> skillList = skillService.postTagList(jobPostId);//태그리스트들 put
            skillMap.put(jobPostId, skillList);
        }

        model.addAttribute("jobGroupList", jobGroupList);
        //        //추가적인페이지네이션 5개단위  //
        model.addAttribute("recruitmentList", recruitmentList);
        model.addAttribute("skillMap", skillMap);


        return "recruitment/recruitmentView";
    }

    @GetMapping("/searchJob")
    @ResponseBody
    public Map<String,Object> searchJob(@RequestParam int jobGroupId) {
        Map<String,Object> result = new HashMap<>();
        try {
            List<JobGroupVo> jobList = recruitmentService.jobList(jobGroupId);
            List<SkillVo> skillVos = skillService.tagList(jobGroupId);

            System.out.println("확인용"+skillVos);

            result.put("jobList", jobList);
            result.put("skillList", skillVos);
        } catch (Exception e) {
            e.printStackTrace(); // ⛳ 콘솔에 찍힘
            result.put("error", "서버 에러 발생: " + e.getMessage());
        }
        return result;
    }


    @GetMapping("detail")
    public String getDetail(@RequestParam int companyId,@RequestParam int jobPostId, Model model) {

        JobVo jobVo = jobService.getJobById(jobPostId);
        RecruitmentVo recruitmentVo = recruitmentService.getRecruitmentId(jobPostId);
        CompanyVo companyVo = companyService.companySelectOne(companyId);
        List<SkillVo> skillList = skillService.postTagList(jobPostId);
        List<WelfareVo> welfare = recruitmentService.selectWelfareByJobPostId(jobPostId);
        if (recruitmentVo.getCompanyId() != companyVo.getCompanyId()) {
            throw new IllegalArgumentException("회사 정보가 일치하지 않습니다.");
        }


        //parameter: id, companyid

        RecruitmentDetailVo recruitmentDetailVo = new RecruitmentDetailVo();

        recruitmentDetailVo.setJob(jobVo);
        recruitmentDetailVo.setCompany(companyVo);
        recruitmentDetailVo.setRecruitment(recruitmentVo);
        recruitmentDetailVo.setSkill(skillList);
        recruitmentDetailVo.setWelfare(welfare);
        System.out.println(recruitmentDetailVo.getRecruitment());

        model.addAttribute("recruitmentDetailVo", recruitmentDetailVo);


        return "recruitment/recruitmentDetail";
    }
    @GetMapping("/insert")
    public String recruitmentInsert(Model model, HttpSession session) {
        Object loginMember = session.getAttribute("userLogin");
        Object userType = session.getAttribute("userType");

        if (loginMember == null || !"company".equals(userType)) {
            return "redirect:/auth/login";
        }

        List<JobGroupVo> jobGroupList = recruitmentService.jobGroupList();
        model.addAttribute("jobGroupList", jobGroupList);

        return "recruitment/recruitmentInsert";
    }

    //insert 처리예정 //

    @PostMapping("/insert")
    public String insertRecruitment(@ModelAttribute RecruitmentVo recruitmentVo,
                                    @RequestParam("skills") String skills, @RequestParam("welfareList") String welfareList,
                                    HttpSession session) {
        System.out.println("📥 컨트롤러 진입");
        // 1. 로그인 체크
        Object loginMember = session.getAttribute("userLogin");
        Object userType = session.getAttribute("userType");

        if (loginMember == null || !"company".equals(userType)) {
            return "redirect:/auth/login";
        }

        // 2. 회사 ID 세팅
        CompanyVo company = (CompanyVo) loginMember;
        recruitmentVo.setCompanyId(company.getCompanyId());


        List<Integer> tagIdList = Arrays.stream(skills.split(","))
                .filter(s -> !s.isBlank())
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        List<WelfareVo> welfareVoList = Arrays.stream(welfareList.split("\\|"))
                .filter(w -> !w.isBlank())
                .map(w -> {
                    WelfareVo vo = new WelfareVo();
                    vo.setBenefitText(w); // jobPostId는 나중에 세팅됨
                    return vo;
                })
                .collect(Collectors.toList());
        try {
            recruitmentService.insertRecruitment(recruitmentVo, tagIdList,welfareVoList);
            System.out.println("✅ insert 성공");
        } catch (Exception e) {
            e.printStackTrace(); // 꼭 전체 출력!
        }




        return "redirect:/Recruitment/list";
    }

    @PostMapping("/filter/count")
    @ResponseBody
    public int filterCount(@RequestBody Map<String, Object> filterParams) {
        // 필터 값 꺼내기 (null safe 처리)
        List<Integer> jobIds = (List<Integer>) filterParams.getOrDefault("jobIds", new ArrayList<>());
        List<String> careers = (List<String>) filterParams.getOrDefault("careers", new ArrayList<>());
        List<String> educations = (List<String>) filterParams.getOrDefault("educations", new ArrayList<>());
        List<Integer> skillTags = (List<Integer>) filterParams.getOrDefault("skillTags", new ArrayList<>());

        FilterRequestVo filterRequestVo = new FilterRequestVo();
        filterRequestVo.setJobIds(jobIds);
        filterRequestVo.setCareers(careers);
        filterRequestVo.setEducations(educations);
        filterRequestVo.setSkillTags(skillTags);

        // 서비스 계층 호출 (필터 조건 기반 count)
        int count = recruitmentService.countFilteredPosts(filterRequestVo);

        return count;
    }

    @GetMapping("/filter/list")
    @ResponseBody
    public Map<String, Object>  filterSearchView(FilterRequestVo filterRequestVo){
        List<JobGroupVo> jobGroupList = recruitmentService.jobGroupList();
        List<RecruitmentVo> recruitmentList  = recruitmentService.getFilteredRecruitmentList(filterRequestVo);


        Map<Integer, List<SkillVo>> skillMap = new HashMap<>();

        for (RecruitmentVo r : recruitmentList ) {
            int jobPostId = r.getJobPostId();
            List<SkillVo> skillList = skillService.postTagList(jobPostId);//태그리스트들 put
            skillMap.put(jobPostId, skillList);
        }
        Map<String,Object> result = new HashMap<>();
        result.put("jobGroupList", jobGroupList);
        result.put("recruitmentList", recruitmentList );
        result.put("skillMap", skillMap);

        return result;
    }
}

