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

<body>

	<%@ include file="/common/navbar.jsp"%>


	<%
	UserBean user = (UserBean) request.getAttribute("user");

	Boolean accountInactive = (Boolean) session.getAttribute("accountInactive");

	if (accountInactive != null && accountInactive) {

		session.removeAttribute("accountInactive");
	%>


	<!-- =========================================================
	     INACTIVE ACCOUNT POPUP
	     ========================================================= -->

	<div id="inactiveOverlay" class="inactive-overlay">

		<div class="inactive-popup">

			<div class="inactive-icon">!</div>

			<h2>Account Inactive</h2>

			<p>Your account is currently inactive.</p>

			<button type="button" class="inactive-ok-button"
				onclick="closeInactivePopup()">OK</button>

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


			<!-- PAGE HEADER -->

			<div class="page-header">

				<h1>My Profile</h1>

				<p>View and manage your account information</p>

			</div>


			<%
			if (user != null) {
			%>


			<!-- PROFILE CARD -->

			<div class="profile-card">


				<!-- CLEAN WHITE TOP -->

				<div class="profile-banner"></div>


				<!-- IDENTITY -->

				<div class="identity-section">

					<div class="identity-row">


						<div class="identity-left">


							<!-- AVATAR -->

							<div class="profile-avatar">

								<%=user.getName().substring(0, 1)%>

							</div>


							<!-- USER DETAILS -->

							<div class="identity-info">

								<h2>
									<%=user.getName()%>
								</h2>

								<p>
									<%=user.getEmail()%>
								</p>

							</div>


						</div>


						<!-- ACCOUNT STATUS -->

						<div>

							<%
							if (user.isStatus()) {
							%>

							<div class="status-badge status-active">

								<span class="status-icon"> ✓ </span> Active Account

							</div>


							<%
							} else {
							%>


							<div class="status-badge status-inactive">

								<span class="status-icon"> ✕ </span> Inactive Account

							</div>


							<%
							}
							%>

						</div>


					</div>

				</div>


				<!-- PROFILE CONTENT -->

				<div class="profile-content">


					<!-- ACCOUNT OVERVIEW -->

					<div class="profile-section">

						<div class="section-header">

							<h3>Account Overview</h3>

							<p>Summary of your account information</p>

						</div>


						<div class="overview-grid">


							<!-- STATUS -->

							<div class="overview-box">

								<span class="overview-box-label"> Account Status </span> <span
									class="overview-box-value"> <%=user.isStatus() ? "Active" : "Inactive"%>

								</span>

							</div>


							<!-- ROLE -->

							<div class="overview-box">

								<span class="overview-box-label"> Account Role </span> <span
									class="overview-box-value"> <%=user.getRole()%>

								</span>

							</div>


							<!-- EMAIL -->

							<div class="overview-box">

								<span class="overview-box-label"> Registered Email </span> <span
									class="overview-box-value"> <%=user.getEmail()%>

								</span>

							</div>


						</div>

					</div>


					<!-- PERSONAL INFORMATION -->

					<div class="profile-section">

						<div class="section-header">

							<h3>Personal Information</h3>

							<p>Basic information associated with your account</p>

						</div>


						<div class="information-grid">


							<!-- NAME -->

							<div class="information-item">

								<span class="information-label"> Full Name </span> <span
									class="information-value"> <%=user.getName()%>

								</span>

							</div>


							<!-- PHONE -->

							<div class="information-item">

								<span class="information-label"> Phone Number </span> <span
									class="information-value"> <%=user.getPhone()%>

								</span>

							</div>


							<!-- EMAIL -->

							<div class="information-item">

								<span class="information-label"> Email Address </span> <span
									class="information-value"> <%=user.getEmail()%>

								</span>

							</div>


							<!-- ROLE -->

							<div class="information-item">

								<span class="information-label"> Account Role </span> <span
									class="information-value"> <span class="role-badge">

										<%=user.getRole()%>

								</span>

								</span>

							</div>


						</div>

					</div>


					<!-- CONTACT INFORMATION -->

					<div class="profile-section">

						<div class="section-header">

							<h3>Contact Information</h3>

							<p>Registered communication details</p>

						</div>


						<div class="information-grid">


							<!-- EMAIL -->

							<div class="information-item">

								<span class="information-label"> Email Address </span> <span
									class="information-value"> <%=user.getEmail()%>

								</span>

							</div>


							<!-- PHONE -->

							<div class="information-item">

								<span class="information-label"> Phone Number </span> <span
									class="information-value"> <%=user.getPhone()%>

								</span>

							</div>


						</div>

					</div>


					<!-- ACCOUNT ACCESS -->

					<div class="profile-section">

						<div class="section-header">

							<h3>Account & Access</h3>

							<p>Current access level and account state</p>

						</div>


						<div class="security-panel">


							<div class="security-left">

								<%
								if (user.isStatus()) {
								%>


								<div class="security-icon security-icon-active">✓</div>


								<div class="security-text">

									<strong> Account Access </strong> <span> Your account is
										currently enabled and available. </span>

								</div>


								<%
								} else {
								%>


								<div class="security-icon security-icon-inactive">✕</div>


								<div class="security-text">

									<strong> Account Access </strong> <span> Your account is
										currently inactive and access is restricted. </span>

								</div>


								<%
								}
								%>

							</div>


							<%
							if (user.isStatus()) {
							%>


							<div class="security-status-active">ACTIVE</div>


							<%
							} else {
							%>


							<div class="security-status-inactive">INACTIVE</div>


							<%
							}
							%>


						</div>

					</div>


					<!-- ACCOUNT ACTIONS -->

					<div class="profile-actions">


						<div class="action-info">

							<h3>Account Actions</h3>

							<p>Manage your current MovieBook session.</p>

						</div>


						<a href="<%=request.getContextPath()%>/logout" class="logout-btn">

							Logout </a>


					</div>


				</div>

			</div>


			<%
			} else {
			%>


			<!-- USER INFORMATION NOT AVAILABLE -->

			<div class="empty-profile">

				<h2>User Information Unavailable</h2>

				<p>We could not retrieve your account information at this time.
				</p>

			</div>


			<%
			}
			%>


		</div>

	</div>


	<!-- =========================================================
	     POPUP JAVASCRIPT
	     ========================================================= -->

	<script src="${pageContext.request.contextPath}/assets/js/profile.js"></script>


</body>

</html>