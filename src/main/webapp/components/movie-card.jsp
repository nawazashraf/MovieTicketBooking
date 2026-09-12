<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/moviecard.css">

<%@ page import="com.movieticket.model.MovieBean"%>

<%
MovieBean movie = (MovieBean) request.getAttribute("movie");
%>
<div class="movie-card">
	<img src="<%=movie.getPosterUrl()%>" alt="<%=movie.getTitle()%> Poster">

	<div class="movie-info">
		<h2><%=movie.getTitle()%></h2>

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

		<p class="status">
			<%=movie.getStatus()%>
		</p>
	</div>
</div>