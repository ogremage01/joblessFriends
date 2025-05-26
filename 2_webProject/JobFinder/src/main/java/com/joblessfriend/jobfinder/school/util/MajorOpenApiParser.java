package com.joblessfriend.jobfinder.school.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joblessfriend.jobfinder.school.domain.MajorInfo;

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
public class MajorOpenApiParser {

    @Value("${api.university.key}")
    private String apiKey;

    public List<MajorInfo> fetchMajorsByKeyword(String keyword) {
        List<MajorInfo> result = new ArrayList<>();

        try {
            String apiUrl = "https://www.career.go.kr/cnet/openapi/getOpenApi?" +
                    "apiKey=" + apiKey +
                    "&svcType=api" +
                    "&svcCode=MAJOR" +
                    "&contentType=json" +
                    "&gubun=univ_list" +
                    "&univSe=univ" +
                    "&thisPage=1" +
                    "&perPage=500" +
                    "&searchTitle=" + URLEncoder.encode(keyword, "UTF-8");

            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(sb.toString());
            JsonNode contentArray = root.path("dataSearch").path("content");

            for (JsonNode item : contentArray) {
            	System.out.println("🎓 major name: " + item.path("mClass").asText());
            	
            	String name = item.path("mClass").asText(); // 학과명
            	
            	System.out.println("🎓 major name: " + name);
            	
            	if (name.contains(keyword)) {
                MajorInfo info = new MajorInfo();
                info.setMajorName(item.path("mClass").asText());
                info.setSummary(item.path("summary").asText());
                info.setLClass(item.path("lClass").asText());
                info.setMajorSeq(item.path("majorSeq").asText());
                result.add(info);
            	}
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}