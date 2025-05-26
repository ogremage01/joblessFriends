<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
  <title>주소 리턴 처리</title>
</head>
<body>
<script>
  // 행안부에서 전달된 주소 파라미터 받기
  const roadAddrPart1 = '<%= request.getParameter("roadAddrPart1") %>';
  const addrDetail = '<%= request.getParameter("addrDetail") %>';

  // 부모창의 함수에 주소 전달
  if (window.opener && typeof window.opener.handleJusoCallback === 'function') {
    window.opener.handleJusoCallback(roadAddrPart1, addrDetail);
  }

  // 팝업창 닫기
  window.close();
</script>
</body>
</html>