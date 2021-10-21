<%@page import="step2_00_loginEx.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>loginPro</title>
</head>
<body>

	<%
		request.setCharacterEncoding("utf-8");
	
		String id = request.getParameter("id");
		String passwd = request.getParameter("passwd");
		
		boolean isValidMember = MemberDao.getInstance().login(id,passwd);
		
		if(isValidMember){
			
			session.setAttribute("id",id);
			//setMaxInactiveInterval : session유지 기간 설정 메서드
			session.setMaxInactiveInterval(60 * 10); // 10분
			/*
			1                > 1초
			60               > 1분
			60 * 60          > 1시간
			60* 60 * 24      > 1일
			60 * 60 * 24 * 7 > 1주일
		*/

	%>
		<script>
			alert("Logged in");
			location.href="00_main.jsp";
		</script>
	<%		
		}
		else{
	%>
		<script>
			alert("아이디와 비밀번호를 확인하시오.")
			history.go(-1);
		</script>
	<%
		}
	%>
</body>
</html>