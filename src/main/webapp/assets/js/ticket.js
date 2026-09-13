document.addEventListener("DOMContentLoaded", function() {

	var qrImage = document.getElementById("qrcode");

	if (!qrImage) {
		return;
	}

	var ticketData =
		"Booking Reference: " + ticketBookingReference +
		" | Booking ID: " + ticketBookingId +
		" | Movie: " + ticketMovieTitle +
		" | Cinema: " + ticketMallName +
		" | Date: " + ticketShowDate +
		" | Time: " + ticketStartTime +
		" | Seats: " + ticketSeats;

	var qrUrl =
		"https://quickchart.io/qr?text=" +
		encodeURIComponent(ticketData) +
		"&size=300&margin=2&ecLevel=H";

	qrImage.src = qrUrl;

});