package com.movieticket.util;

import java.util.Properties;

import jakarta.activation.DataHandler;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.util.ByteArrayDataSource;
import jakarta.servlet.http.HttpSession;

import com.movieticket.model.TicketBean;
import com.movieticket.model.UserBean;

public class EmailService {

	public static void sendTicketEmail(TicketBean ticket, HttpSession session, byte[] pdfBytes) {

		UserBean user = (UserBean) session.getAttribute("user");

		String receiverEmail = user.getEmail();

		// System.out.print(receiverEmail);

		final String senderEmail = "YourEmail";
		final String senderPassword = "YourAppPassword";

		Properties properties = new Properties();

		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");

		Session mailSession = Session.getInstance(properties, new Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication(senderEmail, senderPassword);
			}
		});

		try {

			Message message = new MimeMessage(mailSession);

			message.setFrom(new InternetAddress(senderEmail));

			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverEmail));

			message.setSubject("Movie Ticket - " + ticket.getMovieTitle());

			MimeBodyPart textPart = new MimeBodyPart();

			String emailContent = "<html>" + "<body style='font-family:Arial,sans-serif;'>" + "<h2>Movie E-Ticket</h2>"
					+ "<p>Dear " + ticket.getCustomerName() + ",</p>"
					+ "<p>Your movie ticket has been successfully booked.</p>" + "<p><b>Movie:</b> "
					+ ticket.getMovieTitle() + "</p>" + "<p><b>Booking Reference:</b> " + ticket.getBookingReference()
					+ "</p>" + "<p><b>Show Date:</b> " + ticket.getShowDate() + "</p>" + "<p><b>Start Time:</b> "
					+ ticket.getStartTime() + "</p>" + "<p><b>Seats:</b> " + ticket.getSeats() + "</p>"
					+ "<p><b>Total Amount:</b> ₹" + ticket.getTotalAmount() + "</p>"
					+ "<p>Please find your complete e-ticket attached as a PDF.</p>"
					+ "<p>Thank you for booking with us.</p>" + "</body>" + "</html>";

			textPart.setContent(emailContent, "text/html; charset=UTF-8");

			MimeBodyPart attachmentPart = new MimeBodyPart();

			ByteArrayDataSource dataSource = new ByteArrayDataSource(pdfBytes, "application/pdf");

			attachmentPart.setDataHandler(new DataHandler(dataSource));

			attachmentPart.setFileName("Movie-Ticket-" + ticket.getBookingReference() + ".pdf");

			MimeMultipart multipart = new MimeMultipart();

			multipart.addBodyPart(textPart);
			multipart.addBodyPart(attachmentPart);

			message.setContent(multipart);

			Transport.send(message);

			System.out.println("Ticket email sent to: " + receiverEmail);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}