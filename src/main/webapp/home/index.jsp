<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="com.movieticket.model.MovieBean"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home Page</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/common.css?v=5">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/moviecard.css?v=15">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/movie.css?v=7">


</head>
<body>
	<%@ include file="/common/navbar.jsp"%>
	<%
	List<MovieBean> nowShowingMovies = (List<MovieBean>) request.getAttribute("nowShowingMovies");
	List<MovieBean> comingSoonMovies = (List<MovieBean>) request.getAttribute("comingSoonMovies");
	Map<String, List<MovieBean>> moviesByGenre =
			(Map<String, List<MovieBean>>) request.getAttribute("moviesByGenre");
	%>
	<div class="home-page">
		<section class="movie-section">
			<div class="section-header">
				<h2>Now Showing</h2>
				<a href="${pageContext.request.contextPath}/movies">View All</a>
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
				<a href="${pageContext.request.contextPath}/movies">View All</a>
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

		<section class="movie-section">
			<div class="section-header">
				<h2>Browse by Genre</h2>
			</div>

			<%
			if (moviesByGenre != null) {
				for (Map.Entry<String, List<MovieBean>> genreEntry : moviesByGenre.entrySet()) {
					List<MovieBean> genreMovies = genreEntry.getValue();
					if (genreMovies == null || genreMovies.isEmpty()) {
						continue;
					}
			%>
			<div class="genre-category">
				<div class="section-header">
					<h2><%=genreEntry.getKey()%></h2>
				</div>
				<div class="movie-container">
					<%
					for (MovieBean movie : genreMovies) {
						request.setAttribute("movie", movie);
					%>
					<jsp:include page="/components/movie-card.jsp" />
					<%
					}
					%>
				</div>
			</div>
			<%
				}
			}
			%>
		</section>

	</div>
	<div id="mobile-debug"></div>



</body>
</html>