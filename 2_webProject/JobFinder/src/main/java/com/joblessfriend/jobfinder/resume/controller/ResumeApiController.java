package com.joblessfriend.jobfinder.resume.controller;

import java.util.Map;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joblessfriend.jobfinder.member.domain.MemberVo;
import com.joblessfriend.jobfinder.resume.domain.ResumeVo;
import com.joblessfriend.jobfinder.resume.parser.ResumeParser;
import com.joblessfriend.jobfinder.resume.service.ResumeService;

@RestController
@RequestMapping("/api/resume")
public class ResumeApiController {

    @Autowired
    private ResumeParser resumeParser;
    
    @Autowired
    private ResumeService resumeService;

    /**
     * 이력서 저장 API
     */
    @PostMapping("/save")
    public ResponseEntity<String> saveResume(@RequestBody Map<String, Object> requestData, HttpSession session) {
        try {
            // 세션에서 로그인 사용자 정보 가져오기
            MemberVo loginUser = (MemberVo) session.getAttribute("userLogin");
            if (loginUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
            }
            
            int memberId = loginUser.getMemberId();
            System.out.println(">>> [ResumeApiController] 로그인 사용자 ID: " + memberId);

            // JSON 데이터를 ResumeVo로 파싱
            ResumeVo resumeVo = resumeParser.parseMapToResumeVo(requestData, memberId);
            
            // 파싱된 데이터 검증
            if (resumeVo.getMemberName() == null || resumeVo.getMemberName().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("이름은 필수 입력 항목입니다.");
            }
            
            if (resumeVo.getEmail() == null || resumeVo.getEmail().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("이메일은 필수 입력 항목입니다.");
            }

            // 데이터베이스에 저장
            resumeService.saveResume(resumeVo);
            
            // 성공 응답
            return ResponseEntity.ok("이력서가 성공적으로 저장되었습니다.");
            
        } catch (Exception e) {
            System.err.println(">>> [ResumeApiController] 이력서 저장 실패: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("이력서 저장 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    /**
     * 이력서 수정 API
     */
    @PutMapping("/update")
    public ResponseEntity<String> updateResume(@RequestBody Map<String, Object> requestData, HttpSession session) {
        try {
            // 세션에서 로그인 사용자 정보 가져오기
            MemberVo loginUser = (MemberVo) session.getAttribute("userLogin");
            if (loginUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
            }
            
            int memberId = loginUser.getMemberId();
            System.out.println(">>> [ResumeApiController] 이력서 수정 요청, 사용자 ID: " + memberId);

            // JSON 데이터를 ResumeVo로 파싱
            ResumeVo resumeVo = resumeParser.parseMapToResumeVo(requestData, memberId);
            
            // resumeId가 있는지 확인
            if (resumeVo.getResumeId() <= 0) {
                return ResponseEntity.badRequest().body("이력서 ID가 유효하지 않습니다.");
            }
            
            // 파싱된 데이터 검증
            if (resumeVo.getMemberName() == null || resumeVo.getMemberName().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("이름은 필수 입력 항목입니다.");
            }
            
            if (resumeVo.getEmail() == null || resumeVo.getEmail().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("이메일은 필수 입력 항목입니다.");
            }

            // 데이터베이스에 업데이트
            resumeService.updateResume(resumeVo);
            
            // 성공 응답
            return ResponseEntity.ok("이력서가 성공적으로 수정되었습니다.");
            
        } catch (Exception e) {
            System.err.println(">>> [ResumeApiController] 이력서 수정 실패: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("이력서 수정 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    /**
     * 이력서 상세 조회 API (수정용)
     */
    @GetMapping("/detail/{resumeId}")
    public ResponseEntity<String> getResumeDetail(@PathVariable int resumeId, HttpSession session) {
        try {
            // 세션에서 로그인 사용자 정보 가져오기
            MemberVo loginUser = (MemberVo) session.getAttribute("userLogin");
            if (loginUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
            }
            
            int memberId = loginUser.getMemberId();
            System.out.println(">>> [ResumeApiController] 이력서 조회 요청, resumeId: " + resumeId + ", memberId: " + memberId);

            // 이력서 전체 정보 조회
            ResumeVo resumeVo = resumeService.getResumeWithAllDetails(resumeId);
            
            if (resumeVo == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("이력서를 찾을 수 없습니다.");
            }
            
            // 작성자 본인인지 확인
            if (resumeVo.getMemberId() != memberId) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("본인의 이력서만 조회할 수 있습니다.");
            }

            // ResumeVo를 JSON으로 변환하여 반환
            String jsonResponse = resumeParser.parseResumeVoToJson(resumeVo);
            
            return ResponseEntity.ok(jsonResponse);
            
        } catch (Exception e) {
            System.err.println(">>> [ResumeApiController] 이력서 조회 실패: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("이력서 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    /**
     * 이력서 미리보기용 JSON 변환 API
     */
    @PostMapping("/preview")
    public ResponseEntity<String> getResumePreview(@RequestBody Map<String, Object> requestData, HttpSession session) {
        try {
            // 세션에서 로그인 사용자 정보 가져오기
            MemberVo loginUser = (MemberVo) session.getAttribute("userLogin");
            if (loginUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
            }
            
            int memberId = loginUser.getMemberId();

            // JSON 데이터를 ResumeVo로 파싱
            ResumeVo resumeVo = resumeParser.parseMapToResumeVo(requestData, memberId);
            
            // ResumeVo를 다시 JSON으로 변환하여 반환
            String jsonResponse = resumeParser.parseResumeVoToJson(resumeVo);
            
            return ResponseEntity.ok(jsonResponse);
            
        } catch (Exception e) {
            System.err.println(">>> [ResumeApiController] 미리보기 생성 실패: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("미리보기 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}
