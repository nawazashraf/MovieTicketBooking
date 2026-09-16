function togglePassword(inputId, button) {

	const input = document.getElementById(inputId);

	if (input.type === "password") {

		input.type = "text";

		button.textContent = "Hide";

	} else {

		input.type = "password";

		button.textContent = "Show";

	}

}


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


/* ========================================
   PASSWORD VALIDATION
======================================== */

function validatePassword() {

	const value =

		newPassword.value;


	if (value === "") {

		strengthText.textContent =
			"Password is required.";

		return false;
	}


	if (value.length < 8) {

		strengthText.textContent =
			"Password must contain at least 8 characters.";

		return false;
	}


	strengthText.textContent =
		"";


	return true;
}


/* ========================================
   PASSWORD STRENGTH
======================================== */

function updatePasswordStrength() {

	const value =

		newPassword.value;


	let strength = 0;


	if (value.length >= 8) {

		strength++;

	}


	if (/[A-Z]/.test(value)) {

		strength++;

	}


	if (/[a-z]/.test(value)) {

		strength++;

	}


	if (/[0-9]/.test(value)) {

		strength++;

	}


	if (/[^A-Za-z0-9]/.test(value)) {

		strength++;

	}


	if (value.length === 0) {

		strengthBar.style.width =
			"0%";

		strengthBar.style.background =
			"";

		strengthText.textContent =
			"Use 8 or more characters";

		return;

	}


	if (strength <= 2) {

		strengthBar.style.width =
			"35%";

		strengthBar.style.background =
			"#d92d20";

		strengthText.textContent =
			"Weak password";

	} else if (strength <= 4) {

		strengthBar.style.width =
			"65%";

		strengthBar.style.background =
			"#f79009";

		strengthText.textContent =
			"Good password";

	} else {

		strengthBar.style.width =
			"100%";

		strengthBar.style.background =
			"#12b76a";

		strengthText.textContent =
			"Strong password";

	}

}


/* ========================================
   PASSWORD INPUT
======================================== */

newPassword.addEventListener(

	"input",

	function() {

		updatePasswordStrength();

	}

);


newPassword.addEventListener(

	"blur",

	function() {

		validatePassword();

	}

);


/* ========================================
   CONFIRM PASSWORD
======================================== */

confirmPassword.addEventListener(

	"input",

	function() {

		if (confirmPassword.value === "") {

			matchMessage.textContent = "";

		} else if (

			newPassword.value ===
			confirmPassword.value

		) {

			matchMessage.textContent =
				"Passwords match";

			matchMessage.style.color =
				"#16803c";

		} else {

			matchMessage.textContent =
				"Passwords do not match";

			matchMessage.style.color =
				"#e50914";

		}

	}

);


/* ========================================
   FORM SUBMIT
======================================== */

document

	.getElementById("passwordForm")

	.addEventListener(

		"submit",

		function(event) {


			const passwordValid =

				validatePassword();


			if (!passwordValid) {

				event.preventDefault();

				newPassword.focus();

				return;

			}


			if (

				newPassword.value !==
				confirmPassword.value

			) {

				event.preventDefault();

				matchMessage.textContent =
					"Passwords do not match";

				matchMessage.style.color =
					"#e50914";

				confirmPassword.focus();

				return;

			}

		}

	);