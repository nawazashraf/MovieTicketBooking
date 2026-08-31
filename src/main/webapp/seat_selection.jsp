<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="java.util.ArrayList"%>
<%@ page import="com.movieticket.model.SeatBean"%>
<%@ page import="com.movieticket.model.ShowBean"%>





<!DOCTYPE html>

<html>

<head>

<meta charset="UTF-8">

<title>Select Seats</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/seat.css">


</head>


<body>


	<%
	ShowBean show = (ShowBean) request.getAttribute("show");

	ArrayList<SeatBean> seats = (ArrayList<SeatBean>) request.getAttribute("seats");

	String showId = (String) request.getAttribute("showId");

	if (show == null) {
	%>

	<h2>Show not found</h2>

	<%
	return;
	}

	if (seats == null) {
	seats = new ArrayList<SeatBean>();
	}
	%>


	<!-- =====================================================
     HEADER
===================================================== -->

	<div class="header">

		<div class="logo">

			<div class="logo-icon"></div>

			<div class="logo-text">Online Movie Ticket</div>

		</div>


		<div class="movie-info">

			<h2>
				<%=show.getMovieName()%>
			</h2>

			<p>
				PVR:
				<%=show.getMallName()%>
			</p>

		</div>


		<button class="sign-in">Sign In</button>

	</div>



	<!-- =====================================================
     SHOW DATE / TIME
===================================================== -->

	<div class="show-bar">

		<div class="show-left">

			<span class="calendar"> ▣ </span> <span class="date"> <%=show.getShowDate()%>
			</span> <span class="time"> <%=show.getStartTime()%>
			</span>

		</div>


	</div>



	<!-- =====================================================
     SEAT LAYOUT
===================================================== -->

	<div class="container">


		<%
		/*
		 * These are the ACTUAL seat types
		 * from your database.
		 */

		String[] seatTypes = { "PREMIUM", "RECLINER", "REGULAR" };

		for (String currentType : seatTypes) {

			// -----------------------------------------------------
			// Check whether this seat type exists
			// -----------------------------------------------------

			boolean exists = false;

			for (SeatBean seat : seats) {

				if (seat.getTypeName().equalsIgnoreCase(currentType)) {

			exists = true;
			break;
				}
			}

			if (!exists) {
				continue;
			}

			// -----------------------------------------------------
			// Display name
			// -----------------------------------------------------

			String displayType = currentType;
		%>


		<!-- =====================================================
     SEAT TYPE SECTION
===================================================== -->

		<div class="seat-section">


			<div class="type-title">

				<%=displayType%>

				•


				<%
				// -------------------------------------------------
				// Find price for this seat type
				// -------------------------------------------------

				boolean priceFound = false;

				for (SeatBean seat : seats) {

					if (seat.getTypeName().equalsIgnoreCase(currentType) && !priceFound) {
				%>

				₹<%=String.format("%.2f", seat.getPrice())%>

				<%
				priceFound = true;
				}
				}
				%>

			</div>


			<%
			String currentRow = "";

			// -----------------------------------------------------
			// Display seats
			// -----------------------------------------------------

			for (SeatBean seat : seats) {

				String type = seat.getTypeName();

				// Only current seat type
				if (!type.equalsIgnoreCase(currentType)) {
					continue;
				}

				String row = seat.getRowName();

				int number = seat.getSeatNumber();

				String showSeatId = seat.getShowSeatId();

				double price = seat.getPrice();

				String status = seat.getStatus();

				// -------------------------------------------------
				// NEW ROW
				// -------------------------------------------------

				if (!row.equals(currentRow)) {

					if (!currentRow.equals("")) {
			%>

		</div>

		<%
		}

		currentRow = row;
		%>

		<div class="row">

			<span class="row-name"> <%=row%>
			</span>

			<%
			}

			// -------------------------------------------------
			// BOOKED
			// -------------------------------------------------

			if (status.equalsIgnoreCase("BOOKED")) {
			%>

			<button type="button" class="seat booked" disabled>

				<%=number%>

			</button>

			<%
			}

			// -------------------------------------------------
			// AVAILABLE
			// -------------------------------------------------

			else {
			%>

			<button type="button" class="seat available" id="<%=showSeatId%>"
				data-seat="<%=row + number%>" data-price="<%=price%>"
				onclick="selectSeat(this)">

				<%=number%>

			</button>

			<%
			}

			}

			// -----------------------------------------------------
			// CLOSE LAST ROW
			// -----------------------------------------------------

			if (!currentRow.equals("")) {
			%>

		</div>

		<%
		}
		%>

	</div>

	<%
	}
	%>


	<!-- =====================================================
     SCREEN
===================================================== -->

	<div class="screen-area">

		<div class="screen"></div>

		<div class="screen-text">SCREEN THIS WAY</div>

	</div>



	<!-- =====================================================
     LEGEND
===================================================== -->

	<div class="legend">


		<div class="legend-item">

			<span class="legend-box legend-available"> </span> Available

		</div>


		<div class="legend-item">

			<span class="legend-box legend-occupied"> </span> Occupied

		</div>


		<div class="legend-item">

			<span class="legend-box legend-selected"> </span> Selected

		</div>


	</div>



	<!-- =====================================================
     BOTTOM BAR
===================================================== -->

	<div class="bottom">


		<div class="bottom-left">


			<div class="count">

				<b id="seatCount"> 0 </b> Seats Selected

			</div>


			<div class="total">

				Total: ₹ <span id="total"> 0 </span>

			</div>


		</div>



		<div class="bottom-center">

			<div class="selected-list" id="selectedText">No seats selected

			</div>

		</div>



		<div class="bottom-right">


			<form action="${pageContext.request.contextPath}/booking"
				method="post" onsubmit="return checkSeats();">


				<input type="hidden" name="showId" value="<%=showId%>"> <input
					type="hidden" name="selectedSeats" id="selectedSeatsInput">


				<button type="submit" class="continue" id="continueButton" disabled>

					Continue</button>


			</form>


			<div class="continue-message" id="continueMessage">Select your
				seats to continue</div>


		</div>

	</div>



	<script src="${pageContext.request.contextPath}/assets/js/seat.js"></script>


</body>

</html>