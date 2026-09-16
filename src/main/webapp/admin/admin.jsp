<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List,java.util.Map"%>
<%@ page
	import="com.movieticket.model.MovieBean,com.movieticket.model.TheatreBean,com.movieticket.model.ShowBean,com.movieticket.model.SeatBean"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Management</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/admin.css">
</head>
<body>
	<div class="admin-wrap">
		<h1>Movie Ticket Booking — Admin Management</h1>
		<div class="admin-nav">
			<a href="#movies">Movies</a><a href="#theatres">Theatres</a><a
				href="#shows">Shows</a><a href="#seats">Seats</a><a
				href="${pageContext.request.contextPath}/home">Back to Site</a>
		</div>
		<%
		String message = (String) request.getAttribute("message");
		if (message != null) {
		%><div
			class="message"><%=message%></div>
		<%
		}
		%>
		<%
		List<MovieBean> movies = (List<MovieBean>) request.getAttribute("movies");
		List<TheatreBean> theatres = (List<TheatreBean>) request.getAttribute("theatres");
		List<ShowBean> shows = (List<ShowBean>) request.getAttribute("shows");
		List<SeatBean> seats = (List<SeatBean>) request.getAttribute("seats");
		List<SeatBean> seatTypes = (List<SeatBean>) request.getAttribute("seatTypes");
		Map<String, String> genres = (Map<String, String>) request.getAttribute("genres");
		MovieBean editMovie = (MovieBean) request.getAttribute("editMovieBean");
		TheatreBean editTheatre = (TheatreBean) request.getAttribute("editTheatreBean");
		ShowBean editShow = (ShowBean) request.getAttribute("editShowBean");
		SeatBean editSeat = (SeatBean) request.getAttribute("editSeatBean");
		%>
		<div class="stats">
			<div class="stat">
				Movies<strong><%=movies == null ? 0 : movies.size()%></strong>
			</div>
			<div class="stat">
				Theatres<strong><%=theatres == null ? 0 : theatres.size()%></strong>
			</div>
			<div class="stat">
				Shows<strong><%=shows == null ? 0 : shows.size()%></strong>
			</div>
			<div class="stat">
				Seats<strong><%=seats == null ? 0 : seats.size()%></strong>
			</div>
		</div>

		<section id="movies" class="panel">
			<h2>Movie Management</h2>
			<form method="post" action="${pageContext.request.contextPath}/admin">
				<input type="hidden" name="action"
					value="<%=editMovie == null ? "addMovie" : "updateMovie"%>">
				<%
				if (editMovie != null) {
				%><input type="hidden" name="id"
					value="<%=editMovie.getId()%>">
				<%
				}
				%>
				<div class="form-grid">
					<label>Title<input required name="title"
						value="<%=editMovie == null ? "" : editMovie.getTitle()%>"></label><label>Duration
						(minutes)<input required type="number" min="1"
						name="durationMinutes"
						value="<%=editMovie == null ? "" : editMovie.getDurationMinutes()%>">
					</label><label>Language<input name="language"
						value="<%=editMovie == null ? "" : editMovie.getLanguage()%>"></label><label>Release
						Date<input type="date" name="releaseDate"
						value="<%=editMovie == null || editMovie.getReleaseDate() == null ? "" : editMovie.getReleaseDate()%>">
					</label><label>Certificate<input name="certificate"
						value="<%=editMovie == null ? "" : editMovie.getCertificate()%>"></label><label>Status<select
						name="status">
							<%
							String ms = editMovie == null ? "NOW_SHOWING" : editMovie.getStatus();
							%><option
								<%="NOW_SHOWING".equals(ms) ? "selected" : ""%>>NOW_SHOWING</option>
							<option <%="COMING_SOON".equals(ms) ? "selected" : ""%>>COMING_SOON</option>
							<option <%="ENDED".equals(ms) ? "selected" : ""%>>ENDED</option>
					</select></label><label class="full">Poster URL<input name="posterUrl"
						value="<%=editMovie == null ? "" : editMovie.getPosterUrl()%>"></label><label
						class="full">Trailer URL<input name="trailerUrl"
						value="<%=editMovie == null ? "" : editMovie.getTrailerUrl()%>"></label><label
						class="full">Description<textarea name="description"><%=editMovie == null ? "" : editMovie.getDescription() == null ? "" : editMovie.getDescription()%></textarea></label>
				</div>
				<label>Genres</label>
				<div class="checkboxes">
					<%
					if (genres != null)
						for (Map.Entry<String, String> g : genres.entrySet()) {
							boolean checked = editMovie != null && editMovie.getGenreIds().contains(g.getKey());
					%><label><input
						type="checkbox" style="width: auto" name="genreIds"
						value="<%=g.getKey()%>" <%=checked ? "checked" : ""%>> <%=g.getValue()%></label>
					<%
					}
					%>
				</div>
				<br>
				<button class="btn" type="submit"><%=editMovie == null ? "Add Movie" : "Update Movie"%></button>
				<%
				if (editMovie != null) {
				%><a class="btn secondary"
					href="${pageContext.request.contextPath}/admin#movies">Cancel</a>
				<%
				}
				%>
			</form>
			<div class="table-wrap">
				<table>
					<tr>
						<th>Title</th>
						<th>Language</th>
						<th>Release</th>
						<th>Status</th>
						<th>Actions</th>
					</tr>
					<%
					if (movies != null)
						for (MovieBean m : movies) {
					%><tr>
						<td><%=m.getTitle()%></td>
						<td><%=m.getLanguage()%></td>
						<td><%=m.getReleaseDate()%></td>
						<td><%=m.getStatus()%></td>
						<td class="actions"><a class="btn"
							href="${pageContext.request.contextPath}/admin?editMovie=<%=m.getId()%>#movies">Edit</a>
						<form method="post"
								action="${pageContext.request.contextPath}/admin"
								onsubmit="return confirm('Delete this movie?');">
								<input type="hidden" name="action" value="deleteMovie"><input
									type="hidden" name="id" value="<%=m.getId()%>">
								<button class="btn danger">Delete</button>
							</form></td>
					</tr>
					<%
					}
					%>
				</table>
			</div>
		</section>

		<section id="theatres" class="panel">
			<h2>Theatre Management (stored in malls table)</h2>
			<form method="post" action="${pageContext.request.contextPath}/admin">
				<input type="hidden" name="action"
					value="<%=editTheatre == null ? "addTheatre" : "updateTheatre"%>">
				<%
				if (editTheatre != null) {
				%><input type="hidden" name="id"
					value="<%=editTheatre.getId()%>">
				<%
				}
				%><div class="form-grid">
					<label>Name<input required name="name"
						value="<%=editTheatre == null ? "" : editTheatre.getName()%>"></label><label>City<input
						required name="city"
						value="<%=editTheatre == null ? "" : editTheatre.getCity()%>"></label><label>State<input
						name="state"
						value="<%=editTheatre == null ? "" : editTheatre.getState()%>"></label><label>Pincode<input
						type="number" name="pincode"
						value="<%=editTheatre == null ? "" : editTheatre.getPincode()%>"></label><label>Status<select
						name="status"><option value="on"
								<%=editTheatre == null || editTheatre.isStatus() ? "selected" : ""%>>Active</option>
							<option value="off"
								<%=editTheatre != null && !editTheatre.isStatus() ? "selected" : ""%>>Inactive</option></select></label><label
						class="full">Address<input name="address"
						value="<%=editTheatre == null ? "" : editTheatre.getAddress()%>"></label>
				</div>
				<br>
				<button class="btn"><%=editTheatre == null ? "Add Theatre" : "Update Theatre"%></button>
				<%
				if (editTheatre != null) {
				%><a class="btn secondary"
					href="${pageContext.request.contextPath}/admin#theatres">Cancel</a>
				<%
				}
				%>
			</form>
			<div class="table-wrap">
				<table>
					<tr>
						<th>Name</th>
						<th>Address</th>
						<th>City</th>
						<th>Status</th>
						<th>Actions</th>
					</tr>
					<%
					if (theatres != null)
						for (TheatreBean t : theatres) {
					%><tr>
						<td><%=t.getName()%></td>
						<td><%=t.getAddress()%></td>
						<td><%=t.getCity()%></td>
						<td><%=t.isStatus() ? "Active" : "Inactive"%></td>
						<td class="actions"><a class="btn"
							href="${pageContext.request.contextPath}/admin?editTheatre=<%=t.getId()%>#theatres">Edit</a>
						<form method="post"
								action="${pageContext.request.contextPath}/admin"
								onsubmit="return confirm('Delete this theatre?');">
								<input type="hidden" name="action" value="deleteTheatre"><input
									type="hidden" name="id" value="<%=t.getId()%>">
								<button class="btn danger">Delete</button>
							</form></td>
					</tr>
					<%
					}
					%>
				</table>
			</div>
		</section>

		<section id="shows" class="panel">
			<h2>Show Management</h2>
			<form method="post" action="${pageContext.request.contextPath}/admin">
				<input type="hidden" name="action"
					value="<%=editShow == null ? "addShow" : "updateShow"%>">
				<%
				if (editShow != null) {
				%><input type="hidden" name="id"
					value="<%=editShow.getShowId()%>">
				<%
				}
				%><div class="form-grid">
					<label>Movie<select required name="movieId"><option
								value="">Select movie</option>
							<%
							if (movies != null)
								for (MovieBean m : movies) {
							%><option
								value="<%=m.getId()%>"
								<%=editShow != null && m.getId().equals(editShow.getMovieId()) ? "selected" : ""%>><%=m.getTitle()%></option>
							<%
							}
							%></select></label><label>Theatre<select required name="mallId"><option
								value="">Select theatre</option>
							<%
							if (theatres != null)
								for (TheatreBean t : theatres) {
							%><option
								value="<%=t.getId()%>"
								<%=editShow != null && t.getId().equals(editShow.getMallId()) ? "selected" : ""%>><%=t.getName()%></option>
							<%
							}
							%></select></label><label>Date<input required type="date" name="showDate"
						value="<%=editShow == null ? "" : editShow.getShowDate()%>"></label><label>Start
						Time<input required type="time" name="startTime"
						value="<%=editShow == null ? "" : editShow.getStartTime()%>">
					</label><label>End Time<input required type="time" name="endTime"
						value="<%=editShow == null ? "" : editShow.getEndTime()%>"></label><label>Seat
						Price (₹)<input type="number" step="0.01" min="0" name="seatPrice"
						placeholder="e.g. 150">
					</label><label>Status<select name="status">
							<%
							String ss = editShow == null ? "ACTIVE" : editShow.getStatus();
							%><option
								<%="ACTIVE".equals(ss) ? "selected" : ""%>>ACTIVE</option>
							<option <%="INACTIVE".equals(ss) ? "selected" : ""%>>INACTIVE</option>
							<option <%="CANCELLED".equals(ss) ? "selected" : ""%>>CANCELLED</option>
					</select></label>
				</div>
				<br>
				<button class="btn"><%=editShow == null ? "Add Show" : "Update Show"%></button>
				<%
				if (editShow != null) {
				%><a class="btn secondary"
					href="${pageContext.request.contextPath}/admin#shows">Cancel</a>
				<%
				}
				%>
			</form>
			<div class="table-wrap">
				<table>
					<tr>
						<th>Movie</th>
						<th>Theatre</th>
						<th>Date</th>
						<th>Time</th>
						<th>Status</th>
						<th>Available</th>
						<th>Actions</th>
					</tr>
					<%
					if (shows != null)
						for (ShowBean s : shows) {
					%><tr>
						<td><%=s.getMovieName()%></td>
						<td><%=s.getMallName()%></td>
						<td><%=s.getShowDate()%></td>
						<td><%=s.getStartTime()%> - <%=s.getEndTime()%></td>
						<td><%=s.getStatus()%></td>
						<td><%=s.getAvailableSeats()%></td>
						<td class="actions"><a class="btn"
							href="${pageContext.request.contextPath}/admin?editShow=<%=s.getShowId()%>#shows">Edit</a>
						<form method="post"
								action="${pageContext.request.contextPath}/admin">
								<input type="hidden" name="action" value="generateShowSeats"><input
									type="hidden" name="showId" value="<%=s.getShowId()%>"><input
									type="hidden" name="mallId" value="<%=s.getMallId()%>"><input
									type="number" step="0.01" min="0" name="seatPrice" value="150"
									style="width: 90px">
								<button class="btn">Seats</button>
							</form>
							<form method="post"
								action="${pageContext.request.contextPath}/admin"
								onsubmit="return confirm('Delete this show?');">
								<input type="hidden" name="action" value="deleteShow"><input
									type="hidden" name="id" value="<%=s.getShowId()%>">
								<button class="btn danger">Delete</button>
							</form></td>
					</tr>
					<%
					}
					%>
				</table>
			</div>
		</section>

		<section id="seats" class="panel">
			<h2>Seat Management</h2>
			<form method="post" action="${pageContext.request.contextPath}/admin">
				<input type="hidden" name="action"
					value="<%=editSeat == null ? "addSeat" : "updateSeat"%>">
				<%
				if (editSeat != null) {
				%><input type="hidden" name="id"
					value="<%=editSeat.getSeatId()%>">
				<%
				}
				%><div class="form-grid">
					<label>Theatre<select required name="mallId"><option
								value="">Select theatre</option>
							<%
							if (theatres != null)
								for (TheatreBean t : theatres) {
							%><option
								value="<%=t.getId()%>"
								<%=editSeat != null && t.getId().equals(editSeat.getMallId()) ? "selected" : ""%>><%=t.getName()%></option>
							<%
							}
							%></select></label><label>Seat Type<select required name="seatTypeId"><option
								value="">Select type</option>
							<%
							if (seatTypes != null)
								for (SeatBean st : seatTypes) {
							%><option
								value="<%=st.getSeatTypeId()%>"
								<%=editSeat != null && st.getSeatTypeId().equals(editSeat.getSeatTypeId()) ? "selected" : ""%>><%=st.getTypeName()%></option>
							<%
							}
							%></select></label><label>Row<input required maxlength="10"
						name="rowName"
						value="<%=editSeat == null ? "" : editSeat.getRowName()%>"></label><label>Seat
						Number<input required type="number" min="1" name="seatNumber"
						value="<%=editSeat == null ? "" : editSeat.getSeatNumber()%>">
					</label><label>Status<select name="status"><option
								value="on"
								<%=editSeat == null || editSeat.isActive() ? "selected" : ""%>>Active</option>
							<option value="off"
								<%=editSeat != null && !editSeat.isActive() ? "selected" : ""%>>Inactive</option></select></label>
				</div>
				<br>
				<button class="btn"><%=editSeat == null ? "Add Seat" : "Update Seat"%></button>
				<%
				if (editSeat != null) {
				%><a class="btn secondary"
					href="${pageContext.request.contextPath}/admin#seats">Cancel</a>
				<%
				}
				%>
			</form>
			<div class="table-wrap">
				<table>
					<tr>
						<th>Theatre</th>
						<th>Seat</th>
						<th>Type</th>
						<th>Status</th>
						<th>Actions</th>
					</tr>
					<%
					if (seats != null)
						for (SeatBean s : seats) {
					%><tr>
						<td><%=s.getMallName()%></td>
						<td><%=s.getRowName()%><%=s.getSeatNumber()%></td>
						<td><%=s.getTypeName()%></td>
						<td><%=s.isActive() ? "Active" : "Inactive"%></td>
						<td class="actions"><a class="btn"
							href="${pageContext.request.contextPath}/admin?editSeat=<%=s.getSeatId()%>#seats">Edit</a>
						<form method="post"
								action="${pageContext.request.contextPath}/admin"
								onsubmit="return confirm('Delete this seat?');">
								<input type="hidden" name="action" value="deleteSeat"><input
									type="hidden" name="id" value="<%=s.getSeatId()%>">
								<button class="btn danger">Delete</button>
							</form></td>
					</tr>
					<%
					}
					%>
				</table>
			</div>
		</section>
	</div>
</body>
</html>