<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>Forgot Password - MovieBook</title>

<link rel="stylesheet" href="assets/css/forgotpassword.css">

</head>

<body>

	<div class="forgot-page">

		<div class="forgot-card">

			<div class="brand">

				<div class="brand-logo">M</div>

				<div class="brand-name">MovieBook</div>

			</div>


			<div class="forgot-icon">🔐</div>


			<div class="forgot-header">

				<h1>Forgot Password?</h1>

				<p>Enter your email address to reset your password.</p>

			</div>


			<div class="error-message" id="emailError">

				<span class="error-icon">!</span> <span id="emailErrorText"></span>

			</div>


			<form id="forgotPasswordForm">


				<!-- EMAIL -->

				<div class="form-group">

					<label for="email"> Email Address </label>


					<div class="email-row">


						<div class="email-input-wrapper">

							<span class="input-icon"> ✉ </span> <input type="email"
								id="email" name="email" placeholder="Enter your email address"
								autocomplete="email">

						</div>


						<button type="button" id="verifyEmailButton"
							class="verify-email-button" disabled>Verify Email</button>


					</div>


					<div class="field-error" id="emailFieldError"></div>


				</div>



				<!-- OTP TRAY -->

				<div class="otp-tray" id="otpTray">


					<div class="otp-header">


						<div>

							<strong> Enter Verification Code </strong> <small> We
								sent a 4-digit verification code to your email. </small>

						</div>


						<div class="otp-verified-icon" id="otpVerifiedIcon">✓</div>


					</div>



					<div class="otp-input-row">


						<input type="text" id="otp" maxlength="4" inputmode="numeric"
							autocomplete="one-time-code" placeholder="••••">


						<button type="button" id="verifyOtpButton"
							class="verify-otp-button" disabled>Verify</button>


					</div>



					<div class="otp-message" id="otpMessage"></div>



					<div class="resend-area">


						<span id="resendText"> Resend available in 60 seconds </span>


						<button type="button" id="resendOtpButton" disabled>

							Resend Code</button>


					</div>


				</div>



				<!-- CONTINUE -->

				<button type="submit" id="continueButton" class="continue-button"
					disabled>Continue</button>


			</form>



			<div class="back-login">

				<a href="login.jsp"> ← Back to Login </a>

			</div>


			<div class="security">Your information is secure and protected.

			</div>


		</div>

	</div>


	<script src="assets/js/forgotpassword.js"></script>

</body>
</html>