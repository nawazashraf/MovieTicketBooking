
document.addEventListener("DOMContentLoaded", function() {

	const form = document.getElementById("loginForm");

	const email = document.getElementById("email");

	const password = document.getElementById("password");

	const emailError = document.getElementById("emailError");

	const passwordError =
		document.getElementById("passwordError");

	const passwordToggle =
		document.getElementById("passwordToggle");

	const loginButton =
		document.getElementById("loginButton");

	const buttonText =
		document.getElementById("buttonText");


	/* ========================================
	   PASSWORD SHOW / HIDE
	   ======================================== */

	passwordToggle.addEventListener("click", function() {

		const isHidden =
			password.type === "password";


		if (isHidden) {

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

	});


	/* ========================================
	   EMAIL VALIDATION
	   ======================================== */

	function validateEmail() {

		const value = email.value.trim();

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
	   PASSWORD VALIDATION
	   ======================================== */

	function validatePassword() {

		const value = password.value;


		if (value.trim() === "") {

			showError(
				password,
				passwordError,
				"Password is required."
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

		input.classList.add("input-error");

		errorElement.textContent = message;

		errorElement.classList.add("show");

	}


	/* ========================================
	   CLEAR ERROR
	   ======================================== */

	function clearError(
		input,
		errorElement
	) {

		input.classList.remove("input-error");

		errorElement.textContent = "";

		errorElement.classList.remove("show");

	}


	/* ========================================
	   EMAIL LIVE VALIDATION
	   ======================================== */

	email.addEventListener("blur", function() {

		validateEmail();

	});


	email.addEventListener("input", function() {

		if (
			email.classList.contains("input-error")
		) {
			validateEmail();
		}

	});


	/* ========================================
	   PASSWORD LIVE VALIDATION
	   ======================================== */

	password.addEventListener("blur", function() {

		validatePassword();

	});


	password.addEventListener("input", function() {

		if (
			password.classList.contains("input-error")
		) {
			validatePassword();
		}

	});


	/* ========================================
	   FORM SUBMIT
	   ======================================== */

	form.addEventListener("submit", function(event) {

		const emailValid =
			validateEmail();

		const passwordValid =
			validatePassword();


		if (!emailValid || !passwordValid) {

			event.preventDefault();


			if (!emailValid) {

				email.focus();

			} else {

				password.focus();

			}

			return;

		}


		/*
		 * Disable the button immediately.
		 *
		 * This prevents accidental double-clicks
		 * from submitting the login form twice.
		 */

		loginButton.disabled = true;


		/*
		 * Show professional loading state.
		 */

		buttonText.innerHTML =
			'<span class="login-spinner"></span> Signing in...';


		/*
		 * Allow the form to continue normally.
		 *
		 * The request still goes to:
		 *
		 * /login
		 *
		 * and your existing LoginServlet
		 * handles authentication.
		 */

	});


	/* ========================================
	   ENTER KEY SUPPORT
	   ======================================== */

	password.addEventListener("keydown", function(event) {

		if (event.key === "Enter") {

			form.requestSubmit();

		}

	});


	/* ========================================
	   PREVENT SPACE BEFORE EMAIL
	   ======================================== */

	email.addEventListener("input", function() {

		const cursorPosition =
			email.selectionStart;

		const cleanedValue =
			email.value.replace(/\s/g, "");


		if (email.value !== cleanedValue) {

			email.value = cleanedValue;

			email.setSelectionRange(
				Math.max(0, cursorPosition - 1),
				Math.max(0, cursorPosition - 1)
			);

		}

	});

});

