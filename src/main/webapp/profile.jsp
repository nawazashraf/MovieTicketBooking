<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="com.movieticket.model.UserBean"%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>My Profile</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/common.css">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/profile.css">

</head>

<body data-context-path="<%=request.getContextPath()%>">

	<%@ include file="/common/navbar.jsp"%>

	<%
	String updateSuccess = (String) request.getAttribute("updateSuccess");
	String updateError = (String) request.getAttribute("updateError");

	UserBean user = (UserBean) request.getAttribute("user");

	if (user == null) {
		user = (UserBean) session.getAttribute("user");
	}

	Boolean accountInactive = (Boolean) session.getAttribute("accountInactive");
	%>


	<!-- =========================================================
	     SUCCESS POPUP
	========================================================= -->

	<%
	if (updateSuccess != null) {
	%>

	<div id="profileUpdatePopup" class="profile-update-popup">

		<div class="profile-update-card">

			<div class="profile-update-icon">✓</div>

			<div class="profile-update-content">

				<h3>Profile Updated</h3>

				<p>Your profile has been updated successfully.</p>

			</div>

			<button type="button" class="profile-update-close"
				onclick="closeProfileUpdatePopup()">×</button>

		</div>

	</div>

	<%
	}
	%>


	<!-- =========================================================
	     ERROR POPUP
	========================================================= -->

	<%
	if (updateError != null) {
	%>

	<div id="profileUpdateErrorPopup" class="profile-update-popup">

		<div class="profile-update-card profile-update-error-card">

			<div class="profile-update-icon profile-update-error-icon">!</div>

			<div class="profile-update-content">

				<h3>Update Failed</h3>

				<p><%=updateError%></p>

			</div>

			<button type="button" class="profile-update-close"
				onclick="closeProfileUpdateErrorPopup()">×</button>

		</div>

	</div>

	<%
	}
	%>


	<!-- =========================================================
	     INACTIVE ACCOUNT POPUP
	========================================================= -->

	<%
	if (Boolean.TRUE.equals(accountInactive)) {
	%>

	<div id="inactiveOverlay" class="inactive-overlay">

		<div class="inactive-popup">

			<div class="inactive-icon">!</div>

			<h2>Account Inactive</h2>

			<p>Your account is currently inactive. Some features are
				temporarily restricted.</p>

			<button type="button" class="inactive-ok-button"
				onclick="closeInactivePopup()">Continue</button>

		</div>

	</div>

	<%
	}
	%>


	<!-- =========================================================
	     PROFILE PAGE
	========================================================= -->

	<div class="profile-page">

		<div class="profile-container">

			<div class="page-header">

				<div>

					<div class="page-header-label">ACCOUNT</div>

					<h1>My Profile</h1>

					<p>Manage your account information and preferences.</p>

				</div>

			</div>


			<%
			if (user != null) {
			%>

			<div class="profile-card">

				<div class="profile-banner"></div>


				<!-- IDENTITY -->

				<div class="identity-section">

					<div class="identity-row">

						<div class="identity-left">

							<div class="profile-avatar">

								<%=user.getName() != null ? user.getName().substring(0, 1).toUpperCase() : "U"%>

							</div>

							<div class="identity-info">

								<h2><%=user.getName()%></h2>

								<div class="identity-email">
									<%=user.getEmail()%>
								</div>

								<div class="identity-status-row">

									<%
									if (user.isStatus()) {
									%>

									<span class="status-badge status-active"> <span
										class="status-dot"></span> Active

									</span>

									<%
									} else {
									%>

									<span class="status-badge status-inactive"> <span
										class="status-dot"></span> Inactive

									</span>

									<%
									}
									%>

								</div>

							</div>

						</div>


						<%
						if (user.isStatus()) {
						%>

						<button type="button" class="edit-profile-button"
							onclick="openEditProfile()">

							<span class="edit-icon">✎</span> Edit Profile

						</button>

						<%
						}
						%>

					</div>

				</div>


				<!-- PROFILE CONTENT -->

				<div class="profile-content">

					<section class="profile-section">

						<div class="section-heading">

							<div>

								<h3>Account Information</h3>

								<p>Your registered account details</p>

							</div>

						</div>


						<div class="information-grid">

							<div class="information-item">

								<div class="information-label">FULL NAME</div>

								<div class="information-value">
									<%=user.getName()%>
								</div>

							</div>


							<div class="information-item">

								<div class="information-label">EMAIL ADDRESS</div>

								<div class="information-value">
									<%=user.getEmail()%>
								</div>

							</div>


							<div class="information-item">

								<div class="information-label">PHONE NUMBER</div>

								<div class="information-value">
									+91
									<%=user.getPhone()%>
								</div>

							</div>

						</div>

					</section>


					<section class="profile-section">

						<div class="section-heading">

							<div>

								<h3>Account Status</h3>

								<p>Current access status of your account</p>

							</div>

						</div>


						<%
						if (user.isStatus()) {
						%>

						<div class="security-panel security-active">

							<div class="security-icon">✓</div>

							<div class="security-info">

								<strong>Account Active</strong> <span> Your account is
									active and booking features are available. </span>

							</div>

							<div class="security-status">ACTIVE</div>

						</div>

						<%
						} else {
						%>

						<div class="security-panel security-inactive">

							<div class="security-icon">!</div>

							<div class="security-info">

								<strong>Account Inactive</strong> <span> Booking features
									are restricted until your account is activated. </span>

							</div>

							<div class="security-status">INACTIVE</div>

						</div>

						<%
						}
						%>

					</section>


					<section class="profile-actions">

						<div>

							<h3>Account Session</h3>

							<p>Sign out from your current account</p>

						</div>

						<a href="<%=request.getContextPath()%>/logout" class="logout-btn">
							Logout </a>

					</section>

				</div>

			</div>


			<%
			} else {
			%>

			<div class="empty-profile">

				<div class="empty-icon">!</div>

				<h2>Profile Unavailable</h2>

				<p>We could not load your profile information.</p>

			</div>

			<%
			}
			%>

		</div>

	</div>


	<!-- =========================================================
	     EDIT PROFILE DRAWER
	========================================================= -->

	<%
	if (user != null && user.isStatus()) {
	%>

	<div id="editProfileOverlay" class="drawer-overlay"
		onclick="closeEditProfile(event)">


		<div class="profile-drawer" onclick="event.stopPropagation()">


			<!-- HEADER -->

			<div class="drawer-header">

				<div>

					<div class="drawer-eyebrow">ACCOUNT SETTINGS</div>

					<h2>Edit Profile</h2>

					<p>Update your personal information</p>

				</div>


				<button type="button" class="drawer-close"
					onclick="closeEditProfile()">×</button>

			</div>


			<!-- BODY -->

			<div class="drawer-body">


				<div class="drawer-profile-summary">

					<div class="drawer-avatar">

						<%=user.getName() != null ? user.getName().substring(0, 1).toUpperCase() : "U"%>

					</div>


					<div>

						<strong><%=user.getName()%></strong> <span><%=user.getEmail()%></span>

					</div>

				</div>


				<div class="drawer-divider"></div>


				<!-- FORM -->

				<form action="<%=request.getContextPath()%>/update-profile"
					method="post" id="editProfileForm" class="drawer-form">


					<!-- NAME -->

					<div class="drawer-field">

						<label for="editName"> Full Name </label>

						<div class="drawer-input-wrapper">

							<span class="field-prefix">A</span> <input type="text"
								id="editName" name="name" value="<%=user.getName()%>"
								autocomplete="name" required>

						</div>

						<small id="editNameError" class="field-error"></small>

					</div>


					<!-- PHONE -->

					<div class="drawer-field">

						<label for="editPhone"> Phone Number </label>

						<div class="phone-input-group">

							<span class="phone-prefix"> +91 </span> <input type="tel"
								id="editPhone" name="phone" value="<%=user.getPhone()%>"
								maxlength="10" pattern="[0-9]{10}" inputmode="numeric"
								autocomplete="tel" placeholder="Enter 10 digit number" required>

						</div>

						<small class="field-hint"> 10-digit mobile number </small> <small
							id="editPhoneError" class="field-error"></small>

					</div>


					<!-- SECURITY QUESTION -->

					<div class="drawer-field">

						<label for="editSecurityQuestion"> Security Question </label>

						<div class="drawer-input-wrapper">

							<span class="field-prefix"> ? </span> <select
								id="editSecurityQuestion" name="securityQuestion" required>

								<option value="" disabled
									<%=user.getSecurityQuestion() == null || user.getSecurityQuestion().trim().isEmpty() ? "selected" : ""%>>
									Select a security question</option>


								<option value="What was the name of your first school?"
									<%="What was the name of your first school?".equals(user.getSecurityQuestion()) ? "selected" : ""%>>
									What was the name of your first school?</option>


								<option value="What was the name of your first pet?"
									<%="What was the name of your first pet?".equals(user.getSecurityQuestion()) ? "selected" : ""%>>
									What was the name of your first pet?</option>


								<option value="What is your favourite movie?"
									<%="What is your favourite movie?".equals(user.getSecurityQuestion()) ? "selected" : ""%>>
									What is your favourite movie?</option>


								<option value="What is your favourite food?"
									<%="What is your favourite food?".equals(user.getSecurityQuestion()) ? "selected" : ""%>>
									What is your favourite food?</option>


								<option value="What city were you born in?"
									<%="What city were you born in?".equals(user.getSecurityQuestion()) ? "selected" : ""%>>
									What city were you born in?</option>


								<option value="What was your childhood nickname?"
									<%="What was your childhood nickname?".equals(user.getSecurityQuestion()) ? "selected" : ""%>>
									What was your childhood nickname?</option>


								<option value="What is your favourite book?"
									<%="What is your favourite book?".equals(user.getSecurityQuestion()) ? "selected" : ""%>>
									What is your favourite book?</option>


								<option value="What is your favourite sport?"
									<%="What is your favourite sport?".equals(user.getSecurityQuestion()) ? "selected" : ""%>>
									What is your favourite sport?</option>


								<option value="What is your favourite place to visit?"
									<%="What is your favourite place to visit?".equals(user.getSecurityQuestion()) ? "selected" : ""%>>
									What is your favourite place to visit?</option>

							</select>

						</div>

						<small id="editSecurityQuestionError" class="field-error"></small>

					</div>


					<!-- SECURITY ANSWER -->

					<div class="drawer-field">

						<label for="editSecurityAnswer"> Security Answer </label>

						<div class="drawer-input-wrapper">

							<span class="field-prefix"> A </span> <input type="text"
								id="editSecurityAnswer" name="securityAnswer"
								value="<%=user.getSecurityAnswer() != null ? user.getSecurityAnswer() : ""%>"
								autocomplete="off" maxlength="255"
								placeholder="Enter your answer" required>

						</div>

						<small id="editSecurityAnswerError" class="field-error"></small>

					</div>


					<!-- EMAIL -->

					<div class="drawer-field">

						<label> Email Address </label>

						<div class="drawer-readonly">

							<span class="readonly-icon"> @ </span> <span
								class="readonly-value"> <%=user.getEmail()%>
							</span> <span class="readonly-badge"> VERIFIED </span>

						</div>

						<small> Email address cannot be changed. </small>

					</div>


					<!-- PASSWORD -->

					<div class="drawer-field">

						<label for="editPassword"> New Password </label>

						<div class="password-input-wrapper">

							<input type="password" id="editPassword" name="password"
								autocomplete="new-password"
								placeholder="Leave blank to keep current password">

							<button type="button" class="password-toggle"
								onclick="toggleEditPassword('editPassword', this)">
								Show</button>

						</div>


						<div id="editPasswordStrength" class="password-strength"
							style="display: none;">

							<div class="password-strength-bar">

								<div id="editPasswordStrengthFill"
									class="password-strength-fill"></div>

							</div>

							<div id="editPasswordStrengthText" class="password-strength-text">
							</div>

						</div>


						<small id="editPasswordError" class="field-error"></small>

					</div>


					<!-- CONFIRM PASSWORD -->

					<div class="drawer-field">

						<label for="editConfirmPassword"> Confirm New Password </label>

						<div class="password-input-wrapper">

							<input type="password" id="editConfirmPassword"
								name="confirmPassword" autocomplete="new-password"
								placeholder="Re-enter new password">

							<button type="button" class="password-toggle"
								onclick="toggleEditPassword('editConfirmPassword', this)">
								Show</button>

						</div>


						<div id="editPasswordMatch" class="password-match"></div>


						<small id="editConfirmPasswordError" class="field-error"></small>

					</div>


					<!-- ACTIONS -->

					<div class="drawer-actions">

						<button type="button" class="drawer-cancel"
							onclick="closeEditProfile()">Cancel</button>

						<button type="submit" class="drawer-save"
							id="editProfileSaveButton">

							<span>Save Changes</span> <span class="save-arrow">→</span>

						</button>

					</div>


				</form>

			</div>

		</div>

	</div>

	<%
	}
	%>


	<script src="${pageContext.request.contextPath}/assets/js/profile.js">
		
	</script>

</body>

</html>