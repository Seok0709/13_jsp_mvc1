<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>update</title>
</head>
<body>
	<form action="06_updatePro.jsp" method="post">
		<fieldset>
			<legend>회원정보수정</legend>
			<p>아이디: <input type="text" name="id"></p>
			<p>비밀번호: <input type="password" name="passwd"></p>
			<p>이름: <input type="text" name="name"></p>
			<p><input type="submit" value="회원정보수정"></p>
			
		</fieldset>
	</form>
	
</body>
</html>