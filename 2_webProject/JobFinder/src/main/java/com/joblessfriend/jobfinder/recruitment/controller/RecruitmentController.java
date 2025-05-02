package com.joblessfriend.jobfinder.recruitment.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/Recruitment")
@Controller
public class RecruitmentController {


    @GetMapping("/list")
    public String list() {
        return "recruitment/recruitmentView";
    }
}
