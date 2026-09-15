
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

