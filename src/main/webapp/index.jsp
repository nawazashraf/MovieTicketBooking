<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home Page</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/common.css">

</head>
<body>
	<%@ include file="/common/navbar.jsp"%>

	<form action="${pageContext.request.contextPath}/booking/seats"
		method="post">
		<input type="submit" value="Submit">
	</form>

</body>
</html>