<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
Boolean forgotPassword = session.getAttribute("forgotUserId") != null;
%>

<!DOCTYPE html>

<html lang="en">

<head>

<meta charset="UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title><%=forgotPassword ? "Reset Password" : "Change Password"%>
	| MovieBook</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/changepassword.css">

</head>

<body>

	<%
	String resetSuccess = (String) request.getAttribute("resetSuccess");

	if (resetSuccess != null) {
	%>

	<div class="success-overlay">

		  
		<div class="success-popup">

			<div class="success-icon">✓</div>

			<h2>Password Changed Successfully</h2>

			<p>Your password has been updated successfully.</p>

			<a href="${pageContext.request.contextPath}/login.jsp"
				class="login-button"> Go to Login </a>

		</div>
		

	</div>

	<%
	}
	%>

	<main class="password-page">

		  
		<div class="password-card">


			<div class="brand">

				<div class="brand-logo">M</div>

				<span class="brand-name"> MovieBook </span>

			</div>


			<div class="password-icon">🔐</div>


			<div class="password-header">

				<h1>
					<%=forgotPassword ? "Reset Password" : "Change Password"%>
				</h1>

				<p>
					<%=forgotPassword ? "Create a new password for your account." : "Update your account password securely."%>
				</p>

			</div>


			<%
			String error = (String) request.getAttribute("error");

			if (error != null) {
			%>

			<div class="error-message">

				<span class="error-icon"> ! </span> <span> <%=error%>
				</span>

			</div>

			<%
			}
			%>


			<form id="passwordForm"
				action="${pageContext.request.contextPath}/changepassword"
				method="post">


				<%
				if (!forgotPassword) {
				%>

				<div class="form-group">

					<label for="currentPassword"> Current Password </label>

					<div class="password-wrapper">

						<input type="password" id="currentPassword" name="currentPassword"
							placeholder="Enter current password" required>

						<button type="button" class="show-password"
							onclick="togglePassword('currentPassword', this)">Show</button>

					</div>

				</div>

				<%
				}
				%>


				<div class="form-group">

					<label for="newPassword"> New Password </label>

					<div class="password-wrapper">

						<input type="password" id="newPassword" name="newPassword"
							placeholder="Enter new password" autocomplete="new-password"
							required>

						<button type="button" class="show-password"
							onclick="togglePassword('newPassword', this)">Show</button>

					</div>


					<div class="password-strength">

						<div class="strength-bar">

							<span id="strengthBar"></span>

						</div>

						<span id="strengthText"> Enter a password </span>

					</div>


					<small id="newPasswordError" class="field-error"></small>

				</div>


				<div class="form-group">

					<label for="confirmPassword"> Confirm New Password </label>

					<div class="password-wrapper">

						<input type="password" id="confirmPassword" name="confirmPassword"
							placeholder="Confirm new password" autocomplete="new-password"
							required>

						<button type="button" class="show-password"
							onclick="togglePassword('confirmPassword', this)">Show</button>

					</div>


					<div id="matchMessage" class="match-message"></div>

				</div>


				<button type="submit" class="password-button">

					<%=forgotPassword ? "Reset Password" : "Change Password"%>

				</button>

			</form>


			<div class="back-login">

				<a href="${pageContext.request.contextPath}/login.jsp"> ← Back
					to Login </a>

			</div>


			<div class="security">🔒 Your account information is securely
				handled.</div>


		</div>
		

	</main>

	<script
		src="${pageContext.request.contextPath}/assets/js/changepassword.js">
		
	</script>

</body>

</html>
