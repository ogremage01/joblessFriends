package com.joblessfriend.jobfinder.job.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.joblessfriend.jobfinder.job.domain.JobVo;
import com.joblessfriend.jobfinder.job.service.JobService;

@RestController
@RequestMapping("/job")
public class JobController {

	@Autowired
	private JobService jobService;

	
	  @GetMapping("/list") 
	  public List<JobVo> selectJobsByGroupId(@RequestParam Integer jobGroupId) { 
		  return jobService.selectJobsByGroupId(jobGroupId); 
	  }
}
