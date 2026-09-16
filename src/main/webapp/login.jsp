
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

		<!-- ================================
             BRANDING
             ================================ -->

		<section class="brand-panel">

			<div class="brand-top">

				<div class="brand-logo">M</div>

				<div class="brand-name">MovieBook</div>

			</div>


			<div class="brand-main">

				<h1>
					Your movie night <span>starts here.</span>
				</h1>

				<p>Sign in to continue booking movie tickets, choose your
					preferred seats and manage your bookings from one place.</p>


				<div class="features">

					<div class="feature">

						<div class="feature-icon">✓</div>

						<span> Simple and secure ticket booking </span>

					</div>


					<div class="feature">

						<div class="feature-icon">◆</div>

						<span> Select your preferred seats </span>

					</div>


					<div class="feature">

						<div class="feature-icon">▣</div>

						<span> Manage tickets and bookings </span>

					</div>

				</div>

			</div>


			<div class="brand-footer">© MovieBook · Movie Ticket Booking</div>

		</section>


		<!-- ================================
             LOGIN
             ================================ -->

		<section class="form-panel">

			<div class="form-container">

				<div class="form-header">

					<h2>Welcome back</h2>

					<p>Sign in to your MovieBook account.</p>

				</div>


				<%
				String error = (String) request.getAttribute("error");

				if (error != null) {
				%>

				<div class="error-message">

					<span class="error-icon">!</span> <span> <%=error%>
					</span>

				</div>

				<%
				}
				%>


				<form action="${pageContext.request.contextPath}/login"
					method="post">


					<!-- EMAIL -->

					<div class="form-group">

						<label for="email"> Email address </label>

						<div class="input-wrapper">

							<input type="email" id="email" name="email"
								placeholder="Enter your email" autocomplete="email" required>

							<span class="input-icon"> @ </span>

						</div>

					</div>


					<!-- PASSWORD -->

					<div class="form-group">

						<label for="password"> Password </label>

						<div class="input-wrapper">

							<input type="password" id="password" name="password"
								placeholder="Enter your password"
								autocomplete="current-password" required> <span
								class="input-icon"> • </span>

						</div>

					</div>


					<!-- LOGIN -->

					<button type="submit" class="login-button">Sign in</button>
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

					<p>

						Don't have an account? <a
							href="${pageContext.request.contextPath}/register.jsp">

							Create an account </a>

					</p>

				</div>


				<!-- SECURITY -->

				<div class="security">

					<span>🔒</span> <span> Secure account sign-in </span>

				</div>

			</div>

		</section>

	</main>
	<script src="${pageContext.request.contextPath}/assets/js/login.js"></script>

</body>
</html>

