<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이력서 관리</title>.
<link rel="stylesheet" href="/css/resume/resumeManagementStyle.css" />
</head>
<body>
<jsp:include page="../common/header.jsp"/>

    <div class="container">
    <div class="sidebar">
      <h2>
      	<a href="/member/mypage" style="text-decoration: none; color: inherit;">마이페이지</a>
      </h2>
      <ul>
        <li class="active">이력서 관리</li>
        <li>구직활동 내역</li>
        <li>개인정보 관리</li>
        <li>내가 찜한 공고</li>
      </ul>
    </div>

    <div class="main">
      <h1>이력서 관리</h1>
      <button class="write-btn">이력서 작성하기</button>

      <div class="table-header">
        <div>이력서</div>
        <div>관리</div>
      </div>

      <!-- 반복 이력서 카드 -->
      <div class="resume-card">
      	<div class="resume-content">
	        <div class="resume-title">이력서 제목</div>
	        <div class="resume-meta">
	          <span>경력</span>
	          <span>희망 직무·직업</span>
	        </div>
	        <div class="resume-meta resume-skill">
	          <span>스킬</span>
	        </div>
	        <div class="resume-date">최종수정일 2025.04.15</div>
        </div>
        <!-- 삭제버튼 -->
        <div class="delete-btn"></div>
        
		<!-- 수정버튼 -->
        <div class="resume-actions">
          <button class="edit-btn">수정하기</button>
        </div>
      </div>
      
      <div class="resume-card">
      	<div class="resume-content">
	        <div class="resume-title">이력서 제목</div>
	        <div class="resume-meta">
	          <span>경력</span>
	          <span>희망 직무·직업</span>
	        </div>
	        <div class="resume-meta resume-skill">
	          <span>스킬</span>
	        </div>
	        <div class="resume-date">최종수정일 2025.04.15</div>
        </div>
        <!-- 삭제버튼 -->
        <div class="delete-btn"></div>
        
		<!-- 수정버튼 -->
        <div class="resume-actions">
          <button class="edit-btn">수정하기</button>
        </div>
      </div>
      
      <div class="resume-card">
      	<div class="resume-content">
	        <div class="resume-title">이력서 제목</div>
	        <div class="resume-meta">
	          <span>경력</span>
	          <span>희망 직무·직업</span>
	        </div>
	        <div class="resume-meta resume-skill">
	          <span>스킬</span>
	        </div>
	        <div class="resume-date">최종수정일 2025.04.15</div>
        </div>
        <!-- 삭제버튼 -->
        <div class="delete-btn"></div>
        
		<!-- 수정버튼 -->
        <div class="resume-actions">
          <button class="edit-btn">수정하기</button>
        </div>
      </div>

      <!-- 추가 resume-card 복제 가능 -->
    </div>
  </div>
	
<jsp:include page="../common/footer.jsp" />
</body>
</html>