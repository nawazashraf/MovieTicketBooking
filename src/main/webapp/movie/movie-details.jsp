<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="com.movieticket.model.MovieBean"%>
<%
MovieBean movie = (MovieBean) request.getAttribute("movie");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<div class="movie-details">

		<div class="movie-poster">
			<img src="<%=movie.getPosterUrl()%>"
				alt="<%=movie.getTitle()%> Poster">
		</div>

		<div class="movie-info">

			<h1><%=movie.getTitle()%></h1>

			<p><%=movie.getDescription()%></p>

			<p>
				<strong>Duration:</strong>
				<%=movie.getDurationMinutes()%>
				minutes
			</p>

			<p>
				<strong>Language:</strong>
				<%=movie.getLanguage()%>
			</p>

			<p>
				<strong>Certificate:</strong>
				<%=movie.getCertificate()%>
			</p>

			<p>
				<strong>Release Date:</strong>
				<%=movie.getReleaseDate()%>
			</p>

			<p>
				<strong>Status:</strong>
				<%=movie.getStatus()%>
			</p>

			<a href="${pageContext.request.contextPath}/movies"> Back to
				Movies </a>

		</div>

	</div>
</body>
</html>