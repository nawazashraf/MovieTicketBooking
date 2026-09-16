<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Forgot Password | MovieBook</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/forgotpassword.css">

</head>

<body>

    <main class="forgot-page">

        <div class="forgot-card">


            <!-- BRAND -->

            <div class="brand">

                <div class="brand-logo">
                    M
                </div>

                <span class="brand-name">
                    MovieBook
                </span>

            </div>


            <!-- ICON -->

            <div class="forgot-icon">
                🔐
            </div>


            <!-- HEADER -->

            <div class="forgot-header">

                <h1>
                    Forgot Password?
                </h1>

                <p>
                    Enter your registered email address
                    to continue.
                </p>

            </div>


            <!-- ERROR -->

            <%
                String error =
                    (String) request.getAttribute("error");

                if (error != null) {
            %>

            <div class="error-message">

                <span class="error-icon">
                    !
                </span>

                <span>
                    <%= error %>
                </span>

            </div>

            <%
                }
            %>


            <!-- EMAIL FORM -->

            <form
                action="${pageContext.request.contextPath}/forgotpassword"
                method="post">


                <div class="form-group">

                    <label for="email">
                        Email address
                    </label>

                    <div class="input-wrapper">

                        <span class="input-icon">
                            @
                        </span>

                        <input
                            type="email"
                            id="email"
                            name="email"
                            placeholder="Enter your registered email"
                            autocomplete="email"
                            required>

                    </div>

                </div>


                <button
                    type="submit"
                    class="continue-button">

                    Continue

                </button>

            </form>


            <!-- BACK TO LOGIN -->

            <div class="back-login">

                <a
                    href="${pageContext.request.contextPath}/login.jsp">

                    ← Back to Login

                </a>

            </div>


            <!-- SECURITY -->

            <div class="security">

                🔒 Your account information is securely handled.

            </div>

        </div>

    </main>

</body>

</html>