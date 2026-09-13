<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.movieticket.model.MovieBean"%>

<%
List<MovieBean> movies = (List<MovieBean>) request.getAttribute("movies");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Movies</title>

<style>
body {
	font-family: Arial, sans-serif;
	background: #f5f5f5;
	margin: 0;
	padding: 30px;
}

h1 {
	text-align: center;
}

.movie-container {
	display: flex;
	flex-wrap: wrap;
	justify-content: center;
	gap: 20px;
}

.movie-card {
	width: 260px;
	background: white;
	border-radius: 10px;
	overflow: hidden;
	box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
}

.movie-card img {
	width: 100%;
	height: 330px;
	object-fit: cover;
}

.movie-info {
	padding: 15px;
}

.movie-info h2 {
	font-size: 20px;
	margin-top: 0;
}

.status {
	color: green;
	font-weight: bold;
}
</style>
</head>

<body>
	<%@ include file="/common/navbar.jsp" %>

	<h1><%= (request.getAttribute("searchQuery") != null && !((String)request.getAttribute("searchQuery")).isBlank()) ? "Search Results" : "Movies" %></h1>
	<% if (request.getAttribute("searchQuery") != null && !((String)request.getAttribute("searchQuery")).isBlank()) { %><p>Results for: <strong><%=request.getAttribute("searchQuery")%></strong></p><% } %>

	<div class="movie-container">

		<%
		if (movies != null && !movies.isEmpty()) {
			for (MovieBean movie : movies) {
		%>

		<%
		request.setAttribute("movie", movie);
		%>

		<jsp:include page="/components/movie-card.jsp" />

		<%
		}
		} else {
		%>

		<p>No movies are available.</p>

		<%
		}
		%>

	</div>

</body>
</html>