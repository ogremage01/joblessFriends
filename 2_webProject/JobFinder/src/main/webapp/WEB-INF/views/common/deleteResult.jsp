<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>

    <title>인덱스</title>
   
    <script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-3fp9tS8p9A2Mq7Qz+S8jfwD+xdgu9T+O+NRZz8N5eA8=" crossorigin="anonymous"></script>
  
    <link rel="stylesheet" href="/css/common/common.css">
    
    <style type="text/css">
    	
    	#container{
    		width: 50px;
    		text-align: center;
    	}
    
    </style>
    
</head>

<body>

<jsp:include page="../common/header.jsp"/>

	<div id="container">
		<div id="containerWrap">
		
		탈퇴 요청이 정상처리되었습니다.<br>
		그동안 애용해 주셔서 감사합니다.
		
		</div>
	</div>

<jsp:include page="../common/footer.jsp"/>

</body>

</html>
