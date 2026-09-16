
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Sign In | Movie Ticket Booking</title>

<style>
* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
}

html, body {
	min-height: 100%;
}

body {
	font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto,
		Helvetica, Arial, sans-serif;
	background: #f5f7fa;
	color: #171a1f;
	min-height: 100vh;
	display: flex;
	align-items: center;
	justify-content: center;
	padding: 32px 20px;
}

/* ================================
           MAIN LOGIN CARD
           ================================ */
.login-card {
	width: 100%;
	max-width: 1050px;
	min-height: 620px;
	background: #ffffff;
	border: 1px solid #e4e7ec;
	border-radius: 18px;
	box-shadow: 0 4px 6px rgba(16, 24, 40, 0.02), 0 15px 35px
		rgba(16, 24, 40, 0.06);
	display: grid;
	grid-template-columns: 46% 54%;
	overflow: hidden;
}

/* ================================
           BRAND PANEL
           ================================ */
.brand-panel {
	background: #fafbfc;
	border-right: 1px solid #eaecf0;
	padding: 58px;
	display: flex;
	flex-direction: column;
	justify-content: space-between;
}

.brand-top {
	display: flex;
	align-items: center;
	gap: 12px;
}

.brand-logo {
	width: 42px;
	height: 42px;
	background: #e50914;
	color: #ffffff;
	border-radius: 10px;
	display: flex;
	align-items: center;
	justify-content: center;
	font-size: 20px;
	font-weight: 700;
}

.brand-name {
	font-size: 19px;
	font-weight: 700;
	color: #171a1f;
}

.brand-main {
	max-width: 390px;
}

.brand-main h1 {
	font-size: 40px;
	line-height: 1.13;
	letter-spacing: -1.2px;
	color: #171a1f;
	margin-bottom: 20px;
}

.brand-main h1 span {
	color: #e50914;
}

.brand-main p {
	font-size: 15px;
	line-height: 1.7;
	color: #667085;
	margin-bottom: 32px;
}

/* ================================
           FEATURE LIST
           ================================ */
.features {
	display: flex;
	flex-direction: column;
	gap: 14px;
}

.feature {
	display: flex;
	align-items: center;
	gap: 12px;
	font-size: 14px;
	color: #475467;
}

.feature-icon {
	width: 34px;
	height: 34px;
	flex-shrink: 0;
	background: #ffffff;
	border: 1px solid #e4e7ec;
	border-radius: 8px;
	display: flex;
	align-items: center;
	justify-content: center;
	color: #e50914;
	font-size: 14px;
	font-weight: 700;
}

.brand-footer {
	font-size: 12px;
	color: #98a2b3;
}

/* ================================
           FORM PANEL
           ================================ */
.form-panel {
	padding: 58px 70px;
	display: flex;
	align-items: center;
	justify-content: center;
	background: #ffffff;
}

.form-container {
	width: 100%;
	max-width: 390px;
}

/* ================================
           HEADER
           ================================ */
.form-header {
	margin-bottom: 30px;
}

.form-header h2 {
	font-size: 30px;
	line-height: 1.2;
	letter-spacing: -0.5px;
	color: #101828;
	margin-bottom: 9px;
}

.form-header p {
	font-size: 14px;
	line-height: 1.5;
	color: #667085;
}

/* ================================
           ERROR
           ================================ */
.error-message {
	display: flex;
	align-items: flex-start;
	gap: 10px;
	background: #fff5f5;
	border: 1px solid #fecdca;
	border-radius: 9px;
	padding: 12px 14px;
	margin-bottom: 22px;
	color: #b42318;
	font-size: 13px;
	line-height: 1.5;
}

.error-icon {
	font-weight: 700;
	flex-shrink: 0;
}

/* ================================
           FORM
           ================================ */
.form-group {
	margin-bottom: 20px;
}

.form-group label {
	display: block;
	margin-bottom: 7px;
	color: #344054;
	font-size: 13px;
	font-weight: 600;
}

.input-wrapper {
	position: relative;
}

.input-icon {
	position: absolute;
	left: 14px;
	top: 50%;
	transform: translateY(-50%);
	color: #98a2b3;
	font-size: 14px;
	pointer-events: none;
}

.form-group input {
	width: 100%;
	height: 48px;
	padding: 0 14px 0 40px;
	background: #ffffff;
	border: 1px solid #d0d5dd;
	border-radius: 8px;
	color: #101828;
	font-family: inherit;
	font-size: 14px;
	outline: none;
	transition: border-color 0.18s ease, box-shadow 0.18s ease;
}

.form-group input::placeholder {
	color: #98a2b3;
}

.form-group input:hover {
	border-color: #98a2b3;
}

.form-group input:focus {
	border-color: #e50914;
	box-shadow: 0 0 0 3px rgba(229, 9, 20, 0.08);
}

.form-group input:focus+.input-icon {
	color: #e50914;
}

/* ================================
           LOGIN BUTTON
           ================================ */
.login-button {
	width: 100%;
	height: 48px;
	margin-top: 4px;
	border: none;
	border-radius: 8px;
	background: #e50914;
	color: #ffffff;
	font-family: inherit;
	font-size: 14px;
	font-weight: 600;
	cursor: pointer;
	box-shadow: 0 4px 10px rgba(229, 9, 20, 0.16);
	transition: background 0.18s ease, box-shadow 0.18s ease, transform
		0.18s ease;
}

.login-button:hover {
	background: #c90812;
	box-shadow: 0 6px 14px rgba(229, 9, 20, 0.20);
	transform: translateY(-1px);
}

.login-button:active {
	transform: translateY(0);
}

.login-button:focus-visible {
	outline: 3px solid rgba(229, 9, 20, 0.20);
	outline-offset: 2px;
}

/* ================================
           REGISTER
           ================================ */
.register-area {
	margin-top: 26px;
	padding-top: 23px;
	border-top: 1px solid #eaecf0;
	text-align: center;
}

.register-area p {
	color: #667085;
	font-size: 13px;
}

.register-area a {
	color: #e50914;
	text-decoration: none;
	font-weight: 600;
	margin-left: 3px;
}

.register-area a:hover {
	text-decoration: underline;
}

/* ================================
           SECURITY
           ================================ */
.security {
	display: flex;
	align-items: center;
	justify-content: center;
	gap: 6px;
	margin-top: 20px;
	color: #98a2b3;
	font-size: 11px;
}

/* ================================
           TABLET
           ================================ */
@media ( max-width : 850px) {
	body {
		padding: 20px;
	}
	.login-card {
		max-width: 520px;
		grid-template-columns: 1fr;
		min-height: auto;
	}
	.brand-panel {
		display: none;
	}
	.form-panel {
		padding: 55px 50px;
	}
}

/* ================================
           MOBILE
           ================================ */
@media ( max-width : 520px) {
	body {
		padding: 12px;
	}
	.login-card {
		border-radius: 14px;
	}
	.form-panel {
		padding: 42px 24px;
	}
	.form-header h2 {
		font-size: 27px;
	}
}

/* ================================
           SMALL MOBILE
           ================================ */
@media ( max-width : 360px) {
	.form-panel {
		padding: 35px 18px;
	}
	.form-header h2 {
		font-size: 25px;
	}
	.form-group input {
		height: 46px;
	}
	.login-button {
		height: 46px;
	}
}
</style>

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

</body>
</html>

