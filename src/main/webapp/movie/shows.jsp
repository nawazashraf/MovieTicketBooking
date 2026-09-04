<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.movieticket.model.ShowBean" %>

<%
List<ShowBean> shows = (List<ShowBean>) request.getAttribute("shows");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Movie Shows</title>

<style>
    body {
        font-family: Arial, sans-serif;
        background: #f5f5f5;
        padding: 30px;
    }

    h1 {
        text-align: center;
    }

    .show-card {
        max-width: 700px;
        margin: 15px auto;
        padding: 20px;
        background: white;
        border-radius: 10px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
    }

    .active {
        color: green;
        font-weight: bold;
    }
</style>
</head>

<body>

<h1>Available Shows</h1>

<%
if (shows != null && !shows.isEmpty()) {
    for (ShowBean show : shows) {
%>

<div class="show-card">
    <h2><%= show.getMovieName() %></h2>

    <p><strong>Mall:</strong> <%= show.getMallName() %></p>
    <p><strong>Date:</strong> <%= show.getShowDate() %></p>
    <p>
        <strong>Time:</strong>
        <%= show.getStartTime() %> - <%= show.getEndTime() %>
    </p>
    <p class="active"><%= show.getStatus() %></p>
</div>

<%
    }
} else {
%>

<p style="text-align: center;">No shows are available.</p>

<%
}
%>

</body>
</html>