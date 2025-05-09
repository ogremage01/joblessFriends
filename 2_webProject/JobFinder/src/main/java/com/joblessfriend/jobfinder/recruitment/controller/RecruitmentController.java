package com.joblessfriend.jobfinder.recruitment.controller;

//
//import com.joblessfriend.jobfinder.recruitment.domain.JobGroupVo;
//import com.joblessfriend.jobfinder.recruitment.service.RecruitmentService;
import com.joblessfriend.jobfinder.recruitment.domain.JobGroupVo;
import com.joblessfriend.jobfinder.recruitment.service.RecruitmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/Recruitment")
@Controller
public class RecruitmentController {
    
    @Autowired
    private RecruitmentService recruitmentService;


    @GetMapping("/list")
    public String getAlllist(Model model) {
        List<JobGroupVo> jobGroupList = recruitmentService.jobGroupList();

        model.addAttribute("jobGroupList", jobGroupList);
//        //추가 채용공고완성시 추가진행예정//

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

}
