package com.joblessfriend.jobfinder.school.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joblessfriend.jobfinder.school.domain.SchoolInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Component
public class SchoolOpenApiParser {
	
	// application.properties에서 인증키 받음
    @Value("${api.university.key}")
    private String apiKey;

    // 대학교 목록을 외부 API로부터 가져오는 메소드
    public List<SchoolInfo> fetchUniversitiesByKeyword(String keyword, String schoolType) {
        List<SchoolInfo> result = new ArrayList<>();

        try {
        	// schoolType 값에 따라 sch1 코드값 설정
        	// "univ2" > 100323 (4년제) , 100322 (2,3년제)
            String sch1Code = schoolType.equals("univ2") ? "100322" : "100323";

         // 외부 API URL 구성
            String apiUrl = "https://www.career.go.kr/cnet/openapi/getOpenApi?" +
                    "apiKey=" + apiKey +
                    "&svcType=api" +
                    "&svcCode=SCHOOL" +
                    "&contentType=json" +
                    "&gubun=univ_list" +
                    "&thisPage=1" +
                    "&perPage=500" +
                    "&sch1=" + sch1Code;  // ← 여기서 동적으로 들어감
            
            
            // API 호출
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // 응답 읽기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder responseBuilder = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                responseBuilder.append(line);
            }

            // JSON 파싱
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(responseBuilder.toString());

            // JSON 구조에서 content 배열 추출
            JsonNode contentArray = root.path("dataSearch").path("content");

            for (JsonNode item : contentArray) {
            	// 학교명과 주소 추출
                String name = item.path("schoolName").asText();
                String address = item.path("adres").asText();
                String typeFromApi = item.path("schoolType").asText();

                System.out.println("name=" + name + ", typeFromApi=" + typeFromApi);

                // 검색어가 포함된 학교만 필터링
                 if (name.contains(keyword) && 
                    typeFromApi.equals(schoolType.equals("univ2") ? "전문대학" : "대학교")) {
	                    SchoolInfo info = new SchoolInfo();
	                    info.setSchoolName(name); // 학교명
	                    info.setAddress(address);
	                    info.setKindName(typeFromApi); // "대학교" or "전문대학"
	                    result.add(info);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}

