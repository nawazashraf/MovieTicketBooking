<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="com.movieticket.model.MovieBean"%>
<%@ page import="com.movieticket.model.ShowBean"%>
<%
MovieBean movie = (MovieBean) request.getAttribute("movie");
%>

<%
List<ShowBean> shows = (List<ShowBean>) request.getAttribute("shows");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Movie Details</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/common.css">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/movie-details.css">
</head>
<body>

	<div class="movie-hero">

		<div class="movie-backdrop"
			style="background-image: url('<%=movie.getPosterUrl()%>');"></div>

		<div class="movie-hero-content">

			<div class="movie-poster">
				<img src="<%=movie.getPosterUrl()%>"
					alt="<%=movie.getTitle()%> Poster">
			</div>

			<div class="movie-info">

				<h1><%=movie.getTitle()%></h1>

				<p class="movie-meta">
					<%=movie.getLanguage()%>
					&nbsp;•&nbsp;
					<%=movie.getDurationMinutes()%>
					min &nbsp;•&nbsp;
					<%=movie.getCertificate()%>
				</p>

				<p class="movie-description">
					<%=movie.getDescription()%>
				</p>

				<div class="movie-actions">

					<a href="<%=movie.getTrailerUrl()%>" target="_blank"> Watch
						Trailer </a> <a href="${pageContext.request.contextPath}/movies">
						Back to Movies </a>

				</div>

			</div>

		</div>

	</div>
	<div class="shows-section">

		<h2>Available Shows</h2>

		<%
		if (shows != null && !shows.isEmpty()) {

			for (ShowBean show : shows) {
		%>

		<div class="show-row">

			<div class="show-mall">
				<h3><%=show.getMallName()%></h3>
				<span> <%=show.getShowDate()%>
				</span> 
			</div>

			<div class="show-time">
				<a
					href="${pageContext.request.contextPath}/booking/seats?showId=<%= show.getShowId() %>">
					<%=show.getStartTime()%>
				</a>
			</div>

		</div>

		<%
		}

		} else {
		%>

		<p>No shows available for this movie.</p>

		<%
		}
		%>

	</div>


</body>
</html>