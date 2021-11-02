<%@page import="step4_00_boardEx.BoardDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>11_bRePro</title>
</head>
<body>

	<%
		request.setCharacterEncoding("UTF-8");
	%>
		<jsp:useBean id="boardDto" class="step4_00_boardEx.BoardDto">
			<jsp:setProperty name="boardDto" property="*" />
		</jsp:useBean>
	<%
		BoardDao.getInstance().reWriteBoard(boardDto);
		response.sendRedirect("04_bList.jsp");
	%>

</body>
</html>