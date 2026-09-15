
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
			cardDetails.style.display = "none";
		}

	} else {

		if (qrCard) {
			qrCard.style.display = "none";
		}

		if (cardDetails) {
			cardDetails.style.display = "block";
		}
	}
}


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

	} else {

		if (qrCard) {
			qrCard.style.display = "none";
		}

		if (cardDetails) {
			cardDetails.classList.add("active");
		}
	}
}




// CARD NUMBER

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


// EXPIRY DATE

var expiry =
	document.getElementById("expiryDate");

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


// CVV

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


// PC PAYMENT STATUS CHECK

var paymentHandled = false;

var bookingIdInput =
	document.querySelector(
		'input[name="bookingId"]'
	);


if (bookingIdInput) {

	var bookingId =
		bookingIdInput.value;


	setInterval(function() {

		if (paymentHandled) {
			return;
		}


		fetch(
			window.location.pathname +
			"?bookingId=" +
			encodeURIComponent(bookingId) +
			"&check=true"
		)

			.then(function(response) {

				return response.text();

			})

			.then(function(status) {

				status =
					status.trim();


				console.log(
					"Payment Status: " +
					status
				);


				if (status === "SUCCESS") {

					paymentHandled = true;


					var qrStatus =
						document.getElementById(
							"qrStatus"
						);


					if (qrStatus) {

						qrStatus.innerText =
							"Payment Successful ✓";

					}


					var proceedButton =
						document.getElementById(
							"proceedPayButton"
						);


					if (proceedButton) {

						proceedButton.click();

					}

				}

			})

			.catch(function(error) {

				console.log(
					"Payment check error:",
					error
				);

			});

	}, 1000);

}



