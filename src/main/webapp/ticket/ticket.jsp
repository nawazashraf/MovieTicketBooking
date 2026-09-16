
<%@ page import="com.movieticket.model.TicketBean"%>
<%
TicketBean ticket = (TicketBean) request.getAttribute("ticket");
%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>

<head>

<script>
	var ticketBookingReference = "${ticket.bookingReference}";
	var ticketBookingId = "${ticket.bookingId}";
	var ticketMovieTitle = "${ticket.movieTitle}";
	var ticketMallName = "${ticket.mallName}";
	var ticketShowDate = "${ticket.showDate}";
	var ticketStartTime = "${ticket.startTime}";
	var ticketSeats = "${ticket.seats}";
</script>



<script src="${pageContext.request.contextPath}/assets/js/ticket.js"></script>

<meta charset="UTF-8">

<title>Movie E-Ticket</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/ticket.css">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/common.css">

</head>

<body>

	<%@ include file="/common/navbar.jsp"%>
	<div class="ticket">

		<!-- HEADER -->

		<div class="ticket-header">

			<div>
				<h1>Movie E-Ticket</h1>

				<p>Booking Confirmed</p>
			</div>

			<div class="confirmed">✓ CONFIRMED</div>

		</div>


		<!-- MOVIE DETAILS -->

		<div class="movie-section">

			<div class="movie-poster">

				<img src="${ticket.posterUrl}" alt="${ticket.movieTitle}">

			</div>


			<div class="movie-info">

				<h2 class="movie-title">${ticket.movieTitle}</h2>

				<div class="movie-meta">

					<span>${ticket.language}</span> <span>•</span> <span>${ticket.certificate}</span>

					<span>•</span> <span>${ticket.durationMinutes} min</span>

				</div>

				<div class="genre">${ticket.genre}</div>

				<div class="booking-ref">

					<span class="detail-label"> Booking Reference </span> <strong>
						${ticket.bookingReference} </strong>

				</div>

			</div>

		</div>


		<!-- CINEMA DETAILS -->

		<div class="section">

			<h3 class="section-title">Cinema Details</h3>

			<div class="cinema-box">

				<div class="detail">

					<span class="detail-label"> Cinema / Mall </span> <span
						class="detail-value"> ${ticket.mallName} </span>

				</div>


				<div class="detail">

					<span class="detail-label"> Address </span> <span
						class="detail-value"> ${ticket.mallAddress} </span>

				</div>


				<div class="detail">

					<span class="detail-label"> City </span> <span class="detail-value">
						${ticket.city} </span>

				</div>


				<div class="detail">

					<span class="detail-label"> State </span> <span
						class="detail-value"> ${ticket.state} </span>

				</div>


				<div class="detail">

					<span class="detail-label"> Pincode </span> <span
						class="detail-value"> ${ticket.pincode} </span>

				</div>

			</div>

		</div>


		<!-- SHOW DETAILS -->

		<div class="section">

			<h3 class="section-title">Show Details</h3>

			<div class="show-details">

				<div class="show-item">

					<span class="detail-label"> DATE </span> <span class="show-value">
						${ticket.showDate} </span>

				</div>


				<div class="show-item">

					<span class="detail-label"> START TIME </span> <span
						class="show-value"> ${ticket.startTime} </span>

				</div>


				<div class="show-item">

					<span class="detail-label"> END TIME </span> <span
						class="show-value"> ${ticket.endTime} </span>

				</div>

			</div>

		</div>

		<!-- SEAT DETAILS -->

		<div class="section">
			<h3 class="section-title">Seat Details</h3>

			<div class="seats-box">

				<div class="seat-header">
					<span>Seat</span> <span>Type</span> <span>Total Price</span>
				</div>

				<%
				String[] seats = ticket.getSeats().split("\\|");
				String[] seatTypes = ticket.getSeatTypes().split("\\|");
				String[] seatPrices = ticket.getSeatPrices().split("\\|");

				for (int i = 0; i < seatTypes.length; i++) {
				%>

				<div class="seat-row">
					<span class="seat-number"><%=seats[i]%></span> <span><%=seatTypes[i]%></span>
					<span>₹<%=seatPrices[i]%></span>
				</div>

				<%
				}
				%>

			</div>
		</div>





		<!-- CUSTOMER DETAILS -->

		<div class="section">

			<h3 class="section-title">Customer Details</h3>

			<div class="customer-details">

				<div class="detail">

					<span class="detail-label"> NAME </span> <span class="detail-value">
						${ticket.customerName} </span>

				</div>


				<div class="detail">

					<span class="detail-label"> EMAIL </span> <span
						class="detail-value"> ${ticket.customerEmail} </span>

				</div>


				<div class="detail">

					<span class="detail-label"> PHONE </span> <span
						class="detail-value"> ${ticket.customerPhone} </span>

				</div>

			</div>

		</div>


		<!-- PAYMENT DETAILS -->

		<div class="section">

			<h3 class="section-title">Payment Details</h3>

			<div class="payment-details">

				<div class="detail">

					<span class="detail-label"> PAYMENT METHOD </span> <span
						class="detail-value"> ${ticket.paymentMethod} </span>

				</div>


				<div class="detail">

					<span class="detail-label"> TRANSACTION ID </span> <span
						class="detail-value"> ${ticket.transactionId} </span>

				</div>


				<div class="detail">

					<span class="detail-label"> PAYMENT STATUS </span> <span
						class="status"> ${ticket.paymentStatus} </span>

				</div>


				<div class="detail">

					<span class="detail-label"> PAID AT </span> <span
						class="detail-value"> ${ticket.paidAt} </span>

				</div>

			</div>

		</div>


		<!-- TOTAL -->

		<div class="total-section">

			<div>

				<span class="detail-label"> TOTAL AMOUNT </span> <span
					class="total-label"> Amount Paid </span>

			</div>

			<div class="total-amount">₹${ticket.totalAmount}</div>

		</div>


		<!-- BOOKING INFORMATION -->

		<div class="booking-section">

			<div class="booking-info">

				<h3>Booking Information</h3>

				<div class="detail">

					<span class="detail-label"> BOOKING ID </span> <span
						class="detail-value"> ${ticket.bookingId} </span>

				</div>


				<div class="detail">

					<span class="detail-label"> BOOKING STATUS </span> <span
						class="status"> ${ticket.bookingStatus} </span>

				</div>


				<div class="detail">

					<span class="detail-label"> BOOKED ON </span> <span
						class="detail-value"> ${ticket.bookingDate} </span>

				</div>

			</div>


			<div class="qr-box">
				<img id="qrcode" alt="Ticket QR Code">
			</div>

		</div>


		<div class="ticket-footer">

			<strong>Please arrive 15–20 minutes before the show.</strong>

			<p>Carry this e-ticket and a valid ID for entry into the theatre.
			</p>
			<p>Your e-ticket has been mailed successfully to your registered
				email address.</p>

			<p>Tickets are subject to the cinema's cancellation and refund
				policy.</p>

		</div>

	</div>

</body>

</html>
