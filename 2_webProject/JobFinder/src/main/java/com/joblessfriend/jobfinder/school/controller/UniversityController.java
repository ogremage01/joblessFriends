package com.joblessfriend.jobfinder.school.controller;

import com.joblessfriend.jobfinder.school.domain.SchoolInfo;
import com.joblessfriend.jobfinder.school.service.UniversityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/university")
@RequiredArgsConstructor
public class UniversityController {

    private final UniversityService universityService;

    @GetMapping("/search")
    public List<SchoolInfo> searchUniversity(
            @RequestParam("keyword") String keyword,
            @RequestParam("schoolType") String schoolType
    ) {
        return universityService.getUniversityList(keyword, schoolType);
    }
}
