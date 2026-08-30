<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="com.movieticket.model.UserBean" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Profile</title>
</head>

<body>

    <h2>My Profile</h2>

    <%
        UserBean user = (UserBean) request.getAttribute("user");
    %>

    <% if (user != null) { %>

        <p>
            <strong>Name:</strong>
            <%= user.getName() %>
        </p>

        <p>
            <strong>Email:</strong>
            <%= user.getEmail() %>
        </p>

        <p>
            <strong>Phone:</strong>
            <%= user.getPhone() %>
        </p>

        <p>
            <strong>Role:</strong>
            <%= user.getRole() %>
        </p>

        <p>
            <strong>Status:</strong>
            <%= user.isStatus() ? "Active" : "Inactive" %>
        </p>

        <br>

        <a href="<%= request.getContextPath() %>/logout">
            Logout
        </a>

    <% } else { %>

        <p>User information not available.</p>

    <% } %>

</body>
</html>