document.addEventListener("DOMContentLoaded", function() {


	const form =
		document.getElementById("registerForm");


	const name =
		document.getElementById("name");


	const email =
		document.getElementById("email");


	const phone =
		document.getElementById("phone");


	const password =
		document.getElementById("password");


	const nameError =
		document.getElementById("nameError");


	const emailError =
		document.getElementById("emailError");


	const phoneError =
		document.getElementById("phoneError");


	const passwordError =
		document.getElementById("passwordError");


	const passwordToggle =
		document.getElementById("passwordToggle");


	const strengthBar =
		document.getElementById("strengthBar");


	const strengthText =
		document.getElementById("strengthText");


	const registerButton =
		document.getElementById("registerButton");


	const buttonText =
		document.getElementById("buttonText");


	/* ========================================
	   PASSWORD SHOW / HIDE
	======================================== */

	passwordToggle.addEventListener(
		"click",
		function() {

			if (password.type === "password") {

				password.type = "text";

				passwordToggle.textContent = "Hide";

				passwordToggle.setAttribute(
					"aria-label",
					"Hide password"
				);

			} else {

				password.type = "password";

				passwordToggle.textContent = "Show";

				passwordToggle.setAttribute(
					"aria-label",
					"Show password"
				);

			}

		}
	);


	/* ========================================
	   NAME VALIDATION
	======================================== */

	function validateName() {

		const value =
			name.value.trim();


		if (value === "") {

			showError(
				name,
				nameError,
				"Full name is required."
			);

			return false;
		}


		if (value.length < 2) {

			showError(
				name,
				nameError,
				"Please enter your full name."
			);

			return false;
		}


		clearError(
			name,
			nameError
		);

		return true;
	}


	/* ========================================
	   EMAIL VALIDATION
	======================================== */

	function validateEmail() {

		const value =
			email.value.trim();


		const pattern =
			/^[^\s@]+@[^\s@]+\.[^\s@]+$/;


		if (value === "") {

			showError(
				email,
				emailError,
				"Email address is required."
			);

			return false;
		}


		if (!pattern.test(value)) {

			showError(
				email,
				emailError,
				"Please enter a valid email address."
			);

			return false;
		}


		clearError(
			email,
			emailError
		);

		return true;
	}


	/* ========================================
	   PHONE VALIDATION
	======================================== */

	function validatePhone() {

		const value =
			phone.value.trim();


		if (value === "") {

			showError(
				phone,
				phoneError,
				"Phone number is required."
			);

			return false;
		}


		if (!/^[0-9]{10}$/.test(value)) {

			showError(
				phone,
				phoneError,
				"Phone number must be exactly 10 digits."
			);

			return false;
		}


		clearError(
			phone,
			phoneError
		);

		return true;
	}


	/* ========================================
	   PASSWORD VALIDATION
	======================================== */

	function validatePassword() {

		const value =
			password.value;


		if (value === "") {

			showError(
				password,
				passwordError,
				"Password is required."
			);

			return false;
		}


		if (value.length < 8) {

			showError(
				password,
				passwordError,
				"Password must contain at least 8 characters."
			);

			return false;
		}


		clearError(
			password,
			passwordError
		);

		return true;
	}


	/* ========================================
	   SHOW ERROR
	======================================== */

	function showError(
		input,
		errorElement,
		message
	) {

		input.classList.add(
			"input-error"
		);


		errorElement.textContent =
			message;


		errorElement.classList.add(
			"show"
		);

	}


	/* ========================================
	   CLEAR ERROR
	======================================== */

	function clearError(
		input,
		errorElement
	) {

		input.classList.remove(
			"input-error"
		);


		errorElement.textContent =
			"";


		errorElement.classList.remove(
			"show"
		);

	}


	/* ========================================
	   PASSWORD STRENGTH
	======================================== */

	function updatePasswordStrength() {

		const value =
			password.value;


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
	   INPUT EVENTS
	======================================== */

	name.addEventListener(
		"blur",
		validateName
	);


	email.addEventListener(
		"blur",
		validateEmail
	);


	phone.addEventListener(
		"blur",
		validatePhone
	);


	password.addEventListener(
		"blur",
		validatePassword
	);


	name.addEventListener(
		"input",
		function() {

			if (
				name.classList.contains(
					"input-error"
				)
			) {

				validateName();

			}

		}
	);


	email.addEventListener(
		"input",
		function() {

			if (
				email.classList.contains(
					"input-error"
				)
			) {

				validateEmail();

			}

		}
	);


	phone.addEventListener(
		"input",
		function() {

			phone.value =
				phone.value
					.replace(
						/[^0-9]/g,
						""
					)
					.slice(0, 10);


			if (
				phone.classList.contains(
					"input-error"
				)
			) {

				validatePhone();

			}

		}
	);


	password.addEventListener(
		"input",
		function() {

			updatePasswordStrength();


			if (
				password.classList.contains(
					"input-error"
				)
			) {

				validatePassword();

			}

		}
	);


	/* ========================================
	   FORM SUBMIT
	======================================== */

	form.addEventListener(
		"submit",
		function(event) {


			const nameValid =
				validateName();


			const emailValid =
				validateEmail();


			const phoneValid =
				validatePhone();


			const passwordValid =
				validatePassword();


			if (
				!nameValid ||
				!emailValid ||
				!phoneValid ||
				!passwordValid
			) {

				event.preventDefault();


				if (!nameValid) {

					name.focus();

				} else if (!emailValid) {

					email.focus();

				} else if (!phoneValid) {

					phone.focus();

				} else {

					password.focus();

				}

				return;
			}


			/* Prevent double submission */

			registerButton.disabled = true;


			/* Loading state */

			buttonText.innerHTML =
				'<span class="register-spinner"></span> Creating account...';

		}
	);

});