<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/navbar.css">

<nav class="navbar">


	<!-- Top navigation -->
	<div class="nav-top">

		<div class="nav-logo">
			<a href="${pageContext.request.contextPath}/home"> MovieTicket </a>
		</div>

		<!-- Mobile menu button -->
		<button class="menu-toggle" type="button"
			aria-label="Toggle navigation" aria-expanded="false"
			onclick="toggleMobileMenu(this)">

			<span></span> <span></span> <span></span>

		</button>

	</div>


	<!-- Search -->
	<form class="nav-search"
		action="${pageContext.request.contextPath}/movies/search" method="get">

		<input type="text" placeholder="Search movies..." name="search"
			value="${requestScope.searchQuery}">

		<button type="submit">Search</button>

	</form>


	<!-- Navigation links -->
	<div class="nav-links" id="navLinks">

		<a href="${pageContext.request.contextPath}/home"> Home </a> <a
			href="${pageContext.request.contextPath}/movies"> Movies </a> <a
			href="${pageContext.request.contextPath}/shows"> Shows </a> <a
			href="${pageContext.request.contextPath}/bookings"> My Bookings </a>

		<a href="${pageContext.request.contextPath}/profile"> My Profile </a>

		<%
		if ("ADMIN".equalsIgnoreCase(String.valueOf(session.getAttribute("userRole")))
				|| "MALL_ADMIN".equalsIgnoreCase(String.valueOf(session.getAttribute("userRole")))) {
		%>

		<a href="${pageContext.request.contextPath}/admin"> Admin </a>

		<%
		}
		%>

	</div>


</nav>

<script>
	function toggleMobileMenu(button) {

		const navLinks = document.getElementById("navLinks");

		const isOpen = navLinks.classList.toggle("mobile-open");

		button.classList.toggle("active", isOpen);

		button.setAttribute("aria-expanded", isOpen);
	}
</script>
