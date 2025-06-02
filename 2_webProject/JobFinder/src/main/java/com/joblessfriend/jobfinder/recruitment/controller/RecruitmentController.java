package com.joblessfriend.jobfinder.recruitment.controller;

//
//import com.joblessfriend.jobfinder.recruitment.domain.JobGroupVo;
//import com.joblessfriend.jobfinder.recruitment.service.RecruitmentService;
import com.joblessfriend.jobfinder.company.domain.CompanyVo;
import com.joblessfriend.jobfinder.company.service.CompanyService;
import com.joblessfriend.jobfinder.job.domain.JobVo;
import com.joblessfriend.jobfinder.job.service.JobService;
import com.joblessfriend.jobfinder.member.domain.MemberVo;
import com.joblessfriend.jobfinder.recruitment.domain.*;
import com.joblessfriend.jobfinder.recruitment.service.RecruitmentService;
import com.joblessfriend.jobfinder.resume.domain.ResumeVo;
import com.joblessfriend.jobfinder.resume.service.ResumeService;
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
import java.util.stream.Stream;

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
    @Autowired
    private ResumeService resumeService;

    @GetMapping("/list")
    public String getAllList(@ModelAttribute SearchVo searchVo, Model model,HttpSession session) {
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
        // 로그인 사용자 확인
        // 또는 "loginUser"로 통일

        // 세션에서 로그인 정보 가져오기
        String userType = (String) session.getAttribute("userType");
        MemberVo memberVo = null;
        if(userType =="member") {
        	memberVo = (MemberVo) session.getAttribute("userLogin");
        }

        System.out.println("🔍 userType: " + userType);
        System.out.println("🔍 loginMember: " + memberVo);

// ✅ 개인회원(member)인 경우에만 이력서 조회
        if (memberVo != null && "member".equals(userType)) {
            int memberId = memberVo.getMemberId();
            System.out.println("✅ 개인회원 ID: " + memberId);

            List<ResumeVo> myResumeList = resumeService.getResumesByMemberId(memberId);
            model.addAttribute("resumeList", myResumeList);
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
    public String getDetail(@RequestParam int companyId,@RequestParam int jobPostId, Model model,HttpSession session) {
        String viewKey = "viewed_" + jobPostId;
        if (session.getAttribute(viewKey) == null) {
            recruitmentService.increaseViews(jobPostId);
            session.setAttribute(viewKey, true); // 중복 방지 플래그 저장
        }


        RecruitmentVo recruitmentVo = recruitmentService.getRecruitmentId(jobPostId);
        JobVo jobVo = jobService.getJobById(recruitmentVo.getJobId());
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
        
        /* 추가사항(찜했는지 구분하는 model)(찜 구분) */

        Object loginUser = session.getAttribute("userLogin");
        String userType = (String) session.getAttribute("userType");  // 이미 login 체크할 때 사용한 값

        if ("member".equals(userType) && loginUser instanceof MemberVo) {
            MemberVo memberVo = (MemberVo) loginUser;
            int memberId = memberVo.getMemberId();
            List<ResumeVo> myResumeList = resumeService.getResumesByMemberId(memberId);
            model.addAttribute("resumeList", myResumeList);
            Integer bookMarked_JobPostId = recruitmentService.selectBookMark(memberId, jobPostId);
            model.addAttribute("bookMarked_JobPostId", bookMarked_JobPostId);
        }

        /* 찜 구분 end*/

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
                                    ,@RequestParam("tempKey") String tempKey,@RequestParam("jobImgFile") MultipartFile jobImgFile,@RequestParam(value = "question1", required = false) String q1,
                                    @RequestParam(value = "question2", required = false) String q2,
                                    @RequestParam(value = "question3", required = false) String q3,HttpSession session) {
        System.out.println("📥 컨트롤러 진입");
        String cleanTempKey = tempKey.trim().replaceAll(",", "");

        System.out.println("🔥 정제된 tempKey = " + cleanTempKey);
        // 1. 로그인 체크
        Object loginMember = session.getAttribute("userLogin");
        Object userType = session.getAttribute("userType");

        if (loginMember == null || !"company".equals(userType)) {
            return "redirect:/auth/login";
        }
        String savedName = saveImage(jobImgFile);
        // 2. 회사 ID 세팅
        CompanyVo company = (CompanyVo) loginMember;
        recruitmentVo.setCompanyId(company.getCompanyId());
        recruitmentVo.setTempKey(cleanTempKey);
        recruitmentVo.setJobImg(savedName);
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
            List<JobPostQuestionVo> questionList = new ArrayList<>();
            if (q1 != null && !q1.isBlank()) questionList.add(new JobPostQuestionVo(null, null, 1, q1));
            if (q2 != null && !q2.isBlank()) questionList.add(new JobPostQuestionVo(null, null, 2, q2));
            if (q3 != null && !q3.isBlank()) questionList.add(new JobPostQuestionVo(null, null, 3, q3));

            recruitmentVo.setQuestionList(questionList);
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

    //파일업로드//
    private String saveImage(MultipartFile file) {
        if (file == null || file.isEmpty()) return null;

        try {
            String uploadDir = "C:/upload/job_post/thumbs/";
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            String originalName = file.getOriginalFilename();
            String extension = originalName.substring(originalName.lastIndexOf('.') + 1);
            String uuid = UUID.randomUUID().toString();
            String storedName = uuid + "." + extension;

            File dest = new File(uploadDir + storedName);
            file.transferTo(dest);

            // DB에는 상대 경로 or URL 형태로 저장
            return "/upload/job_post/thumbs/" + storedName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
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

    //업데이트 갯 //
    @GetMapping("/update")
    public String updateRecruitmentForm(@RequestParam("jobPostId") int jobPostId,
                                        HttpSession session,
                                        Model model) {

        System.out.println("컨트롤러 updateRecruitmentForm jobPostId = " + jobPostId);
        // 로그인 및 권한 확인
        Object loginMember = session.getAttribute("userLogin");
        Object userType = session.getAttribute("userType");

        if (loginMember == null || !"company".equals(userType)) {
            return "redirect:/auth/login";
        }

        CompanyVo company = (CompanyVo) loginMember;

        // 1. 채용공고 조회
        RecruitmentVo recruitmentVo = recruitmentService.getRecruitmentId(jobPostId);

        if (recruitmentVo == null) {
            throw new IllegalArgumentException("공고를 찾을 수 없습니다.");
        }

        // 2. 권한 확인 (회사 소유 공고인지)
//        if (recruitmentVo.getCompanyId() != company.getCompanyId()) {
//            throw new AccessDeniedException("수정 권한이 없습니다.");
//        }

        // 3. 직무 정보
        JobVo jobVo = jobService.getJobById(recruitmentVo.getJobId());

        // 4. 복리후생, 스킬, 직군 목록
        List<WelfareVo> welfareList = recruitmentService.selectWelfareByJobPostId(jobPostId);
        List<SkillVo> selectedSkills = skillService.postTagList(jobPostId);
        List<JobGroupVo> jobGroupList = recruitmentService.jobGroupList();

        // 5. 선택된 스킬 ID만 추출
        List<Integer> selectedSkillIds = selectedSkills.stream()
                .map(SkillVo::getTagId)
                .collect(Collectors.toList());

        List<JobPostQuestionVo> JobPostQuestionList = recruitmentService.getRecruitmentQuestion(jobPostId);
        // Model에 데이터 추가
        model.addAttribute("recruitmentVo", recruitmentVo);
        model.addAttribute("jobVo", jobVo);
        model.addAttribute("welfareList", welfareList);
        model.addAttribute("jobGroupList", jobGroupList);
        model.addAttribute("selectedSkills", selectedSkills);
        model.addAttribute("JobPostQuestionList", JobPostQuestionList);

        return "recruitment/recruitmentUpdate"; // JSP 경로
    }

    @PostMapping("/update")
    public String updateRecruitment(@ModelAttribute RecruitmentVo recruitmentVo,
                                    @RequestParam("skills") String skills,
                                    @RequestParam("welfareList") String welfareList,
                                    @RequestParam("tempKey") String tempKey,
                                    @RequestParam("jobImgFile") MultipartFile jobImgFile,@RequestParam(value = "question1", required = false) String q1,
                                    @RequestParam(value = "question2", required = false) String q2,
                                    @RequestParam(value = "question3", required = false) String q3,
                                    HttpSession session) {


        // 1. 로그인 체크
        Object loginMember = session.getAttribute("userLogin");
        Object userType = session.getAttribute("userType");
        if (loginMember == null || !"company".equals(userType)) {
            return "redirect:/auth/login";
        }

        // 2. 기업 ID 세팅
        CompanyVo company = (CompanyVo) loginMember;
        recruitmentVo.setCompanyId(company.getCompanyId());

        // 3. 이미지 저장 처리 (수정일 경우 새 이미지가 들어왔을 때만 처리)
        if (jobImgFile != null && !jobImgFile.isEmpty()) {
            String savedName = saveImage(jobImgFile);
            recruitmentVo.setJobImg(savedName);
        }

        // 4. skills 처리
        List<Integer> tagIdList = Arrays.stream(skills.split(","))
                .filter(s -> !s.isBlank())
                .map(Integer::parseInt)
                .distinct() // ✅ 이 한 줄로 중복 태그 방지
                .collect(Collectors.toList());

        // 5. 복리후생 리스트 처리
        List<WelfareVo> welfareVoList = Arrays.stream(Optional.ofNullable(welfareList).orElse("")
                        .split("\\|"))
                .map(String::trim)
                .filter(w -> !w.isBlank())                     // 공백 제거
                .filter(w -> !w.equals(","))                   // 쉼표 단독 제거
                .filter(w -> w.matches(".*[가-힣a-zA-Z0-9].*")) // 내용 없는 특수문자만 필터링
                .distinct()
                .map(w -> {
                    WelfareVo vo = new WelfareVo();
                    vo.setBenefitText(w);
                    return vo;
                })
                .collect(Collectors.toList());
        System.out.println("질문지1: " + q1);
        System.out.println("질문지2: "+ q2);
        System.out.println("질문지3: "+ q3);


        List<JobPostQuestionVo> questionList = new ArrayList<>();
        if (q1 != null && !q1.isBlank()) questionList.add(new JobPostQuestionVo(null, null, 1, q1));
        if (q2 != null && !q2.isBlank()) questionList.add(new JobPostQuestionVo(null, null, 2, q2));
        if (q3 != null && !q3.isBlank()) questionList.add(new JobPostQuestionVo(null, null, 3, q3));
        recruitmentVo.setQuestionList(questionList);
        System.out.println("❓사전질문 몇 개? => " + questionList.size());
        try {
            // 6. 서비스 호출

            recruitmentService.updateRecruitment(recruitmentVo, tagIdList, welfareVoList, tempKey);
            System.out.println("✅ 채용공고 업데이트 성공 - jobPostId: " + recruitmentVo.getJobPostId());
        } catch (Exception e) {
            e.printStackTrace(); // 예외 로깅
            // 예외 페이지 혹은 에러처리 로직 구성 가능
        }

        return "redirect:/Recruitment/detail?jobPostId=" + recruitmentVo.getJobPostId()
                + "&companyId=" + recruitmentVo.getCompanyId();
    }



    @PostMapping("/filter/count")
    @ResponseBody
    public int filterCount(@RequestBody Map<String, Object> filterParams) {
        List<Integer> jobIds = (List<Integer>) filterParams.getOrDefault("jobIds", new ArrayList<>());
        List<String> careers = (List<String>) filterParams.getOrDefault("careers", new ArrayList<>());
        List<String> educations = (List<String>) filterParams.getOrDefault("educations", new ArrayList<>());
        List<Integer> skillTags = (List<Integer>) filterParams.getOrDefault("skillTags", new ArrayList<>());

        // 필터가 모두 비어있으면 count 0 또는 전체 조회 방지
        boolean isAllEmpty = jobIds.isEmpty() && careers.isEmpty() && educations.isEmpty() && skillTags.isEmpty();
        if (isAllEmpty) {
            return 0; // 또는 -1 등 프론트에서 구분 가능한 값
        }

        FilterRequestVo filterRequestVo = new FilterRequestVo();
        filterRequestVo.setJobIds(jobIds);
        filterRequestVo.setCareers(careers);
        filterRequestVo.setEducations(educations);
        filterRequestVo.setSkillTags(skillTags);

        return recruitmentService.countFilteredPosts(filterRequestVo);
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

