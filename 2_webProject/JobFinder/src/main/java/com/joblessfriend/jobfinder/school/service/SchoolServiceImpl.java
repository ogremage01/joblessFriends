package com.joblessfriend.jobfinder.school.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joblessfriend.jobfinder.school.domain.SchoolInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SchoolServiceImpl implements SchoolService {

    @Value("${api.school.key}")
    private String serviceKey;

    // SSL 인증 무시 설정 (HTTPS 요청 시 인증서 경고 회피용)
    private void setTrustAllCerts() throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) { }

                public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) { }
            }
        };

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
    }

    /*
     * 기본 API 호출 (연도/학교급 고정: 2024, 고등학교)
     */
    @Override
    public List<SchoolInfo> searchSchools(String keyword) {
        // 하드코딩된 연도와 학교코드로 호출 위임
        return searchSchools(keyword, 2024, "04");
    }

    /*
     * API 호출 (연도 및 학교급 파라미터 사용)
     */
    @Override
    public List<SchoolInfo> searchSchools(String keyword, int year, String schulKndCode) {
        List<SchoolInfo> resultList = new ArrayList<>();

        try {
            setTrustAllCerts(); // SSL 인증 무시 설정

            // API 요청 URL 구성
            String urlStr = "https://www.schoolinfo.go.kr/openApi.do"
                    + "?apiKey=" + URLEncoder.encode(serviceKey.trim(), "UTF-8")
                    + "&apiType=62"
                    + "&pbanYr=" + year
                    + "&schulKndCode=" + schulKndCode;

            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET"); // GET 방식 요청
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            conn.setRequestProperty("Accept", "application/json");

            // 응답 데이터 읽기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            // 디버깅 로그 출력
            System.out.println("🔍 최종 요청 URL: " + urlStr);
            System.out.println("🔐 API KEY 확인: " + serviceKey);
            System.out.println("응답 길이: " + sb.length());

            // JSON 파싱
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(sb.toString());
            JsonNode rowList = root.path("list");

            // 필요한 정보만 추출하여 리스트에 저장
            for (JsonNode node : rowList) {
            	String schoolName = node.path("SCHUL_NM").asText();
            	
            	// 입력한 키워드를 포함한 학교명만 추가
            	if (schoolName.contains(keyword)) {
                    SchoolInfo info = new SchoolInfo();
                    info.setSchoolName(schoolName);
                    resultList.add(info);
                }
            }

        } catch (Exception e) {
            log.error("학교알리미 API 호출 실패", e);
        }

        return resultList;
    }
}
