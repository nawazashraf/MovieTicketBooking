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
	href="${pageContext.request.contextPath}/assets/css/movie-details.css">
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



			<div class="movie-actions">

				<a href="<%=movie.getTrailerUrl()%>" target="_blank"> Watch
					Trailer </a> <a href="${pageContext.request.contextPath}/movies">
					Back to Movies </a>

			</div>

			<div class="shows-section">

				<h2>Available Shows</h2>

				<%
				if (shows != null && !shows.isEmpty()) {

					for (ShowBean show : shows) {
				%>

				<div class="show-card">
					<a
						href="${pageContext.request.contextPath}/booking/seats?showId=<%= show.getShowId() %>">
						Select Show </a>

					<h3><%=show.getMallName()%></h3>

					<p>
						<%=show.getShowDate()%>
					</p>

					<p>
						<%=show.getStartTime()%>
						-
						<%=show.getEndTime()%>
					</p>

					<p>
						<%=show.getStatus()%>
					</p>

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
		</div>




	</div>
</body>
</html>