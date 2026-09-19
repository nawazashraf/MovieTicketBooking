package com.movieticket.controller.payment;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Base64;
import java.util.UUID;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import com.movieticket.dao.BookingDAO;
import com.movieticket.dao.PaymentDAO;
import com.movieticket.dao.TicketDAO;

import com.movieticket.model.BookingBean;
import com.movieticket.model.PaymentBean;
import com.movieticket.model.TicketBean;

import com.movieticket.util.EmailService;
import com.movieticket.util.PdfService;

@WebServlet("/payment")
public class PaymentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String bookingId = request.getParameter("bookingId");
		String qr = request.getParameter("qr");
		String check = request.getParameter("check");
		String processing = request.getParameter("processing");
		String paymentMethod = request.getParameter("paymentMethod");

		PaymentDAO paymentDAO = new PaymentDAO();

		/*
		 * ================================================= CHECK PAYMENT STATUS
		 * =================================================
		 */

		if ("true".equals(check)) {

			PaymentBean payment = paymentDAO.getPaymentByBookingId(bookingId);

			response.setContentType("text/plain;charset=UTF-8");

			if (payment != null) {
				response.getWriter().print(payment.getPaymentStatus());
			} else {
				response.getWriter().print("PENDING");
			}

			return;
		}

		/*
		 * ================================================= GET BOOKING
		 * =================================================
		 */

		BookingDAO bookingDAO = new BookingDAO();

		BookingBean booking = bookingDAO.getBookingById(bookingId);

		if (booking == null) {

			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Booking not found");

			return;
		}

		/*
		 * ================================================= PROCESSING PAGE FOR QR
		 * PAYMENT =================================================
		 */

		if ("true".equals(processing)) {

			PaymentBean payment = paymentDAO.getPaymentByBookingId(bookingId);

			if (payment == null || !"SUCCESS".equals(payment.getPaymentStatus())) {

				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Payment is not completed");

				return;
			}

			request.setAttribute("bookingId", bookingId);

			request.setAttribute("paymentMethod", paymentMethod);

			request.getRequestDispatcher("/payment/payment-processing.jsp").forward(request, response);

			return;
		}

		/*
		 * ================================================= GET OR CREATE PAYMENT
		 * =================================================
		 */

		PaymentBean payment = paymentDAO.getPaymentByBookingId(bookingId);

		if (payment == null) {

			payment = new PaymentBean();

			payment.setId(UUID.randomUUID().toString());

			payment.setBookingId(booking.getId());

			payment.setAmount(booking.getTotalAmount());

			payment.setPaymentStatus("PENDING");

			boolean paymentCreated = paymentDAO.createPayment(payment);

			if (!paymentCreated) {

				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to create payment");

				return;
			}
		}

		/*
		 * ================================================= CHECK QR PAGE
		 * =================================================
		 */

		boolean qrPage = "true".equals(qr);

		/*
		 * ================================================= LOGIN REQUIRED ONLY FOR PC
		 * PAGE =================================================
		 */

		if (!qrPage) {

			HttpSession session = request.getSession(false);

			if (session == null || session.getAttribute("user") == null) {

				response.sendRedirect(request.getContextPath() + "/login.jsp");

				return;
			}
		}

		/*
		 * ================================================= CREATE QR ONLY FOR PC PAGE
		 * =================================================
		 */

		if (!qrPage) {

			String hostAddress = InetAddress.getLocalHost().getHostAddress();

			String qrUrl = "http://" + hostAddress + ":8081" + request.getContextPath() + "/payment?bookingId="
					+ bookingId + "&qr=true";

			System.out.println("QR URL = " + qrUrl);

			try {

				BitMatrix matrix = new MultiFormatWriter().encode(qrUrl, BarcodeFormat.QR_CODE, 250, 250);

				ByteArrayOutputStream output = new ByteArrayOutputStream();

				MatrixToImageWriter.writeToStream(matrix, "PNG", output);

				String qrImage = Base64.getEncoder().encodeToString(output.toByteArray());

				request.setAttribute("qrImage", qrImage);

			} catch (Exception e) {

				e.printStackTrace();
			}
		}

		/*
		 * ================================================= SEND BOOKING TO JSP
		 * =================================================
		 */

		request.setAttribute("booking", booking);

		request.setAttribute("qrPage", qrPage);

		/*
		 * ================================================= OPEN CORRECT PAGE
		 * =================================================
		 */

		if (qrPage) {

			request.getRequestDispatcher("/payment/qr-payment.jsp").forward(request, response);

		} else {

			request.getRequestDispatcher("/payment/payment.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String dummyPayment = request.getParameter("dummyPayment");

		/*
		 * ================================================= PHONE DUMMY PAYMENT
		 * =================================================
		 */

		if ("true".equals(dummyPayment)) {

			String bookingId = request.getParameter("bookingId");

			PaymentDAO paymentDAO = new PaymentDAO();

			String transactionId = "DUMMY-" + UUID.randomUUID().toString();

			/*
			 * MARK PAYMENT SUCCESS
			 */

			boolean paymentSuccess = paymentDAO.markPaymentSuccess(bookingId, "QR-DUMMY", transactionId);

			/*
			 * ================================================= CONFIRM BOOKING AND UPDATE
			 * SEATS =================================================
			 */

			if (paymentSuccess) {

				BookingDAO bookingDAO = new BookingDAO();

				boolean bookingConfirmed = bookingDAO.confirmBookingAndSeats(bookingId);

				if (!bookingConfirmed) {
					paymentSuccess = false;
				}
			}

			/*
			 * ================================================= SEND EMAIL AFTER SUCCESSFUL
			 * QR PAYMENT =================================================
			 */

			if (paymentSuccess) {

				HttpSession session = request.getSession(false);

				if (session != null && session.getAttribute("user") != null) {

					TicketDAO ticketDAO = new TicketDAO();

					TicketBean ticket = ticketDAO.getTicketByBookingId(bookingId);

					if (ticket != null) {

						byte[] pdfBytes = PdfService.generateTicketPdf(ticket);

						if (pdfBytes != null) {

							EmailService.sendTicketEmail(ticket, session, pdfBytes);
						}
					}
				}
			}

			/*
			 * ================================================= SUCCESS / FAILURE PAGE
			 * =================================================
			 */

			response.setContentType("text/html;charset=UTF-8");

			if (paymentSuccess) {

				response.getWriter().println(

						"<!DOCTYPE html>" + "<html>" + "<head>" + "<meta charset='UTF-8'>" + "<meta name='viewport' "
								+ "content='width=device-width, initial-scale=1.0'>"
								+ "<title>Payment Successful</title>"

								+ "<style>"

								+ "*{box-sizing:border-box;}"

								+ "body{" + "margin:0;" + "padding:20px;" + "min-height:100vh;" + "background:#f5f5f5;"
								+ "font-family:Arial,Helvetica,sans-serif;" + "display:flex;" + "align-items:center;"
								+ "justify-content:center;" + "}"

								+ ".success-card{" + "width:100%;" + "max-width:400px;" + "background:white;"
								+ "border-radius:16px;" + "padding:30px 25px;" + "text-align:center;"
								+ "box-shadow:0 5px 25px rgba(0,0,0,.12);" + "}"

								+ ".icon{" + "width:65px;" + "height:65px;" + "margin:0 auto 15px;"
								+ "border-radius:50%;" + "background:#198754;" + "color:white;" + "font-size:35px;"
								+ "display:flex;" + "align-items:center;" + "justify-content:center;" + "}"

								+ ".title{" + "font-size:24px;" + "font-weight:bold;" + "color:#222;"
								+ "margin-bottom:8px;" + "}"

								+ ".message{" + "font-size:14px;" + "color:#777;" + "}"

								+ "</style>"

								+ "</head>"

								+ "<body>"

								+ "<div class='success-card'>"

								+ "<div class='icon'>✓</div>"

								+ "<div class='title'>" + "Payment Successful" + "</div>"

								+ "<div class='message'>" + "Your payment has been completed successfully." + "</div>"

								+ "</div>"

								+ "</body>"

								+ "</html>");

			} else {

				response.getWriter().println("<h2>Payment Failed</h2>");
			}

			return;
		}

		/*
		 * ================================================= NORMAL PC PAYMENT
		 * =================================================
		 */

		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("user") == null) {

			response.sendRedirect(request.getContextPath() + "/login.jsp");

			return;
		}

		String bookingId = request.getParameter("bookingId");

		String paymentMethod = request.getParameter("paymentMethod");

		request.setAttribute("bookingId", bookingId);

		request.setAttribute("paymentMethod", paymentMethod);

		request.getRequestDispatcher("/payment/payment-processing.jsp").forward(request, response);
	}
}