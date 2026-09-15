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
	} else {

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

			<div class="logo-text">Online Movie Tickets</div>

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

	</div>



	<!-- =====================================================
     SHOW DATE / TIME
===================================================== -->

	<div class="show-bar">

		<div class="show-left">

			<span class="calendar">▣</span> <span class="date"> <%=show.getShowDate()%>
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
		 * Seat types from database:
		 *
		 * PREMIUM
		 * RECLINER
		 * REGULAR
		 */

		String[] seatTypes = {"PREMIUM", "RECLINER", "REGULAR"};

		for (String currentType : seatTypes) {

			// Check if this seat type exists
			boolean exists = false;

			for (SeatBean seat : seats) {

				if (seat.getTypeName() != null && seat.getTypeName().equalsIgnoreCase(currentType)) {

			exists = true;
			break;
				}
			}

			if (!exists) {
				continue;
			}

			// Find price for this category
			double categoryPrice = 0;

			for (SeatBean seat : seats) {

				if (seat.getTypeName() != null && seat.getTypeName().equalsIgnoreCase(currentType)) {

			categoryPrice = seat.getPrice();
			break;
				}
			}
		%>


		<!-- =====================================================
     SEAT TYPE SECTION
===================================================== -->

		<div class="seat-section">

			<div class="type-title">

				<span> <%=currentType%>
				</span> <span>•</span> <span class="type-price"> ₹<%=String.format("%.2f", categoryPrice)%>
				</span>

			</div>


			<!-- =================================================
	     SEAT ROWS -- each row now renders as 3 blocks
	     (LEFT wing / CENTER / RIGHT wing) so the LEFT and
	     RIGHT sections can curve independently instead of
	     being one flat 10-seat strip.
	================================================= -->

			<div class="seat-layout">

				<%
				String currentRow = "";
				String currentSection = "";
				double curveAngle = 0;

				// Rows are always returned ordered by row_name then
				// seat_number, and within a row seat_number runs
				// LEFT -> CENTER -> RIGHT (see the seed script), so
				// section changes are encountered in that fixed order.
				for (SeatBean seat : seats) {

					String type = seat.getTypeName();

					if (type == null || !type.equalsIgnoreCase(currentType)) {
						continue;
					}

					String row = seat.getRowName();

					if (row == null) {
						continue;
					}

					String section = seat.getSection();

					if (section == null) {
						section = "CENTER";
					}

					int number = seat.getSeatNumber();

					String showSeatId = seat.getShowSeatId();

					double price = seat.getPrice();

					String status = seat.getStatus();

					// =============================================
					// NEW ROW
					// =============================================

					if (!row.equals(currentRow)) {

						// Close previous row (last section-grid + the
						// seat-blocks wrapper + the seat-row itself)
						if (!currentRow.equals("")) {
				%>

			</div>
		</div>
	</div>

	<%
	}

	currentRow = row;
	currentSection = "";

	// Rows curve more near the screen (row A) and flatten out
	// toward the back (row H onward). Tune the 1.4 multiplier
	// and the "8" midpoint to taste -- this is a visual
	// approximation, not a geometric projection.
	int rowIndex = Character.toUpperCase(row.charAt(0)) - 'A' + 1;
	curveAngle = Math.max(0, (8 - rowIndex)) * 1.4;
	%>

	<div class="seat-row">

		<span class="row-name"> <%=row%>
		</span>

		<div class="seat-blocks">

			<%
			}

			// =============================================
			// NEW SECTION WITHIN THE ROW (LEFT / CENTER / RIGHT)
			// =============================================

			if (!section.equalsIgnoreCase(currentSection)) {

			if (!currentSection.equals("")) {
			%>

		</div>

		<%
		}

		currentSection = section;

		String sectionClass = "seat-grid-" + section.toLowerCase();
		String curveStyle = "";

		if (section.equalsIgnoreCase("LEFT")) {
		curveStyle = "transform: rotate(-" + String.format("%.1f", curveAngle) + "deg); transform-origin: right center;";
		} else if (section.equalsIgnoreCase("RIGHT")) {
		curveStyle = "transform: rotate(" + String.format("%.1f", curveAngle) + "deg); transform-origin: left center;";
		}
		%>

		<div class="seat-grid <%=sectionClass%>" style="<%=curveStyle%>">

			<%
			}

			// =============================================
			// BOOKED
			// =============================================

			if ("BOOKED".equalsIgnoreCase(status)) {
			%>

			<button type="button" class="seat booked" disabled>

				<%=number%>

			</button>

			<%
			}

			// =============================================
			// AVAILABLE
			// =============================================

			else if ("AVAILABLE".equalsIgnoreCase(status)) {
			%>

			<button type="button" class="seat available" id="<%=showSeatId%>"
				data-seat="<%=row + number%>" data-price="<%=price%>"
				onclick="selectSeat(this)">

				<%=number%>

			</button>

			<%
			}

			// =============================================
			// HELD / OTHER STATUS
			// =============================================

			else {
			%>

			<button type="button" class="seat booked" disabled>

				<%=number%>

			</button>

			<%
			}

			}

			// =============================================
			// CLOSE LAST SECTION + LAST ROW
			// =============================================

			if (!currentRow.equals("")) {
			%>

		</div>
	</div>
	</div>

	<%
	}
	%>

	</div>

	</div>


	<%
	}
	%>

	</div>



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

			<span class="legend-box legend-available"></span> Available

		</div>


		<div class="legend-item">

			<span class="legend-box legend-occupied"></span> Occupied

		</div>


		<div class="legend-item">

			<span class="legend-box legend-selected"></span> Selected

		</div>

	</div>



	<!-- =====================================================
     BOTTOM BAR
===================================================== -->

	<div class="bottom">

		<div class="bottom-left">

			<div class="count">

				<b id="seatCount">0</b> Seats Selected

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



	<script src="${pageContext.request.contextPath}/assets/js/seat.js">
		
	</script>

	<%
	} // end else (show != null)
	%>

</body>
</html>
