package com.joblessfriend.jobfinder.community.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.catalina.User;
import org.apache.ibatis.annotations.Delete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.joblessfriend.jobfinder.community.domain.CommunityVo;
import com.joblessfriend.jobfinder.community.domain.PostCommentVo;
import com.joblessfriend.jobfinder.community.service.CommunityService;
import com.joblessfriend.jobfinder.community.service.CommunityServiceImpl;
import com.joblessfriend.jobfinder.community.service.PostCommentService;
import com.joblessfriend.jobfinder.member.domain.MemberVo;
import com.joblessfriend.jobfinder.util.file.FileUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/community")//기본 시작 루트. 하단 모든 경로 앞에 붙는다 생각하기
@Controller
public class CommunityController {

	private Logger logger = LoggerFactory.getLogger(CommunityController.class);
    private final CommunityServiceImpl communityServiceImpl;
	@Autowired
	private PostCommentService postCommentService;
	@Autowired
	private CommunityService communityService;
	
	//파일 업로드용(추가)
	@Autowired
	private FileUtils fileUtils;

    CommunityController(CommunityServiceImpl communityServiceImpl) {
        this.communityServiceImpl = communityServiceImpl;
    }

	//커뮤니티 메인
//	@RequestMapping(value="", method = {RequestMethod.GET, RequestMethod.POST})
	@GetMapping("")
	public String communityList(Model model) {
	    // 커뮤니티 리스트 조회
	    List<CommunityVo> communityList = communityService.communitySelectList();  // DB에서 리스트 가져오기


	    // communityList를 순회하면서 각 커뮤니티의 content를 마크다운 -> HTML로 변환 후, 태그 제거
	    for (CommunityVo communityVo : communityList) {
	        // 마크다운을 HTML로 변환
	        String htmlContent = Markdown.markdownToHtml(communityVo.getContent());

	        // HTML 태그를 제거하고 텍스트만 남기기
	        String textContent = htmlContent.replaceAll("<[^>]*>", "");  // HTML 태그 제거

	        // 변환된 텍스트를 커뮤니티 객체에 다시 저장
	        communityVo.setContent(textContent);  // 태그 제거된 텍스트만 저장
	    }

	    // 변환된 커뮤니티 리스트를 모델에 추가
	    model.addAttribute("communityList", communityList);

	    // 리스트 화면 반환
	    return "community/list/communityList";
	}
	
	//커뮤니티 생성(화면)
	@GetMapping("/upload")
	public String communityUpload(Model model) {
		
		//업로드 화면
		return "community/add/communityUpload";
	}
	
	//커뮤니티 메인(저장)
	@PostMapping("/upload")
	public String communityUpload(@ModelAttribute CommunityVo communityVo,
			 @SessionAttribute(name = "userLogin", required = false) MemberVo userLogin, 
			 HttpSession session,
			 MultipartHttpServletRequest mhr) throws Exception{	
	    System.out.println("글쓰기 시작");
	    //게시글 시퀀스 넘버 생성용 저장 변수
	    int communityId = communityService.communitySeqNum();

	    //세션정보 저장(로그인 유저 ID 저장)
	    communityVo.setMemberId(userLogin.getMemberId()); 
	    //커뮤니티 아이디 저장
	    communityVo.setCommunityId(communityId);
	    
	    //게시글 관련 전부 저장
	    communityService.communityInsertOne(communityVo);
	    // 2. 파일 정보 세션에서 가져오기(uploadImage에서 저장한 것)
	    List<Map<String, Object>> uploadedFiles = (List<Map<String, Object>>) session.getAttribute("uploadedFiles");
	    if (uploadedFiles != null) {
	        for (Map<String, Object> fileMap : uploadedFiles) {
	            fileMap.put("parentId", communityVo.getCommunityId()); // 커뮤니티 ID 연결
	            communityService.communityFileInsertOne(fileMap);
	        }
	        session.removeAttribute("uploadedFiles"); // 사용 후 제거
	    }
	    

	    // 글 작성 완료 후 자신이 쓴 글 상세로 이동
	    return "redirect:/community/detail?no="+communityId;
	}
	
	//커뮤니티 세부
	@GetMapping("/detail")
	public String communityDetail(@RequestParam int no, Model model) {
		System.out.println("게시판 세부 시작");
		
		// 커뮤니티 상세 정보 가져오기
		CommunityVo communityVo = communityService.communityDetail(no);
		// 마크다운 -> HTML 변환
		String htmlContent = Markdown.markdownToHtml(communityVo.getContent());
	    
		/* 페이지 처음 생성시 보여지는 댓글리스트 로직 */
		List<PostCommentVo> commentsList = postCommentService.postCommentSelectList(no);
		System.out.println("댓글 수: " + commentsList.size());		
		
		model.addAttribute("commentsList", commentsList);
		
	    model.addAttribute("community", communityVo);
	    model.addAttribute("contentHtml", htmlContent); // 변환된 HTML
		
		//글 상세 화면
		return "community/detail/communityDetail";
	}
	
	//커뮤니티 업데이트(화면)
	@GetMapping("/update")
	public String communityUpdate(@RequestParam int no, Model model) {
		System.out.println("게시판 수정 시작");
		//게시글 불러옴
		CommunityVo communityVo = communityService.communityDetail(no);		
		model.addAttribute("community", communityVo);
		
		//게시글의 파일 리스트 불러옴
		List<Map<String, Object>> fileList = communityService.communityFileList(no);
		//잘 불러와지는지 확인
		for (Map<String, Object> file : fileList) {
		    System.out.println("\nfile: " + file);
		}
		//model객체에 저장
		model.addAttribute("fileList", fileList);
		
		//글 상세 화면
		return "community/update/communityUpdate";
	}
	
	//커뮤니티 업데이트(저장)
	@PostMapping("/update")
	public String communityUpdate(@ModelAttribute CommunityVo communityVo, 
			Model model, HttpSession session) {
		System.out.println("게시판 수정 시작");
		int communityId=communityVo.getCommunityId();
		

		
		model.addAttribute("community", communityVo);
		
	    //게시글 관련 전부 저장
		communityService.communityUpdate(communityVo);
	    // 2. 파일 정보 세션에서 가져오기(uploadImage에서 저장한 것)
	    List<Map<String, Object>> updatedFiles = (List<Map<String, Object>>) session.getAttribute("updatedFiles");
	    
	    //3. 업데이트 전 기존 파일 목록 삭제
	    communityService.communityFileDelete(communityId);
	    
	    //4. 새 파일 저장
	    if (updatedFiles != null) {
	        for (Map<String, Object> fileMap : updatedFiles) {
	            fileMap.put("parentId", communityId); // 커뮤니티 ID 연결
	            communityService.communityFileNewInsert(fileMap);//파일 삽입
	        }
	        session.removeAttribute("updatedFiles"); // 사용 후 제거
	    }
		    
		//글 상세 화면
		return "redirect:/community/detail?no="+communityId;
	}
	
	//커뮤니티 삭제
	@DeleteMapping("/delete/{communityId}")
	public ResponseEntity<String> communityDelete(@PathVariable("communityId") int communityId){
		communityService.communityDelete(communityId);
		
		return ResponseEntity.ok("게시물이 삭제되었습니다.");
	}
	
	//------------------------------
	//이미지 업로드(서버 파일 시스템에만 저장)
	@PostMapping("/uploadImage")
	public ResponseEntity<Map<String, Object>> uploadImage(@RequestParam("uploadFile") 
	MultipartFile file, HttpSession session) throws Exception {
	    // fileUtils의 uploadFile 메서드 호출
	    Map<String, String> uploadResult = fileUtils.uploadFile(file);

	    String storedFileName = uploadResult.get("storedFileName");
	    String originalFileName = uploadResult.get("originalFileName");
	    String fileExtension = storedFileName.substring(storedFileName.lastIndexOf('.') + 1); // 확장자 추출
	    String imageUrl = "http://localhost:9090/image/"+ storedFileName;

	    
	    Map<String, Object> fileMap = new HashMap<>();

	    fileMap.put("originalFileName", originalFileName);
	    fileMap.put("storedFileName", storedFileName);
	    fileMap.put("fileSize", file.getSize());
	    fileMap.put("fileExtension", fileExtension);
	    fileMap.put("fileLink", imageUrl);
	    
	    
	    // 세션에 파일 정보 추가
	    List<Map<String, Object>> uploadedFiles = (List<Map<String, Object>>) session.getAttribute("uploadedFiles");
	    if (uploadedFiles == null) {
	        uploadedFiles = new ArrayList<>();
	    }
	    uploadedFiles.add(fileMap);
	    session.setAttribute("uploadedFiles", uploadedFiles);
	    
	    
	    Map<String, Object> response = new HashMap<>();
	    response.put("imageUrl", imageUrl);
	    response.put("fileName", originalFileName);
	    response.put("fileId", storedFileName); // 고유 식별자 대체

	    return ResponseEntity.ok(response);
	}
	
	//파일 삭제
	@DeleteMapping("/deleteImage/{fileId}")
	public ResponseEntity<String> deleteImage(@PathVariable("fileId") String fileId, HttpSession session) {
	    List<Map<String, Object>> uploadedFiles = (List<Map<String, Object>>) session.getAttribute("uploadedFiles");

	    if (uploadedFiles != null) {
	        uploadedFiles.removeIf(file -> fileId.equals(file.get("storedFileName"))); // storedFileName가 같은 경우 리스트에서 제외
	        session.setAttribute("uploadedFiles", uploadedFiles); // 리스트 갱신
	    }

	    return ResponseEntity.ok("삭제 성공");
	}
	
	
	//파일 업데이트(단순 수정, 삭제 이용)
	@PostMapping("/updateImage")
	public ResponseEntity<Map<String, Object>> updateImage(@RequestParam("updateFile") 
		MultipartFile file, HttpSession session) throws Exception {
	    // fileUtils의 uploadFile 메서드 호출
	    Map<String, String> updateResult = fileUtils.uploadFile(file);

	    //맵 리스트에 저장할 정보
	    String storedFileName = updateResult.get("storedFileName");
	    String originalFileName = updateResult.get("originalFileName");
	    String fileExtension = storedFileName.substring(storedFileName.lastIndexOf('.') + 1); // 확장자 추출
	    String imageUrl = "http://localhost:9090/image/"+ storedFileName;

	    		
	    System.out.println("\n파일명: " + storedFileName);
	    System.out.println("원래파일명: " + originalFileName);
	    System.out.println("확장자명: "+ fileExtension);
	    System.out.println("링크: "+imageUrl);
	    System.out.println("파일 사이즈: "+ file.getSize());
		/* System.out.println("파일 사이즈: "+ communityId); */
	    
	    Map<String, Object> fileMap = new HashMap<>();

	    fileMap.put("originalFileName", originalFileName);
	    fileMap.put("storedFileName", storedFileName);
	    fileMap.put("fileSize", file.getSize());
	    fileMap.put("fileExtension", fileExtension);
	    fileMap.put("fileLink", imageUrl);
	    
	    
	    // 세션에 파일 정보 추가
	    List<Map<String, Object>> updatedFiles = (List<Map<String, Object>>) session.getAttribute("updatedFiles");
	    if (updatedFiles == null) {
	    	updatedFiles = new ArrayList<>();
	    }
	    updatedFiles.add(fileMap);
	    session.setAttribute("uploadedFiles", updatedFiles);
	    
	    
	    Map<String, Object> response = new HashMap<>();
	    response.put("imageUrl", imageUrl);
	    response.put("fileName", originalFileName);
	    response.put("fileId", storedFileName); // 고유 식별자 대체

	    return ResponseEntity.ok(response);
	}
	


}
