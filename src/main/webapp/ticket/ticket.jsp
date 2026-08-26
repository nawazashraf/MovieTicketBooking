<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>E-Ticket</title>
</head>

<body>

	<h1>Movie E-Ticket</h1>

	<hr>

	<h3>${ticket.movieTitle}</h3>

	<p>
		<strong>Booking Reference:</strong> ${ticket.bookingReference}
	</p>

	<p>
		<strong>Mall:</strong> ${ticket.mallName}
	</p>

	<p>
		<strong>Show Date:</strong> ${ticket.showDate}
	</p>

	<p>
		<strong>Show Time:</strong> ${ticket.startTime}
	</p>

	<p>
		<strong>Total Amount:</strong> ₹${ticket.totalAmount}
	</p>

	<hr>

	<h3>Payment Details</h3>

	<p>
		<strong>Payment Method:</strong> ${ticket.paymentMethod}
	</p>

	<p>
		<strong>Transaction ID:</strong> ${ticket.transactionId}
	</p>

	<p>
		<strong>Payment Status:</strong> ${ticket.paymentStatus}
	</p>

</body>
</html>