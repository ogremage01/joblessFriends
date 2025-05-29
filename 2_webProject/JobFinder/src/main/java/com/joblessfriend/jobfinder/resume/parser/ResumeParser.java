package com.joblessfriend.jobfinder.resume.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joblessfriend.jobfinder.resume.domain.CareerVo;
import com.joblessfriend.jobfinder.resume.domain.CertificateVo;
import com.joblessfriend.jobfinder.resume.domain.EducationVo;
import com.joblessfriend.jobfinder.resume.domain.PortfolioVo;
import com.joblessfriend.jobfinder.resume.domain.ResumeVo;
import com.joblessfriend.jobfinder.resume.domain.SchoolVo;

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
    public ResumeVo parseJsonToResumeVo(String jsonString, int memberId) throws Exception {
        Map<String, Object> jsonMap = objectMapper.readValue(jsonString, Map.class);
        return parseMapToResumeVo(jsonMap, memberId);
    }

    /**
     * Map 객체를 ResumeVo 객체로 변환
     */
    @SuppressWarnings("unchecked")
    public ResumeVo parseMapToResumeVo(Map<String, Object> requestMap, int memberId) throws Exception {
        ResumeVo resumeVo = new ResumeVo();

        // 기본 정보 파싱
        resumeVo.setResumeId(getIntValue(requestMap, "resumeId"));
        resumeVo.setMemberName(getStringValue(requestMap, "name"));
        resumeVo.setBirthDate(parseDate(getStringValue(requestMap, "birthdate")));
        resumeVo.setPhoneNumber(getStringValue(requestMap, "phoneNumber"));
        resumeVo.setEmail(getStringValue(requestMap, "email"));
        resumeVo.setAddress(getStringValue(requestMap, "address"));
        resumeVo.setSelfIntroduction(getStringValue(requestMap, "selfIntroduction"));
        resumeVo.setProfile(getStringValue(requestMap, "profile"));
        resumeVo.setMemberId(memberId);
        resumeVo.setPostalCodeId(getIntValue(requestMap, "postalCodeId"));
        resumeVo.setJobGroupId(getIntValue(requestMap, "jobGroupId"));
        resumeVo.setJobId(getIntValue(requestMap, "jobId"));

        // 현재 시간으로 생성/수정 날짜 설정
        Date now = new Date();
        resumeVo.setCreateDate(now);
        resumeVo.setModifyDate(now);

        // 리스트 객체들 파싱
        resumeVo.setSchoolList(parseSchoolList((List<Map<String, Object>>) requestMap.get("schools")));
        resumeVo.setCareerList(parseCareerList((List<Map<String, Object>>) requestMap.get("careers")));
        resumeVo.setEducationList(parseEducationList((List<Map<String, Object>>) requestMap.get("educations")));
        resumeVo.setCertificateList(parseCertificateList((List<Integer>) requestMap.get("certificateIds")));
        resumeVo.setPortfolioList(parsePortfolioList((List<Map<String, Object>>) requestMap.get("portfolios")));

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
                schoolVo.setStartDate(getStringValue(schoolData, "startDate"));
                schoolVo.setEndDate(getStringValue(schoolData, "endDate"));
                
                schoolList.add(schoolVo);
            }
        }
        
        return schoolList;
    }

    /**
     * 경력 리스트 파싱
     */
    private List<CareerVo> parseCareerList(List<Map<String, Object>> careerDataList) {
        List<CareerVo> careerList = new ArrayList<>();
        
        if (careerDataList != null) {
            for (Map<String, Object> careerData : careerDataList) {
                CareerVo careerVo = new CareerVo();
                careerVo.setCompanyName(getStringValue(careerData, "companyName"));
                careerVo.setDepartmentName(getStringValue(careerData, "departmentName"));
                careerVo.setHireYm(getStringValue(careerData, "hireYm"));
                careerVo.setResignYm(getStringValue(careerData, "resignYm"));
                careerVo.setPosition(getStringValue(careerData, "position"));
                careerVo.setJobTitle(getStringValue(careerData, "jobTitle"));
                careerVo.setTaskRole(getStringValue(careerData, "taskRole"));
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
    private List<EducationVo> parseEducationList(List<Map<String, Object>> educationDataList) throws ParseException {
        List<EducationVo> educationList = new ArrayList<>();
        
        if (educationDataList != null) {
            for (Map<String, Object> educationData : educationDataList) {
                EducationVo educationVo = new EducationVo();
                educationVo.setEduName(getStringValue(educationData, "eduName"));
                educationVo.setEduInstitution(getStringValue(educationData, "eduInstitution"));
                
                // 날짜 파싱 (yyyy.MM 형식)
                String startDateStr = getStringValue(educationData, "startDate");
                String endDateStr = getStringValue(educationData, "endDate");
                
                if (startDateStr != null && !startDateStr.isEmpty()) {
                    educationVo.setStartDate(yearMonthFormat.parse(startDateStr));
                }
                if (endDateStr != null && !endDateStr.isEmpty()) {
                    educationVo.setEndDate(yearMonthFormat.parse(endDateStr));
                }
                
                educationList.add(educationVo);
            }
        }
        
        return educationList;
    }

    /**
     * 자격증 리스트 파싱 (ID 리스트를 받아서 CertificateVo 객체 생성)
     */
    private List<CertificateVo> parseCertificateList(List<Integer> certificateIds) {
        List<CertificateVo> certificateList = new ArrayList<>();
        
        if (certificateIds != null) {
            for (Integer certificateId : certificateIds) {
                if (certificateId != null && certificateId > 0) {
                    CertificateVo certificateVo = new CertificateVo();
                    certificateVo.setCertificateId(certificateId);
                    certificateList.add(certificateVo);
                }
            }
        }
        
        return certificateList;
    }

    /**
     * 포트폴리오 리스트 파싱
     */
    private List<PortfolioVo> parsePortfolioList(List<Map<String, Object>> portfolioDataList) {
        List<PortfolioVo> portfolioList = new ArrayList<>();
        
        if (portfolioDataList != null) {
            for (Map<String, Object> portfolioData : portfolioDataList) {
                PortfolioVo portfolioVo = new PortfolioVo();
                portfolioVo.setFileName(getStringValue(portfolioData, "fileName"));
                portfolioVo.setStoredFileName(getStringValue(portfolioData, "storedFileName"));
                portfolioVo.setFileExtension(getStringValue(portfolioData, "fileExtension"));
                
                // 현재 시간으로 생성/수정 시간 설정
                Date now = new Date();
                portfolioVo.setCreateAt(now);
                portfolioVo.setModifiedAt(now);
                
                portfolioList.add(portfolioVo);
            }
        }
        
        return portfolioList;
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
    private Date parseDate(String dateString) throws ParseException {
        if (dateString == null || dateString.trim().isEmpty()) {
            return null;
        }
        return dateFormat.parse(dateString.trim());
    }

    /**
     * ResumeVo 객체를 JSON 문자열로 변환
     */
    public String parseResumeVoToJson(ResumeVo resumeVo) throws Exception {
        return objectMapper.writeValueAsString(resumeVo);
    }
} 