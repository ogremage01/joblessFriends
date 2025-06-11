<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<style>
/* 사이드바 */
.sidebar {
	width: 180px;
	height: auto;
	border: 1px solid #D8D9E3;
	border-radius: 5px;
	background-color: white;
}

.sidebar h2 {
	font-size: 20px;
	font-weight: bold;
	color: #1B202F;
	border-bottom: 1px solid #D8D9E3;
	margin: 0px auto;
	padding: 15px 30px;
}

.sidebar h2 a {
	text-decoration: none;
	color: inherit;
}

.sidebar ul {
	list-style: none;
	margin: 0;
	padding: 20px 30px;
	display: flex;
	flex-direction: column;
	gap: 10px;
}

.sidebar li {
	font-size: 15px;
	color: #1B202F;
}

.sidebar li a {
	color: inherit;
	text-decoration: none;
}

.sidebar li a:hover {
	color: #F69800;
	text-decoration: none;
}

/* 토글 버튼 */
.toggle-btn {
	background: none;
	border: none;
	color: #1B202F;
	font-size: 15px;
	text-align: left;
	padding: 0;
	cursor: pointer;
	width: 100%;
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.toggle-btn:hover {
	color: #F69800;
}

.toggle-btn[aria-expanded="true"] i.bi-chevron-down {
	transform: rotate(180deg);
}

/* 서브메뉴 */
.submenu {
	margin-left: 15px;
	margin-top: 10px;
	display: flex;
	flex-direction: column;
	gap: 8px;
}

.submenu li {
	font-size: 14px;
	color: #666;
}

.submenu a:hover {
	color: #F69800;
}

/* 현재 페이지 */
.current-page {
	color: #F69800 !important;
	font-weight: bold;
}

/* 안읽은 메시지 배지 */
.unread-badge {
	display: inline-block;
	background-color: #dc3545;
	color: white;
	font-size: 11px;
	font-weight: bold;
	padding: 2px 6px;
	border-radius: 10px;
	margin-left: 8px;
	min-width: 18px;
	text-align: center;
	animation: pulse 2s infinite;
	box-shadow: 0 2px 4px rgba(220, 53, 69, 0.3);
}

.unread-badge:empty {
	display: none;
}

/* 배지 애니메이션 */
@keyframes pulse {
	0% {
		box-shadow: 0 2px 4px rgba(220, 53, 69, 0.3);
	}
	50% {
		box-shadow: 0 2px 8px rgba(220, 53, 69, 0.6);
		transform: scale(1.05);
	}
	100% {
		box-shadow: 0 2px 4px rgba(220, 53, 69, 0.3);
	}
}

/* 채팅 링크에 안읽은 메시지가 있을 때 강조 */
.sidebar li a:has(.unread-badge),
.sidebar li a.has-unread {
	background-color: rgba(220, 53, 69, 0.05);
	border-radius: 4px;
	padding: 4px 8px;
	margin: -4px -8px;
	transition: background-color 0.3s ease;
}

/* 로그아웃 */
.logout-section {
	border-top: 1px solid #D8D9E3;
	padding: 15px 30px;
	margin-top: 20px;
}

.logout-btn {
	background-color: #dc3545;
	color: white;
	border: none;
	padding: 8px 16px;
	border-radius: 3px;
	width: 100%;
	text-decoration: none;
	display: block;
	text-align: center;
	font-size: 14px;
}

.logout-btn:hover {
	background-color: #c82333;
	color: white;
	text-decoration: none;
}

/* Bootstrap collapse 덮어쓰기 */
.collapse {
	transition: none !important;
}

.collapse.show {
	display: block;
}

.collapse:not(.show) {
	display: none;
}

/* 모든 애니메이션 제거 */
.sidebar *, .sidebar *::before, .sidebar *::after {
	transition: none !important;
	animation: none !important;
	transform: none !important;
}
</style>

<div class="sidebar">
  <h2>관리자</h2>
  
  <ul>
    <li>
      <a href="/admin/main">
        <i class="bi bi-house"></i> 대시보드
      </a>
    </li>
    
    <li>
      <button class="toggle-btn" data-bs-toggle="collapse" data-bs-target="#member-collapse" aria-expanded="false">
        <span><i class="bi bi-people"></i> 회원</span>
        <i class="bi bi-chevron-down"></i>
      </button>
      <ul class="collapse submenu" id="member-collapse">
        <li>
          <a href="/admin/member/individual">
            <i class="bi bi-person"></i> 일반
          </a>
        </li>
        <li>
          <a href="/admin/member/company">
            <i class="bi bi-building"></i> 기업
          </a>
        </li>
      </ul>
    </li>
    
    <li>
      <a href="/admin/recruitment">
        <i class="bi bi-briefcase"></i> 공고
      </a>
    </li>
    
    <li>
      <button class="toggle-btn" data-bs-toggle="collapse" data-bs-target="#community-collapse" aria-expanded="false">
        <span><i class="bi bi-chat-square-text"></i> 커뮤니티</span>
        <i class="bi bi-chevron-down"></i>
      </button>
      <ul class="collapse submenu" id="community-collapse">
        <li>
          <a href="/admin/community/post">
            <i class="bi bi-file-text"></i>게시판 
          </a>
        </li>
        <li>
          <a href="/admin/community/comment">
            <i class="bi bi-chat-dots"></i> 댓글 
          </a>
        </li>
        <li>
          <a href="/admin/community/notice">
            <i class="bi bi-megaphone"></i> 공지 
          </a>
        </li>
      </ul>
    </li>
    
    <li>
      <button class="toggle-btn" data-bs-toggle="collapse" data-bs-target="#job-collapse" aria-expanded="false">
        <span><i class="bi bi-diagram-3"></i> 직군/직무</span>
        <i class="bi bi-chevron-down"></i>
      </button>
      <ul class="collapse submenu" id="job-collapse">
        <li>
          <a href="/admin/job/jobGroup">
            <i class="bi bi-collection"></i> 직군
          </a>
        </li>
        <li>
          <a href="/admin/job/singleJob">
            <i class="bi bi-gear"></i> 직무
          </a>
        </li>
      </ul>
    </li>
    
    <li>
      <a href="/admin/skill">
        <i class="bi bi-tools"></i> 스킬
      </a>
    </li>
    
    <li>
      <a href="/admin/chat/view">
        <i class="bi bi-chat"></i> 채팅
        <c:if test="${unreadChatCount != null && unreadChatCount > 0}">
          <span class="unread-badge" id="unreadChatBadge">${unreadChatCount}</span>
        </c:if>
      </a>
    </li>
  </ul>
  
  <div class="logout-section">
    <a href="/admin/logout" class="logout-btn">
      <i class="bi bi-box-arrow-right"></i> 로그아웃
    </a>
  </div>
</div>

<script>
// 현재 페이지 하이라이트
document.addEventListener('DOMContentLoaded', function() {
    const currentPath = window.location.pathname;// 현재 URL 경로
    const navLinks = document.querySelectorAll('.sidebar a');
    
    navLinks.forEach(link => {
        if (currentPath.startsWith(link.getAttribute('href'))) {
            link.classList.add('current-page');// 현재 페이지 링크에 클래스 추가
            
            // 서브메뉴 항목이면 부모 메뉴도 열기
            const parentCollapse = link.closest('.collapse');
            if (parentCollapse) {
                parentCollapse.classList.add('show');
                const toggleBtn = document.querySelector(`[data-bs-target="#${parentCollapse.id}"]`);
                if (toggleBtn) {
                    toggleBtn.setAttribute('aria-expanded', 'true');
                }
            }
        }
    });
    
    // 페이지 로드 시 안읽은 메시지 수 한 번만 업데이트
    updateUnreadChatCount();
});

// 안읽은 채팅 메시지 수 업데이트 함수
function updateUnreadChatCount() {
    fetch('/admin/chat/unreadCount', {
        method: 'GET',
        credentials: 'include'
    })
    .then(response => response.json())
    .then(data => {
        updateChatBadge(data.unreadCount || 0);
    })
    .catch(error => {
        console.log('안읽은 메시지 수 조회 실패:', error);
    });
}

// 채팅 배지 업데이트 함수
function updateChatBadge(count) {
    const chatLink = document.querySelector('a[href="/admin/chat/view"]');
    let badge = document.getElementById('unreadChatBadge');
    
    if (count > 0) {
        if (!badge) {
            // 배지가 없으면 생성
            badge = document.createElement('span');
            badge.id = 'unreadChatBadge';
            badge.className = 'unread-badge';
            chatLink.appendChild(badge);
        }
        badge.textContent = count > 99 ? '99+' : count;
        badge.style.display = 'inline-block';
        
        // 채팅 링크 강조
        chatLink.classList.add('has-unread');
    } else {
        if (badge) {
            badge.style.display = 'none';
        }
        // 채팅 링크 강조 제거
        chatLink.classList.remove('has-unread');
    }
}

// 채팅 페이지 방문 시 안읽은 메시지 초기화
function markChatAsRead() {
    if (window.location.pathname === '/admin/chat/view') {
        updateChatBadge(0);
    }
}
</script>