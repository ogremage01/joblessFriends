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
    	
    	#container #containerWrap{
    		width: 50px;
    	}
    
    </style>
    
</head>

<body>

<jsp:include page="../common/header.jsp"/>

	<div id="container">
		<div id="containerWrap">
		
		이곳에 내용물을 넣으시면 됩니다
		가로 사이즈의 경우 스타일에 #container #containerWrap에서 조정하면됩니다
		헤더 푸터의 경우 상대경로이므로 잘 확인해서 쓰세요
		
		</div>
	</div>

<jsp:include page="../common/footer.jsp"/>

</body>

</html>
