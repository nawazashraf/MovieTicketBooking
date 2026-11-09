/* =====================================================
   PAYMENT BUTTON
===================================================== */

var proceedPayButton =
	document.getElementById("proceedPayButton");


function disablePayButton() {

	if (proceedPayButton) {

		proceedPayButton.disabled = true;

	}

}


function enablePayButton() {

	if (proceedPayButton) {

		proceedPayButton.disabled = false;

	}

}


/* =====================================================
   PAYMENT METHOD
===================================================== */

function togglePaymentInputs() {

	var upiRadio =
		document.querySelector(
			'input[name="paymentMethod"][value="UPI"]'
		);

	var cardDetails =
		document.querySelector(
			".card-details"
		);

	var upiDetails =
		document.querySelector(
			".upi-details"
		);

	if (!upiRadio) {
		return;
	}

	if (upiRadio.checked) {
		if (upiDetails) {
			upiDetails.classList.add("active");
		}
		if (cardDetails) {
			cardDetails.classList.remove("active");
		}
		updatePayButton(false);
	} else {
		if (upiDetails) {
			upiDetails.classList.remove("active");
		}
		if (cardDetails) {
			cardDetails.classList.add("active");
		}
		updatePayButton(false);
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


/* =====================================================
   ERROR HANDLING
===================================================== */

function setFieldError(input, errorElement, message) {

	if (errorElement) {

		errorElement.textContent = message;

	}

	if (input) {

		input.classList.add("input-error");

		input.classList.remove("input-valid");

	}

}


function clearFieldError(input, errorElement) {

	if (errorElement) {

		errorElement.textContent = "";

	}

	if (input) {

		input.classList.remove("input-error");

	}

}


/* =====================================================
   CARD NUMBER
===================================================== */

var cardNumber =
	document.getElementById("cardNumber");

var cardNumberError =
	document.getElementById("cardNumberError");


function isValidLuhn(number) {

	var sum = 0;

	var shouldDouble = false;


	for (
		var i = number.length - 1;
		i >= 0;
		i--
	) {

		var digit =
			parseInt(number.charAt(i), 10);


		if (shouldDouble) {

			digit = digit * 2;


			if (digit > 9) {

				digit = digit - 9;

			}

		}


		sum += digit;

		shouldDouble = !shouldDouble;

	}


	return sum % 10 === 0;

}


function validateCardNumber(showError) {

	if (!cardNumber) {

		return false;

	}


	var value =
		cardNumber.value.replace(/\D/g, "");


	if (value.length === 0) {

		if (showError) {

			setFieldError(
				cardNumber,
				cardNumberError,
				"Card number is required"
			);

		}

		return false;

	}


	if (value.length < 16) {

		if (showError) {

			setFieldError(
				cardNumber,
				cardNumberError,
				"Card number must contain 16 digits"
			);

		}

		return false;

	}


	if (value.length > 16) {

		if (showError) {

			setFieldError(
				cardNumber,
				cardNumberError,
				"Card number must contain 16 digits"
			);

		}

		return false;

	}


	if (!isValidLuhn(value)) {

		if (showError) {

			setFieldError(
				cardNumber,
				cardNumberError,
				"Enter a valid card number"
			);

		}

		return false;

	}


	clearFieldError(
		cardNumber,
		cardNumberError
	);

	return true;

}


if (cardNumber) {

	cardNumber.addEventListener(
		"input",
		function() {

			var value =
				this.value.replace(/\D/g, "");


			value =
				value.substring(0, 16);


			var formatted =
				value.match(/.{1,4}/g);


			this.value =
				formatted
					? formatted.join(" ")
					: "";


			/*
			 * REAL-TIME VALIDATION
			 */
			validateCardNumber(true);


			/*
			 * UPDATE BUTTON IMMEDIATELY
			 */
			updatePayButton(false);

		}
	);


	cardNumber.addEventListener(
		"blur",
		function() {

			validateCardNumber(true);

			updatePayButton(false);

		}
	);

}


/* =====================================================
   CARD HOLDER
===================================================== */

var cardHolder =
	document.getElementById("cardHolder");

var cardHolderError =
	document.getElementById("cardHolderError");


function validateCardHolder(showError) {

	if (!cardHolder) {

		return false;

	}


	var value =
		cardHolder.value.trim();


	if (value.length === 0) {

		if (showError) {

			setFieldError(
				cardHolder,
				cardHolderError,
				"Card holder name is required"
			);

		}

		return false;

	}


	if (!/^[A-Za-z ]+$/.test(value)) {

		if (showError) {

			setFieldError(
				cardHolder,
				cardHolderError,
				"Name can contain only letters and spaces"
			);

		}

		return false;

	}


	if (value.length < 2) {

		if (showError) {

			setFieldError(
				cardHolder,
				cardHolderError,
				"Enter a valid card holder name"
			);

		}

		return false;

	}


	clearFieldError(
		cardHolder,
		cardHolderError
	);

	return true;

}


if (cardHolder) {

	cardHolder.addEventListener(
		"input",
		function() {

			var value =
				this.value.replace(
					/[^A-Za-z ]/g,
					""
				);


			value =
				value.replace(
					/\s+/g,
					" "
				);


			this.value =
				value.toUpperCase();


			/*
			 * REAL-TIME VALIDATION
			 */
			validateCardHolder(true);


			/*
			 * UPDATE BUTTON IMMEDIATELY
			 */
			updatePayButton(false);

		}
	);


	cardHolder.addEventListener(
		"blur",
		function() {

			validateCardHolder(true);

			updatePayButton(false);

		}
	);

}


/* =====================================================
   EXPIRY DATE
===================================================== */

var expiry =
	document.getElementById("expiryDate");

var expiryError =
	document.getElementById("expiryDateError");


function validateExpiry(showError) {

	if (!expiry) {

		return false;

	}


	var value =
		expiry.value.trim();


	if (value.length === 0) {

		if (showError) {

			setFieldError(
				expiry,
				expiryError,
				"Expiry date is required"
			);

		}

		return false;

	}


	if (!/^\d{2}\/\d{2}$/.test(value)) {

		if (showError) {

			setFieldError(
				expiry,
				expiryError,
				"Enter expiry date as MM/YY"
			);

		}

		return false;

	}


	var parts =
		value.split("/");


	var month =
		parseInt(parts[0], 10);


	var year =
		parseInt(parts[1], 10);


	if (
		month < 1 ||
		month > 12
	) {

		if (showError) {

			setFieldError(
				expiry,
				expiryError,
				"Enter a valid month"
			);

		}

		return false;

	}


	var currentDate =
		new Date();


	var currentYear =
		currentDate.getFullYear() % 100;


	var currentMonth =
		currentDate.getMonth() + 1;


	if (
		year < currentYear ||
		(
			year === currentYear &&
			month < currentMonth
		)
	) {

		if (showError) {

			setFieldError(
				expiry,
				expiryError,
				"Card has expired"
			);

		}

		return false;

	}


	clearFieldError(
		expiry,
		expiryError
	);

	return true;

}


if (expiry) {

	expiry.addEventListener(
		"input",
		function() {

			var value =
				this.value.replace(/\D/g, "");


			value =
				value.substring(0, 4);


			if (value.length >= 3) {

				value =
					value.substring(0, 2) +
					"/" +
					value.substring(2);

			}


			this.value =
				value;


			/*
			 * REAL-TIME VALIDATION
			 */
			validateExpiry(true);


			/*
			 * UPDATE BUTTON IMMEDIATELY
			 */
			updatePayButton(false);

		}
	);


	expiry.addEventListener(
		"blur",
		function() {

			validateExpiry(true);

			updatePayButton(false);

		}
	);

}


/* =====================================================
   CVV
===================================================== */

var cvv =
	document.getElementById("cvv");

var cvvError =
	document.getElementById("cvvError");


function validateCVV(showError) {

	if (!cvv) {

		return false;

	}


	var value =
		cvv.value;


	if (value.length === 0) {

		if (showError) {

			setFieldError(
				cvv,
				cvvError,
				"CVV is required"
			);

		}

		return false;

	}


	if (!/^\d{3}$/.test(value)) {

		if (showError) {

			setFieldError(
				cvv,
				cvvError,
				"CVV must contain exactly 3 digits"
			);

		}

		return false;

	}


	clearFieldError(
		cvv,
		cvvError
	);

	return true;

}


if (cvv) {

	cvv.addEventListener(
		"input",
		function() {

			this.value =
				this.value
					.replace(/\D/g, "")
					.substring(0, 3);


			/*
			 * REAL-TIME VALIDATION
			 */
			validateCVV(true);


			/*
			 * UPDATE BUTTON IMMEDIATELY
			 */
			updatePayButton(false);

		}
	);


	cvv.addEventListener(
		"blur",
		function() {

			validateCVV(true);

			updatePayButton(false);

		}
	);

}


/* =====================================================
   COMPLETE CARD VALIDATION
===================================================== */

function validateCardForm(showErrors) {

	var cardValid =
		validateCardNumber(showErrors);


	var holderValid =
		validateCardHolder(showErrors);


	var expiryValid =
		validateExpiry(showErrors);


	var cvvValid =
		validateCVV(showErrors);


	var allValid =
		cardValid &&
		holderValid &&
		expiryValid &&
		cvvValid;


	if (allValid) {

		enablePayButton();

	} else {

		disablePayButton();

	}


	return allValid;

}

var upiId = document.getElementById("upiId");
var upiIdError = document.getElementById("upiIdError");

function validateUpiId(showError) {
	if (!upiId) {
		return false;
	}

	var value = upiId.value.trim();
	var valid = /^[A-Za-z0-9._-]{2,}@[A-Za-z][A-Za-z0-9.-]{1,62}$/.test(value);

	if (!valid && showError) {
		setFieldError(upiId, upiIdError, "Enter a valid UPI ID, for example name@bank");
	} else if (valid) {
		clearFieldError(upiId, upiIdError);
	}

	return valid;
}

function updatePayButton(showErrors) {
	var upiRadio = document.querySelector('input[name="paymentMethod"][value="UPI"]');
	if (upiRadio && upiRadio.checked) {
		if (validateUpiId(showErrors)) {
			enablePayButton();
		} else {
			disablePayButton();
		}
		return;
	}

	validateCardForm(showErrors);
}

if (upiId) {
	upiId.addEventListener("input", function() {
		updatePayButton(true);
	});

	upiId.addEventListener("blur", function() {
		updatePayButton(true);
	});
}


/* =====================================================
   FORM SUBMIT
===================================================== */

var paymentForm =
	document.querySelector(
		"form.checkout-container"
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
				if (!validateUpiId(true)) {
					event.preventDefault();
				}
				return;
			}

			if (!validateCardForm(true)) {
				event.preventDefault();
			}

		}
	);

}


/* =====================================================
   INITIAL STATE
===================================================== */

togglePaymentInputs();
disablePayButton();