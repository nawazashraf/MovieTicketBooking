let activationResendTimer = null;


/* =========================================================
   INACTIVE POPUP
========================================================= */

function closeInactivePopup() {

	const overlay =
		document.getElementById("inactiveOverlay");

	if (overlay) {

		overlay.classList.add(
			"inactive-overlay-hidden"
		);

	}

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
   PHONE INPUT
========================================================= */

document.addEventListener("DOMContentLoaded", function() {

	const phone =
		document.getElementById("editPhone");

	if (phone) {

		phone.addEventListener(
			"input",
			function() {

				this.value =
					this.value.replace(/\D/g, "");

				if (this.value.length > 10) {

					this.value =
						this.value.substring(0, 10);

				}

			}
		);

	}

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

			}
			else {

				if (sendButton) {

					sendButton.disabled = false;

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

				sendButton.disabled = false;

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

			}
			else {

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

				resendButton.disabled = false;

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