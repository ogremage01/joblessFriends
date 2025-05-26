package com.joblessfriend.jobfinder.school.controller;

import com.joblessfriend.jobfinder.school.domain.MajorInfo;
import com.joblessfriend.jobfinder.school.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/major")
public class MajorController {

    private final MajorService majorService;

    @Autowired
    public MajorController(MajorService majorService) {
        this.majorService = majorService;
    }

    @GetMapping("/search")
    public List<MajorInfo> searchMajors(@RequestParam("keyword") String keyword) {
        return majorService.getMajorsByKeyword(keyword);
    }
}
