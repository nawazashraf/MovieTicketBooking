<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Payment</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/common.css">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/payment.css">


</head>
<body>

	<%@ include file="/common/navbar.jsp"%>


	<main class="payment-page">

		<h1>Secure Checkout</h1>

		<form action="${pageContext.request.contextPath}/payment"
			method="post" class="checkout-container">

			<input type="hidden" name="bookingId" value="${booking.id}">

			<!-- LEFT: PAYMENT METHODS -->
			<section class="payment-options">

				<h2>Payment Options</h2>

				<label class="payment-method"> <input type="radio"
					name="paymentMethod" value="UPI" checked>

					<div>
						<h3>UPI</h3>
						<p>Pay using GPay, PhonePe or Paytm</p>
					</div>
				</label> <label class="payment-method"> <input type="radio"
					name="paymentMethod" value="CARD">

					<div>
						<h3>Credit / Debit Card</h3>
						<p>Visa, Mastercard, RuPay and more</p>
					</div>
				</label>

			</section>


			<!-- RIGHT: BOOKING SUMMARY -->
			<aside class="booking-summary">

				<h2>Booking Summary</h2>

				<div class="summary-row">
					<span>Booking Reference</span> <strong>${booking.bookingReference}</strong>
				</div>

				<div class="summary-row">
					<span>Status</span> <strong>${booking.bookingStatus}</strong>
				</div>

				<div class="summary-total">
					<span>Total Amount</span> <strong>₹${booking.totalAmount}</strong>
				</div>

				<button type="submit">Pay ₹${booking.totalAmount}</button>

			</aside>

		</form>

	</main>

</body>
</html>


