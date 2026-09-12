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

		<div class="payment-container">

			<h1>Secure Checkout</h1>


			<form action="${pageContext.request.contextPath}/payment"
				method="post" class="checkout-container">

				<!-- Booking ID -->

				<input type="hidden" name="bookingId" value="${booking.id}">


				<!-- ================================================= -->
				<!-- LEFT SIDE -->
				<!-- ================================================= -->

				<section class="payment-left">


					<!-- ================= MOVIE CARD ================= -->

					<div class="movie-banner">

						<div class="banner-background"
							style="background-image: url('${booking.posterUrl}');"></div>

						<div class="banner-overlay"></div>

						<div class="movie-content">

							<img class="movie-poster" src="${booking.posterUrl}"
								alt="${booking.movieTitle}">

							<div class="movie-details">

								<h3>${booking.movieTitle}</h3>

								<p>${booking.mallName}</p>

								<p>${booking.showDate} &nbsp;•&nbsp; ${booking.startTime}</p>

							</div>

						</div>

					</div>


					<!-- ================= BOOKING CARD ================= -->

					<div class="booking-details-card">

						<div class="booking-details-header">

							<strong> ${booking.showDate} &nbsp;•&nbsp;
								${booking.startTime} </strong>

						</div>


						<div class="booking-details-content">

							<div>

								<strong> Booking </strong> <span>
									${booking.bookingReference} </span>

							</div>


							<strong> ₹${booking.totalAmount} </strong>

						</div>

					</div>


					<!-- ================= CANCELLATION ================= -->

					<div class="info-card">

						<i class="fa-solid fa-circle-info"></i> <span> This theatre
							allows cancellation </span>

					</div>


					<!-- ================= OFFERS ================= -->

					<div class="offers-card">

						<div class="offers-title">

							<i class="fa-solid fa-percent"></i> <strong> Offers </strong>

						</div>


						<button type="button">View all Offers</button>

					</div>


					<!-- ================= PAYMENT OPTIONS ================= -->

					<h2 class="payment-options-title">Payment Options</h2>


					<!-- ================= UPI ================= -->

					<div class="payment-option-block">

						<label class="payment-method"> <input type="radio"
							name="paymentMethod" value="UPI" checked>

							<div class="payment-icon">

								<i class="fa-solid fa-mobile-screen-button"></i>

							</div>


							<div class="payment-method-text">

								<h3>UPI</h3>

								<p>Pay using GPay, PhonePe or Paytm</p>

							</div>

						</label>


						<div class="payment-inputs upi-details">

							<label for="upiId"> UPI ID </label> <input type="text" id="upiId"
								name="upiId" placeholder="example@upi" autocomplete="off"
								inputmode="email">

						</div>

					</div>


					<!-- ================= CARD ================= -->

					<div class="payment-option-block">

						<label class="payment-method"> <input type="radio"
							name="paymentMethod" value="CARD">

							<div class="payment-icon">

								<i class="fa-regular fa-credit-card"></i>

							</div>


							<div class="payment-method-text">

								<h3>Credit / Debit Card</h3>

								<p>Visa, Mastercard, RuPay and more</p>

							</div>

						</label>


						<div class="payment-inputs card-details">

							<label for="cardNumber"> Card Number </label> <input type="text"
								id="cardNumber" name="cardNumber"
								placeholder="1234 5678 9012 3456" maxlength="19"
								autocomplete="cc-number" inputmode="numeric"> <label
								for="cardHolder"> Card Holder Name </label> <input type="text"
								id="cardHolder" name="cardHolder" placeholder="Name as on card"
								autocomplete="cc-name">


							<div class="card-row">


								<div class="card-field">

									<label for="expiryDate"> Expiry Date </label> <input
										type="text" id="expiryDate" name="expiryDate"
										placeholder="MM/YY" maxlength="5" autocomplete="cc-exp"
										inputmode="numeric">

								</div>


								<div class="card-field">

									<label for="cvv"> CVV </label> <input type="password" id="cvv"
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


				<!-- ================================================= -->
				<!-- RIGHT SIDE -->
				<!-- ================================================= -->

				<aside class="booking-summary">


					<!-- ================= SUMMARY TITLE ================= -->

					<h2>Payment summary</h2>


					<div class="summary-container">


						<div class="summary-row">

							<span> Order amount </span> <strong>
								₹${booking.totalAmount} </strong>

						</div>


						<div class="summary-row">

							<span> Taxes & fees </span> <strong> ₹0 </strong>

						</div>


						<div class="summary-total">

							<span> To be paid </span> <strong>
								₹${booking.totalAmount} </strong>

						</div>


					</div>


					<!-- ================= BOOKING DETAILS ================= -->

					<h2 class="details-title">Booking details</h2>


					<div class="user-details-card">


						<div class="user-icon">

							<i class="fa-regular fa-calendar"></i>

						</div>


						<div>

							<strong> ${booking.bookingReference} </strong>

							<p>${booking.movieTitle}</p>

							<p>${booking.mallName}</p>

						</div>


					</div>


					<!-- ================= TERMS ================= -->

					<div class="terms-card">

						<i class="fa-regular fa-circle-question"></i> <span> Terms
							and conditions </span>

					</div>


					<!-- ================= PAY BUTTON ================= -->

					<button type="submit" class="pay-button">

						<span> ₹${booking.totalAmount}  </span> <span> Proceed
							To Pay </span>

					</button>


				</aside>

			</form>

		</div>

	</main>


	<script src="${pageContext.request.contextPath}/assets/js/payment.js">
		
	</script>

</body>

</html>