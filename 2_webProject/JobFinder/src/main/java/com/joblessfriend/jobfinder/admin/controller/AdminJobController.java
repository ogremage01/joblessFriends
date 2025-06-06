package com.joblessfriend.jobfinder.admin.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.joblessfriend.jobfinder.admin.service.AdminJobService;
import com.joblessfriend.jobfinder.job.domain.JobVo;
import com.joblessfriend.jobfinder.jobGroup.domain.JobGroupVo;
import com.joblessfriend.jobfinder.util.Pagination;
import com.joblessfriend.jobfinder.util.SearchVo;

@RequestMapping("/admin/job/singleJob")
@Controller
public class AdminJobController {
    
    private Logger logger = LoggerFactory.getLogger(AdminJobController.class);
    
    @Autowired
    private AdminJobService adminJobService;
    
    /**
     * 직무 관리 목록 페이지 (페이징 및 키워드 검색 가능)
     */
    @GetMapping("")
    public String jobView(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "") String keyword) {
        logger.info("직무 관리 목록으로 이동");
        
        SearchVo searchVo = new SearchVo();
        searchVo.setKeyword(keyword);
        searchVo.setPage(page);
        
        int totalCount = adminJobService.getJobCount(searchVo);
        Pagination pagination = new Pagination(totalCount, searchVo);
        
        // Oracle 11g에 맞게 startRow, endRow 계산
        searchVo.setStartRow(pagination.getLimitStart() + 1); // 1부터 시작
        searchVo.setEndRow(searchVo.getStartRow() + searchVo.getRecordSize() - 1);
        
        List<JobVo> jobList = adminJobService.getJobList(searchVo);
        List<JobGroupVo> jobGroupList = adminJobService.getAllJobGroups();
        
        // 뷰로 데이터 전달
        model.addAttribute("searchVo", searchVo);
        model.addAttribute("jobList", jobList);
        model.addAttribute("jobGroupList", jobGroupList);
        model.addAttribute("pagination", pagination);
        
        return "admin/job/jobView";
    }
    
    /**
     * 직무 추가
     */
    @PostMapping("/add")
    public ResponseEntity<String> addJob(@RequestBody JobAddRequest request) {
        logger.info("직무 추가: {}", request.getJobName());
        
        try {
            int result = adminJobService.insertJob(request.getJobName(), request.getJobGroupId());
            
            if (result > 0) {
                return ResponseEntity.ok("추가완료");
            } else {
                return ResponseEntity.badRequest().body("추가실패");
            }
        } catch (Exception e) {
            logger.error("직무 추가 실패: {}", e.getMessage());
            return ResponseEntity.badRequest().body("추가실패");
        }
    }
    
    /**
     * 직무 삭제 (다중 삭제 지원)
     */
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteJobs(@RequestBody List<Integer> jobIdList) {
        logger.info("직무 삭제: {}", jobIdList);
        
        try {
            int result;
            if (jobIdList.size() == 1) {
                result = adminJobService.deleteJob(jobIdList.get(0));
            } else {
                result = adminJobService.deleteJobs(jobIdList);
            }
            
            if (result > 0) {
                return ResponseEntity.ok("삭제완료");
            } else {
                return ResponseEntity.badRequest().body("삭제실패");
            }
        } catch (Exception e) {
            logger.error("직무 삭제 실패: {}", e.getMessage());
            return ResponseEntity.badRequest().body("삭제실패");
        }
    }
    
    // 직무 추가 요청 DTO
    public static class JobAddRequest {
        private String jobName;
        private int jobGroupId;
        
        public String getJobName() {
            return jobName;
        }
        
        public void setJobName(String jobName) {
            this.jobName = jobName;
        }
        
        public int getJobGroupId() {
            return jobGroupId;
        }
        
        public void setJobGroupId(int jobGroupId) {
            this.jobGroupId = jobGroupId;
        }
    }
} 