<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Sign In | Movie Ticket Booking</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/login.css">
</head>

<body>

	<main class="login-card">

		   
		<!-- LEFT BRAND PANEL -->

		<section class="brand-panel">

			<div class="brand-top">

				<div class="brand-logo">M</div>

				<div class="brand-name">MovieBook</div>

			</div>


			<div class="brand-main">

				<div class="brand-label">MOVIE TICKET BOOKING</div>

				<h1>
					Your movie night <span>starts here.</span>
				</h1>

				<p>Sign in to book your tickets, choose your preferred seats and
					manage your movie bookings from one secure account.</p>


				<div class="features">

					<div class="feature">

						<div class="feature-icon">✓</div>

						<div>
							<strong>Simple booking</strong> <span>Book your movie
								tickets in minutes.</span>
						</div>

					</div>


					<div class="feature">

						<div class="feature-icon">◆</div>

						<div>
							<strong>Preferred seats</strong> <span>Choose the seats
								you want before checkout.</span>
						</div>

					</div>


					<div class="feature">

						<div class="feature-icon">▣</div>

						<div>
							<strong>Manage bookings</strong> <span>Keep your tickets
								and bookings in one place.</span>
						</div>

					</div>

				</div>

			</div>


			<div class="brand-footer">

				<span>© MovieBook</span> <span class="footer-dot">•</span> <span>Secure
					movie booking platform</span>

			</div>

		</section>


		<!-- RIGHT LOGIN PANEL -->

		<section class="form-panel">

			<div class="form-container">

				<div class="form-header">

					<div class="welcome-label">ACCOUNT LOGIN</div>

					<h2>Welcome back</h2>

					<p>Sign in to continue to your MovieBook account.</p>

				</div>


				<form id="loginForm"
					action="${pageContext.request.contextPath}/login" method="post"
					novalidate>


					<!-- EMAIL -->

					<div class="form-group email-group">

						<label for="email"> Email address </label>

						<div class="input-wrapper">

							<input type="email" id="email" name="email"
								placeholder="Enter your email" autocomplete="email"
								spellcheck="false" required> <span
								class="input-icon email-icon" aria-hidden="true"> </span>

						</div>


						<%
						String serverError = (String) request.getAttribute("emailError");
						%>

						<div class="field-error <%=serverError != null ? "show" : ""%>"
							id="emailError">

							<%=serverError != null ? serverError : ""%>

						</div>

					</div>


					<!-- PASSWORD -->

					<div class="form-group password-group">

						<label for="password"> Password </label>

						<div class="input-wrapper">

							<input type="password" id="password" name="password"
								placeholder="Enter your password"
								autocomplete="current-password" required>

							<button type="button" id="passwordToggle" class="password-toggle"
								aria-label="Show password">

								<span id="passwordEye"></span>

							</button>

						</div>


						<div class="field-error" id="passwordError"></div>

					</div>


					<!-- LOGIN BUTTON -->

					<button type="submit" class="login-button" id="loginButton">

						<span id="buttonText"> Sign in </span>

					</button>


					<!-- FORGOT PASSWORD -->

					<%
					Boolean showForgotPassword = (Boolean) request.getAttribute("showForgotPassword");

					if (Boolean.TRUE.equals(showForgotPassword)) {
					%>

					<div class="forgot-password-area">

						<a href="${pageContext.request.contextPath}/forgotpassword.jsp">
							Forgot your password? </a>

					</div>

					<%
					}
					%>

				</form>


				<!-- REGISTER -->

				<div class="register-area">

					<span> Don't have an account? </span> <a
						href="${pageContext.request.contextPath}/register.jsp"> Create
						an account </a>

				</div>


				<!-- SECURITY -->

				<div class="security">

					<span class="security-icon">✓</span> <span> Secure account
						sign-in </span>

				</div>

			</div>

		</section>
		   

	</main>

	<script src="${pageContext.request.contextPath}/assets/js/login.js">
		
	</script>

</body>
</html>
