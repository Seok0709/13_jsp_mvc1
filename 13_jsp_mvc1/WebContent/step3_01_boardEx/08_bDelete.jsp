<%@page import="step3_00_boardEx.BoardDao"%>
<%@page import="step3_00_boardEx.BoardDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>bDelete</title>
</head>
<body>
	<%
		int num = Integer.parseInt(request.getParameter("num"));
		BoardDto boardDto = BoardDao.getInstance().getOneBoard(num);
	%>
	
	<form action="09_bDeletePro.jsp" method="post">
		<h2>게시글 삭제하기</h2>
		<table border="1">
			
			<tr>
				<td>작성자</td>
				<td><%=boardDto.getWriter() %></td>
			</tr>
			<tr>
				<td>작성일</td>
				<td><%=boardDto.getRegDate() %></td>
			</tr>
			<tr>
				<td>제목</td>
				<td><%=boardDto.getSubject() %></td>
			</tr>
			<tr>
				<td>비밀번호</td>
				<td><input type="password" name="password"></td>
			</tr>
		</table>
		<p>
			<input type="hidden" name="num" value="<%=boardDto.getNum() %>">
			<input type="submit" value="글삭제">
			<input type="button" value="목록보기" onclick = "location.href='04_bList.jsp'">
		</p>
	</form>
	
</body>
</html>