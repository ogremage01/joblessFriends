package com.joblessfriend.jobfinder.jobGroup.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joblessfriend.jobfinder.jobGroup.domain.JobGroupVo;
import com.joblessfriend.jobfinder.jobGroup.service.JobGroupService;

@RestController
@RequestMapping("/jobGroup")
public class JobGroupController {
	
	@Autowired
	private JobGroupService jobGroupService;
	
	@GetMapping("/list")
	public List<JobGroupVo> getAllJobGroups() {
		return jobGroupService.selectAllJobGroupsForAjax();
	}

}
