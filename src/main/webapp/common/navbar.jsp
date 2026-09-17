<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/navbar.css">
<nav class="navbar">
	<div class="nav-logo">
		<a href="${pageContext.request.contextPath}/home">MovieTicket</a>
	</div>
	<form class="nav-search"
		action="${pageContext.request.contextPath}/movies/search" method="get">
		<input type="text" placeholder="Search movies..." name="search"
			value="${requestScope.searchQuery}">
		<button type="submit">Search</button>
	</form>
	<div class="nav-links">
		<a href="${pageContext.request.contextPath}/home">Home</a> <a
			href="${pageContext.request.contextPath}/movies">Movies</a> <a
			href="${pageContext.request.contextPath}/shows">Shows</a> <a
			href="${pageContext.request.contextPath}/bookings">My Bookings</a> <a
			href="${pageContext.request.contextPath}/profile">My Profile</a>

		<%
		if ("ADMIN".equalsIgnoreCase(String.valueOf(session.getAttribute("userRole")))
				|| "MALL_ADMIN".equalsIgnoreCase(String.valueOf(session.getAttribute("userRole")))) {
		%><a
			href="${pageContext.request.contextPath}/admin">Admin</a>
		<%
		}
		%>
	</div>
</nav>