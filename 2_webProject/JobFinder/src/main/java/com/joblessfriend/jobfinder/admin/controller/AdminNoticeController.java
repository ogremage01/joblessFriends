package com.joblessfriend.jobfinder.admin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.joblessfriend.jobfinder.admin.domain.AdminVo;
import com.joblessfriend.jobfinder.community.controller.Markdown;
import com.joblessfriend.jobfinder.community.domain.CommunityVo;
import com.joblessfriend.jobfinder.community.domain.NoticeCategoryVo;
import com.joblessfriend.jobfinder.community.domain.NoticeVo;
import com.joblessfriend.jobfinder.community.service.NoticeCategoryService;
import com.joblessfriend.jobfinder.community.service.NoticeService;
import com.joblessfriend.jobfinder.member.domain.MemberVo;
import com.joblessfriend.jobfinder.util.Pagination;
import com.joblessfriend.jobfinder.util.SearchVo;

import jakarta.servlet.http.HttpSession;

//공지 부문
/* 존재하는 기능: 공지글 삽입, 삭제, 업데이트(화면 따로 디자인 및 생성), 리스트 띄우기*/


@RequestMapping("/admin/community/notice")
@Controller
public class AdminNoticeController {
	
	@Autowired
	private NoticeService noticeService;
	
	@Autowired
	private NoticeCategoryService noticeCategoryService;
	
	//관리자: 공지 리스트
	@GetMapping("")
	public String noticeAdminSelectList(Model model, @RequestParam(defaultValue = "1") int page, 
			@RequestParam(defaultValue = "") String keyword) {
		
/* 페이지네이션 */
		SearchVo searchVo = new SearchVo();
		searchVo.setKeyword(keyword);
		searchVo.setPage(page);
		searchVo.setRecordSize(8);	
		
	    int totalCount = noticeService.getNoticeTotalCount(searchVo);//전체 데이터 수
	    Pagination pagination = new Pagination(totalCount, searchVo);
	    
        searchVo.setStartRow(pagination.getLimitStart() + 1); // 1부터 시작
        searchVo.setEndRow(searchVo.getStartRow() + searchVo.getRecordSize() - 1);
/* 페이지네이션 세팅 끝 */
        
        
	    // 커뮤니티 리스트 조회
	    List<NoticeVo> noticeList = noticeService.noticeSelectList(searchVo);  // DB에서 리스트 가져오기

	    // 변환된 커뮤니티 리스트를 모델에 추가(화면에 출력하기 위함)
	    model.addAttribute("noticeList", noticeList);
	    model.addAttribute("searchVo", searchVo);
	    model.addAttribute("pagination", pagination);
	    
	    
        
		return "admin/community/notice/noticeView";
	}
	
	
/* 관리자: 공지 추가 */
	@GetMapping("/upload")
	public String noticeAdminUpload(Model model) {
		
		//카테고리 정보 전체 리스트로 가져오기
		List<NoticeCategoryVo> noticeCategoryList = noticeCategoryService.noticeCategoryList();
		
		model.addAttribute("noticeCategoryList", noticeCategoryList);
		//업로드 화면
		return "admin/community/notice/noticeUploadView";
	}
	
	@PostMapping("/upload")
	public String noticeAdminUpload(@ModelAttribute NoticeVo noticeVo,
			 @SessionAttribute(name = "userLogin", required = false) AdminVo userLogin,//이부분은 좀 관리자에 맞게 수정 
			 HttpSession session,
			 MultipartHttpServletRequest mhr) {
		
		
		//게시글 시퀀스 넘버 생성용 저장 변수
	    int noticeId = noticeService.noticeSeqNum();

	    //세션정보 저장(로그인 관리자 ID 저장)
	    noticeVo.setAdminId(userLogin.getAdminId()); 
	    
	    //커뮤니티 아이디 저장
	    noticeVo.setNoticeId(noticeId);
	    
	    //게시글 관련 전부 저장
	    noticeService.noticeInsertOne(noticeVo);
	    
/*파일 관련 우선 주석	    
	    // 2. 파일 정보 세션에서 가져오기(uploadImage에서 저장한 것)
	    List<Map<String, Object>> uploadedFiles = (List<Map<String, Object>>) session.getAttribute("uploadedFiles");
	    if (uploadedFiles != null) {
	        for (Map<String, Object> fileMap : uploadedFiles) {
	            fileMap.put("NOTICEID", noticeVo.getNoticeId()); // 커뮤니티 ID 연결
	            noticeService.noticeFileInsertOne(fileMap);
	        }
	        session.removeAttribute("uploadedFiles"); // 사용 후 제거
	    }
 */	   
		// 글 작성 완료 후 목록으로 돌아가기
	    return "redirect:/admin/community/notice";
	}

/* 관리자: 공지 추가 end */	
	
	
	/* 관리자: 공지 삭제 */
	@DeleteMapping("/delete")
	public ResponseEntity<String> communityPostDelete(@RequestBody List<Integer> noticeIdList) {
		for (Integer i : noticeIdList) {
			System.out.println("삭제할 게시물 Id " + i);

		}
		
		noticeService.noticeDelete(noticeIdList);

		
		return ResponseEntity.ok("삭제완료"); 
	}
	/* 관리자: 공지 삭제 end*/
	
	/* 관리자: 공지 수정 */
	//공지 업데이트 화면 생성
	@GetMapping("/update")
	public String noticeUpdate(@RequestParam int no, Model model, HttpSession session) {
		System.out.println("공지 수정 시작(화면)");

		//게시글 불러옴
		NoticeVo noticeVo = noticeService.noticeDetail(no);		
		model.addAttribute("notice", noticeVo);
		
		//카테고리 정보 전체 리스트로 가져오기
		List<NoticeCategoryVo> noticeCategoryList = noticeCategoryService.noticeCategoryList();
		
		model.addAttribute("noticeCategoryList", noticeCategoryList);
		
		String adminCheck = (String)session.getAttribute("userType");
		
		if(adminCheck != "admin") {
			
			return "error/error";
		}
		
//		//게시글의 파일 리스트 불러옴
//		List<Map<String, Object>> fileList = noticeService.noticeFileList(no);
//		//잘 불러와지는지 확인(잘뜸)
//		for (Map<String, Object> file : fileList) {
//		    System.out.println("\n열 부적합 확인해보는 file: " + file);
//		}
//		//model객체에 저장
//		model.addAttribute("fileList", fileList);
		
		
		// 이미 세션에 updatedFiles가 있으면 다시 넣지 않음
//	    if (session.getAttribute("updatedFiles") == null) {
//	        session.setAttribute("updatedFiles", fileList);
//	    }
//	    
//		session.setAttribute("updatedFiles", fileList);//<<열부적합은 해결했는데 이번엔 영원히 들어간다.

		//글 상세 화면
		return "admin/community/notice/noticeUpdateView";
	}
	
	//공지 업데이트(저장)
	@PostMapping("/update")
	public String noticeUpdate(@ModelAttribute NoticeVo noticeVo, 
			Model model, HttpSession session) {
		System.out.println("공지 수정 시작");
		int noticeId=noticeVo.getNoticeId();
		System.out.println("수정 시 공지 아이디값: "+noticeId);
		
		model.addAttribute("notice", noticeVo);
		
		System.out.println("noticeVo 확인용: "+ noticeVo);
		
	    //게시글 관련 전부 저장
		noticeService.noticeUpdate(noticeVo);
		
//	    List<Map<String, Object>> updatedFiles = (List<Map<String, Object>>) session.getAttribute("updatedFiles");
//	    System.out.println("updatedFiles 확인용: "+ updatedFiles);//잘나옴
	    

//	    //3. 업데이트 전 기존 파일 목록 삭제
//	    noticeService.noticeFileDelete(noticeId);//파일 전체 삭제- Db
//	    if (updatedFiles != null && !updatedFiles.isEmpty()) {
//	    // 2. 파일 정보 세션에서 가져오기(uploadImage에서 저장한 것)
//    
//		    //4. 새 파일 저장
//	        for (Map<String, Object> fileMap : updatedFiles) {
//	            fileMap.put("COMMUNITYID", noticeId); // 커뮤니티 ID 연결
//	            noticeService.noticeFileNewInsert(fileMap);//파일 삽입
//	        }
//	    }
//		session.removeAttribute("updatedFiles"); // 사용 후 제거
		    
		//글 상세 화면
		return "redirect:/admin/community/notice";
	}
	/* 관리자: 공지 수정 */
	

}
