<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/navbar.css">

<nav class="navbar">

	<div class="nav-logo">MovieTicket</div>

	<div class="nav-search">
		<input type="text" placeholder="Search movies..." name="search">
	</div>

	<div class="nav-links">
		<a href="#">Home</a> <a href="#">Movies</a> <a href="#">Malls</a>
		<a href="${pageContext.request.contextPath}/profile">
        My Profile
    </a>

    <a href="${pageContext.request.contextPath}/logout">
        Logout
    </a>
	</div>

</nav>