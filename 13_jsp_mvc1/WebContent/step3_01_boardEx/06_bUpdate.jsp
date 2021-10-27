<%@page import="step3_00_boardEx.BoardDto"%>
<%@page import="step3_00_boardEx.BoardDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>bUpdate</title>
</head>
<body>

	<% 
		int num = Integer.parseInt(request.getParameter("num"));
		BoardDto boardDto = BoardDao.getInstance().getOneBoard(num);
	%>
	
	<form action="07_bUpdatePro.jsp" method="post">
		<h2>게시글 수정하기</h2>
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
				<td><input type="text" name="subject" value="<%=boardDto.getSubject() %>"></td>
			</tr>
			<tr>
				<td>패스워드</td>
				<td><input type="password" name="password"></td>
			</tr>
			<tr>
				<td>글 내용</td>
				<td><textarea rows="10" cols="60" name="content"><%=boardDto.getContent() %></textarea> </td>
			</tr>
		</table>
		<p>
			<input type="hidden" name="num" value="<%=boardDto.getNum() %>">
			<input type="submit" value="수정하기">
			<input type="button" value="전체글보기" onclick="location.href='04_bList.jsp'">
		</p>
	</form>

</body>
</html>

