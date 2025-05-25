package com.joblessfriend.jobfinder.school.service;

import java.util.List;

import com.joblessfriend.jobfinder.school.domain.SchoolInfo;

public interface SchoolService {
	List<SchoolInfo> searchSchools(String keyword);
	List<SchoolInfo> searchSchools(String keyword, int year, String schulKndCode);
}
