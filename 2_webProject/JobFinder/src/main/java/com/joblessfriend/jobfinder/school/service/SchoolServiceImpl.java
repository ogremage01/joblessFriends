package com.joblessfriend.jobfinder.school.service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.joblessfriend.jobfinder.school.dao.SchoolDao;
import com.joblessfriend.jobfinder.school.domain.SchoolInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SchoolServiceImpl implements SchoolService{
	
	
	@Value("${api.school.key}")
	private String serviceKey;

	@Override
	public List<SchoolInfo> searchSchools(String keyword) {
		// TODO Auto-generated method stub
		List<SchoolInfo> resultList = new ArrayList<>();

        try {
            String urlStr = "https://www.safemap.go.kr/openApiService/data/getSchoolData.do"
                    + "?serviceKey=" + serviceKey
                    + "&searchKeyWord=" + URLEncoder.encode(keyword, "UTF-8")
                    + "&format=xml"; // json 미지원이므로 xml 사용

            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(new InputSource(conn.getInputStream()));
            doc.getDocumentElement().normalize();

            NodeList items = doc.getElementsByTagName("item");

            for (int i = 0; i < items.getLength(); i++) {
                Node node = items.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element e = (Element) node;

                    SchoolInfo info = new SchoolInfo();
                    info.setFCLTYNM(getTagValue("FCLTYNM", e));
                    info.setLNMADDR(getTagValue("LNMDADR", e));
                    resultList.add(info);
                }
            }

        } catch (Exception e) {
            log.error("학교 정보 조회 실패", e);
        }

        return resultList;
    }

    private String getTagValue(String tag, Element e) {
        NodeList nodeList = e.getElementsByTagName(tag);
        if (nodeList.getLength() > 0 && nodeList.item(0).getFirstChild() != null) {
            return nodeList.item(0).getFirstChild().getNodeValue();
        } else {
            return "";
        }
    }
}
