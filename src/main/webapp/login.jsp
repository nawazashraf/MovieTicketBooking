<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - Movie Ticket Booking</title>
</head>

<body>

    <h2>Login</h2>

    <%
        String error = (String) request.getAttribute("error");

        if (error != null) {
    %>
        <p><%= error %></p>
    <%
        }
    %>

    <form action="${pageContext.request.contextPath}/login" method="post">

        <label>Email:</label>
        <input type="email" name="email" required>

        <br><br>

        <label>Password:</label>
        <input type="password" name="password" required>

        <br><br>

        <button type="submit">Login</button>

    </form>

    <p>
        Don't have an account?
        <a href="${pageContext.request.contextPath}/register.jsp">
            Register
        </a>
    </p>

</body>
</html>