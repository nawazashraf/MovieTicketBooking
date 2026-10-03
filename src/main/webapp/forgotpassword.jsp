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


			<%
			String error = (String) request.getAttribute("error");
			if (error != null) {
			%>

			<div class="error-message" id="serverError">

				<span class="error-icon">!</span> <span><%=error%></span>

			</div>

			<%
			}
			%>


			<form id="forgotPasswordForm"
				action="${pageContext.request.contextPath}/forgotpassword"
				method="post">


				<!-- EMAIL -->

				<div class="form-group">

					<label for="email"> Email Address </label>

					<div class="email-input-wrapper">

						<span class="input-icon"> ✉ </span> <input type="email" id="email"
							name="email" placeholder="Enter your email address"
							autocomplete="email"
							value="<%=request.getAttribute("email") != null ? request.getAttribute("email") : ""%>"
							<%=request.getAttribute("securityQuestion") != null ? "readonly" : ""%>
							required>

					</div>

					<div class="field-error" id="emailFieldError"></div>

				</div>


				<%
				String securityQuestion = (String) request.getAttribute("securityQuestion");

				if (securityQuestion != null) {
				%>

				<!-- SECURITY QUESTION -->

				<div class="form-group">

					<label for="securityQuestion"> Security Question </label>

					<div class="email-input-wrapper security-question-wrapper">

						<span class="input-icon"> ? </span> <input type="text"
							id="securityQuestion" value="<%=securityQuestion%>" readonly
							disabled>

					</div>

				</div>


				<!-- SECURITY ANSWER -->

				<div class="form-group">

					<label for="securityAnswer"> Security Answer </label>

					<div class="email-input-wrapper">

						<span class="input-icon"> A </span> <input type="text"
							id="securityAnswer" name="securityAnswer"
							placeholder="Enter your answer" autocomplete="off" required>

					</div>

					<div class="field-error" id="securityAnswerFieldError"></div>

				</div>


				<input type="hidden" name="action" value="verifyAnswer">

				<button type="submit" id="continueButton" class="continue-button">
					Continue</button>

				<%
				} else {
				%>


				<!-- CONTINUE -->

				<button type="submit" id="continueButton" class="continue-button">
					Continue</button>


				<%
				}
				%>


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