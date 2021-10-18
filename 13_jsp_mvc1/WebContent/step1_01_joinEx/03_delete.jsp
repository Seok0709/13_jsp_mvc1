<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원삭제(delete)</title>
</head>
<body>
	
	<form action="04_deletePro.jsp" method="post">
		<filedset>
			<legend>회원삭제</legend>
			<p>아이디: <input type="text" name="id"></p>
			<p>비밀번호: <input type="password" name="passwd"></p>
			<p><input type="submit" value="삭제"></p>
		</filedset>
	</form>	
</body>
</html>