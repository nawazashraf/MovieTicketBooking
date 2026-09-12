<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.movieticket.model.MovieBean"%>
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
	<%
	List<MovieBean> nowShowingMovies = (List<MovieBean>) request.getAttribute("nowShowingMovies");
	%>
	<section class="movie-section">
		<h2>Now Showing</h2>

		<div class="movie-container">
			<%
			if (nowShowingMovies != null && !nowShowingMovies.isEmpty()) {
				for (MovieBean movie : nowShowingMovies) {
					request.setAttribute("movie", movie);
			%>
			<jsp:include page="/components/movie-card.jsp"></jsp:include>
			<%
			}
			}
			%>
		</div>
	</section>

	<form action="${pageContext.request.contextPath}/booking/seats"
		method="post">
		<input type="submit" value="Submit">
	</form>

</body>
</html>