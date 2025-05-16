<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이페이지</title>
<link rel="stylesheet" href="/css/common/common.css">
<link rel="stylesheet" href="/css/member/memberMyPage.css" />
</head>
<body>

<jsp:include page="../common/header.jsp"/>

	<div class="container">
		<div id="containerWrap">
			<jsp:include page="../common/mypageSidebar.jsp"/>
		    <div class="main">
		    
		    	<div class="content">
			    	<h1 class="mainTitle">마이페이지</h1>
			    	<div class="item">
				    	내용
			    	</div>
		    	</div>
		      
		    </div>
		</div>    
	</div>
	
<jsp:include page="../common/footer.jsp" />

</body>
</html>