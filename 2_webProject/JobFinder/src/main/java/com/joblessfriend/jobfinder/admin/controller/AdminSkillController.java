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

import com.joblessfriend.jobfinder.admin.service.AdminSkillService;
import com.joblessfriend.jobfinder.skill.domain.SkillVo;
import com.joblessfriend.jobfinder.jobGroup.domain.JobGroupVo;
import com.joblessfriend.jobfinder.util.Pagination;
import com.joblessfriend.jobfinder.util.SearchVo;

@RequestMapping("/admin/skill")
@Controller
public class AdminSkillController {
    
    private Logger logger = LoggerFactory.getLogger(AdminSkillController.class);
    
    @Autowired
    private AdminSkillService adminSkillService;
    
    /**
     * 스킬 관리 목록 페이지 (페이징 및 키워드 검색 가능)
     */
    @GetMapping("")
    public String skillView(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "") String keyword) {
        logger.info("스킬 관리 목록으로 이동");
        
        SearchVo searchVo = new SearchVo();
        searchVo.setKeyword(keyword);
        searchVo.setPage(page);
        
        int totalCount = adminSkillService.getSkillCount(searchVo);
        Pagination pagination = new Pagination(totalCount, searchVo);
        
        // Oracle 11g에 맞게 startRow, endRow 계산
        searchVo.setStartRow(pagination.getLimitStart() + 1); // 1부터 시작
        searchVo.setEndRow(searchVo.getStartRow() + searchVo.getRecordSize() - 1);
        
        List<SkillVo> skillList = adminSkillService.getSkillList(searchVo);
        List<JobGroupVo> jobGroupList = adminSkillService.getAllJobGroups();
        
        // 뷰로 데이터 전달
        model.addAttribute("searchVo", searchVo);
        model.addAttribute("skillList", skillList);
        model.addAttribute("jobGroupList", jobGroupList);
        model.addAttribute("pagination", pagination);
        
        return "admin/skillView";
    }
    
    /**
     * 스킬 추가
     */
    @PostMapping("/add")
    public ResponseEntity<String> addSkill(@RequestBody SkillAddRequest request) {
        logger.info("스킬 추가: {}", request.getTagName());
        
        try {
            int result = adminSkillService.insertSkill(request.getTagName(), request.getJobGroupId());
            
            if (result > 0) {
                return ResponseEntity.ok("추가완료");
            } else {
                return ResponseEntity.badRequest().body("추가실패");
            }
        } catch (Exception e) {
            logger.error("스킬 추가 실패: {}", e.getMessage());
            return ResponseEntity.badRequest().body("추가실패");
        }
    }
    
    /**
     * 스킬 삭제 (단일/다중 삭제 지원)
     */
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteSkills(@RequestBody List<Integer> tagIdList) {
        logger.info("스킬 삭제: {}", tagIdList);
        
        try {
            int result = adminSkillService.deleteSkills(tagIdList);
            
            if (result > 0) {
                return ResponseEntity.ok("삭제완료");
            } else {
                return ResponseEntity.badRequest().body("삭제실패");
            }
        } catch (Exception e) {
            logger.error("스킬 삭제 실패: {}", e.getMessage());
            return ResponseEntity.badRequest().body("삭제실패");
        }
    }
    
    // 스킬 추가 요청 DTO
    public static class SkillAddRequest {
        private String tagName;
        private int jobGroupId;
        
        public String getTagName() {
            return tagName;
        }
        
        public void setTagName(String tagName) {
            this.tagName = tagName;
        }
        
        public int getJobGroupId() {
            return jobGroupId;
        }
        
        public void setJobGroupId(int jobGroupId) {
            this.jobGroupId = jobGroupId;
        }
    }
} 