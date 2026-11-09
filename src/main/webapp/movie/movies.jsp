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
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Movies</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/common.css?v=3">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/moviecard.css?v=13">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/movie.css?v=4">
</head>

<body>
	<%@ include file="/common/navbar.jsp" %>

	<main class="movies-page">
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
	</main>

</body>
</html>