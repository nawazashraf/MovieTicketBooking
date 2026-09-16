<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Create Account - MovieBook</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/register.css">

</head>

<body>

	<main class="register-page">

		<section class="register-card">

			<!-- =========================
                 LEFT BRAND PANEL
            ========================== -->

			<div class="brand-panel">

				<div class="brand">

					<div class="brand-logo">M</div>

					<span class="brand-name"> MovieBook </span>

				</div>


				<div class="brand-content">

					<span class="brand-tag"> MOVIE TICKET BOOKING </span>

					<h1>
						Your movie <span>journey</span> starts here.
					</h1>

					<p>Create your account and enjoy a simple, secure and
						convenient way to book your favourite movies.</p>


					<div class="features">

						<div class="feature">

							<div class="feature-icon">✓</div>

							<div>
								<strong> Easy Booking </strong> <small> Book your
									favourite movies quickly. </small>
							</div>

						</div>


						<div class="feature">

							<div class="feature-icon">◆</div>

							<div>
								<strong> Choose Your Seat </strong> <small> Select the
									seat you want. </small>
							</div>

						</div>


						<div class="feature">

							<div class="feature-icon">▣</div>

							<div>
								<strong> Manage Tickets </strong> <small> Keep your
									bookings in one place. </small>
							</div>

						</div>

					</div>

				</div>


				<div class="brand-footer">© MovieBook. All rights reserved.</div>

			</div>


			<!-- =========================
                 REGISTER PANEL
            ========================== -->

			<div class="register-panel">

				<div class="register-container">


					<!-- HEADER -->

					<div class="register-header">

						<h2>Create your account</h2>

						<p>Enter your details to get started.</p>

					</div>


					<!-- SERVER ERROR -->

					<%
					String error = (String) request.getAttribute("error");

					if (error != null) {
					%>

					<div class="server-error">

						<span class="error-icon"> ! </span> <span> <%=error%>
						</span>

					</div>

					<%
					}
					%>


					<!-- =========================
                         REGISTER FORM
                    ========================== -->

					<form id="registerForm"
						action="${pageContext.request.contextPath}/register" method="post">


						<!-- NAME -->

						<div class="form-group">

							<label for="name"> Full name </label>

							<div class="input-wrapper">

								<span class="input-icon"> A </span> <input type="text" id="name"
									name="name" placeholder="Enter your full name"
									autocomplete="name" required>

							</div>

							<span id="nameError" class="field-error"> </span>

						</div>


						<!-- EMAIL -->
						<div class="form-group">

							<label for="email"> Email address </label>

							<div class="email-verification-row">

								<div class="input-wrapper email-input-wrapper">

									<span class="input-icon"> @ </span> <input type="email"
										id="email" name="email" placeholder="you@example.com"
										autocomplete="email" required>

								</div>

								<button type="button" id="verifyEmailButton"
									class="verify-email-button" disabled>Verify Email</button>

							</div>

							<span id="emailError" class="field-error"> </span>


							<!-- OTP TRAY -->

							<div id="otpTray" class="otp-tray">

								<div class="otp-header">

									<div>

										<strong> Verify your email </strong> <small> Enter the
											4-digit code sent to your email. </small>

									</div>

									<span id="otpVerifiedIcon" class="otp-verified-icon"> ✓
									</span>

								</div>


								<div class="otp-input-row">

									<input type="text" id="otp" name="otp" maxlength="4"
										inputmode="numeric" autocomplete="one-time-code"
										placeholder="0000">

									<button type="button" id="verifyOtpButton"
										class="verify-otp-button">Verify</button>

								</div>


								<div id="otpMessage" class="otp-message"></div>


								<div id="resendArea" class="resend-area">

									<span id="resendText"> Resend available in 1s </span>

									<button type="button" id="resendOtpButton" disabled>

										Resend Verification Code</button>

								</div>

							</div>

						</div>


						<!-- PHONE -->

						<div class="form-group">

							<label for="phone"> Phone number </label>

							<div class="input-wrapper">

								<span class="phone-prefix"> +91 </span> <input type="tel"
									id="phone" name="phone" placeholder="10 digit phone number"
									autocomplete="tel" inputmode="numeric" maxlength="10"
									pattern="[0-9]{10}" required>

							</div>

							<span id="phoneError" class="field-error"> </span>

						</div>


						<!-- PASSWORD -->

						<div class="form-group">

							<label for="password"> Password </label>

							<div class="input-wrapper">

								<span class="input-icon"> • </span> <input type="password"
									id="password" name="password" placeholder="Create a password"
									autocomplete="new-password" required>

								<button type="button" id="passwordToggle"
									class="password-toggle" aria-label="Show password">

									Show</button>

							</div>

							<span id="passwordError" class="field-error"> </span>

						</div>


						<!-- PASSWORD STRENGTH -->

						<div class="password-strength">

							<div class="strength-track">

								<span id="strengthBar"></span>

							</div>

							<span id="strengthText"> Use 8 or more characters </span>

						</div>


						<!-- REGISTER BUTTON -->

						<button type="submit" id="registerButton" class="register-button"
							disabled>

							<span id="buttonText"> Verify Email to Continue </span>

						</button>

					</form>

					<%
					String success = request.getParameter("success");

					if ("1".equals(success)) {
					%>

					<div class="success-overlay" id="successPopup">

						<div class="success-box">

							<div class="success-icon">✓</div>

							<h2>Registration Completed!</h2>

							<p>Thank you for registering with MovieBook.</p>

							<p class="success-message">Your account has been created
								successfully.</p>

							<a href="${pageContext.request.contextPath}/login.jsp"
								class="login-success-button"> Go to Login </a>

						</div>

					</div>

					<%
					}
					%>


					<!-- LOGIN -->

					<div class="login-area">

						<span> Already have an account? </span> <a
							href="${pageContext.request.contextPath}/login.jsp"> Sign in

						</a>

					</div>


					<!-- SECURITY -->

					<div class="security-note">

						<span class="security-icon"> 🔒 </span> <span> Your
							information is securely handled </span>

					</div>

				</div>

			</div>

		</section>

	</main>


	<script src="${pageContext.request.contextPath}/assets/js/register.js">
		
	</script>

</body>

</html>