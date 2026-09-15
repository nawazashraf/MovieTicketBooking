
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Secure Payment</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/qr-payment.css">

</head>

<body>

	<div class="payment-page">

		<div class="payment-card">

			<!-- PAYMENT HEADER -->

			<div class="payment-header">

				<div class="payment-logo">OM</div>

				<div class="header-text">

					<div class="merchant-name">Online Movie Tickets</div>

					<div class="payment-status">

						<span class="check"> ✓ </span> Secure Payment Gateway

					</div>

				</div>

			</div>


			<!-- AMOUNT -->

			<div class="amount-section">

				<div class="amount-label">Total Amount</div>

				<div class="amount">₹${booking.totalAmount}</div>

				<div class="payment-for">Movie Ticket Booking</div>

			</div>


			<!-- PAYMENT DETAILS -->

			<div class="section-title">Payment Details</div>

			<div class="details">

				<div class="detail-row">

					<span class="label"> Merchant </span> <span class="value">
						Online Movie Tickets </span>

				</div>


				<div class="detail-row">

					<span class="label"> Bank </span> <span class="value"> HDFC
						Bank </span>

				</div>


				<div class="detail-row">

					<span class="label"> Payment Method </span> <span class="value">
						UPI </span>

				</div>


				<div class="detail-row">

					<span class="label"> UPI ID </span> <span class="value">
						onlinemovie@hdfcbank </span>

				</div>


				<div class="detail-row">

					<span class="label"> Order ID </span> <span class="value">
						${booking.bookingReference} </span>

				</div>


				<div class="detail-row">

					<span class="label"> Movie </span> <span class="value">
						${booking.movieTitle} </span>

				</div>

			</div>


			<!-- PAYMENT NOTICE -->

			<div class="payment-notice">

				<div class="notice-icon">🔒</div>

				<div>

					<div class="notice-title">Secure Transaction</div>

					<div class="notice-text">Your payment information is
						protected using secure payment processing.</div>

				</div>

			</div>


			<!-- PAY BUTTON -->

			<form action="${pageContext.request.contextPath}/payment"
				id="dummyPaymentForm" method="post"
				onsubmit="startDummyPayment(event)">

				<input type="hidden" name="bookingId" value="${booking.id}">

				<input type="hidden" name="dummyPayment" value="true">

				<button type="submit" class="pay-button" id="dummyPayButton">

					Pay Now ₹${booking.totalAmount}</button>

			</form>


			<!-- FOOTER -->

			<div class="payment-footer">

				<span> ✓ Verified Merchant </span> <span> • </span> <span> 🔒
					Secure Checkout </span>

			</div>


			<div class="powered">Powered by Online Movie Tickets</div>

		</div>

	</div>


	<script
		src="${pageContext.request.contextPath}/assets/js/qr-payment.js"></script>

</body>

</html>

