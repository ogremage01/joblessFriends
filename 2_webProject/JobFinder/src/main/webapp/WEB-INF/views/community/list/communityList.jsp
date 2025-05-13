<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 목록</title>

<link rel="stylesheet" href="/css/common/common.css">
<style type="text/css">	
	.wrap{

		display: flex;
		justify-content: space-between;
	}

	#communityList{
		margin-bottom: 15px;
	}
	

	
</style>
<script type="text/javascript">

</script>
</head>

<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<div id='containerWrap' class="wrap">

	<div id='communityList'>
		<jsp:include page="/WEB-INF/views/community/list/communitySideBar.jsp"/>
	</div>	

	<div>
		<jsp:include page="/WEB-INF/views/community/list/communityListOne.jsp"/>
	</div>
	
	<jsp:include page="/WEB-INF/views/community/topBar.jsp"/>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>