package com.joblessfriend.jobfinder.school.util;

import com.joblessfriend.jobfinder.school.domain.SchoolInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Component
public class SchoolOpenApiParser {

    @Value("${api.university.key}")
    private String serviceKey;

    public List<SchoolInfo> fetchUniversitiesByKeyword(String keyword, String schoolType) {
        List<SchoolInfo> result = new ArrayList<>();
        try {
            String requestUrl = "http://openapi.academyinfo.go.kr/openapi/service/rest/SchoolInfoService/getSchoolInfo" +
                    "?serviceKey=" + serviceKey +
                    "&schlKrnNm=" + URLEncoder.encode(keyword, "UTF-8") +
                    "&svyYr=2023" +
                    "&numOfRows=100";

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(requestUrl);
            NodeList nodeList = doc.getElementsByTagName("item");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element el = (Element) nodeList.item(i);

                String name = getTagValue("schlNm", el);
                String kind = getTagValue("schlKndNm", el);
                String address = getTagValue("postNoAdrs", el); // 소재지 도로명 주소

                if ((schoolType.equals("univ4") && "대학".equals(kind)) ||
                    (schoolType.equals("univ2") && "전문대학".equals(kind))) {

                    SchoolInfo info = new SchoolInfo();
                    info.setSchoolName(name);
                    info.setKindName(kind);
                    info.setAddress(address);
                    result.add(info);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);
        if (nodeList.getLength() == 0) return "";
        return nodeList.item(0).getTextContent();
    }
}
