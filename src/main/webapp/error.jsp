
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Service Unavailable | Movie Ticket Booking</title>

<meta name="viewport" content="width=device-width, initial-scale=1.0">

<style>
* {
	box-sizing: border-box;
}

html, body {
	margin: 0;
	padding: 0;
	min-height: 100%;
}

body {
	min-height: 100vh;
	display: flex;
	align-items: center;
	justify-content: center;
	padding: 24px;
	font-family: Arial, Helvetica, sans-serif;
	background: #f7f8fa;
	color: #1f2937;
}

.error-wrapper {
	width: 100%;
	max-width: 560px;
}

.error-card {
	background: #ffffff;
	border: 1px solid #e5e7eb;
	border-radius: 16px;
	padding: 48px 42px;
	text-align: center;
	box-shadow: 0 12px 35px rgba(0, 0, 0, 0.07);
}

.brand {
	margin-bottom: 32px;
	font-size: 20px;
	font-weight: 700;
	color: #111827;
	letter-spacing: -0.3px;
}

.brand span {
	color: #e11d48;
}

.error-icon {
	width: 72px;
	height: 72px;
	margin: 0 auto 24px;
	display: flex;
	align-items: center;
	justify-content: center;
	border-radius: 50%;
	background: #fff4e5;
	color: #d97706;
	font-size: 34px;
	font-weight: 700;
}

.error-code {
	margin-bottom: 8px;
	font-size: 13px;
	font-weight: 600;
	color: #9ca3af;
	text-transform: uppercase;
	letter-spacing: 1px;
}

h1 {
	margin: 0 0 14px;
	font-size: 30px;
	line-height: 1.25;
	font-weight: 700;
	color: #111827;
}

.message {
	max-width: 430px;
	margin: 0 auto 30px;
	font-size: 15px;
	line-height: 1.7;
	color: #6b7280;
}

.actions {
	display: flex;
	justify-content: center;
}

.btn {
	display: inline-flex;
	align-items: center;
	justify-content: center;
	min-width: 140px;
	padding: 12px 24px;
	border-radius: 8px;
	font-size: 14px;
	font-weight: 600;
	text-decoration: none;
	transition: all 0.2s ease;
}

.retry-btn {
	background: #111827;
	color: #ffffff;
	border: 1px solid #111827;
}

.retry-btn:hover {
	background: #374151;
	border-color: #374151;
}

.help-text {
	margin-top: 28px;
	padding-top: 20px;
	border-top: 1px solid #f0f0f0;
	font-size: 12px;
	line-height: 1.6;
	color: #9ca3af;
}

/* ============================================================
   LARGE TABLET
   ============================================================ */
@media ( max-width : 900px) {
	.error-card {
		max-width: 520px;
		padding: 44px 36px;
	}
	h1 {
		font-size: 28px;
	}
}

/* ============================================================
   TABLET
   ============================================================ */
@media ( max-width : 768px) {
	body {
		padding: 20px;
	}
	.error-card {
		padding: 40px 30px;
		border-radius: 14px;
	}
	.brand {
		margin-bottom: 28px;
		font-size: 19px;
	}
	.error-icon {
		width: 68px;
		height: 68px;
		margin-bottom: 22px;
		font-size: 32px;
	}
	h1 {
		font-size: 27px;
	}
	.message {
		font-size: 14px;
		max-width: 400px;
	}
}

/* ============================================================
   MOBILE
   ============================================================ */
@media ( max-width : 576px) {
	body {
		padding: 16px;
	}
	.error-card {
		padding: 36px 24px;
		border-radius: 14px;
	}
	.brand {
		margin-bottom: 26px;
		font-size: 18px;
	}
	.error-icon {
		width: 64px;
		height: 64px;
		margin-bottom: 20px;
		font-size: 30px;
	}
	.error-code {
		font-size: 12px;
		margin-bottom: 7px;
	}
	h1 {
		font-size: 24px;
		line-height: 1.3;
		margin-bottom: 12px;
	}
	.message {
		font-size: 14px;
		line-height: 1.6;
		margin-bottom: 26px;
	}
	.btn {
		width: 100%;
		min-width: 0;
		padding: 12px 18px;
	}
	.help-text {
		margin-top: 24px;
		padding-top: 18px;
		font-size: 11px;
	}
}

/* ============================================================
   SMALL MOBILE
   ============================================================ */
@media ( max-width : 400px) {
	body {
		padding: 12px;
	}
	.error-card {
		padding: 30px 18px;
		border-radius: 12px;
	}
	.brand {
		font-size: 17px;
		margin-bottom: 22px;
	}
	.error-icon {
		width: 58px;
		height: 58px;
		margin-bottom: 18px;
		font-size: 27px;
	}
	h1 {
		font-size: 22px;
	}
	.message {
		font-size: 13px;
	}
	.btn {
		font-size: 13px;
		padding: 11px 16px;
	}
}

/* ============================================================
   VERY SMALL MOBILE
   ============================================================ */
@media ( max-width : 320px) {
	body {
		padding: 8px;
	}
	.error-card {
		padding: 26px 15px;
	}
	.brand {
		font-size: 16px;
	}
	.error-icon {
		width: 54px;
		height: 54px;
		font-size: 25px;
	}
	h1 {
		font-size: 20px;
	}
	.message {
		font-size: 12px;
		line-height: 1.5;
	}
	.btn {
		font-size: 12px;
		padding: 10px 14px;
	}
	.help-text {
		font-size: 10px;
	}
}
</style>

</head>

<body>

	<div class="error-wrapper">

		<div class="error-card">

			<div class="brand">
				Movie<span>Ticket</span>
			</div>

			<div class="error-icon">!</div>

			<div class="error-code">Service Unavailable</div>

			<h1>We’re having trouble right now</h1>

			<p class="message">We couldn't load the movie information at the
				moment. This may be a temporary service issue. Please try again in a
				moment.</p>

			<div class="actions">

				<a class="btn retry-btn"
					href="${pageContext.request.contextPath}/home"> Try Again </a>

			</div>

			<div class="help-text">If the problem continues, please try
				again later.</div>

		</div>

	</div>

</body>

</html>

