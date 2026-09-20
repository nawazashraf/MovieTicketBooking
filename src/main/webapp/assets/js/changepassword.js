
function togglePassword(inputId, button) {

	const input =
		document.getElementById(inputId);

	if (!input) {
		return;
	}

	if (input.type === "password") {

		input.type = "text";

		button.textContent = "Hide";

	} else {

		input.type = "password";

		button.textContent = "Show";

	}

}


/* =========================================================
PASSWORD ELEMENTS
========================================================= */

const newPassword =
	document.getElementById("newPassword");

const confirmPassword =
	document.getElementById("confirmPassword");

const strengthBar =
	document.getElementById("strengthBar");

const strengthText =
	document.getElementById("strengthText");

const matchMessage =
	document.getElementById("matchMessage");

const newPasswordError =
	document.getElementById("newPasswordError");


/* =========================================================
SHOW ERROR
========================================================= */

function showError(element, message) {

	if (!element) {
		return;
	}

	element.textContent = message;

	element.style.display = "block";

	element.style.color = "#d92d20";

}


/* =========================================================
CLEAR ERROR
========================================================= */

function clearError(element) {

	if (!element) {
		return;
	}

	element.textContent = "";

	element.style.display = "none";

}


/* =========================================================
PASSWORD VALIDATION
========================================================= */

function validatePassword() {

	const value =
		newPassword.value;

	clearError(newPasswordError);


	if (value === "") {

		showError(
			newPasswordError,
			"Password is required."
		);

		return false;

	}


	if (value !== value.trim()) {

		showError(
			newPasswordError,
			"Password must not contain leading or trailing spaces."
		);

		return false;

	}


	/*
	 * Do not show an error message here.
	 * The strength section already displays:
	 * "Minimum 8 characters"
	 */

	if (value.length < 8) {

		return false;

	}


	if (value.length > 128) {

		showError(
			newPasswordError,
			"Password cannot exceed 128 characters."
		);

		return false;

	}


	return true;

}


/* =========================================================
PASSWORD STRENGTH
========================================================= */

function updatePasswordStrength() {

	const value =
		newPassword.value;


	if (value.length === 0) {

		strengthBar.style.width = "0%";

		strengthBar.style.background = "";

		strengthText.textContent =
			"Minimum 8 characters";

		return;

	}


	if (value.length < 8) {

		strengthBar.style.width = "35%";

		strengthBar.style.background =
			"#d92d20";

		strengthText.textContent =
			"Minimum 8 characters";

	} else if (value.length < 12) {

		strengthBar.style.width = "65%";

		strengthBar.style.background =
			"#f79009";

		strengthText.textContent =
			"Password length is valid";

	} else {

		strengthBar.style.width = "100%";

		strengthBar.style.background =
			"#12b76a";

		strengthText.textContent =
			"Password length is valid";

	}

}


/* =========================================================
CONFIRM PASSWORD VALIDATION
========================================================= */

function validateConfirmPassword() {

	const passwordValue =
		newPassword.value;

	const confirmValue =
		confirmPassword.value;


	matchMessage.textContent = "";

	matchMessage.style.color = "";


	if (confirmValue === "") {

		return false;

	}


	if (passwordValue !== confirmValue) {

		matchMessage.textContent =
			"Passwords do not match.";

		matchMessage.style.color =
			"#d92d20";

		return false;

	}


	matchMessage.textContent =
		"Passwords match.";

	matchMessage.style.color =
		"#16803c";

	return true;

}


/* =========================================================
PASSWORD INPUT
========================================================= */

if (newPassword) {

	newPassword.addEventListener(
		"input",
		function() {

			updatePasswordStrength();

			validatePassword();

			if (confirmPassword.value !== "") {

				validateConfirmPassword();

			}

		}
	);


	newPassword.addEventListener(
		"blur",
		function() {

			validatePassword();

		}
	);

}


/* =========================================================
CONFIRM PASSWORD INPUT
========================================================= */

if (confirmPassword) {

	confirmPassword.addEventListener(
		"input",
		function() {

			validateConfirmPassword();

		}
	);


	confirmPassword.addEventListener(
		"blur",
		function() {

			validateConfirmPassword();

		}
	);

}


/* =========================================================
FORM SUBMIT
========================================================= */

const passwordForm =
	document.getElementById("passwordForm");

if (passwordForm) {

	passwordForm.addEventListener(
		"submit",
		function(event) {

			const passwordValid =
				validatePassword();


			if (!passwordValid) {

				event.preventDefault();

				newPassword.focus();

				return;

			}


			const confirmValid =
				validateConfirmPassword();


			if (!confirmValid) {

				event.preventDefault();

				confirmPassword.focus();

				return;

			}

		}
	);

}



