
let activationResendTimer = null;


/* INACTIVE POPUP */

function closeInactivePopup() {

	const overlay = document.getElementById("inactiveOverlay");

	if (!overlay) return;

	overlay.style.display = "none";

	document.body.style.overflow = "";

}


/* EDIT PROFILE DRAWER */

function openEditProfile() {

	const overlay = document.getElementById("editProfileOverlay");

	if (!overlay) return;

	overlay.classList.add("open");

	document.body.style.overflow = "hidden";

	setTimeout(function() {

		const name = document.getElementById("editName");

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

	const overlay = document.getElementById("editProfileOverlay");

	if (!overlay) return;

	overlay.classList.remove("open");

	document.body.style.overflow = "";

}


/* ESCAPE KEY */

document.addEventListener("keydown", function(event) {

	if (event.key !== "Escape") return;

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


/* NAME + PHONE INPUT */

document.addEventListener("DOMContentLoaded", function() {

	const name =
		document.getElementById("editName");

	const phone =
		document.getElementById("editPhone");


	/* NAME INPUT */

	if (name) {

		name.addEventListener("input", function() {

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

				const words =
					name.value
						.toLowerCase()
						.split(" ");

				for (let i = 0; i < words.length; i++) {

					if (words[i].length > 0) {

						words[i] =
							words[i].charAt(0).toUpperCase() +
							words[i].substring(1);

					}

				}

				name.value =
					words.join(" ");

			}

		});


		name.addEventListener("blur", function() {

			let value =
				name.value.trim();

			if (value === "") {

				name.value = "";

				return;

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

		});

	}


	/* PHONE INPUT */

	if (phone) {

		phone.addEventListener("input", function() {

			phone.value =
				phone.value.replace(
					/\D/g,
					""
				);

			if (phone.value.length > 10) {

				phone.value =
					phone.value.substring(0, 10);

			}

			const phoneError =
				document.getElementById("editPhoneError");

			const value =
				phone.value.trim();


			/* EMPTY */

			if (value === "") {

				if (phoneError) {

					phoneError.textContent = "";

					phoneError.style.display =
						"none";

				}

				return;

			}


			/* STARTS WITH 0 */

			if (value.startsWith("0")) {

				if (phoneError) {

					phoneError.textContent =
						"Phone number must not start with 0.";

					phoneError.style.display =
						"block";

					phoneError.style.color =
						"#d92d20";

				}

				return;

			}


			/* LESS THAN 10 DIGITS */

			if (value.length < 10) {

				if (phoneError) {

					phoneError.textContent =
						"Phone number must be exactly 10 digits.";

					phoneError.style.display =
						"block";

					phoneError.style.color =
						"#d92d20";

				}

				return;

			}


			/* EXACTLY 10 DIGITS */

			if (value.length === 10) {

				if (phoneError) {

					phoneError.textContent = "";

					phoneError.style.display =
						"none";

				}

			}

		});

	}

});


/* PASSWORD TOGGLE */

function togglePassword(inputId, button) {

	const input =
		document.getElementById(inputId);

	if (!input) return;

	if (input.type === "password") {

		input.type = "text";

		button.textContent = "Hide";

		button.setAttribute(
			"aria-label",
			"Hide password"
		);

	} else {

		input.type = "password";

		button.textContent = "Show";

		button.setAttribute(
			"aria-label",
			"Show password"
		);

	}

}


function toggleEditPassword(inputId, button) {

	togglePassword(inputId, button);

}


/* PASSWORD + PROFILE VALIDATION */

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


	const nameError =
		document.getElementById(
			"editNameError"
		);

	const phoneError =
		document.getElementById(
			"editPhoneError"
		);

	const passwordError =
		document.getElementById(
			"editPasswordError"
		);

	const confirmPasswordError =
		document.getElementById(
			"editConfirmPasswordError"
		);


	/* SHOW ERROR */

	function showError(element, message) {

		if (!element) return;

		element.textContent =
			message;

		element.style.display =
			"block";

		element.style.color =
			"#d92d20";

	}


	/* CLEAR ERROR */

	function clearError(element) {

		if (!element) return;

		element.textContent = "";

		element.style.display =
			"none";

	}


	/* NAME VALIDATION */

	function formatName(value) {

		value =
			value
				.trim()
				.replace(/\s+/g, " ");

		if (value === "") {

			return "";

		}

		const words =
			value
				.toLowerCase()
				.split(" ");

		const formattedWords = [];


		for (let word of words) {

			if (word.length > 0) {

				word =
					word.charAt(0).toUpperCase() +
					word.substring(1);

				formattedWords.push(word);

			}

		}


		return formattedWords.join(" ");

	}


	function validateName() {

		if (!name) return true;

		let value =
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


		value =
			value.replace(
				/\s+/g,
				" "
			);


		if (value.length < 2) {

			showError(
				nameError,
				"Name must contain at least 2 characters."
			);

			return false;

		}


		if (value.length > 50) {

			showError(
				nameError,
				"Name cannot exceed 50 characters."
			);

			return false;

		}


		name.value =
			formatName(value);

		return true;

	}


	/* PHONE VALIDATION */

	function validatePhone() {

		if (!phone) return true;

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


	/* PASSWORD VALIDATION */

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


	/* PASSWORD STRENGTH */

	function updatePasswordStrength() {

		const value =
			newPassword.value;

		let strength = 0;


		/*
		 * EXACT SAME LOGIC AS register.js
		 */

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

			if (strengthContainer) {

				strengthContainer.style.display =
					"none";

			}

		} else if (strength <= 2) {

			if (strengthContainer) {

				strengthContainer.style.display =
					"block";

			}

			strengthBar.style.width =
				"35%";

			strengthBar.style.backgroundColor =
				"#d92d20";

			strengthText.textContent =
				"Weak password";

		} else if (strength <= 4) {

			if (strengthContainer) {

				strengthContainer.style.display =
					"block";

			}

			strengthBar.style.width =
				"65%";

			strengthBar.style.backgroundColor =
				"#f79009";

			strengthText.textContent =
				"Good password";

		} else {

			if (strengthContainer) {

				strengthContainer.style.display =
					"block";

			}

			strengthBar.style.width =
				"100%";

			strengthBar.style.backgroundColor =
				"#12b76a";

			strengthText.textContent =
				"Strong password";

		}

	}


	/* PASSWORD EVENTS */

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


	/* CONFIRM PASSWORD */

	function validateConfirmPassword() {

		const passwordValue =
			newPassword.value;

		const confirmValue =
			confirmPassword.value;


		clearError(
			confirmPasswordError
		);


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


	confirmPassword.addEventListener(
		"input",
		function() {

			validateConfirmPassword();

		}
	);


	/* NAME EVENTS */

	if (name) {

		name.addEventListener(
			"blur",
			function() {

				validateName();

			}
		);

	}


	/* PHONE EVENTS */

	if (phone) {

		phone.addEventListener(
			"blur",
			function() {

				validatePhone();

			}
		);

	}


	/* FORM SUBMIT */

	form.addEventListener(
		"submit",
		function(event) {

			const validName =
				validateName();


			if (!validName) {

				event.preventDefault();

				name.focus();

				return;

			}


			const validPhone =
				validatePhone();


			if (!validPhone) {

				event.preventDefault();

				phone.focus();

				return;

			}


			const validPassword =
				validatePassword();


			if (!validPassword) {

				event.preventDefault();

				newPassword.focus();

				return;

			}


			const validConfirmPassword =
				validateConfirmPassword();


			if (!validConfirmPassword) {

				event.preventDefault();

				confirmPassword.focus();

				return;

			}


			name.value =
				name.value.trim();

			phone.value =
				phone.value.trim();

		}

	);

});


/* ACTIVATION POPUP */

function openActivationPopup() {

	const overlay =
		document.getElementById(
			"activationOverlay"
		);

	if (overlay) {

		overlay.style.display =
			"flex";

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

		overlay.style.display =
			"none";

		document.body.style.overflow =
			"";

	}

}


/* SEND ACTIVATION OTP */

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

		sendButton.disabled =
			true;

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


/* VERIFY ACTIVATION OTP */

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


/* RESEND ACTIVATION OTP */

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

		resendButton.disabled =
			true;

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


/* RESEND TIMER */

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


	resendButton.disabled =
		true;


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


/* PROFILE UPDATE SUCCESS POPUP */

function closeProfileUpdatePopup() {

	const popup =
		document.getElementById(
			"profileUpdatePopup"
		);


	if (!popup) return;


	popup.classList.add("hide");


	setTimeout(function() {

		if (popup) {

			popup.remove();

		}

	}, 300);

}


/* PROFILE UPDATE ERROR POPUP */

function closeProfileUpdateErrorPopup() {

	const popup =
		document.getElementById(
			"profileUpdateErrorPopup"
		);


	if (!popup) return;


	popup.classList.add("hide");


	setTimeout(function() {

		if (popup) {

			popup.remove();

		}

	}, 300);

}


/* PROFILE UPDATE POPUPS */

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



