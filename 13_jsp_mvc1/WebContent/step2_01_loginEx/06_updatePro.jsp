<%@page import="step2_00_loginEx.MemberDao"%>
<%@page import="step2_00_loginEx.MemberDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>updatePro</title>
</head>
<body>
	<%
		request.setCharacterEncoding("utf-8");
	
		MemberDto memberDto = new MemberDto();
		
		memberDto.setId(request.getParameter("id"));
		memberDto.setPasswd(request.getParameter("passwd"));
		memberDto.setName(request.getParameter("name"));
		
		boolean isUpdateMember = MemberDao.getInstance().updateMember(memberDto);
		
		if(isUpdateMember){
	%>
		<script>
			alert("정보가 수정되었습니다");
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