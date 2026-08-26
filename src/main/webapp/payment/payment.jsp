<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Payment</title>
</head>
<body>

	<h1>Payment Page</h1>

	<p>Booking ID: ${booking.id}</p>

	<p>Booking Reference: ${booking.bookingReference}</p>

	<p>Total Amount: ₹${booking.totalAmount}</p>

	<p>Status: ${booking.bookingStatus}</p>
</body>
</html>