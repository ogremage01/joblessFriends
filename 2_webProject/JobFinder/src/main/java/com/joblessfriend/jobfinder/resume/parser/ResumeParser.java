package com.joblessfriend.jobfinder.resume.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joblessfriend.jobfinder.resume.domain.CareerVo;
import com.joblessfriend.jobfinder.resume.domain.CertificateResumeVo;
import com.joblessfriend.jobfinder.resume.domain.EducationVo;
import com.joblessfriend.jobfinder.resume.domain.PortfolioVo;
import com.joblessfriend.jobfinder.resume.domain.ResumeVo;
import com.joblessfriend.jobfinder.resume.domain.SchoolVo;
import com.joblessfriend.jobfinder.skill.domain.SkillVo;

@Component
public class ResumeParser {

    private final ObjectMapper objectMapper;
    private final SimpleDateFormat dateFormat;
    private final SimpleDateFormat yearMonthFormat;

    public ResumeParser() {
        this.objectMapper = new ObjectMapper();
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.yearMonthFormat = new SimpleDateFormat("yyyy.MM");
    }

    /**
     * JSON 문자열을 ResumeVo 객체로 변환
     */
    public ResumeVo parseJsonToResumeVo(String jsonString, int memberId) {
        try {
            Map<String, Object> jsonMap = objectMapper.readValue(jsonString, Map.class);
            return parseMapToResumeVo(jsonMap, memberId);
        } catch (Exception e) {
            System.err.println("JSON 파싱 오류: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Map 객체를 ResumeVo 객체로 변환
     */
    @SuppressWarnings("unchecked")
    public ResumeVo parseMapToResumeVo(Map<String, Object> requestMap, int memberId) {
        ResumeVo resumeVo = new ResumeVo();

        // 기본 정보 파싱
        resumeVo.setResumeId(getIntValue(requestMap, "resumeId"));
        
        System.out.println(">>>> requestMap.get(\"resumeId\"): " + requestMap.get("resumeId"));
        System.out.println(">>>> 파싱된 resumeId: " + resumeVo.getResumeId());
        
        resumeVo.setTitle(getStringValue(requestMap, "title"));
        resumeVo.setMemberName(getStringValue(requestMap, "name"));
        resumeVo.setBirthDate(parseDate(getStringValue(requestMap, "birthdate")));
        resumeVo.setPhoneNumber(getStringValue(requestMap, "phoneNumber"));
        resumeVo.setEmail(getStringValue(requestMap, "email"));
        resumeVo.setAddress(getStringValue(requestMap, "address"));
        resumeVo.setSelfIntroduction(getStringValue(requestMap, "selfIntroduction"));
        resumeVo.setProfile(getStringValue(requestMap, "profile"));
        resumeVo.setMemberId(memberId);
        resumeVo.setPostalCodeId(getIntValue(requestMap, "postalCodeId"));
        //resumeVo.setJobGroupId(getIntValue(requestMap, "jobGroupId"));// deprecated.
        //resumeVo.setJobId(getIntValue(requestMap, "jobId"));// deprecated.

        // 현재 시간으로 생성/수정 날짜 설정
        Date now = new Date();
        resumeVo.setCreateDate(now);
        resumeVo.setModifyDate(now);

        // 리스트 객체들 파싱
        System.out.println(">>> [parseMapToResumeVo] requestMap 키 목록: " + requestMap.keySet());
        System.out.println(">>> [parseMapToResumeVo] portfolios 데이터: " + requestMap.get("portfolios"));
        
        resumeVo.setSchoolList(parseSchoolList((List<Map<String, Object>>) requestMap.get("schools")));
        resumeVo.setCareerList(parseCareerList((List<Map<String, Object>>) requestMap.get("careers")));
        resumeVo.setEducationList(parseEducationList((List<Map<String, Object>>) requestMap.get("educations")));
        resumeVo.setCertificateList(parseCertificateList((List<Map<String, Object>>) requestMap.get("certificates")));
        resumeVo.setPortfolioList(parsePortfolioList((List<Map<String, Object>>) requestMap.get("portfolios")));
        resumeVo.setSkillList(parseSkillList((List<Object>) requestMap.get("tagIds")));

        return resumeVo;
    }

    /**
     * Map 객체를 ResumeVo 객체로 변환 (세션 데이터 포함)
     */
    @SuppressWarnings("unchecked")
    public ResumeVo parseMapToResumeVo(Map<String, Object> requestMap, int memberId, HttpSession session) {
        System.out.println(">>> [parseMapToResumeVo with session] 세션 포함 파싱 시작");
        
        // 기본 파싱
        ResumeVo resumeVo = parseMapToResumeVo(requestMap, memberId);
        
        // 세션에서 포트폴리오 데이터 가져오기
        if (session != null) {
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> sessionPortfolios = (List<Map<String, Object>>) session.getAttribute("portfolioFiles");
            
            System.out.println(">>> [parseMapToResumeVo with session] 세션 포트폴리오 데이터: " + sessionPortfolios);
            
            if (sessionPortfolios != null && !sessionPortfolios.isEmpty()) {
                List<PortfolioVo> portfolioList = parsePortfolioList(sessionPortfolios);
                resumeVo.setPortfolioList(portfolioList);
                System.out.println(">>> [parseMapToResumeVo with session] 세션에서 포트폴리오 " + portfolioList.size() + "개 추가됨");
            } else {
                System.out.println(">>> [parseMapToResumeVo with session] 세션에 포트폴리오 데이터가 없음");
            }
        } else {
            System.out.println(">>> [parseMapToResumeVo with session] HttpSession이 null임");
        }
        
        return resumeVo;
    }

    /**
     * 학력 리스트 파싱
     */
    private List<SchoolVo> parseSchoolList(List<Map<String, Object>> schoolDataList) {
        List<SchoolVo> schoolList = new ArrayList<>();
        
        if (schoolDataList != null) {
            for (Map<String, Object> schoolData : schoolDataList) {
                SchoolVo schoolVo = new SchoolVo();
                schoolVo.setSortation(getStringValue(schoolData, "sortation"));
                schoolVo.setSchoolName(getStringValue(schoolData, "schoolName"));
                schoolVo.setYearOfGraduation(getStringValue(schoolData, "yearOfGraduation"));
                schoolVo.setStatus(getStringValue(schoolData, "status"));
                schoolVo.setMajorName(getStringValue(schoolData, "majorName"));
                schoolVo.setStartDate(parseYearMonthDate(getStringValue(schoolData, "startDate")));
                schoolVo.setEndDate(parseYearMonthDate(getStringValue(schoolData, "endDate")));	
                
                schoolList.add(schoolVo);
            }
        }
        
        return schoolList;
    }

    /**
     * 경력 리스트 파싱
     */
    private List<CareerVo> parseCareerList(List<Map<String, Object>> careerDataList) {
    	System.out.println(">>>>> careerDataList: " + careerDataList);
        List<CareerVo> careerList = new ArrayList<>();
        
        if (careerDataList != null) {
            for (Map<String, Object> careerData : careerDataList) {
                CareerVo careerVo = new CareerVo();
                careerVo.setCompanyName(getStringValue(careerData, "companyName"));
                System.out.println(">>>>> companyName: " + careerVo.getCompanyName());
                careerVo.setDepartmentName(getStringValue(careerData, "departmentName"));
                careerVo.setHireYm(parseYearMonthDate(getStringValue(careerData, "hireYm")));
                careerVo.setResignYm(parseYearMonthDate(getStringValue(careerData, "resignYm")));
                careerVo.setPosition(getStringValue(careerData, "position"));
                careerVo.setJobGroupId(getIntValue(careerData, "jobGroupId"));
                careerVo.setJobId(getIntValue(careerData, "jobId"));
                careerVo.setWorkDescription(getStringValue(careerData, "workDescription"));
                careerVo.setSalary(getStringValue(careerData, "salary"));
                
                careerList.add(careerVo);
            }
        }
        
        return careerList;
    }

    /**
     * 교육 리스트 파싱
     */
    private List<EducationVo> parseEducationList(List<Map<String, Object>> educationDataList) {
        List<EducationVo> educationList = new ArrayList<>();
        
        if (educationDataList != null) {
            for (Map<String, Object> educationData : educationDataList) {
                EducationVo educationVo = new EducationVo();
                educationVo.setEduName(getStringValue(educationData, "eduName"));
                educationVo.setEduInstitution(getStringValue(educationData, "eduInstitution"));
                educationVo.setContent(getStringValue(educationData, "content"));
                
                // 날짜 파싱 (yyyy.MM 형식) - 안전한 파싱
                String startDateStr = getStringValue(educationData, "startDate");
                String endDateStr = getStringValue(educationData, "endDate");
                
                educationVo.setStartDate(parseYearMonthDate(startDateStr));
                educationVo.setEndDate(parseYearMonthDate(endDateStr));
                
                educationList.add(educationVo);
            }
        }
        
        return educationList;
    }

    /**
     * 자격증 리스트 파싱 (자격증 데이터를 받아서 CertificateResumeVo 객체 생성)
     */
    private List<CertificateResumeVo> parseCertificateList(List<Map<String, Object>> certificateDataList) {
        List<CertificateResumeVo> certificateList = new ArrayList<>();
        
        if (certificateDataList != null) {
            for (Map<String, Object> certificateData : certificateDataList) {
                CertificateResumeVo certificateVo = new CertificateResumeVo();
                certificateVo.setCertificateName(getStringValue(certificateData, "certificateName"));
                certificateVo.setIssuingAuthority(getStringValue(certificateData, "issuingAuthority"));
                certificateVo.setAcquisitionDate(parseYearMonthDate(getStringValue(certificateData, "acquisitionDate")));
                certificateList.add(certificateVo);
            }
        }
        
        return certificateList;
    }

    /**
     * 포트폴리오 리스트 파싱
     */
    private List<PortfolioVo> parsePortfolioList(List<Map<String, Object>> portfolioDataList) {
        System.out.println(">>> [parsePortfolioList] 포트폴리오 파싱 시작");
        System.out.println(">>> [parsePortfolioList] portfolioDataList: " + portfolioDataList);
        
        List<PortfolioVo> portfolioList = new ArrayList<>();
        
        if (portfolioDataList != null) {
            System.out.println(">>> [parsePortfolioList] portfolioDataList 크기: " + portfolioDataList.size());
            
            for (int i = 0; i < portfolioDataList.size(); i++) {
                Map<String, Object> portfolioData = portfolioDataList.get(i);
                System.out.println(">>> [parsePortfolioList] Portfolio[" + i + "] 원본 데이터: " + portfolioData);
                
                PortfolioVo portfolioVo = new PortfolioVo();
                portfolioVo.setFileName(getStringValue(portfolioData, "fileName"));
                portfolioVo.setStoredFileName(getStringValue(portfolioData, "storedFileName"));
                portfolioVo.setFileExtension(getStringValue(portfolioData, "fileExtension"));
                
                System.out.println(">>> [parsePortfolioList] Portfolio[" + i + "] 파싱된 데이터: fileName=" + 
                    portfolioVo.getFileName() + ", storedFileName=" + portfolioVo.getStoredFileName() + 
                    ", fileExtension=" + portfolioVo.getFileExtension());
                
                // 현재 시간으로 생성/수정 시간 설정
                Date now = new Date();
                portfolioVo.setCreateAt(now);
                portfolioVo.setModifiedAt(now);
                
                portfolioList.add(portfolioVo);
            }
        } else {
            System.out.println(">>> [parsePortfolioList] portfolioDataList가 null입니다");
        }
        
        System.out.println(">>> [parsePortfolioList] 최종 portfolioList 크기: " + portfolioList.size());
        return portfolioList;
    }

    /**
     * 스킬 리스트 파싱
     */
    private List<SkillVo> parseSkillList(List<Object> tagIdList) {
        List<SkillVo> skillList = new ArrayList<>();
        
        if (tagIdList != null) {
            for (Object tagIdObj : tagIdList) {
                if (tagIdObj != null) {
                    try {
                        int tagId;
                        if (tagIdObj instanceof Number) {
                            tagId = ((Number) tagIdObj).intValue();
                        } else {
                            tagId = Integer.parseInt(tagIdObj.toString());
                        }
                        
                        SkillVo skill = new SkillVo();
                        skill.setTagId(tagId);
                        skillList.add(skill);
                    } catch (NumberFormatException e) {
                        System.err.println("태그 ID 파싱 오류: " + tagIdObj);
                    }
                }
            }
        }
        
        return skillList;
    }

    /**
     * Map에서 String 값을 안전하게 추출
     */
    private String getStringValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        return value != null ? value.toString().trim() : null;
    }

    /**
     * Map에서 int 값을 안전하게 추출
     */
    private int getIntValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null) return 0;
        
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        
        try {
            return Integer.parseInt(value.toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * 문자열 날짜를 Date 객체로 변환 (yyyy-MM-dd 형식)
     */
    private Date parseDate(String dateString) {
        if (dateString == null || dateString.trim().isEmpty()) {
            return null;
        }
        try {
            return dateFormat.parse(dateString.trim());
        } catch (ParseException e) {
            System.err.println("날짜 파싱 오류 (yyyy-MM-dd): " + dateString + " - " + e.getMessage());
            return null;
        }
    }

    /**
     * 년월 형식 문자열을 Date 객체로 변환 (yyyy.MM 형식)
     */
    private Date parseYearMonthDate(String dateString) {
        if (dateString == null || dateString.trim().isEmpty()) {
            return null;
        }
        try {
            return yearMonthFormat.parse(dateString.trim());
        } catch (ParseException e) {
            System.err.println("날짜 파싱 오류 (yyyy.MM): " + dateString + " - " + e.getMessage());
            return null;
        }
    }

    /**
     * ResumeVo 객체를 JSON 문자열로 변환
     */
    public String parseResumeVoToJson(ResumeVo resumeVo) throws Exception {
        return objectMapper.writeValueAsString(resumeVo);
    }
    

    
} 