function togglePaymentInputs() {

	var upiRadio =
		document.querySelector(
			'input[name="paymentMethod"][value="UPI"]'
		);

	var qrCard =
		document.querySelector(
			'.qr-payment-card'
		);

	var cardDetails =
		document.querySelector(
			'.card-details'
		);

	if (!upiRadio) {
		return;
	}

	if (upiRadio.checked) {

		if (qrCard) {
			qrCard.style.display = "block";
		}

		if (cardDetails) {
			cardDetails.classList.remove("active");
		}

		startPaymentStatusCheck();

	} else {

		if (qrCard) {
			qrCard.style.display = "none";
		}

		if (cardDetails) {
			cardDetails.classList.add("active");
		}

		stopPaymentStatusCheck();
	}
}


/* =====================================================
   PAYMENT METHOD CHANGE
   ===================================================== */

var paymentMethods =
	document.querySelectorAll(
		'input[name="paymentMethod"]'
	);

paymentMethods.forEach(function(radio) {

	radio.addEventListener(
		"change",
		togglePaymentInputs
	);

});

togglePaymentInputs();


/* =====================================================
   QR PAYMENT STATUS CHECK
   ===================================================== */

var paymentCheckInterval = null;

function startPaymentStatusCheck() {

	if (paymentCheckInterval !== null) {
		return;
	}

	var bookingInput =
		document.querySelector(
			'input[name="bookingId"]'
		);

	if (!bookingInput) {
		return;
	}

	var bookingId = bookingInput.value;

	if (!bookingId) {
		return;
	}

	var qrStatus =
		document.getElementById("qrStatus");


	if (qrStatus) {
		qrStatus.innerText =
			"Waiting for payment from phone...";
	}


	paymentCheckInterval = setInterval(function() {

		fetch(
			window.contextPath +
			"/payment?bookingId=" +
			encodeURIComponent(bookingId) +
			"&check=true"
		)

			.then(function(response) {
				return response.text();
			})

			.then(function(status) {

				status = status.trim();

				console.log(
					"Payment Status:",
					status
				);


				if (status === "SUCCESS") {

					stopPaymentStatusCheck();

					if (qrStatus) {
						qrStatus.innerText =
							"Payment received! Opening payment processing...";
					}


					setTimeout(function() {

						window.location.href =
							window.contextPath +
							"/payment?bookingId=" +
							encodeURIComponent(bookingId) +
							"&processing=true&paymentMethod=UPI";

					}, 500);

				}

			})

			.catch(function(error) {

				console.log(
					"Payment status check error:",
					error
				);

			});

	}, 2000);
}


function stopPaymentStatusCheck() {

	if (paymentCheckInterval !== null) {

		clearInterval(
			paymentCheckInterval
		);

		paymentCheckInterval = null;
	}
}


/* =====================================================
   CARD NUMBER
   ===================================================== */

var cardNumber =
	document.getElementById(
		"cardNumber"
	);

if (cardNumber) {

	cardNumber.addEventListener(
		"input",
		function() {

			var value =
				this.value.replace(
					/\D/g,
					""
				);

			value =
				value.substring(
					0,
					16
				);

			var formatted =
				value.match(
					/.{1,4}/g
				);

			this.value =
				formatted
					? formatted.join(" ")
					: "";

		}
	);

}


/* =====================================================
   EXPIRY DATE
   ===================================================== */

var expiry =
	document.getElementById(
		"expiryDate"
	);

if (expiry) {

	expiry.addEventListener(
		"input",
		function() {

			var value =
				this.value.replace(
					/\D/g,
					""
				);

			value =
				value.substring(
					0,
					4
				);

			if (value.length >= 3) {

				value =
					value.substring(0, 2) +
					"/" +
					value.substring(2);
			}

			this.value =
				value;

		}
	);

}


/* =====================================================
   CVV
   ===================================================== */

var cvv =
	document.getElementById(
		"cvv"
	);

if (cvv) {

	cvv.addEventListener(
		"input",
		function() {

			this.value =
				this.value
					.replace(/\D/g, "")
					.substring(0, 3);

		}
	);

}


/* =====================================================
   IMPORTANT:
   UPI BUTTON SHOULD NOT SUBMIT THE FORM
   ===================================================== */

var paymentForm =
	document.querySelector(
		'form.checkout-container'
	);

if (paymentForm) {

	paymentForm.addEventListener(
		"submit",
		function(event) {

			var upiRadio =
				document.querySelector(
					'input[name="paymentMethod"][value="UPI"]'
				);

			if (upiRadio && upiRadio.checked) {

				/*
				 * Do not submit the PC form.
				 * PC must wait for phone QR payment.
				 */

				event.preventDefault();

				var qrStatus =
					document.getElementById(
						"qrStatus"
					);

				if (qrStatus) {

					qrStatus.innerText =
						"Waiting for payment from phone...";

				}

				startPaymentStatusCheck();

			}

			/*
			 * CARD:
			 * Nothing is prevented.
			 * Existing logic continues normally.
			 */

		}
	);
}