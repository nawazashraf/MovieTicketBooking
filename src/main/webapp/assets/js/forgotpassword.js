document.addEventListener("DOMContentLoaded", function() {

const form =
	document.getElementById("forgotPasswordForm");

const email =
	document.getElementById("email");

const emailError =
	document.getElementById("emailFieldError");

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

const continueButton =
	document.getElementById("continueButton");


let emailVerified = false;

let resendTimer = null;


const emailPattern =
	/^[^\s@]+@[^\s@]+\.[^\s@]+$/;


/* INITIAL STATE */

verifyEmailButton.disabled = true;

verifyOtpButton.disabled = true;

resendOtpButton.disabled = true;

continueButton.disabled = true;

otpVerifiedIcon.style.display = "none";


/* EMAIL VALIDATION */

function validateEmail() {

	const value =
		email.value.trim();


	if (value === "") {

		emailError.textContent =
			"Email address is required.";

		verifyEmailButton.disabled = true;

		return false;
	}


	if (!emailPattern.test(value)) {

		emailError.textContent =
			"Please enter a valid email address.";

		verifyEmailButton.disabled = true;

		return false;
	}


	emailError.textContent = "";

	verifyEmailButton.disabled = false;

	return true;
}


/* EMAIL INPUT */

email.addEventListener("input", function() {

	validateEmail();


	if (emailVerified) {

		emailVerified = false;

		email.readOnly = false;

		verifyEmailButton.disabled = false;

		verifyEmailButton.textContent =
			"Verify Email";

		verifyEmailButton.classList.remove(
			"email-verified-button"
		);

		otpTray.classList.remove("active");

		otp.value = "";

		otp.readOnly = false;

		verifyOtpButton.disabled = true;

		verifyOtpButton.textContent =
			"Verify";

		otpMessage.textContent = "";

		otpMessage.className =
			"otp-message";

		otpVerifiedIcon.style.display =
			"none";

		resendOtpButton.disabled = true;

		resendText.textContent =
			"Resend available in 60 seconds";

		continueButton.disabled = true;

		clearInterval(resendTimer);
	}
});


/* EMAIL BLUR */

email.addEventListener("blur", function() {

	validateEmail();

});


/* VERIFY EMAIL */

verifyEmailButton.addEventListener(
	"click",
	function() {

		if (!validateEmail()) {
			return;
		}


		const value =
			email.value.trim();


		verifyEmailButton.disabled = true;

		verifyEmailButton.textContent =
			"Sending...";


		fetch("forgotpassword", {

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

				emailError.textContent = "";

				verifyEmailButton.textContent =
					"Code Sent";

				otpTray.classList.add(
					"active"
				);

				otp.value = "";

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

				continueButton.disabled =
					true;

				startResendTimer();

			} else {

				verifyEmailButton.disabled =
					false;

				verifyEmailButton.textContent =
					"Verify Email";

				emailError.textContent =
					data.message;
			}

		})

		.catch(function() {

			verifyEmailButton.disabled =
				false;

			verifyEmailButton.textContent =
				"Verify Email";

			emailError.textContent =
				"Unable to send verification code.";

		});

	}
);


/* OTP INPUT */

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


/* VERIFY OTP */

verifyOtpButton.addEventListener(
	"click",
	function() {

		const enteredOtp =
			otp.value.trim();


		if (enteredOtp.length !== 4) {

			otpMessage.textContent =
				"Please enter the 4-digit verification code.";

			otpMessage.className =
				"otp-message error";

			return;
		}


		verifyOtpButton.disabled =
			true;

		verifyOtpButton.textContent =
			"Verifying...";


		fetch("forgotpassword", {

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

				emailVerified = true;


				otpMessage.textContent =
					data.message;

				otpMessage.className =
					"otp-message success";


				otpVerifiedIcon.style.display =
					"flex";


				email.readOnly = true;


				otp.readOnly = true;


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


				/* ACTIVATE CONTINUE */

				continueButton.disabled =
					false;


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


/* RESEND OTP */

resendOtpButton.addEventListener(
	"click",
	function() {

		resendOtpButton.disabled =
			true;

		resendText.textContent =
			"Sending new code...";


		fetch("forgotpassword", {

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

				otp.value = "";

				verifyOtpButton.disabled =
					true;

				verifyOtpButton.textContent =
					"Verify";

				otpMessage.textContent =
					data.message;

				otpMessage.className =
					"otp-message success";

				continueButton.disabled =
					true;

				emailVerified = false;

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


/* RESEND TIMER */

function startResendTimer() {

	let seconds = 60;

	resendOtpButton.disabled =
		true;

	resendText.textContent =
		"Resend available in " +
		seconds +
		" seconds";


	clearInterval(resendTimer);


	resendTimer =
		setInterval(function() {

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

		}, 1000);

}


/* CONTINUE */

form.addEventListener(
	"submit",
	function(event) {

		event.preventDefault();


		if (!emailVerified) {

			otpMessage.textContent =
				"Please verify your email first.";

			otpMessage.className =
				"otp-message error";

			continueButton.disabled =
				true;

			return;
		}


		window.location.href =
			"changepassword";

	}
);


});
