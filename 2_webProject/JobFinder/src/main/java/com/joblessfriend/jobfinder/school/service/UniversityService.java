package com.joblessfriend.jobfinder.school.service;

import java.util.List;

import com.joblessfriend.jobfinder.school.domain.SchoolInfo;

public interface UniversityService {
	
	List<SchoolInfo> getUniversityList(String keyword, String schoolType);

}
