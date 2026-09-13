<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.movieticket.model.BookingBean" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Booking History</title>
<style>
body{font-family:Arial,sans-serif;margin:0;background:#f4f4f4;color:#222}.wrap{max-width:1000px;margin:30px auto;padding:0 20px}.card{background:#fff;border-radius:12px;padding:20px;margin:16px 0;box-shadow:0 2px 10px rgba(0,0,0,.08)}.top{display:flex;justify-content:space-between;gap:20px}.status{font-weight:bold}.confirmed{color:green}.pending{color:#b77900}.cancelled{color:#c00}.meta{color:#555;line-height:1.7}.btn{display:inline-block;padding:9px 14px;background:#111;color:#fff;text-decoration:none;border-radius:7px;margin-top:8px}.empty{text-align:center;padding:60px;background:#fff;border-radius:12px}
</style>
</head>
<body>
<%@ include file="/common/navbar.jsp" %>
<div class="wrap">
<h1>My Booking History</h1>
<% List<BookingBean> bookings=(List<BookingBean>)request.getAttribute("bookings"); %>
<% if(bookings==null || bookings.isEmpty()) { %>
<div class="empty">No bookings found.</div>
<% } else { for(BookingBean b: bookings) { String cls=b.getBookingStatus()==null?"":b.getBookingStatus().toLowerCase(); %>
<div class="card">
  <div class="top"><div><h2><%= b.getMovieTitle() %></h2><div class="meta"><strong>Theatre:</strong> <%= b.getMallName() %><br><strong>Date:</strong> <%= b.getShowDate() %> &nbsp; <strong>Time:</strong> <%= b.getStartTime() %> - <%= b.getEndTime() %><br><strong>Seats:</strong> <%= b.getSeatLabels()==null?"-":b.getSeatLabels() %></div></div><div class="status <%=cls%>"><%=b.getBookingStatus()%></div></div>
  <p><strong>Booking Reference:</strong> <%=b.getBookingReference()%> &nbsp; | &nbsp; <strong>Total:</strong> ₹<%=b.getTotalAmount()%></p>
  <% if("CONFIRMED".equalsIgnoreCase(b.getBookingStatus())) { %><a class="btn" href="<%=request.getContextPath()%>/ticket?bookingId=<%=b.getId()%>">View Ticket</a><% } %>
</div>
<% }} %>
</div>
</body>
</html>