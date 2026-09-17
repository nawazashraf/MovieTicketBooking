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


	/* ==================== EMAIL OTP ==================== */


	const verifyEmailButton =
		document.getElementById("verifyEmailButton");


	const otpTray =
		document.getElementById("otpTray");


	const otp =
		document.getElementById("otp");


	const verifyOtpButton =
		document.getElementById("verifyOtpButton");


	const otpMessage =
		document.getElementById("otpMessage");


	const resendOtpButton =
		document.getElementById("resendOtpButton");


	const resendText =
		document.getElementById("resendText");


	const otpVerifiedIcon =
		document.getElementById("otpVerifiedIcon");


	let emailVerified = false;


	let resendTimer = null;


	const emailPattern =
		/^[^\s@]+@[^\s@]+\.[^\s@]+$/;



	/* ==================== PASSWORD SHOW / HIDE ==================== */


	passwordToggle.addEventListener(
		"click",
		function() {

			if (password.type === "password") {

				password.type = "text";

				passwordToggle.textContent =
					"Hide";

				passwordToggle.setAttribute(
					"aria-label",
					"Hide password"
				);

			} else {

				password.type = "password";

				passwordToggle.textContent =
					"Show";

				passwordToggle.setAttribute(
					"aria-label",
					"Show password"
				);

			}

		}
	);



	/* ==================== NAME VALIDATION ==================== */


	function validateName() {

		const value =
			name.value.trim();


		if (value === "") {

			nameError.textContent =
				"Full name is required.";

			return false;

		}


		if (value.length < 2) {

			nameError.textContent =
				"Please enter your full name.";

			return false;

		}


		nameError.textContent = "";

		return true;

	}



	/* ==================== EMAIL VALIDATION ==================== */


	function isValidEmail(value) {

		return emailPattern.test(value);

	}


	function validateEmail() {

		const value =
			email.value.trim();


		if (value === "") {

			emailError.textContent =
				"Email address is required.";

			verifyEmailButton.disabled = true;

			return false;

		}


		if (!isValidEmail(value)) {

			emailError.textContent =
				"Please enter a valid email address.";

			verifyEmailButton.disabled = true;

			return false;

		}


		emailError.textContent = "";

		verifyEmailButton.disabled = false;

		return true;

	}



	/* ==================== PHONE VALIDATION ==================== */


	function validatePhone() {

		const value =
			phone.value.trim();


		if (value === "") {

			phoneError.textContent =
				"Phone number is required.";

			return false;

		}


		if (!/^\d{10}$/.test(value)) {

			phoneError.textContent =
				"Phone number must be exactly 10 digits.";

			return false;

		}


		phoneError.textContent = "";

		return true;

	}



	/* ==================== PASSWORD VALIDATION ==================== */


	function validatePassword() {

		const value =
			password.value;


		if (value === "") {

			passwordError.textContent =
				"Password is required.";

			return false;

		}


		if (value.length < 8) {

			passwordError.textContent =
				"Password must contain at least 8 characters.";

			return false;

		}


		passwordError.textContent = "";

		return true;

	}



	/* ==================== PASSWORD STRENGTH ==================== */


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


		if (/\d/.test(value)) {

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

		} else if (strength <= 2) {

			strengthBar.style.width =
				"35%";

			strengthBar.style.backgroundColor =
				"#d92d20";

			strengthText.textContent =
				"Weak password";

		} else if (strength <= 4) {

			strengthBar.style.width =
				"65%";

			strengthBar.style.backgroundColor =
				"#f79009";

			strengthText.textContent =
				"Good password";

		} else {

			strengthBar.style.width =
				"100%";

			strengthBar.style.backgroundColor =
				"#12b76a";

			strengthText.textContent =
				"Strong password";

		}

	}



	/* ==================== NAME EVENTS ==================== */


	name.addEventListener(
		"blur",
		function() {

			validateName();

		}
	);


	name.addEventListener(
		"input",
		function() {

			if (nameError.textContent !== "") {

				validateName();

			}

		}
	);



	/* ==================== EMAIL EVENTS ==================== */


	email.addEventListener(
		"blur",
		function() {

			validateEmail();

		}
	);


	email.addEventListener(
		"input",
		function() {

			const value =
				email.value.trim();


			validateEmail();


			if (emailVerified) {

				emailVerified = false;


				email.readOnly = false;


				verifyEmailButton.disabled =
					!isValidEmail(value);


				verifyEmailButton.textContent =
					"Verify Email";


				verifyEmailButton.classList.remove(
					"email-verified-button"
				);


				otpTray.classList.remove(
					"active"
				);


				otp.value = "";


				otp.readOnly = false;


				verifyOtpButton.disabled =
					true;


				verifyOtpButton.textContent =
					"Verify";


				otpVerifiedIcon.style.display =
					"none";


				otpMessage.textContent = "";


				otpMessage.className =
					"otp-message";


				registerButton.disabled =
					true;


				buttonText.textContent =
					"Verify Email to Continue";


				resendOtpButton.disabled =
					true;


				resendText.textContent =
					"Resend available in 60 seconds";


				clearInterval(
					resendTimer
				);

			}

		}
	);



	/* ==================== PHONE EVENTS ==================== */


	phone.addEventListener(
		"blur",
		function() {

			validatePhone();

		}
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
					phone.value.slice(0, 10);

			}


			if (phoneError.textContent !== "") {

				validatePhone();

			}

		}
	);



	/* ==================== PASSWORD EVENTS ==================== */


	password.addEventListener(
		"blur",
		function() {

			validatePassword();

		}
	);


	password.addEventListener(
		"input",
		function() {

			updatePasswordStrength();


			if (passwordError.textContent !== "") {

				validatePassword();

			}

		}
	);



	/* ==================== INITIAL OTP STATE ==================== */


	verifyEmailButton.disabled =
		true;


	verifyOtpButton.disabled =
		true;


	resendOtpButton.disabled =
		true;


	registerButton.disabled =
		true;


	if (otpVerifiedIcon) {

		otpVerifiedIcon.style.display =
			"none";

	}



	/* ==================== VERIFY EMAIL ==================== */


	verifyEmailButton.addEventListener(
		"click",
		function() {


			if (!validateEmail()) {

				return;

			}


			const value =
				email.value.trim();


			verifyEmailButton.disabled =
				true;


			verifyEmailButton.textContent =
				"Sending...";


			fetch("emailVerification", {

				method: "POST",

				headers: {

					"Content-Type":
						"application/x-www-form-urlencoded"

				},

				body:
					"action=send&email=" +
					encodeURIComponent(value)

			})


				.then(function(response) {

					return response.json();

				})


				.then(function(data) {


					if (data.success) {


						verifyEmailButton.textContent =
							"Code Sent";


						otpTray.classList.add(
							"active"
						);


						otp.value = "";


						otp.readOnly = false;


						verifyOtpButton.disabled =
							true;


						verifyOtpButton.textContent =
							"Verify";


						otpMessage.textContent =
							data.message;


						otpMessage.className =
							"otp-message success";


						resendOtpButton.disabled =
							true;


						registerButton.disabled =
							true;


						startResendTimer();


					} else {


						verifyEmailButton.disabled =
							false;


						verifyEmailButton.textContent =
							"Verify Email";


						otpMessage.textContent =
							data.message;


						otpMessage.className =
							"otp-message error";

					}

				})


				.catch(function() {


					verifyEmailButton.disabled =
						false;


					verifyEmailButton.textContent =
						"Verify Email";


					otpMessage.textContent =
						"Unable to send verification code.";


					otpMessage.className =
						"otp-message error";

				});

		}
	);



	/* ==================== OTP INPUT ==================== */


	otp.addEventListener(
		"input",
		function() {


			otp.value =
				otp.value
					.replace(/\D/g, "")
					.slice(0, 4);


			if (otp.value.length === 4) {

				verifyOtpButton.disabled =
					false;

			} else {

				verifyOtpButton.disabled =
					true;

			}


			otpMessage.textContent = "";


			otpMessage.className =
				"otp-message";

		}
	);



	/* ==================== VERIFY OTP ==================== */


	verifyOtpButton.addEventListener(
		"click",
		function() {


			const enteredOtp =
				otp.value.trim();


			if (enteredOtp.length !== 4) {

				return;

			}


			verifyOtpButton.disabled =
				true;


			verifyOtpButton.textContent =
				"Verifying...";


			fetch("emailVerification", {

				method: "POST",

				headers: {

					"Content-Type":
						"application/x-www-form-urlencoded"

				},

				body:
					"action=verify&otp=" +
					encodeURIComponent(
						enteredOtp
					)

			})


				.then(function(response) {

					return response.json();

				})


				.then(function(data) {


					if (data.success) {


						emailVerified =
							true;


						otpMessage.textContent =
							data.message;


						otpMessage.className =
							"otp-message success";


						otpVerifiedIcon.style.display =
							"flex";


						email.readOnly =
							true;


						otp.readOnly =
							true;


						verifyOtpButton.disabled =
							true;


						verifyOtpButton.textContent =
							"Verified";


						verifyEmailButton.disabled =
							true;


						verifyEmailButton.textContent =
							"✓ Verified";


						verifyEmailButton.classList.add(
							"email-verified-button"
						);


						/* CLOSE OTP TRAY */

						otpTray.classList.remove(
							"active"
						);


						/* ACTIVATE REGISTER */

						registerButton.disabled =
							false;


						buttonText.textContent =
							"Create Account";


						clearInterval(
							resendTimer
						);


						resendOtpButton.disabled =
							true;


						resendText.textContent =
							"Email verified";

					} else {


						verifyOtpButton.disabled =
							false;


						verifyOtpButton.textContent =
							"Verify";


						otpMessage.textContent =
							data.message;


						otpMessage.className =
							"otp-message error";

					}

				})


				.catch(function() {


					verifyOtpButton.disabled =
						false;


					verifyOtpButton.textContent =
						"Verify";


					otpMessage.textContent =
						"Unable to verify the code.";


					otpMessage.className =
						"otp-message error";

				});

		}
	);



	/* ==================== RESEND OTP ==================== */


	resendOtpButton.addEventListener(
		"click",
		function() {


			if (emailVerified) {

				return;

			}


			resendOtpButton.disabled =
				true;


			resendText.textContent =
				"Sending new code...";


			otp.value = "";


			verifyOtpButton.disabled =
				true;


			verifyOtpButton.textContent =
				"Verify";


			otpMessage.textContent = "";


			otpMessage.className =
				"otp-message";


			fetch("emailVerification", {

				method: "POST",

				headers: {

					"Content-Type":
						"application/x-www-form-urlencoded"

				},

				body:
					"action=resend"

			})


				.then(function(response) {

					return response.json();

				})


				.then(function(data) {


					if (data.success) {


						emailVerified =
							false;


						registerButton.disabled =
							true;


						buttonText.textContent =
							"Verify Email to Continue";


						otp.value = "";


						verifyOtpButton.disabled =
							true;


						verifyOtpButton.textContent =
							"Verify";


						otpMessage.textContent =
							data.message;


						otpMessage.className =
							"otp-message success";


						startResendTimer();


					} else {


						otpMessage.textContent =
							data.message;


						otpMessage.className =
							"otp-message error";


						resendOtpButton.disabled =
							false;

					}

				})


				.catch(function() {


					otpMessage.textContent =
						"Unable to resend verification code.";


					otpMessage.className =
						"otp-message error";


					resendOtpButton.disabled =
						false;

				});

		}
	);



	/* ==================== RESEND TIMER ==================== */


	function startResendTimer() {


		let seconds =
			60;


		resendOtpButton.disabled =
			true;


		resendText.textContent =
			"Resend available in " +
			seconds +
			" seconds";


		clearInterval(
			resendTimer
		);


		resendTimer =
			setInterval(
				function() {


					seconds--;


					if (seconds <= 0) {


						clearInterval(
							resendTimer
						);


						resendOtpButton.disabled =
							false;


						resendText.textContent =
							"You can request a new code.";


						return;

					}


					resendText.textContent =
						"Resend available in " +
						seconds +
						" seconds";


				},
				1000
			);

	}



	/* ==================== FORM SUBMIT ==================== */


	form.addEventListener(
		"submit",
		function(event) {


			const validName =
				validateName();


			const validEmail =
				validateEmail();


			const validPhone =
				validatePhone();


			const validPassword =
				validatePassword();


			if (
				!validName ||
				!validEmail ||
				!validPhone ||
				!validPassword ||
				!emailVerified
			) {


				event.preventDefault();


				if (!validName) {

					name.focus();

				} else if (!validEmail) {

					email.focus();

				} else if (!validPhone) {

					phone.focus();

				} else if (!validPassword) {

					password.focus();

				} else if (!emailVerified) {

					email.focus();

				}


				return;

			}


			registerButton.disabled =
				true;


			buttonText.textContent =
				"Creating account...";

		}
	);


});