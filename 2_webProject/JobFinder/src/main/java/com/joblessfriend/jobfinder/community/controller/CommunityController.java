package com.joblessfriend.jobfinder.community.controller;
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
			 MultipartHttpServletRequest mhr) throws Exception{	
	    System.out.println("글쓰기 시작");

	    //세션정보 저장(로그인 유저 ID 저장)
	    communityVo.setMemberId(userLogin.getMemberId()); 
	    
	    //게시글 관련 전부 저장
	    communityService.communityInsertOne(communityVo);
	    
	    // 2. 파일 업로드 및 파일 메타정보 가져오기
	    List<Map<String, Object>> fileList = fileUtils.uploadFilesInfo(communityVo.getCommunityId(), mhr);
	   
	    logger.info("이미지 107번쨰 라인 시작");
	    logger.info("fileList: {}", fileList); 
	    // 3. 파일 정보를 DB에 저장
	    for (Map<String, Object> fileMap : fileList) {
	    	logger.info("이미지 107번쨰 라인 안 for문 시작");

	        communityService.communityFileInsertOne(fileMap);
	    }
	    /*start +img */
	    //에디터 본문 내용(이미지까지 포함됨)
	    //String content = communityVo.getContent();
	    
		/*
		 * //정규표현식으로 <img src="링크"> 추출 후 content 삽입 Pattern pattern =
		 * Pattern.compile("<img[^>]+src=[\"']http://localhost:9090/img/(.+?[\"'])");
		 * Matcher matcher = pattern.matcher(content);
		 */
		/*
		 * //파일 정보를 DB에 저장할 MAP(이미지 URL이 포함된 경우) while(matcher.find()) { String
		 * storedFileName = matcher.group(1);//추출된 파일 이름
		 * 
		 * Map<String,Object> fileMap = new HashMap<>(); fileMap.put("parentId",
		 * communityVo.getCommunityId());//게시글 ID fileMap.put("storedFileName",
		 * storedFileName);//저장된 파일명
		 * 
		 * communityService.communityFileInsertOne(fileMap); }
		 */
	    
	    /*end +img*/


	    // 글 작성 완료 후 목록 페이지로 리다이렉트
	    return "redirect:/community";
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
	
	@GetMapping("/update")
	public String communityUpdate(@RequestParam int no, Model model) {
		System.out.println("게시판 수정 시작");
		
		CommunityVo communityVo = communityService.communityDetail(no);
		
		model.addAttribute("community", communityVo);
		//글 상세 화면
		return "community/update/communityUpdate";
	}
	
	//커뮤니티 업로드
	@PostMapping("/update")
	public String communityUpdate(@ModelAttribute CommunityVo communityVo, Model model) {
		System.out.println("게시판 수정 시작");
		
		communityService.communityUpdate(communityVo);
		
		model.addAttribute("community", communityVo);
		    
		//글 상세 화면
		return "redirect:/community/detail?no="+communityVo.getCommunityId();
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
	public ResponseEntity<Map<String, Object>> uploadImage(@RequestParam("uploadFile") MultipartFile file) throws Exception {
	    // fileUtils의 uploadFile 메서드 호출
	    Map<String, String> uploadResult = fileUtils.uploadFile(file);

	    String storedFileName = uploadResult.get("storedFileName");
	    System.out.println("파일명: "+storedFileName);
	    String imageUrl = "http://localhost:9090/image/" + storedFileName;
	    String originalFileName = uploadResult.get("originalFileName");
	    
	    System.out.println("원래파일명: "+originalFileName);
	    
	    Map<String, Object> fileMeta = new HashMap<>();
	    fileMeta.put("storedFileName", storedFileName);
	    fileMeta.put("originalFileName", originalFileName);
	    fileMeta.put("fileSize", file.getSize());
	    
	    
	    Map<String, Object> response = new HashMap<>();
	    response.put("imageUrl", imageUrl);

	    return ResponseEntity.ok(response);
	}
	


}
