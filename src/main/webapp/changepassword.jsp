<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Change Password</title>
</head>

<body>

    <h2>Change Password</h2>

    <%
        String error = (String) request.getAttribute("error");

        if (error != null) {
    %>
        <p><%= error %></p>
    <%
        }
    %>

    <form action="${pageContext.request.contextPath}/changepassword"
          method="post">

        <label>Current Password:</label>
        <input type="password" name="currentPassword" required>

        <br><br>

        <label>New Password:</label>
        <input type="password" name="newPassword" required>

        <br><br>

        <label>Confirm New Password:</label>
        <input type="password" name="confirmPassword" required>

        <br><br>

        <button type="submit">Change Password</button>

    </form>

    <br>

    <a href="${pageContext.request.contextPath}/profile">
        Back to Profile
    </a>

</body>
</html>