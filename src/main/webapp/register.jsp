<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register - Movie Ticket Booking</title>
</head>

<body>

    <h2>Create Account</h2>

    <% 
        String error = (String) request.getAttribute("error");
        if (error != null) {
    %>
        <p><%= error %></p>
    <% 
        }
    %>

    <form action="${pageContext.request.contextPath}/register" method="post">

        <label>Name:</label>
        <input type="text" name="name" required>
        <br><br>

        <label>Email:</label>
        <input type="email" name="email" required>
        <br><br>

        <label>Phone:</label>
        <input type="tel" name="phone" required>
        <br><br>

        <label>Password:</label>
        <input type="password" name="password" required>
        <br><br>

        <button type="submit">Register</button>

    </form>

    <p>
        Already have an account?
        <a href="${pageContext.request.contextPath}/login.jsp">
            Login
        </a>
    </p>

</body>
</html>