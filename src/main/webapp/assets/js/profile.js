
let activationResendTimer = null;


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
   EDIT PROFILE DRAWER
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
   ESCAPE KEY
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

	const activation =
		document.getElementById("activationOverlay");

	if (activation &&
		activation.style.display === "flex") {

		closeActivationPopup();

	}

});


/* =========================================================
   NAME + PHONE INPUT
========================================================= */

document.addEventListener("DOMContentLoaded", function() {

	const name =
		document.getElementById("editName");

	const phone =
		document.getElementById("editPhone");


	/* ========================================
	   NAME INPUT
	======================================== */

	if (name) {

		name.addEventListener("input", function() {

			this.value =
				this.value.replace(/[^A-Za-z ]/g, "");

			this.value =
				this.value.replace(/\s{2,}/g, " ");

			if (this.value.length > 50) {

				this.value =
					this.value.substring(0, 50);

			}

		});


		name.addEventListener("blur", function() {

			let value =
				this.value.trim();

			if (value === "") {

				this.value = "";

				return;

			}

			this.value =
				value
					.toLowerCase()
					.split(" ")
					.map(function(word) {

						return word.charAt(0).toUpperCase() +
							word.substring(1);

					})
					.join(" ");

		});

	}


	/* ========================================
	   PHONE INPUT
	======================================== */

	if (phone) {

		phone.addEventListener("input", function() {

			this.value =
				this.value.replace(/\D/g, "");

			if (this.value.length > 10) {

				this.value =
					this.value.substring(0, 10);

			}

		});

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


/* =========================================================
   KEEP OLD JSP FUNCTION
========================================================= */

function toggleEditPassword(inputId, button) {

	togglePassword(inputId, button);

}


/* =========================================================
   PASSWORD + PROFILE VALIDATION
========================================================= */

document.addEventListener("DOMContentLoaded", function() {

	const name =
		document.getElementById("editName");

	const phone =
		document.getElementById("editPhone");

	const newPassword =
		document.getElementById("editPassword");

	const confirmPassword =
		document.getElementById("editConfirmPassword");

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


	if (!newPassword ||
		!confirmPassword ||
		!strengthBar ||
		!strengthText ||
		!matchMessage ||
		!form) {

		return;

	}


	/* ========================================
	   ERROR ELEMENTS
	======================================== */

	const nameError =
		document.getElementById("editNameError");

	const phoneError =
		document.getElementById("editPhoneError");

	const passwordError =
		document.getElementById("editPasswordError");

	const confirmPasswordError =
		document.getElementById(
			"editConfirmPasswordError"
		);


	/* ========================================
	   SHOW ERROR
	======================================== */

	function showError(element, message) {

		if (!element) {
			return;
		}

		element.textContent = message;

		element.style.display = "block";

		element.style.color = "#d92d20";

	}


	/* ========================================
	   CLEAR ERROR
	======================================== */

	function clearError(element) {

		if (!element) {
			return;
		}

		element.textContent = "";

		element.style.display = "none";

	}


	/* ========================================
	   NAME VALIDATION
	======================================== */

	function validateName() {

		if (!name) {
			return true;
		}

		const value =
			name.value.trim();

		clearError(nameError);


		if (value.length < 2 ||
			value.length > 50) {

			showError(
				nameError,
				"Name must contain between 2 and 50 characters."
			);

			return false;

		}


		if (!/^[A-Za-z]+(?: [A-Za-z]+)*$/.test(value)) {

			showError(
				nameError,
				"Only English letters and single spaces are allowed."
			);

			return false;

		}


		name.value =
			value
				.toLowerCase()
				.split(" ")
				.map(function(word) {

					return word.charAt(0).toUpperCase() +
						word.substring(1);

				})
				.join(" ");


		return true;

	}


	/* ========================================
	   PHONE VALIDATION
	======================================== */

	function validatePhone() {

		if (!phone) {
			return true;
		}

		const value =
			phone.value.trim();

		clearError(phoneError);


		if (!/^\d{10}$/.test(value)) {

			showError(
				phoneError,
				"Phone number must contain exactly 10 digits."
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


		return true;

	}


	/* ========================================
	   PASSWORD VALIDATION
	======================================== */

	function validatePassword() {

		const value =
			newPassword.value;

		clearError(passwordError);


		/*
		 * Empty password means
		 * keep existing password.
		 */

		if (value === "") {

			return true;

		}


		/*
		 * Leading/trailing spaces.
		 */

		if (value !== value.trim()) {

			showError(
				passwordError,
				"Password must not contain leading or trailing spaces."
			);

			return false;

		}


		/*
		 * Less than 8 characters.
		 *
		 * Do NOT show another error line.
		 * The strength section already shows:
		 * "Minimum 8 characters"
		 */

		if (value.length < 8) {

			return false;

		}


		/*
		 * Maximum 128 characters.
		 */

		if (value.length > 128) {

			showError(
				passwordError,
				"Password cannot exceed 128 characters."
			);

			return false;

		}


		return true;

	}


	/* ========================================
	   PASSWORD STRENGTH
	======================================== */

	function updatePasswordStrength() {

		const value =
			newPassword.value;


		if (value.length === 0) {

			strengthBar.style.width = "0%";

			strengthBar.style.background = "";

			strengthText.textContent =
				"Minimum 8 characters, maximum 128 characters";

			if (strengthContainer) {

				strengthContainer.style.display =
					"none";

			}

			return;

		}


		if (strengthContainer) {

			strengthContainer.style.display =
				"block";

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


	/* ========================================
	   PASSWORD INPUT
	======================================== */

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


	/* ========================================
	   CONFIRM PASSWORD VALIDATION
	======================================== */

	function validateConfirmPassword() {

		const passwordValue =
			newPassword.value;

		const confirmValue =
			confirmPassword.value;


		/*
		 * Clear BOTH possible error areas.
		 *
		 * The match message is now the
		 * ONLY place showing password mismatch.
		 */

		clearError(confirmPasswordError);

		matchMessage.textContent = "";

		matchMessage.style.color = "";


		/*
		 * Both empty means
		 * no password change.
		 */

		if (passwordValue === "" &&
			confirmValue === "") {

			return true;

		}


		/*
		 * Confirm field empty.
		 *
		 * Do not show a second error line.
		 */

		if (confirmValue === "") {

			return false;

		}


		/*
		 * Passwords do not match.
		 *
		 * ONLY matchMessage is used.
		 */

		if (passwordValue !== confirmValue) {

			matchMessage.textContent =
				"Passwords do not match";

			matchMessage.style.color =
				"#d92d20";

			return false;

		}


		/*
		 * Passwords match.
		 */

		matchMessage.textContent =
			"Passwords match";

		matchMessage.style.color =
			"#16803c";


		return true;

	}


	/* ========================================
	   CONFIRM PASSWORD INPUT
	======================================== */

	confirmPassword.addEventListener(
		"input",
		function() {

			validateConfirmPassword();

		}
	);


	/* ========================================
	   NAME INPUT VALIDATION
	======================================== */

	if (name) {

		name.addEventListener(
			"blur",
			function() {

				validateName();

			}
		);

	}


	/* ========================================
	   PHONE INPUT VALIDATION
	======================================== */

	if (phone) {

		phone.addEventListener(
			"blur",
			function() {

				validatePhone();

			}
		);

	}


	/* ========================================
	   FORM SUBMIT
	======================================== */

	form.addEventListener(
		"submit",
		function(event) {


			/* ========================================
			   NAME
			======================================== */

			const validName =
				validateName();


			if (!validName) {

				event.preventDefault();

				name.focus();

				return;

			}


			/* ========================================
			   PHONE
			======================================== */

			const validPhone =
				validatePhone();


			if (!validPhone) {

				event.preventDefault();

				phone.focus();

				return;

			}


			/* ========================================
			   PASSWORD
			======================================== */

			const validPassword =
				validatePassword();


			if (!validPassword) {

				event.preventDefault();

				newPassword.focus();

				return;

			}


			/* ========================================
			   CONFIRM PASSWORD
			======================================== */

			const validConfirmPassword =
				validateConfirmPassword();


			if (!validConfirmPassword) {

				event.preventDefault();

				confirmPassword.focus();

				return;

			}


			/*
			 * Everything is valid.
			 */

			name.value =
				name.value.trim();

			phone.value =
				phone.value.trim();

		}
	);

});


/* =========================================================
   ACTIVATION POPUP
========================================================= */

function openActivationPopup() {

	const overlay =
		document.getElementById(
			"activationOverlay"
		);

	if (overlay) {

		overlay.style.display = "flex";

		document.body.style.overflow =
			"hidden";

	}

}


function closeActivationPopup() {

	const overlay =
		document.getElementById(
			"activationOverlay"
		);

	if (overlay) {

		overlay.style.display = "none";

		document.body.style.overflow =
			"";

	}

}


/* =========================================================
   SEND ACTIVATION OTP
========================================================= */

function sendActivationOtp() {

	const contextPath =
		document.body.dataset.contextPath || "";

	const message =
		document.getElementById(
			"activationMessage"
		);

	const sendButton =
		document.querySelector(
			".activation-send-btn"
		);


	if (message) {

		message.textContent =
			"Sending verification code...";

	}


	if (sendButton) {

		sendButton.disabled = true;

	}


	fetch(
		contextPath + "/emailVerification",
		{
			method: "POST",

			headers: {
				"Content-Type":
					"application/x-www-form-urlencoded"
			},

			body:
				"action=activateSend"
		}
	)

		.then(function(response) {

			return response.json();

		})

		.then(function(data) {

			if (message) {

				message.textContent =
					data.message;

			}


			if (data.success) {

				const sendSection =
					document.getElementById(
						"activationSendSection"
					);

				const otpSection =
					document.getElementById(
						"activationOtpSection"
					);

				const otp =
					document.getElementById(
						"activationOtp"
					);


				if (sendSection) {

					sendSection.style.display =
						"none";

				}


				if (otpSection) {

					otpSection.style.display =
						"block";

				}


				if (otp) {

					otp.value = "";

					otp.focus();

				}


				startActivationResendTimer();


			} else {

				if (sendButton) {

					sendButton.disabled =
						false;

				}

			}

		})

		.catch(function(error) {

			console.error(error);


			if (message) {

				message.textContent =
					"Unable to send verification code.";

			}


			if (sendButton) {

				sendButton.disabled =
					false;

			}

		});

}


/* =========================================================
   VERIFY ACTIVATION OTP
========================================================= */

function verifyActivationOtp() {

	const otpElement =
		document.getElementById(
			"activationOtp"
		);

	const message =
		document.getElementById(
			"activationMessage"
		);


	if (!otpElement || !message) {

		return;

	}


	const otp =
		otpElement.value.trim();


	if (otp === "") {

		message.textContent =
			"Please enter the verification code.";

		return;

	}


	fetch(
		(document.body.dataset.contextPath || "") +
		"/emailVerification",
		{
			method: "POST",

			headers: {
				"Content-Type":
					"application/x-www-form-urlencoded"
			},

			body:
				"action=activateVerify&otp=" +
				encodeURIComponent(otp)
		}
	)

		.then(function(response) {

			return response.json();

		})

		.then(function(data) {

			message.textContent =
				data.message;


			if (data.success) {

				clearInterval(
					activationResendTimer
				);


				const resendButton =
					document.getElementById(
						"activationResendButton"
					);


				if (resendButton) {

					resendButton.disabled =
						true;

				}


				const resendText =
					document.getElementById(
						"activationResendText"
					);


				if (resendText) {

					resendText.textContent =
						"Account activated successfully.";

				}


				setTimeout(function() {

					location.reload();

				}, 1000);

			}

		})

		.catch(function(error) {

			console.error(error);

			message.textContent =
				"Unable to verify the code.";

		});

}


/* =========================================================
   RESEND ACTIVATION OTP
========================================================= */

function resendActivationOtp() {

	const message =
		document.getElementById(
			"activationMessage"
		);

	const resendButton =
		document.getElementById(
			"activationResendButton"
		);


	if (resendButton &&
		resendButton.disabled) {

		return;

	}


	if (message) {

		message.textContent =
			"Requesting new verification code...";

	}


	if (resendButton) {

		resendButton.disabled = true;

	}


	fetch(
		(document.body.dataset.contextPath || "") +
		"/emailVerification",
		{
			method: "POST",

			headers: {
				"Content-Type":
					"application/x-www-form-urlencoded"
			},

			body:
				"action=activateResend"
		}
	)

		.then(function(response) {

			return response.json();

		})

		.then(function(data) {

			if (message) {

				message.textContent =
					data.message;

			}


			if (data.success) {

				const otp =
					document.getElementById(
						"activationOtp"
					);


				if (otp) {

					otp.value = "";

					otp.focus();

				}


				startActivationResendTimer();


			} else {

				if (resendButton) {

					resendButton.disabled =
						false;

				}


				const resendText =
					document.getElementById(
						"activationResendText"
					);


				if (resendText) {

					resendText.textContent =
						"You can request a new code.";

				}

			}

		})

		.catch(function(error) {

			console.error(error);


			if (message) {

				message.textContent =
					"Unable to resend verification code.";

			}


			if (resendButton) {

				resendButton.disabled =
					false;

			}


			const resendText =
				document.getElementById(
					"activationResendText"
				);


			if (resendText) {

				resendText.textContent =
					"You can request a new code.";

			}

		});

}


/* =========================================================
   RESEND TIMER
========================================================= */

function startActivationResendTimer() {

	let seconds = 60;

	const resendButton =
		document.getElementById(
			"activationResendButton"
		);

	const resendText =
		document.getElementById(
			"activationResendText"
		);


	if (!resendButton ||
		!resendText) {

		return;

	}


	resendButton.disabled = true;


	resendText.textContent =
		"Resend available in " +
		seconds +
		" seconds";


	clearInterval(
		activationResendTimer
	);


	activationResendTimer =
		setInterval(function() {

			seconds--;


			if (seconds <= 0) {

				clearInterval(
					activationResendTimer
				);


				resendButton.disabled =
					false;


				resendText.textContent =
					"You can request a new code.";

				return;

			}


			resendText.textContent =
				"Resend available in " +
				seconds +
				" seconds";

		}, 1000);

}


/* =========================================================
   PROFILE UPDATE SUCCESS POPUP
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

		if (popup) {

			popup.remove();

		}

	}, 300);

}


/* =========================================================
   PROFILE UPDATE ERROR POPUP
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

		if (popup) {

			popup.remove();

		}

	}, 300);

}


/* =========================================================
   PROFILE UPDATE POPUPS
========================================================= */

document.addEventListener("DOMContentLoaded", function() {

	const successPopup =
		document.getElementById(
			"profileUpdatePopup"
		);


	if (successPopup) {

		setTimeout(function() {

			closeProfileUpdatePopup();

		}, 4000);

	}


	const errorPopup =
		document.getElementById(
			"profileUpdateErrorPopup"
		);


	if (errorPopup) {

		setTimeout(function() {

			closeProfileUpdateErrorPopup();

		}, 4000);

	}

});
 

 

