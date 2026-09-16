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

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/movie.css">


</head>
<body>
	<%@ include file="/common/navbar.jsp"%>
	<%
	List<MovieBean> nowShowingMovies = (List<MovieBean>) request.getAttribute("nowShowingMovies");
	List<MovieBean> comingSoonMovies = (List<MovieBean>) request.getAttribute("comingSoonMovies");
	%>
	<div class="home-page">
		<section class="movie-section">
			<div class="section-header">
				<h2>Now Showing</h2>
				<a
					href="${pageContext.request.contextPath}/movies">View All</a>
			</div>

			<div class="movie-container">
				<%
				if (nowShowingMovies != null && !nowShowingMovies.isEmpty()) {
					for (MovieBean movie : nowShowingMovies) {
						request.setAttribute("movie", movie);
				%>
				<jsp:include page="/components/movie-card.jsp"></jsp:include>
				<%
				}

				} else {
				%>
				<p>No movies currently showing.</p>
				<%
				}
				%>
			</div>
		</section>

		<section class="movie-section">

			<div class="section-header">
				<h2>Coming Soon</h2>
				<a
					href="${pageContext.request.contextPath}/movies">View All</a>
			</div>

			<div class="movie-container">

				<%
				if (comingSoonMovies != null && !comingSoonMovies.isEmpty()) {

					for (MovieBean movie : comingSoonMovies) {
						request.setAttribute("movie", movie);
				%>

				<jsp:include page="/components/movie-card.jsp" />

				<%
				}

				} else {
				%>

				<p>No upcoming movies.</p>

				<%
				}
				%>

			</div>

		</section>

	</div>

</body>
</html>