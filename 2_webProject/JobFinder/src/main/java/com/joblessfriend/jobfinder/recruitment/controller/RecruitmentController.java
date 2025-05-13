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
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<JobGroupVo> searchJob(@RequestParam int jobGroupId) {
        System.out.println("Job Group ID: " + jobGroupId);

        List<JobGroupVo> jobList = recruitmentService.jobList(jobGroupId);

        System.out.println("잡리스트" + jobList);
        return jobList;
    }

    @GetMapping("detail")
    public String getDetail(@RequestParam int companyId,@RequestParam int jobPostId, Model model) {

        JobVo jobVo = jobService.getJobById(jobPostId);
        RecruitmentVo recruitmentVo = recruitmentService.getRecruitmentId(jobPostId);
        CompanyVo companyVo = companyService.companySelectOne(companyId);
        if (recruitmentVo.getCompanyId() != companyVo.getCompanyId()) {
            throw new IllegalArgumentException("회사 정보가 일치하지 않습니다.");
        }


        //parameter: id, companyid

        RecruitmentDetailVo recruitmentDetailVo = new RecruitmentDetailVo();

        recruitmentDetailVo.setJob(jobVo);
        recruitmentDetailVo.setCompany(companyVo);
        recruitmentDetailVo.setRecruitment(recruitmentVo);

        System.out.println(recruitmentDetailVo.getRecruitment());


        model.addAttribute("recruitmentDetailVo", recruitmentDetailVo);




        return "recruitment/recruitmentDetail";
    }

}
