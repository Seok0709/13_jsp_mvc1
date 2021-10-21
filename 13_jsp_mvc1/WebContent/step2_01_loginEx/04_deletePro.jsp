<%@page import="step2_00_loginEx.MemberDto"%>
<%@page import="step2_00_loginEx.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>deletePro</title>
</head>
<body>
	<%
		request.setCharacterEncoding("utf-8");
	
		MemberDto memberDto = new MemberDto();
		memberDto.setId(request.getParameter("id"));
		memberDto.setPasswd(request.getParameter("passwd"));
		
		boolean isDeleteMember = MemberDao.getInstance().deleteMember(memberDto);
		
		if(isDeleteMember){
			
			// session.removeAttribute("id"); // 세션지우는 방법
			session.invalidate();	// 세션지우는 방법2
	%>
		<script>
			alert("계정이 삭제되었습니다.")
			location.href="00_main.jsp";
		</script>
	<%
			
		}
		else{
	%>
		<script>
			alert("아이디와 비밀번호를 확인하세요");
			history.go(-1);
		</script>
	<%
		}
		
		
	%>
</body>
</html>