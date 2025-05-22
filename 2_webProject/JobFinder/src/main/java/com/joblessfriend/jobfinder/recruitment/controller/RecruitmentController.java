package com.joblessfriend.jobfinder.recruitment.controller;

//
//import com.joblessfriend.jobfinder.recruitment.domain.JobGroupVo;
//import com.joblessfriend.jobfinder.recruitment.service.RecruitmentService;
import com.joblessfriend.jobfinder.company.domain.CompanyVo;
import com.joblessfriend.jobfinder.company.service.CompanyService;
import com.joblessfriend.jobfinder.job.domain.JobVo;
import com.joblessfriend.jobfinder.job.service.JobService;
import com.joblessfriend.jobfinder.recruitment.domain.*;
import com.joblessfriend.jobfinder.recruitment.service.RecruitmentService;
import com.joblessfriend.jobfinder.skill.domain.SkillVo;
import com.joblessfriend.jobfinder.skill.service.SkillService;
import com.joblessfriend.jobfinder.util.Pagination;
import com.joblessfriend.jobfinder.util.SearchVo;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/Recruitment")
@Controller
public class RecruitmentController {
    
    @Autowired
    private RecruitmentService recruitmentService;

    @Autowired
    private JobService jobService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private SkillService skillService;

    @GetMapping("/list")
    public String getAllList(@ModelAttribute SearchVo searchVo, Model model) {
        searchVo.setRecordSize(4);
        int totalCount = recruitmentService.getRecruitmentTotalCount(searchVo); // 총 레코드 수 조회
        Pagination pagination = new Pagination(totalCount, searchVo);

        // Oracle 11g에 맞게 startRow, endRow 계산
        searchVo.setStartRow(pagination.getLimitStart() + 1); // 1부터 시작
        searchVo.setEndRow(searchVo.getStartRow() + searchVo.getRecordSize() - 1);

        List<JobGroupVo> jobGroupList = recruitmentService.jobGroupList();
        List<RecruitmentVo> recruitmentList = recruitmentService.recruitmentList(searchVo);

        Map<Integer, List<SkillVo>> skillMap = new HashMap<>();
        for (RecruitmentVo r : recruitmentList) {
            int jobPostId = r.getJobPostId();
            List<SkillVo> skillList = skillService.postTagList(jobPostId);
            skillMap.put(jobPostId, skillList);
        }

        model.addAttribute("jobGroupList", jobGroupList);
        model.addAttribute("recruitmentList", recruitmentList);
        model.addAttribute("skillMap", skillMap);
        model.addAttribute("pagination", pagination);

        return "recruitment/recruitmentView";
    }


    //페이지네이션 ajax
    @GetMapping("/list/json")
    @ResponseBody
    public Map<String, Object> getAllListJson(@ModelAttribute SearchVo searchVo) {
        searchVo.setRecordSize(4);
        int totalCount = recruitmentService.getRecruitmentTotalCount(searchVo);
        Pagination pagination = new Pagination(totalCount, searchVo);

        searchVo.setStartRow(pagination.getLimitStart() + 1);
        searchVo.setEndRow(searchVo.getStartRow() + searchVo.getRecordSize() - 1);

        List<RecruitmentVo> recruitmentList = recruitmentService.recruitmentList(searchVo);
        Map<Integer, List<SkillVo>> skillMap = new HashMap<>();
        for (RecruitmentVo r : recruitmentList) {
            skillMap.put(r.getJobPostId(), skillService.postTagList(r.getJobPostId()));
        }

        Map<String, Object> result = new HashMap<>();
        result.put("recruitmentList", recruitmentList);
        result.put("skillMap", skillMap);
        result.put("pagination", pagination);
        return result;
    }

    @GetMapping("/searchJob")
    @ResponseBody
    public Map<String,Object> searchJob(@RequestParam int jobGroupId) {
        Map<String,Object> result = new HashMap<>();
        try {
            List<JobGroupVo> jobList = recruitmentService.jobList(jobGroupId);
            List<SkillVo> skillVos = skillService.tagList(jobGroupId);

            System.out.println("확인용"+skillVos);

            result.put("jobList", jobList);
            result.put("skillList", skillVos);
        } catch (Exception e) {
            e.printStackTrace(); // ⛳ 콘솔에 찍힘
            result.put("error", "서버 에러 발생: " + e.getMessage());
        }
        return result;
    }


    @GetMapping("detail")
    public String getDetail(@RequestParam int companyId,@RequestParam int jobPostId, Model model) {

        JobVo jobVo = jobService.getJobById(jobPostId);
        RecruitmentVo recruitmentVo = recruitmentService.getRecruitmentId(jobPostId);
        CompanyVo companyVo = companyService.companySelectOne(companyId);
        List<SkillVo> skillList = skillService.postTagList(jobPostId);
        List<WelfareVo> welfare = recruitmentService.selectWelfareByJobPostId(jobPostId);
        if (recruitmentVo.getCompanyId() != companyVo.getCompanyId()) {
            throw new IllegalArgumentException("회사 정보가 일치하지 않습니다.");
        }


        //parameter: id, companyid

        RecruitmentDetailVo recruitmentDetailVo = new RecruitmentDetailVo();

        recruitmentDetailVo.setJob(jobVo);
        recruitmentDetailVo.setCompany(companyVo);
        recruitmentDetailVo.setRecruitment(recruitmentVo);
        recruitmentDetailVo.setSkill(skillList);
        recruitmentDetailVo.setWelfare(welfare);
        System.out.println(recruitmentDetailVo.getRecruitment());

        model.addAttribute("recruitmentDetailVo", recruitmentDetailVo);


        return "recruitment/recruitmentDetail";
    }
    @GetMapping("/insert")
    public String recruitmentInsert(Model model, HttpSession session) {
        Object loginMember = session.getAttribute("userLogin");
        Object userType = session.getAttribute("userType");

        if (loginMember == null || !"company".equals(userType)) {
            return "redirect:/auth/login";
        }

        List<JobGroupVo> jobGroupList = recruitmentService.jobGroupList();
        model.addAttribute("jobGroupList", jobGroupList);

        return "recruitment/recruitmentInsert";
    }

    //insert 처리예정 //

    @PostMapping("/insert")
    public String insertRecruitment(@ModelAttribute RecruitmentVo recruitmentVo,
                                    @RequestParam("skills") String skills, @RequestParam("welfareList") String welfareList
                                    ,@RequestParam("tempKey") String tempKey,HttpSession session) {
        System.out.println("📥 컨트롤러 진입");
        String cleanTempKey = tempKey.trim().replaceAll(",", "");

        System.out.println("🔥 정제된 tempKey = " + cleanTempKey);
        // 1. 로그인 체크
        Object loginMember = session.getAttribute("userLogin");
        Object userType = session.getAttribute("userType");

        if (loginMember == null || !"company".equals(userType)) {
            return "redirect:/auth/login";
        }

        // 2. 회사 ID 세팅
        CompanyVo company = (CompanyVo) loginMember;
        recruitmentVo.setCompanyId(company.getCompanyId());
        recruitmentVo.setTempKey(cleanTempKey);

        List<Integer> tagIdList = Arrays.stream(skills.split(","))
                .filter(s -> !s.isBlank())
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        List<WelfareVo> welfareVoList = Arrays.stream(welfareList.split("\\|"))
                .filter(w -> !w.isBlank())
                .map(w -> {
                    WelfareVo vo = new WelfareVo();
                    vo.setBenefitText(w); // jobPostId는 나중에 세팅됨
                    return vo;
                })
                .collect(Collectors.toList());
        try {
            recruitmentService.insertRecruitment(recruitmentVo, tagIdList,welfareVoList);
            System.out.println("🔥 생성된 jobPostId = " + recruitmentVo.getJobPostId());
            System.out.println("🔥 생성된 tempKey = " + cleanTempKey);
            recruitmentService.updateJobPostIdByTempKey(recruitmentVo.getJobPostId(),cleanTempKey);
            System.out.println("insert 성공");
        } catch (Exception e) {
            e.printStackTrace(); // 꼭 전체 출력!
        }




        return "redirect:/Recruitment/list";
    }
    @PostMapping("/uploadImage")
    @ResponseBody
    public Map<String, Object> uploadImage(@RequestParam("image") MultipartFile file,
                                           @RequestParam("tempKey") String tempKey) throws IOException {


        // 5. JSON 응답 (Toast UI Editor에서 기대하는 형식)
        Map<String, Object> result = new HashMap<>();
        try {
            // 1. 저장 경로 설정
            String uploadDir = "C:/upload/job_post/";
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            // 2. 저장 파일 이름 생성
            String originalName = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();
            String storedName = uuid + "_" + originalName;

            // 3. 실제 저장
            File dest = new File(uploadDir + storedName);
            file.transferTo(dest);

            // 4. DB 저장 정보 구성
            JobPostFileVo fileVo = new JobPostFileVo();
            fileVo.setFileName(originalName);
            fileVo.setStoredFileName(storedName);
            fileVo.setFileExtension(originalName.substring(originalName.lastIndexOf('.') + 1));
            fileVo.setFileSize(file.getSize());
            fileVo.setTempKey(tempKey); // 임시 식별 키

            recruitmentService.insertJobPostFile(fileVo);
            result.put("success", 1);
            Map<String, String> fileMap = new HashMap<>();
            fileMap.put("url", "/upload/job_post/" + storedName);
            result.put("file", fileMap);
            System.out.println("url"+fileMap.get("url"));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", 0);
            result.put("message", "서버 오류 발생: " + e.getMessage());
        }
        return result;
    }

    @PostMapping("/filter/count")
    @ResponseBody
    public int filterCount(@RequestBody Map<String, Object> filterParams) {
        // 필터 값 꺼내기 (null safe 처리)
        List<Integer> jobIds = (List<Integer>) filterParams.getOrDefault("jobIds", new ArrayList<>());
        List<String> careers = (List<String>) filterParams.getOrDefault("careers", new ArrayList<>());
        List<String> educations = (List<String>) filterParams.getOrDefault("educations", new ArrayList<>());
        List<Integer> skillTags = (List<Integer>) filterParams.getOrDefault("skillTags", new ArrayList<>());

        FilterRequestVo filterRequestVo = new FilterRequestVo();
        filterRequestVo.setJobIds(jobIds);
        filterRequestVo.setCareers(careers);
        filterRequestVo.setEducations(educations);
        filterRequestVo.setSkillTags(skillTags);

        // 서비스 계층 호출 (필터 조건 기반 count)
        int count = recruitmentService.countFilteredPosts(filterRequestVo);

        return count;
    }

    @GetMapping("/filter/list")
    @ResponseBody
    public Map<String, Object> filterSearchView(FilterRequestVo filterRequestVo) {
        if (filterRequestVo.getPage() == 0) {
            filterRequestVo.setPage(1);
        }
        if (filterRequestVo.getRecordSize() == 0) {
            filterRequestVo.setRecordSize(4); // 기본 4개씩
        }

        int totalCount = recruitmentService.getFilteredRecruitmentTotalCount(filterRequestVo);
        Pagination pagination = new Pagination(totalCount, filterRequestVo);

        filterRequestVo.setStartRow(pagination.getLimitStart() + 1);
        filterRequestVo.setEndRow(filterRequestVo.getStartRow() + filterRequestVo.getRecordSize() - 1);

        List<RecruitmentVo> recruitmentList = recruitmentService.getFilteredRecruitmentList(filterRequestVo);
        Map<Integer, List<SkillVo>> skillMap = new HashMap<>();
        for (RecruitmentVo r : recruitmentList) {
            skillMap.put(r.getJobPostId(), skillService.postTagList(r.getJobPostId()));
        }

        Map<String, Object> result = new HashMap<>();
        result.put("recruitmentList", recruitmentList);
        result.put("skillMap", skillMap);
        result.put("pagination", pagination);
        return result;
    }


}

