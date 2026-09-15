<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="java.util.List"%>
<%@ page import="com.movieticket.model.MovieAnalyticsBean"%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Movie Analytics</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/analytics.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/common.css">

</head>

<body>

	<%@ include file="/common/navbar.jsp"%>
	<div class="analytics-page">



		<!-- ================= HEADER ================= -->

		<header class="analytics-header">

			<div class="header-content">

				<h1>Movie Analytics</h1>

				<p>Overview of your movie ticket booking system</p>

			</div>

			<div class="header-icon"></div>

		</header>


		<!-- ================= OVERVIEW ================= -->

		<section class="analytics-section">

			<div class="section-heading">

				<h2>Overview</h2>

				<p>Key performance metrics</p>

			</div>


			<div class="analytics-grid">


				<!-- TOTAL REVENUE -->

				<div class="analytics-card">

					<div class="card-header">

						<span class="card-label"> Total Revenue </span> <span
							class="card-icon revenue-icon"> ₹ </span>

					</div>

					<div class="card-value">₹ ${analytics.totalRevenue}</div>

					<p class="card-description">Revenue from successful payments</p>

				</div>


				<!-- TOTAL BOOKINGS -->

				<div class="analytics-card">

					<div class="card-header">

						<span class="card-label"> Total Bookings </span> <span
							class="card-icon booking-icon"> 🎟 </span>

					</div>

					<div class="card-value">${analytics.totalBookings}</div>

					<p class="card-description">All bookings created</p>

				</div>


				<!-- CONFIRMED BOOKINGS -->

				<div class="analytics-card">

					<div class="card-header">

						<span class="card-label"> Confirmed Bookings </span> <span
							class="card-icon confirmed-icon"> ✓ </span>

					</div>

					<div class="card-value">${analytics.confirmedBookings}</div>

					<p class="card-description">Successfully confirmed bookings</p>

				</div>


				<!-- TICKETS SOLD -->

				<div class="analytics-card">

					<div class="card-header">

						<span class="card-label"> Tickets Sold </span> <span
							class="card-icon ticket-icon"> 🎫 </span>

					</div>

					<div class="card-value">${analytics.totalTicketsSold}</div>

					<p class="card-description">Tickets from confirmed bookings</p>

				</div>


				<!-- TOTAL USERS -->

				<div class="analytics-card">

					<div class="card-header">

						<span class="card-label"> Total Users </span> <span
							class="card-icon user-icon"> 👤 </span>

					</div>

					<div class="card-value">${analytics.totalUsers}</div>

					<p class="card-description">Registered customers</p>

				</div>


				<!-- TODAY REVENUE -->

				<div class="analytics-card">

					<div class="card-header">

						<span class="card-label"> Today's Revenue </span> <span
							class="card-icon today-icon"> ↑ </span>

					</div>

					<div class="card-value">₹ ${analytics.revenueToday}</div>

					<p class="card-description">Successful payments today</p>

				</div>


				<!-- MONTH REVENUE -->

				<div class="analytics-card">

					<div class="card-header">

						<span class="card-label"> This Month </span> <span
							class="card-icon month-icon"> 📅 </span>

					</div>

					<div class="card-value">₹ ${analytics.revenueThisMonth}</div>

					<p class="card-description">Successful payments this month</p>

				</div>


			</div>

		</section>


		<!-- ================= DIVIDER ================= -->

		<div class="section-divider"></div>


		<!-- ================= TOP MOVIES ================= -->

		<section class="analytics-section">

			<div class="section-heading">

				<h2>Top Movies</h2>

				<p>Most popular movies based on tickets sold</p>

			</div>


			<div class="movie-table-card">

				<table class="movie-table">

					<thead>

						<tr>

							<th>#</th>

							<th>Movie</th>

							<th>Tickets Sold</th>

							<th>Revenue</th>

						</tr>

					</thead>


					<tbody>

						<%
						List<MovieAnalyticsBean> topMovies = (List<MovieAnalyticsBean>) request.getAttribute("topMovies");

						if (topMovies != null && !topMovies.isEmpty()) {

							int rank = 1;

							for (MovieAnalyticsBean movie : topMovies) {
						%>

						<tr>

							<td class="rank"><%=rank++%></td>

							<td class="movie-name"><%=movie.getMovieTitle()%></td>

							<td><span class="tickets-badge"> <%=movie.getTicketsSold()%>
							</span></td>

							<td class="movie-revenue">₹ <%=movie.getRevenue()%>
							</td>

						</tr>

						<%
						}

						} else {
						%>

						<tr>

							<td colspan="4" class="no-data">No movie analytics
								available.</td>

						</tr>

						<%
						}
						%>

					</tbody>

				</table>

			</div>

		</section>


	</div>

</body>

</html>

