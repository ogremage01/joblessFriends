package com.joblessfriend.jobfinder.job.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.joblessfriend.jobfinder.job.domain.JobVo;
import com.joblessfriend.jobfinder.job.service.JobService;

@RestController
@RequestMapping("/job")
public class JobController {

	@Autowired
	private JobService jobService;

	
	@GetMapping("/list")
	@ResponseBody
	public List<JobVo> selectJobsByGroupId(@RequestParam("jobGroupId") int jobGroupId) {
		List<JobVo> list = jobService.selectJobsByGroupId(jobGroupId);
	    System.out.println("직무 리스트 확인: " + list);
	    return list;
	}
}
