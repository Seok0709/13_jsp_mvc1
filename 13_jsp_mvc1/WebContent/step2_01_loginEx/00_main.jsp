<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>main</title>
</head>
<body>
	
	<%
		String id = (String)session.getAttribute("id");
	
		if(id == null){
	%>
		<p>로그인 하지 않았음</p>
		<p><a href="01_insert.jsp">Join!</a></p>
		<p><a href="07_login.jsp">Login!</a></p>
	<%
		}
		else{
	%>
		<p>로그인 하였음</p>
	<%	
		}
	%>
</body>
</html>