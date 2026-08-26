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

	<form action="${pageContext.request.contextPath}/payment" method="post">
		<input type="hidden" name="bookingId" value="${booking.id}"> <label
			for="paymentMethod">Payment Method:</label> <select
			name="paymentMethod" id="paymentMethod" required>
			<option value="">-- Select Payment Method --</option>
			<option value="UPI">UPI</option>
			<option value="CARD">Credit / Debit Card</option>
		</select> <br>
		<br>

		<button type="submit">Pay ₹${booking.totalAmount}</button>
	</form>
</body>
</html>