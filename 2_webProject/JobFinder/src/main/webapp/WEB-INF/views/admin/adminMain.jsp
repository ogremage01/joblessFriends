<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
    <title>adminMain</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/css/bootstrap.min.css" rel="stylesheet" 
      crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" 
	integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.min.js" 
	integrity="sha384-VQqxDN0EQCkWoxt/0vsQvZswzTHUVOImccYmSyhJTp7kGtPed0Qcx8rK9h9YEgx+" crossorigin="anonymous"></script>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">

    <style>
        /*기본값(default)이 이미 "text/css"로 되어 있어서 자동인식한다하여 뺐음 */
    </style>
</head>
<body>
<main class="d-flex flex-nowrap">

  <div class="d-flex flex-column flex-shrink-0 p-3 text-bg-dark" style="width: 280px; height: 100vh">
    <a href="/admin/logout" class="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-white text-decoration-none">
      <i class="bi pe-none me-2" width="40" height="32" aria-hidden="true"></i>
      <span class="fs-4">로그아웃</span>
    </a>
    <hr>
    <ul class="nav nav-pills flex-column mb-auto">
      <li class="nav-item">
        <a href="#" class="nav-link active" aria-current="page">
          메인
        </a>
      </li>
      <li>
        <a href="#" class="nav-link text-white">
          회원관리
        </a>
      </li>
      <li>
        <a href="#" class="nav-link text-white">
          공고관리
        </a>
      </li>
      <li>
        <a href="#" class="nav-link text-white">
          게시판관리
        </a>
      </li>
      <li>
        <a href="#" class="nav-link text-white">
          스킬관리
        </a>
      </li>
       <li>
        <a href="#" class="nav-link text-white">
          채팅관리
        </a>
      </li>
       <li>
        <a href="#" class="nav-link text-white">
          관리자관리
        </a>
      </li>
       <li>
        <a href="#" class="nav-link text-white">
          직무/직군관리
        </a>
      </li>
    </ul>
    <hr>
    <div class="adminName">

        <strong>관리자명이 들어갈 부분</strong>


    </div>
  </div>
</main>

</body>
</html>