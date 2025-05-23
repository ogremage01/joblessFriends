package com.joblessfriend.jobfinder.util.file;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


@Component("fileUtils")//Spring Bean으로 등록
public class FileUtils {
	private static final String filePath = "C:\\upload\\files"; //파일 저장경로
	
	//다중 파일 업로드 처리
	//MultipartHttpServletRequest: 서버에서 요청보낸 이미지를 이곳에 받음(저장 x-저장로직은 따로)
	public List<Map<String,Object>> uploadFilesInfo(int communityId, 
			MultipartHttpServletRequest multifiles) throws Exception{
		
		Iterator<String> serverFileName = multifiles.getFileNames();//여러파일을 받을 수 있으므로 getFileNames().
		MultipartFile multipartFile = null;//파일 객체
		String fileName = null;//원본 파일 이름
		String fileExtension = null;//확장자
		String storedFileName = null;//서버에 저장될 파일 이름(중복 방지용으로 랜덤 처리)
		
		List<Map<String, Object>> fileList = new ArrayList<Map<String, Object>>();//반환할 리스트 객체(파일 정보들이 들었음)
		Map<String, Object> fileInfoMap = null; //개별 파일 정보가 들어가는 Map 객체
		
		File file = new File(filePath); //저장경로(D:\\files)에 File 객체 생성
		
		//위에서 선언한 filePath(경로)가 존재하지 않을 경우 디렉토리 생성
		if(file.exists() == false) {
			file.mkdirs();
		}
		
		//업로드된 파일 처리
		//serverFileName는 현재 위치를 내부적으로 기억하고 있는 객체
		//여기서 serverFileName 역할: 파일 이름 목록 순회(파일 이름을 순서대로 꺼냄)-serverFileName을 통해 진짜 파일인 multipartFile을 가져온다.
		while (serverFileName.hasNext()) {

			multipartFile = multifiles.getFile(serverFileName.next());//serverFileName.next()는 다음 요소로 이동준비 하면서 지금차례 요소를 반환.
			
			//파일이 존재할 경우
			if(multipartFile.isEmpty()==false) {
				fileName = multipartFile.getOriginalFilename();//fileName에 사용자 업로드 원본 파일명 저장
				fileExtension = fileName.substring(fileName.lastIndexOf("."));//확장자 저장
				
				//랜덤 문자열로 된 파일명 생성(중복 방지)
				storedFileName = RandomUtils.getRandomString()+fileExtension;
				
				//화면에서 서버에 들어온파일을 서버 디스크에 저장.<- 사진을 화면에 올림과 동시에 서버로 전송요청. 그것을 디스크에 저장하는 과정.
				file = new File(filePath, storedFileName);
				multipartFile.transferTo(file);//디스크에 저장
				
				//파일 정보를 메모리(DB)에 저장
				fileInfoMap = new HashMap<>();
				fileInfoMap.put("parentId", communityId);
				fileInfoMap.put("originalFileName", fileName);
				fileInfoMap.put("storedFileName", storedFileName);
		//		fileInfoMap.put("fileExtension", fileExtension);
				fileInfoMap.put("fileSize", multipartFile.getSize());

				fileList.add(fileInfoMap);
			}
		}
		
		return fileList;
	}
	
	/* 메모할 부분 
	 * 1. MultipartFile image: 파일 객체
	 * 
	 * 
	 * */
	
	
	//단일 파일 업로드(후크 테스트용)
	@PostMapping("/uploadImage")
	public Map<String, String> uploadFile(MultipartFile multipartFile) throws Exception {
		//파일이 비어있을 경우반환.
	    if (multipartFile == null || multipartFile.isEmpty()) {
	        throw new IllegalArgumentException("File is empty");
	    }

	    String originalFileName = multipartFile.getOriginalFilename();
	    String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
	    String storedFileName = RandomUtils.getRandomString() + fileExtension;

	    //파일 디렉토리 없을 경우 추가
	    File fileDir = new File(filePath);
	    if (!fileDir.exists()) {
	        fileDir.mkdirs();
	    }

	    //저장할 객체
	    File fileToSave = new File(fileDir, storedFileName);
	    multipartFile.transferTo(fileToSave);

	    Map<String, String> result = new HashMap<>();
	    result.put("storedFileName", storedFileName);
	    result.put("originalFileName", originalFileName);

	    return result;
	}
	
	
	
}
