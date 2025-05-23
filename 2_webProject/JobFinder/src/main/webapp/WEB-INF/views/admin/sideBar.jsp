  <%@ page contentType="text/html;charset=UTF-8" language="java" %>
  
  <div class="flex-shrink-0 p-3" style="width: 280px; height:100vh; border-right: 1px solid black;">
    <a href="#" class="d-flex align-items-center pb-3 mb-3 link-body-emphasis text-decoration-none border-bottom">
      <span class="fs-5 fw-semibold">관리자 화면</span>
    </a>
    <ul class="list-unstyled ps-0">
      <li class="mb-1">
        <a class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed" href="/admin/main">
          Home
        </a>
      
      </li>
      <li class="mb-1">
        <button class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed" data-bs-toggle="collapse" data-bs-target="#member-collapse" aria-expanded="false">
          회원관리
        </button>
        <div class="collapse" id="member-collapse">
          <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
            <li><a href="/admin/member/individual" class="link-body-emphasis d-inline-flex text-decoration-none rounded">일반회원</a></li>
            <li><a href="/admin/member/company" class="link-body-emphasis d-inline-flex text-decoration-none rounded">기업회원</a></li>
            <!-- <li><a href="/admin/admin" class="link-body-emphasis d-inline-flex text-decoration-none rounded">관리자</a></li> -->
          </ul>
        </div>
      </li>
      <li class="mb-1">
        <a href="/admin/recruitment" class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed">
          공고관리
        </a>
      </li>
            <li class="mb-1">
        <button class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed" data-bs-toggle="collapse" data-bs-target="#community-collapse" aria-expanded="false">
          커뮤니티관리
        </button>
        <div class="collapse" id="community-collapse">
          <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
            <li><a href="/admin/community/post" class="link-body-emphasis d-inline-flex text-decoration-none rounded">게시판 관리</a></li>
            <li><a href="/admin/community/comment" class="link-body-emphasis d-inline-flex text-decoration-none rounded">댓글 관리</a></li></ul>
        </div>
      </li>
      <li class="mb-1">
        <button class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed" data-bs-toggle="collapse" data-bs-target="#job-collapse" aria-expanded="false">
          직군/직무관리
        </button>
        <div class="collapse" id="job-collapse">
          <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
            <li><a href="/admin/job/jobGroup" class="link-body-emphasis d-inline-flex text-decoration-none rounded">직군관리</a></li>
            <li><a href="/admin/job/job" class="link-body-emphasis d-inline-flex text-decoration-none rounded">직무관리</a></li>
          </ul>
        </div>
      </li>
      <li class="mb-1">
        <a href="/admin/skill" class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed">
          스킬관리
        </a>
      </li>
      <li class="mb-1">
<<<<<<< HEAD
        <a href="/admin/chat/rooms" class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed">
=======
<<<<<<< HEAD
        <a href="/admin/chat" class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed">
=======
        <a href="/admin/chat/view" class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed">
>>>>>>> origin/owr
>>>>>>> origin/jhs
          채팅관리
        </a>
      </li>
      <li class="border-top my-3"></li>
    </ul>
    <a href="/admin/logout" class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed">로그아웃</a>
    <!-- 확인용 -->
  </div>