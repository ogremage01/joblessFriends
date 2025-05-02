<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
    <title>인덱스</title>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
     integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.min.js"
 integrity="sha384-VQqxDN0EQCkWoxt/0vsQvZswzTHUVOImccYmSyhJTp7kGtPed0Qcx8rK9h9YEgx+" crossorigin="anonymous"></script>
    <script
            src="https://code.jquery.com/jquery-3.7.1.js"
            integrity="sha256-eKhayi8LEQwp4NKxN+Cfch+3qOVUtJn3QNZOtciWLP4="
            crossorigin="anonymous">
    </script>
    <link rel="stylesheet" href="/css/common/index.css">
</head>
<body>
	<jsp:include page="common/header.jsp"/>
	
	<div id="container">
		<div id="containerWrap">
			<div class="jobInfoSection section">
				<h2>채용정보</h2>
				<div class="jobInfoContent">
				
					<div class="jobInfoItem">
						<div class="jobImg">
							<img alt="" src="https://www.casenews.co.kr/news/photo/202408/16238_35415_720.jpg"/>
							<div class="jobOverImage">
								<div class="jobDeadline">
									<span>
										~0414(월)
										<svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-star" viewBox="0 0 15 15" width="15px" height="15px">
		 									 <path d="M2.866 14.85c-.078.444.36.791.746.593l4.39-2.256 4.389 2.256c.386.198.824-.149.746-.592l-.83-4.73 3.522-3.356c.33-.314.16-.888-.282-.95l-4.898-.696L8.465.792a.513.513 0 0 0-.927 0L5.354 5.12l-4.898.696c-.441.062-.612.636-.283.95l3.523 3.356-.83 4.73zm4.905-2.767-3.686 1.894.694-3.957a.56.56 0 0 0-.163-.505L1.71 6.745l4.052-.576a.53.53 0 0 0 .393-.288L8 2.223l1.847 3.658a.53.53 0 0 0 .393.288l4.052.575-2.906 2.77a.56.56 0 0 0-.163.506l.694 3.957-3.686-1.894a.5.5 0 0 0-.461 0z"/>
										</svg>
									</span>
								</div>
								<div class="skillTagsWrap">
									<div class="jobSkillTag">
										<span>aaaaaaaaaajavaScript</span>
									</div>
									<div class="jobSkillTag">
										<span>aaaaaaaaaajavaScript</span>
									</div>
									<div class="jobSkillTag">
										<span>aaaaaaaaaajavaScript</span>
									</div>
								</div>
							</div>
						</div>
						
						<span class="jobTitle">제목 혹은 직무명</span>
						<span class="jobRegionCareer">지역·경력</span>
						<span class="jobCompanyName">회사명</span>
						
					</div>
			<!-- ======================  여기부터 복붙  ======================-->
					<div class="jobInfoItem">
						<div class="jobImg">
							<img alt="" src="https://www.casenews.co.kr/news/photo/202408/16238_35415_720.jpg"/>
							<div class="jobOverImage">
								<div class="jobDeadline">
									<span>
										오늘마감
										<svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-star" viewBox="0 0 15 15" width="15px" height="15px">
		 									 <path d="M2.866 14.85c-.078.444.36.791.746.593l4.39-2.256 4.389 2.256c.386.198.824-.149.746-.592l-.83-4.73 3.522-3.356c.33-.314.16-.888-.282-.95l-4.898-.696L8.465.792a.513.513 0 0 0-.927 0L5.354 5.12l-4.898.696c-.441.062-.612.636-.283.95l3.523 3.356-.83 4.73zm4.905-2.767-3.686 1.894.694-3.957a.56.56 0 0 0-.163-.505L1.71 6.745l4.052-.576a.53.53 0 0 0 .393-.288L8 2.223l1.847 3.658a.53.53 0 0 0 .393.288l4.052.575-2.906 2.77a.56.56 0 0 0-.163.506l.694 3.957-3.686-1.894a.5.5 0 0 0-.461 0z"/>
										</svg>
									</span>
								</div>
								<div class="skillTagsWrap">
									<div class="jobSkillTag">
										<span>javaScript</span>
									</div>
								</div>
							</div>
						</div>
						
						<span class="jobTitle">제목 혹은 직무명</span>
						<span class="jobRegionCareer">지역·경력</span>
						<span class="jobCompanyName">회사명</span>
						
					</div>
					
					<div class="jobInfoItem">
						<div class="jobImg">
							<img alt="" src="https://www.casenews.co.kr/news/photo/202408/16238_35415_720.jpg"/>
							<div class="jobOverImage">
								<div class="jobDeadline">
									<span>
										D-10
										<svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-star" viewBox="0 0 15 15" width="15px" height="15px">
		 									 <path d="M2.866 14.85c-.078.444.36.791.746.593l4.39-2.256 4.389 2.256c.386.198.824-.149.746-.592l-.83-4.73 3.522-3.356c.33-.314.16-.888-.282-.95l-4.898-.696L8.465.792a.513.513 0 0 0-.927 0L5.354 5.12l-4.898.696c-.441.062-.612.636-.283.95l3.523 3.356-.83 4.73zm4.905-2.767-3.686 1.894.694-3.957a.56.56 0 0 0-.163-.505L1.71 6.745l4.052-.576a.53.53 0 0 0 .393-.288L8 2.223l1.847 3.658a.53.53 0 0 0 .393.288l4.052.575-2.906 2.77a.56.56 0 0 0-.163.506l.694 3.957-3.686-1.894a.5.5 0 0 0-.461 0z"/>
										</svg>
									</span>
								</div>
								<div class="skillTagsWrap">
									<div class="jobSkillTag">
										<span>react</span>
									</div>
								</div>
							</div>
						</div>
						
						<span class="jobTitle">제목 혹은 직무명</span>
						<span class="jobRegionCareer">지역·경력</span>
						<span class="jobCompanyName">회사명</span>
						
					</div>
					
					<div class="jobInfoItem">
						<div class="jobImg">
							<img alt="" src="https://www.casenews.co.kr/news/photo/202408/16238_35415_720.jpg"/>
							<div class="jobOverImage">
								<div class="jobDeadline">
									<span>
										D-10
										<svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-star" viewBox="0 0 15 15" width="15px" height="15px">
		 									 <path d="M2.866 14.85c-.078.444.36.791.746.593l4.39-2.256 4.389 2.256c.386.198.824-.149.746-.592l-.83-4.73 3.522-3.356c.33-.314.16-.888-.282-.95l-4.898-.696L8.465.792a.513.513 0 0 0-.927 0L5.354 5.12l-4.898.696c-.441.062-.612.636-.283.95l3.523 3.356-.83 4.73zm4.905-2.767-3.686 1.894.694-3.957a.56.56 0 0 0-.163-.505L1.71 6.745l4.052-.576a.53.53 0 0 0 .393-.288L8 2.223l1.847 3.658a.53.53 0 0 0 .393.288l4.052.575-2.906 2.77a.56.56 0 0 0-.163.506l.694 3.957-3.686-1.894a.5.5 0 0 0-.461 0z"/>
										</svg>
									</span>
								</div>
								<div class="skillTagsWrap">
									<div class="jobSkillTag">
										<span>javaScript</span>
									</div>
									<div class="jobSkillTag">
										<span>react</span>
									</div>
								</div>
							</div>
						</div>
						
						<span class="jobTitle">제목 혹은 직무명</span>
						<span class="jobRegionCareer">지역·경력</span>
						<span class="jobCompanyName">회사명</span>
						
					</div>
					
					<div class="jobInfoItem">
						<div class="jobImg">
							<img alt="" src="https://www.casenews.co.kr/news/photo/202408/16238_35415_720.jpg"/>
							<div class="jobOverImage">
								<div class="jobDeadline">
									<span>
										D-10
										<svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-star" viewBox="0 0 15 15" width="15px" height="15px">
		 									 <path d="M2.866 14.85c-.078.444.36.791.746.593l4.39-2.256 4.389 2.256c.386.198.824-.149.746-.592l-.83-4.73 3.522-3.356c.33-.314.16-.888-.282-.95l-4.898-.696L8.465.792a.513.513 0 0 0-.927 0L5.354 5.12l-4.898.696c-.441.062-.612.636-.283.95l3.523 3.356-.83 4.73zm4.905-2.767-3.686 1.894.694-3.957a.56.56 0 0 0-.163-.505L1.71 6.745l4.052-.576a.53.53 0 0 0 .393-.288L8 2.223l1.847 3.658a.53.53 0 0 0 .393.288l4.052.575-2.906 2.77a.56.56 0 0 0-.163.506l.694 3.957-3.686-1.894a.5.5 0 0 0-.461 0z"/>
										</svg>
									</span>
								</div>
								<div class="skillTagsWrap">
									<div class="jobSkillTag">
										<span>javaScript</span>
									</div>
									<div class="jobSkillTag">
										<span>photoshop</span>
									</div>
									<div class="jobSkillTag">
										<span>react</span>
									</div>
								</div>
							</div>
						</div>
						
						<span class="jobTitle">제목 혹은 직무명</span>
						<span class="jobRegionCareer">지역·경력</span>
						<span class="jobCompanyName">회사명</span>
						
					</div>
					
					<div class="jobInfoItem">
						<div class="jobImg">
							<img alt="" src="https://www.casenews.co.kr/news/photo/202408/16238_35415_720.jpg"/>
							<div class="jobOverImage">
								<div class="jobDeadline">
									<span>
										D-10
										<svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-star" viewBox="0 0 15 15" width="15px" height="15px">
		 									 <path d="M2.866 14.85c-.078.444.36.791.746.593l4.39-2.256 4.389 2.256c.386.198.824-.149.746-.592l-.83-4.73 3.522-3.356c.33-.314.16-.888-.282-.95l-4.898-.696L8.465.792a.513.513 0 0 0-.927 0L5.354 5.12l-4.898.696c-.441.062-.612.636-.283.95l3.523 3.356-.83 4.73zm4.905-2.767-3.686 1.894.694-3.957a.56.56 0 0 0-.163-.505L1.71 6.745l4.052-.576a.53.53 0 0 0 .393-.288L8 2.223l1.847 3.658a.53.53 0 0 0 .393.288l4.052.575-2.906 2.77a.56.56 0 0 0-.163.506l.694 3.957-3.686-1.894a.5.5 0 0 0-.461 0z"/>
										</svg>
									</span>
								</div>
								<div class="skillTagsWrap">
									<div class="jobSkillTag">
										<span>photoshop</span>
									</div>
									<div class="jobSkillTag">
										<span>illustrator</span>
									</div>
								</div>
							</div>
						</div>
						
						<span class="jobTitle">제목 혹은 직무명</span>
						<span class="jobRegionCareer">지역·경력</span>
						<span class="jobCompanyName">회사명</span>
						
					</div>
					
					<div class="jobInfoItem">
						<div class="jobImg">
							<img alt="" src="https://www.casenews.co.kr/news/photo/202408/16238_35415_720.jpg"/>
							<div class="jobOverImage">
								<div class="jobDeadline">
									<span>
										D-10
										<svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-star" viewBox="0 0 15 15" width="15px" height="15px">
		 									 <path d="M2.866 14.85c-.078.444.36.791.746.593l4.39-2.256 4.389 2.256c.386.198.824-.149.746-.592l-.83-4.73 3.522-3.356c.33-.314.16-.888-.282-.95l-4.898-.696L8.465.792a.513.513 0 0 0-.927 0L5.354 5.12l-4.898.696c-.441.062-.612.636-.283.95l3.523 3.356-.83 4.73zm4.905-2.767-3.686 1.894.694-3.957a.56.56 0 0 0-.163-.505L1.71 6.745l4.052-.576a.53.53 0 0 0 .393-.288L8 2.223l1.847 3.658a.53.53 0 0 0 .393.288l4.052.575-2.906 2.77a.56.56 0 0 0-.163.506l.694 3.957-3.686-1.894a.5.5 0 0 0-.461 0z"/>
										</svg>
									</span>
								</div>
								<div class="skillTagsWrap">
								</div>
							</div>
						</div>
						
						<span class="jobTitle">제목 혹은 직무명</span>
						<span class="jobRegionCareer">지역·경력</span>
						<span class="jobCompanyName">회사명</span>
						
					</div>
					
					<div class="jobInfoItem">
						<div class="jobImg">
							<img alt="" src="https://www.casenews.co.kr/news/photo/202408/16238_35415_720.jpg"/>
							<div class="jobOverImage">
								<div class="jobDeadline">
									<span>
										D-10
										<svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-star" viewBox="0 0 15 15" width="15px" height="15px">
		 									 <path d="M2.866 14.85c-.078.444.36.791.746.593l4.39-2.256 4.389 2.256c.386.198.824-.149.746-.592l-.83-4.73 3.522-3.356c.33-.314.16-.888-.282-.95l-4.898-.696L8.465.792a.513.513 0 0 0-.927 0L5.354 5.12l-4.898.696c-.441.062-.612.636-.283.95l3.523 3.356-.83 4.73zm4.905-2.767-3.686 1.894.694-3.957a.56.56 0 0 0-.163-.505L1.71 6.745l4.052-.576a.53.53 0 0 0 .393-.288L8 2.223l1.847 3.658a.53.53 0 0 0 .393.288l4.052.575-2.906 2.77a.56.56 0 0 0-.163.506l.694 3.957-3.686-1.894a.5.5 0 0 0-.461 0z"/>
										</svg>
									</span>
								</div>
								<div class="skillTagsWrap">
									<div class="jobSkillTag">
										<span>javaScript</span>
									</div>
									<div class="jobSkillTag">
										<span>photoshop</span>
									</div>
									<div class="jobSkillTag">
										<span>react</span>
									</div>
								</div>
							</div>
						</div>
						
						<span class="jobTitle">제목 혹은 직무명</span>
						<span class="jobRegionCareer">지역·경력</span>
						<span class="jobCompanyName">회사명</span>
						
					</div>
					<!-- ====================== 복붙 끝  ======================-->
				</div>
			</div>
		</div>
	</div>	
	
	<div>
	
	

	<jsp:include page="common/footer.jsp"/>
</body>
</html>