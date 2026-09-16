
package com.movieticket.util;

import java.io.InputStream;
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

		Properties properties = new Properties();

		try {
			InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("db.properties");

			properties.load(input);

			String SENDER_EMAIL = properties.getProperty("SENDER_EMAIL");
			String SENDER_PASSWORD = properties.getProperty("SENDER_PASSWORD");

			properties.put("mail.smtp.host", "smtp.gmail.com");
			properties.put("mail.smtp.port", "587");
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls.enable", "true");

			Session mailSession = Session.getInstance(properties, new Authenticator() {

				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
				}
			});

			Message message = new MimeMessage(mailSession);

			message.setFrom(new InternetAddress(SENDER_EMAIL));

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

	public static void sendOtpEmail(String receiverEmail, String otp) throws Exception {

		Properties properties = new Properties();

		InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("db.properties");

		properties.load(input);

		String SENDER_EMAIL = properties.getProperty("SENDER_EMAIL");

		String SENDER_PASSWORD = properties.getProperty("SENDER_PASSWORD");

		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");

		Session session = Session.getInstance(properties, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
			}
		});

		Message message = new MimeMessage(session);

		message.setFrom(new InternetAddress(SENDER_EMAIL));

		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverEmail));

		message.setSubject("MovieBook - Email Verification Code");

		String html = "<!DOCTYPE html>" + "<html>" + "<body style='font-family:Arial,sans-serif;"
				+ "background:#f5f7fa;padding:30px;'>"

				+ "<div style='max-width:500px;margin:auto;" + "background:white;padding:30px;"
				+ "border-radius:12px;'>"

				+ "<h2 style='margin-top:0;'>Verify your email</h2>"

				+ "<p>Your MovieBook verification code is:</p>"

				+ "<div style='font-size:32px;font-weight:bold;" + "letter-spacing:8px;text-align:center;"
				+ "padding:20px;background:#f2f4f7;" + "border-radius:10px;'>"

				+ otp

				+ "</div>"

				+ "<p style='margin-top:25px;'>" + "This code is required to verify your email address." + "</p>"

				+ "<p style='color:#667085;font-size:13px;'>"
				+ "If you did not request this code, you can safely ignore this email." + "</p>"

				+ "</div>" + "</body>" + "</html>";

		message.setContent(html, "text/html; charset=UTF-8");

		Transport.send(message);
	}
}
