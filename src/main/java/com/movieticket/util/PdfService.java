package com.movieticket.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import com.movieticket.model.TicketBean;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

public class PdfService {

	public static byte[] generateTicketPdf(TicketBean ticket) {

		try {

			String qrBase64 = generateQRCode(ticket.getBookingReference());
			String posterData = getImageAsBase64(ticket.getPosterUrl());

			String html = "<!DOCTYPE html>" + "<html>" + "<head>" + "<meta charset='UTF-8' />" + "<style>"

					+ "* {" + "margin: 0;" + "padding: 0;" + "box-sizing: border-box;" + "}"

					+ "body {" + "font-family: Arial, Helvetica, sans-serif;" + "background: #f5f5f5;"
					+ "color: #222222;" + "padding: 30px;" + "}"

					+ ".ticket {" + "width: 100%;" + "background: #ffffff;" + "border-radius: 18px;"
					+ "overflow: hidden;" + "}"

					+ ".ticket-header {" + "padding: 25px 30px;" + "background: #fafafa;"
					+ "border-bottom: 1px solid #dddddd;" + "}"

					+ ".ticket-header h1 {" + "font-size: 26px;" + "margin-bottom: 6px;" + "}"

					+ ".ticket-header p {" + "color: #666666;" + "font-size: 13px;" + "}"

					+ ".confirmed {" + "margin-top: 10px;" + "padding: 7px 12px;" + "border-radius: 20px;"
					+ "background: #e8f7ef;" + "color: #218a55;" + "font-size: 12px;" + "font-weight: bold;"
					+ "display: inline-block;" + "}"

					+ ".movie-section {" + "padding: 25px 30px;" + "}"

					+ ".movie-poster {" + "width: 120px;" + "height: 170px;" + "float: left;" + "margin-right: 20px;"
					+ "background: #eeeeee;" + "border-radius: 10px;" + "overflow: hidden;" + "}"

					+ ".movie-poster img {" + "width: 120px;" + "height: 170px;" + "}"

					+ ".movie-info {" + "min-height: 170px;" + "padding-top: 5px;" + "}"

					+ ".movie-title {" + "font-size: 25px;" + "margin-bottom: 10px;" + "}"

					+ ".movie-meta {" + "color: #666666;" + "font-size: 13px;" + "margin-bottom: 12px;" + "}"

					+ ".genre {" + "display: inline-block;" + "padding: 5px 10px;" + "border-radius: 20px;"
					+ "background: #eeeeee;" + "color: #444444;" + "font-size: 12px;" + "margin-bottom: 20px;" + "}"

					+ ".booking-ref {" + "padding: 12px 14px;" + "background: #f5f5f5;" + "border-radius: 8px;"
					+ "display: inline-block;" + "}"

					+ ".section {" + "padding: 22px 30px;" + "border-top: 1px solid #e0e0e0;" + "}"

					+ ".section-title {" + "font-size: 17px;" + "margin-bottom: 17px;" + "}"

					+ ".detail-label {" + "color: #888888;" + "font-size: 10px;" + "text-transform: uppercase;"
					+ "letter-spacing: 1px;" + "display: block;" + "margin-bottom: 5px;" + "}"

					+ ".detail-value {" + "color: #222222;" + "font-size: 13px;" + "font-weight: bold;" + "}"

					+ ".cinema-box {" + "padding: 18px;" + "background: #fafafa;" + "border-radius: 10px;" + "}"

					+ ".detail {" + "margin-bottom: 13px;" + "}"

					+ ".show-details {" + "width: 100%;" + "}"

					+ ".show-item {" + "display: inline-block;" + "width: 31%;" + "margin-right: 2%;" + "padding: 16px;"
					+ "background: #fafafa;" + "border-radius: 10px;" + "vertical-align: top;" + "}"

					+ ".show-value {" + "font-size: 16px;" + "font-weight: bold;" + "}"

					+ ".seats-box {" + "background: #fafafa;" + "border-radius: 10px;" + "overflow: hidden;" + "}"

					+ ".seat-header, .seat-row {" + "width: 100%;" + "padding: 13px 16px;" + "}"

					+ ".seat-header {" + "background: #eeeeee;" + "color: #888888;" + "font-size: 10px;"
					+ "text-transform: uppercase;" + "}"

					+ ".seat-row {" + "font-size: 13px;" + "font-weight: bold;" + "}"

					+ ".customer-details {" + "width: 100%;" + "}"

					+ ".customer-details .detail {" + "display: inline-block;" + "width: 31%;" + "margin-right: 2%;"
					+ "vertical-align: top;" + "}"

					+ ".payment-details {" + "width: 100%;" + "}"

					+ ".payment-details .detail {" + "display: inline-block;" + "width: 47%;" + "margin-right: 2%;"
					+ "vertical-align: top;" + "}"

					+ ".status {" + "display: inline-block;" + "padding: 5px 10px;" + "border-radius: 20px;"
					+ "background: #e8f7ef;" + "color: #218a55;" + "font-size: 11px;" + "font-weight: bold;" + "}"

					+ ".total-section {" + "padding: 20px 30px;" + "background: #fafafa;"
					+ "border-top: 1px solid #dddddd;" + "border-bottom: 1px solid #dddddd;" + "}"

					+ ".total-label {" + "display: block;" + "font-size: 15px;" + "font-weight: bold;"
					+ "margin-top: 4px;" + "}"

					+ ".total-amount {" + "font-size: 25px;" + "font-weight: bold;" + "margin-top: 8px;" + "}"

					+ ".booking-section {" + "padding: 25px 30px;" + "}"

					+ ".booking-info {" + "width: 65%;" + "display: inline-block;" + "vertical-align: top;" + "}"

					+ ".booking-info h3 {" + "font-size: 17px;" + "margin-bottom: 18px;" + "}"

					+ ".qr-box {" + "width: 150px;" + "height: 150px;" + "padding: 10px;" + "display: inline-block;"
					+ "vertical-align: top;" + "text-align: center;" + "}"

					+ ".qr-box img {" + "width: 130px;" + "height: 130px;" + "}"

					+ ".ticket-footer {" + "padding: 18px 30px;" + "background: #eeeeee;" + "text-align: center;"
					+ "color: #777777;" + "font-size: 11px;" + "line-height: 1.6;" + "}"

					+ ".ticket-footer strong {" + "display: block;" + "color: #444444;" + "margin-bottom: 5px;" + "}"

					+ "</style>" + "</head>"

					+ "<body>"

					+ "<div class='ticket'>"

					+ "<div class='ticket-header'>" + "<h1>Movie E-Ticket</h1>" + "<p>Booking Confirmed</p>"
					+ "<div class='confirmed'>CONFIRMED</div>" + "</div>"

					+ "<div class='movie-section'>"

					+ "<div class='movie-poster'>";

			if (posterData != null) {
				html += "<img src='" + posterData + "' />";
			}

			html += "</div>"

					+ "<div class='movie-info'>"

					+ "<h2 class='movie-title'>" + ticket.getMovieTitle() + "</h2>"

					+ "<div class='movie-meta'>" + ticket.getLanguage() + " &#160; • &#160; " + ticket.getCertificate()
					+ " &#160; • &#160; " + ticket.getDurationMinutes() + " min" + "</div>"

					+ "<div class='genre'>" + ticket.getGenre() + "</div>"

					+ "<div class='booking-ref'>" + "<span class='detail-label'>Booking Reference</span>" + "<strong>"
					+ ticket.getBookingReference() + "</strong>" + "</div>"

					+ "</div>" + "</div>"

					+ "<div class='section'>" + "<h3 class='section-title'>Cinema Details</h3>"

					+ "<div class='cinema-box'>"

					+ "<div class='detail'>" + "<span class='detail-label'>Cinema / Mall</span>"
					+ "<span class='detail-value'>" + ticket.getMallName() + "</span>" + "</div>"

					+ "<div class='detail'>" + "<span class='detail-label'>Address</span>"
					+ "<span class='detail-value'>" + ticket.getMallAddress() + "</span>" + "</div>"

					+ "<div class='detail'>" + "<span class='detail-label'>City</span>" + "<span class='detail-value'>"
					+ ticket.getCity() + "</span>" + "</div>"

					+ "<div class='detail'>" + "<span class='detail-label'>State</span>" + "<span class='detail-value'>"
					+ ticket.getState() + "</span>" + "</div>"

					+ "<div class='detail'>" + "<span class='detail-label'>Pincode</span>"
					+ "<span class='detail-value'>" + ticket.getPincode() + "</span>" + "</div>"

					+ "</div>" + "</div>"

					+ "<div class='section'>" + "<h3 class='section-title'>Show Details</h3>"

					+ "<div class='show-details'>"

					+ "<div class='show-item'>" + "<span class='detail-label'>DATE</span>" + "<span class='show-value'>"
					+ ticket.getShowDate() + "</span>" + "</div>"

					+ "<div class='show-item'>" + "<span class='detail-label'>START TIME</span>"
					+ "<span class='show-value'>" + ticket.getStartTime() + "</span>" + "</div>"

					+ "<div class='show-item'>" + "<span class='detail-label'>END TIME</span>"
					+ "<span class='show-value'>" + ticket.getEndTime() + "</span>" + "</div>"

					+ "</div>" + "</div>"

					+ "<div class='section'>" + "<h3 class='section-title'>Seat Details</h3>"

					+ "<div class='seats-box'>"

					+ "<div class='seat-header'>"
					+ "Seat &#160;&#160;&#160;&#160;&#160;&#160; Type &#160;&#160;&#160;&#160;&#160;&#160; Price"
					+ "</div>"

					+ "<div class='seat-row'>" + ticket.getSeats() + " &#160;&#160;&#160;&#160;&#160;&#160; "
					+ "Booked Seats" + " &#160;&#160;&#160;&#160;&#160;&#160; ₹" + ticket.getTotalAmount() + "</div>"

					+ "</div>" + "</div>"

					+ "<div class='section'>" + "<h3 class='section-title'>Customer Details</h3>"

					+ "<div class='customer-details'>"

					+ "<div class='detail'>" + "<span class='detail-label'>NAME</span>" + "<span class='detail-value'>"
					+ ticket.getCustomerName() + "</span>" + "</div>"

					+ "<div class='detail'>" + "<span class='detail-label'>EMAIL</span>" + "<span class='detail-value'>"
					+ ticket.getCustomerEmail() + "</span>" + "</div>"

					+ "<div class='detail'>" + "<span class='detail-label'>PHONE</span>" + "<span class='detail-value'>"
					+ ticket.getCustomerPhone() + "</span>" + "</div>"

					+ "</div>" + "</div>"

					+ "<div class='section'>" + "<h3 class='section-title'>Payment Details</h3>"

					+ "<div class='payment-details'>"

					+ "<div class='detail'>" + "<span class='detail-label'>PAYMENT METHOD</span>"
					+ "<span class='detail-value'>" + ticket.getPaymentMethod() + "</span>" + "</div>"

					+ "<div class='detail'>" + "<span class='detail-label'>TRANSACTION ID</span>"
					+ "<span class='detail-value'>" + ticket.getTransactionId() + "</span>" + "</div>"

					+ "<div class='detail'>" + "<span class='detail-label'>PAYMENT STATUS</span>"
					+ "<span class='status'>" + ticket.getPaymentStatus() + "</span>" + "</div>"

					+ "<div class='detail'>" + "<span class='detail-label'>PAID AT</span>"
					+ "<span class='detail-value'>" + ticket.getPaidAt() + "</span>" + "</div>"

					+ "</div>" + "</div>"

					+ "<div class='total-section'>" + "<span class='detail-label'>TOTAL AMOUNT</span>"
					+ "<span class='total-label'>Amount Paid</span>" + "<div class='total-amount'>₹"
					+ ticket.getTotalAmount() + "</div>" + "</div>"

					+ "<div class='booking-section'>"

					+ "<div class='booking-info'>"

					+ "<h3>Booking Information</h3>"

					+ "<div class='detail'>" + "<span class='detail-label'>BOOKING ID</span>"
					+ "<span class='detail-value'>" + ticket.getBookingId() + "</span>" + "</div>"

					+ "<div class='detail'>" + "<span class='detail-label'>BOOKING STATUS</span>"
					+ "<span class='status'>" + ticket.getBookingStatus() + "</span>" + "</div>"

					+ "<div class='detail'>" + "<span class='detail-label'>BOOKED ON</span>"
					+ "<span class='detail-value'>" + ticket.getBookingDate() + "</span>" + "</div>"

					+ "</div>"

					+ "<div class='qr-box'>" + "<img src='data:image/png;base64," + qrBase64 + "' />" + "</div>"

					+ "</div>"

					+ "<div class='ticket-footer'>"

					+ "<strong>Please arrive 15-20 minutes before the show.</strong>"

					+ "<p>Carry this e-ticket and a valid ID for entry into the theatre.</p>"

					+ "<p>Tickets are subject to the cinema's cancellation and refund policy.</p>"

					+ "</div>"

					+ "</div>"

					+ "</body>" + "</html>";

			ByteArrayOutputStream output = new ByteArrayOutputStream();

			PdfRendererBuilder builder = new PdfRendererBuilder();

			builder.withHtmlContent(html, null);

			builder.toStream(output);

			builder.run();

			return output.toByteArray();

		} catch (Exception e) {

			e.printStackTrace();

			return null;
		}
	}

	private static String generateQRCode(String text) throws Exception {

		BitMatrix matrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, 300, 300);

		ByteArrayOutputStream output = new ByteArrayOutputStream();

		MatrixToImageWriter.writeToStream(matrix, "PNG", output);

		return Base64.getEncoder().encodeToString(output.toByteArray());
	}

	private static String getImageAsBase64(String imageUrl) {

		try {

			if (imageUrl == null || imageUrl.trim().isEmpty()) {
				return null;
			}

			URL url = new URL(imageUrl);

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			connection.setRequestProperty("User-Agent", "Mozilla/5.0");

			InputStream inputStream = connection.getInputStream();

			ByteArrayOutputStream output = new ByteArrayOutputStream();

			byte[] buffer = new byte[4096];

			int bytesRead;

			while ((bytesRead = inputStream.read(buffer)) != -1) {
				output.write(buffer, 0, bytesRead);
			}

			inputStream.close();

			String contentType = connection.getContentType();

			if (contentType == null) {
				contentType = "image/jpeg";
			}

			return "data:" + contentType + ";base64," + Base64.getEncoder().encodeToString(output.toByteArray());

		} catch (Exception e) {

			System.out.println("Poster could not be loaded: " + e.getMessage());

			return null;
		}
	}

}
