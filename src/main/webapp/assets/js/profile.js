let activationResendTimer = null;


/* =========================================================
   INACTIVE ACCOUNT POPUP
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
   ACTIVATION POPUP
   ========================================================= */

function openActivationPopup() {

	const overlay =
		document.getElementById("activationOverlay");

	if (overlay) {

		overlay.style.display = "flex";

	}

}


function closeActivationPopup() {

	const overlay =
		document.getElementById("activationOverlay");

	if (overlay) {

		overlay.style.display = "none";

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

	message.textContent =
		"Sending verification code...";


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


		.then(response =>
			response.json()
		)


		.then(data => {

			message.textContent =
				data.message;


			if (data.success) {

				document.getElementById(
					"activationSendSection"
				).style.display = "none";


				document.getElementById(
					"activationOtpSection"
				).style.display = "block";


				document.getElementById(
					"activationOtp"
				).value = "";


				document.getElementById(
					"activationOtp"
				).focus();


				startActivationResendTimer();

			} else {

				if (sendButton) {

					sendButton.disabled = false;

				}

			}

		})


		.catch(error => {

			console.error(error);

			message.textContent =
				"Unable to send verification code.";


			if (sendButton) {

				sendButton.disabled = false;

			}

		});

}


/* =========================================================
   VERIFY ACTIVATION OTP
   ========================================================= */

function verifyActivationOtp() {

	const otp =
		document.getElementById(
			"activationOtp"
		).value.trim();


	const message =
		document.getElementById(
			"activationMessage"
		);


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


		.then(response =>
			response.json()
		)


		.then(data => {

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

					resendButton.disabled = true;

				}


				const resendText =
					document.getElementById(
						"activationResendText"
					);


				if (resendText) {

					resendText.textContent =
						"Email verified successfully.";

				}


				setTimeout(function() {

					location.reload();

				}, 1000);

			}

		})


		.catch(error => {

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


	message.textContent =
		"Requesting new verification code...";


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


		.then(response =>
			response.json()
		)


		.then(data => {

			message.textContent =
				data.message;


			if (data.success) {

				document.getElementById(
					"activationOtp"
				).value = "";


				document.getElementById(
					"activationOtp"
				).focus();


				startActivationResendTimer();

			} else {

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

			}

		})


		.catch(error => {

			console.error(error);

			message.textContent =
				"Unable to resend verification code.";


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
   60 SECOND ACTIVATION RESEND TIMER
   SAME LOGIC AS FORGOT PASSWORD
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