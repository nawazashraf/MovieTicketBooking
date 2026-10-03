/* =========================================================
   INACTIVE POPUP
========================================================= */

function closeInactivePopup() {

	const overlay =
		document.getElementById("inactiveOverlay");

	if (!overlay) {
		return;
	}

	overlay.style.display = "none";

	document.body.style.overflow = "";

}


/* =========================================================
   EDIT PROFILE
========================================================= */

function openEditProfile() {

	const overlay =
		document.getElementById("editProfileOverlay");

	if (!overlay) {
		return;
	}

	overlay.classList.add("open");

	document.body.style.overflow = "hidden";

	setTimeout(function() {

		const name =
			document.getElementById("editName");

		if (name) {

			name.focus();

			name.select();

		}

	}, 300);

}


function closeEditProfile(event) {

	if (event &&
		event.target &&
		event.target.id !== "editProfileOverlay") {

		return;

	}

	const overlay =
		document.getElementById("editProfileOverlay");

	if (!overlay) {
		return;
	}

	overlay.classList.remove("open");

	document.body.style.overflow = "";

}


/* =========================================================
   ESCAPE
========================================================= */

document.addEventListener("keydown", function(event) {

	if (event.key !== "Escape") {
		return;
	}

	const overlay =
		document.getElementById("editProfileOverlay");

	if (overlay &&
		overlay.classList.contains("open")) {

		closeEditProfile();

	}

});


/* =========================================================
   PASSWORD TOGGLE
========================================================= */

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


function toggleEditPassword(inputId, button) {

	togglePassword(inputId, button);

}


/* =========================================================
   PROFILE VALIDATION
========================================================= */

document.addEventListener("DOMContentLoaded", function() {

	const name =
		document.getElementById("editName");

	const phone =
		document.getElementById("editPhone");

	const securityQuestion =
		document.getElementById(
			"editSecurityQuestion"
		);

	const securityAnswer =
		document.getElementById(
			"editSecurityAnswer"
		);

	const newPassword =
		document.getElementById("editPassword");

	const confirmPassword =
		document.getElementById(
			"editConfirmPassword"
		);

	const strengthBar =
		document.getElementById(
			"editPasswordStrengthFill"
		);

	const strengthText =
		document.getElementById(
			"editPasswordStrengthText"
		);

	const strengthContainer =
		document.getElementById(
			"editPasswordStrength"
		);

	const matchMessage =
		document.getElementById(
			"editPasswordMatch"
		);

	const form =
		document.getElementById(
			"editProfileForm"
		);


	if (!form ||
		!name ||
		!phone ||
		!securityQuestion ||
		!securityAnswer ||
		!newPassword ||
		!confirmPassword) {

		return;

	}


	const nameError =
		document.getElementById(
			"editNameError"
		);

	const phoneError =
		document.getElementById(
			"editPhoneError"
		);

	const securityQuestionError =
		document.getElementById(
			"editSecurityQuestionError"
		);

	const securityAnswerError =
		document.getElementById(
			"editSecurityAnswerError"
		);

	const passwordError =
		document.getElementById(
			"editPasswordError"
		);

	const confirmPasswordError =
		document.getElementById(
			"editConfirmPasswordError"
		);


	/* =====================================================
	   ERROR
	===================================================== */

	function showError(element, message) {

		if (!element) {
			return;
		}

		element.textContent = message;

		element.style.display = "block";

	}


	function clearError(element) {

		if (!element) {
			return;
		}

		element.textContent = "";

		element.style.display = "none";

	}


	/* =====================================================
	   NAME
	===================================================== */

	function formatName(value) {

		value =
			value
				.trim()
				.replace(/\s+/g, " ");

		if (value === "") {
			return "";
		}

		return value
			.toLowerCase()
			.split(" ")
			.map(function(word) {

				return word.charAt(0).toUpperCase() +
					word.substring(1);

			})
			.join(" ");

	}


	function validateName() {

		const value =
			name.value.trim();

		clearError(nameError);


		if (value === "") {

			showError(
				nameError,
				"Full name is required."
			);

			return false;

		}


		if (!/^[A-Za-z ]+$/.test(value)) {

			showError(
				nameError,
				"Name can contain only letters and spaces."
			);

			return false;

		}


		const formatted =
			value.replace(/\s+/g, " ");

		if (formatted.length < 2) {

			showError(
				nameError,
				"Name must contain at least 2 characters."
			);

			return false;

		}


		if (formatted.length > 50) {

			showError(
				nameError,
				"Name cannot exceed 50 characters."
			);

			return false;

		}


		name.value =
			formatName(formatted);

		return true;

	}


	/* =====================================================
	   PHONE
	===================================================== */

	function validatePhone() {

		const value =
			phone.value.trim();

		clearError(phoneError);


		if (value === "") {

			showError(
				phoneError,
				"Phone number is required."
			);

			return false;

		}


		if (value.startsWith("0")) {

			showError(
				phoneError,
				"Phone number must not start with 0."
			);

			return false;

		}


		if (!/^\d{10}$/.test(value)) {

			showError(
				phoneError,
				"Phone number must be exactly 10 digits."
			);

			return false;

		}


		return true;

	}


	/* =====================================================
	   SECURITY QUESTION
	===================================================== */

	function validateSecurityQuestion() {

		clearError(
			securityQuestionError
		);


		if (securityQuestion.value === "") {

			showError(
				securityQuestionError,
				"Please select a security question."
			);

			return false;

		}


		return true;

	}


	/* =====================================================
	   SECURITY ANSWER
	===================================================== */

	function validateSecurityAnswer() {

		const value =
			securityAnswer.value.trim();

		clearError(
			securityAnswerError
		);


		if (value === "") {

			showError(
				securityAnswerError,
				"Security answer is required."
			);

			return false;

		}


		if (value.length < 2) {

			showError(
				securityAnswerError,
				"Security answer must contain at least 2 characters."
			);

			return false;

		}


		if (value.length > 255) {

			showError(
				securityAnswerError,
				"Security answer cannot exceed 255 characters."
			);

			return false;

		}


		return true;

	}


	/* =====================================================
	   PASSWORD
	===================================================== */

	function validatePassword() {

		const value =
			newPassword.value;

		clearError(passwordError);


		if (value === "") {
			return true;
		}


		if (value !== value.trim()) {

			showError(
				passwordError,
				"Password must not contain leading or trailing spaces."
			);

			return false;

		}


		if (value.length < 8) {

			showError(
				passwordError,
				"Password must contain at least 8 characters."
			);

			return false;

		}


		if (value.length > 128) {

			showError(
				passwordError,
				"Password cannot exceed 128 characters."
			);

			return false;

		}


		return true;

	}


	/* =====================================================
	   PASSWORD STRENGTH
	===================================================== */

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

		if (/\d/.test(value)) {
			strength++;
		}

		if (/[^A-Za-z0-9]/.test(value)) {
			strength++;
		}


		if (value.length === 0) {

			strengthBar.style.width = "0%";

			strengthText.textContent =
				"Use 8 or more characters";

			strengthContainer.style.display =
				"none";

		} else if (strength <= 2) {

			strengthContainer.style.display =
				"block";

			strengthBar.style.width = "35%";

			strengthBar.style.backgroundColor =
				"#d92d20";

			strengthText.textContent =
				"Weak password";

		} else if (strength <= 4) {

			strengthContainer.style.display =
				"block";

			strengthBar.style.width = "65%";

			strengthBar.style.backgroundColor =
				"#f79009";

			strengthText.textContent =
				"Good password";

		} else {

			strengthContainer.style.display =
				"block";

			strengthBar.style.width = "100%";

			strengthBar.style.backgroundColor =
				"#12b76a";

			strengthText.textContent =
				"Strong password";

		}

	}


	/* =====================================================
	   CONFIRM PASSWORD
	===================================================== */

	function validateConfirmPassword() {

		const passwordValue =
			newPassword.value;

		const confirmValue =
			confirmPassword.value;


		clearError(confirmPasswordError);

		matchMessage.textContent = "";

		matchMessage.style.color = "";


		if (passwordValue === "" &&
			confirmValue === "") {

			return true;

		}


		if (confirmValue === "") {

			return false;

		}


		if (passwordValue !== confirmValue) {

			matchMessage.textContent =
				"Passwords do not match";

			matchMessage.style.color =
				"#d92d20";

			return false;

		}


		matchMessage.textContent =
			"Passwords match";

		matchMessage.style.color =
			"#16803c";

		return true;

	}


	/* =====================================================
	   INPUT EVENTS
	===================================================== */

	name.addEventListener(
		"input",
		function() {

			name.value =
				name.value.replace(
					/[^A-Za-z ]/g,
					""
				);

			name.value =
				name.value.replace(
					/\s+/g,
					" "
				);

			if (name.value.length > 50) {

				name.value =
					name.value.substring(0, 50);

			}

			if (name.value.trim() !== "") {

				name.value =
					formatName(name.value);

			}

		}
	);


	name.addEventListener(
		"blur",
		validateName
	);


	phone.addEventListener(
		"input",
		function() {

			phone.value =
				phone.value.replace(
					/\D/g,
					""
				);

			if (phone.value.length > 10) {

				phone.value =
					phone.value.substring(0, 10);

			}

		}
	);


	phone.addEventListener(
		"blur",
		validatePhone
	);


	securityQuestion.addEventListener(
		"change",
		validateSecurityQuestion
	);


	securityQuestion.addEventListener(
		"blur",
		validateSecurityQuestion
	);


	securityAnswer.addEventListener(
		"input",
		validateSecurityAnswer
	);


	securityAnswer.addEventListener(
		"blur",
		validateSecurityAnswer
	);


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
		validatePassword
	);


	confirmPassword.addEventListener(
		"input",
		validateConfirmPassword
	);


	/* =====================================================
	   FORM SUBMIT
	===================================================== */

	form.addEventListener(
		"submit",
		function(event) {

			if (!validateName()) {

				event.preventDefault();

				name.focus();

				return;

			}


			if (!validatePhone()) {

				event.preventDefault();

				phone.focus();

				return;

			}


			if (!validateSecurityQuestion()) {

				event.preventDefault();

				securityQuestion.focus();

				return;

			}


			if (!validateSecurityAnswer()) {

				event.preventDefault();

				securityAnswer.focus();

				return;

			}


			if (!validatePassword()) {

				event.preventDefault();

				newPassword.focus();

				return;

			}


			if (!validateConfirmPassword()) {

				event.preventDefault();

				confirmPassword.focus();

				return;

			}


			name.value =
				name.value.trim();

			phone.value =
				phone.value.trim();

			securityAnswer.value =
				securityAnswer.value.trim();

		}
	);

});


/* =========================================================
   SUCCESS POPUP
========================================================= */

function closeProfileUpdatePopup() {

	const popup =
		document.getElementById(
			"profileUpdatePopup"
		);

	if (!popup) {
		return;
	}

	popup.classList.add("hide");

	setTimeout(function() {

		popup.remove();

	}, 300);

}


/* =========================================================
   ERROR POPUP
========================================================= */

function closeProfileUpdateErrorPopup() {

	const popup =
		document.getElementById(
			"profileUpdateErrorPopup"
		);

	if (!popup) {
		return;
	}

	popup.classList.add("hide");

	setTimeout(function() {

		popup.remove();

	}, 300);

}


/* =========================================================
   AUTO CLOSE POPUPS
========================================================= */

document.addEventListener(
	"DOMContentLoaded",
	function() {

		const successPopup =
			document.getElementById(
				"profileUpdatePopup"
			);

		if (successPopup) {

			setTimeout(
				closeProfileUpdatePopup,
				4000
			);

		}


		const errorPopup =
			document.getElementById(
				"profileUpdateErrorPopup"
			);

		if (errorPopup) {

			setTimeout(
				closeProfileUpdateErrorPopup,
				4000
			);

		}

	}
);