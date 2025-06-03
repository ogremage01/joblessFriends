<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/css/common/mypageSidebar.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body>

	<div class="sidebar">
	
		<h2>
			<span style="text-decoration: none; color: inherit; user-select:none;">마이페이지</span>
		</h2>
	
		<ul>
			<li>
				<a href="/resume/management" style="text-decoration: none;">
					<i class="bi bi-file-text"></i> 이력서 관리
				</a>
			</li>
			<li>
				<a href="/member/application"><i class="bi bi-briefcase"></i> 구직활동 내역</a>
			</li>
			<li>
				<a href="/member/info" style="text-decoration: none;">
					<i class="bi bi-person-gear"></i> 개인정보 관리
				</a>
			</li>
			<li>
				<a href="/member/bookmark" style="text-decoration: none;">
					<i class="bi bi-star"></i> 내가 찜한 공고
				</a>
			</li>
		</ul>
		
	</div>

<script>
// 현재 페이지 하이라이트
document.addEventListener('DOMContentLoaded', function() {
    const currentPath = window.location.pathname;
    const navLinks = document.querySelectorAll('.sidebar a');
    
    // 모든 li에서 current-page 클래스 제거
    document.querySelectorAll('.sidebar li').forEach(li => {
        li.classList.remove('current-page');
    });
    
    navLinks.forEach(link => {
        if (link.getAttribute('href') === currentPath) {
            // 현재 페이지에 해당하는 li에 current-page 클래스 추가
            link.closest('li').classList.add('current-page');
        }
    });
});
</script>
	
</body>
</html>