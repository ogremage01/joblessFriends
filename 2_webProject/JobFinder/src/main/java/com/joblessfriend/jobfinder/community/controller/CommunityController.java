package com.joblessfriend.jobfinder.community.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.joblessfriend.jobfinder.community.domain.CommunityVo;
import com.joblessfriend.jobfinder.community.domain.PostCommentVo;
import com.joblessfriend.jobfinder.community.service.CommunityService;
import com.joblessfriend.jobfinder.community.service.PostCommentService;
import com.joblessfriend.jobfinder.community.service.ReplyService;
import com.joblessfriend.jobfinder.member.domain.MemberVo;
import com.joblessfriend.jobfinder.util.Pagination;
import com.joblessfriend.jobfinder.util.SearchVo;
import com.joblessfriend.jobfinder.util.file.FileUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/community")//기본 시작 루트. 하단 모든 경로 앞에 붙는다 생각하기
@Controller
public class CommunityController {

	private Logger logger = LoggerFactory.getLogger(CommunityController.class);

	@Autowired
	private PostCommentService postCommentService;
	@Autowired
	private CommunityService communityService;
	@Autowired
	private ReplyService replyService;
	
	@Autowired
	HttpServletRequest request;//이미지 저장 서버를 위해 생성
	
	//파일 업로드용(추가)
	@Autowired
	private FileUtils fileUtils;


	//커뮤니티 메인
//	@RequestMapping(value="", method = {RequestMethod.GET, RequestMethod.POST})
	@GetMapping("")
	public String communityList(Model model, @RequestParam(defaultValue = "1") int page, 
			@RequestParam(defaultValue = "") String keyword) {

/* 페이지네이션 */
		SearchVo searchVo = new SearchVo();
		searchVo.setKeyword(keyword);
		searchVo.setPage(page);
		searchVo.setRecordSize(4);	
		
	    int totalCount = communityService.getCommunityTotalCount(searchVo);//전체 데이터 수
	    Pagination pagination = new Pagination(totalCount, searchVo);
	    
        searchVo.setStartRow(pagination.getLimitStart() + 1); // 1부터 시작
        searchVo.setEndRow(searchVo.getStartRow() + searchVo.getRecordSize() - 1);
/* 페이지네이션 세팅 끝 */
        
        
	    // 커뮤니티 리스트 조회
	    List<CommunityVo> communityList = communityService.communitySelectList(searchVo);  // DB에서 리스트 가져오기
	    

	    List<PostCommentVo> commentList = null;
	    int commentCount = 0;
	    int replycount = 0;
	    
	    // communityList를 순회하면서 각 커뮤니티의 content를 마크다운 -> HTML로 변환 후, 태그 제거
	    for (CommunityVo communityVo : communityList) {
	        // 마크다운을 HTML로 변환
	        String htmlContent = Markdown.markdownToHtml(communityVo.getContent());

	        // HTML 태그를 제거하고 텍스트만 남기기
	        String textContent = htmlContent.replaceAll("<[^>]*>", "");  // HTML 태그 제거

	        // 변환된 텍스트를 커뮤니티 객체에 다시 저장
	        communityVo.setContent(textContent);  // 태그 제거된 텍스트만 저장
	        
	        //댓글 수 저장
	        commentList=postCommentService.postCommentSelectList(communityVo.getCommunityId());
	        for (PostCommentVo postCommentVo : commentList) {
	        	replycount += replyService.replySelectList(postCommentVo.getPostCommentId()).size();
	        }
	        
	        commentCount = commentList.size()+replycount;
	        communityVo.setCommentCount(commentCount);
	    }

	    // 변환된 커뮤니티 리스트를 모델에 추가(화면에 출력하기 위함)
	    model.addAttribute("communityList", communityList);
	    model.addAttribute("searchVo", searchVo);
	    model.addAttribute("pagination", pagination);
	    
	    
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
	            fileMap.put("COMMUNITYID", communityId); // 커뮤니티 ID 연결
	            communityService.communityFileInsertOne(fileMap);
	        }
	        session.removeAttribute("uploadedFiles"); // 사용 후 제거
	    }
	    

	    // 글 작성 완료 후 자신이 쓴 글 상세로 이동
	    return "redirect:/community/detail?no="+communityId;
	}
	
	//커뮤니티 세부
	@GetMapping("/detail")
	public String communityDetail(@RequestParam int no, Model model, HttpSession session) {
		System.out.println("게시판 세부 시작");

		// 커뮤니티 상세 정보 가져오기
		CommunityVo communityVo = communityService.communityDetail(no);
		
		//session중일 떄 view 카운트
		if(session != null || session.getAttribute("userLogin")!=null) {
			Boolean viewed = (Boolean) session.getAttribute("community_"+no);
			
			if(viewed == null || !viewed) {
	
				int views = communityVo.getViews();
				
				views += 1;
				
				communityVo.setViews(views);
				
				//조회수 업데이트
				communityService.communityViewCount(communityVo);
				
				session.setAttribute("community_"+no, true);//해당 세션 중 뷰 카운트 했으면 true(여러번 카운트 못하게 막음)
			}
		
		}
		
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
	public String communityUpdate(@RequestParam int no, Model model, HttpSession session) {
		System.out.println("게시판 수정 시작");

		//게시글 불러옴
		CommunityVo communityVo = communityService.communityDetail(no);		
		model.addAttribute("community", communityVo);
		
		MemberVo memberVo = (MemberVo) session.getAttribute("userLogin");
		
		if(memberVo.getMemberId() != communityVo.getMemberId()) {
			
			return "error/error";
		}
		
		//게시글의 파일 리스트 불러옴
		List<Map<String, Object>> fileList = communityService.communityFileList(no);
		//잘 불러와지는지 확인(잘뜸)
		for (Map<String, Object> file : fileList) {
		    System.out.println("\n열 부적합 확인해보는 file: " + file);
		}
		//model객체에 저장
		model.addAttribute("fileList", fileList);
		
		
		// 이미 세션에 updatedFiles가 있으면 다시 넣지 않음
	    if (session.getAttribute("updatedFiles") == null) {
	        session.setAttribute("updatedFiles", fileList);
	    }
	    
		session.setAttribute("updatedFiles", fileList);//<<열부적합은 해결했는데 이번엔 영원히 들어간다.

		//글 상세 화면
		return "community/update/communityUpdate";
	}
	
	//커뮤니티 업데이트(저장)
	@PostMapping("/update")
	public String communityUpdate(@ModelAttribute CommunityVo communityVo, 
			Model model, HttpSession session) {
		System.out.println("게시판 수정 시작");
		int communityId=communityVo.getCommunityId();
		System.out.println("수정 시 게시글 아이디값: "+communityId);
		
		model.addAttribute("community", communityVo);
		
		System.out.println("communityVo 확인용: ㄴ"+ communityVo);
		
	    //게시글 관련 전부 저장
		communityService.communityUpdate(communityVo);
		
	    List<Map<String, Object>> updatedFiles = (List<Map<String, Object>>) session.getAttribute("updatedFiles");
	    System.out.println("updatedFiles 확인용: "+ updatedFiles);//잘나옴
	    

	    //3. 업데이트 전 기존 파일 목록 삭제
	    communityService.communityFileDelete(communityId);//파일 전체 삭제- Db
	    if (updatedFiles != null && !updatedFiles.isEmpty()) {
	    // 2. 파일 정보 세션에서 가져오기(uploadImage에서 저장한 것)
    
		    //4. 새 파일 저장
	        for (Map<String, Object> fileMap : updatedFiles) {
	            fileMap.put("COMMUNITYID", communityId); // 커뮤니티 ID 연결
	            communityService.communityFileNewInsert(fileMap);//파일 삽입
	        }
	    }
		session.removeAttribute("updatedFiles"); // 사용 후 제거
		    
		//글 상세 화면
		return "redirect:/community/detail?no="+communityId;
	}
	
	//커뮤니티 삭제
	@DeleteMapping("/delete/{communityId}")
	public ResponseEntity<String> communityDelete(@PathVariable("communityId") int communityId, HttpSession session){		
		if(session.getAttribute("userType") == "member") {
			MemberVo memberVo = (MemberVo) session.getAttribute("userLogin");
			//게시글 불러옴
			CommunityVo communityVo = communityService.communityDetail(communityId);	
			
			if(memberVo.getMemberId() != communityVo.getMemberId()) {
				
				return ResponseEntity.notFound().build();
			}
		}

				
		if(session.getAttribute("userType") != "admin") {
			
			return ResponseEntity.notFound().build();
		}
		
		communityService.communityFileDelete(communityId);
		communityService.communityDelete(communityId);
		return ResponseEntity.ok("게시물 삭제");
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
	    String imageUrl = "/image/"+ storedFileName;

	    
	    Map<String, Object> fileMap = new HashMap<>();

	    System.out.println("originalFileName: "+originalFileName);
	    fileMap.put("FILENAME", originalFileName);
	    fileMap.put("STOREDFILENAME", storedFileName);
	    fileMap.put("FILESIZE", file.getSize());
	    fileMap.put("FILEEXTENSION", fileExtension);
	    fileMap.put("FILELINK", imageUrl);
	    
	    
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
	    List<Map<String, Object>> updatedFiles = (List<Map<String, Object>>) session.getAttribute("updatedFiles");

	    // 각각 null이 아닐 경우에만 필터링해서 삭제
	    if (uploadedFiles != null) {
	        uploadedFiles.removeIf(file -> fileId.equals(file.get("STOREDFILENAME")));
	        session.setAttribute("uploadedFiles", uploadedFiles);
	    }

	    if (updatedFiles != null) {
	    	updatedFiles.removeIf(file -> fileId.equals(file.get("STOREDFILENAME")));
	        session.setAttribute("updatedFiles", updatedFiles);
	    }
	    System.out.println("삭제 후 updatedFiles: " + updatedFiles);
	    
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
	    String imageUrl = "/image/"+ storedFileName;

	    		
	    System.out.println("\n파일명: " + storedFileName);
	    System.out.println("원래파일명: " + originalFileName);
	    System.out.println("확장자명: "+ fileExtension);
	    System.out.println("링크: "+imageUrl);
	    System.out.println("파일 사이즈: "+ file.getSize());
		/* System.out.println("파일 사이즈: "+ communityId); */
	    
	    Map<String, Object> fileMap = new HashMap<>();

	    fileMap.put("FILENAME", originalFileName);
	    fileMap.put("STOREDFILENAME", storedFileName);
	    fileMap.put("FILESIZE", file.getSize());
	    fileMap.put("FILEEXTENSION", fileExtension);
	    fileMap.put("FILELINK", imageUrl);
	    
	
		System.out.println("\n열 부적합 확인해보는 새로넣은 file: " + fileMap);
		
	    // 세션에 파일 정보 추가
	    List<Map<String, Object>> updatedFiles = (List<Map<String, Object>>) session.getAttribute("updatedFiles");
	    if (updatedFiles == null) {
	    	updatedFiles = new ArrayList<>();
	    }
	    updatedFiles.add(fileMap);
	    session.setAttribute("updatedFiles", updatedFiles);
	    
	    
	    Map<String, Object> response = new HashMap<>();
	    response.put("imageUrl", imageUrl);
	    response.put("fileName", originalFileName);
	    response.put("fileId", storedFileName); // 고유 식별자 대체

	    return ResponseEntity.ok(response);
	}
	


}
