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

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

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

				<!-- UPI OPTION -->
				<div class="payment-option-block">
					<label class="payment-method"> <input type="radio"
						name="paymentMethod" value="UPI" checked>

						<div class="payment-icon">
							<i class="fa-solid fa-mobile-screen-button"></i>
						</div>

						<div>
							<h3>UPI</h3>
							<p>Pay using GPay, PhonePe or Paytm</p>
						</div>
					</label>

					<div class="payment-inputs upi-details">
						<label for="upiId">UPI ID</label> <input type="text" id="upiId"
							name="upiId" placeholder="example@upi" autocomplete="off"
							inputmode="email">
					</div>
				</div>

				<!-- CARD OPTION -->
				<div class="payment-option-block">
					<label class="payment-method"> <input type="radio"
						name="paymentMethod" value="CARD">

						<div class="payment-icon">
							<i class="fa-regular fa-credit-card"></i>
						</div>

						<div>
							<h3>Credit / Debit Card</h3>
							<p>Visa, Mastercard, RuPay and more</p>
						</div>
					</label>

					<div class="payment-inputs card-details">
						<label for="cardNumber">Card Number</label> <input type="text"
							id="cardNumber" name="cardNumber"
							placeholder="1234 5678 9012 3456" maxlength="19"
							autocomplete="cc-number" inputmode="numeric"> <label
							for="cardHolder">Card Holder Name</label> <input type="text"
							id="cardHolder" name="cardHolder" placeholder="Name as on card"
							autocomplete="cc-name">

						<div class="card-row">
							<div class="card-field">
								<label for="expiryDate">Expiry Date</label> <input type="text"
									id="expiryDate" name="expiryDate" placeholder="MM/YY"
									maxlength="5" autocomplete="cc-exp" inputmode="numeric">
							</div>

							<div class="card-field">
								<label for="cvv">CVV</label> <input type="password" id="cvv"
									name="cvv" placeholder="•••" maxlength="4"
									autocomplete="cc-csc" inputmode="numeric">
							</div>
						</div>

						<p class="secure-note">
							<i class="fa-solid fa-lock"></i> Your card details are encrypted
							and never stored
						</p>
					</div>
				</div>

			</section>


			<!-- RIGHT: BOOKING SUMMARY -->
			<aside class="booking-summary">



				<div class="movie-banner">

					<div class="banner-background"
						style="background-image: url('${booking.posterUrl}');"></div>

					<div class="banner-overlay"></div>

					<div class="movie-content">

						<img class="movie-poster" src="${booking.posterUrl}"
							alt="${booking.movieTitle}" />

						<div class="movie-details">
							<h3>${booking.movieTitle}</h3>
							<p>${booking.mallName}</p>
							<p>${booking.showDate}•${booking.startTime}</p>
						</div>

					</div>

				</div>
				<div class="summary-container">
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


				</div>
			</aside>

		</form>

	</main>

	<script type="text/javascript"
		src="${pageContext.request.contextPath}/assets/js/payment.js"></script>

</body>
</html>