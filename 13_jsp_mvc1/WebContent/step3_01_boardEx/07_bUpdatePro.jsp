<%@page import="step3_00_boardEx.BoardDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>bUpdatePro</title>
</head>
<body>
	
	<% 
		request.setCharacterEncoding("utf-8");
	%>
	
	<jsp:useBean id="boardDto" class="step3_00_boardEx.BoardDto">
		<jsp:setProperty property="*" name="boardDto"/>
	</jsp:useBean>
	
	<% 
	
		boolean isUpdate = BoardDao.getInstance().updateBoard(boardDto);
	
		if (isUpdate) {
	%>
			<script>
				alert("수정되었습니다.");
				location.href = "04_bList.jsp";
			</script>
	<% 		
		}
		else {
	%>
			<script>
				alert("아이디와 패스워드를 확인해주세요.");
				history.go(-1);
			</script>
	<% 		
		}
	%>




</body>
</html>