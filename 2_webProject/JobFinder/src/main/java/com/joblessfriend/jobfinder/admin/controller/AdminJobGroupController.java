package com.joblessfriend.jobfinder.admin.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.joblessfriend.jobfinder.company.domain.CompanyVo;
import com.joblessfriend.jobfinder.jobGroup.domain.JobGroupVo;
import com.joblessfriend.jobfinder.jobGroup.service.JobGroupService;


@RequestMapping("/admin/job/jobGroup")
@Controller
public class AdminJobGroupController {

	private Logger logger = LoggerFactory.getLogger(AdminAuthController.class);
	private final String logTitleMsg = "==Admin control==";

	@Autowired
	private JobGroupService jobGroupService;

	@GetMapping("")
	public String jobGroupSelectList(Model model, 
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(value = "keyword", required = false) String keyword) {
		logger.info("직군 목록으로 이동");

		List<JobGroupVo> jobGroupList = new ArrayList<>();
		int jobGroupCount = 0;
		int totalPage = 0;
		if (keyword != null && !keyword.trim().isEmpty()) {
			jobGroupList = jobGroupService.jobGroupSelectList(page, keyword);
			jobGroupCount = jobGroupService.jobGroupCount(keyword);
			totalPage = jobGroupCount / 10 + (jobGroupCount % 10 == 0 ? 0 : 1);

		} else {
			jobGroupList = jobGroupService.jobGroupSelectList(page);
			jobGroupCount = jobGroupService.jobGroupCount();
			System.out.println(jobGroupCount);
			totalPage = jobGroupCount / 10 + (jobGroupCount % 10 == 0 ? 0 : 1);
		}

		int curPage = page;
		model.addAttribute("jobGroupList", jobGroupList);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("curPage", curPage);

		
		return "/admin/job/jobGroupView";
	}

}
