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
		<p>로그인 되지 않은 상태</p>
		<p><a href="01_insert.jsp">Join!</a></p>
		<p><a href="07_login.jsp">Login!</a></p>
	<%
		}
		else{
	%>
		<p>로그인된 상태</p>
		<h2>Welcome "<%=id %>"님</h2>
		<p><a href="03_delete.jsp">Delete!</a></p>
		<p><a href="05_update.jsp">Update!</a></p>
		<p><a href="09_logout.jsp">Logout!</a></p>
		
	<%	
		}
	%>
</body>
</html>