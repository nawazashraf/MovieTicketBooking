<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>

<head>

<meta charset="UTF-8">

<title>Processing Payment</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/common.css">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/payment-processing.css">

</head>


<body>

	<%@ include file="/common/navbar.jsp"%>


	<main class="payment-processing-page">

		<div class="processing-card">

			<div class="loader"></div>

			<h1>Processing Payment</h1>

			<p>Please wait while we process your payment.</p>

			<span class="processing-note"> Do not refresh or close this
				page. </span>

		</div>

	</main>


	<form id="processPaymentForm"
		action="${pageContext.request.contextPath}/payment/process"
		method="post">

		<input type="hidden" name="bookingId" value="${bookingId}"> <input
			type="hidden" name="paymentMethod" value="${paymentMethod}">

	</form>


	<script>
		var paymentMethod = "${paymentMethod}";

		/* =================================================
		   CARD PAYMENT
		   ================================================= */

		if (paymentMethod === "CARD") {

			setTimeout(function() {

				document.getElementById("processPaymentForm").submit();

			}, 2500);

		}

		/* =================================================
		   QR / UPI PAYMENT
		   ================================================= */

		else {

			setTimeout(
					function() {

						window.location.href = "${pageContext.request.contextPath}/ticket?bookingId=${bookingId}";

					}, 2500);

		}
	</script>

</body>

</html>