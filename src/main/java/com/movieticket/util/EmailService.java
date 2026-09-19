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

	// ============================================================
	// TICKET BOOKING EMAIL
	// ============================================================

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

			message.setSubject("Booking Confirmed • " + ticket.getMovieTitle());

			// ========================================================
			// EMAIL HTML
			// ========================================================

			MimeBodyPart textPart = new MimeBodyPart();

			String emailContent =

					"<!DOCTYPE html>" + "<html>" + "<head>" +

							"<meta charset='UTF-8'>" +

							"<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +

							"</head>" +

							"<body style='margin:0;padding:0;background:#f3f4f6;"
							+ "font-family:Arial,Helvetica,sans-serif;color:#1f2937;'>"

							// =================================================
							// OUTER WRAPPER
							// =================================================
							+

							"<table width='100%' cellpadding='0' cellspacing='0' "
							+ "border='0' style='background:#f3f4f6;padding:35px 15px;'>"

							+

							"<tr>" + "<td align='center'>"

							+

							"<table width='620' cellpadding='0' cellspacing='0' "
							+ "border='0' style='max-width:620px;width:100%;" + "background:#ffffff;border-radius:14px;"
							+ "overflow:hidden;border:1px solid #e5e7eb;'>"

							// =================================================
							// HEADER
							// =================================================
							+

							"<tr>" + "<td style='background:#111827;padding:22px 30px;'>"

							+

							"<table width='100%' cellpadding='0' cellspacing='0'>" + "<tr>"

							+

							"<td style='font-size:23px;font-weight:bold;color:#ffffff;'>"

							+

							"<span style='color:#e50914;'>M</span>" + " MovieBook"

							+

							"</td>"

							+

							"<td align='right' style='font-size:12px;color:#cbd5e1;'>"

							+

							"BOOKING CONFIRMATION"

							+

							"</td>"

							+

							"</tr>" + "</table>"

							+

							"</td>" + "</tr>"

							// =================================================
							// SUCCESS SECTION
							// =================================================
							+

							"<tr>" + "<td style='padding:35px 35px 20px;'>"

							+

							"<div style='width:54px;height:54px;" + "background:#dcfce7;border-radius:50%;"
							+ "text-align:center;line-height:54px;" + "font-size:27px;color:#15803d;font-weight:bold;'>"

							+

							"✓"

							+

							"</div>"

							+

							"<h1 style='margin:20px 0 8px;font-size:25px;" + "line-height:1.3;color:#111827;'>"

							+

							"Your booking is confirmed"

							+

							"</h1>"

							+

							"<p style='margin:0;font-size:14px;" + "line-height:1.7;color:#6b7280;'>"

							+

							"Hi " + ticket.getCustomerName() + ", your movie tickets have been successfully booked. "
							+ "Your e-ticket is attached to this email."

							+

							"</p>"

							+

							"</td>" + "</tr>"

							// =================================================
							// MOVIE CARD
							// =================================================
							+

							"<tr>" + "<td style='padding:10px 35px 0;'>"

							+

							"<table width='100%' cellpadding='0' cellspacing='0' "
							+ "style='background:#f9fafb;border:1px solid #e5e7eb;" + "border-radius:12px;'>"

							+

							"<tr>" + "<td style='padding:22px;'>"

							+

							"<div style='font-size:11px;color:#9ca3af;"
							+ "font-weight:bold;letter-spacing:1px;text-transform:uppercase;'>"

							+

							"MOVIE"

							+

							"</div>"

							+

							"<div style='font-size:21px;font-weight:bold;" + "color:#111827;margin-top:7px;'>"

							+

							ticket.getMovieTitle()

							+

							"</div>"

							+

							"<div style='margin-top:8px;font-size:13px;" + "color:#6b7280;'>"

							+

							"MovieBook Online Ticket"

							+

							"</div>"

							+

							"</td>" + "</tr>" +

							"</table>"

							+

							"</td>" + "</tr>"

							// =================================================
							// BOOKING REFERENCE
							// =================================================
							+

							"<tr>" + "<td style='padding:22px 35px 5px;'>"

							+

							"<table width='100%' cellpadding='0' cellspacing='0' "
							+ "style='border:1px dashed #d1d5db;border-radius:10px;'>"

							+

							"<tr>" + "<td style='padding:16px 18px;'>"

							+

							"<div style='font-size:11px;color:#9ca3af;" + "letter-spacing:1px;font-weight:bold;'>"

							+

							"BOOKING REFERENCE"

							+

							"</div>"

							+

							"<div style='font-size:19px;font-weight:bold;"
							+ "letter-spacing:1px;color:#111827;margin-top:6px;'>"

							+

							ticket.getBookingReference()

							+

							"</div>"

							+

							"</td>" +

							"<td align='right' style='padding:16px 18px;'>"

							+

							"<span style='display:inline-block;" + "background:#dcfce7;color:#15803d;"
							+ "font-size:11px;font-weight:bold;" + "padding:7px 11px;border-radius:20px;'>"

							+

							"CONFIRMED"

							+

							"</span>"

							+

							"</td>" +

							"</tr>" +

							"</table>"

							+

							"</td>" + "</tr>"

							// =================================================
							// SHOW DETAILS
							// =================================================
							+

							"<tr>" + "<td style='padding:20px 35px;'>"

							+

							"<table width='100%' cellpadding='0' cellspacing='0'>"

							+

							"<tr>"

							+

							"<td width='50%' style='padding:12px 0;'>" + "<div style='font-size:11px;color:#9ca3af;"
							+ "font-weight:bold;'>SHOW DATE</div>" + "<div style='font-size:14px;font-weight:bold;"
							+ "color:#111827;margin-top:5px;'>" + ticket.getShowDate() + "</div>" + "</td>"

							+

							"<td width='50%' style='padding:12px 0;'>" + "<div style='font-size:11px;color:#9ca3af;"
							+ "font-weight:bold;'>START TIME</div>" + "<div style='font-size:14px;font-weight:bold;"
							+ "color:#111827;margin-top:5px;'>" + ticket.getStartTime() + "</div>" + "</td>"

							+

							"</tr>"

							+

							"<tr>"

							+

							"<td width='50%' style='padding:12px 0;'>" + "<div style='font-size:11px;color:#9ca3af;"
							+ "font-weight:bold;'>SEATS</div>" + "<div style='font-size:14px;font-weight:bold;"
							+ "color:#111827;margin-top:5px;'>" + ticket.getSeats() + "</div>" + "</td>"

							+

							"<td width='50%' style='padding:12px 0;'>" + "<div style='font-size:11px;color:#9ca3af;"
							+ "font-weight:bold;'>TOTAL PAID</div>" + "<div style='font-size:16px;font-weight:bold;"
							+ "color:#111827;margin-top:5px;'>₹" + ticket.getTotalAmount() + "</div>" + "</td>"

							+

							"</tr>"

							+

							"</table>"

							+

							"</td>" + "</tr>"

							// =================================================
							// IMPORTANT INFORMATION
							// =================================================
							+

							"<tr>" + "<td style='padding:0 35px 25px;'>"

							+

							"<table width='100%' cellpadding='0' cellspacing='0' "
							+ "style='background:#fff7ed;border:1px solid #fed7aa;" + "border-radius:10px;'>"

							+

							"<tr>" + "<td style='padding:17px 18px;'>"

							+

							"<div style='font-size:13px;font-weight:bold;" + "color:#9a3412;margin-bottom:6px;'>"

							+

							"Before you arrive"

							+

							"</div>"

							+

							"<div style='font-size:12px;line-height:1.7;" + "color:#7c2d12;'>"

							+

							"Please carry your booking reference or the attached "
							+ "e-ticket when you arrive at the cinema. "
							+ "Entry is subject to the cinema's applicable terms and conditions."

							+

							"</div>"

							+

							"</td>" + "</tr>" +

							"</table>"

							+

							"</td>" + "</tr>"

							// =================================================
							// ATTACHMENT INFORMATION
							// =================================================
							+

							"<tr>" + "<td style='padding:0 35px 30px;'>"

							+

							"<div style='border-top:1px solid #e5e7eb;" + "padding-top:22px;'>"

							+

							"<div style='font-size:13px;font-weight:bold;" + "color:#374151;'>"

							+

							"Your e-ticket is attached"

							+

							"</div>"

							+

							"<div style='font-size:12px;color:#6b7280;" + "margin-top:6px;line-height:1.6;'>"

							+

							"The attached PDF contains your complete booking "
							+ "details and can be saved for your records."

							+

							"</div>"

							+

							"</div>"

							+

							"</td>" + "</tr>"

							// =================================================
							// FOOTER
							// =================================================
							+

							"<tr>" + "<td style='background:#f9fafb;" + "border-top:1px solid #e5e7eb;"
							+ "padding:24px 30px;text-align:center;'>"

							+

							"<div style='font-size:13px;font-weight:bold;" + "color:#374151;'>"

							+

							"MovieBook"

							+

							"</div>"

							+

							"<div style='font-size:11px;color:#9ca3af;" + "margin-top:7px;line-height:1.6;'>"

							+

							"This is an automated email. Please do not reply to this message."

							+

							"</div>"

							+

							"<div style='font-size:11px;color:#9ca3af;" + "margin-top:5px;'>"

							+

							"© MovieBook. All rights reserved."

							+

							"</div>"

							+

							"</td>" + "</tr>"

							+

							"</table>" +

							"</td>" + "</tr>" + "</table>" +

							"</body>" + "</html>";

			textPart.setContent(emailContent, "text/html; charset=UTF-8");

			// ========================================================
			// PDF ATTACHMENT
			// ========================================================

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

	// ============================================================
	// OTP EMAIL
	// ============================================================

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

		message.setSubject("MovieBook • Your verification code");

		String html =

				"<!DOCTYPE html>" + "<html>" + "<head>" + "<meta charset='UTF-8'>" + "<meta name='viewport' "
						+ "content='width=device-width,initial-scale=1.0'>" + "</head>" +

						"<body style='margin:0;padding:0;" + "background:#f3f4f6;"
						+ "font-family:Arial,Helvetica,sans-serif;'>"

						+

						"<table width='100%' cellpadding='0' cellspacing='0' "
						+ "style='padding:40px 15px;background:#f3f4f6;'>"

						+

						"<tr>" + "<td align='center'>"

						+

						"<table width='520' cellpadding='0' cellspacing='0' " + "style='max-width:520px;width:100%;"
						+ "background:#ffffff;border:1px solid #e5e7eb;" + "border-radius:14px;overflow:hidden;'>"

						// HEADER
						+

						"<tr>" + "<td style='background:#111827;padding:23px 30px;'>"

						+

						"<div style='font-size:23px;font-weight:bold;color:white;'>"

						+

						"<span style='color:#e50914;'>M</span> MovieBook"

						+

						"</div>"

						+

						"</td>" + "</tr>"

						// CONTENT
						+

						"<tr>" + "<td style='padding:35px 32px;'>"

						+

						"<div style='font-size:12px;font-weight:bold;" + "letter-spacing:1px;color:#6b7280;'>"

						+

						"EMAIL VERIFICATION"

						+

						"</div>"

						+

						"<h2 style='margin:10px 0 10px;" + "font-size:24px;color:#111827;'>"

						+

						"Verify your email address"

						+

						"</h2>"

						+

						"<p style='font-size:14px;line-height:1.7;" + "color:#6b7280;margin-bottom:25px;'>"

						+

						"Use the verification code below to continue " + "with your MovieBook account."

						+

						"</p>"

						// OTP
						+

						"<div style='background:#f9fafb;" + "border:1px solid #e5e7eb;"
						+ "border-radius:12px;padding:22px;" + "text-align:center;'>"

						+

						"<div style='font-size:11px;color:#9ca3af;" + "letter-spacing:1px;margin-bottom:10px;'>"

						+

						"VERIFICATION CODE"

						+

						"</div>"

						+

						"<div style='font-size:34px;font-weight:bold;" + "letter-spacing:9px;color:#111827;'>"

						+

						otp

						+

						"</div>"

						+

						"</div>"

						+

						"<p style='font-size:12px;color:#6b7280;" + "line-height:1.7;margin-top:22px;'>"

						+

						"For your security, never share this verification "
						+ "code with anyone, including someone claiming to be " + "from MovieBook."

						+

						"</p>"

						+

						"<div style='margin-top:24px;padding-top:20px;" + "border-top:1px solid #e5e7eb;'>"

						+

						"<p style='margin:0;font-size:12px;" + "color:#9ca3af;line-height:1.6;'>"

						+

						"If you did not request this code, you can safely " + "ignore this email."

						+

						"</p>"

						+

						"</div>"

						+

						"</td>" + "</tr>"

						// FOOTER
						+

						"<tr>" + "<td style='background:#f9fafb;" + "border-top:1px solid #e5e7eb;"
						+ "padding:20px;text-align:center;'>"

						+

						"<div style='font-size:11px;color:#9ca3af;'>"

						+

						"© MovieBook. All rights reserved."

						+

						"</div>"

						+

						"</td>" + "</tr>"

						+

						"</table>"

						+

						"</td>" + "</tr>" + "</table>"

						+

						"</body>" + "</html>";

		message.setContent(html, "text/html; charset=UTF-8");

		Transport.send(message);
	}

	// ============================================================
	// WELCOME EMAIL
	// ============================================================

	public static void sendWelcomeEmail(String receiverEmail, String customerName, String contextPath)
			throws Exception {

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

		message.setSubject("Welcome to MovieBook, " + customerName);

		String html =

				"<!DOCTYPE html>" + "<html>" + "<head>" + "<meta charset='UTF-8'>" + "<meta name='viewport' "
						+ "content='width=device-width,initial-scale=1.0'>" + "</head>" +

						"<body style='margin:0;padding:0;" + "background:#f3f4f6;"
						+ "font-family:Arial,Helvetica,sans-serif;" + "color:#111827;'>"

						+

						"<table width='100%' cellpadding='0' cellspacing='0' "
						+ "style='padding:40px 15px;background:#f3f4f6;'>"

						+

						"<tr>" + "<td align='center'>"

						+

						"<table width='600' cellpadding='0' cellspacing='0' " + "style='max-width:600px;width:100%;"
						+ "background:white;border-radius:14px;" + "overflow:hidden;border:1px solid #e5e7eb;'>"

						// HEADER
						+

						"<tr>" + "<td style='background:#111827;padding:24px 30px;'>"

						+

						"<div style='font-size:24px;" + "font-weight:bold;color:#ffffff;'>"

						+

						"<span style='color:#e50914;'>M</span> MovieBook"

						+

						"</div>"

						+

						"</td>" + "</tr>"

						// CONTENT
						+

						"<tr>" + "<td style='padding:38px 32px;'>"

						+

						"<div style='width:54px;height:54px;" + "background:#dcfce7;border-radius:50%;"
						+ "line-height:54px;text-align:center;" + "font-size:26px;color:#15803d;font-weight:bold;'>"

						+

						"✓"

						+

						"</div>"

						+

						"<h1 style='font-size:26px;margin:20px 0 10px;'>"

						+

						"Welcome to MovieBook!"

						+

						"</h1>"

						+

						"<p style='font-size:14px;line-height:1.8;" + "color:#6b7280;'>"

						+

						"Hi " + customerName + ","

						+

						"</p>"

						+

						"<p style='font-size:14px;line-height:1.8;" + "color:#6b7280;'>"

						+

						"Your MovieBook account has been successfully created. "
						+ "You can now browse movies, choose your seats and " + "book your favourite shows."

						+

						"</p>"

						// ACCOUNT CARD
						+

						"<table width='100%' cellpadding='0' cellspacing='0' "
						+ "style='margin:25px 0;background:#f9fafb;" + "border:1px solid #e5e7eb;border-radius:10px;'>"

						+

						"<tr>" + "<td style='padding:18px;'>"

						+

						"<div style='font-size:11px;color:#9ca3af;" + "font-weight:bold;letter-spacing:1px;'>"

						+

						"ACCOUNT EMAIL"

						+

						"</div>"

						+

						"<div style='font-size:14px;font-weight:bold;" + "color:#111827;margin-top:7px;'>"

						+

						receiverEmail

						+

						"</div>"

						+

						"</td>" + "</tr>" +

						"</table>"

						+

						"<div style='border-top:1px solid #e5e7eb;" + "padding-top:22px;'>"

						+

						"<div style='font-size:13px;font-weight:bold;" + "color:#374151;'>"

						+

						"Security reminder"

						+

						"</div>"

						+

						"<p style='font-size:12px;color:#6b7280;" + "line-height:1.7;margin-bottom:0;'>"

						+

						"MovieBook will never ask you to share your " + "password or verification codes by email."

						+

						"</p>"

						+

						"</div>"

						+

						"</td>" + "</tr>"

						// FOOTER
						+

						"<tr>" + "<td style='background:#f9fafb;" + "border-top:1px solid #e5e7eb;"
						+ "padding:22px;text-align:center;'>"

						+

						"<div style='font-size:11px;color:#9ca3af;" + "line-height:1.6;'>"

						+

						"© MovieBook. All rights reserved."

						+

						"</div>"

						+

						"</td>" + "</tr>"

						+

						"</table>"

						+

						"</td>" + "</tr>" + "</table>"

						+

						"</body>" + "</html>";

		message.setContent(html, "text/html; charset=UTF-8");

		Transport.send(message);

		System.out.println("Welcome email sent to: " + receiverEmail);
	}

	// ============================================================
	// PASSWORD CHANGED EMAIL
	// ============================================================

	public static void sendPasswordChangedEmail(String receiverEmail, String customerName, String changedAt)
			throws Exception {

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

		message.setSubject("MovieBook Security Alert • Password Changed");

		String html =

				"<!DOCTYPE html>" + "<html>" + "<head>" + "<meta charset='UTF-8'>" + "<meta name='viewport' "
						+ "content='width=device-width,initial-scale=1.0'>" + "</head>" +

						"<body style='margin:0;padding:0;" + "background:#f3f4f6;"
						+ "font-family:Arial,Helvetica,sans-serif;" + "color:#111827;'>"

						+

						"<table width='100%' cellpadding='0' cellspacing='0' "
						+ "style='padding:40px 15px;background:#f3f4f6;'>"

						+

						"<tr>" + "<td align='center'>"

						+

						"<table width='600' cellpadding='0' cellspacing='0' " + "style='max-width:600px;width:100%;"
						+ "background:#ffffff;border-radius:14px;" + "overflow:hidden;border:1px solid #e5e7eb;'>"

						// HEADER
						+

						"<tr>" + "<td style='background:#111827;padding:24px 30px;'>"

						+

						"<div style='font-size:24px;" + "font-weight:bold;color:#ffffff;'>"

						+

						"<span style='color:#e50914;'>M</span> MovieBook"

						+

						"</div>"

						+

						"</td>" + "</tr>"

						// CONTENT
						+

						"<tr>" + "<td style='padding:38px 32px;'>"

						+

						"<div style='width:54px;height:54px;" + "background:#dcfce7;border-radius:50%;"
						+ "line-height:54px;text-align:center;" + "font-size:26px;color:#15803d;font-weight:bold;'>"

						+

						"✓"

						+

						"</div>"

						+

						"<h1 style='font-size:25px;margin:20px 0 10px;'>"

						+

						"Your password was changed"

						+

						"</h1>"

						+

						"<p style='font-size:14px;line-height:1.8;" + "color:#6b7280;'>"

						+

						"Dear " + customerName + ","

						+

						"</p>"

						+

						"<p style='font-size:14px;line-height:1.8;" + "color:#6b7280;'>"

						+

						"The password for your MovieBook account " + "was successfully changed."

						+

						"</p>"

						// SECURITY DETAILS
						+

						"<table width='100%' cellpadding='0' cellspacing='0' "
						+ "style='margin:25px 0;background:#f9fafb;" + "border:1px solid #e5e7eb;border-radius:10px;'>"

						+

						"<tr>" + "<td style='padding:18px;'>"

						+

						"<div style='font-size:11px;color:#9ca3af;" + "font-weight:bold;letter-spacing:1px;'>"

						+

						"ACCOUNT"

						+

						"</div>"

						+

						"<div style='font-size:14px;font-weight:bold;" + "margin-top:6px;color:#111827;'>"

						+

						receiverEmail

						+

						"</div>"

						+

						"<div style='height:15px;'></div>"

						+

						"<div style='font-size:11px;color:#9ca3af;" + "font-weight:bold;letter-spacing:1px;'>"

						+

						"CHANGED AT"

						+

						"</div>"

						+

						"<div style='font-size:14px;font-weight:bold;" + "margin-top:6px;color:#111827;'>"

						+

						changedAt

						+

						"</div>"

						+

						"</td>" + "</tr>" +

						"</table>"

						// WARNING
						+

						"<table width='100%' cellpadding='0' cellspacing='0' " + "style='background:#fff7ed;"
						+ "border:1px solid #fed7aa;border-radius:10px;'>"

						+

						"<tr>" + "<td style='padding:18px;'>"

						+

						"<div style='font-size:13px;font-weight:bold;" + "color:#9a3412;margin-bottom:7px;'>"

						+

						"Didn't change your password?"

						+

						"</div>"

						+

						"<div style='font-size:12px;line-height:1.7;" + "color:#7c2d12;'>"

						+

						"If you did not make this change, someone may have "
						+ "access to your account. Please reset your password "
						+ "immediately and contact MovieBook support."

						+

						"</div>"

						+

						"</td>" + "</tr>" +

						"</table>"

						+

						"<p style='font-size:12px;color:#6b7280;" + "line-height:1.7;margin-top:22px;'>"

						+

						"For your security, MovieBook will never include " + "your password in an email."

						+

						"</p>"

						+

						"</td>" + "</tr>"

						// FOOTER
						+

						"<tr>" + "<td style='background:#f9fafb;" + "border-top:1px solid #e5e7eb;"
						+ "padding:22px;text-align:center;'>"

						+

						"<div style='font-size:11px;color:#9ca3af;'>"

						+

						"This is an automated security notification."

						+

						"</div>"

						+

						"<div style='font-size:11px;color:#9ca3af;" + "margin-top:5px;'>"

						+

						"© MovieBook. All rights reserved."

						+

						"</div>"

						+

						"</td>" + "</tr>"

						+

						"</table>"

						+

						"</td>" + "</tr>" + "</table>"

						+

						"</body>" + "</html>";

		message.setContent(html, "text/html; charset=UTF-8");

		Transport.send(message);

		System.out.println("Password changed email sent to: " + receiverEmail);
	}

	// ============================================================
	// ACCOUNT ACTIVATED EMAIL
	// ============================================================

	public static void sendAccountActivatedEmail(String receiverEmail, String customerName) throws Exception {

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

		message.setSubject("MovieBook • Account Activated Successfully");

		String html =

				"<!DOCTYPE html>" + "<html>" + "<head>" + "<meta charset='UTF-8'>" + "<meta name='viewport' "
						+ "content='width=device-width,initial-scale=1.0'>" + "</head>"

						+ "<body style='margin:0;padding:0;" + "background:#f3f4f6;"
						+ "font-family:Arial,Helvetica,sans-serif;" + "color:#111827;'>"

						+ "<table width='100%' cellpadding='0' cellspacing='0' "
						+ "style='padding:40px 15px;background:#f3f4f6;'>"

						+ "<tr>" + "<td align='center'>"

						+ "<table width='600' cellpadding='0' cellspacing='0' " + "style='max-width:600px;width:100%;"
						+ "background:#ffffff;" + "border-radius:14px;" + "overflow:hidden;"
						+ "border:1px solid #e5e7eb;'>"

						// =================================================
						// HEADER
						// =================================================

						+ "<tr>" + "<td style='background:#111827;padding:24px 30px;'>"

						+ "<div style='font-size:24px;" + "font-weight:bold;color:#ffffff;'>"

						+ "<span style='color:#e50914;'>M</span> MovieBook"

						+ "</div>"

						+ "</td>" + "</tr>"

						// =================================================
						// CONTENT
						// =================================================

						+ "<tr>" + "<td style='padding:38px 32px;'>"

						// SUCCESS ICON

						+ "<div style='width:54px;height:54px;" + "background:#dcfce7;" + "border-radius:50%;"
						+ "line-height:54px;" + "text-align:center;" + "font-size:26px;" + "color:#15803d;"
						+ "font-weight:bold;'>"

						+ "✓"

						+ "</div>"

						// HEADING

						+ "<h1 style='font-size:26px;" + "margin:20px 0 10px;" + "color:#111827;'>"

						+ "Account Activated"

						+ "</h1>"

						// GREETING

						+ "<p style='font-size:14px;" + "line-height:1.8;" + "color:#6b7280;'>"

						+ "Hi " + customerName + ","

						+ "</p>"

						// MESSAGE

						+ "<p style='font-size:14px;" + "line-height:1.8;" + "color:#6b7280;'>"

						+ "Your MovieBook account has been successfully " + "verified and activated."

						+ "</p>"

						// ACCOUNT CARD

						+ "<table width='100%' cellpadding='0' cellspacing='0' " + "style='margin:25px 0;"
						+ "background:#f9fafb;" + "border:1px solid #e5e7eb;" + "border-radius:10px;'>"

						+ "<tr>" + "<td style='padding:18px;'>"

						+ "<div style='font-size:11px;" + "color:#9ca3af;" + "font-weight:bold;"
						+ "letter-spacing:1px;'>"

						+ "ACCOUNT EMAIL"

						+ "</div>"

						+ "<div style='font-size:14px;" + "font-weight:bold;" + "color:#111827;" + "margin-top:7px;'>"

						+ receiverEmail

						+ "</div>"

						+ "</td>" + "</tr>"

						+ "</table>"

						// STATUS CARD

						+ "<table width='100%' cellpadding='0' cellspacing='0' " + "style='background:#f0fdf4;"
						+ "border:1px solid #bbf7d0;" + "border-radius:10px;'>"

						+ "<tr>" + "<td style='padding:18px;'>"

						+ "<div style='font-size:13px;" + "font-weight:bold;" + "color:#166534;"
						+ "margin-bottom:7px;'>"

						+ "Account Status"

						+ "</div>"

						+ "<div style='font-size:12px;" + "line-height:1.7;" + "color:#166534;'>"

						+ "Your account is now active. " + "You can browse movies, select seats and "
						+ "book your favourite shows."

						+ "</div>"

						+ "</td>" + "</tr>"

						+ "</table>"

						// SECURITY REMINDER

						+ "<div style='border-top:1px solid #e5e7eb;" + "padding-top:22px;" + "margin-top:25px;'>"

						+ "<div style='font-size:13px;" + "font-weight:bold;" + "color:#374151;'>"

						+ "Security reminder"

						+ "</div>"

						+ "<p style='font-size:12px;" + "color:#6b7280;" + "line-height:1.7;" + "margin-bottom:0;'>"

						+ "MovieBook will never ask you to share your " + "password or verification codes by email."

						+ "</p>"

						+ "</div>"

						+ "</td>" + "</tr>"

						// =================================================
						// FOOTER
						// =================================================

						+ "<tr>" + "<td style='background:#f9fafb;" + "border-top:1px solid #e5e7eb;" + "padding:22px;"
						+ "text-align:center;'>"

						+ "<div style='font-size:11px;" + "color:#9ca3af;" + "line-height:1.6;'>"

						+ "This is an automated email."

						+ "</div>"

						+ "<div style='font-size:11px;" + "color:#9ca3af;" + "margin-top:5px;'>"

						+ "© MovieBook. All rights reserved."

						+ "</div>"

						+ "</td>" + "</tr>"

						+ "</table>"

						+ "</td>" + "</tr>"

						+ "</table>"

						+ "</body>" + "</html>";

		message.setContent(html, "text/html; charset=UTF-8");

		Transport.send(message);

		System.out.println("Account activation email sent to: " + receiverEmail);
	}

}