<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="com.movieticket.model.MovieBean"%>
<%@ page import="com.movieticket.model.ShowBean"%>

<%
MovieBean movie = (MovieBean) request.getAttribute("movie");

Map<String, List<ShowBean>> showsByDate = (Map<String, List<ShowBean>>) request.getAttribute("showsByDate");
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

	<!-- Movie Hero Section -->

	<div class="movie-hero">

		<div class="movie-backdrop"
			style="background-image: url('<%=movie.getPosterUrl()%>');"></div>

		<div class="movie-hero-content">

			<div class="movie-poster">

				<img src="<%=movie.getPosterUrl()%>"
					alt="<%=movie.getTitle()%> Poster">

			</div>

			<div class="movie-info">

				<h1>
					<%=movie.getTitle()%>
				</h1>

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


	<!-- Shows Section -->

	<div class="shows-section">

		<h2>Available Shows</h2>

		<div class="date-selector">

			<button type="button" class="date-btn active">All</button>

			<%
			if (showsByDate != null && !showsByDate.isEmpty()) {

				for (String date : showsByDate.keySet()) {
			%>

			<button type="button" class="date-btn" data-date="<%=date%>">

				<%=date%>

			</button>

			<%
			}
			}
			%>

		</div>


		<%
		if (showsByDate != null && !showsByDate.isEmpty()) {

			for (Map.Entry<String, List<ShowBean>> entry : showsByDate.entrySet()) {

				String date = entry.getKey();

				List<ShowBean> dateShows = entry.getValue();
		%>


		<!-- Date -->

		<div class="show-date" data-date="<%=date%>">

			<div class="date-heading">
				<h3><%=date%></h3>
			</div>


			<%
			String currentMall = null;

			for (ShowBean show : dateShows) {

				String mallName = show.getMallName();

				if (!mallName.equals(currentMall)) {

					if (currentMall != null) {
			%>

		</div>

		<%
		}

		currentMall = mallName;
		%>


		<!-- Mall -->

		<div class="show-list">

			<div class="show-mall">

				<strong> <%=mallName%>
				</strong>

			</div>


			<!-- Show Times -->

			<div class="show-times">

				<%
				}
				%>



				<button type="button" class="show-time-btn"
					data-show-id="<%=show.getShowId()%>">
					<%=show.getStartTime()%>
				</button>


				<%
				}

				if (currentMall != null) {
				%>

			</div>

		</div>

		<%
		}
		%>

	</div>


	<%
	}

	} else {
	%>


	<p class="no-shows">No shows available for this movie.</p>


	<%
	}
	%>

	</div>



	<script type="text/javascript"
		src="${pageContext.request.contextPath}/assets/js/movie-details.js"></script>

</body>

</html>