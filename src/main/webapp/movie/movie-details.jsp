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


		<!-- Date Selector -->
		<!-- Date Selector -->
		<div class="date-selector">

			<button type="button" class="date-btn active" data-date="undefined">

				<span class="date-day">ALL</span> <span class="date-number">●</span>
				<span class="date-month">SHOWS</span>

			</button>

			<%
			if (showsByDate != null && !showsByDate.isEmpty()) {

				for (String date : showsByDate.keySet()) {

					java.sql.Date showDate = java.sql.Date.valueOf(date);

					java.time.LocalDate localDate = showDate.toLocalDate();

					java.time.LocalDate today = java.time.LocalDate.now();

					String dayLabel;

					if (localDate.equals(today)) {

				dayLabel = "TODAY";

					} else if (localDate.equals(today.plusDays(1))) {

				dayLabel = "TOMORROW";

					} else {

				dayLabel = localDate.getDayOfWeek().toString().substring(0, 3);
					}
			%>

			<button type="button" class="date-btn" data-date="<%=date%>">

				<span class="date-day"> <%=dayLabel%>
				</span> <span class="date-number"> <%=localDate.getDayOfMonth()%>
				</span> <span class="date-month"> <%=localDate.getMonth().toString().substring(0, 3)%>
				</span>

			</button>

			<%
			}
			}
			%>

		</div>


		<!-- Shows -->
		<%
		if (showsByDate != null && !showsByDate.isEmpty()) {

			for (Map.Entry<String, List<ShowBean>> entry : showsByDate.entrySet()) {

				String date = entry.getKey();
				List<ShowBean> dateShows = entry.getValue();
		%>

		<!-- Date -->
		<div class="show-date" data-date="<%=date%>">

			<div class="date-heading">

				<h3>
					<%=date%>
				</h3>

			</div>


			<%
			String currentMall = null;

			for (ShowBean show : dateShows) {

				String mallName = show.getMallName();

				if (!mallName.equals(currentMall)) {

					if (currentMall != null) {
			%>

		</div>
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


			<!-- Showtime -->
			<button type="button"
				class="show-time-btn <%=show.getAvailableSeats() == 0 ? "sold-out" : ""%>"
				data-show-id="<%=show.getShowId()%>" data-show-date="<%=date%>"
				data-mall-name="<%=show.getMallName()%>"
				data-show-time="<%=show.getStartTime()%>"
				<%=show.getAvailableSeats() == 0 ? "disabled" : ""%>>

				<span class="show-time"> <%=show.getStartTime()%>
				</span> <span class="available-seats"> <%=show.getAvailableSeats() == 0 ? "Sold Out" : show.getAvailableSeats() + " seats"%>
				</span>

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


	<!-- Booking Summary -->
	<div class="booking-summary" id="bookingSummary">

		<div>
			<span>Selected Showtime</span> <strong id="selectedShow">
				Please select a showtime </strong>
		</div>

		<button type="button" id="proceedButton" disabled>Proceed</button>

	</div>


	</div>


	<!-- JavaScript -->

	<script>
    	const contextPath = "<%=request.getContextPath()%>
		";
	</script>

	<script
		src="${pageContext.request.contextPath}/assets/js/movie-details.js">
		
	</script>

</body>

</html>