document.addEventListener("DOMContentLoaded", function() {


	const form =
		document.getElementById("registerForm");


	const name =
		document.getElementById("name");


	const email =
		document.getElementById("email");


	const phone =
		document.getElementById("phone");


	const securityQuestion =
		document.getElementById("securityQuestion");


	const securityAnswer =
		document.getElementById("securityAnswer");


	const password =
		document.getElementById("password");


	const nameError =
		document.getElementById("nameError");


	const emailError =
		document.getElementById("emailError");


	const phoneError =
		document.getElementById("phoneError");


	const securityQuestionError =
		document.getElementById("securityQuestionError");


	const securityAnswerError =
		document.getElementById("securityAnswerError");


	const passwordError =
		document.getElementById("passwordError");


	const passwordToggle =
		document.getElementById("passwordToggle");


	const strengthBar =
		document.getElementById("strengthBar");


	const strengthText =
		document.getElementById("strengthText");


	const emailPattern =
		/^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;



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


	function formatName(value) {

		value = value
			.trim()
			.replace(/\s+/g, " ");

		if (value === "") {

			return "";

		}

		const words = value
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

		let value =
			name.value.trim();

		if (value === "") {

			nameError.textContent =
				"Name is required.";

			return false;

		}


		if (!/^[A-Za-z ]+$/.test(value)) {

			nameError.textContent =
				"Name can contain only letters and spaces.";

			return false;

		}


		value =
			value.replace(/\s+/g, " ");


		if (value.length < 2 || value.length > 50) {

			nameError.textContent =
				"Name must be between 2 and 50 characters.";

			return false;

		}


		name.value =
			formatName(value);

		nameError.textContent = "";

		return true;

	}



	/* ==================== EMAIL VALIDATION ==================== */


	function isValidEmail(value) {

		return emailPattern.test(value);

	}


	function validateEmail() {

		let value =
			email.value.trim();

		if (value === "") {

			emailError.textContent =
				"Email is required.";

			return false;

		}


		value =
			value.toLowerCase();

		email.value =
			value;


		if (!isValidEmail(value)) {

			emailError.textContent =
				"Please enter a valid email address.";

			return false;

		}


		emailError.textContent = "";

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
				"Phone number must contain exactly 10 digits.";

			return false;

		}


		if (value.startsWith("0")) {

			phoneError.textContent =
				"Please enter a valid 10-digit mobile number.";

			return false;

		}


		phoneError.textContent = "";

		return true;

	}



	/* ==================== SECURITY QUESTION VALIDATION ==================== */


	function validateSecurityQuestion() {

		const value =
			securityQuestion.value;


		if (value === "") {

			securityQuestionError.textContent =
				"Please select a security question.";

			return false;

		}


		securityQuestionError.textContent = "";

		return true;

	}



	/* ==================== SECURITY ANSWER VALIDATION ==================== */


	function validateSecurityAnswer() {

		const value =
			securityAnswer.value.trim();


		if (value === "") {

			securityAnswerError.textContent =
				"Security answer is required.";

			return false;

		}


		if (value.length < 2) {

			securityAnswerError.textContent =
				"Security answer must contain at least 2 characters.";

			return false;

		}


		if (value.length > 255) {

			securityAnswerError.textContent =
				"Security answer cannot exceed 255 characters.";

			return false;

		}


		securityAnswer.value =
			value;

		securityAnswerError.textContent = "";

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


		if (value !== value.trim()) {

			passwordError.textContent =
				"Password must not contain leading or trailing spaces.";

			return false;

		}


		if (value.length < 8) {

			passwordError.textContent =
				"Password must contain at least 8 characters.";

			return false;

		}


		if (value.length > 128) {

			passwordError.textContent =
				"Password cannot exceed 128 characters.";

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

			let value =
				email.value.trim();


			email.value =
				value.toLowerCase();


			validateEmail();

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



	/* ==================== SECURITY QUESTION EVENTS ==================== */


	securityQuestion.addEventListener(
		"change",
		function() {

			validateSecurityQuestion();

		}
	);


	securityQuestion.addEventListener(
		"blur",
		function() {

			validateSecurityQuestion();

		}
	);



	/* ==================== SECURITY ANSWER EVENTS ==================== */


	securityAnswer.addEventListener(
		"blur",
		function() {

			validateSecurityAnswer();

		}
	);


	securityAnswer.addEventListener(
		"input",
		function() {

			if (securityAnswer.value.length > 255) {

				securityAnswer.value =
					securityAnswer.value.substring(0, 255);

			}


			if (securityAnswerError.textContent !== "") {

				validateSecurityAnswer();

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



	/* ==================== FORM SUBMIT ==================== */


	form.addEventListener(
		"submit",
		function(event) {

			const nameValid =
				validateName();


			const emailValid =
				validateEmail();


			const phoneValid =
				validatePhone();


			const securityQuestionValid =
				validateSecurityQuestion();


			const securityAnswerValid =
				validateSecurityAnswer();


			const passwordValid =
				validatePassword();


			if (
				!nameValid ||
				!emailValid ||
				!phoneValid ||
				!securityQuestionValid ||
				!securityAnswerValid ||
				!passwordValid
			) {

				event.preventDefault();

			}

		}
	);


});