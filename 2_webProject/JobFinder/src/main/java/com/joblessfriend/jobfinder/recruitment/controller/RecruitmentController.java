package com.joblessfriend.jobfinder.recruitment.controller;

//
//import com.joblessfriend.jobfinder.recruitment.domain.JobGroupVo;
//import com.joblessfriend.jobfinder.recruitment.service.RecruitmentService;
import com.joblessfriend.jobfinder.company.domain.CompanyVo;
import com.joblessfriend.jobfinder.company.service.CompanyService;
import com.joblessfriend.jobfinder.job.domain.JobVo;
import com.joblessfriend.jobfinder.job.service.JobService;
import com.joblessfriend.jobfinder.recruitment.dao.RecruitmentDao;
import com.joblessfriend.jobfinder.recruitment.domain.JobGroupVo;
import com.joblessfriend.jobfinder.recruitment.domain.RecruitmentDetailVo;
import com.joblessfriend.jobfinder.recruitment.domain.RecruitmentVo;
import com.joblessfriend.jobfinder.recruitment.service.RecruitmentService;
import com.joblessfriend.jobfinder.skill.domain.SkillVo;
import com.joblessfriend.jobfinder.skill.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        model.addAttribute("jobGroupList", jobGroupList);
        //        //추가적인페이지네이션 5개단위  //
        model.addAttribute("recruitmentList", recruitmentList);



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
        List<SkillVo> skillList = skillService.tagList(jobPostId);
        if (recruitmentVo.getCompanyId() != companyVo.getCompanyId()) {
            throw new IllegalArgumentException("회사 정보가 일치하지 않습니다.");
        }


        //parameter: id, companyid

        RecruitmentDetailVo recruitmentDetailVo = new RecruitmentDetailVo();

        recruitmentDetailVo.setJob(jobVo);
        recruitmentDetailVo.setCompany(companyVo);
        recruitmentDetailVo.setRecruitment(recruitmentVo);
        recruitmentDetailVo.setSkill(skillList);
        System.out.println(recruitmentDetailVo.getRecruitment());

        model.addAttribute("recruitmentDetailVo", recruitmentDetailVo);


        return "recruitment/recruitmentDetail";
    }
    @GetMapping("/insert")
    public String recruitmentInsert(Model model) {
//        HttpSession session;
//        Object loginMember = session.getAttribute("userLogin");
//
//        if (loginMember == null) {
//            // 로그인 안 된 경우 → 로그인 페이지로 리다이렉트
//            return "redirect:/member/login";
//        } 세션연결예정
        List<JobGroupVo> jobGroupList = recruitmentService.jobGroupList();
        model.addAttribute("jobGroupList", jobGroupList);
        return "recruitment/recruitmentInsert";
    }
    //insert 처리예정 //
    @PostMapping("/insert")
    public RecruitmentVo insertRecruitment(@ModelAttribute RecruitmentVo recruitmentVo) {
        //        HttpSession session;
//        Object loginMember = session.getAttribute("userLogin");
//
//        if (loginMember == null) {
//            // 로그인 안 된 경우 → 로그인 페이지로 리다이렉트
//            return "redirect:/member/login";
//        } 세션연결예정

        return recruitmentVo;
    }
}
