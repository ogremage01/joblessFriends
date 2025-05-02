<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 목록</title>

<style type="text/css">	
	.wrap{

		display: flex;
		justify-content: space-between;
		
		
		
	}
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="wrap">
	<div>
		<jsp:include page="/WEB-INF/views/commue/list/commueSideBar.jsp"/>
	</div>	
	<jsp:include page="/WEB-INF/views/commue/list/commueListOne.jsp"/>

</div>
</body>
</html>