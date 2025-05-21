<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공고별 지원자 관리</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/css/bootstrap.min.css"
	rel="stylesheet" crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
	integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.min.js"
	integrity="sha384-VQqxDN0EQCkWoxt/0vsQvZswzTHUVOImccYmSyhJTp7kGtPed0Qcx8rK9h9YEgx+"
	crossorigin="anonymous"></script>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css"
	rel="stylesheet">


<link href="/css/company/applicantsList.css" rel="stylesheet">

</head>
<body>
<jsp:include page="../../common/header.jsp"/>



<div class="container">
    <div class="sideBar">
		<jsp:include page="../companyPage/companyPageSidebar.jsp"/>
    </div>
	<!-- 메인 -->
  <div class="main" style="width: 800px;">
  
  <h1>지원자 관리</h1>
  

   	<table id="recruitmentList" class="table table-border">
	  <thead id="table-header" class="table-group-divider">
	    <tr>
	      <th>지원자</th>
	      <th>제목</th>
	      <th>지원일자</th>
	      <th>적합도</th>
	      <th>관리</th>
	    </tr>
	  </thead>


    <tbody>
      <tr>
        <td colspan="5">
          <span id="emptyList">(지원자가 없을 때)지원자가 없습니다</span>
        </td>
      </tr>
    </tbody>

    <tbody>
      <tr>
        <td style="text-align: center;">
          <span>지원자</span>
        </td>
        <td style="text-align: center;">
          <span>제목</span>
        </td>
        <td style="text-align: center;">
          <span>지원일자</span>
        </td>
        <td style="text-align: center;">
          <span>적합도</span>
        </td>
         <td style="text-align: center;">
          <span>버튼?</span>
        </td>
      </tr>
    </tbody>


</table>

   
    </div>
    
    
    

</div>
	
<jsp:include page="../../common/footer.jsp" />
</body>




</html>