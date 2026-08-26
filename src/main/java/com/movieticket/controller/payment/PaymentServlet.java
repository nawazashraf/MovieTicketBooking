package com.movieticket.controller.payment;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.movieticket.dao.BookingDAO;
import com.movieticket.model.BookingBean;

/**
 * Servlet implementation class PaymentServlet
 */
@WebServlet("/payment")
public class PaymentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PaymentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String bookingId =  request.getParameter("bookingId");
		
		BookingDAO bookingDAO = new BookingDAO();
		
		BookingBean booking = bookingDAO.getBookingById(bookingId);
		
		
		if(booking == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND,"Booking not found");
			return;
		}
		
		request.setAttribute("booking", booking);
		
		request.getRequestDispatcher("/payment/payment.jsp").forward(request, response);
	}


}
