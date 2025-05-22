package com.joblessfriend.jobfinder.resume.portfolio.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.joblessfriend.jobfinder.util.file.FileUtils;

import jakarta.servlet.http.HttpSession;

@Controller
public class PortfolioController {
	
	@Autowired
	private FileUtils fileUtils;
	
	//이미지 업로드(서버 파일 시스템에만 저장)
		@PostMapping("/uploadFile")
		public ResponseEntity<Map<String, Object>> uploadImage(@RequestParam("uploadFile") 
		MultipartFile file, HttpSession session) throws Exception {
		    // fileUtils의 uploadFile 메서드 호출
		    Map<String, String> uploadResult = fileUtils.uploadFile(file);

		    String storedFileName = uploadResult.get("storedFileName");
		    String originalFileName = uploadResult.get("originalFileName");
		    String fileExtension = storedFileName.substring(storedFileName.lastIndexOf('.') + 1); // 확장자 추출
		    String fileUrl = "http://localhost:9090/resume/"+ storedFileName;

		    		
		    System.out.println("\n파일명: " + storedFileName);
		    System.out.println("원래파일명: " + originalFileName);
		    System.out.println("확장자명: "+ fileExtension);
		    System.out.println("링크: "+fileUrl);
		    System.out.println("파일 사이즈: "+ file.getSize());
		    
		    Map<String, Object> fileMap = new HashMap<>();

		    fileMap.put("originalFileName", originalFileName);
		    fileMap.put("storedFileName", storedFileName);
		    fileMap.put("fileSize", file.getSize());
		    fileMap.put("fileExtension", fileExtension);
		    fileMap.put("fileLink", fileUrl);
		    
		    
		    // 세션에 파일 정보 추가
		    List<Map<String, Object>> uploadedFiles = (List<Map<String, Object>>) session.getAttribute("uploadedFiles");
		    if (uploadedFiles == null) {
		        uploadedFiles = new ArrayList<>();
		    }
		    uploadedFiles.add(fileMap);
		    session.setAttribute("uploadedFiles", uploadedFiles);
		    

		    // DB에 저장
		  //  communityService.communityFileInsertOne(fileMap);
		    
		    
		    Map<String, Object> response = new HashMap<>();
		    response.put("fileUrl", fileUrl);
		    response.put("fileName", originalFileName);
		    response.put("fileId", storedFileName); // 고유 식별자 대체

		    return ResponseEntity.ok(response);
		}
		
		//파일 삭제
		@DeleteMapping("/deleteFile/{fileId}")
		public ResponseEntity<String> deleteImage(@PathVariable("fileId") String fileId, HttpSession session) {
		    List<Map<String, Object>> uploadedFiles = (List<Map<String, Object>>) session.getAttribute("uploadedFiles");

		    if (uploadedFiles != null) {
		        uploadedFiles.removeIf(file -> fileId.equals(file.get("storedFileName"))); // storedFileName가 같은 경우 리스트에서 제외
		        session.setAttribute("uploadedFiles", uploadedFiles); // 리스트 갱신
		    }

		    return ResponseEntity.ok("삭제 성공");
		}

}
