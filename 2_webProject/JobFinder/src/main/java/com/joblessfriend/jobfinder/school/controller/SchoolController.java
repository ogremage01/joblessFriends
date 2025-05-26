package com.joblessfriend.jobfinder.school.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.joblessfriend.jobfinder.school.domain.SchoolInfo;
import com.joblessfriend.jobfinder.school.service.SchoolService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/school")
@RequiredArgsConstructor
public class SchoolController {
	
	private final SchoolService schoolService;
	
	//학교검색 api
	@GetMapping("/search")
	public List<SchoolInfo> searchSchool(@RequestParam String keyword) {
		
		int fixedYear = 2024;
		String fixedSchulKndCode = "04";
		
		log.debug("School search keyword: {}", keyword);
		return schoolService.searchSchools(keyword, fixedYear, fixedSchulKndCode);
	}

}
