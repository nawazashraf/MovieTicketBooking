<%@page import="com.movieticket.model.ShowBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.LinkedHashMap"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.List"%>
<%@ page import="com.movieticket.model.SeatBean"%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Select Seats</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

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

	<div class="error-container">

		<h2>Show not found</h2>

	</div>

	<%
	} else {

	if (seats == null) {
		seats = new ArrayList<SeatBean>();
	}

	/* =========================================================
	   GROUP SEATS BY ROW
	   ========================================================= */

	LinkedHashMap<String, ArrayList<SeatBean>> rows = new LinkedHashMap<String, ArrayList<SeatBean>>();

	for (SeatBean seat : seats) {

		String row = seat.getRowName();

		if (row == null || row.trim().isEmpty()) {
			continue;
		}

		if (!rows.containsKey(row)) {

			rows.put(row, new ArrayList<SeatBean>());

		}

		rows.get(row).add(seat);
	}

	/* =========================================================
	   REVERSE ROW ORDER

	   Database:
	   A B C D E F G

	   Display:
	   G F E D C B A

	   G = closest to screen
	   A = farthest from screen
	   ========================================================= */

	List<Map.Entry<String, ArrayList<SeatBean>>> rowList = new ArrayList<Map.Entry<String, ArrayList<SeatBean>>>(
			rows.entrySet());
	%>


	<!-- =========================================================
     HEADER
========================================================= -->

	<header class="header">

		<div class="logo">

			<div class="logo-icon">▶</div>

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

	</header>


	<!-- =========================================================
     SHOW BAR
========================================================= -->

	<div class="show-bar">

		<div class="show-left">

			<span class="calendar"> ▣ </span> <span class="date"> <%=show.getShowDate()%>
			</span> <span class="time"> <%=show.getStartTime()%>
			</span>

		</div>

	</div>


	<!-- =========================================================
     MAIN
========================================================= -->

	<div class="container">


		<div class="seat-map-title">SELECT YOUR SEATS</div>


		<!-- =====================================================
	     SEAT LAYOUT
	====================================================== -->

		<div class="cinema-layout">


			<%
			int rowNumber = 0;

			for (int i = rowList.size() - 1; i >= 0; i--) {

				rowNumber++;

				Map.Entry<String, ArrayList<SeatBean>> entry = rowList.get(i);

				String rowName = entry.getKey();

				ArrayList<SeatBean> rowSeats = entry.getValue();
			%>


			<!-- =================================================
		     ROW
		================================================== -->

			<div class="seat-row row-<%=rowName%>">


				<!-- =============================================
			     ROW LABEL
			============================================== -->

				<div class="row-label">

					<%=rowName%>

				</div>


				<!-- =============================================
			     LEFT SECTION
			============================================== -->

				<div class="seat-group left-group">

					<%
					for (SeatBean seat : rowSeats) {

						String section = seat.getSection();

						if (section == null) {
							section = "CENTER";
						}

						if (!section.equalsIgnoreCase("LEFT")) {
							continue;
						}

						String status = seat.getStatus();

						String showSeatId = seat.getShowSeatId();

						String seatNumber = String.valueOf(seat.getSeatNumber());

						String price = String.valueOf(seat.getPrice());

						String type = seat.getTypeName();

						String typeClass = "";

						if (type != null) {
							typeClass = type.toLowerCase().replace(" ", "-");
						}

						if ("BOOKED".equalsIgnoreCase(status)) {
					%>

					<button type="button" class="seat booked <%=typeClass%>" disabled>

						<%=seatNumber%>

					</button>

					<%
					} else if ("AVAILABLE".equalsIgnoreCase(status)) {
					%>

					<button type="button" class="seat available <%=typeClass%>"
						id="<%=showSeatId%>" data-seat="<%=rowName + seatNumber%>"
						data-price="<%=price%>" data-type="<%=type%>"
						onclick="selectSeat(this)">

						<%=seatNumber%>

					</button>

					<%
					} else {
					%>

					<button type="button" class="seat booked <%=typeClass%>" disabled>

						<%=seatNumber%>

					</button>

					<%
					}

					}
					%>

				</div>


				<!-- =============================================
			     AISLE
			============================================== -->

				<div class="aisle"></div>


				<!-- =============================================
			     CENTER SECTION
			============================================== -->

				<div class="seat-group center-group">

					<%
					for (SeatBean seat : rowSeats) {

						String section = seat.getSection();

						if (section == null) {
							section = "CENTER";
						}

						if (!section.equalsIgnoreCase("CENTER")) {
							continue;
						}

						String status = seat.getStatus();

						String showSeatId = seat.getShowSeatId();

						String seatNumber = String.valueOf(seat.getSeatNumber());

						String price = String.valueOf(seat.getPrice());

						String type = seat.getTypeName();

						String typeClass = "";

						if (type != null) {
							typeClass = type.toLowerCase().replace(" ", "-");
						}

						if ("BOOKED".equalsIgnoreCase(status)) {
					%>

					<button type="button" class="seat booked <%=typeClass%>" disabled>

						<%=seatNumber%>

					</button>

					<%
					} else if ("AVAILABLE".equalsIgnoreCase(status)) {
					%>

					<button type="button" class="seat available <%=typeClass%>"
						id="<%=showSeatId%>" data-seat="<%=rowName + seatNumber%>"
						data-price="<%=price%>" data-type="<%=type%>"
						onclick="selectSeat(this)">

						<%=seatNumber%>

					</button>

					<%
					} else {
					%>

					<button type="button" class="seat booked <%=typeClass%>" disabled>

						<%=seatNumber%>

					</button>

					<%
					}

					}
					%>

				</div>


				<!-- =============================================
			     AISLE
			============================================== -->

				<div class="aisle"></div>


				<!-- =============================================
			     RIGHT SECTION
			============================================== -->

				<div class="seat-group right-group">

					<%
					for (SeatBean seat : rowSeats) {

						String section = seat.getSection();

						if (section == null) {
							section = "CENTER";
						}

						if (!section.equalsIgnoreCase("RIGHT")) {
							continue;
						}

						String status = seat.getStatus();

						String showSeatId = seat.getShowSeatId();

						String seatNumber = String.valueOf(seat.getSeatNumber());

						String price = String.valueOf(seat.getPrice());

						String type = seat.getTypeName();

						String typeClass = "";

						if (type != null) {
							typeClass = type.toLowerCase().replace(" ", "-");
						}

						if ("BOOKED".equalsIgnoreCase(status)) {
					%>

					<button type="button" class="seat booked <%=typeClass%>" disabled>

						<%=seatNumber%>

					</button>

					<%
					} else if ("AVAILABLE".equalsIgnoreCase(status)) {
					%>

					<button type="button" class="seat available <%=typeClass%>"
						id="<%=showSeatId%>" data-seat="<%=rowName + seatNumber%>"
						data-price="<%=price%>" data-type="<%=type%>"
						onclick="selectSeat(this)">

						<%=seatNumber%>

					</button>

					<%
					} else {
					%>

					<button type="button" class="seat booked <%=typeClass%>" disabled>

						<%=seatNumber%>

					</button>

					<%
					}

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
	====================================================== -->

		<div class="screen-area">

			<div class="screen"></div>

			<div class="screen-text">SCREEN THIS WAY</div>

		</div>


		<!-- =====================================================
	     SEAT CATEGORY + STATUS LEGEND
	     BELOW SCREEN
	====================================================== -->

		<div class="legend">


			<!-- PREMIUM -->

			<div class="legend-item">

				<span class="legend-box legend-premium"> </span> Premium ₹250

			</div>


			<!-- REGULAR -->

			<div class="legend-item">

				<span class="legend-box legend-regular"> </span> Regular ₹150

			</div>


			<!-- RECLINER -->

			<div class="legend-item">

				<span class="legend-box legend-recliner"> </span> Recliner ₹350

			</div>


			<!-- AVAILABLE -->

			<div class="legend-item">

				<span class="legend-box legend-available"></span> Available

			</div>


			<!-- OCCUPIED -->

			<div class="legend-item">

				<span class="legend-box legend-occupied"></span> Occupied

			</div>


			<!-- SELECTED -->

			<div class="legend-item">

				<span class="legend-box legend-selected"></span> Selected

			</div>


		</div>


	</div>


	<!-- =========================================================
     BOTTOM BAR
========================================================= -->

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


	<script src="${pageContext.request.contextPath}/assets/js/seat.js">
		
	</script>


	<%
	}
	%>

</body>

</html>
