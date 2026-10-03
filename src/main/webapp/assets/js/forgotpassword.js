document.addEventListener("DOMContentLoaded", function() {

	const form =
		document.getElementById("forgotPasswordForm");

	const email =
		document.getElementById("email");

	const emailError =
		document.getElementById("emailFieldError");

	const continueButton =
		document.getElementById("continueButton");

	const securityAnswer =
		document.getElementById("securityAnswer");

	const securityAnswerError =
		document.getElementById("securityAnswerFieldError");


	const emailPattern =
		/^[^\s@]+@[^\s@]+\.[^\s@]+$/;


	/* EMAIL VALIDATION */

	function validateEmail() {

		const value =
			email.value.trim();


		if (value === "") {

			emailError.textContent =
				"Email address is required.";

			return false;
		}


		if (!emailPattern.test(value)) {

			emailError.textContent =
				"Please enter a valid email address.";

			return false;
		}


		emailError.textContent = "";

		return true;
	}


	/* SECURITY ANSWER VALIDATION */

	function validateSecurityAnswer() {

		if (!securityAnswer) {
			return true;
		}

		const value =
			securityAnswer.value.trim();


		if (value === "") {

			securityAnswerError.textContent =
				"Security answer is required.";

			return false;
		}


		securityAnswerError.textContent = "";

		return true;
	}


	/* EMAIL INPUT */

	email.addEventListener("input", function() {

		validateEmail();

	});


	/* EMAIL BLUR */

	email.addEventListener("blur", function() {

		validateEmail();

	});


	/* SECURITY ANSWER INPUT */

	if (securityAnswer) {

		securityAnswer.addEventListener("input", function() {

			validateSecurityAnswer();

		});


		securityAnswer.addEventListener("blur", function() {

			validateSecurityAnswer();

		});

	}


	/* FORM SUBMIT */

	form.addEventListener(
		"submit",
		function(event) {

			if (!validateEmail()) {

				event.preventDefault();

				email.focus();

				return;
			}


			if (!validateSecurityAnswer()) {

				event.preventDefault();

				securityAnswer.focus();

				return;
			}

		}
	);


});