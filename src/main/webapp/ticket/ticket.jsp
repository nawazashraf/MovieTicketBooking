<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>E-Ticket</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/ticket.css">
</head>

<body>

	<div class="ticket">

		<!-- Header -->
		<div class="ticket-header">
			<h1>Movie E-Ticket</h1>
			<p>Your booking has been confirmed</p>
		</div>

		<!-- Movie & Booking Details -->
		<div class="movie-section">

			<h2 class="movie-title">${ticket.movieTitle}</h2>

			<div class="booking-ref">
				<strong>Booking Reference:</strong> ${ticket.bookingReference}
			</div>

			<div class="details">

				<div class="detail">
					<span class="detail-label">Mall</span> <span class="detail-value">${ticket.mallName}</span>
				</div>

				<div class="detail">
					<span class="detail-label">Show Date</span> <span
						class="detail-value">${ticket.showDate}</span>
				</div>

				<div class="detail">
					<span class="detail-label">Show Time</span> <span
						class="detail-value">${ticket.startTime}</span>
				</div>

				<div class="detail">
					<span class="detail-label">Total Amount</span> <span
						class="detail-value">₹${ticket.totalAmount}</span>
				</div>

			</div>

		</div>

		<!-- Ticket Divider -->
		<div class="ticket-divider"></div>

		<!-- Payment Details -->
		<div class="payment-section">

			<h3>Payment Details</h3>

			<div class="payment-details">

				<div class="detail">
					<span class="detail-label">Payment Method</span> <span
						class="detail-value">${ticket.paymentMethod}</span>
				</div>

				<div class="detail">
					<span class="detail-label">Transaction ID</span> <span
						class="detail-value">${ticket.transactionId}</span>
				</div>

				<div class="detail">
					<span class="detail-label">Payment Status</span> <span
						class="detail-value"> <span class="status">${ticket.paymentStatus}</span>
					</span>
				</div>

			</div>

			<div class="seats">
				<span class="seats-label">Seats</span> <span class="seats-value">${ticket.seats}</span>
			</div>

		</div>

		<div class="ticket-actions">

			<!--  <a
				href="${pageContext.request.contextPath}/ticket/download?bookingId=${ticket.bookingId}"
				class="download-btn"> Download Ticket </a> -->

			<button onclick="window.print()" class="print-btn">Print
				Ticket</button>

		</div>


		<!-- Footer -->
		<div class="ticket-footer">Please carry this e-ticket for entry
			into the theatre.</div>

	</div>

</body>
</html>