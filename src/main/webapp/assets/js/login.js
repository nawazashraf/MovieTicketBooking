document.addEventListener("DOMContentLoaded", function() {

	    
const form = document.getElementById("loginForm");

const email = document.getElementById("email");

const password = document.getElementById("password");

const emailError = document.getElementById("emailError");

const passwordError = document.getElementById("passwordError");

const passwordToggle =
    document.getElementById("passwordToggle");

const loginButton =
    document.getElementById("loginButton");

const buttonText =
    document.getElementById("buttonText");


/* ==============================
   PASSWORD TOGGLE
   ============================== */

passwordToggle.addEventListener("click", function() {

    const isHidden =
        password.type === "password";

    password.type =
        isHidden ? "text" : "password";

    passwordToggle.classList.toggle(
        "visible",
        isHidden
    );

    passwordToggle.setAttribute(
        "aria-label",
        isHidden
            ? "Hide password"
            : "Show password"
    );

});


/* ==============================
   EMAIL VALIDATION
   ============================== */

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


/* ==============================
   PASSWORD VALIDATION
   ============================== */

function validatePassword() {

    const value =
        password.value;


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


/* ==============================
   SHOW ERROR
   ============================== */

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


/* ==============================
   CLEAR ERROR
   ============================== */

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


/* ==============================
   EMAIL BLUR
   ============================== */

email.addEventListener(
    "blur",
    function() {

        validateEmail();

    }
);


/* ==============================
   EMAIL INPUT
   ============================== */

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


/* ==============================
   PASSWORD BLUR
   ============================== */

password.addEventListener(
    "blur",
    function() {

        validatePassword();

    }
);


/* ==============================
   PASSWORD INPUT
   ============================== */

password.addEventListener(
    "input",
    function() {

        if (
            password.classList.contains(
                "input-error"
            )
        ) {

            validatePassword();

        }

    }
);


/* ==============================
   FORM SUBMIT
   ============================== */

form.addEventListener(
    "submit",
    function(event) {

        const emailValid =
            validateEmail();

        const passwordValid =
            validatePassword();


        if (
            !emailValid ||
            !passwordValid
        ) {

            event.preventDefault();


            if (!emailValid) {

                email.focus();

            } else {

                password.focus();

            }

            return;
        }


        loginButton.disabled =
            true;

        buttonText.innerHTML =
            '<span class="login-spinner"></span> Signing in...';

    }
);


/* ==============================
   PASSWORD ENTER
   ============================== */

password.addEventListener(
    "keydown",
    function(event) {

        if (event.key === "Enter") {

            form.requestSubmit();

        }

    }
);


/* ==============================
   REMOVE SPACES FROM EMAIL
   ============================== */

email.addEventListener(
    "input",
    function() {

        const cursorPosition =
            email.selectionStart;

        const cleanedValue =
            email.value.replace(/\s/g, "");


        if (
            email.value !== cleanedValue
        ) {

            email.value =
                cleanedValue;

            email.setSelectionRange(
                Math.max(
                    0,
                    cursorPosition - 1
                ),
                Math.max(
                    0,
                    cursorPosition - 1
                )
            );

        }

    }
);


});
