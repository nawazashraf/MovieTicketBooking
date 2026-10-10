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

<style>
body {
	font-family: Arial, sans-serif;
	background: #f5f5f5;
	margin: 0;
	padding: 48px;
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

@media (max-width: 768px) and (min-width: 410px) {
	body {
		padding: 10px 6px !important;
	}

	.movie-container {
		display: grid !important;
		grid-template-columns: repeat(3, minmax(0, 1fr)) !important;
		gap: 6px !important;
		width: 100% !important;
		box-sizing: border-box !important;
	}

	.movie-card-link {
		width: 100% !important;
		max-width: 100% !important;
		min-width: 0 !important;
		flex: none !important;
		display: block !important;
		box-sizing: border-box !important;
	}

	.movie-card {
		width: 100% !important;
		min-width: 0 !important;
		height: 270px !important;
		border-radius: 8px !important;
		box-sizing: border-box !important;
	}

	.movie-card img {
		width: 100% !important;
		height: 175px !important;
		flex: 0 0 175px !important;
		object-fit: cover !important;
	}

	.movie-info {
		padding: 5px 5px 7px !important;
		min-width: 0 !important;
		overflow: hidden !important;
		box-sizing: border-box !important;
	}

	.movie-info h2 {
		font-size: 11.5px !important;
		line-height: 1.25 !important;
		margin: 0 0 3px !important;
		white-space: nowrap !important;
		overflow: hidden !important;
		text-overflow: ellipsis !important;
	}

	.movie-info p:nth-of-type(1) {
		display: none !important;
	}

	.movie-info p {
		margin: 2px 0 !important;
		font-size: 8.5px !important;
		line-height: 1.2 !important;
		white-space: nowrap !important;
		overflow: hidden !important;
		text-overflow: ellipsis !important;
	}

	.movie-info .status {
		margin-top: 3px !important;
		font-size: 8px !important;
	}
}

@media (max-width: 409px) {
	body {
		padding: 8px 4px !important;
	}

	.movie-container {
		display: grid !important;
		grid-template-columns: repeat(2, minmax(0, 1fr)) !important;
		gap: 6px !important;
		width: 100% !important;
		box-sizing: border-box !important;
	}

	.movie-card-link {
		width: 100% !important;
		max-width: 100% !important;
		min-width: 0 !important;
		flex: none !important;
		display: block !important;
		box-sizing: border-box !important;
	}

	.movie-card {
		width: 100% !important;
		min-width: 0 !important;
		height: 285px !important;
		border-radius: 8px !important;
		box-sizing: border-box !important;
	}

	.movie-card img {
		width: 100% !important;
		height: 190px !important;
		flex: 0 0 190px !important;
		object-fit: cover !important;
	}

	.movie-info {
		padding: 7px 7px 9px !important;
		min-width: 0 !important;
		overflow: hidden !important;
	}

	.movie-info h2 {
		font-size: 13px !important;
		white-space: nowrap !important;
		overflow: hidden !important;
		text-overflow: ellipsis !important;
	}

	.movie-info p:nth-of-type(1) {
		display: none !important;
	}

	.movie-info p {
		margin: 2px 0 !important;
		font-size: 9px !important;
		white-space: nowrap !important;
		overflow: hidden !important;
		text-overflow: ellipsis !important;
	}

	.movie-info .status {
		margin-top: 3px !important;
		font-size: 8px !important;
	}
}
</style>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/common.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/moviecard.css?v=10">
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